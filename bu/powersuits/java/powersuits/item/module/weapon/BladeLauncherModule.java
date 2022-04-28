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

package lehjr.powersuits.item.module.weapon;

import lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.entity.SpinningBladeEntity;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class BladeLauncherModule extends AbstractPowerModule {
    public BladeLauncherModule() {
        super();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IRightClickModule rightClickie;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module, EnumModuleCategory.WEAPON, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.BLADE_ENERGY, 5000, "FE");
                addBaseProperty(MPSConstants.BLADE_DAMAGE, 6, "pt");
            }};
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> rightClickie));
        }

        class RightClickie extends RightClickModule {
            public RightClickie(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResult use(ItemStack itemStackIn, World worldIn, Player playerIn, Hand hand) {
                if (hand == Hand.MAIN_HAND) {
                    if (ElectricItemUtils.getPlayerEnergy(playerIn) > applyPropertyModifiers(MPSConstants.BLADE_ENERGY)) {
                        playerIn.startUsingItem(hand);
                        return new ActionResult(InteractionResult.SUCCESS, itemStackIn);
                    }
                }
                return new ActionResult(InteractionResult.PASS, itemStackIn);
            }

            @Override
            public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
                if (!worldIn.isClientSide) {
                   int energyConsumption = getEnergyUsage();

                    if (ElectricItemUtils.getPlayerEnergy(entityLiving) > energyConsumption) {
                        ElectricItemUtils.drainPlayerEnergy(entityLiving, energyConsumption);
                        SpinningBladeEntity blade = new SpinningBladeEntity(worldIn, entityLiving);
                        worldIn.addFreshEntity(blade);
                    }
                }
            }

            @Override
            public int getEnergyUsage() {
                return (int) Math.round(applyPropertyModifiers(MPSConstants.BLADE_ENERGY));
            }
        }
    }
}