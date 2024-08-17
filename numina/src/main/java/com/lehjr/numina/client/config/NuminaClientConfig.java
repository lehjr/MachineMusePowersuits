package com.lehjr.numina.client.config;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.MathUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;


@EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NuminaClientConfig {
    /**
     * General Settings -----------------------------------------------------------------------------------------------
     */
    private static final ModConfigSpec.Builder GENERAL_SETTINGS_BUILDER =
            new ModConfigSpec.Builder().push("General Settings");

    private static final ModConfigSpec.BooleanValue USE_FOV_FIX = GENERAL_SETTINGS_BUILDER
            .comment("Ignore speed boosts for field of view")
            .translation(NuminaConstants.CONFIG_USE_FOV_FIX)
            .define("useFOVFix", true);

    private static final ModConfigSpec.BooleanValue USE_FOV_NORMALIZE = GENERAL_SETTINGS_BUILDER
            .comment("Use FOV Fix to normalize FOV changes")
            .translation(NuminaConstants.CONFIG_USE_FOV_NORMALIZE)
            .define("useFOVNormalize", true);

    private static final ModConfigSpec.BooleanValue FOV_FIX_DEAULT_STATE = GENERAL_SETTINGS_BUILDER
            .comment("Default state of FOVfix on login (enabled = true, disabled = false)")
            .translation(NuminaConstants.CONFIG_FOV_FIX_DEAULT_STATE)
            .define("fovFixDefaultState", true);

    private static final ModConfigSpec.BooleanValue USE_SOUNDS = GENERAL_SETTINGS_BUILDER
            .comment("Use sounds")
            .translation(NuminaConstants.CONFIG_USE_SOUNDS)
            .define("useSounds", true);

    /**
     * Energy Meter ---------------------------------------------------------------------------------------------------
     */
    // Glass Color ----------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER = GENERAL_SETTINGS_BUILDER.pop().push("Energy Meter Settings").push("Glass Color");

    private static final ModConfigSpec.DoubleValue CHARGING_BASE_ENERGY_METER_DEBUG_VAL = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
            .defineInRange("chargingBaseEnergyMeterDebugValue", 0, 0, 100D);

    private static final ModConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_GLASS_RED = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("charging base meter glass red color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterGlassRedPercent", 100, 0, 100);

    private static final ModConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_GLASS_GREEN = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("charging base meter glass green color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterGlassGreenPercent", 100, 0, 100);

    private static final ModConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_GLASS_BLUE = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("charging base meter glass blue color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterGlassBluePercent", 100, 0, 100);

    private static final ModConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_GLASS_ALPHA = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER
            .comment("charging base meter glass alpha color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterGlassAlphaPercent", 85, 0, 100);

    // Bar Color ------------------------------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER = ENERGY_METER_GLASS_COLOR_SETTINGS_BUILDER.pop().push("Bar Color");

    private static final ModConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_BAR_RED = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER
            .comment("charging base meter bar red color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterBarRedPercent", 0, 0, 100);

    private static final ModConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_BAR_GREEN = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER
            .comment("charging base meter bar green color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterBarGreenPercent", 100, 0, 100);

    private static final ModConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_BAR_BLUE = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER
            .comment("charging base meter bar blue color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterBarBluePercent", 0, 0, 100);

    private static final ModConfigSpec.IntValue CHARGING_BASE_ENERGY_METER_BAR_ALPHA = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER
            .comment("charging base meter bar alpha color amount (0 - 100)")
            .defineInRange("chargingBaseEnergyMeterBarAlphaPercent", 85, 0, 100);

    private static final ModConfigSpec.Builder DEVELOPMENT_SETTINGS_BUILDER = ENERGY_METER_BAR_COLOR_SETTINGS_BUILDER.pop(2).push("Development Settings");

    private static final ModConfigSpec.BooleanValue DEBUGGING_INFO = DEVELOPMENT_SETTINGS_BUILDER
            .comment("Enable debugging info")
            .translation(NuminaConstants.CONFIG_DEBUGGING_INFO)
            .define("enableDebugging", false);

    public static final ModConfigSpec CLIENT_SPEC = DEVELOPMENT_SETTINGS_BUILDER.pop().build();

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