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
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.MathUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID)
public class NuminaClientConfig {
    /**
     * General Settings -----------------------------------------------------------------------------------------------
     */
    private static final ForgeConfigSpec.Builder GENERAL_SETTINGS_BUILDER =
            new ForgeConfigSpec.Builder().push("General Settings");

    private static final ForgeConfigSpec.BooleanValue USE_FOV_FIX = GENERAL_SETTINGS_BUILDER
            .comment("Ignore speed boosts for field of view")
            .translation(NuminaConstants.CONFIG_USE_FOV_FIX)
            .define("useFOVFix", true);

    private static final ForgeConfigSpec.BooleanValue USE_FOV_NORMALIZE = GENERAL_SETTINGS_BUILDER
            .comment("Use FOV Fix to normalize FOV changes")
            .translation(NuminaConstants.CONFIG_USE_FOV_NORMALIZE)
            .define("useFOVNormalize", true);

    private static final ForgeConfigSpec.BooleanValue FOV_FIX_DEAULT_STATE = GENERAL_SETTINGS_BUILDER
            .comment("Default state of FOVfix on login (enabled = true, disabled = false)")
            .translation(NuminaConstants.CONFIG_FOV_FIX_DEAULT_STATE)
            .define("fovFixDefaultState", true);

    private static final ForgeConfigSpec.BooleanValue USE_SOUNDS = GENERAL_SETTINGS_BUILDER
            .comment("Use sounds")
            .translation(NuminaConstants.CONFIG_USE_SOUNDS)
            .define("useSounds", true);

    /**
     * Energy Meter ---------------------------------------------------------------------------------------------------
     */
    // Glass Color ----------------------------------------------------------------------------------------------------
    private static final ForgeConfigSpec.Builder ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER = GENERAL_SETTINGS_BUILDER.pop().push("Energy Meter Settings").push("Glass Color");

    private static final ForgeConfigSpec.DoubleValue CHARGING_BASE_ENERGY_METER_DEBUG_VAL = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
            .defineInRange("chargingBaseEnergyMeterDebugValue", 0, 0, 100D);

    private static final ForgeConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_GLASS_RED = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("charging base meter glass red color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterGlassRedPercent", 100, 0, 100);

    private static final ForgeConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_GLASS_GREEN = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("charging base meter glass green color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterGlassGreenPercent", 100, 0, 100);

    private static final ForgeConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_GLASS_BLUE = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("charging base meter glass blue color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterGlassBluePercent", 100, 0, 100);

    private static final ForgeConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_GLASS_ALPHA = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("charging base meter glass alpha color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterGlassAlphaPercent", 85, 0, 100);

    // Bar Color ------------------------------------------------------------------------------------------------------
    private static final ForgeConfigSpec.Builder ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER.pop().push("Bar Color");

    private static final ForgeConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_BAR_RED = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER
            .comment("charging base meter bar red color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterBarRedPercent", 0, 0, 100);

    private static final ForgeConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_BAR_GREEN = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER
            .comment("charging base meter bar green color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterBarGreenPercent", 100, 0, 100);

    private static final ForgeConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_BAR_BLUE = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER
            .comment("charging base meter bar blue color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterBarBluePercent", 0, 0, 100);

    private static final ForgeConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_BAR_ALPHA = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER
            .comment("charging base meter bar alpha color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterBarAlphaPercent", 85, 0, 100);

    private static final ForgeConfigSpec.Builder DEVELOPMENT_SETTINGS_BUILDER = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER.pop(2).push("Development Settings");

    private static final ForgeConfigSpec.BooleanValue DEBUGGING_INFO = DEVELOPMENT_SETTINGS_BUILDER
            .comment("Enable debugging info")
            .translation(NuminaConstants.CONFIG_DEBUGGING_INFO)
            .define("enableDebugging", false);

    public static final ForgeConfigSpec CLIENT_SPEC = DEVELOPMENT_SETTINGS_BUILDER.pop().build();

    // General Settings
    public static boolean useFovFix;
    public static boolean useFovNormalize;
    public static boolean fovFixDefaultState;
    public static boolean useSounds;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            useFovFix = USE_FOV_FIX.get();
            useFovNormalize = USE_FOV_NORMALIZE.get();
            fovFixDefaultState = FOV_FIX_DEAULT_STATE.get();
            useSounds = USE_SOUNDS.get();

        }
    }

    public static IMeterConfig getEnergyMeterConfig() {
        return EnergyMeterConfig.INSTANCE;
    }

    public enum EnergyMeterConfig implements IMeterConfig {
        INSTANCE;

        @Override
        public float getDebugValue() {
            return (float) (0.01 * MathUtils.clampDouble(CHARGING_BASE_ENERGY_METER_DEBUG_VAL.get(), 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = CHARGING_BASE_ENERGY_METER_GLASS_RED.get() * 0.01F;
            float green = CHARGING_BASE_ENERGY_METER_GLASS_GREEN.get() * 0.01F;
            float blue = CHARGING_BASE_ENERGY_METER_GLASS_BLUE.get() * 0.01F;
            float alpha = CHARGING_BASE_ENERGY_METER_GLASS_ALPHA.get() * 0.01F;

            //"#d9 43 ff 64" = 217, 67, 255, 100; = 85, 26, 100, 39
            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = CHARGING_BASE_ENERGY_METER_BAR_RED.get() * 0.01F;
            float green = CHARGING_BASE_ENERGY_METER_BAR_GREEN.get() * 0.01F;
            float blue = CHARGING_BASE_ENERGY_METER_BAR_BLUE.get() * 0.01F;
            float alpha = CHARGING_BASE_ENERGY_METER_BAR_ALPHA.get() * 0.01F;

            return new Color(red, green, blue, alpha);
        }
    }

    public static boolean enableDebugging() {
        if(CLIENT_SPEC.isLoaded()) {
            return DEBUGGING_INFO.get();
        }
        return false;
    }
}
