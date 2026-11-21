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

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.WeaponModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.entity.SpinningBladeEntity;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class BladeLauncherModule extends AbstractPowerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final RightClickie rightClickie;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(ItemStack module) {
            this.module = module;
            this.rightClickie = new RightClickie(module);
            powerModuleHolder = LazyOptional.of(() -> rightClickie);
        }

        public static class RightClickie extends RightClickModule {
            public RightClickie(ItemStack module) {
                super(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, WeaponModuleConfig.bladeLauncherEnergyConsumption, "FE");
                addBaseProperty(MPSConstants.BLADE_DAMAGE, WeaponModuleConfig.bladeLauncherSpinningBladeDamage, "pt");
            }

            @Override
            public InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
                if (hand == InteractionHand.MAIN_HAND) {
                    if (ElectricItemUtils.getPlayerEnergy(playerIn) > applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION)) {
                        playerIn.startUsingItem(hand);
                        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStackIn);
                    }
                }
                return new InteractionResultHolder<>(InteractionResult.PASS, itemStackIn);
            }

            @Override
            public void releaseUsing(ItemStack stack, Level levelIn, LivingEntity entityLiving, int timeLeft) {
                if (!levelIn.isClientSide) {
                    double energyConsumption = getEnergyUsage();

                    if (ElectricItemUtils.getPlayerEnergy(entityLiving) > energyConsumption) {
                        ElectricItemUtils.drainPlayerEnergy(entityLiving, energyConsumption, false);
                        SpinningBladeEntity blade = new SpinningBladeEntity(levelIn, entityLiving);
                        levelIn.addFreshEntity(blade);
                    }
                }
            }

            @Override
            public boolean isModuleOnline() {
                return true;
            }

            @Override
            public boolean isAllowed() {
                return WeaponModuleConfig.bladeLauncherIsAllowed;
            }

            @Override
            public double getEnergyUsage() {
                return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }

            @Override
            public int getTier() {
                return 1;
            }

            @Override
            public String getModuleGroup() {
                return "";
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        public <T> LazyOptional<T> getCapability(final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}