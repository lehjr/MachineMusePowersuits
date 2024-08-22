package com.lehjr.powersuits.common.config;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ToolModuleConfig {
    // Block Breaking =================================================================================================
    // Axes -----------------------------------------------------------------------------------------------------------
    // Stone Axe --------------------------------------------------------------
    private static final ModConfigSpec.Builder STONE_AXE_MODULE_BUILDER = new ModConfigSpec.Builder().push("Tool_Modules").push("Block_Breaking").push("Axes").push("Stone_Axe_Module");
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

    // Pickaxe Modules ------------------------------------------------------------------------------------------------
    // Stone Pickaxe ----------------------------------------------------------
    private static final ModConfigSpec.Builder STONE_PICKAXE_MODULE_BUILDER = NETHERITE_AXE_MODULE_BUILDER.pop().pop().push("PickAxes").push("Stone_PickAxe_Module");
    private static final ModConfigSpec.BooleanValue STONE_PICKAXE_MODULE__IS_ALLOWED = STONE_PICKAXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE = STONE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = STONE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__HARVEST_SPEED_BASE = STONE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = STONE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 3, 0, 100.0D);

    // Iron Pickaxe -----------------------------------------------------------
    private static final ModConfigSpec.Builder IRON_PICKAXE_MODULE_BUILDER = STONE_PICKAXE_MODULE_BUILDER.pop().push("Iron_PickAxe_Module");
    private static final ModConfigSpec.BooleanValue IRON_PICKAXE_MODULE__IS_ALLOWED = IRON_PICKAXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE = IRON_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = IRON_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__HARVEST_SPEED_BASE = IRON_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = IRON_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Diamond Pickaxe --------------------------------------------------------
    private static final ModConfigSpec.Builder DIAMOND_PICKAXE_MODULE_BUILDER = IRON_PICKAXE_MODULE_BUILDER.pop().push("Diamond_Pickaxe_Module");
    private static final ModConfigSpec.BooleanValue DIAMOND_PICKAXE_MODULE__IS_ALLOWED = DIAMOND_PICKAXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE = DIAMOND_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = DIAMOND_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_BASE = DIAMOND_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = DIAMOND_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Netherite Pickaxe ------------------------------------------------------
    private static final ModConfigSpec.Builder NETHERITE_PICKAXE_MODULE_BUILDER = DIAMOND_PICKAXE_MODULE_BUILDER.pop().push("Netherite Pickaxe");
    private static final ModConfigSpec.BooleanValue NETHERITE_PICKAXE_MODULE__IS_ALLOWED = NETHERITE_PICKAXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE = NETHERITE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = NETHERITE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_BASE = NETHERITE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = NETHERITE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    //  Shovels -------------------------------------------------------------------------------------------------------
    // Stone Shovel -----------------------------------------------------------
    private static final ModConfigSpec.Builder STONE_SHOVEL_MODULE_BUILDER = NETHERITE_PICKAXE_MODULE_BUILDER.pop().pop().push("Shovels").push("Stone_Shovel_Module");
    private static final ModConfigSpec.BooleanValue STONE_SHOVEL_MODULE__IS_ALLOWED = STONE_SHOVEL_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue STONE_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE = STONE_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = STONE_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_SHOVEL_MODULE__HARVEST_SPEED_BASE = STONE_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue STONE_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = STONE_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 3, 0, 100.0D);

    // Iron -------------------------------------------------------------------
    private static final ModConfigSpec.Builder IRON_SHOVEL_MODULE_BUILDER = STONE_SHOVEL_MODULE_BUILDER.pop().push("Iron_Shovel_Module");
    private static final ModConfigSpec.BooleanValue IRON_SHOVEL_MODULE__IS_ALLOWED = IRON_SHOVEL_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue IRON_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE = IRON_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = IRON_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_SHOVEL_MODULE__HARVEST_SPEED_BASE = IRON_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue IRON_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = IRON_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Diamond Shovel ---------------------------------------------------------
    private static final ModConfigSpec.Builder DIAMOND_SHOVEL_MODULE_BUILDER = IRON_SHOVEL_MODULE_BUILDER.pop().push("Diamond_Shovel_Module");
    private static final ModConfigSpec.BooleanValue DIAMOND_SHOVEL_MODULE__IS_ALLOWED = DIAMOND_SHOVEL_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue DIAMOND_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE = DIAMOND_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = DIAMOND_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_SHOVEL_MODULE__HARVEST_SPEED_BASE = DIAMOND_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = DIAMOND_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Netherite Shovel -------------------------------------------------------
    private static final ModConfigSpec.Builder NETHERITE_SHOVEL_MODULE_BUILDER = DIAMOND_SHOVEL_MODULE_BUILDER.pop().push("Netherite_Shovel_Module");
    private static final ModConfigSpec.BooleanValue NETHERITE_SHOVEL_MODULE__IS_ALLOWED = NETHERITE_SHOVEL_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue NETHERITE_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE = NETHERITE_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = NETHERITE_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_SHOVEL_MODULE__HARVEST_SPEED_BASE = NETHERITE_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 6, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = NETHERITE_SHOVEL_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 40, 0, 100.0D);

    // Miscellaneous Modules ------------------------------------------------------------------------------------------
    // Flint and Steel -------------------------------------------------------
    private static final ModConfigSpec.Builder FLINT_AND_STEEL_MODULE__SETTINGS_BUILDER = NETHERITE_SHOVEL_MODULE_BUILDER.pop().pop().push("Miscellaneous").push("Flint_And_Steel_Module");
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

    //    // TOOL -------------------------------------------------------------------
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

    // Stone Pickaxe
    public static boolean stonePickAxeModuleIsAllowed;
    public static double stonePickAxeModuleEnergyConsumptionBase;
    public static double stonePickAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double stonePickAxeModuleHarvestSpeedBase;
    public static double stonePickAxeModuleHarvestSpeedOverclockMultiplier;

    // Iron Pickaxe
    public static boolean ironPickAxeModuleIsAllowed;
    public static double ironPickAxeModuleEnergyConsumptionBase;
    public static double ironPickAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double ironPickAxeModuleHarvestSpeedBase;
    public static double ironPickAxeModuleHarvestSpeedOverclockMultiplier;

    // Diamond Pickaxe
    public static boolean diamondPickAxeModuleIsAllowed;
    public static double diamondPickAxeModuleEnergyConsumptionBase;
    public static double diamondPickAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double diamondPickAxeModuleHarvestSpeedBase;
    public static double diamondPickAxeModuleHarvestSpeedOverclockMultiplier;

    // Netherite Pickaxe
    public static boolean netheritePickAxeModuleIsAllowed;
    public static double netheritePickAxeModuleEnergyConsumptionBase;
    public static double netheritePickAxeModuleEnergyConsumptionOverclockMultiplier;
    public static double netheritePickAxeModuleHarvestSpeedBase;
    public static double netheritePickAxeModuleHarvestSpeedOverclockMultiplier;

    // Stone Shovel
    public static boolean stoneShovelModuleIsAllowed;
    public static double stoneShovelModuleEnergyConsumptionBase;
    public static double stoneShovelModuleEnergyConsumptionOverclockMultiplier;
    public static double stoneShovelModuleHarvestSpeedBase;
    public static double stoneShovelModuleHarvestSpeedOverclockMultiplier;

    // Iron Shovel
    public static boolean ironShovelModuleIsAllowed;
    public static double ironShovelModuleEnergyConsumptionBase;
    public static double ironShovelModuleEnergyConsumptionOverclockMultiplier;
    public static double ironShovelModuleHarvestSpeedBase;
    public static double ironShovelModuleHarvestSpeedOverclockMultiplier;

    // Diamond Shovel
    public static boolean diamondShovelModuleIsAllowed;
    public static double diamondShovelModuleEnergyConsumptionBase;
    public static double diamondShovelModuleEnergyConsumptionOverclockMultiplier;
    public static double diamondShovelModuleHarvestSpeedBase;
    public static double diamondShovelModuleHarvestSpeedOverclockMultiplier;

    // Netherite Shovel
    public static boolean netheriteShovelModuleIsAllowed;
    public static double netheriteShovelModuleEnergyConsumptionBase;
    public static double netheriteShovelModuleEnergyConsumptionOverclockMultiplier;
    public static double netheriteShovelModuleHarvestSpeedBase;
    public static double netheriteShovelModuleHarvestSpeedOverclockMultiplier;

    // Flint and Steel
    public static boolean flintAndSteelModuleIsAllowed;
    public static double flintAndSteelEnergyConsumption;

    // Leaf Blower
    public static boolean leafBlowerModuleIsAllowed;

    // Lux Capacitor
    public static boolean luxCapacitorModuleIsAllowed;
    public static double luxCapacitorEnergyConsumptionBase;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_TOOL_MODULE_SPEC) {
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

            // Stone Pickaxe
            stonePickAxeModuleIsAllowed = STONE_PICKAXE_MODULE__IS_ALLOWED.get();
            stonePickAxeModuleEnergyConsumptionBase = STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            stonePickAxeModuleEnergyConsumptionOverclockMultiplier = STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            stonePickAxeModuleHarvestSpeedBase = STONE_PICKAXE_MODULE__HARVEST_SPEED_BASE.get();
            stonePickAxeModuleHarvestSpeedOverclockMultiplier = STONE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Iron Pickaxe
            ironPickAxeModuleIsAllowed = IRON_PICKAXE_MODULE__IS_ALLOWED.get();
            ironPickAxeModuleEnergyConsumptionBase = IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            ironPickAxeModuleEnergyConsumptionOverclockMultiplier = IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            ironPickAxeModuleHarvestSpeedBase = IRON_PICKAXE_MODULE__HARVEST_SPEED_BASE.get();
            ironPickAxeModuleHarvestSpeedOverclockMultiplier = IRON_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Diamond Pickaxe
            diamondPickAxeModuleIsAllowed = DIAMOND_PICKAXE_MODULE__IS_ALLOWED.get();
            diamondPickAxeModuleEnergyConsumptionBase = DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            diamondPickAxeModuleEnergyConsumptionOverclockMultiplier = DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            diamondPickAxeModuleHarvestSpeedBase = DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_BASE.get();
            diamondPickAxeModuleHarvestSpeedOverclockMultiplier = DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Netherite Pickaxe
            netheritePickAxeModuleIsAllowed = NETHERITE_PICKAXE_MODULE__IS_ALLOWED.get();
            netheritePickAxeModuleEnergyConsumptionBase = NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE.get();
            netheritePickAxeModuleEnergyConsumptionOverclockMultiplier = NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            netheritePickAxeModuleHarvestSpeedBase = NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_BASE.get();
            netheritePickAxeModuleHarvestSpeedOverclockMultiplier = NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Stone Shovel
            stoneShovelModuleIsAllowed = STONE_SHOVEL_MODULE__IS_ALLOWED.get();
            stoneShovelModuleEnergyConsumptionBase = STONE_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE.get();
            stoneShovelModuleEnergyConsumptionOverclockMultiplier = STONE_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            stoneShovelModuleHarvestSpeedBase = STONE_SHOVEL_MODULE__HARVEST_SPEED_BASE.get();
            stoneShovelModuleHarvestSpeedOverclockMultiplier = STONE_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Iron Shovel
            ironShovelModuleIsAllowed = IRON_SHOVEL_MODULE__IS_ALLOWED.get();
            ironShovelModuleEnergyConsumptionBase = IRON_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE.get();
            ironShovelModuleEnergyConsumptionOverclockMultiplier = IRON_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            ironShovelModuleHarvestSpeedBase = IRON_SHOVEL_MODULE__HARVEST_SPEED_BASE.get();
            ironShovelModuleHarvestSpeedOverclockMultiplier = IRON_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Diamond Shovel
            diamondShovelModuleIsAllowed = DIAMOND_SHOVEL_MODULE__IS_ALLOWED.get();
            diamondShovelModuleEnergyConsumptionBase = DIAMOND_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE.get();
            diamondShovelModuleEnergyConsumptionOverclockMultiplier = DIAMOND_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            diamondShovelModuleHarvestSpeedBase = DIAMOND_SHOVEL_MODULE__HARVEST_SPEED_BASE.get();
            diamondShovelModuleHarvestSpeedOverclockMultiplier = DIAMOND_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

            // Netherite Shovel
            netheriteShovelModuleIsAllowed = NETHERITE_SHOVEL_MODULE__IS_ALLOWED.get();
            netheriteShovelModuleEnergyConsumptionBase = NETHERITE_SHOVEL_MODULE__ENERGY_CONSUMPTION_BASE.get();
            netheriteShovelModuleEnergyConsumptionOverclockMultiplier = NETHERITE_SHOVEL_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER.get();
            netheriteShovelModuleHarvestSpeedBase = NETHERITE_SHOVEL_MODULE__HARVEST_SPEED_BASE.get();
            netheriteShovelModuleHarvestSpeedOverclockMultiplier = NETHERITE_SHOVEL_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER.get();

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
