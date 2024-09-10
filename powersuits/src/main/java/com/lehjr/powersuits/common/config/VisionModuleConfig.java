package com.lehjr.powersuits.common.config;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class VisionModuleConfig {
    // Binnoculars
    private static final ModConfigSpec.Builder BINOCULARS_MODULE__SETTINGS_BUILDER = new ModConfigSpec.Builder().push("Vision Modules").push("Binnoculars");
    private static final ModConfigSpec.BooleanValue BINOCULARS_MODULE__IS_ALLOWED = BINOCULARS_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue BINOCULARS_MODULE__FOV_BASE = BINOCULARS_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.FOV_BASE, 0.5, 0, 100.0D);
    private static final ModConfigSpec.DoubleValue BINOCULARS_MODULE__FOV_FIELD_OF_VIEW_MULITIPLIER = BINOCULARS_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.FOV_FIELD_OF_VIEW_MULITIPLIER, 9.5, 0, 100.0D);

    // Night Vision
    private static final ModConfigSpec.Builder NIGHT_VISION_MODULE__SETTINGS_BUILDER = BINOCULARS_MODULE__SETTINGS_BUILDER.pop().push("Night Vision");
    private static final ModConfigSpec.BooleanValue NIGHT_VISION_MODULE__IS_ALLOWED = NIGHT_VISION_MODULE__SETTINGS_BUILDER.define(NuminaConstants.CONFIG_IS_ALLOWED, true);
    private static final ModConfigSpec.DoubleValue NIGHT_VISION_MODULE__ENERGY_CONSUMPTION = NIGHT_VISION_MODULE__SETTINGS_BUILDER.defineInRange(MPSConstants.ENERGY_CONSUMPTION_BASE, 100, 0, 1000000.0D);


    public static final ModConfigSpec MPS_VISION_MODULE_SPEC = NIGHT_VISION_MODULE__SETTINGS_BUILDER.build();

    // Binoculars
    public static boolean binocularsModuleIsAllowed;
    public static double binocularsModuleFOVBase;
    public static double binocularsModuleFOVFieldOfViewMultiplier;
    // Night Vision
    public static boolean nightVisionModuleIsAllowed;
    public static double nightVisionEnergyConsumption;

    @SubscribeEvent
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
