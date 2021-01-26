/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.powersuits.item.module.weapon;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.numina.util.heat.MuseHeatUtils;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.entity.PlasmaBallEntity;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class PlasmaCannonModule extends AbstractPowerModule {
    public PlasmaCannonModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IRightClickModule rightClickie;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, EnumModuleCategory.WEAPON, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.rightClickie.addBaseProperty(MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, 100, "FE");
            this.rightClickie.addBaseProperty(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, 2, "pt");
            this.rightClickie.addTradeoffProperty(MPSConstants.AMPERAGE, MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, 1500, "FE");
            this.rightClickie.addTradeoffProperty(MPSConstants.AMPERAGE, MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, 38, "pt");
            this.rightClickie.addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, 500, "FE");
            this.rightClickie.addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.PLASMA_CANNON_EXPLOSIVENESS, 0.5F, MPSConstants.CREEPER);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClickie));
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResult onItemRightClick(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                if (hand == Hand.MAIN_HAND && ElectricItemUtils.getPlayerEnergy(playerIn) > getEnergyUsage()) {
                    playerIn.setActiveHand(hand);
                    return ActionResult.resultSuccess(itemStackIn);
                }
                return ActionResult.resultPass(itemStackIn);
            }

            @Override
            public void onPlayerStoppedUsing(ItemStack itemStack, World worldIn, LivingEntity entityLiving, int timeLeft) {
                int chargeTicks = (int) MuseMathUtils.clampDouble(itemStack.getUseDuration() - timeLeft, 10, 50);
                if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
                    double chargePercent = chargeTicks * 0.02; // chargeticks/50
                    double energyConsumption = getEnergyUsage() * chargePercent;
                    PlayerEntity player = (PlayerEntity) entityLiving;
                    if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption) {
                        float explosiveness = (float) (applyPropertyModifiers(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS) * chargePercent);
                        float damagingness = (float) (applyPropertyModifiers(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE) * chargePercent);
                        PlasmaBallEntity plasmaBolt = new PlasmaBallEntity(worldIn, player, explosiveness, damagingness, (float) chargePercent);
                        if (worldIn.addEntity(plasmaBolt)) {
                            MuseHeatUtils.heatPlayer(player, energyConsumption / 5000F * chargePercent);
                            ElectricItemUtils.drainPlayerEnergy(player, (int) energyConsumption);
                        }
                    }
                }
            }

            @Override
            public int getEnergyUsage() {
                return (int) Math.round(applyPropertyModifiers(MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK));
            }
        }
    }
}