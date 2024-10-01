package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MovementModuleConfig {
    private static final ModConfigSpec.Builder BLINK_DRIVE_MODULE__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Movement_Modules").push("Blink_Drive");
    private static final ModConfigSpec.BooleanValue BLINK_DRIVE_MODULE__IS_ALLOWED = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_BASE = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 10000, 0, 400000.0D);
    private static final ModConfigSpec.DoubleValue BLINK_DRIVE_MODULE__RANGE_BASE = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.RANGE_BASE, 5, 0, 1000D);
    private static final ModConfigSpec.DoubleValue BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_RANGE_MULTIPLIER = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_RANGE_MULTIPLIER, 140000, 0, 1000000.0D);
    private static final ModConfigSpec.DoubleValue BLINK_DRIVE_MODULE__RANGE_MULTIPLIER = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.RANGE_MULTIPLIER, 95, 0, 10000.0D);

    public static boolean blinkDriveModuleIsAllowed;
    public static double blinkDriveModuleEnergyConsumptionBase;
    public static double blinkDriveModuleRangeBase;
    public static double blinkDriveModuleEnergyConsumptionRangeMultiplier;
    public static double blinkDriveModuleRangeMultiplier;

    public static final ModConfigSpec MPS_MOVEMENGT_MODULE_SPEC = BLINK_DRIVE_MODULE__SETTINGS_BUILDER.build();

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_MOVEMENGT_MODULE_SPEC) {
            blinkDriveModuleIsAllowed = BLINK_DRIVE_MODULE__IS_ALLOWED.get();
            blinkDriveModuleEnergyConsumptionBase = BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            blinkDriveModuleRangeBase = BLINK_DRIVE_MODULE__RANGE_BASE.get();
            blinkDriveModuleEnergyConsumptionRangeMultiplier = BLINK_DRIVE_MODULE__ENERGY_CONSUMPTION_RANGE_MULTIPLIER.get();
            blinkDriveModuleRangeMultiplier = BLINK_DRIVE_MODULE__RANGE_MULTIPLIER.get();
        }
    }
}
