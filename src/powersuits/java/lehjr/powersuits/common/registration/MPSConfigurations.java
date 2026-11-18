package lehjr.powersuits.common.registration;

import lehjr.numina.common.config.ConfigHelper;
import lehjr.powersuits.client.config.MPSClientConfig;
import lehjr.powersuits.common.config.ArmorConfig;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.config.PowerFistConfig;
import lehjr.powersuits.common.config.module.*;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID)
public class MPSConfigurations {
    public static void setup(ModLoadingContext modContainer) {
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

        // Tool - Axe
        registerCommon(modContainer, AxeModuleConfig.MPS_AXE_MODULE_SPEC, "common/items/modules/tool_axe.toml");

        // Energy Generation
        registerCommon(modContainer, EnergyGenerationModuleConfig.MPS_GENERATOR_MODULE_SPEC, "common/items/modules/energy_generation.toml");

        // Environmental
        registerCommon(modContainer, EnvironmentalModuleConfig.ENVIRONMENTAL_MODULE_SPEC, "common/items/modules/environmental.toml");

        // FLuid Storage
        registerCommon(modContainer, FluidStorageConfig.FLUID_STORAGE_MODULE_SPEC, "common/items/modules/fluid_storage.toml");

        // Tool - Hoe
        registerCommon(modContainer, HoeModuleConfig.MPS_HOE_MODULE_SPEC, "common/items/modules/tool_rototiller.toml");

        // Mining Enchantment
        registerCommon(modContainer, MiningEnchantmentModuleConfig.MINING_ENCHANTMENT_MODULE_CONFIG_SPEC, "common/items/modules/mining_enchantment.toml");

        // Mining Enhancement
        registerCommon(modContainer, MiningEnhancementModuleConfig.MINING_ENHANCEMENT_MODULE_SPEC, "common/items/modules/mining_enhancement.toml");

        // Movement
        registerCommon(modContainer, MovementModuleConfig.MPS_MOVEMENGT_MODULE_SPEC, "common/items/modules/movement.toml");
        // Tool - Pickaxe
        registerCommon(modContainer, PickaxeModuleConfig.MPS_PICKAXE_MODULE_SPEC, "common/items/modules/tool_pickaxe.toml");
        // Tool - Shovel
        registerCommon(modContainer, ShovelModuleConfig.MPS_SHOVEL_MODULE_SPEC, "common/items/modules/tool_shovels.toml");
        // Tool -Misc
        registerCommon(modContainer, ToolModuleConfig.MPS_TOOL_MODULE_SPEC, "common/items/modules/tool.toml");
        // Vision
        registerCommon(modContainer, VisionModuleConfig.MPS_VISION_MODULE_SPEC, "common/items/modules/vision.toml");
        // Weapon
        registerCommon(modContainer, WeaponModuleConfig.MPS_WEAPON_MODULE_SPEC, "common/items/modules/weapon.toml");
    }

    static void registerClient(ModLoadingContext modContainer, ForgeConfigSpec spec, String path) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, spec, ConfigHelper.setupConfigFile(path ,MPSConstants.MOD_ID).getAbsolutePath());
    }

    static void registerCommon(ModLoadingContext modContainer, ForgeConfigSpec spec, String path) {
        modContainer.registerConfig(ModConfig.Type.COMMON, spec, ConfigHelper.setupConfigFile(path ,MPSConstants.MOD_ID).getAbsolutePath());
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        MPSCommonConfig.onLoad(event);
        // Modular Items
        ArmorConfig.onLoad(event);
        PowerFistConfig.onLoad(event);

        // Modules
        ArmorModuleConfig.onLoad(event);

        CosmeticModuleConfig.onLoad(event);

        EnergyGenerationModuleConfig.onLoad(event);

        EnvironmentalModuleConfig.onLoad(event);

        FluidStorageConfig.onLoad(event);

        MiningEnchantmentModuleConfig.onLoad(event);

        MiningEnhancementModuleConfig.onLoad(event);

        MovementModuleConfig.onLoad(event);

        AxeModuleConfig.onLoad(event);
        HoeModuleConfig.onLoad(event);
        PickaxeModuleConfig.onLoad(event);
        ShovelModuleConfig.onLoad(event);
        ToolModuleConfig.onLoad(event);

        VisionModuleConfig.onLoad(event);
        WeaponModuleConfig.onLoad(event);
    }
}
