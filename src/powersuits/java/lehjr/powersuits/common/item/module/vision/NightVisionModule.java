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

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.numina.common.tags.TagUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

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
            this.ticker = new Ticker(module, ModuleCategory.VISION, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 100, "FE");
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule {
            boolean added;
            boolean removed;

            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);

                // setting to and loading these just allow values to be persistant when capability reloads
                added = TagUtils.getModuleBooleanOrFalse(module, "added");
                removed = TagUtils.getModuleBooleanOrFalse(module, "removed");
            }

            int tick = 0; // cooldown timer because firing too quickly will crash things and this doesn't need to fire that rapidly anyway

            void setAdded(boolean added) {
                this.added = added;
                TagUtils.setModuleBoolean(module, "added", added);
            }

            void setRemoved(boolean removed) {
                this.removed = removed;
                TagUtils.setModuleBoolean(module, "removed", removed);
            }

            @Override
            public void onPlayerTickActive(Player player, ItemStack item) {
                if (player.level().isClientSide) {
                    return;
                }
                double powerDrain = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);

                double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
                MobEffectInstance nightVisionEffect = player.hasEffect(MobEffects.NIGHT_VISION) ? player.getEffect(MobEffects.NIGHT_VISION) : null;
                if (totalEnergy > powerDrain) {
                    if (nightVisionEffect == null || nightVisionEffect.getDuration() < 500) {
                        MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false);
                        if (player.canBeAffected(mobEffectInstance)) { // is this check needed?
                            if (player.addEffect(mobEffectInstance)) {
                                setAdded(true);
                                setRemoved(false);
                                ElectricItemUtils.drainPlayerEnergy(player, powerDrain);
                            }
                        }
                    }
                } else {
                    onPlayerTickInactive(player, item);
                }
            }

            @Override
            public void onPlayerTickInactive(Player player, ItemStack item) {
                // there doesn't seem to be any way to immediately remove effect.
                if (added && !removed && player.hasEffect(MobEffects.NIGHT_VISION)) {
                    player.removeEffect(MobEffects.NIGHT_VISION);
                    setAdded(false);
                    setRemoved(true);
                }
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