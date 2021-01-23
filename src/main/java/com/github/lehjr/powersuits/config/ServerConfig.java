package com.github.lehjr.powersuits.config;

import com.github.lehjr.powersuits.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class ServerConfig {
    /** General ------------------------------------------------------------------------------------------------------- */
    protected ForgeConfigSpec.DoubleValue
            GENERAL_MAX_FLYING_SPEED,
            GENERAL_BASE_MAX_HEAT_POWERFIST,
            GENERAL_BASE_MAX_HEAT_HELMET,
            GENERAL_BASE_MAX_HEAT_CHEST,
            GENERAL_BASE_MAX_HEAT_LEGS,
            GENERAL_BASE_MAX_HEAT_FEET;

    protected ForgeConfigSpec.ConfigValue<List<? extends String>> GENERAL_VEIN_MINER_ORE_LIST;
    protected ForgeConfigSpec.ConfigValue<List<?>> GENERAL_VEIN_MINER_BLOCK_LIST;

    /** Cosmetics ----------------------------------------------------------------------------------------------------- */
// Note: these are controlled by the server because the legacy settings can create a vast number
//      of NBT Tags for tracking the settings for each individual model part.
    protected ForgeConfigSpec.BooleanValue
            COSMETIC_USE_LEGACY_COSMETIC_SYSTEM,
            COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS,
            COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN;

    /** Recipes ------------------------------------------------------------------------------------------------------- */
    protected ForgeConfigSpec.BooleanValue RECIPES_USE_VANILLA;



    protected ServerConfig(ForgeConfigSpec.Builder builder) {
        /** General --------------------------------------------------------------------------------------------------- */
        builder.comment("General settings").push("General");
        GENERAL_MAX_FLYING_SPEED = builder.comment("Maximum flight speed (in m/s)")
                .translation(MPSConstants.CONFIG_GENERAL_MAX_FLYING_SPEED)
                .defineInRange("maximumFlyingSpeedmps", 25.0, 0, Float.MAX_VALUE);

        GENERAL_BASE_MAX_HEAT_POWERFIST = builder.comment("PowerFistModel2 Base Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_POWERFIST)
                .defineInRange("baseMaxHeatPowerFist", 5.0, 0, 5000);

        GENERAL_BASE_MAX_HEAT_HELMET = builder.comment("Power Armor Helmet Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_HELMET)
                .defineInRange("baseMaxHeatHelmet", 5.0, 0, 5000);

        GENERAL_BASE_MAX_HEAT_CHEST = builder.comment("Power Armor Chestplate Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_CHESTPLATE)
                .defineInRange("baseMaxHeatChest", 20.0, 0, 5000);

        GENERAL_BASE_MAX_HEAT_LEGS = builder.comment("Power Armor Leggings Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_LEGGINGS)
                .defineInRange("baseMaxHeatLegs", 15.0, 0, 5000);

        GENERAL_BASE_MAX_HEAT_FEET = builder.comment("Power Armor Boots Heat Cap")
                .translation(MPSConstants.CONFIG_GENERAL_BASE_MAX_HEAT_FEET)
                .defineInRange("baseMaxHeatFeet", 5.0, 0, 5000);

        GENERAL_VEIN_MINER_ORE_LIST = builder
                .comment("Ore tag list for vein miner module.")
                .translation(MPSConstants.CONFIG_GENERAL_VEIN_MINER_ORE_LIST)
                .worldRestart()
                .defineList("veinMinerOres", Arrays.asList(
                        // metals
                        "forge:ores/iron",
                        "forge:ores/copper",
                        "forge:ores/tin",
                        "forge:ores/lead",
                        "forge:ores/aluminum",
                        "forge:ores/aluminium",
                        "forge:ores/silver",
                        "forge:ores/gold",
                        "forge:ores/cinnabar",
                        "forge:ores/zinc",
                        "forge:ores/uranium",
                        "forge:ores/platinum",
                        "forge:ores/bismuth",
                        "forge:ores/osmium",

                        // non-metal
                        "forge:ores/coal",
                        "forge:ores/redstone",
                        "minecraft:glowstone",
                        "forge:ores/diamond",
                        "forge:ores/lapis",
                        "forge:ores/quartz"
                ), o -> o instanceof String && !((String) o).isEmpty());

        GENERAL_VEIN_MINER_BLOCK_LIST = builder
                .comment("Block registry name whitelist for the vein miner module. \n" +
                        "Use for blocks that don't have an ore tag or to fine tune which blocks to break")
                .translation(MPSConstants.CONFIG_GENERAL_VEIN_MINER_BLOCK_LIST)
                .worldRestart()
                .defineList("veinMinerBlocks", Arrays.asList(), o -> o instanceof String && !((String) o).isEmpty());
        builder.pop();


        /** Cosmetics ------------------------------------------------------------------------------------------------- */
        builder.comment("Model cosmetic settings").push("Cosmetic");

        COSMETIC_USE_LEGACY_COSMETIC_SYSTEM = builder
                .comment("Use legacy cosmetic configuration instead of cosmetic presets")
                .translation(MPSConstants.CONFIG_COSMETIC_USE_LEGACY_COSMETIC_SYSTEM)
                .worldRestart()
                .define("useLegacyCosmeticSystem", true);

        COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS = builder
                .comment("Allow high polly armor models instead of just skins")
                .translation(MPSConstants.CONFIG_COSMETIC_ALLOW_HIGH_POLLY_ARMOR_MODELS)
                .define("allowHighPollyArmorModuels", true);

        COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN = builder
                .comment("Allow PowerFistModel2 model to be customized")
                .translation(MPSConstants.CONFIG_COSMETIC_ALLOW_POWER_FIST_CUSTOMIZATOIN)
                .define("allowPowerFistCustomization", true);
        builder.pop();

        /** Recipes --------------------------------------------------------------------------------------------------- */
        builder.comment("Recipe settings").push("Recipes");
        RECIPES_USE_VANILLA = builder
                .comment("Use recipes for Vanilla")
                .translation(MPSConstants.CONFIG_RECIPES_USE_VANILLA)
                .worldRestart()
                .define("useVanillaRecipes", true);
        builder.pop();

        /** Modules --------------------------------------------------------------------------------------------------- */


    }
}
