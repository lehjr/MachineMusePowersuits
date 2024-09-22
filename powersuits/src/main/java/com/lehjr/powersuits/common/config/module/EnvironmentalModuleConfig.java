package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnvironmentalModuleConfig {
    private static final ModConfigSpec.Builder ENVIRONMENTAL_MODULE_BUILDER = new ModConfigSpec.Builder().push("Environmental");

    private static final ModConfigSpec.Builder ACTIVE_CAMOUFLAGE_MODULE_BUILDER = ENVIRONMENTAL_MODULE_BUILDER.push("Active Camouflage");
    private static final ModConfigSpec.BooleanValue ACTIVE_CAMOUFLAGE_MODULE__IS_ALLOWED = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue ACTIVE_CAMOUFLAGE_MODULE__ENERGY_CONSUMPTION_BASE = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5, 0, 100);

    // Magnet
    private static final ModConfigSpec.Builder MAGNET_MODULE_BUILDER = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.pop().push("Magnet");
    private static final ModConfigSpec.BooleanValue MAGNET_MODULE__IS_ALLOWED = MAGNET_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue MAGNET_MODULE__RADIUS_BASE = MAGNET_MODULE_BUILDER.defineInRange(MPSConstants.RADIUS, 1, 0, 100);
    private static final ModConfigSpec.IntValue MAGNET_MODULE__RADIUS_MULTIPLIER = MAGNET_MODULE_BUILDER.defineInRange(MPSConstants.RADIUS_MULTIPLIER, 9, 0, 100);
    private static final ModConfigSpec.IntValue MAGNET_MODULE__ENERGY_CONSUMPTION_BASE = MAGNET_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5, 0, 100);
    private static final ModConfigSpec.IntValue MAGNET_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER = MAGNET_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_RADIUS_MULTIPLIER, 2000, 0, 100000);

    // Mob Repulsor
    private static final ModConfigSpec.Builder MOB_REPULSOR_MODULE_BUILDER = MAGNET_MODULE_BUILDER.pop().push("Mob_Repulsor");
    private static final ModConfigSpec.BooleanValue MOB_REPULSOR_MODULE__IS_ALLOWED = MOB_REPULSOR_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue MOB_REPULSOR_MODULE__ENERGY_CONSUMPTION_BASE = MOB_REPULSOR_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 2500, 0, 100000.0D);

    // Piglin Pacifigication
    private static final ModConfigSpec.Builder PIGLIN_PACIFICATIOIN_MODULE_BUILDER = MOB_REPULSOR_MODULE_BUILDER.pop().push("Piglin_Pacification");
    private static final ModConfigSpec.BooleanValue PIGLIN_PACIFICATION_MODULE__IS_ALLOWED = PIGLIN_PACIFICATIOIN_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Water Electrolyzer
    private static final ModConfigSpec.Builder WATER_ELECTROLYZER_MODULE_BUILDER = PIGLIN_PACIFICATIOIN_MODULE_BUILDER.pop().push("Water_Electrolyzer");
    private static final ModConfigSpec.BooleanValue WATER_ELECTROLYZER_MODULE__IS_ALLOWED = WATER_ELECTROLYZER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue WATER_ELECTROLYZER_MODULE__ENERGY_CONSUMPTION_BASE = WATER_ELECTROLYZER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 1000, 0, 100000.0D);

    public static final ModConfigSpec ENVIRONMENTAL_MODULE_SPEC = WATER_ELECTROLYZER_MODULE_BUILDER.build();

    // Active Camouflage
    public static boolean activeCamouflageModuleIsAllowed;
    public static int activeCamouflageModuleEnergyConsumptionBase;

    // Magnet
    public static boolean magnetModuleIsAllowed;
    public static int magnetModuleRadiusBase;
    public static int magnetModuleRadiusMultiplier;
    public static double magnetModuleEnergyConsumptionBase;
    public static double magnetModuleEnergyConsumptionRadiusMultiplier;

    // Mob Repulsor
    public static boolean mobRepulsorModuleIsAllowed;
    public static double mobReulsorModuleEnergyConsumptionBase;

    // Piglin Pacification
    public static boolean piglinPacificationIsAllowed;

    // Water Electrolyzer
    public static boolean waterElectrolyzerIsAllowed;
    public static double waterElectrolyzerEnergyConsumptionBase;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == ENVIRONMENTAL_MODULE_SPEC) {
            // Active Camouflage
            activeCamouflageModuleIsAllowed = ACTIVE_CAMOUFLAGE_MODULE__IS_ALLOWED.get();
            activeCamouflageModuleEnergyConsumptionBase = ACTIVE_CAMOUFLAGE_MODULE__ENERGY_CONSUMPTION_BASE.get();

            // Magnet
            magnetModuleIsAllowed = MAGNET_MODULE__IS_ALLOWED.get();
            magnetModuleRadiusBase = MAGNET_MODULE__RADIUS_BASE.get();
            magnetModuleRadiusMultiplier = MAGNET_MODULE__RADIUS_MULTIPLIER.get();
            magnetModuleEnergyConsumptionBase = MAGNET_MODULE__ENERGY_CONSUMPTION_BASE.get();
            magnetModuleEnergyConsumptionRadiusMultiplier = MAGNET_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER.get();

            // Mob Repulsor
            mobRepulsorModuleIsAllowed = MOB_REPULSOR_MODULE__IS_ALLOWED.get();
            mobReulsorModuleEnergyConsumptionBase = MOB_REPULSOR_MODULE__ENERGY_CONSUMPTION_BASE.get();

            // Piglin Pacification
            piglinPacificationIsAllowed = PIGLIN_PACIFICATION_MODULE__IS_ALLOWED.get();

            // Water Electrolyzer
            waterElectrolyzerIsAllowed = WATER_ELECTROLYZER_MODULE__IS_ALLOWED.get();
            waterElectrolyzerEnergyConsumptionBase = WATER_ELECTROLYZER_MODULE__ENERGY_CONSUMPTION_BASE.get();
        }
    }
}
