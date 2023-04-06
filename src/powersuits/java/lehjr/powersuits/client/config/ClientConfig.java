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

package lehjr.powersuits.client.config;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    /** HUD ---------------------------------------------------------------------------------------*/
    public ForgeConfigSpec.BooleanValue
            HUD_USE_GRAPHICAL_METERS,
            HUD_TOGGLE_MODULE_SPAM,
            HUD_DISPLAY_HUD,
            HUD_USE_24_HOUR_CLOCK,
            SHOW_METERS_WHEN_PAUSED;

    public ForgeConfigSpec.ConfigValue<String>
            HEAT_METER_GLASS_COLOR,
            HEAT_METER_BAR_COLOR,
            ENERGY_METER_GLASS_COLOR,
            ENERGY_METER_BAR_COLOR,
            PLASMA_METER_GLASS_COLOR,
            PLASMA_METER_BAR_COLOR,
            WATER_METER_GLASS_COLOR,
            WATER_METER_BAR_COLOR;

    public ForgeConfigSpec.DoubleValue
            HUD_KEYBIND_X,
            HUD_KEYBIND_Y,

            // Heat Meter -----------------------
            HEAT_METER_DEBUG_VAL,
            ENERGY_METER_DEBUG_VAL,
            PLASMA_METER_DEBUG_VAL,
            WATER_METER_DEBUG_VAL;


    /** General ----------------------------------------------------------------------------------- */
    public ForgeConfigSpec.BooleanValue
            GENERAL_ALLOW_CONFLICTING_KEYBINDS;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
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
                .defineInRange("keybindHUDy", 60.0, 0, Double.MAX_VALUE);

        HUD_USE_24_HOUR_CLOCK = builder
                .comment("Use a 24h clock instead of 12h")
                .translation(MPSConstants.CONFIG_HUD_USE_24_HOUR_CLOCK)
                .define("use24hClock", false);
        builder.pop();

        // Meter Settings --------------------------------------------------------------------------------------------
        builder.push("Meter Settings");// meter settings start
        SHOW_METERS_WHEN_PAUSED = builder
                .comment("Show meters when paused\n Useful for trying different colors in real time")
                .translation(NuminaConstants.CONFIG_USE_SOUNDS)
                .define("showMetersWhenPaused", false);

        // Heat Meter ------------------------------------------------------------------------------------------------
        builder.push("Heat Meter Settings");
        {
            HEAT_METER_DEBUG_VAL = builder
                    .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                    .defineInRange("heatMeterDebugValue", 0, 0, 100D);

            HEAT_METER_GLASS_COLOR = builder
                    .comment("Meter bar glass in RGB or RGBA hex")
                    .define("heatMeterGlassColor", "#FFFFFF");

            HEAT_METER_BAR_COLOR = builder
                    .comment("Meter bar color in RGB or RGBA hex")
                    .define("heatMeterBarColor", "#FFFFFF");
        }
        builder.pop();

        // Energy Meter ------------------------------------------------------------------------------------------------
        builder.push("Energy Meter Settings");

        ENERGY_METER_DEBUG_VAL = builder
                .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                .defineInRange("heatMeterDebugValue", 0, 0, 100D);


        ENERGY_METER_GLASS_COLOR = builder
                .comment("Meter bar glass in RGB or RGBA hex")
                .define("energyMeterGlassColor", "#FFFFFF");

        ENERGY_METER_BAR_COLOR = builder
                .comment("Meter bar color in RGB or RGBA hex")
                .define("energyMeterBarColor", "#FFFFFF");
        builder.pop();

        // Plasma Meter ------------------------------------------------------------------------------------------------
        builder.push("Plasma Meter Settings");
        PLASMA_METER_DEBUG_VAL = builder
                .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                .defineInRange("plasmaMeterDebugValue", 0, 0, 100D);

        PLASMA_METER_GLASS_COLOR = builder
                .comment("Meter bar glass in RGB or RGBA hex")
                .define("plasmaMeterGlassColor", "#FFFFFF");

        PLASMA_METER_BAR_COLOR = builder
                .comment("Meter bar color in RGB or RGBA hex")
                .define("plasmaMeterBarColor", "#FFFFFF");
        builder.pop();

        // Water Meter ------------------------------------------------------------------------------------------------
        builder.push("Water Meter Settings");
        WATER_METER_DEBUG_VAL = builder
                .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                .defineInRange("waterMeterDebugValue", 0, 0, 100D);

        WATER_METER_GLASS_COLOR = builder
                .comment("Meter bar glass in RGB or RGBA hex")
                .define("waterMeterGlassColor", "#FFFFFF");

        WATER_METER_BAR_COLOR = builder
                .comment("Meter bar color in RGB or RGBA hex")
                .define("waterMeterBarColor", "#FFFFFF");
        builder.pop();

        builder.pop(); // Meter settings end
    }
}
