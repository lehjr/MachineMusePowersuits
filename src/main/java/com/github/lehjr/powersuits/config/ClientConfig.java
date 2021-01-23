package com.github.lehjr.powersuits.config;

import com.github.lehjr.powersuits.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    /** HUD ---------------------------------------------------------------------------------------*/
    protected ForgeConfigSpec.BooleanValue
            HUD_USE_GRAPHICAL_METERS,
            HUD_TOGGLE_MODULE_SPAM,
            HUD_DISPLAY_HUD,
            HUD_USE_24_HOUR_CLOCK;

    protected ForgeConfigSpec.DoubleValue
            HUD_KEYBIND_X,
            HUD_KEYBIND_Y;

    /** General ----------------------------------------------------------------------------------- */
    protected ForgeConfigSpec.BooleanValue
            GENERAL_ALLOW_CONFLICTING_KEYBINDS;

    protected ClientConfig(ForgeConfigSpec.Builder builder) {
        // HUD ------------------------------------------------------------------------------------
        builder.comment("HUD settings").push("HUD");
        HUD_USE_GRAPHICAL_METERS = builder
                .comment("Use Graphical Meters")
                .translation(MPSConstants.CONFIG_HUD_USE_GRAPHICAL_METERS)
                .define("useGraphicalMeters", true);

        HUD_TOGGLE_MODULE_SPAM = builder
                .comment("Chat message when toggling module")
                .translation(MPSConstants.CONFIG_HUD_TOGGLE_MODULE_SPAM)
                .define("toggleModuleSpam", false);

        HUD_DISPLAY_HUD = builder
                .comment("Display HUD")
                .translation(MPSConstants.CONFIG_HUD_DISPLAY_HUD)
                .define("keybind_HUD_on", true);

        HUD_KEYBIND_X = builder
                .comment("x position")
                .translation(MPSConstants.CONFIG_HUD_KEYBIND_HUD_X)
                .defineInRange("keybindHUDx", 8.0, 0, Double.MAX_VALUE);

        HUD_KEYBIND_Y = builder
                .comment("x position")
                .translation(MPSConstants.CONFIG_HUD_KEYBIND_HUD_Y)
                .defineInRange("keybindHUDy", 32.0, 0, Double.MAX_VALUE);

        HUD_USE_24_HOUR_CLOCK = builder
                .comment("Use a 24h clock instead of 12h")
                .translation(MPSConstants.CONFIG_HUD_USE_24_HOUR_CLOCK)
                .define("use24hClock", false);
        builder.pop();

        builder.comment("General settings").push("General");
        GENERAL_ALLOW_CONFLICTING_KEYBINDS = builder
                .comment("Allow Conflicting Keybinds")
                .translation(MPSConstants.CONFIG_GENERAL_ALLOW_CONFLICTING_KEYBINDS)
                .define("allowConflictingKeybinds", true);
    }
}
