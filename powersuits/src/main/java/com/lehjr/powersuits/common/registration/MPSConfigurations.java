package com.lehjr.powersuits.common.registration;

import com.lehjr.numina.common.config.ConfigHelper;
import com.lehjr.powersuits.client.config.MPSClientConfig;
import com.lehjr.powersuits.common.config.ArmorConfig;
import com.lehjr.powersuits.common.config.ArmorModuleConfig;
import com.lehjr.powersuits.common.config.CosmeticModuleConfig;
import com.lehjr.powersuits.common.config.MPSCommonConfig;
import com.lehjr.powersuits.common.config.PowerFistConfig;
import com.lehjr.powersuits.common.config.ToolModuleConfig;
import com.lehjr.powersuits.common.config.VisionModuleConfig;
import com.lehjr.powersuits.common.config.WeaponModuleConfig;
import com.lehjr.powersuits.common.config.module.tool.blockbreaking.AxeModuleConfig;
import com.lehjr.powersuits.common.config.module.tool.blockbreaking.PickaxeModuleConfig;
import com.lehjr.powersuits.common.config.module.tool.blockbreaking.HoeModuleConfig;
import com.lehjr.powersuits.common.config.module.tool.blockbreaking.ShovelModuleConfig;
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
        registerCommon(modContainer, ArmorConfig.MPS_ARMOR_CONFIG_SPEC, "common/items/armor.toml");

        // PowerFist --------------------------------------------------------------------------------------------------
        registerCommon(modContainer, PowerFistConfig.MPS_POWER_FIST_CONFIG_SPEC, "common/items/powerfist.toml");

        // Modules ----------------------------------------------------------------------------------------------------
        // Armor
        registerCommon(modContainer, ArmorModuleConfig.MPS_ARMOR_MODULE_CONFIG_SPEC, "common/items/modules/armor-modules.toml");

        // Cosmetic
        registerCommon(modContainer, CosmeticModuleConfig.MPS_COSMETIC_MODULE_CONFIG_SPEC, "common/items/modules/cosmetic-modules.toml");
        // Energy Generation
        // TODO!!
        // Environmental
        // TODO!!
        // Mining Enchantment
        // TODO!!
        // Mining Enhancement
        // TODO!!
        // Movement
        // TODO!!
        // Tool - Axe
        registerCommon(modContainer, AxeModuleConfig.MPS_AXE_MODULE_SPEC, "common/items/modules/tool/block_breaking/axes.toml");
        // Tool - Pickaxe
        registerCommon(modContainer, PickaxeModuleConfig.MPS_PICKAXE_MODULE_SPEC, "common/items/modules/tool/block_breaking/pickaxes.toml");
        // Tool - Hoe
        registerCommon(modContainer, HoeModuleConfig.MPS_HOE_MODULE_SPEC, "common/items/modules/tool/block_breaking/rototillers.toml");
        // Tool - Shovel
        registerCommon(modContainer, ShovelModuleConfig.MPS_SHOVEL_MODULE_SPEC, "common/items/modules/tool/block_breaking/shovels.toml");
        // Tool -Misc
        registerCommon(modContainer, ToolModuleConfig.MPS_TOOL_MODULE_SPEC, "common/items/modules/tool-modules.toml");
        // Vision
        registerCommon(modContainer, VisionModuleConfig.MPS_VISION_MODULE_SPEC, "common/items/modules/vision-modules.toml");
        // Weapon
        registerCommon(modContainer, WeaponModuleConfig.MPS_WEAPON_MODULE_SPEC, "common/items/modules/weapon-modules.toml");
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

        ArmorConfig.onLoad(event);
        PowerFistConfig.onLoad(event);

        ArmorModuleConfig.onLoad(event);
        CosmeticModuleConfig.onLoad(event);
        AxeModuleConfig.onLoad(event);
        PickaxeModuleConfig.onLoad(event);
        HoeModuleConfig.onLoad(event);
        ShovelModuleConfig.onLoad(event);
        ToolModuleConfig.onLoad(event);
        VisionModuleConfig.onLoad(event);
        WeaponModuleConfig.onLoad(event);
    }
}
