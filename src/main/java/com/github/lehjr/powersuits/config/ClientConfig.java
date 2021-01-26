/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
