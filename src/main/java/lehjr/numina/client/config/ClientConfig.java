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
            DEBUGGING_INFO;

    public ForgeConfigSpec.IntValue
            CHARGING_BASE_ENERGY_METER_GLASS_RED,
            CHARGING_BASE_ENERGY_METER_GLASS_BLUE,
            CHARGING_BASE_ENERGY_METER_GLASS_GREEN,
            CHARGING_BASE_ENERGY_METER_GLASS_ALPHA,
            CHARGING_BASE_ENERGY_METER_BAR_RED,
            CHARGING_BASE_ENERGY_METER_BAR_BLUE,
            CHARGING_BASE_ENERGY_METER_BAR_GREEN,
            CHARGING_BASE_ENERGY_METER_BAR_ALPHA;

    public ForgeConfigSpec.DoubleValue CHARGING_BASE_ENERGY_METER_DEBUG_VAL;



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

        // Energy Meter ------------------------------------------------------------------------------------------------
        builder.push("Charging Base Energy Meter Settings");

        CHARGING_BASE_ENERGY_METER_DEBUG_VAL = builder
                .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                .defineInRange("chargingBaseEnergyMeterDebugValue", 0, 0, 100D);

        CHARGING_BASE_ENERGY_METER_GLASS_RED = builder
                .comment("charging base meter glass red color amount (0 - 100)")
                .defineInRange("chargingBaseEnergyMeterGlassRedPercent", 100, 0, 100);

        CHARGING_BASE_ENERGY_METER_GLASS_BLUE = builder
                .comment("charging base meter glass blue color amount (0 - 100)")
                .defineInRange("chargingBaseEnergyMeterGlassBluePercent", 100, 0, 100);

        CHARGING_BASE_ENERGY_METER_GLASS_GREEN = builder
                .comment("charging base meter glass green color amount (0 - 100)")
                .defineInRange("chargingBaseEnergyMeterGlassGreenPercent", 100, 0, 100);

        CHARGING_BASE_ENERGY_METER_GLASS_ALPHA = builder
                .comment("charging base meter glass alpha color amount (0 - 100)")
                .defineInRange("chargingBaseEnergyMeterGlassAlphaPercent", 85, 0, 100);



        CHARGING_BASE_ENERGY_METER_BAR_RED = builder
                .comment("charging base meter bar red color amount (0 - 100)")
                .defineInRange("chargingBaseEnergyMeterBarRedPercent", 0, 0, 100);

        CHARGING_BASE_ENERGY_METER_BAR_GREEN = builder
                .comment("charging base meter bar green color amount (0 - 100)")
                .defineInRange("chargingBaseEnergyMeterBarGreenPercent", 100, 0, 100);

        CHARGING_BASE_ENERGY_METER_BAR_BLUE = builder
                .comment("charging base meter bar blue color amount (0 - 100)")
                .defineInRange("chargingBaseEnergyMeterBarBluePercent", 0, 0, 100);

        CHARGING_BASE_ENERGY_METER_BAR_ALPHA = builder
                .comment("charging base meter bar alpha color amount (0 - 100)")
                .defineInRange("chargingBaseEnergyMeterBarAlphaPercent", 85, 0, 100);

        builder.pop();

        builder.push("Development Settings");
        DEBUGGING_INFO = builder
                .comment("Enable debugging info")
                .translation(NuminaConstants.CONFIG_DEBUGGING_INFO)
                .define("enableDebugging", false);
        builder.pop();
    }
}
