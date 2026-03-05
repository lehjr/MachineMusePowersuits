package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnergyGenerationModuleConfig {
    // Combustion Generators ----------------------------------------------------------------------
    private static final ModConfigSpec.Builder COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER = new ModConfigSpec.Builder().push("Energy_Generation").push("Combustion_Generators").push("Combustion_Energy_Generator_Module_1");
    private static final ModConfigSpec.BooleanValue COMBUSTION_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder COMBUSTION_ENERGY_GENERATOR_MODULE_2__BUILDER = COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Combustion_Energy_Generator_Module_2");
    private static final ModConfigSpec.BooleanValue COMBUSTION_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = COMBUSTION_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);


    // Thermal Generators ----------------------------------------------------------------------------
    private static final ModConfigSpec.Builder THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER = COMBUSTION_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Heat_Generators").push("Heat_Energy_Generator_Module_1");
    private static final ModConfigSpec.BooleanValue THERMAL_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.IntValue THERMAL_ENERGY_GENERATOR_MODULE_1__FLUID_TANK_SIZE = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.FLUID_TANK_SIZE, 1000, 0, 100000);
    private static final ModConfigSpec.IntValue THERMAL_ENERGY_GENERATOR_MODULE_1__HEAT_ACTIVATION_PERCENT = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_ACTIVATION_PERCENT , 80, 0, 100);


    // Activation percent
// water per energy
// heat per water

    public static boolean thermalEnergyGenerator_1_isAllowed;
    public static int thermalEnergyGenerator_1_fluidTankSize;
    public static int thermalEnergyGenerator_1_heatActivationPercent;

    public static int thermalEnergyGenerator_1_passiveEnergyPerHeatPerTick;
    public static int thermalEnergyGenerator_1_activeEnergyPerHeatPerTick;
    public static int thermalEnergyGenerator_1_activeWaterPerHeatPerTick;





    private static final ModConfigSpec.Builder HEAT_ENERGY_GENERATOR_MODULE_2__BUILDER = THERMAL_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Heat_Energy_Generator_Module_2");
    private static final ModConfigSpec.BooleanValue HEAT_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = HEAT_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);




















    // Kinetic Generators -------------------------------------------------------------------------
    private static final ModConfigSpec.Builder KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER = HEAT_ENERGY_GENERATOR_MODULE_2__BUILDER.pop().push("Kinetic_Generators").push("Kinetic_Energy_Generator_Module_1");
    private static final ModConfigSpec.BooleanValue KINETIC_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    private static final ModConfigSpec.Builder KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER = KINETIC_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Kinetic_Energy_Generator_Module_2");
    private static final ModConfigSpec.BooleanValue KINETIC_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Solar Generators ---------------------------------------------------------------------------
    private static final ModConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER = KINETIC_ENERGY_GENERATOR_MODULE_2__BUILDER.pop().push("Solar_Generators").push("Solar_Energy_Generator_Module_1");
    private static final ModConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_1__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_1__ENERGY_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_DAY_BASE, 15000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_1__ENERGY_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_NIGHT_BASE, 1500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_1__HEAT_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_DAY_BASE, 15, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_1__HEAT_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_NIGHT_BASE, 5, 0, 100000.0D);


    private static final ModConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_1__BUILDER.pop().push("Solar_Energy_Generator_Module_2");
    private static final ModConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_2__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_2__ENERGY_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_DAY_BASE, 15000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_2__ENERGY_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_NIGHT_BASE, 1500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_2__HEAT_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_DAY_BASE, 15, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_2__HEAT_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_NIGHT_BASE, 5, 0, 100000.0D);

    private static final ModConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.pop().push("Solar_Energy_Generator_Module_3");
    private static final ModConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_3__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_3__ENERGY_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_DAY_BASE, 15000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_3__ENERGY_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_NIGHT_BASE, 1500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_3__HEAT_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_DAY_BASE, 15, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_3__HEAT_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_NIGHT_BASE, 5, 0, 100000.0D);

    private static final ModConfigSpec.Builder SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER = SOLAR_ENERGY_GENERATOR_MODULE_3__BUILDER.pop().push("Solar_Energy_Generator_Module_4");
    private static final ModConfigSpec.BooleanValue SOLAR_ENERGY_GENERATOR_MODULE_4__IS_ALLOWED = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_4__ENERGY_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_DAY_BASE, 15000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_4__ENERGY_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.ENERGY_GENERATION_NIGHT_BASE, 1500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_4__HEAT_GENERATION_DAY_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_DAY_BASE, 15, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue SOLAR_ENERGY_GENERATOR_MODULE_4__HEAT_GENERATION_NIGHT_BASE = SOLAR_ENERGY_GENERATOR_MODULE_2__BUILDER.defineInRange(MPSConstants.HEAT_GENERATION_NIGHT_BASE, 5, 0, 100000.0D);





    public static final ModConfigSpec MPS_GENERATOR_MODULE_SPEC = SOLAR_ENERGY_GENERATOR_MODULE_4__BUILDER.build();


    // Combustion Generators

    // Heat Generators

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
