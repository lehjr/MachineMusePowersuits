package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;


public class ToolModuleConfig {
    // Miscellaneous Modules ------------------------------------------------------------------------------------------
    // Flint and Steel -------------------------------------------------------
    private static final ModConfigSpec.Builder FLINT_AND_STEEL_MODULE__SETTINGS_BUILDER =  new ModConfigSpec.Builder().push("Tool_Modules").push("Miscellaneous").push("Flint_And_Steel_Module");
    private static final ModConfigSpec.BooleanValue FLINT_AND_STEEL__IS_ALLOWED = FLINT_AND_STEEL_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue FLINT_AND_STEEL_MODULE__ENERGY_CONSUMPTION_BASE = FLINT_AND_STEEL_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 10000, 0, 100000.0D);

    // Leaf Blower
    private static final ModConfigSpec.Builder LEAF_BLOWER_MODULE__SETTINGS_BUILDER = FLINT_AND_STEEL_MODULE__SETTINGS_BUILDER.pop().push("Leaf_Blower_Module");
    private static final ModConfigSpec.BooleanValue LEAF_BLOWER_MODULE__IS_ALLOWED = LEAF_BLOWER_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);

    // Lux Capacitor
    private static final ModConfigSpec.Builder LUX_CAPACITOR_MODULE__SETTINGS_BUILDER = LEAF_BLOWER_MODULE__SETTINGS_BUILDER.pop().push("Lux Capacitor Module");
    private static final ModConfigSpec.BooleanValue LUX_CAPACITOR_MODULE__IS_ALLOWED = LUX_CAPACITOR_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue LUX_CAPACITOR_MODULE__ENERGY_CONSUMPTION_BASE = LUX_CAPACITOR_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 1000, 0, 100000.0D);
    public static final ModConfigSpec MPS_TOOL_MODULE_SPEC = LUX_CAPACITOR_MODULE__SETTINGS_BUILDER.build();

    // Flint and Steel
    public static boolean flintAndSteelModuleIsAllowed;
    public static double flintAndSteelEnergyConsumption;

    // Leaf Blower
    public static boolean leafBlowerModuleIsAllowed;

    // Lux Capacitor
    public static boolean luxCapacitorModuleIsAllowed;
    public static double luxCapacitorEnergyConsumptionBase;


    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_TOOL_MODULE_SPEC) {

            // Flint And Steel
            flintAndSteelModuleIsAllowed = FLINT_AND_STEEL__IS_ALLOWED.get();
            flintAndSteelEnergyConsumption = FLINT_AND_STEEL_MODULE__ENERGY_CONSUMPTION_BASE.get();

            // Leaf Blower
            leafBlowerModuleIsAllowed = LEAF_BLOWER_MODULE__IS_ALLOWED.get();

            // Lux Capacitor
            luxCapacitorModuleIsAllowed = LUX_CAPACITOR_MODULE__IS_ALLOWED.get();
            luxCapacitorEnergyConsumptionBase = LUX_CAPACITOR_MODULE__ENERGY_CONSUMPTION_BASE.get();
        }
    }
}
