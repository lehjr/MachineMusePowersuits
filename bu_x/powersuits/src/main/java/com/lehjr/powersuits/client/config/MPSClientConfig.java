package com.lehjr.powersuits.client.config;

import com.lehjr.numina.client.config.IMeterConfig;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.MathUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;


@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MPSClientConfig {

    /**
     * HUD ----------------------------------------------------------------------------------------
     */
    private static final ModConfigSpec.Builder HUD_SETTINGS_BUILDER =
            new ModConfigSpec.Builder().push("HUD Settings").push("HUD");

    private static final ModConfigSpec.BooleanValue HUD_USE_GRAPHICAL_METERS = HUD_SETTINGS_BUILDER
            .comment("Use Graphical Meters")
            .translation(MPSConstants.CONFIG_HUD_USE_GRAPHICAL_METERS)
                .define("useGraphicalMeters", true);

    private static ModConfigSpec.BooleanValue HUD_DISPLAY_KB_HUD = HUD_SETTINGS_BUILDER
            .comment("Display KeyBinding HUD")
            .translation(MPSConstants.CONFIG_HUD_DISPLAY_KB_HUD)
                .define("keybind_HUD_on", true);

    private static ModConfigSpec.DoubleValue HUD_KEYBIND_X = HUD_SETTINGS_BUILDER
            .comment("x position")
            .translation(MPSConstants.CONFIG_HUD_KEYBIND_HUD_X)
                .defineInRange("keybindHUDx", 8.0, 0, Double.MAX_VALUE);

    private static ModConfigSpec.DoubleValue  HUD_KEYBIND_Y = HUD_SETTINGS_BUILDER
            .comment("x position")
            .translation(MPSConstants.CONFIG_HUD_KEYBIND_HUD_Y)
                .defineInRange("keybindHUDy", 60.0, 0, Double.MAX_VALUE);

    private static ModConfigSpec.BooleanValue HUD_USE_24_HOUR_CLOCK = HUD_SETTINGS_BUILDER
            .comment("Use a 24h clock instead of 12h")
            .translation(MPSConstants.CONFIG_HUD_USE_24_HOUR_CLOCK)
                .define("use24hClock", false);

    /**
     * Meters -------------------------------------------------------------------------------------
     */
    private static final ModConfigSpec.Builder METER_SETTINGS_BUILDER = HUD_SETTINGS_BUILDER.pop()
            .push("Meter Settings");// meter settings start

    private static ModConfigSpec.BooleanValue SHOW_METERS_WHEN_PAUSED = METER_SETTINGS_BUILDER
            .comment("Show meters when paused\n Useful for trying different colors in real time")
            .translation(NuminaConstants.CONFIG_USE_SOUNDS)
                .define("showMetersWhenPaused", false);


    // Heat Meter ---------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder HEAT_METER_SETTINGS_BUILDER = METER_SETTINGS_BUILDER
            .push("Heat Meter");

    private static ModConfigSpec.DoubleValue HEAT_METER_DEBUG_VAL = HEAT_METER_SETTINGS_BUILDER
            .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
            .defineInRange("heatMeterDebugValue", 0, 0, 100D);

    private static ModConfigSpec.IntValue HEAT_METER_GLASS_RED = HEAT_METER_SETTINGS_BUILDER
                .comment("hud heat meter glass red color amount (0 - 100)")
                .defineInRange("heatMeterGlassRedPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue HEAT_METER_GLASS_GREEN = HEAT_METER_SETTINGS_BUILDER
                .comment("hud heat meter glass green color amount (0 - 100)")
                .defineInRange("heatMeterGlassGreenPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue HEAT_METER_GLASS_BLUE = HEAT_METER_SETTINGS_BUILDER
                .comment("hud heat meter glass blue color amount (0 - 100)")
                .defineInRange("heatMeterGlassBluePercent", 100, 0, 100);

    private static ModConfigSpec.IntValue HEAT_METER_GLASS_ALPHA = HEAT_METER_SETTINGS_BUILDER
                .comment("hud heat meter glass alpha color amount (0 - 100)")
                .defineInRange("heatMeterGlassAlphaPercent", 85, 0, 100);


    private static ModConfigSpec.IntValue HEAT_METER_BAR_RED = HEAT_METER_SETTINGS_BUILDER
                .comment("hud heat meter bar red color amount (0 - 100)")
                .defineInRange("heatMeterBarRedPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue HEAT_METER_BAR_GREEN = HEAT_METER_SETTINGS_BUILDER
                .comment("hud heat meter bar green color amount (0 - 100)")
                .defineInRange("heatMeterBarGreenPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue HEAT_METER_BAR_BLUE = HEAT_METER_SETTINGS_BUILDER
                .comment("hud heat meter bar blue color amount (0 - 100)")
                .defineInRange("heatMeterBarBluePercent", 100, 0, 100);

    private static ModConfigSpec.IntValue HEAT_METER_BAR_ALPHA = HEAT_METER_SETTINGS_BUILDER
                .comment("hud heat meter bar alpha color amount (0 - 100)")
                .defineInRange("heatMeterBarAlphaPercent", 85, 0, 100);


    // Energy Meter -------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder ENERGY_METER_SETTINGS_BUILDER = HEAT_METER_SETTINGS_BUILDER
            .pop().push("Energy Meter");

    private static ModConfigSpec.DoubleValue ENERGY_METER_DEBUG_VAL = ENERGY_METER_SETTINGS_BUILDER
            .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
            .defineInRange("energyMeterDebugValue", 0, 0, 100D);

    private static ModConfigSpec.IntValue ENERGY_METER_GLASS_RED = ENERGY_METER_SETTINGS_BUILDER
            .comment("hud energy meter glass red color amount (0 - 100)")
            .defineInRange("energyMeterGlassRedPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue ENERGY_METER_GLASS_GREEN = ENERGY_METER_SETTINGS_BUILDER
            .comment("hud energy meter glass green color amount (0 - 100)")
            .defineInRange("energyMeterGlassGreenPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue ENERGY_METER_GLASS_BLUE = ENERGY_METER_SETTINGS_BUILDER
            .comment("hud energy meter glass blue color amount (0 - 100)")
            .defineInRange("energyMeterGlassBluePercent", 100, 0, 100);

    private static ModConfigSpec.IntValue ENERGY_METER_GLASS_ALPHA = ENERGY_METER_SETTINGS_BUILDER
            .comment("hud energy meter glass alpha color amount (0 - 100)")
            .defineInRange("energyMeterGlassAlphaPercent", 85, 0, 100);

    private static ModConfigSpec.IntValue ENERGY_METER_BAR_RED = ENERGY_METER_SETTINGS_BUILDER
            .comment("hud energy meter bar red color amount (0 - 100)")
            .defineInRange("energyMeterBarRedPercent", 20, 0, 100);

    private static ModConfigSpec.IntValue ENERGY_METER_BAR_GREEN = ENERGY_METER_SETTINGS_BUILDER
            .comment("hud energy meter bar green color amount (0 - 100)")
            .defineInRange("energyMeterBarGreenPercent", 90, 0, 100);

    private static ModConfigSpec.IntValue ENERGY_METER_BAR_BLUE = ENERGY_METER_SETTINGS_BUILDER
            .comment("hud energy meter bar blue color amount (0 - 100)")
            .defineInRange("energyMeterBarBluePercent", 30, 0, 100);

    private static ModConfigSpec.IntValue ENERGY_METER_BAR_ALPHA = ENERGY_METER_SETTINGS_BUILDER
            .comment("hud energy meter bar alpha color amount (0 - 100)")
            .defineInRange("energyMeterBarAlphaPercent", 85, 0, 100);


    // Plasma Meter -------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder PLASMA_METER_SETTINGS_BUILDER = ENERGY_METER_SETTINGS_BUILDER
            .pop().push("Weapon Charge Meter");

    private static ModConfigSpec.DoubleValue PLASMA_METER_DEBUG_VAL = PLASMA_METER_SETTINGS_BUILDER
                .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                .defineInRange("weaponChargeMeterDebugValue", 0, 0, 100D);

    private static ModConfigSpec.IntValue PLASMA_CHARGE_METER_GLASS_RED = PLASMA_METER_SETTINGS_BUILDER
                .comment("hud weapon charge meter glass red color amount (0 - 100)")
                .defineInRange("weaponChargeMeterGlassRedPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue PLASMA_CHARGE_METER_GLASS_GREEN = PLASMA_METER_SETTINGS_BUILDER
                .comment("hud weapon charge meter glass green color amount (0 - 100)")
                .defineInRange("weaponChargeMeterGlassGreenPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue PLASMA_CHARGE_METER_GLASS_BLUE = PLASMA_METER_SETTINGS_BUILDER
                .comment("hud weapon charge meter glass blue color amount (0 - 100)")
                .defineInRange("weaponChargeMeterGlassBluePercent", 100, 0, 100);

    private static ModConfigSpec.IntValue PLASMA_CHARGE_METER_GLASS_ALPHA = PLASMA_METER_SETTINGS_BUILDER
                .comment("hud weapon charge meter glass alpha color amount (0 - 100)")
                .defineInRange("weaponChargeMeterGlassAlphaPercent", 85, 0, 100);

    private static ModConfigSpec.IntValue PLASMA_CHARGE_METER_BAR_RED = PLASMA_METER_SETTINGS_BUILDER
                .comment("hud weapon charge meter bar red color amount (0 - 100)")
                .defineInRange("weaponChargeMeterBarRedPercent", 80, 0, 100);

    private static ModConfigSpec.IntValue PLASMA_CHARGE_METER_BAR_GREEN = PLASMA_METER_SETTINGS_BUILDER
                .comment("hud weapon charge meter bar green color amount (0 - 100)")
                .defineInRange("weaponChargeMeterBarGreenPercent", 60, 0, 100);

    private static ModConfigSpec.IntValue PLASMA_CHARGE_METER_BAR_BLUE = PLASMA_METER_SETTINGS_BUILDER
                .comment("hud weapon charge meter bar blue color amount (0 - 100)")
                .defineInRange("weaponChargeMeterBarBluePercent", 100, 0, 100);

    private static ModConfigSpec.IntValue PLASMA_CHARGE_METER_BAR_ALPHA = PLASMA_METER_SETTINGS_BUILDER
                .comment("hud weapon charge meter bar alpha color amount (0 - 100)")
                .defineInRange("weaponChargeMeterBarAlphaPercent", 85, 0, 100);


    // Water Meter -------------------------------------------------------------------------------
    private static final ModConfigSpec.Builder WATER_METER_SETTINGS_BUILDER = PLASMA_METER_SETTINGS_BUILDER
            .pop().push("Water Meter");

    private static ModConfigSpec.DoubleValue WATER_METER_DEBUG_VAL = WATER_METER_SETTINGS_BUILDER
                .comment("value to manually set the meter at.\n Useful for trying different colors in real time")
                .defineInRange("waterMeterDebugValue", 0, 0, 100D);

    private static ModConfigSpec.IntValue WATER_METER_GLASS_RED = WATER_METER_SETTINGS_BUILDER
                .comment("hud water meter glass red color amount (0 - 100)")
                .defineInRange("waterMeterGlassRedPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue WATER_METER_GLASS_GREEN = WATER_METER_SETTINGS_BUILDER
                .comment("hud water meter glass green color amount (0 - 100)")
                .defineInRange("waterMeterGlassGreenPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue WATER_METER_GLASS_BLUE = WATER_METER_SETTINGS_BUILDER
                .comment("hud water meter glass blue color amount (0 - 100)")
                .defineInRange("waterMeterGlassBluePercent", 100, 0, 100);

    private static ModConfigSpec.IntValue WATER_METER_GLASS_ALPHA = WATER_METER_SETTINGS_BUILDER
                .comment("hud water meter glass alpha color amount (0 - 100)")
                .defineInRange("waterMeterGlassAlphaPercent", 85, 0, 100);


    private static ModConfigSpec.IntValue WATER_METER_BAR_RED = WATER_METER_SETTINGS_BUILDER
                .comment("hud water meter bar red color amount (0 - 100)")
                .defineInRange("waterMeterBarRedPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue WATER_METER_BAR_GREEN = WATER_METER_SETTINGS_BUILDER
                .comment("hud water meter bar green color amount (0 - 100)")
                .defineInRange("waterMeterBarGreenPercent", 100, 0, 100);

    private static ModConfigSpec.IntValue WATER_METER_BAR_BLUE = WATER_METER_SETTINGS_BUILDER
                .comment("hud water meter bar blue color amount (0 - 100)")
                .defineInRange("waterMeterBarBluePercent", 100, 0, 100);

    private static ModConfigSpec.IntValue WATER_METER_BAR_ALPHA = WATER_METER_SETTINGS_BUILDER
                .comment("hud water meter bar alpha color amount (0 - 100)")
                .defineInRange("waterMeterBarAlphaPercent", 85, 0, 100);

    public static final ModConfigSpec CLIENT_SPEC = WATER_METER_SETTINGS_BUILDER.pop().build();

    // HUD
    public static boolean hud_use_graphical_meters;
    public static boolean hud_display_keybindings_hud;
    public static double hud_keybind_x;
    public static double hud_keybind_y;
    public static boolean hud_use_24_hour_clock;

    // Meters --------------------------------------
    public static boolean show_meters_when_paused;

    // Heat Meter
    private static double heat_meter_debug_val;
    private static int heat_meter_glass_red;
    private static int heat_meter_glass_green;
    private static int heat_meter_glass_blue;
    private static int heat_meter_glass_alpha;

    private static int heat_meter_bar_red;
    private static int heat_meter_bar_green;
    private static int heat_meter_bar_blue;
    private static int heat_meter_bar_alpha;

    // Energy Meter
    private static double energy_meter_debug_val;
    private static int energy_meter_glass_red;
    private static int energy_meter_glass_green;
    private static int energy_meter_glass_blue;
    private static int energy_meter_glass_alpha;

    private static int energy_meter_bar_red;
    private static int energy_meter_bar_green;
    private static int energy_meter_bar_blue;
    private static int energy_meter_bar_alpha;

    // Weapon Charge Meter
    private static double plasma_meter_debug_val;
    private static int plasma_meter_glass_red;
    private static int plasma_meter_glass_green;
    private static int plasma_meter_glass_blue;
    private static int plasma_meter_glass_alpha;

    private static int plasma_meter_bar_red;
    private static int plasma_meter_bar_green;
    private static int plasma_meter_bar_blue;
    private static int plasma_meter_bar_alpha;


    // Water Meter
    private static double water_meter_debug_val;
    private static int water_meter_glass_red;
    private static int water_meter_glass_green;
    private static int water_meter_glass_blue;
    private static int water_meter_glass_alpha;

    private static int water_meter_bar_red;
    private static int water_meter_bar_green;
    private static int water_meter_bar_blue;
    private static int water_meter_bar_alpha;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            hud_use_graphical_meters = HUD_USE_GRAPHICAL_METERS.get();
            hud_display_keybindings_hud = HUD_DISPLAY_KB_HUD.get();
            hud_keybind_x = HUD_KEYBIND_X.get();
            hud_keybind_y = HUD_KEYBIND_Y.get();
            hud_use_24_hour_clock = HUD_USE_24_HOUR_CLOCK.get();

            // Meters -----------------------------------------------------
            show_meters_when_paused = SHOW_METERS_WHEN_PAUSED.get();

            // Heat Meter
            heat_meter_debug_val = HEAT_METER_DEBUG_VAL.get();

            heat_meter_glass_red = HEAT_METER_GLASS_RED.get();
            heat_meter_glass_green = HEAT_METER_GLASS_GREEN.get();
            heat_meter_glass_blue = HEAT_METER_GLASS_BLUE.get();
            heat_meter_glass_alpha = HEAT_METER_GLASS_ALPHA.get();

            heat_meter_bar_red = HEAT_METER_BAR_RED.get();
            heat_meter_bar_green = HEAT_METER_BAR_GREEN.get();
            heat_meter_bar_blue = HEAT_METER_BAR_BLUE.get();
            heat_meter_bar_alpha = HEAT_METER_BAR_ALPHA.get();

            // Energy
            energy_meter_debug_val = ENERGY_METER_DEBUG_VAL.get();

            energy_meter_glass_red = ENERGY_METER_GLASS_RED.get();
            energy_meter_glass_green = ENERGY_METER_GLASS_GREEN.get();
            energy_meter_glass_blue = ENERGY_METER_GLASS_BLUE.get();
            energy_meter_glass_alpha = ENERGY_METER_GLASS_ALPHA.get();

            energy_meter_bar_red = ENERGY_METER_BAR_RED.get();
            energy_meter_bar_green = ENERGY_METER_BAR_GREEN.get();
            energy_meter_bar_blue = ENERGY_METER_BAR_BLUE.get();
            energy_meter_bar_alpha = ENERGY_METER_BAR_ALPHA.get();

            // Weapon Charge
            plasma_meter_debug_val = PLASMA_METER_DEBUG_VAL.get();

            plasma_meter_glass_red = PLASMA_CHARGE_METER_GLASS_RED.get();
            plasma_meter_glass_green = PLASMA_CHARGE_METER_GLASS_GREEN.get();
            plasma_meter_glass_blue = PLASMA_CHARGE_METER_GLASS_BLUE.get();
            plasma_meter_glass_alpha = PLASMA_CHARGE_METER_GLASS_ALPHA.get();

            plasma_meter_bar_red = PLASMA_CHARGE_METER_BAR_RED.get();
            plasma_meter_bar_green = PLASMA_CHARGE_METER_BAR_GREEN.get();
            plasma_meter_bar_blue = PLASMA_CHARGE_METER_BAR_BLUE.get();
            plasma_meter_bar_alpha = PLASMA_CHARGE_METER_BAR_ALPHA.get();

            // Water
            water_meter_debug_val = WATER_METER_DEBUG_VAL.get();

            water_meter_glass_red = WATER_METER_GLASS_RED.get();
            water_meter_glass_green = WATER_METER_GLASS_GREEN.get();
            water_meter_glass_blue = WATER_METER_GLASS_BLUE.get();
            water_meter_glass_alpha = WATER_METER_GLASS_ALPHA.get();

            water_meter_bar_red = WATER_METER_BAR_RED.get();
            water_meter_bar_green = WATER_METER_BAR_GREEN.get();
            water_meter_bar_blue = WATER_METER_BAR_BLUE.get();
            water_meter_bar_alpha = WATER_METER_BAR_ALPHA.get();
        }
    }

    public static IMeterConfig getHeatMeterConfig() {
        return HeatMeterConfig.INSTANCE;
    }

    public enum HeatMeterConfig implements IMeterConfig {
        INSTANCE;

        @Override
        public float getDebugValue() {
            return (float) (0.01 * MathUtils.clampDouble(heat_meter_debug_val, 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = heat_meter_glass_red * 0.01F;
            float green = heat_meter_glass_green * 0.01F;
            float blue = heat_meter_glass_blue * 0.01F;
            float alpha = heat_meter_glass_alpha * 0.01F;

            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = heat_meter_bar_red * 0.01F;
            float green = heat_meter_bar_green * 0.01F;
            float blue = heat_meter_bar_blue * 0.01F;
            float alpha = heat_meter_bar_alpha * 0.01F;

            return new Color(red, green, blue, alpha);
        }
    }

    public static IMeterConfig getEnergyMeterConfig() {
        return EnergyMeterConfig.INSTANCE;
    }

    public enum EnergyMeterConfig implements IMeterConfig {
        INSTANCE;

        @Override
        public float getDebugValue() {
            return (float) (0.01 * MathUtils.clampDouble(energy_meter_debug_val, 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = energy_meter_glass_red * 0.01F;
            float green = energy_meter_glass_green * 0.01F;
            float blue = energy_meter_glass_blue * 0.01F;
            float alpha = energy_meter_glass_alpha * 0.01F;

            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = energy_meter_bar_red * 0.01F;
            float green = energy_meter_bar_green * 0.01F;
            float blue = energy_meter_bar_blue * 0.01F;
            float alpha = energy_meter_bar_alpha * 0.01F;

            return new Color(red, green, blue, alpha);
        }
    }

    public static IMeterConfig getPlasmaMeterConfig() {
        return PlasmaMeterConfig.INSTANCE;
    }

    public enum PlasmaMeterConfig implements IMeterConfig {
        INSTANCE;

        @Override
        public float getDebugValue() {
            return (float) (0.01 * MathUtils.clampDouble(plasma_meter_debug_val, 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = plasma_meter_glass_red * 0.01F;
            float green = plasma_meter_glass_green * 0.01F;
            float blue = plasma_meter_glass_blue * 0.01F;
            float alpha = plasma_meter_glass_alpha * 0.01F;

            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = plasma_meter_bar_red * 0.01F;
            float green = plasma_meter_bar_green * 0.01F;
            float blue = plasma_meter_bar_blue * 0.01F;
            float alpha = plasma_meter_bar_alpha * 0.01F;

            return new Color(red, green, blue, alpha);
        }
    }

    public static IMeterConfig getWaterMeterConfig() {
        return WaterMeterConfig.INSTANCE;
    }

    public enum WaterMeterConfig implements IMeterConfig {
        INSTANCE;

        @Override
        public float getDebugValue() {
            return (float) (0.01 * MathUtils.clampDouble(water_meter_debug_val, 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = water_meter_glass_red * 0.01F;
            float green = water_meter_glass_green * 0.01F;
            float blue = water_meter_glass_blue * 0.01F;
            float alpha = water_meter_glass_alpha * 0.01F;

            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = water_meter_bar_red * 0.01F;
            float green = water_meter_bar_green * 0.01F;
            float blue = water_meter_bar_blue * 0.01F;
            float alpha = water_meter_bar_alpha * 0.01F;

            return new Color(red, green, blue, alpha);
        }
    }

    public static boolean showMetersWhenPaused() {
        return show_meters_when_paused;
    }
}
