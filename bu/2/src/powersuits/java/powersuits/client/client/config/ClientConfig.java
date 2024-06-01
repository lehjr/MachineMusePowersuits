package lehjr.numina.client.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {
    public static final ClientConfig CLIENT_CONFIG;
    public static final ModConfigSpec CLIENT_SPEC;

    static {
        {
            final Pair<ClientConfig, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
            CLIENT_SPEC = clientSpecPair.getRight();
            CLIENT_CONFIG = clientSpecPair.getLeft();
        }
    }

    /** HUD ---------------------------------------------------------------------------------------*/
    public ModConfigSpec.BooleanValue
            HUD_USE_GRAPHICAL_METERS,
            HUD_TOGGLE_MODULE_SPAM,
            HUD_DISPLAY_HUD,
            HUD_USE_24_HOUR_CLOCK,
            SHOW_METERS_WHEN_PAUSED;

    public ModConfigSpec.IntValue
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

    public ModConfigSpec.DoubleValue
            HUD_KEYBIND_X,
            HUD_KEYBIND_Y,

    HEAT_METER_DEBUG_VAL,
            ENERGY_METER_DEBUG_VAL,
            PLASMA_METER_DEBUG_VAL,
            WATER_METER_DEBUG_VAL;


    /** General ----------------------------------------------------------------------------------- */
    public ModConfigSpec.BooleanValue
            GENERAL_ALLOW_CONFLICTING_KEYBINDS;

    public ClientConfig(ModConfigSpec.Builder builder) {
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

    /** Client ------------------------------------------------------------------------------------ */
    // HUD ---------------------------------------------------------------------------------------
    public static boolean useGraphicalMeters() {
        return CLIENT_SPEC.isLoaded() ? CLIENT_CONFIG.HUD_USE_GRAPHICAL_METERS.get() : false;
    }

    public static boolean displayHud() {
        return CLIENT_SPEC.isLoaded() ? CLIENT_CONFIG.HUD_DISPLAY_HUD.get() : false;
    }

    public static boolean use24HourClock() {
        return CLIENT_SPEC.isLoaded() ? CLIENT_CONFIG.HUD_USE_24_HOUR_CLOCK.get() : false;
    }

    public static float getHudKeybindX() {
        return CLIENT_SPEC.isLoaded() ? toFloat(CLIENT_CONFIG.HUD_KEYBIND_X.get()) : 8.0F;
    }

    public static float getHudKeybindY() {
        return CLIENT_SPEC.isLoaded() ? toFloat(CLIENT_CONFIG.HUD_KEYBIND_Y.get()) : 32.0F;
    }

    public static IMeterConfig getHeatMeterConfig() {
        return HeatMeterConfig.INSTANCE;
    }

    public enum HeatMeterConfig implements IMeterConfig {
        INSTANCE;

        @Override
        public float getDebugValue() {
            return (float) (0.01 * MathUtils.clampDouble(CLIENT_CONFIG.HEAT_METER_DEBUG_VAL.get(), 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = CLIENT_CONFIG.HUD_HEAT_METER_GLASS_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.HUD_HEAT_METER_GLASS_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.HUD_HEAT_METER_GLASS_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.HUD_HEAT_METER_GLASS_ALPHA.get() * 0.01F;

            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = CLIENT_CONFIG.HUD_HEAT_METER_BAR_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.HUD_HEAT_METER_BAR_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.HUD_HEAT_METER_BAR_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.HUD_HEAT_METER_BAR_ALPHA.get() * 0.01F;

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
            return (float) (0.01 * MathUtils.clampDouble(CLIENT_CONFIG.ENERGY_METER_DEBUG_VAL.get(), 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = CLIENT_CONFIG.HUD_ENERGY_METER_GLASS_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.HUD_ENERGY_METER_GLASS_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.HUD_ENERGY_METER_GLASS_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.HUD_ENERGY_METER_GLASS_ALPHA.get() * 0.01F;

            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = CLIENT_CONFIG.HUD_ENERGY_METER_BAR_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.HUD_ENERGY_METER_BAR_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.HUD_ENERGY_METER_BAR_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.HUD_ENERGY_METER_BAR_ALPHA.get() * 0.01F;

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
            return (float) (0.01 * MathUtils.clampDouble(CLIENT_CONFIG.PLASMA_METER_DEBUG_VAL.get(), 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = CLIENT_CONFIG.HUD_WEAPON_CHARGE_METER_GLASS_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.HUD_WEAPON_CHARGE_METER_GLASS_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.HUD_WEAPON_CHARGE_METER_GLASS_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.HUD_WEAPON_CHARGE_METER_GLASS_ALPHA.get() * 0.01F;

            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = CLIENT_CONFIG.HUD_WEAPON_CHARGE_METER_BAR_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.HUD_WEAPON_CHARGE_METER_BAR_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.HUD_WEAPON_CHARGE_METER_BAR_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.HUD_WEAPON_CHARGE_METER_BAR_ALPHA.get() * 0.01F;

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
            return (float) (0.01 * MathUtils.clampDouble(CLIENT_CONFIG.WATER_METER_DEBUG_VAL.get(), 0, 100));
        }

        @Override
        public Color getGlassColor() {
            float red = CLIENT_CONFIG.HUD_WATER_METER_GLASS_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.HUD_WATER_METER_GLASS_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.HUD_WATER_METER_GLASS_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.HUD_WATER_METER_GLASS_ALPHA.get() * 0.01F;

            return new Color(red, green, blue, alpha);
        }

        @Override
        public Color getBarColor() {
            float red = CLIENT_CONFIG.HUD_WATER_METER_BAR_RED.get() * 0.01F;
            float green = CLIENT_CONFIG.HUD_WATER_METER_BAR_GREEN.get() * 0.01F;
            float blue = CLIENT_CONFIG.HUD_WATER_METER_BAR_BLUE.get() * 0.01F;
            float alpha = CLIENT_CONFIG.HUD_WATER_METER_BAR_ALPHA.get() * 0.01F;

            return new Color(red, green, blue, alpha);
        }
    }

    public static boolean showMetersWhenPaused() {
        return CLIENT_SPEC.isLoaded() ? CLIENT_CONFIG.SHOW_METERS_WHEN_PAUSED.get() : false;
    }
    
}
