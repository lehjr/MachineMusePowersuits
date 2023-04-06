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

    public ForgeConfigSpec.ConfigValue<String>
            ENERGY_METER_GLASS_COLOR,
            ENERGY_METER_BAR_COLOR;

    public ForgeConfigSpec.DoubleValue ENERGY_METER_DEBUG_VAL;



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

        ENERGY_METER_DEBUG_VAL = builder
                .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                .defineInRange("chargingBaseEnergyMeterDebugValue", 0, 0, 100D);

        ENERGY_METER_GLASS_COLOR = builder
                .comment("Meter glass color in ARGB or RGB hex")
                .define("chargingBaseEnergyMeterGlassColor", "d9FFFFFF");

        ENERGY_METER_BAR_COLOR = builder
                .comment("Meter bar color in RGB or RGBA hex")
                .define("chargingBaseEnergyMeterBarColor", "d943ff64");
        builder.pop();

        builder.push("Development Settings");
        DEBUGGING_INFO = builder
                .comment("Enable debugging info")
                .translation(NuminaConstants.CONFIG_DEBUGGING_INFO)
                .define("enableDebugging", false);
        builder.pop();
    }
}
