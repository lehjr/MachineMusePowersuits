package com.lehjr.powersuits.common.registration;

import com.lehjr.numina.common.config.ConfigHelper;
import com.lehjr.powersuits.client.config.MPSClientConfig;
import com.lehjr.powersuits.common.config.ArmorConfig;
import com.lehjr.powersuits.common.config.module.ArmorModuleConfig;
import com.lehjr.powersuits.common.config.module.CosmeticModuleConfig;
import com.lehjr.powersuits.common.config.MPSCommonConfig;
import com.lehjr.powersuits.common.config.PowerFistConfig;
import com.lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import com.lehjr.powersuits.common.config.module.MovementModuleConfig;
import com.lehjr.powersuits.common.config.module.ToolModuleConfig;
import com.lehjr.powersuits.common.config.module.VisionModuleConfig;
import com.lehjr.powersuits.common.config.module.WeaponModuleConfig;
import com.lehjr.powersuits.common.config.module.AxeModuleConfig;
import com.lehjr.powersuits.common.config.module.PickaxeModuleConfig;
import com.lehjr.powersuits.common.config.module.HoeModuleConfig;
import com.lehjr.powersuits.common.config.module.ShovelModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MPSConfigurations {
    public static void setup(ModContainer modContainer) {
        // Client
        registerClient(modContainer, MPSClientConfig.CLIENT_SPEC, "powersuits-client-only.toml");

        // General ----------------------------------------------------------------------------------------------------
        registerCommon(modContainer, MPSCommonConfig.MPS_GENERAL_SPEC, "common/general.toml");

        // Armor ------------------------------------------------------------------------------------------------------
        registerCommon(modContainer, ArmorConfig.ARMOR_CONFIG_SPEC, "common/items/armor.toml");

        // PowerFist --------------------------------------------------------------------------------------------------
        registerCommon(modContainer, PowerFistConfig.POWER_FIST_CONFIG_SPEC, "common/items/powerfist.toml");

        // Modules ----------------------------------------------------------------------------------------------------
        // Armor
        registerCommon(modContainer, ArmorModuleConfig.ARMOR_MODULE_CONFIG_SPEC, "common/items/modules/armor.toml");

        // Cosmetic
        registerCommon(modContainer, CosmeticModuleConfig.COSMETIC_MODULE_CONFIG_SPEC, "common/items/modules/cosmetic.toml");
        // Energy Generation
        // TODO!!
        // Environmental
        registerCommon(modContainer, EnvironmentalModuleConfig.ENVIRONMENTAL_MODULE_SPEC, "common/items/modules/environmental.toml");


        // TODO!!
        // Mining Enchantment
        // TODO!!
        // Mining Enhancement
        // TODO!!
        // Movement
        registerCommon(modContainer, MovementModuleConfig.MPS_MOVEMENGT_MODULE_SPEC, "common/items/modules/movement.toml");
        // Tool - Axe
        registerCommon(modContainer, AxeModuleConfig.MPS_AXE_MODULE_SPEC, "common/items/modules/tool_axe.toml");
        // Tool - Pickaxe
        registerCommon(modContainer, PickaxeModuleConfig.MPS_PICKAXE_MODULE_SPEC, "common/items/modules/tool_pickaxe.toml");
        // Tool - Hoe
        registerCommon(modContainer, HoeModuleConfig.MPS_HOE_MODULE_SPEC, "common/items/modules/tool_rototiller.toml");
        // Tool - Shovel
        registerCommon(modContainer, ShovelModuleConfig.MPS_SHOVEL_MODULE_SPEC, "common/items/modules/tool_shovels.toml");
        // Tool -Misc
        registerCommon(modContainer, ToolModuleConfig.MPS_TOOL_MODULE_SPEC, "common/items/modules/tool.toml");
        // Vision
        registerCommon(modContainer, VisionModuleConfig.MPS_VISION_MODULE_SPEC, "common/items/modules/vision.toml");
        // Weapon
        registerCommon(modContainer, WeaponModuleConfig.MPS_WEAPON_MODULE_SPEC, "common/items/modules/weapon.toml");
    }

    static void registerClient(ModContainer modContainer, ModConfigSpec spec, String path) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, spec, ConfigHelper.setupConfigFile(path ,MPSConstants.MOD_ID).getAbsolutePath());
    }

    static void registerCommon(ModContainer modContainer, ModConfigSpec spec, String path) {
        modContainer.registerConfig(ModConfig.Type.COMMON, spec, ConfigHelper.setupConfigFile(path ,MPSConstants.MOD_ID).getAbsolutePath());
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        MPSCommonConfig.onLoad(event);
        // Modular Items
        ArmorConfig.onLoad(event);
        PowerFistConfig.onLoad(event);

        ArmorModuleConfig.onLoad(event);

        CosmeticModuleConfig.onLoad(event);

        EnvironmentalModuleConfig.onLoad(event);

        MovementModuleConfig.onLoad(event);

        AxeModuleConfig.onLoad(event);
        PickaxeModuleConfig.onLoad(event);
        HoeModuleConfig.onLoad(event);
        ShovelModuleConfig.onLoad(event);
        ToolModuleConfig.onLoad(event);

        VisionModuleConfig.onLoad(event);

        WeaponModuleConfig.onLoad(event);
    }
}
