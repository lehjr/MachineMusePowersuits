package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnvironmentalModuleConfig {
    private static final ModConfigSpec.Builder ENVIRONMENTAL_MODULE_BUILDER = new ModConfigSpec.Builder().push("Environmental");

    // Active Camouflage
    private static final ModConfigSpec.Builder ACTIVE_CAMOUFLAGE_MODULE_BUILDER = ENVIRONMENTAL_MODULE_BUILDER.push("Active_Camouflage");
    private static final ModConfigSpec.BooleanValue ACTIVE_CAMOUFLAGE_MODULE__IS_ALLOWED = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue ACTIVE_CAMOUFLAGE_MODULE__ENERGY_CONSUMPTION_BASE = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 5, 0, 100);

    // Auto Feeder
    private static final ModConfigSpec.Builder AUTO_FEEDER_MODULE_BUILDER = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.pop().push("Auto_Feeder");
    private static final ModConfigSpec.BooleanValue AUTO_FEEDER_MODULE__IS_ALLOWED = AUTO_FEEDER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.BooleanValue AUTO_FEEDER_MODULE__USE_OLD_AUTO_FEEDER = AUTO_FEEDER_MODULE_BUILDER.define(MPSConstants.USE_OLD_AUTO_FEEDER, false);
    private static final ModConfigSpec.DoubleValue AUTO_FEEDER_MODULE__ENERGY_CONSUMPTION_BASE = AUTO_FEEDER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 100, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue AUTO_FEEDER_MODULE__EATING_EFFICIENCY_BASE = AUTO_FEEDER_MODULE_BUILDER.defineInRange(MPSConstants.AUTO_FEEDER_EFFICIENCY + MPSConstants.BASE, 50, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue AUTO_FEEDER_MODULE__EFFICIENCY_ENERGY__CONSUMPTION_MULTIPLIER = AUTO_FEEDER_MODULE_BUILDER.defineInRange(MPSConstants.EFFICIENCY + MPSConstants.ENERGY_CONSUMPTION + MPSConstants.MULTIPLIER, 1000, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue AUTO_FEEDER_MODULE__EFFICIENCY_EATING_EFFICIENCY_MULTIPLIER = AUTO_FEEDER_MODULE_BUILDER.defineInRange(MPSConstants.EFFICIENCY + MPSConstants.AUTO_FEEDER_EFFICIENCY + MPSConstants.MULTIPLIER, 50, 0, 1000000D);

    // Coolant Tank
    private static final ModConfigSpec.Builder COOLANT_TANK_MODULE_1__BUILDER = ACTIVE_CAMOUFLAGE_MODULE_BUILDER.pop().push("Coolant_Tank_1");
    private static final ModConfigSpec.BooleanValue COOLANT_TANK_MODULE_1__IS_ALLOWED = COOLANT_TANK_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue COOLANT_TANK_MODULE_1_CAPACITY = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 1000, 1000, 100000);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_1__HEAT_ACTIVATION_PERCENT_BASE  = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_BASE , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_1__HEAT_ACTIVATION_PERCENT_MULTIPLIER  = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_MULTIPLIER , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_1__ENERGY_CONSUMPTION_BASE  = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE , 1, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_1__ENERGY_CONSUMPTION_MULTIPLIER  = COOLANT_TANK_MODULE_1__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_MULTIPLIER , 40, 0, 1000000D);

    private static final ModConfigSpec.Builder COOLANT_TANK_MODULE_2__BUILDER = COOLANT_TANK_MODULE_1__BUILDER.pop().push("Coolant_Tank_2");
    private static final ModConfigSpec.BooleanValue COOLANT_TANK_MODULE_2__IS_ALLOWED = COOLANT_TANK_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue COOLANT_TANK_MODULE_2_CAPACITY = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 2000, 1000, 100000);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_2__HEAT_ACTIVATION_PERCENT_BASE  = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_BASE , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_2__HEAT_ACTIVATION_PERCENT_MULTIPLIER  = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_MULTIPLIER , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_2__ENERGY_CONSUMPTION_BASE  = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE , 1, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_2__ENERGY_CONSUMPTION_MULTIPLIER  = COOLANT_TANK_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_MULTIPLIER , 40, 0, 1000000D);

    private static final ModConfigSpec.Builder COOLANT_TANK_MODULE_3__BUILDER = COOLANT_TANK_MODULE_2__BUILDER.pop().push("Coolant_Tank_3");
    private static final ModConfigSpec.BooleanValue COOLANT_TANK_MODULE_3__IS_ALLOWED = COOLANT_TANK_MODULE_3__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue COOLANT_TANK_MODULE_3_CAPACITY = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 3000, 1000, 100000);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_3__HEAT_ACTIVATION_PERCENT_BASE  = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_BASE , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_3__HEAT_ACTIVATION_PERCENT_MULTIPLIER  = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_MULTIPLIER , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_3__ENERGY_CONSUMPTION_BASE  = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE , 1, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_3__ENERGY_CONSUMPTION_MULTIPLIER  = COOLANT_TANK_MODULE_3__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_MULTIPLIER , 40, 0, 1000000D);

    private static final ModConfigSpec.Builder COOLANT_TANK_MODULE_4__BUILDER = COOLANT_TANK_MODULE_3__BUILDER.pop().push("Coolant_Tank_4");
    private static final ModConfigSpec.BooleanValue COOLANT_TANK_MODULE_4__IS_ALLOWED = COOLANT_TANK_MODULE_4__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue COOLANT_TANK_MODULE_4_CAPACITY = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 4000, 1000, 100000);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_4__HEAT_ACTIVATION_PERCENT_BASE  = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_BASE , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_4__HEAT_ACTIVATION_PERCENT_MULTIPLIER  = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT_MULTIPLIER , 0.5, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_4__ENERGY_CONSUMPTION_BASE  = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE , 1, 0, 1000000D);
    private static final ModConfigSpec.DoubleValue COOLANT_TANK_MODULE_4__ENERGY_CONSUMPTION_MULTIPLIER  = COOLANT_TANK_MODULE_4__BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_MULTIPLIER , 40, 0, 1000000D);

    // Magnet
    private static final ModConfigSpec.Builder MAGNET_MODULE_BUILDER = AUTO_FEEDER_MODULE_BUILDER.pop().push("Magnet");
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

    // Auto Feeder
    public static boolean autoFeederModuleIsAllowed;
    public static boolean autoFeederModuleUseOldAutoFeeder;
    public static double autoFeederModuleEnergyConsumptionBase;
    public static double autoFeederModuleEatingEfficiencyBase;
    public static double autoFeederModuleEfficiencyEnergyConsumptionMultiplier;
    public static double autoFeederModuleEfficiencyEatingEfficiencyMultiplier;

    // Coolant Tank
    public static boolean coolantTankModuleIsAllowed1;
    public static int coolantTankModuleCapacity1;
    public static double coolantTankModuleHeatActivationPercentBase1;
    public static double coolantTankModuleHeatActivationPercentMultiplier1;
    public static double coolantTankModuleEnergyConsumptionBase1;
    public static double coolantTankModuleEnergyConsumptionBMultiplier1;

    public static boolean coolantModuleIsAllowed2;
    public static int coolantTankModuleCapacity2;
    public static double coolantTankModuleHeatActivationPercentBase2;
    public static double coolantTankModuleHeatActivationPercentMultiplier2;
    public static double coolantTankModuleEnergyConsumptionBase2;
    public static double coolantTankModuleEnergyConsumptionBMultiplier2;

    public static boolean coolantModuleIsAllowed3;
    public static int coolantTankModuleCapacity3;
    public static double coolantTankModuleHeatActivationPercentBase3;
    public static double coolantTankModuleHeatActivationPercentMultiplier3;
    public static double coolantTankModuleEnergyConsumptionBase3;
    public static double coolantTankModuleEnergyConsumptionBMultiplier3;

    public static boolean coolantModuleIsAllowed4;
    public static int coolantTankModuleCapacity4;
    public static double coolantTankModuleHeatActivationPercentBase4;
    public static double coolantTankModuleHeatActivationPercentMultiplier4;
    public static double coolantTankModuleEnergyConsumptionBase4;
    public static double coolantTankModuleEnergyConsumptionBMultiplier4;

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

            // Coolant Tank
            coolantTankModuleIsAllowed1 = COOLANT_TANK_MODULE_1__IS_ALLOWED.get();
            coolantTankModuleCapacity1 = COOLANT_TANK_MODULE_1_CAPACITY.get();
            coolantTankModuleHeatActivationPercentBase1 = COOLANT_TANK_MODULE_1__HEAT_ACTIVATION_PERCENT_BASE.get();
            coolantTankModuleHeatActivationPercentMultiplier1 = COOLANT_TANK_MODULE_1__HEAT_ACTIVATION_PERCENT_MULTIPLIER.get();
            coolantTankModuleEnergyConsumptionBase1 = COOLANT_TANK_MODULE_1__ENERGY_CONSUMPTION_BASE.get();
            coolantTankModuleEnergyConsumptionBMultiplier1 = COOLANT_TANK_MODULE_1__ENERGY_CONSUMPTION_MULTIPLIER.get();

            coolantModuleIsAllowed2 = COOLANT_TANK_MODULE_2__IS_ALLOWED.get();
            coolantTankModuleCapacity2 = COOLANT_TANK_MODULE_2_CAPACITY.get();
            coolantTankModuleHeatActivationPercentBase2 = COOLANT_TANK_MODULE_2__HEAT_ACTIVATION_PERCENT_BASE.get();
            coolantTankModuleHeatActivationPercentMultiplier2 = COOLANT_TANK_MODULE_2__HEAT_ACTIVATION_PERCENT_MULTIPLIER.get();
            coolantTankModuleEnergyConsumptionBase2 = COOLANT_TANK_MODULE_2__ENERGY_CONSUMPTION_BASE.get();
            coolantTankModuleEnergyConsumptionBMultiplier2 = COOLANT_TANK_MODULE_2__ENERGY_CONSUMPTION_MULTIPLIER.get();

            coolantModuleIsAllowed3 = COOLANT_TANK_MODULE_3__IS_ALLOWED.get();
            coolantTankModuleCapacity3 = COOLANT_TANK_MODULE_3_CAPACITY.get();
            coolantTankModuleHeatActivationPercentBase3 = COOLANT_TANK_MODULE_3__HEAT_ACTIVATION_PERCENT_BASE.get();
            coolantTankModuleHeatActivationPercentMultiplier3 = COOLANT_TANK_MODULE_3__HEAT_ACTIVATION_PERCENT_MULTIPLIER.get();
            coolantTankModuleEnergyConsumptionBase3 = COOLANT_TANK_MODULE_3__ENERGY_CONSUMPTION_BASE.get();
            coolantTankModuleEnergyConsumptionBMultiplier3 = COOLANT_TANK_MODULE_3__ENERGY_CONSUMPTION_MULTIPLIER.get();

            coolantModuleIsAllowed4 = COOLANT_TANK_MODULE_4__IS_ALLOWED.get();
            coolantTankModuleCapacity4 = COOLANT_TANK_MODULE_4_CAPACITY.get();
            coolantTankModuleHeatActivationPercentBase4 = COOLANT_TANK_MODULE_4__HEAT_ACTIVATION_PERCENT_BASE.get();
            coolantTankModuleHeatActivationPercentMultiplier4 = COOLANT_TANK_MODULE_4__HEAT_ACTIVATION_PERCENT_MULTIPLIER.get();
            coolantTankModuleEnergyConsumptionBase4 = COOLANT_TANK_MODULE_4__ENERGY_CONSUMPTION_BASE.get();
            coolantTankModuleEnergyConsumptionBMultiplier4 = COOLANT_TANK_MODULE_4__ENERGY_CONSUMPTION_MULTIPLIER.get();

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
