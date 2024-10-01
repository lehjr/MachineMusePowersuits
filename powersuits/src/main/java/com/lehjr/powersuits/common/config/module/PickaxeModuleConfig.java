package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class PickaxeModuleConfig {
    // Stone Pickaxe ----------------------------------------------------------
    private static final ModConfigSpec.Builder STONE_PICKAXE_MODULE_BUILDER = new ModConfigSpec.Builder().push("PickAxes").push("Stone_PickAxe_Module");
    private static final ModConfigSpec.BooleanValue STONE_PICKAXE_MODULE__IS_ALLOWED = STONE_PICKAXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE = STONE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = STONE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__HARVEST_SPEED_BASE = STONE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue STONE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = STONE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 6, 0, 100.0D);

    // Iron Pickaxe -----------------------------------------------------------
    private static final ModConfigSpec.Builder IRON_PICKAXE_MODULE_BUILDER = STONE_PICKAXE_MODULE_BUILDER.pop().push("Iron_PickAxe_Module");
    private static final ModConfigSpec.BooleanValue IRON_PICKAXE_MODULE__IS_ALLOWED = IRON_PICKAXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE = IRON_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = IRON_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__HARVEST_SPEED_BASE = IRON_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue IRON_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = IRON_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 6, 0, 100.0D);

    // Diamond Pickaxe --------------------------------------------------------
    private static final ModConfigSpec.Builder DIAMOND_PICKAXE_MODULE_BUILDER = IRON_PICKAXE_MODULE_BUILDER.pop().push("Diamond_Pickaxe_Module");
    private static final ModConfigSpec.BooleanValue DIAMOND_PICKAXE_MODULE__IS_ALLOWED = DIAMOND_PICKAXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE = DIAMOND_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = DIAMOND_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_BASE = DIAMOND_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue DIAMOND_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = DIAMOND_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 9, 0, 100.0D);

    // Netherite Pickaxe ------------------------------------------------------
    private static final ModConfigSpec.Builder NETHERITE_PICKAXE_MODULE_BUILDER = DIAMOND_PICKAXE_MODULE_BUILDER.pop().push("Netherite Pickaxe");
    private static final ModConfigSpec.BooleanValue NETHERITE_PICKAXE_MODULE__IS_ALLOWED = NETHERITE_PICKAXE_MODULE_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_BASE = NETHERITE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 600, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__ENERGY_CONSUMPTION_OVERCLOCK_MULTIPLIER = NETHERITE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION__OVERCLOCK_MULTIPLIER, 1000, 0, 100000.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_BASE = NETHERITE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_BASE, 1, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue NETHERITE_PICKAXE_MODULE__HARVEST_SPEED_OVERCLOCK_MULTIPLIER = NETHERITE_PICKAXE_MODULE_BUILDER.defineInRange(MPSConstants.HARVEST_SPEED_OVERCLOCK_MULTIPLIER, 9, 0, 100.0D);

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

    public static final ModConfigSpec MPS_PICKAXE_MODULE_SPEC = NETHERITE_PICKAXE_MODULE_BUILDER.build();

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_PICKAXE_MODULE_SPEC) {
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
        }
    }
}
