package com.github.lehjr.numina.config;

import com.github.lehjr.numina.constants.NuminaConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
        public ForgeConfigSpec.IntValue ARMOR_STAND_MAX_POWER;
        protected ForgeConfigSpec.BooleanValue RECIPES_USE_VANILLA;


        public ServerConfig(ForgeConfigSpec.Builder builder) {
                builder.comment("General settings").push("General");
                {
                        ARMOR_STAND_MAX_POWER = builder
                                .comment("Ignore speed boosts for field of view")
                                .translation(NuminaConstants.CONFIG_USE_FOV_FIX)
                                .defineInRange("armorStandMaxPower", 1000000, 100, 10000000);

                        /** Recipes ----------------------------------------------------------------------------------------------- */
                        builder.comment("Recipe settings").push("Recipes");
                        RECIPES_USE_VANILLA = builder
                                .comment("Use recipes for Vanilla")
                                .translation(NuminaConstants.CONFIG_RECIPES_USE_VANILLA)
                                .worldRestart()
                                .define("useVanillaRecipes", true);
                        builder.pop();
                }
                builder.pop();

                builder.push("Modules");
                builder.push("Energy Storage");
                {
                        builder.push("battery_basic");
                        builder.defineInRange("base_maxEnergy", 1000000.0D, 0, 1.7976931348623157E308);
                        builder.define("isAllowed", true);
                        builder.defineInRange("base_maxTransfer", 1000000.0D, 0, 1.7976931348623157E308);
                        builder.pop();
                }
                {
                        builder.push("battery_advanced");
                        builder.defineInRange("base_maxEnergy", 5000000.0D, 0, 1.7976931348623157E308);
                        builder.define("isAllowed", true);
                        builder.defineInRange("base_maxTransfer", 5000000.0D, 0, 1.7976931348623157E308);
                        builder.pop();
                }
                {
                        builder.push("battery_elite");
                        builder.defineInRange("base_maxEnergy", 5.0E7D, 0, 1.7976931348623157E308);
                        builder.define("isAllowed", true);
                        builder.defineInRange("base_maxTransfer", 5.0E7D, 0, 1.7976931348623157E308);
                        builder.pop();
                }
                {
                        builder.push("battery_ultimate");
                        builder.defineInRange("base_maxEnergy", 1.0E8D, 0, 1.7976931348623157E308);
                        builder.define("isAllowed", true);
                        builder.defineInRange("base_maxTransfer", 1.0E8D, 0, 1.7976931348623157E308);
                        builder.pop();
                }
                builder.pop();
                builder.pop();
        }
}