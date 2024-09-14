package com.lehjr.powersuits.common.config.module;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ShovelModuleConfig {
    // Stone Shovel -----------------------------------------------------------
    private static final ModConfigSpec.Builder STONE_SHOVEL_MODULE_BUILDER = new ModConfigSpec.Builder().push("Shovels").push("Stone_Shovel_Module");
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
    public static final ModConfigSpec MPS_SHOVEL_MODULE_SPEC = NETHERITE_SHOVEL_MODULE_BUILDER.build();

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

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_SHOVEL_MODULE_SPEC) {
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
        }
    }
}
