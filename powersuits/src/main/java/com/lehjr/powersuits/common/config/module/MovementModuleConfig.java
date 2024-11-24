package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MovementModuleConfig {
    // Blink Drive
    private static final ModConfigSpec.Builder BLINK_DRIVE_MODULE__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Movement_Modules").push("Blink_Drive");
    private static final ModConfigSpec.BooleanValue BLINK_DRIVE_MODULE__IS_ALLOWED = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_BASE = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 10000, 0, 400000.0D);
    private static final ModConfigSpec.DoubleValue BLINK_DRIVE_MODULE__RANGE_BASE = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.RANGE_BASE, 5, 0, 1000D);
    private static final ModConfigSpec.DoubleValue BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_RANGE_MULTIPLIER = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_RANGE_MULTIPLIER, 140000, 0, 1000000.0D);
    private static final ModConfigSpec.DoubleValue BLINK_DRIVE_MODULE__RANGE_MULTIPLIER = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.RANGE_MULTIPLIER, 95, 0, 10000.0D);

    // Dimensional Rift Module
    private static final ModConfigSpec.Builder DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.pop().push("Dimensional_Rift");
    private static final ModConfigSpec.BooleanValue DIMENSIONAL_RIFT_MODULE__IS_ALLOWED = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue DIMENSIONAL_RIFT_MODULE__HEAT_GENERATION_BASE = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.RANGE_MULTIPLIER, 55, 0, 10000.0D);
    private static final ModConfigSpec.DoubleValue DIMENSIONAL_RIFT_MODULE__ENERGY_CONSUMPTION_BASE = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 200000, 0, 1000000.0D);

    // Flight Control
    private static final ModConfigSpec.Builder FLIGHT_CONTROL_MODULE__SETTINGS_BUILDER = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.pop().push("Flight_Control");
    private static final ModConfigSpec.BooleanValue FLIGHT_CONTRTOL_MODULE__IS_ALLOWED = FLIGHT_CONTROL_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Glider
    private static final ModConfigSpec.Builder GLIDER_MODULE__SETTINGS_BUILDER = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.pop().push("Glider");
    private static final ModConfigSpec.BooleanValue GLIDER_MODULE__IS_ALLOWED = GLIDER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);


    private static final ModConfigSpec.Builder JETBOOTS_MODULE__SETTINGS_BUILDER = GLIDER_MODULE__SETTINGS_BUILDER.pop().push("JetBoots");


    private static final ModConfigSpec.Builder JETPACK_MODULE__SETTINGS_BUILDER = JETBOOTS_MODULE__SETTINGS_BUILDER.pop().push("JetPack");

    private static final ModConfigSpec.Builder JUMP_ASSIST_MODULE__SETTINGS_BUILDER = JETPACK_MODULE__SETTINGS_BUILDER.pop().push("Jump_Assist");

    private static final ModConfigSpec.Builder PARACHUTE_MODULE__SETTINGS_BUILDER = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.pop().push("Parachute");

    private static final ModConfigSpec.Builder SHOCK_ABSORBER_MODULE__SETTINGS_BUILDER = PARACHUTE_MODULE__SETTINGS_BUILDER.pop().push("Shock_Absorber");

    private static final ModConfigSpec.Builder SPRINT_ASSIST_MODULE__SETTINGS_BUILDER = SHOCK_ABSORBER_MODULE__SETTINGS_BUILDER.pop().push("Sprint_Assist");

    private static final ModConfigSpec.Builder STEP_ASSIST_MODULE__SETTINGS_BUILDER = SPRINT_ASSIST_MODULE__SETTINGS_BUILDER.pop().push("Step_Assist");

    private static final ModConfigSpec.Builder SWIM_ASSIST_MODULE__SETTINGS_BUILDER = STEP_ASSIST_MODULE__SETTINGS_BUILDER.pop().push("Step_Assist");










    // --------------------
    // JetBootsModule
    // --------------------
    // JetPackModule
    // --------------------
    // JumpAssistModule
    // --------------------
    // ParachuteModule
    // --------------------
    // ShockAbsorberModule
    // --------------------
    // SprintAssistModule
    // --------------------
    // SwimAssistModule

    // Bink Drive
    public static boolean blinkDriveModuleIsAllowed;
    public static double blinkDriveModuleEnergyConsumptionBase;
    public static double blinkDriveModuleRangeBase;
    public static double blinkDriveModuleEnergyConsumptionRangeMultiplier;
    public static double blinkDriveModuleRangeMultiplier;

    // Dimensional Rift
    public static boolean dimensionalRiftModuleIsAllowed;
    public static double dimensionalRiftModuleHeatGenerationBase;
    public static double dimensionalRiftModuleEnergyConsumptionBase;

    // Flight Control
    public static boolean flightControlModuleIsAllowed;

    // Glider
    public static boolean gliderModuleIsAllowed;





    public static final ModConfigSpec MPS_MOVEMENGT_MODULE_SPEC = SWIM_ASSIST_MODULE__SETTINGS_BUILDER.build();
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_MOVEMENGT_MODULE_SPEC) {
            // Blink Drive
            blinkDriveModuleIsAllowed = BLINK_DRIVE_MODULE__IS_ALLOWED.get();
            blinkDriveModuleEnergyConsumptionBase = BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            blinkDriveModuleRangeBase = BLINK_DRIVE_MODULE__RANGE_BASE.get();
            blinkDriveModuleEnergyConsumptionRangeMultiplier = BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_RANGE_MULTIPLIER.get();
            blinkDriveModuleRangeMultiplier = BLINK_DRIVE_MODULE__RANGE_MULTIPLIER.get();

            // Dimensional Rift
            dimensionalRiftModuleIsAllowed = DIMENSIONAL_RIFT_MODULE__IS_ALLOWED.get();
            dimensionalRiftModuleHeatGenerationBase = DIMENSIONAL_RIFT_MODULE__HEAT_GENERATION_BASE.get();
            dimensionalRiftModuleEnergyConsumptionBase = DIMENSIONAL_RIFT_MODULE__ENERGY_CONSUMPTION_BASE.get();

            // Flight Control
            flightControlModuleIsAllowed = FLIGHT_CONTRTOL_MODULE__IS_ALLOWED.get();

            // Glider
            gliderModuleIsAllowed = GLIDER_MODULE__IS_ALLOWED.get();





        }
    }
}
