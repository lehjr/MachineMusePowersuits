package lehjr.powersuits.common.config.module;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class VisionModuleConfig {
    // Binnoculars
    private static final ForgeConfigSpec.Builder BINOCULARS_MODULE__SETTINGS_BUILDER = new ForgeConfigSpec.Builder().push("Vision Modules").push("Binnoculars");
    private static final ForgeConfigSpec.BooleanValue BINOCULARS_MODULE__IS_ALLOWED = BINOCULARS_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue BINOCULARS_MODULE__FOV_BASE = BINOCULARS_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.FOV_BASE, 0.5, 0, 100.0D);
    private static final ForgeConfigSpec.DoubleValue BINOCULARS_MODULE__FOV_FIELD_OF_VIEW_MULITIPLIER = BINOCULARS_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.FOV_FIELD_OF_VIEW_MULITIPLIER, 9.5, 0, 100.0D);

    // Night Vision
    private static final ForgeConfigSpec.Builder NIGHT_VISION_MODULE__SETTINGS_BUILDER = BINOCULARS_MODULE__SETTINGS_BUILDER.pop().push("Night Vision");
    private static final ForgeConfigSpec.BooleanValue NIGHT_VISION_MODULE__IS_ALLOWED = NIGHT_VISION_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ForgeConfigSpec.DoubleValue NIGHT_VISION_MODULE__ENERGY_CONSUMPTION = NIGHT_VISION_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 100, 0, 1000000.0D);


    public static final ForgeConfigSpec MPS_VISION_MODULE_SPEC = NIGHT_VISION_MODULE__SETTINGS_BUILDER.build();

    // Binoculars
    public static boolean binocularsModuleIsAllowed;
    public static double binocularsModuleFOVBase;
    public static double binocularsModuleFOVFieldOfViewMultiplier;
    // Night Vision
    public static boolean nightVisionModuleIsAllowed;
    public static double nightVisionEnergyConsumption;

    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == MPS_VISION_MODULE_SPEC) {
            // Binoculars
            binocularsModuleIsAllowed = BINOCULARS_MODULE__IS_ALLOWED.get();
            binocularsModuleFOVBase = BINOCULARS_MODULE__FOV_BASE.get();
            binocularsModuleFOVFieldOfViewMultiplier = BINOCULARS_MODULE__FOV_FIELD_OF_VIEW_MULITIPLIER.get();
            // Night Vision
            nightVisionModuleIsAllowed = NIGHT_VISION_MODULE__IS_ALLOWED.get();
            nightVisionEnergyConsumption = NIGHT_VISION_MODULE__ENERGY_CONSUMPTION.get();
        }
    }
}
