package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnvironmentalModuleConfig {
    private static final ModConfigSpec.Builder ENVIRONMENTAL_MODULE_BUILDER = new ModConfigSpec.Builder().push("Environmental");


    // Water Electrolyzer
    private static final ModConfigSpec.Builder WATER_ELECTROLYZER_MODULE_BUILDER = ENVIRONMENTAL_MODULE_BUILDER.push("WaterElectrolyzer");
    private static final ModConfigSpec.BooleanValue WATER_ELECTROLYZER_MODULE__IS_ALLOWED = WATER_ELECTROLYZER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue WATER_ELECTROLYZER_MODULE__ENERGY_CONSUMPTION_BASE = WATER_ELECTROLYZER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 1000, 0, 100000.0D);

    public static final ModConfigSpec ENVIRONMENTAL_MODULE_SPEC = WATER_ELECTROLYZER_MODULE_BUILDER.build();

    public static boolean waterElectrolyzerIsAllowed;
    public static double waterElectrolyzerEnergyConsumptionBase;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == ENVIRONMENTAL_MODULE_SPEC) {


            // Water Electrolyzer
            waterElectrolyzerIsAllowed = WATER_ELECTROLYZER_MODULE__IS_ALLOWED.get();
            waterElectrolyzerEnergyConsumptionBase = WATER_ELECTROLYZER_MODULE__ENERGY_CONSUMPTION_BASE.get();
        }
    }
}
