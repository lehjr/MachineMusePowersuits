package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnvironmentalModuleConfig {
    private static final ModConfigSpec.Builder ENVIRONMENTAL_MODULE_BUILDER = new ModConfigSpec.Builder().push("Environmental");



    // Piglin Pacifigication
    private static final ModConfigSpec.Builder PIGLIN_PACIFICATIOIN_MODULE_BUILDER = ENVIRONMENTAL_MODULE_BUILDER.push("Piglin_Pacification");
    private static final ModConfigSpec.BooleanValue PIGLIN_PACIFICATION_MODULE__IS_ALLOWED = PIGLIN_PACIFICATIOIN_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Water Electrolyzer
    private static final ModConfigSpec.Builder WATER_ELECTROLYZER_MODULE_BUILDER = PIGLIN_PACIFICATIOIN_MODULE_BUILDER.pop().push("Water_Electrolyzer");
    private static final ModConfigSpec.BooleanValue WATER_ELECTROLYZER_MODULE__IS_ALLOWED = WATER_ELECTROLYZER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue WATER_ELECTROLYZER_MODULE__ENERGY_CONSUMPTION_BASE = WATER_ELECTROLYZER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 1000, 0, 100000.0D);

    public static final ModConfigSpec ENVIRONMENTAL_MODULE_SPEC = WATER_ELECTROLYZER_MODULE_BUILDER.build();

    public static boolean piglinPacificationIsAllowed;

    public static boolean waterElectrolyzerIsAllowed;
    public static double waterElectrolyzerEnergyConsumptionBase;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == ENVIRONMENTAL_MODULE_SPEC) {

            piglinPacificationIsAllowed = PIGLIN_PACIFICATION_MODULE__IS_ALLOWED.get();
            // Water Electrolyzer
            waterElectrolyzerIsAllowed = WATER_ELECTROLYZER_MODULE__IS_ALLOWED.get();
            waterElectrolyzerEnergyConsumptionBase = WATER_ELECTROLYZER_MODULE__ENERGY_CONSUMPTION_BASE.get();
        }
    }
}
