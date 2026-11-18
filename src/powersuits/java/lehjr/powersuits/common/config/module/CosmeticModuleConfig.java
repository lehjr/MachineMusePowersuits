package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.common.ForgeConfigSpec;

public class CosmeticModuleConfig {
    // Transparent Armor
    private static final ForgeConfigSpec.Builder TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER =  new ForgeConfigSpec.Builder().push("Cosmetic Modules").push("Transparent Armor");
    private static final ForgeConfigSpec.BooleanValue TRANSPARENT_ARMOR_MODULE__IS_ALLOWED = TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    public static final ForgeConfigSpec COSMETIC_MODULE_CONFIG_SPEC = TRANSPARENT_ARMOR_MODULE__SETTINGS_BUILDER.build();

    public static boolean isTransparentArmorAllowed;

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == COSMETIC_MODULE_CONFIG_SPEC) {
            // Transparent Armor
            isTransparentArmorAllowed = TRANSPARENT_ARMOR_MODULE__IS_ALLOWED.get();
        }
    }
}
