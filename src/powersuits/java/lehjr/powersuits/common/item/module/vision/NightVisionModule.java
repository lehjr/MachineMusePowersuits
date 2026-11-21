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

package lehjr.powersuits.common.item.module.vision;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.TagUtils;
import lehjr.powersuits.common.config.module.VisionModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NightVisionModule extends AbstractPowerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundTag nbt){
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module);
            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        static final String ADDED = "added";
        static final String REMOVED = "removed";

        public static class Ticker extends PlayerTickModule {
            boolean added;
            boolean removed;

            public Ticker(@Nonnull ItemStack module) {
                super(module, ModuleCategory.VISION, ModuleTarget.HEADONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, VisionModuleConfig.nightVisionEnergyConsumption, "FE");
                // setting to and loading these just allow values to be persistant when capability reloads
                added = TagUtils.getModuleBoolean(module, ADDED);
                removed = TagUtils.getModuleBoolean(module, REMOVED);
            }

            //        int tick = 0; // cooldown timer because firing too quickly will crash things and this doesn't need to fire that rapidly anyway

            @Override
            public int getTier() {
                return 1;
            }

            @Override
            public String getModuleGroup() {
                return "";
            }

            @Override
            public boolean isAllowed() {
                return VisionModuleConfig.nightVisionModuleIsAllowed;
            }

            @Override
            public boolean onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item, int moduleIndex) {
                if (level.isClientSide) {
                    return false;
                }
                double powerDrain = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);

                double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
                MobEffectInstance nightVisionEffect = player.hasEffect(MobEffects.NIGHT_VISION) ? player.getEffect(MobEffects.NIGHT_VISION) : null;
                if (totalEnergy > powerDrain) {
                    if (nightVisionEffect == null || nightVisionEffect.getDuration() < 500) {
                        MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.NIGHT_VISION, 800, 0, false, false);
                        if (player.addEffect(mobEffectInstance)) {
                            IModularItem modularItem = NuminaCapabilities.getModularItem(item);
                            if (modularItem != null) {
                                modularItem.setModuleBoolean(MPSConstants.NIGHT_VISION_MODULE, ADDED, true);
                                modularItem.setModuleBoolean(MPSConstants.NIGHT_VISION_MODULE, REMOVED, false);
                            }
                            ElectricItemUtils.drainPlayerEnergy(player, powerDrain, false);
                        }
                    }
                } else {
                    return onPlayerTickInactive(player, level, item,  moduleIndex);
                }
                return false;
            }

            @Override
            public boolean onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack item, int moduleIndex) {
                // there doesn't seem to be any way to immediately remove effect.
                if (added && !removed && player.hasEffect(MobEffects.NIGHT_VISION)) {
                    player.removeEffect(MobEffects.NIGHT_VISION);
                    IModularItem modularItem = NuminaCapabilities.getModularItem(item);
                    if (modularItem != null) {
                        modularItem.setModuleBoolean(MPSConstants.NIGHT_VISION_MODULE, ADDED, false);
                        modularItem.setModuleBoolean(MPSConstants.NIGHT_VISION_MODULE, REMOVED, true);
                    }
                }
                return false;
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}