package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class HoeModuleConfig {
    // Stone Rototiller --------------------------------------------------------------
    private static final ModConfigSpec.Builder STONE_ROTOTILLER_MODULE_BUILDER = new ModConfigSpec.Builder().push("Rototillers").push("Stone_Rototiller_Module");
    private static final ModConfigSpec.BooleanValue STONE_ROTOTILLER_MODULE__IS_ALLOWED = STONE_ROTOTILLER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue STONE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_BASE = STONE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 500, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = STONE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_ROTOTILLER_MODULE__HARVEST_SPEED_BASE = STONE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue STONE_ROTOTILLER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = STONE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 3, 0, 100.0D);
    private static final ModConfigSpec.IntValue STONE_ROTOTILLER_MODULE__RADIUS_MULTIPLIER = STONE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.RADIUS_MULTIPLIER, 8, 0, 100);
    private static final ModConfigSpec.DoubleValue STONE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER = STONE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_RADIUS_MULTIPLIER, 9500.0D, 0, 100000.0D);

    // Iron Rototiller ---------------------------------------------------------------
    private static final ModConfigSpec.Builder IRON_ROTOTILLER_MODULE_BUILDER = STONE_ROTOTILLER_MODULE_BUILDER.pop().push("Iron_Rototiller_Module");
    private static final ModConfigSpec.BooleanValue IRON_ROTOTILLER_MODULE__IS_ALLOWED = IRON_ROTOTILLER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue IRON_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_BASE = IRON_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = IRON_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_ROTOTILLER_MODULE__HARVEST_SPEED_BASE = IRON_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue IRON_ROTOTILLER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = IRON_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);
    private static final ModConfigSpec.IntValue IRON_ROTOTILLER_MODULE__RADIUS_MULTIPLIER = IRON_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.RADIUS_MULTIPLIER, 8, 0, 100);
    private static final ModConfigSpec.DoubleValue IRON_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER = IRON_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_RADIUS_MULTIPLIER, 9500.0D, 0, 100000.0D);

    // Diamond Rototiller ------------------------------------------------------------
    private static final ModConfigSpec.Builder DIAMOND_ROTOTILLER_MODULE_BUILDER = IRON_ROTOTILLER_MODULE_BUILDER.pop().push("Diamond_Rototiller_Module");
    private static final ModConfigSpec.BooleanValue DIAMOND_ROTOTILLER_MODULE__IS_ALLOWED = DIAMOND_ROTOTILLER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue DIAMOND_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_BASE = DIAMOND_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = DIAMOND_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_ROTOTILLER_MODULE__HARVEST_SPEED_BASE = DIAMOND_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_ROTOTILLER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = DIAMOND_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);
    private static final ModConfigSpec.IntValue DIAMOND_ROTOTILLER_MODULE__RADIUS_MULTIPLIER = DIAMOND_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.RADIUS_MULTIPLIER, 8, 0, 100);
    private static final ModConfigSpec.DoubleValue DIAMOND_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER = DIAMOND_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_RADIUS_MULTIPLIER, 9500.0D, 0, 100000.0D);

    // Netherite Rototiller ----------------------------------------------------------
    private static final ModConfigSpec.Builder NETHERITE_ROTOTILLER_MODULE_BUILDER = DIAMOND_ROTOTILLER_MODULE_BUILDER.pop().push("Netherite Rototiller");
    private static final ModConfigSpec.BooleanValue NETHERITE_ROTOTILLER_MODULE__IS_ALLOWED = NETHERITE_ROTOTILLER_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue NETHERITE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_BASE = NETHERITE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = NETHERITE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_ROTOTILLER_MODULE__HARVEST_SPEED_BASE = NETHERITE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_ROTOTILLER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = NETHERITE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);
    private static final ModConfigSpec.IntValue NETHERITE_ROTOTILLER_MODULE__RADIUS_MULTIPLIER = NETHERITE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.RADIUS_MULTIPLIER, 8, 0, 100);
    private static final ModConfigSpec.DoubleValue NETHERITE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER = NETHERITE_ROTOTILLER_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_RADIUS_MULTIPLIER, 9500.0D, 0, 100000.0D);

    public static final ModConfigSpec MPS_HOE_MODULE_SPEC = NETHERITE_ROTOTILLER_MODULE_BUILDER.build();

    // Stone axe
    public static boolean stoneRototillerModuleIsAllowed;
    public static double stoneRototillerModuleEnergyConsumptionBase;
    public static double stoneRototillerModuleEnergyConsumptionOverclockMultiplier;
    public static double stoneRototillerModuleHarvestSpeedBase;
    public static double stoneRototillerModuleHarvestSpeedOverclockMultiplier;
    public static int stoneRototillerModuleRadiusMultiplier;
    public static double stoneRototillerModuleEnergyConsumptionRadiusMultiplier;

    // Iron axe
    public static boolean ironRototillerModuleIsAllowed;
    public static double ironRototillerModuleEnergyConsumptionBase;
    public static double ironRototillerModuleEnergyConsumptionOverclockMultiplier;
    public static double ironRototillerModuleHarvestSpeedBase;
    public static double ironRototillerModuleHarvestSpeedOverclockMultiplier;
    public static int ironRototillerModuleRadiusMultiplier;
    public static double ironRototillerModuleEnergyConsumptionRadiusMultiplier;

    // Diamond axe
    public static boolean diamondRototillerModuleIsAllowed;
    public static double diamondRototillerModuleEnergyConsumptionBase;
    public static double diamondRototillerModuleEnergyConsumptionOverclockMultiplier;
    public static double diamondRototillerModuleHarvestSpeedBase;
    public static double diamondRototillerModuleHarvestSpeedOverclockMultiplier;
    public static int diamondRototillerModuleRadiusMultiplier;
    public static double diamondRototillerModuleEnergyConsumptionRadiusMultiplier;

    // Netherite axe
    public static boolean netheriteRototillerModuleIsAllowed;
    public static double netheriteRototillerModuleEnergyConsumptionBase;
    public static double netheriteRototillerModuleEnergyConsumptionOverclockMultiplier;
    public static double netheriteRototillerModuleHarvestSpeedBase;
    public static double netheriteRototillerModuleHarvestSpeedOverclockMultiplier;
    public static int netheriteRototillerModuleRadiusMultiplier;
    public static double netheriteRototillerModuleEnergyConsumptionRadiusMultiplier;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_HOE_MODULE_SPEC) {
            // Stone Rototiller
            stoneRototillerModuleIsAllowed = STONE_ROTOTILLER_MODULE__IS_ALLOWED.get();
            stoneRototillerModuleEnergyConsumptionBase = STONE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            stoneRototillerModuleEnergyConsumptionOverclockMultiplier = STONE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            stoneRototillerModuleHarvestSpeedBase = STONE_ROTOTILLER_MODULE__HARVEST_SPEED_BASE.get();
            stoneRototillerModuleHarvestSpeedOverclockMultiplier = STONE_ROTOTILLER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();
            stoneRototillerModuleRadiusMultiplier = STONE_ROTOTILLER_MODULE__RADIUS_MULTIPLIER.get();
            stoneRototillerModuleEnergyConsumptionRadiusMultiplier = STONE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER.get();

            // Iron Rototiller
            ironRototillerModuleIsAllowed = IRON_ROTOTILLER_MODULE__IS_ALLOWED.get();
            ironRototillerModuleEnergyConsumptionBase = IRON_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            ironRototillerModuleEnergyConsumptionOverclockMultiplier = IRON_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            ironRototillerModuleHarvestSpeedBase = IRON_ROTOTILLER_MODULE__HARVEST_SPEED_BASE.get();
            ironRototillerModuleHarvestSpeedOverclockMultiplier = IRON_ROTOTILLER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();
            ironRototillerModuleRadiusMultiplier = IRON_ROTOTILLER_MODULE__RADIUS_MULTIPLIER.get();
            ironRototillerModuleEnergyConsumptionRadiusMultiplier = IRON_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER.get();

            // Diamond Rototiller
            diamondRototillerModuleIsAllowed = DIAMOND_ROTOTILLER_MODULE__IS_ALLOWED.get();
            diamondRototillerModuleEnergyConsumptionBase = DIAMOND_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            diamondRototillerModuleEnergyConsumptionOverclockMultiplier = DIAMOND_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            diamondRototillerModuleHarvestSpeedBase = DIAMOND_ROTOTILLER_MODULE__HARVEST_SPEED_BASE.get();
            diamondRototillerModuleHarvestSpeedOverclockMultiplier = DIAMOND_ROTOTILLER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();
            diamondRototillerModuleRadiusMultiplier = DIAMOND_ROTOTILLER_MODULE__RADIUS_MULTIPLIER.get();
            diamondRototillerModuleEnergyConsumptionRadiusMultiplier = DIAMOND_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER.get();

            // Netherite Rototiller
            netheriteRototillerModuleIsAllowed = NETHERITE_ROTOTILLER_MODULE__IS_ALLOWED.get();
            netheriteRototillerModuleEnergyConsumptionBase = NETHERITE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_BASE.get();
            netheriteRototillerModuleEnergyConsumptionOverclockMultiplier = NETHERITE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            netheriteRototillerModuleHarvestSpeedBase = NETHERITE_ROTOTILLER_MODULE__HARVEST_SPEED_BASE.get();
            netheriteRototillerModuleHarvestSpeedOverclockMultiplier = NETHERITE_ROTOTILLER_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();
            netheriteRototillerModuleRadiusMultiplier = NETHERITE_ROTOTILLER_MODULE__RADIUS_MULTIPLIER.get();
            netheriteRototillerModuleEnergyConsumptionRadiusMultiplier = NETHERITE_ROTOTILLER_MODULE__ENERGY_CONSUMPTION_RADIUS_MULTIPLIER.get();
        }
    }
}
