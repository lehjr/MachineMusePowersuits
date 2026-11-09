package lehjr.powersuits.common.config;

import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID)
public class MPSCommonConfig {

    private static final ModConfigSpec.Builder builder = new ModConfigSpec.Builder().comment("General settings").push("General");


    // 20 ticks per second...
    private static final ModConfigSpec.DoubleValue GENERAL_MAX_FLYING_SPEED = builder.comment("Maximum flight speed (in m/s)").translation(MPSConstants.CONFIG_GENERAL_MAX_FLYING_SPEED).defineInRange("maximumFlyingSpeedmps", 25.0, 0, Float.MAX_VALUE);

    public static final ModConfigSpec MPS_GENERAL_SPEC = builder.build();

    public static double maxFlyingSpeed;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_GENERAL_SPEC) {
                maxFlyingSpeed = GENERAL_MAX_FLYING_SPEED.get();
        }
    }
}
