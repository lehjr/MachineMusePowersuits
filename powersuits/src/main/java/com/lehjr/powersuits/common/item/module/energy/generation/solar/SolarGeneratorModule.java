package com.lehjr.powersuits.common.item.module.energy.generation.solar;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.HeatUtils;
import com.lehjr.powersuits.common.config.module.EnergyGenerationModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;

public class SolarGeneratorModule extends AbstractPowerModule {

    public static class SolarGeneratorTickingCapability extends PlayerTickModule {
        int tier;
        public SolarGeneratorTickingCapability(ItemStack module, int tier) {
            super(module, ModuleCategory.ENERGY_GENERATION, ModuleTarget.HEADONLY);
            this.tier = tier;

            switch(tier) {
                case 1-> {
//                    addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, 15000, "FE");
//                    addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, 1500, "FE");
//                    addBaseProperty(MPSConstants.HEAT_GENERATION_DAY, 15);
//                    addBaseProperty(MPSConstants.HEAT_GENERATION_NIGHT, 5);
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
        public void onPlayerTickActive(Player player, Level level, @NotNull ItemStack item) {
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
        }
    }
}
