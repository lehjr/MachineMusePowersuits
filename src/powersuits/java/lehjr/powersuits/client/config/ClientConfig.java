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

    public ForgeConfigSpec.IntValue
            // Heat Meter
            HUD_HEAT_METER_GLASS_RED,
            HUD_HEAT_METER_GLASS_GREEN,
            HUD_HEAT_METER_GLASS_BLUE,
            HUD_HEAT_METER_GLASS_ALPHA,
            HUD_HEAT_METER_BAR_RED,
            HUD_HEAT_METER_BAR_GREEN,
            HUD_HEAT_METER_BAR_BLUE,
            HUD_HEAT_METER_BAR_ALPHA,

            // Energy Meter
            HUD_ENERGY_METER_GLASS_RED,
            HUD_ENERGY_METER_GLASS_GREEN,
            HUD_ENERGY_METER_GLASS_BLUE,
            HUD_ENERGY_METER_GLASS_ALPHA,
            HUD_ENERGY_METER_BAR_RED,
            HUD_ENERGY_METER_BAR_GREEN,
            HUD_ENERGY_METER_BAR_BLUE,
            HUD_ENERGY_METER_BAR_ALPHA,

            // Weapon Charge Meter
            HUD_WEAPON_CHARGE_METER_GLASS_RED,
            HUD_WEAPON_CHARGE_METER_GLASS_GREEN,
            HUD_WEAPON_CHARGE_METER_GLASS_BLUE,
            HUD_WEAPON_CHARGE_METER_GLASS_ALPHA,
            HUD_WEAPON_CHARGE_METER_BAR_RED,
            HUD_WEAPON_CHARGE_METER_BAR_GREEN,
            HUD_WEAPON_CHARGE_METER_BAR_BLUE,
            HUD_WEAPON_CHARGE_METER_BAR_ALPHA,

            // Water Meter
            HUD_WATER_METER_GLASS_RED,
            HUD_WATER_METER_GLASS_GREEN,
            HUD_WATER_METER_GLASS_BLUE,
            HUD_WATER_METER_GLASS_ALPHA,
            HUD_WATER_METER_BAR_RED,
            HUD_WATER_METER_BAR_GREEN,
            HUD_WATER_METER_BAR_BLUE,
            HUD_WATER_METER_BAR_ALPHA;

    public ForgeConfigSpec.DoubleValue
            HUD_KEYBIND_X,
            HUD_KEYBIND_Y,

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

            HUD_HEAT_METER_GLASS_RED = builder
                    .comment("hud heat meter glass red color amount (0 - 100)")
                    .defineInRange("heatMeterGlassRedPercent", 100, 0, 100);

            HUD_HEAT_METER_GLASS_GREEN = builder
                    .comment("hud heat meter glass green color amount (0 - 100)")
                    .defineInRange("heatMeterGlassGreenPercent", 100, 0, 100);

            HUD_HEAT_METER_GLASS_BLUE = builder
                    .comment("hud heat meter glass blue color amount (0 - 100)")
                    .defineInRange("heatMeterGlassBluePercent", 100, 0, 100);

            HUD_HEAT_METER_GLASS_ALPHA = builder
                    .comment("hud heat meter glass alpha color amount (0 - 100)")
                    .defineInRange("heatMeterGlassAlphaPercent", 85, 0, 100);


            HUD_HEAT_METER_BAR_RED = builder
                    .comment("hud heat meter bar red color amount (0 - 100)")
                    .defineInRange("heatMeterBarRedPercent", 100, 0, 100);

            HUD_HEAT_METER_BAR_GREEN = builder
                    .comment("hud heat meter bar green color amount (0 - 100)")
                    .defineInRange("heatMeterBarGreenPercent", 100, 0, 100);

            HUD_HEAT_METER_BAR_BLUE = builder
                    .comment("hud heat meter bar blue color amount (0 - 100)")
                    .defineInRange("heatMeterBarBluePercent", 100, 0, 100);

            HUD_HEAT_METER_BAR_ALPHA = builder
                    .comment("hud heat meter bar alpha color amount (0 - 100)")
                    .defineInRange("heatMeterBarAlphaPercent", 85, 0, 100);
        }
        builder.pop();

        // Energy Meter ------------------------------------------------------------------------------------------------
        builder.push("Energy Meter Settings");
        {
            ENERGY_METER_DEBUG_VAL = builder
                    .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                    .defineInRange("energyMeterDebugValue", 0, 0, 100D);

            HUD_ENERGY_METER_GLASS_RED = builder
                    .comment("hud energy meter glass red color amount (0 - 100)")
                    .defineInRange("energyMeterGlassRedPercent", 100, 0, 100);

            HUD_ENERGY_METER_GLASS_GREEN = builder
                    .comment("hud energy meter glass green color amount (0 - 100)")
                    .defineInRange("energyMeterGlassGreenPercent", 100, 0, 100);

            HUD_ENERGY_METER_GLASS_BLUE = builder
                    .comment("hud energy meter glass blue color amount (0 - 100)")
                    .defineInRange("energyMeterGlassBluePercent", 100, 0, 100);

            HUD_ENERGY_METER_GLASS_ALPHA = builder
                    .comment("hud energy meter glass alpha color amount (0 - 100)")
                    .defineInRange("energyMeterGlassAlphaPercent", 85, 0, 100);


            HUD_ENERGY_METER_BAR_RED = builder
                    .comment("hud energy meter bar red color amount (0 - 100)")
                    .defineInRange("energyMeterBarRedPercent", 20, 0, 100);

            HUD_ENERGY_METER_BAR_GREEN = builder
                    .comment("hud energy meter bar green color amount (0 - 100)")
                    .defineInRange("energyMeterBarGreenPercent", 90, 0, 100);

            HUD_ENERGY_METER_BAR_BLUE = builder
                    .comment("hud energy meter bar blue color amount (0 - 100)")
                    .defineInRange("energyMeterBarBluePercent", 30, 0, 100);

            HUD_ENERGY_METER_BAR_ALPHA = builder
                    .comment("hud energy meter bar alpha color amount (0 - 100)")
                    .defineInRange("energyMeterBarAlphaPercent", 85, 0, 100);
        }
        builder.pop();

        // Plasma Meter ------------------------------------------------------------------------------------------------
        builder.push("Weapon Charge Meter Settings");
        {
            PLASMA_METER_DEBUG_VAL = builder
                    .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                    .defineInRange("weaponChargeMeterDebugValue", 0, 0, 100D);

            HUD_WEAPON_CHARGE_METER_GLASS_RED = builder
                    .comment("hud weapon charge meter glass red color amount (0 - 100)")
                    .defineInRange("weaponChargeMeterGlassRedPercent", 100, 0, 100);

            HUD_WEAPON_CHARGE_METER_GLASS_GREEN = builder
                    .comment("hud weapon charge meter glass green color amount (0 - 100)")
                    .defineInRange("weaponChargeMeterGlassGreenPercent", 100, 0, 100);

            HUD_WEAPON_CHARGE_METER_GLASS_BLUE = builder
                    .comment("hud weapon charge meter glass blue color amount (0 - 100)")
                    .defineInRange("weaponChargeMeterGlassBluePercent", 100, 0, 100);

            HUD_WEAPON_CHARGE_METER_GLASS_ALPHA = builder
                    .comment("hud weapon charge meter glass alpha color amount (0 - 100)")
                    .defineInRange("weaponChargeMeterGlassAlphaPercent", 85, 0, 100);


            HUD_WEAPON_CHARGE_METER_BAR_RED = builder
                    .comment("hud weapon charge meter bar red color amount (0 - 100)")
                    .defineInRange("weaponChargeMeterBarRedPercent", 80, 0, 100);

            HUD_WEAPON_CHARGE_METER_BAR_GREEN = builder
                    .comment("hud weapon charge meter bar green color amount (0 - 100)")
                    .defineInRange("weaponChargeMeterBarGreenPercent", 60, 0, 100);

            HUD_WEAPON_CHARGE_METER_BAR_BLUE = builder
                    .comment("hud weapon charge meter bar blue color amount (0 - 100)")
                    .defineInRange("weaponChargeMeterBarBluePercent", 100, 0, 100);

            HUD_WEAPON_CHARGE_METER_BAR_ALPHA = builder
                    .comment("hud weapon charge meter bar alpha color amount (0 - 100)")
                    .defineInRange("weaponChargeMeterBarAlphaPercent", 85, 0, 100);
        }
        builder.pop();

        // Water Meter ------------------------------------------------------------------------------------------------
        builder.push("Water Meter Settings");
        {
            WATER_METER_DEBUG_VAL = builder
                    .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                    .defineInRange("waterMeterDebugValue", 0, 0, 100D);

            HUD_WATER_METER_GLASS_RED = builder
                    .comment("hud water meter glass red color amount (0 - 100)")
                    .defineInRange("waterMeterGlassRedPercent", 100, 0, 100);

            HUD_WATER_METER_GLASS_GREEN = builder
                    .comment("hud water meter glass green color amount (0 - 100)")
                    .defineInRange("waterMeterGlassGreenPercent", 100, 0, 100);

            HUD_WATER_METER_GLASS_BLUE = builder
                    .comment("hud water meter glass blue color amount (0 - 100)")
                    .defineInRange("waterMeterGlassBluePercent", 100, 0, 100);

            HUD_WATER_METER_GLASS_ALPHA = builder
                    .comment("hud water meter glass alpha color amount (0 - 100)")
                    .defineInRange("waterMeterGlassAlphaPercent", 85, 0, 100);


            HUD_WATER_METER_BAR_RED = builder
                    .comment("hud water meter bar red color amount (0 - 100)")
                    .defineInRange("waterMeterBarRedPercent", 100, 0, 100);

            HUD_WATER_METER_BAR_GREEN = builder
                    .comment("hud water meter bar green color amount (0 - 100)")
                    .defineInRange("waterMeterBarGreenPercent", 100, 0, 100);

            HUD_WATER_METER_BAR_BLUE = builder
                    .comment("hud water meter bar blue color amount (0 - 100)")
                    .defineInRange("waterMeterBarBluePercent", 100, 0, 100);

            HUD_WATER_METER_BAR_ALPHA = builder
                    .comment("hud water meter bar alpha color amount (0 - 100)")
                    .defineInRange("waterMeterBarAlphaPercent", 85, 0, 100);
        }
        builder.pop();

        builder.pop(); // Meter settings end
    }
}
