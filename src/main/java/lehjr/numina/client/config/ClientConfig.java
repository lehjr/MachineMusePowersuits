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

package lehjr.numina.client.config;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public ForgeConfigSpec.BooleanValue
            USE_FOV_FIX,
            USE_FOV_NORMALIZE,
            FOV_FIX_DEAULT_STATE,
            USE_SOUNDS,
            DEBUGGING_INFO,
            DRAW_GUI_SPACERS,
            SHOW_METERS_WHEN_PAUSED;

    public ForgeConfigSpec.DoubleValue
                        GLASS_RED,
                        GLASS_GREEN,
                        GLASS_BLUE,
                        GLASS_ALPHA,

                        HEAT_METER_RED,
                        HEAT_METER_GREEN,
                        HEAT_METER_BLUE,
                        HEAT_METER_ALPHA,

                        ENERGY_METER_RED,
                        ENERGY_METER_GREEN,
                        ENERGY_METER_BLUE,
                        ENERGY_METER_ALPHA,

                        PLASMA_METER_RED,
                        PLASMA_METER_GREEN,
                        PLASMA_METER_BLUE,
                        PLASMA_METER_ALPHA,

                        WATER_METER_RED,
                        WATER_METER_GREEN,
                        WATER_METER_BLUE,
                        WATER_METER_ALPHA;


    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General Settings").push("General");

        USE_FOV_FIX = builder
                .comment("Ignore speed boosts for field of view")
                .translation(NuminaConstants.CONFIG_USE_FOV_FIX)
                .define("useFOVFix", true);

        USE_FOV_NORMALIZE = builder
                .comment("Use FOV Fix to normalize FOV changes")
                .translation(NuminaConstants.CONFIG_USE_FOV_NORMALIZE)
                .define("useFOVNormalize", true);

        FOV_FIX_DEAULT_STATE = builder
                .comment("Default state of FOVfix on login (enabled = true, disabled = false)")
                .translation(NuminaConstants.CONFIG_FOV_FIX_DEAULT_STATE)
                .define("fovFixDefaultState", true);

        USE_SOUNDS = builder
                .comment("Use sounds")
                .translation(NuminaConstants.CONFIG_USE_SOUNDS)
                .define("useSounds", true);
        builder.pop();

        builder.push("Meter Settings");
        SHOW_METERS_WHEN_PAUSED = builder
                .comment("Show meters when paused\n Useful for trying different colors in real time")
                .translation(NuminaConstants.CONFIG_USE_SOUNDS)
                .define("showMetersWhenPaused", false);

        // Meter Glass ------------------------------------------------------------------------------------------------
        GLASS_RED = builder
                .comment("glass red")
                .defineInRange("meterGlassRed", 1.0, 0, 1.0);

        GLASS_GREEN = builder
                .comment("glass green")
                .defineInRange("meterGlassGreen", 0.3, 0, 1.0);

        GLASS_BLUE = builder
                .comment("glass blue")
                .defineInRange("meterGlassBlue", 0.2, 0, 1.0);

        GLASS_ALPHA = builder
                .comment("glass alpha")
                .defineInRange("meterGlassAlpha", 0.75, 0, 1.0);

        // Heat Meter ------------------------------------------------------------------------------------------------
        builder.push("Heat Meter Settings");
        HEAT_METER_RED = builder
                .comment("heat meter red")
                .defineInRange("heatMeterRed", 1.0, 0, 1.0);

        HEAT_METER_GREEN = builder
                .comment("heat meter green")
                .defineInRange("heatMeterGreen", 0.3, 0, 1.0);

        HEAT_METER_BLUE = builder
                .comment("heat meter blue")
                .defineInRange("heatMeterBlue", 0.5, 0, 1.0);

        HEAT_METER_ALPHA = builder
                .comment("heat meter alpha")
                .defineInRange("heatMeterAlpha", 0.75, 0, 1.0);
        builder.pop();

        // Energy Meter ------------------------------------------------------------------------------------------------
        builder.push("Energy Meter Settings");
        ENERGY_METER_RED = builder
                .comment("energy meter red")
                .defineInRange("energyMeterRed", 0.2, 0, 1.0);

        ENERGY_METER_GREEN = builder
                .comment("energy meter green")
                .defineInRange("energyMeterGreen", 1.0, 0, 1.0);

        ENERGY_METER_BLUE = builder
                .comment("energy meter blue")
                .defineInRange("energyMeterBlue", 0.5, 0, 1.0);

        ENERGY_METER_ALPHA = builder
                .comment("energy meter alpha")
                .defineInRange("energyMeterAlpha", 0.75, 0, 1.0);
        builder.pop();

        // Plasma Meter ------------------------------------------------------------------------------------------------
        builder.push("Plasma Meter Settings");
        PLASMA_METER_RED = builder
                .comment("plasma meter red")
                .defineInRange("plasmaMeterRed", 1.0, 0, 1.0);

        PLASMA_METER_GREEN = builder
                .comment("plasma meter green")
                .defineInRange("plasmaMeterGreen", 0.2, 0, 1.0);

        PLASMA_METER_BLUE = builder
                .comment("plasma meter blue")
                .defineInRange("plasmaMeterBlue", 1.0, 0, 1.0);

        PLASMA_METER_ALPHA = builder
                .comment("plasma meter alpha")
                .defineInRange("plasmaMeterAlpha", 0.75, 0, 1.0);
        builder.pop();

        // Water Meter ------------------------------------------------------------------------------------------------
        builder.push("Water Meter Settings");
        WATER_METER_RED = builder
                .comment("water meter red")
                .defineInRange("waterMeterRed", 0.5, 0, 1.0);

        WATER_METER_GREEN = builder
                .comment("water meter green")
                .defineInRange("waterMeterGreen", 0.50, 0, 1.0);

        WATER_METER_BLUE = builder
                .comment("water meter blue")
                .defineInRange("waterMeterBlue", 1.0, 0, 1.0);

        WATER_METER_ALPHA = builder
                .comment("water meter alpha")
                .defineInRange("waterMeterAlpha", 0.75, 0, 1.0);
        builder.pop();

        builder.pop(); // Meter settings end

        builder.push("Development Settings");
        DEBUGGING_INFO = builder
                .comment("Enable debugging info")
                .translation(NuminaConstants.CONFIG_DEBUGGING_INFO)
                .define("enableDebugging", false);

        builder.pop(); // Dev settings end
    }
}
