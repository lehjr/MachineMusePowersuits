package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class EnergyGenerationModuleConfig {
    // Combustion Generators ----------------------------------------------------------------------
//    private static final ForgeConfigSpec.Builder COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER = new ForgeConfigSpec.Builder().push("Energy_Generation").push("Combustion_Generators").push("Combustion_Energy_Generator_Module_1");
//    private static final ForgeConfigSpec.BooleanValue COMBUSTION_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
//
//    private static final ForgeConfigSpec.Builder COMBUSTION_ENERGY_GENERATOR_MODULE_2__BUILDER = COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Combustion_Energy_Generator_Module_2");
//    private static final ForgeConfigSpec.BooleanValue COMBUSTION_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = COMBUSTION_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);


    // Thermal Generators ----------------------------------------------------------------------------
    private static final ForgeConfigSpec.Builder THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER = /* COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Thermal_Generators").push("Thermal_Energy_Generator_Module_1"); */
    new ForgeConfigSpec.Builder().push("Energy_Generation").push("Thermal_Generators").push("Thermal_Energy_Generator_Module_1");

    // Tier 1
    private static final ForgeConfigSpec.BooleanValue THERMAL_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    // Thermoelectric
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_1__THERMOELECTRIC_ENERGY_GENERATION = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, 250D, 0, 100000D);
    // Steam Electric
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_1__STEAM_ELECTRIC_WATER_CONSUMPTION_BASE = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION + MPSConstants.BASE, 150D, 1D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_1__STEAM_ELECTRIC_WATER_CONSUMPTION_MULTIPLIER = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION + MPSConstants.MULTIPLIER, 150D, 1D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_1__STEAM_ELECTRIC_ENERGY_GENERATION_BASE = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION + MPSConstants.BASE, 10D, 1D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_1__STEAM_ELECTRIC_ENERGY_GENERATION_MULTIPLIER = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION + MPSConstants.MULTIPLIER, 490D, 0, 100000D);

    // Tier 2
    private static final ForgeConfigSpec.Builder THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Thermal_Energy_Generator_Module_2");
    private static final ForgeConfigSpec.BooleanValue THERMAL_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    // Thermoelectric
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_2__THERMOELECTRIC_ENERGY_GENERATION = THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, 500D, 0, 100000D);
    // Steam Electric
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_2__STEAM_ELECTRIC_WATER_CONSUMPTION_BASE = THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION + MPSConstants.BASE, 150D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_2__STEAM_ELECTRIC_WATER_CONSUMPTION_MULTIPLIER = THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION + MPSConstants.MULTIPLIER, 300D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_2__STEAM_ELECTRIC_ENERGY_GENERATION_BASE = THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION + MPSConstants.BASE, 50D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_2__STEAM_ELECTRIC_ENERGY_GENERATION_MULTIPLIER = THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION + MPSConstants.MULTIPLIER, 950D, 0, 100000D);

    // Tier 3
    private static final ForgeConfigSpec.Builder THERMAL_ENERGY_GENERATOR_MODULE_3__BUILDER = THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER.pop().push("Thermal_Energy_Generator_Module_3");
    private static final ForgeConfigSpec.BooleanValue THERMAL_ENERGY_GENERATOR_MODULE_3__IS_ALLOWED = THERMAL_ENERGY_GENERATOR_MODULE_3__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    // Thermoelectric
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_3__THERMOELECTRIC_ENERGY_GENERATION = THERMAL_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, 750D, 0, 100000D);
    // Steam Electric
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_3__STEAM_ELECTRIC_WATER_CONSUMPTION_BASE = THERMAL_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION + MPSConstants.BASE, 150D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_3__STEAM_ELECTRIC_WATER_CONSUMPTION_MULTIPLIER = THERMAL_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION + MPSConstants.MULTIPLIER, 450D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_3__STEAM_ELECTRIC_ENERGY_GENERATION_BASE = THERMAL_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION + MPSConstants.BASE, 150D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_3__STEAM_ELECTRIC_ENERGY_GENERATION_MULTIPLIER = THERMAL_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION + MPSConstants.MULTIPLIER, 1350D, 0, 100000D);

    // Tier 4
    private static final ForgeConfigSpec.Builder THERMAL_ENERGY_GENERATOR_MODULE_4__BUILDER = THERMAL_ENERGY_GENERATOR_MODULE_3__BUILDER.pop().push("Thermal_Energy_Generator_Module_4");
    private static final ForgeConfigSpec.BooleanValue THERMAL_ENERGY_GENERATOR_MODULE_4__IS_ALLOWED = THERMAL_ENERGY_GENERATOR_MODULE_4__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    // Thermoelectric
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_4__THERMOELECTRIC_ENERGY_GENERATION = THERMAL_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.THERMOELECTRIC_ENERGY_GENERATION, 1000D, 0, 100000D);
    // Steam Electric
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_4__STEAM_ELECTRIC_WATER_CONSUMPTION_BASE = THERMAL_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION + MPSConstants.BASE, 150D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_4__STEAM_ELECTRIC_WATER_CONSUMPTION_MULTIPLIER = THERMAL_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_WATER_CONSUMPTION + MPSConstants.MULTIPLIER, 600D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_4__STEAM_ELECTRIC_ENERGY_GENERATION_BASE = THERMAL_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION + MPSConstants.BASE, 450D, 10D, 100000D);
    private static final ForgeConfigSpec.DoubleValue THERMAL_ENERGY_GENERATOR_MODULE_4__STEAM_ELECTRIC_ENERGY_GENERATION_MULTIPLIER = THERMAL_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.STEAM_ELECTRIC_ENERGY_GENERATION + MPSConstants.MULTIPLIER, 1550D, 0, 100000D);

    // Kinetic Generators -------------------------------------------------------------------------
    private static final ForgeConfigSpec.Builder KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER = THERMAL_ENERGY_GENERATOR_MODULE_2__BUILDER
        .pop(2).push("Kinetic_Generators").push("Kinetic_Energy_Generator_Module_1");
    private static final ForgeConfigSpec.BooleanValue KINETIC_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ForgeConfigSpec.Builder KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER = KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Kinetic_Energy_Generator_Module_2");
    private static final ForgeConfigSpec.BooleanValue KINETIC_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Solar Generators ---------------------------------------------------------------------------
    private static final ForgeConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER = KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER.pop(2).push("Solar_Generators").push("Solar_Energy_Generator_Module_1");
    private static final ForgeConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_1__ENERGY_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_DAY_BASE, 12500, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_1__ENERGY_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_NIGHT_BASE, 1500, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_1__HEAT_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_DAY_BASE, 15, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_1__HEAT_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_NIGHT_BASE, 5, 0, 100000.0D);


    private static final ForgeConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Solar_Energy_Generator_Module_2");
    private static final ForgeConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_2__ENERGY_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_DAY_BASE, 25000, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_2__ENERGY_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_NIGHT_BASE, 1800, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_2__HEAT_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_DAY_BASE, 18, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_2__HEAT_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_NIGHT_BASE, 7, 0, 100000.0D);

    private static final ForgeConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.pop().push("Solar_Energy_Generator_Module_3");
    private static final ForgeConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_3__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_3__ENERGY_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_DAY_BASE, 50000, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_3__ENERGY_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_NIGHT_BASE, 2000, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_3__HEAT_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_DAY_BASE, 20, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_3__HEAT_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_NIGHT_BASE, 9, 0, 100000.0D);

    private static final ForgeConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.pop().push("Solar_Energy_Generator_Module_4");
    private static final ForgeConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_4__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_4__ENERGY_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_DAY_BASE, 10000, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_4__ENERGY_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_NIGHT_BASE, 3000, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_4__HEAT_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_DAY_BASE, 22, 0, 100000.0D);
    private static final ForgeConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_4__HEAT_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_NIGHT_BASE, 10, 0, 100000.0D);

    public static final ForgeConfigSpec MPS_GENERATOR_MODULE_SPEC = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.build();

    // Combustion Generators

    // Thermal Generators
    public static boolean thermalEnergyGenerator_1_isAllowed;
    public static double thermalEnergyGenerator_1_thermoelectricEnergyGeneration;
    public static double thermalEnergyGenerator_1_steamElectricWaterConsumptionBase;
    public static double thermalEnergyGenerator_1_steamElectricWaterConsumptionMultiplier;
    public static double thermalEnergyGenerator_1_steamElectricEnergyGenerationBase;
    public static double thermalEnergyGenerator_1_steamElectricEnergyGenerationMultiplier;

    public static boolean thermalEnergyGenerator_2_isAllowed;
    public static double thermalEnergyGenerator_2_thermoelectricEnergyGeneration;
    public static double thermalEnergyGenerator_2_steamElectricWaterConsumptionBase;
    public static double thermalEnergyGenerator_2_steamElectricWaterConsumptionMultiplier;
    public static double thermalEnergyGenerator_2_steamElectricEnergyGenerationBase;
    public static double thermalEnergyGenerator_2_steamElectricEnergyGenerationMultiplier;

    public static boolean thermalEnergyGenerator_3_isAllowed;
    public static double thermalEnergyGenerator_3_thermoelectricEnergyGeneration;
    public static double thermalEnergyGenerator_3_steamElectricWaterConsumptionBase;
    public static double thermalEnergyGenerator_3_steamElectricWaterConsumptionMultiplier;
    public static double thermalEnergyGenerator_3_steamElectricEnergyGenerationBase;
    public static double thermalEnergyGenerator_3_steamElectricEnergyGenerationMultiplier;

    public static boolean thermalEnergyGenerator_4_isAllowed;
    public static double thermalEnergyGenerator_4_thermoelectricEnergyGeneration;
    public static double thermalEnergyGenerator_4_steamElectricWaterConsumptionBase;
    public static double thermalEnergyGenerator_4_steamElectricWaterConsumptionMultiplier;
    public static double thermalEnergyGenerator_4_steamElectricEnergyGenerationBase;
    public static double thermalEnergyGenerator_4_steamElectricEnergyGenerationMultiplier;
    // Kinetic Generators

    // Solar Generators
    public static boolean solarGeneratorModule_1_IsAllowed;
    public static double solarGeneratorModule_1_energyGenerationDay;
    public static double solarGeneratorModule_1_energyGenerationNight;
    public static double solarGeneratorModule_1_heatGenerationDay;
    public static double solarGeneratorModule_1_heatGenerationNight;

    public static boolean solarGeneratorModule_2_IsAllowed;
    public static double solarGeneratorModule_2_energyGenerationDay;
    public static double solarGeneratorModule_2_energyGenerationNight;
    public static double solarGeneratorModule_2_heatGenerationDay;
    public static double solarGeneratorModule_2_heatGenerationNight;

    public static boolean solarGeneratorModule_3_IsAllowed;
    public static double solarGeneratorModule_3_energyGenerationDay;
    public static double solarGeneratorModule_3_energyGenerationNight;
    public static double solarGeneratorModule_3_heatGenerationDay;
    public static double solarGeneratorModule_3_heatGenerationNight;

    public static boolean solarGeneratorModule_4_IsAllowed;
    public static double solarGeneratorModule_4_energyGenerationDay;
    public static double solarGeneratorModule_4_energyGenerationNight;
    public static double solarGeneratorModule_4_heatGenerationDay;
    public static double solarGeneratorModule_4_heatGenerationNight;

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_GENERATOR_MODULE_SPEC) {
            // Thermal Generators -----------------------------------------------------------------------------------------------
            thermalEnergyGenerator_1_isAllowed = THERMAL_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED.get();
            thermalEnergyGenerator_1_thermoelectricEnergyGeneration = THERMAL_ENERGY_GENERATOR_MODULE_1__THERMOELECTRIC_ENERGY_GENERATION.get();
            thermalEnergyGenerator_1_steamElectricWaterConsumptionBase = THERMAL_ENERGY_GENERATOR_MODULE_1__STEAM_ELECTRIC_WATER_CONSUMPTION_BASE.get();
            thermalEnergyGenerator_1_steamElectricWaterConsumptionMultiplier = THERMAL_ENERGY_GENERATOR_MODULE_1__STEAM_ELECTRIC_WATER_CONSUMPTION_MULTIPLIER.get();
            thermalEnergyGenerator_1_steamElectricEnergyGenerationBase = THERMAL_ENERGY_GENERATOR_MODULE_1__STEAM_ELECTRIC_ENERGY_GENERATION_BASE.get();
            thermalEnergyGenerator_1_steamElectricEnergyGenerationMultiplier = THERMAL_ENERGY_GENERATOR_MODULE_1__STEAM_ELECTRIC_ENERGY_GENERATION_MULTIPLIER.get();

            thermalEnergyGenerator_2_isAllowed = THERMAL_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED.get();
            thermalEnergyGenerator_2_thermoelectricEnergyGeneration = THERMAL_ENERGY_GENERATOR_MODULE_2__THERMOELECTRIC_ENERGY_GENERATION.get();
            thermalEnergyGenerator_2_steamElectricWaterConsumptionBase = THERMAL_ENERGY_GENERATOR_MODULE_2__STEAM_ELECTRIC_WATER_CONSUMPTION_BASE.get();
            thermalEnergyGenerator_2_steamElectricWaterConsumptionMultiplier = THERMAL_ENERGY_GENERATOR_MODULE_2__STEAM_ELECTRIC_WATER_CONSUMPTION_MULTIPLIER.get();
            thermalEnergyGenerator_2_steamElectricEnergyGenerationBase = THERMAL_ENERGY_GENERATOR_MODULE_2__STEAM_ELECTRIC_ENERGY_GENERATION_BASE.get();
            thermalEnergyGenerator_2_steamElectricEnergyGenerationMultiplier = THERMAL_ENERGY_GENERATOR_MODULE_2__STEAM_ELECTRIC_ENERGY_GENERATION_MULTIPLIER.get();

            thermalEnergyGenerator_3_isAllowed = THERMAL_ENERGY_GENERATOR_MODULE_3__IS_ALLOWED.get();
            thermalEnergyGenerator_3_thermoelectricEnergyGeneration = THERMAL_ENERGY_GENERATOR_MODULE_3__THERMOELECTRIC_ENERGY_GENERATION.get();
            thermalEnergyGenerator_3_steamElectricWaterConsumptionBase = THERMAL_ENERGY_GENERATOR_MODULE_3__STEAM_ELECTRIC_WATER_CONSUMPTION_BASE.get();
            thermalEnergyGenerator_3_steamElectricWaterConsumptionMultiplier = THERMAL_ENERGY_GENERATOR_MODULE_3__STEAM_ELECTRIC_WATER_CONSUMPTION_MULTIPLIER.get();
            thermalEnergyGenerator_3_steamElectricEnergyGenerationBase = THERMAL_ENERGY_GENERATOR_MODULE_3__STEAM_ELECTRIC_ENERGY_GENERATION_BASE.get();
            thermalEnergyGenerator_3_steamElectricEnergyGenerationMultiplier = THERMAL_ENERGY_GENERATOR_MODULE_3__STEAM_ELECTRIC_ENERGY_GENERATION_MULTIPLIER.get();

            thermalEnergyGenerator_4_isAllowed = THERMAL_ENERGY_GENERATOR_MODULE_4__IS_ALLOWED.get();
            thermalEnergyGenerator_4_thermoelectricEnergyGeneration = THERMAL_ENERGY_GENERATOR_MODULE_4__THERMOELECTRIC_ENERGY_GENERATION.get();
            thermalEnergyGenerator_4_steamElectricWaterConsumptionBase = THERMAL_ENERGY_GENERATOR_MODULE_4__STEAM_ELECTRIC_WATER_CONSUMPTION_BASE.get();
            thermalEnergyGenerator_4_steamElectricWaterConsumptionMultiplier = THERMAL_ENERGY_GENERATOR_MODULE_4__STEAM_ELECTRIC_WATER_CONSUMPTION_MULTIPLIER.get();
            thermalEnergyGenerator_4_steamElectricEnergyGenerationBase = THERMAL_ENERGY_GENERATOR_MODULE_4__STEAM_ELECTRIC_ENERGY_GENERATION_BASE.get();
            thermalEnergyGenerator_4_steamElectricEnergyGenerationMultiplier = THERMAL_ENERGY_GENERATOR_MODULE_4__STEAM_ELECTRIC_ENERGY_GENERATION_MULTIPLIER.get();


            // Solar Generators -------------------------------------------------------------------------------------------------
            solarGeneratorModule_1_IsAllowed = SOLAR_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED.get();
            solarGeneratorModule_1_energyGenerationDay = SOLAR_ENERGY_GENERATOR_MODULE_1__ENERGY_GENERATION_DAY_BASE.get();
            solarGeneratorModule_1_energyGenerationNight = SOLAR_ENERGY_GENERATOR_MODULE_1__ENERGY_GENERATION_NIGHT_BASE.get();
            solarGeneratorModule_1_heatGenerationDay = SOLAR_ENERGY_GENERATOR_MODULE_1__HEAT_GENERATION_DAY_BASE.get();;
            solarGeneratorModule_1_heatGenerationNight = SOLAR_ENERGY_GENERATOR_MODULE_1__HEAT_GENERATION_NIGHT_BASE.get();

            solarGeneratorModule_2_IsAllowed = SOLAR_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED.get();
            solarGeneratorModule_2_energyGenerationDay = SOLAR_ENERGY_GENERATOR_MODULE_2__ENERGY_GENERATION_DAY_BASE.get();
            solarGeneratorModule_2_energyGenerationNight = SOLAR_ENERGY_GENERATOR_MODULE_2__ENERGY_GENERATION_NIGHT_BASE.get();
            solarGeneratorModule_2_heatGenerationDay = SOLAR_ENERGY_GENERATOR_MODULE_2__HEAT_GENERATION_DAY_BASE.get();;
            solarGeneratorModule_2_heatGenerationNight = SOLAR_ENERGY_GENERATOR_MODULE_2__HEAT_GENERATION_NIGHT_BASE.get();

            solarGeneratorModule_3_IsAllowed = SOLAR_ENERGY_GENERATOR_MODULE_3__IS_ALLOWED.get();
            solarGeneratorModule_3_energyGenerationDay = SOLAR_ENERGY_GENERATOR_MODULE_3__ENERGY_GENERATION_DAY_BASE.get();
            solarGeneratorModule_3_energyGenerationNight = SOLAR_ENERGY_GENERATOR_MODULE_3__ENERGY_GENERATION_NIGHT_BASE.get();
            solarGeneratorModule_3_heatGenerationDay = SOLAR_ENERGY_GENERATOR_MODULE_3__HEAT_GENERATION_DAY_BASE.get();;
            solarGeneratorModule_3_heatGenerationNight = SOLAR_ENERGY_GENERATOR_MODULE_3__HEAT_GENERATION_NIGHT_BASE.get();

            solarGeneratorModule_4_IsAllowed = SOLAR_ENERGY_GENERATOR_MODULE_4__IS_ALLOWED.get();
            solarGeneratorModule_4_energyGenerationDay = SOLAR_ENERGY_GENERATOR_MODULE_4__ENERGY_GENERATION_DAY_BASE.get();
            solarGeneratorModule_4_energyGenerationNight = SOLAR_ENERGY_GENERATOR_MODULE_4__ENERGY_GENERATION_NIGHT_BASE.get();
            solarGeneratorModule_4_heatGenerationDay = SOLAR_ENERGY_GENERATOR_MODULE_4__HEAT_GENERATION_DAY_BASE.get();;
            solarGeneratorModule_4_heatGenerationNight = SOLAR_ENERGY_GENERATOR_MODULE_4__HEAT_GENERATION_NIGHT_BASE.get();
        }
    }
}
