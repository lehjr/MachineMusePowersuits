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

package lehjr.powersuits.common.item.module.energy_generation;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.EnergyGenerationModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SolarGeneratorModule extends AbstractPowerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundTag nbt){
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final SolarGeneratorTickingCapability ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new SolarGeneratorTickingCapability(module, getTier(module));

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        public static class SolarGeneratorTickingCapability extends PlayerTickModule {
            int tier;
            public SolarGeneratorTickingCapability(ItemStack module, int tier) {
                super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.HEADONLY);
                this.tier = tier;

                switch(tier) {
                    case 1-> {
                        addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, EnergyGenerationModuleConfig.solarGeneratorModule_1_energyGenerationDay, "FE");
                        addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, EnergyGenerationModuleConfig.solarGeneratorModule_1_energyGenerationNight, "FE");
                        addBaseProperty(MPSConstants.HEAT_GENERATION_DAY, EnergyGenerationModuleConfig.solarGeneratorModule_1_heatGenerationDay);
                        addBaseProperty(MPSConstants.HEAT_GENERATION_NIGHT, EnergyGenerationModuleConfig.solarGeneratorModule_1_heatGenerationNight);
                    }
                    case 2 ->{
                        addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, EnergyGenerationModuleConfig.solarGeneratorModule_2_energyGenerationDay, "FE");
                        addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, EnergyGenerationModuleConfig.solarGeneratorModule_2_energyGenerationNight, "FE");
                        addBaseProperty(MPSConstants.HEAT_GENERATION_DAY, EnergyGenerationModuleConfig.solarGeneratorModule_2_heatGenerationDay);
                        addBaseProperty(MPSConstants.HEAT_GENERATION_NIGHT, EnergyGenerationModuleConfig.solarGeneratorModule_2_heatGenerationNight);
                    }

                    case 3 -> {
                        addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, EnergyGenerationModuleConfig.solarGeneratorModule_3_energyGenerationDay, "FE");
                        addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, EnergyGenerationModuleConfig.solarGeneratorModule_3_energyGenerationNight, "FE");
                        addBaseProperty(MPSConstants.HEAT_GENERATION_DAY, EnergyGenerationModuleConfig.solarGeneratorModule_3_heatGenerationDay);
                        addBaseProperty(MPSConstants.HEAT_GENERATION_NIGHT, EnergyGenerationModuleConfig.solarGeneratorModule_3_heatGenerationNight);
                    }

                    case 4 -> {
                        addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, EnergyGenerationModuleConfig.solarGeneratorModule_4_energyGenerationDay, "FE");
                        addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, EnergyGenerationModuleConfig.solarGeneratorModule_4_energyGenerationNight, "FE");
                        addBaseProperty(MPSConstants.HEAT_GENERATION_DAY, EnergyGenerationModuleConfig.solarGeneratorModule_4_heatGenerationDay);
                        addBaseProperty(MPSConstants.HEAT_GENERATION_NIGHT, EnergyGenerationModuleConfig.solarGeneratorModule_4_heatGenerationNight);
                    }
                }
            }

            @Override
            public boolean isAllowed() {
                switch (tier) {
                    case 1-> {
                        return EnergyGenerationModuleConfig.solarGeneratorModule_1_IsAllowed;
                    }
                    case 2-> {
                        return EnergyGenerationModuleConfig.solarGeneratorModule_2_IsAllowed;
                    }
                    case 3-> {
                        return EnergyGenerationModuleConfig.solarGeneratorModule_3_IsAllowed;
                    }
                    case 4-> {
                        return EnergyGenerationModuleConfig.solarGeneratorModule_4_IsAllowed;
                    }
                }
                return false;
            }

            @Override
            public boolean onPlayerTickActive(Player player, Level level, @NotNull ItemStack item, int moduleIndex) {
                Level world = player.level();
                boolean isRaining, canRain = true;
                if (world.getGameTime() % 20 == 0) {
                    canRain = world.getBiome(player.blockPosition()).value().hasPrecipitation();
                }
                isRaining = canRain && (world.isRaining() || world.isThundering());
                boolean sunVisible = world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().above());
                boolean moonVisible = !world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().above());

                if (!world.isClientSide && world.dimensionType().hasSkyLight() && (world.getGameTime() % 80) == 0) {
                    float lightLevelScaled = (world.getBrightness(LightLayer.SKY, player.blockPosition().above()) - world.getSkyDarken())/15F;

                    if (sunVisible) {
                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_DAY) * lightLevelScaled), false);
                        HeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.HEAT_GENERATION_DAY) * lightLevelScaled / 2);
                    } else if (moonVisible) {
                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_NIGHT) * lightLevelScaled), false);
                        HeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.HEAT_GENERATION_NIGHT) * lightLevelScaled / 2);
                    }
                }
                return false;
            }

            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public String getModuleGroup() {
                return "solar generators";
            }
        }

        static int getTier(ItemStack stack) {
            if(stack.is(MPSItems.SOLAR_GENERATOR_MODULE_1.get())) {
                return 1;
            }
            if(stack.is(MPSItems.SOLAR_GENERATOR_MODULE_2.get())) {
                return 2;
            }
            if(stack.is(MPSItems.SOLAR_GENERATOR_MODULE_3.get())) {
                return 3;
            }
            if (stack.is(MPSItems.SOLAR_GENERATOR_MODULE_4.get())) {
                return 4;
            }
            return 0;
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