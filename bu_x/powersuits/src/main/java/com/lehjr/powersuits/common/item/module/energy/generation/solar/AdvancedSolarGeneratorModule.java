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

package com.lehjr.powersuits.common.item.module.energy.generation.solar;

import com.lehjr.powersuits.common.item.module.AbstractPowerModule;

/**
 * Created by Eximius88 on 1/12/14.
 */
public class AdvancedSolarGeneratorModule extends AbstractPowerModule {
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundTag nbt){
//        return new CapProvider(stack);
//    }
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final Ticker ticker;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.ticker = new Ticker(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, 45000, "FE");
//                addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, 1500, "FE");
//                addBaseProperty(MPSConstants.HEAT_GENERATION_DAY, 15);
//                addBaseProperty(MPSConstants.HEAT_GENERATION_NIGHT, 5);
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                ticker.loadCapValues();
//                return ticker;
//            });
//        }
//
//        class Ticker extends PlayerTickModule {
//            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
//                super(module, category, target, config, true);
//            }
//
//            @Override
//            public int getTier() {
//                return 2;
//            }
//
//            @Override
//            public String getModuleGroup() {
//                return "Energy Generation";
//            }
//
//            @Override
//            public void onPlayerTickActive(Player player, ItemStack itemStack) {
//                Level world = player.level();
//                boolean isRaining, canRain = true;
//                if (world.getGameTime() % 20 == 0) {
//                    canRain = world.getBiome(player.blockPosition()).value().hasPrecipitation();
//                }
//                isRaining = canRain && (world.isRaining() || world.isThundering());
//                boolean sunVisible = world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().above());
//                boolean moonVisible = !world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().above());
//
//                if (!world.isClientSide && world.dimensionType().hasSkyLight() && (world.getGameTime() % 80) == 0) {
//                    float lightLevelScaled = (world.getBrightness(LightLayer.SKY, player.blockPosition().above()) - world.getSkyDarken())/15F;
//
//                    if (sunVisible) {
//                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_DAY) * lightLevelScaled));
//                        HeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.HEAT_GENERATION_DAY) * lightLevelScaled / 2);
//                    } else if (moonVisible) {
//                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_NIGHT) * lightLevelScaled));
//                        HeatUtils.heatPlayer(player, applyPropertyModifiers(MPSConstants.HEAT_GENERATION_NIGHT) * lightLevelScaled / 2);
//                    }
//                }
//            }
//        }
//
//        /** ICapabilityProvider ----------------------------------------------------------------------- */
//        @Override
//        @Nonnull
//        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
//            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
//            if (powerModuleCapability.isPresent()) {
//                return powerModuleCapability;
//            }
//            return LazyOptional.empty();
//        }
//    }
}