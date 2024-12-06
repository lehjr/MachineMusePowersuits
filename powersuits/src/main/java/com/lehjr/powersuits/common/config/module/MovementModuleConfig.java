package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.utils.StringUtils;
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

    // Climb Assist
    private static final ModConfigSpec.Builder CLIMB_ASSIST_MODULE__SETTINGS_BUILDER =  BLINK_DRIVE_MODULE__SETTINGS_BUILDER.pop().push("Climb_Assist");
    private static final ModConfigSpec.BooleanValue CLIMB_ASSIST_MODULE__IS_ALLOWED = CLIMB_ASSIST_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Dimensional Rift Module
    private static final ModConfigSpec.Builder DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER = CLIMB_ASSIST_MODULE__SETTINGS_BUILDER.pop().push("Dimensional_Rift");
    private static final ModConfigSpec.BooleanValue DIMENSIONAL_RIFT_MODULE__IS_ALLOWED = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue DIMENSIONAL_RIFT_MODULE__HEAT_GENERATION_BASE = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.RANGE_MULTIPLIER, 55, 0, 10000.0D);
    private static final ModConfigSpec.DoubleValue DIMENSIONAL_RIFT_MODULE__ENERGY_CONSUMPTION_BASE = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 200000, 0, 1000000.0D);

    // Flight Control
    private static final ModConfigSpec.Builder FLIGHT_CONTROL_MODULE__SETTINGS_BUILDER = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.pop().push("Flight_Control");
    private static final ModConfigSpec.BooleanValue FLIGHT_CONTRTOL_MODULE__IS_ALLOWED = FLIGHT_CONTROL_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Glider
    private static final ModConfigSpec.Builder GLIDER_MODULE__SETTINGS_BUILDER = DIMENSIONAL_RIFT_MODULE__SETTINGS_BUILDER.pop().push("Glider");
    private static final ModConfigSpec.BooleanValue GLIDER_MODULE__IS_ALLOWED = GLIDER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // JetBoots
    private static final ModConfigSpec.Builder JETBOOTS_MODULE__SETTINGS_BUILDER = GLIDER_MODULE__SETTINGS_BUILDER.pop().push("JetBoots");
    private static final ModConfigSpec.BooleanValue JETBOOTS_MODULE__IS_ALLOWED = JETBOOTS_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue JETBOOTS_MODULE__ENERGY_CONSUMPTION_BASE = JETBOOTS_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 0, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JETBOOTS_MODULE__THRUST_BASE = JETBOOTS_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.THRUST, 0, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JETBOOTS_MODULE__ENERGY_CONSUMPTION_THRUST_MULTIPLIER = JETBOOTS_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_THRUST_MULTIPLIER, 750, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JETBOOTS_MODULE__THRUST_MULTIPLIER = JETBOOTS_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.THRUST_MULTIPLIER, 0.08, 0, 10000000.0D);

    // JetPack
    private static final ModConfigSpec.Builder JETPACK_MODULE__SETTINGS_BUILDER = JETBOOTS_MODULE__SETTINGS_BUILDER.pop().push("JetPack");
    private static final ModConfigSpec.BooleanValue JETPACK_MODULE__IS_ALLOWED = JETPACK_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue JETPACK_MODULE__ENERGY_CONSUMPTION_BASE = JETPACK_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 0, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JETPACK_MODULE__THRUST_BASE = JETPACK_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.THRUST, 0, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JETPACK_MODULE__ENERGY_CONSUMPTION_THRUST_MULTIPLIER = JETPACK_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_THRUST_MULTIPLIER, 15000, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JETPACK_MODULE__THRUST_MULTIPLIER = JETPACK_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.THRUST_MULTIPLIER, 0.16, 0, 10000000.0D);

    // Jump Assist
    private static final ModConfigSpec.Builder JUMP_ASSIST_MODULE__SETTINGS_BUILDER = JETPACK_MODULE__SETTINGS_BUILDER.pop().push("Jump_Assist");
    private static final ModConfigSpec.BooleanValue JUMP_ASSIST_MODULE__IS_ALLOWED = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue JUMP_ASSIST_MODULE__ENERGY_CONSUMPTION_BASE = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 0, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JUMP_ASSIST_MODULE__ENERGY_CONSUMPTION_POWER_MULTIPLIER = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_POWER_MULTIPLIER, 250, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JUMP_ASSIST_MODULE__JUMP_BOOST_BASE = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.JUMP_BOOST_BASE, 0, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JUMP_ASSIST_MODULE__JUMP_BOOST_POWER_MULTIPLIER = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.JUMP_BOOST + StringUtils.capitalize(MPSConstants.POWER) + MPSConstants.MULTIPLIER, 4, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JUMP_ASSIST_MODULE__ENERGY_CONSUMPTION_COMPENSATION_MULTIPLIER = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_COMPENSATION_MULTIPLIER, 50, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JUMP_ASSIST_MODULE__EXHAUSTION_COMPENSATION_BASE = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.EXAUSTION_COMPENSATION + MPSConstants.BASE, 0, 0, 10000000.0D);
    private static final ModConfigSpec.DoubleValue JUMP_ASSIST_MODULE__EXHAUSTION_COMPENSATION_COMPENSATION_MULTIPLIER = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.EXAUSTION_COMPENSATION + StringUtils.capitalize(MPSConstants.COMPENSATION) + MPSConstants.MULTIPLIER, 1, 0, 10000000.0D);

    // Pararachute




    private static final ModConfigSpec.Builder PARACHUTE_MODULE__SETTINGS_BUILDER = JUMP_ASSIST_MODULE__SETTINGS_BUILDER.pop().push("Parachute");

    private static final ModConfigSpec.Builder SHOCK_ABSORBER_MODULE__SETTINGS_BUILDER = PARACHUTE_MODULE__SETTINGS_BUILDER.pop().push("Shock_Absorber");

    private static final ModConfigSpec.Builder SPRINT_ASSIST_MODULE__SETTINGS_BUILDER = SHOCK_ABSORBER_MODULE__SETTINGS_BUILDER.pop().push("Sprint_Assist");


    private static final ModConfigSpec.Builder SWIM_ASSIST_MODULE__SETTINGS_BUILDER = SPRINT_ASSIST_MODULE__SETTINGS_BUILDER.pop().push("Swim_Assist");
    private static final ModConfigSpec.BooleanValue SWIM_ASSIST_MODULE__IS_ALLOWED = SWIM_ASSIST_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue SWIM_ASSIST_MODULE__ENERGY_CONSUMPTION_RANGE_MULTIPLIER = SWIM_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_THRUST_MULTIPLIER, 1000, 0, 1000000.0D);
    private static final ModConfigSpec.DoubleValue SWIM_ASSIST_MODULE__THRUST_BOOST_MULTIPLIER = SWIM_ASSIST_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.THRUST.toUpperCase() + MPSConstants.SWIM_BOOST_AMOUNT + MPSConstants.MULTIPLIER, 1, 0, 100.0D);







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

    // Climb Assist
    public static boolean climbAssistModuleIsAllowed;

    // Dimensional Rift
    public static boolean dimensionalRiftModuleIsAllowed;
    public static double dimensionalRiftModuleHeatGenerationBase;
    public static double dimensionalRiftModuleEnergyConsumptionBase;

    // Flight Control
    public static boolean flightControlModuleIsAllowed;

    // Glider
    public static boolean gliderModuleIsAllowed;

    // JetBoots
    public static boolean jetBootsModuleIsAllowed;
    public static double jetBootsModuleEnergyConsumptionBase;
    public static double jetBootsThrustBase;
    public static double jetBootsEnergyConsumptionThrustMultiplier;
    public static double jetBootsThrustMultiplier;

    // JetPack
    public static boolean jetPackModuleIsAllowed;
    public static double jetPackModuleEnergyConsumptionBase;
    public static double jetPackThrustBase;
    public static double jetPackEnergyConsumptionThrustMultiplier;
    public static double jetPackThrustMultiplier;

    // Jump Assist
    public static boolean jumpAssistModuleIsAllowed;
    public static double jumpAssistModuleEnergyConsumptionBase;
    public static double jumpAssistModuleEnergyConsumptionPowerMultiplier;
    public static double jumpAssistModuleJumpBoostBase;
    public static double jumpAssistModuleJumpBoostPowerMultiplier;
    public static double jumpAssistModuleEnergyConsumptionCompensationMultiplier;
    public static double jumpAssistModuleExhaustionCompensationBase;
    public static double jumpAssistModuleExhaustionCompensationCompensationMultiplier;

    // Swim Assist
    public static boolean swimAssistModuleIsAllowed;
    public static double swimAssistModuleEnergyConsumptionThrustMultiplier;
    public static double swimAssistModuleThrustBoostMultiplier;

    public static final ModConfigSpec MPS_MOVEMENGT_MODULE_SPEC = SWIM_ASSIST_MODULE__SETTINGS_BUILDER.build();
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_MOVEMENGT_MODULE_SPEC) {
            // Blink Drive
            blinkDriveModuleIsAllowed = BLINK_DRIVE_MODULE__IS_ALLOWED.get();
            blinkDriveModuleEnergyConsumptionBase = BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            blinkDriveModuleRangeBase = BLINK_DRIVE_MODULE__RANGE_BASE.get();
            blinkDriveModuleEnergyConsumptionRangeMultiplier = BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_RANGE_MULTIPLIER.get();
            blinkDriveModuleRangeMultiplier = BLINK_DRIVE_MODULE__RANGE_MULTIPLIER.get();

            // Climb Assist
            climbAssistModuleIsAllowed = CLIMB_ASSIST_MODULE__IS_ALLOWED.get();

            // Dimensional Rift
            dimensionalRiftModuleIsAllowed = DIMENSIONAL_RIFT_MODULE__IS_ALLOWED.get();
            dimensionalRiftModuleHeatGenerationBase = DIMENSIONAL_RIFT_MODULE__HEAT_GENERATION_BASE.get();
            dimensionalRiftModuleEnergyConsumptionBase = DIMENSIONAL_RIFT_MODULE__ENERGY_CONSUMPTION_BASE.get();

            // Flight Control
            flightControlModuleIsAllowed = FLIGHT_CONTRTOL_MODULE__IS_ALLOWED.get();

            // Glider
            gliderModuleIsAllowed = GLIDER_MODULE__IS_ALLOWED.get();

            //  JetBoots
            jetBootsModuleIsAllowed = JETBOOTS_MODULE__IS_ALLOWED.get();
            jetBootsModuleEnergyConsumptionBase = JETBOOTS_MODULE__ENERGY_CONSUMPTION_BASE.get();
            jetBootsThrustBase = JETBOOTS_MODULE__THRUST_BASE.get();
            jetBootsEnergyConsumptionThrustMultiplier = JETBOOTS_MODULE__ENERGY_CONSUMPTION_THRUST_MULTIPLIER.get();
            jetBootsThrustMultiplier = JETBOOTS_MODULE__THRUST_MULTIPLIER.get();

            //  JetPack
            jetPackModuleIsAllowed = JETPACK_MODULE__IS_ALLOWED.get();
            jetPackModuleEnergyConsumptionBase = JETPACK_MODULE__ENERGY_CONSUMPTION_BASE.get();
            jetPackThrustBase = JETPACK_MODULE__THRUST_BASE.get();
            jetPackEnergyConsumptionThrustMultiplier = JETPACK_MODULE__ENERGY_CONSUMPTION_THRUST_MULTIPLIER.get();
            jetPackThrustMultiplier = JETPACK_MODULE__THRUST_MULTIPLIER.get();

            // Jump Assist
            jumpAssistModuleIsAllowed = JUMP_ASSIST_MODULE__IS_ALLOWED.get();
            jumpAssistModuleEnergyConsumptionBase = JUMP_ASSIST_MODULE__ENERGY_CONSUMPTION_BASE.get();
            jumpAssistModuleEnergyConsumptionPowerMultiplier = JUMP_ASSIST_MODULE__ENERGY_CONSUMPTION_POWER_MULTIPLIER.get();
            jumpAssistModuleJumpBoostBase = JUMP_ASSIST_MODULE__JUMP_BOOST_BASE.get();
            jumpAssistModuleJumpBoostPowerMultiplier = JUMP_ASSIST_MODULE__JUMP_BOOST_POWER_MULTIPLIER.get();
            jumpAssistModuleEnergyConsumptionCompensationMultiplier = JUMP_ASSIST_MODULE__ENERGY_CONSUMPTION_COMPENSATION_MULTIPLIER.get();
            jumpAssistModuleExhaustionCompensationBase = JUMP_ASSIST_MODULE__EXHAUSTION_COMPENSATION_BASE.get();
            jumpAssistModuleExhaustionCompensationCompensationMultiplier = JUMP_ASSIST_MODULE__EXHAUSTION_COMPENSATION_COMPENSATION_MULTIPLIER.get();

            // Swim Assist
            swimAssistModuleIsAllowed = SWIM_ASSIST_MODULE__IS_ALLOWED.get();
            swimAssistModuleEnergyConsumptionThrustMultiplier = SWIM_ASSIST_MODULE__ENERGY_CONSUMPTION_RANGE_MULTIPLIER.get();
            swimAssistModuleThrustBoostMultiplier = SWIM_ASSIST_MODULE__THRUST_BOOST_MULTIPLIER.get();
        }
    }
}
