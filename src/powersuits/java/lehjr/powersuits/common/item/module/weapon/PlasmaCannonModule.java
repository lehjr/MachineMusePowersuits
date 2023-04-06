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

package lehjr.powersuits.common.item.module.weapon;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.numina.common.math.MathUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.entity.PlasmaBallEntity;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class PlasmaCannonModule extends AbstractPowerModule {
    public PlasmaCannonModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final RightClickie rightClickie;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, 100, "FE");
                addBaseProperty(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, 2, "pt");
                addTradeoffProperty(MPSConstants.AMPERAGE, MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, 1500, "FE");
                addTradeoffProperty(MPSConstants.AMPERAGE, MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE, 38, "pt");
                addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.PLASMA_CANNON_ENERGY_PER_TICK, 500, "FE");
                addTradeoffProperty(MPSConstants.VOLTAGE, MPSConstants.PLASMA_CANNON_EXPLOSIVENESS, 0.5F, MPSConstants.CREEPER);
            }};

            powerModuleHolder = LazyOptional.of(() -> rightClickie);
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public InteractionResultHolder<ItemStack> use(@NotNull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
                if (hand == InteractionHand.MAIN_HAND && ElectricItemUtils.getPlayerEnergy(playerIn) > getEnergyUsage()) {
                    playerIn.startUsingItem(hand);
                    return InteractionResultHolder.success(itemStackIn);
                }
                return InteractionResultHolder.pass(itemStackIn);
            }

            @Override
            public void releaseUsing(@NotNull ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
                int chargeTicks = (int) MathUtils.clampDouble(stack.getUseDuration() - timeLeft, 10, 50);
                if (!worldIn.isClientSide && entityLiving instanceof Player) {
                    double chargePercent = chargeTicks * 0.02; // chargeticks/50
                    double energyConsumption = getEnergyUsage() * chargePercent;
                    Player player = (Player) entityLiving;
                    if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption) {
                        float explosiveness = (float) (applyPropertyModifiers(MPSConstants.PLASMA_CANNON_EXPLOSIVENESS) * chargePercent);
                        float damagingness = (float) (applyPropertyModifiers(MPSConstants.PLASMA_CANNON_DAMAGE_AT_FULL_CHARGE) * chargePercent);
                        PlasmaBallEntity plasmaBolt = new PlasmaBallEntity(worldIn, player, explosiveness, damagingness, (float) chargePercent);
                        if (worldIn.addFreshEntity(plasmaBolt)) {
                            HeatUtils.heatPlayer(player, energyConsumption / 5000F * chargePercent);
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

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}