package lehjr.powersuits.common.config;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CosmeticModuleConfig {
    // Transparent Armor
    private static final ModConfigSpec.Builder TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER =  new ModConfigSpec.Builder().push("Cosmetic Modules").push("Transparent Armor");
    private static final ModConfigSpec.BooleanValue TRANSPARENT_ARMOR_MODULE__IS_ALLOWED = TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    public static final ModConfigSpec MPS_COSMETIC_MODULE_CONFIG_SPEC = TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER.build();

    public static boolean isTransparentArmorAllowed;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_COSMETIC_MODULE_CONFIG_SPEC) {
            // Transparent Armor
            isTransparentArmorAllowed = TRANSPARENT_ARMOR_MODULE__IS_ALLOWED.get();
        }
    }
}
