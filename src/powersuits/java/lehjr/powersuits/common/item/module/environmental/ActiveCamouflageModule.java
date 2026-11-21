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

package lehjr.powersuits.common.item.module.environmental;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
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

public class ActiveCamouflageModule extends AbstractPowerModule {
    private final MobEffect invisibility = MobEffects.INVISIBILITY;

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
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

        class Ticker extends PlayerTickModule {
            public Ticker(ItemStack module) {
                super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TOOLONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION_BASE, EnvironmentalModuleConfig.activeCamouflageModuleEnergyConsumptionBase, "FE");
            }
            @Override
            public int getTier() {
                return 2;
            }

            @Override
            public String getModuleGroup() {
                return "";
            }

            @Override
            public boolean isAllowed() {
                return EnvironmentalModuleConfig.activeCamouflageModuleIsAllowed;
            }

            @Override
            public boolean onPlayerTickActive(Player player, Level level, ItemStack item, int moduleIndex) {
                double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
                MobEffectInstance invis = null;
                if (player.hasEffect(invisibility)) {
                    invis = player.getEffect(invisibility);
                }
                double energyUsage = getEnergyUsage();
                if (energyUsage < totalEnergy) {
                    if (invis == null || invis.getDuration() < 210) {
                        player.addEffect(new MobEffectInstance(invisibility, 500, -3, false, false));
                        ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
                    }
                } else {
                    onPlayerTickInactive(player, level, item,  moduleIndex);
                }
                return false;
            }

            @Override
            public boolean onPlayerTickInactive(Player player, Level level, ItemStack item, int moduleIndex) {
                MobEffectInstance invis = null;
                if (player.hasEffect(invisibility)) {
                    invis = player.getEffect(invisibility);
                }
                if (invis != null && invis.getAmplifier() == -3) {
                    if (level.isClientSide) {
                        player.removeEffectNoUpdate(invisibility);
                    } else {
                        player.removeEffect(invisibility);
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