package com.lehjr.powersuits.common.config;

import com.lehjr.powersuits.common.config.module.ArmorModuleConfig;
import com.lehjr.powersuits.common.config.module.CosmeticModuleConfig;
import com.lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import com.lehjr.powersuits.common.config.module.ToolModuleConfig;
import com.lehjr.powersuits.common.config.module.VisionModuleConfig;
import com.lehjr.powersuits.common.config.module.WeaponModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MPSCommonConfig {

    private static final ModConfigSpec.Builder builder = new ModConfigSpec.Builder().comment("General settings").push("General");
    private static final ModConfigSpec.DoubleValue GENERAL_MAX_FLYING_SPEED = builder.comment("Maximum flight speed (in m/s)").translation(MPSConstants.CONFIG_GENERAL_MAX_FLYING_SPEED).defineInRange("maximumFlyingSpeedmps", 25.0, 0, Float.MAX_VALUE);

    public static final ModConfigSpec MPS_GENERAL_SPEC = builder.build();

    public static double maxFlyingSpeed;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() instanceof ModConfigSpec SPEC) {
            // this
            if (SPEC == MPS_GENERAL_SPEC) {
                maxFlyingSpeed = GENERAL_MAX_FLYING_SPEED.get();

            // Since only this common config event gets called, manually update the others
            } else if (SPEC == ArmorConfig.ARMOR_CONFIG_SPEC) {
                ArmorConfig.onLoad(event);
            } else if (SPEC == PowerFistConfig.POWER_FIST_CONFIG_SPEC){
                PowerFistConfig.onLoad(event);
            } else if (SPEC == ArmorModuleConfig.ARMOR_MODULE_CONFIG_SPEC){
                ArmorModuleConfig.onLoad(event);
            } else if (SPEC == CosmeticModuleConfig.COSMETIC_MODULE_CONFIG_SPEC) {
                CosmeticModuleConfig.onLoad(event);
                // TODO: fill in missing module configs

            } else if(SPEC == EnvironmentalModuleConfig.ENVIRONMENTAL_MODULE_SPEC) {
                EnvironmentalModuleConfig.onLoad(event);

            } else if (SPEC == ToolModuleConfig.MPS_TOOL_MODULE_SPEC){
                ToolModuleConfig.onLoad(event);
            } else if (SPEC == VisionModuleConfig.MPS_VISION_MODULE_SPEC){
                VisionModuleConfig.onLoad(event);
            } else if (SPEC == WeaponModuleConfig.MPS_WEAPON_MODULE_SPEC){
                WeaponModuleConfig.onLoad(event);
            }
        }
    }
}
