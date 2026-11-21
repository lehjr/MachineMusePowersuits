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

import lehjr.numina.client.config.NuminaClientConfig;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WaterElectrolyzerModule extends AbstractPowerModule {

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

        public static class Ticker extends PlayerTickModule {

            public Ticker(ItemStack module) {
                super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.HEADONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, EnvironmentalModuleConfig.waterElectrolyzerEnergyConsumptionBase, "FE");
            }

            @Override
            public int getTier() {
                return -1;
            }

            @Override
            public String getModuleGroup() {
                return "";
            }

            @Override
            public boolean isAllowed() {
                return EnvironmentalModuleConfig.waterElectrolyzerIsAllowed;
            }

            @Override
            public boolean onPlayerTickActive(Player player, Level level, ItemStack item, int moduleIndex) {
                double energy = ElectricItemUtils.getPlayerEnergy(player);
                double energyConsumption = Math.round(applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION));
                if (energy > energyConsumption && player.getAirSupply() < 10) {
                    if ((level.isClientSide()) && NuminaClientConfig.useSounds) {
                        player.playSound(MPSSoundDictionary.SOUND_EVENT_ELECTROLYZER.get(), 1.0f, 1.0f);
                    }
                    ElectricItemUtils.drainPlayerEnergy(player, energyConsumption, false);
                    player.setAirSupply(300);
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