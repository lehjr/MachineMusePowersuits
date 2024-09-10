package com.lehjr.powersuits.common.config.module.tool.blockbreaking;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class AxeModuleConfig {
    // Stone Axe --------------------------------------------------------------
    private static final ModConfigSpec.Builder STONE_AXE_MODULE_BUILDER = new ModConfigSpec.Builder().push("Axes").push("Stone_Axe_Module");
    private static final ModConfigSpec.BooleanValue STONE_AXE_MODULE__IS_ALLOWED = STONE_AXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue STONE_AXE_MODULE__ENERGY_CONSUMPTION_BASE = STONE_AXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = STONE_AXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_AXE_MODULE__HARVEST_SPEED_BASE = STONE_AXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue STONE_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = STONE_AXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 3, 0, 100.0D);

    // Iron Axe ---------------------------------------------------------------
    private static final ModConfigSpec.Builder IRON_AXE_MODULE_BUILDER = STONE_AXE_MODULE_BUILDER.pop().push("Iron_Axe_Module");
    private static final ModConfigSpec.BooleanValue IRON_AXE_MODULE__IS_ALLOWED = IRON_AXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue IRON_AXE_MODULE__ENERGY_CONSUMPTION_BASE = IRON_AXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = IRON_AXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_AXE_MODULE__HARVEST_SPEED_BASE = IRON_AXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue IRON_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = IRON_AXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Diamond Axe ------------------------------------------------------------
    private static final ModConfigSpec.Builder DIAMOND_AXE_MODULE_BUILDER = IRON_AXE_MODULE_BUILDER.pop().push("Diamond_Axe_Module");
    private static final ModConfigSpec.BooleanValue DIAMOND_AXE_MODULE__IS_ALLOWED = DIAMOND_AXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue DIAMOND_AXE_MODULE__ENERGY_CONSUMPTION_BASE = DIAMOND_AXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = DIAMOND_AXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_AXE_MODULE__HARVEST_SPEED_BASE = DIAMOND_AXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = DIAMOND_AXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Netherite Axe ----------------------------------------------------------
    private static final ModConfigSpec.Builder NETHERITE_AXE_MODULE_BUILDER = DIAMOND_AXE_MODULE_BUILDER.pop().push("Netherite Axe");
    private static final ModConfigSpec.BooleanValue NETHERITE_AXE_MODULE__IS_ALLOWED = NETHERITE_AXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue NETHERITE_AXE_MODULE__ENERGY_CONSUMPTION_BASE = NETHERITE_AXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = NETHERITE_AXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_AXE_MODULE__HARVEST_SPEED_BASE = NETHERITE_AXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = NETHERITE_AXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    public static final ModConfigSpec MPS_AXE_MODULE_SPEC = NETHERITE_AXE_MODULE_BUILDER.build();

    // Stone axe
    public static boolean stoneAxeModuleIsAllowed;
    public static double stoneAxeModuleEnergyConsumptionBase;
    public static double stoneAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double stoneAxeModuleHarvestSpeedBase;
    public static double stoneAxeModuleHarvestSpeedOverclockMultiplier;

    // Iron axe
    public static boolean ironAxeModuleIsAllowed;
    public static double ironAxeModuleEnergyConsumptionBase;
    public static double ironAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double ironAxeModuleHarvestSpeedBase;
    public static double ironAxeModuleHarvestSpeedOverclockMultiplier;

    // Diamond axe
    public static boolean diamondAxeModuleIsAllowed;
    public static double diamondAxeModuleEnergyConsumptionBase;
    public static double diamondAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double diamondAxeModuleHarvestSpeedBase;
    public static double diamondAxeModuleHarvestSpeedOverclockMultiplier;

    // Netherite axe
    public static boolean netheriteAxeModuleIsAllowed;
    public static double netheriteAxeModuleEnergyConsumptionBase;
    public static double netheriteAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double netheriteAxeModuleHarvestSpeedBase;
    public static double netheriteAxeModuleHarvestSpeedOverclockMultiplier;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_AXE_MODULE_SPEC) {
            // Stone Axe
            stoneAxeModuleIsAllowed = STONE_AXE_MODULE__IS_ALLOWED.get();
            stoneAxeModuleEnergyConsumptionBase = STONE_AXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            stoneAxeModuleEnergyConsumptionOverclockMultiplier = STONE_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            stoneAxeModuleHarvestSpeedBase = STONE_AXE_MODULE__HARVEST_SPEED_BASE.get();
            stoneAxeModuleHarvestSpeedOverclockMultiplier = STONE_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Iron Axe
            ironAxeModuleIsAllowed = IRON_AXE_MODULE__IS_ALLOWED.get();
            ironAxeModuleEnergyConsumptionBase = IRON_AXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            ironAxeModuleEnergyConsumptionOverclockMultiplier = IRON_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            ironAxeModuleHarvestSpeedBase = IRON_AXE_MODULE__HARVEST_SPEED_BASE.get();
            ironAxeModuleHarvestSpeedOverclockMultiplier = IRON_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Diamond Axe
            diamondAxeModuleIsAllowed = DIAMOND_AXE_MODULE__IS_ALLOWED.get();
            diamondAxeModuleEnergyConsumptionBase = DIAMOND_AXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            diamondAxeModuleEnergyConsumptionOverclockMultiplier = DIAMOND_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            diamondAxeModuleHarvestSpeedBase = DIAMOND_AXE_MODULE__HARVEST_SPEED_BASE.get();
            diamondAxeModuleHarvestSpeedOverclockMultiplier = DIAMOND_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Netherite Axe
            netheriteAxeModuleIsAllowed = NETHERITE_AXE_MODULE__IS_ALLOWED.get();
            netheriteAxeModuleEnergyConsumptionBase = NETHERITE_AXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            netheriteAxeModuleEnergyConsumptionOverclockMultiplier = NETHERITE_AXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            netheriteAxeModuleHarvestSpeedBase = NETHERITE_AXE_MODULE__HARVEST_SPEED_BASE.get();
            netheriteAxeModuleHarvestSpeedOverclockMultiplier = NETHERITE_AXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();
        }
    }
}
