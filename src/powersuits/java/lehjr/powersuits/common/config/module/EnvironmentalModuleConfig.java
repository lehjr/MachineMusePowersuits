package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class EnvironmentalModuleConfig {
    private static final ForgeConfigSpec.Builder ENVIRONMENTAL_MODULE_BUILDER = new ForgeConfigSpec.Builder().push("Environmental");

    // Active Camouflage
    private static final ForgeConfigSpec.Builder ACTIVE_CAMOUFLAGE_MODULE_BUILDER = ENVIRONMENTAL_MODULE_BUILDER.push("Active_Camouflage");
    private static final ForgeConfigSpec.BooleanValue ACTIVE_CAMOUFLAGE_MODULE__IS_ALLOWED = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.IntValue ACTIVE_CAMOUFLAGE_MODULE__ENERGY_CONSUMPTION_BASE = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5, 0, 100);

    // Auto Feeder
    private static final ForgeConfigSpec.Builder AUTO_FEEDER_MODULE_BUILDER = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.pop().push("Auto_Feeder");
    private static final ForgeConfigSpec.BooleanValue AUTO_FEEDER_MODULE__IS_ALLOWED = AUTO_FEEDER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.BooleanValue AUTO_FEEDER_MODULE__USE_OLD_AUTO_FEEDER = AUTO_FEEDER_MODULE_BUILDER.define(MPSConstants.USE_OLD_AUTO_FEEDER, false);
    private static final ForgeConfigSpec.DoubleValue AUTO_FEEDER_MODULE__ENERGY_CONSUMPTION_BASE = AUTO_FEEDER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 100, 0, 1000000D);
    private static final ForgeConfigSpec.DoubleValue AUTO_FEEDER_MODULE__EATING_EFFICIENCY_BASE = AUTO_FEEDER_MODULE_BUILDER.defineInRange(MPSConstants.AUTO_FEEDER_EFFICIENCY + MPSConstants.BASE, 50, 0, 1000000D);
    private static final ForgeConfigSpec.DoubleValue AUTO_FEEDER_MODULE__EFFICIENCY_ENERGY__CONSUMPTION_MULTIPLIER = AUTO_FEEDER_MODULE_BUILDER.defineInRange(MPSConstants.EFFICIENCY + MPSConstants.ENERGY_CONSUMPTION + MPSConstants.MULTIPLIER, 1000, 0, 1000000D);
    private static final ForgeConfigSpec.DoubleValue AUTO_FEEDER_MODULE__EFFICIENCY_EATING_EFFICIENCY_MULTIPLIER = AUTO_FEEDER_MODULE_BUILDER.defineInRange(MPSConstants.EFFICIENCY + MPSConstants.AUTO_FEEDER_EFFICIENCY + MPSConstants.MULTIPLIER, 50, 0, 1000000D);

    // Magnet
    private static final ForgeConfigSpec.Builder MAGNET_MODULE_BUILDER = AUTO_FEEDER_MODULE_BUILDER.pop().push("Magnet");
    private static final ForgeConfigSpec.BooleanValue MAGNET_MODULE__IS_ALLOWED = MAGNET_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.IntValue MAGNET_MODULE__RADIUS_BASE = MAGNET_MODULE_BUILDER.defineInRange(MPSConstants.RADIUS, 1, 0, 100);
    private static final ForgeConfigSpec.IntValue MAGNET_MODULE__RADIUS_MULTIPLIER = MAGNET_MODULE_BUILDER.defineInRange(MPSConstants.RADIUS_MULTIPLIER, 9, 0, 100);
    private static final ForgeConfigSpec.IntValue MAGNET_MODULE__ENERGY_CONSUMPTION_BASE = MAGNET_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5, 0, 100);
    private static final ForgeConfigSpec.IntValue MAGNET_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER = MAGNET_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_RADIUS_MULTIPLIER, 2000, 0, 100000);

    // Mob Repulsor
    private static final ForgeConfigSpec.Builder MOB_REPULSOR_MODULE_BUILDER = MAGNET_MODULE_BUILDER.pop().push("Mob_Repulsor");
    private static final ForgeConfigSpec.BooleanValue MOB_REPULSOR_MODULE__IS_ALLOWED = MOB_REPULSOR_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue MOB_REPULSOR_MODULE__ENERGY_CONSUMPTION_BASE = MOB_REPULSOR_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 2500, 0, 100000.0D);

    // Piglin Pacifigication
    private static final ForgeConfigSpec.Builder PIGLIN_PACIFICATIOIN_MODULE_BUILDER = MOB_REPULSOR_MODULE_BUILDER.pop().push("Piglin_Pacification");
    private static final ForgeConfigSpec.BooleanValue PIGLIN_PACIFICATION_MODULE__IS_ALLOWED = PIGLIN_PACIFICATIOIN_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Water Electrolyzer
    private static final ForgeConfigSpec.Builder WATER_ELECTROLYZER_MODULE_BUILDER = PIGLIN_PACIFICATIOIN_MODULE_BUILDER.pop().push("Water_Electrolyzer");
    private static final ForgeConfigSpec.BooleanValue WATER_ELECTROLYZER_MODULE__IS_ALLOWED = WATER_ELECTROLYZER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue WATER_ELECTROLYZER_MODULE__ENERGY_CONSUMPTION_BASE = WATER_ELECTROLYZER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 1000, 0, 100000.0D);

    public static final ForgeConfigSpec ENVIRONMENTAL_MODULE_SPEC = WATER_ELECTROLYZER_MODULE_BUILDER.build();

    // Active Camouflage
    public static boolean activeCamouflageModuleIsAllowed;
    public static int activeCamouflageModuleEnergyConsumptionBase;

    // Auto Feeder
    public static boolean autoFeederModuleIsAllowed;
    public static boolean autoFeederModuleUseOldAutoFeeder;
    public static double autoFeederModuleEnergyConsumptionBase;
    public static double autoFeederModuleEatingEfficiencyBase;
    public static double autoFeederModuleEfficiencyEnergyConsumptionMultiplier;
    public static double autoFeederModuleEfficiencyEatingEfficiencyMultiplier;

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

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == ENVIRONMENTAL_MODULE_SPEC) {
            // Active Camouflage
            activeCamouflageModuleIsAllowed = ACTIVE_CAMOUFLAGE_MODULE__IS_ALLOWED.get();
            activeCamouflageModuleEnergyConsumptionBase = ACTIVE_CAMOUFLAGE_MODULE__ENERGY_CONSUMPTION_BASE.get();

            // Auto Feeder
            autoFeederModuleIsAllowed = AUTO_FEEDER_MODULE__IS_ALLOWED.get();
            autoFeederModuleUseOldAutoFeeder = AUTO_FEEDER_MODULE__USE_OLD_AUTO_FEEDER.get();
            autoFeederModuleEnergyConsumptionBase = AUTO_FEEDER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            autoFeederModuleEatingEfficiencyBase = AUTO_FEEDER_MODULE__EATING_EFFICIENCY_BASE.get();
            autoFeederModuleEfficiencyEnergyConsumptionMultiplier = AUTO_FEEDER_MODULE__EFFICIENCY_ENERGY__CONSUMPTION_MULTIPLIER.get();
            autoFeederModuleEfficiencyEatingEfficiencyMultiplier = AUTO_FEEDER_MODULE__EFFICIENCY_EATING_EFFICIENCY_MULTIPLIER.get();

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
