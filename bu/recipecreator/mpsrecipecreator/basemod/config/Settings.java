package com.lehjr.mpsrecipecreator.basemod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Settings {
    public static ForgeConfigSpec.BooleanValue
        OVERWRITE_RECIPES,
        OPPED_PLAYERS_CAN_CREATE_ON_SERVER;

    public static ForgeConfigSpec.IntValue
        OP_LEVEL_REQUIRED;

    public Settings(ForgeConfigSpec.Builder builder) {
        builder.comment("General server side settings").push("General");
        OVERWRITE_RECIPES = builder
                .comment("overwrite already created recipes.\n" +
                        "Set true to be able to overwrite a recipe when you make a mistake")
                .define("overwrite_recipes", true);

        OPPED_PLAYERS_CAN_CREATE_ON_SERVER = builder
                .comment("allow opped players to create recipes in multiplayer environments."+
                        "\nThis is good for team collaboration, but should only be used with trusted players")
                .define("oppedPlayersCanCreateRecipesOnServer", false);

        OP_LEVEL_REQUIRED = builder
                .comment("Permission level required for OP to create recipes on the server")
                .defineInRange("opLevelRequired", 4, 2, 4);
    }
}