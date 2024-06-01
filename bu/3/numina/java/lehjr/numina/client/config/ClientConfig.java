package lehjr.numina.client.config;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.MathUtils;
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

    private static ModConfigSpec.BooleanValue
            USE_FOV_FIX,
            USE_FOV_NORMALIZE,
            FOV_FIX_DEAULT_STATE,
            USE_SOUNDS,
            DEBUGGING_INFO;

    private static ModConfigSpec.IntValue
            CHARGING_BASE_ENERGY_METER_GLASS_RED,
            CHARGING_BASE_ENERGY_METER_GLASS_BLUE,
            CHARGING_BASE_ENERGY_METER_GLASS_GREEN,
            CHARGING_BASE_ENERGY_METER_GLASS_ALPHA,
            CHARGING_BASE_ENERGY_METER_BAR_RED,
            CHARGING_BASE_ENERGY_METER_BAR_BLUE,
            CHARGING_BASE_ENERGY_METER_BAR_GREEN,
            CHARGING_BASE_ENERGY_METER_BAR_ALPHA;

    private final ModConfigSpec.DoubleValue CHARGING_BASE_ENERGY_METER_DEBUG_VAL;

    public ClientConfig(ModConfigSpec.Builder builder) {
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


    /** Client settings --------------------------------------------------------------------------- */
    public static boolean useFovFix() {
        return CLIENT_CONFIG != null ? USE_FOV_FIX.get() : true;
    }

    public static boolean useFovFixInPrincessMode() {
        return CLIENT_CONFIG != null ? USE_FOV_FIX.get() : true;
    }

    public static boolean useFovNormalize() {
        return CLIENT_CONFIG != null ? USE_FOV_NORMALIZE.get() : false;
    }

    public static boolean fovFixDefaultState() {
        return CLIENT_CONFIG != null ? FOV_FIX_DEAULT_STATE.get() : true;
    }

    public static boolean useSounds() {
        return CLIENT_CONFIG != null ? USE_SOUNDS.get() : true;
    }


    public static IMeterConfig getEnergyMeterConfig() {
        return EnergyMeterConfig.INSTANCE;
    }

    public enum EnergyMeterConfig implements IMeterConfig {
        INSTANCE;

        @Override
        public float getDebugValue() {
            return (float) (0.01 * MathUtils.clampDouble(CLIENT_CONFIG.CHARGING_BASE_ENERGY_METER_DEBUG_VAL.get(), 0, 100));
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