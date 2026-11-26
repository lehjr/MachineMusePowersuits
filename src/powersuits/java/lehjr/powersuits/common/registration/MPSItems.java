package lehjr.powersuits.common.registration;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.item.block.TinkerTableItem;
import lehjr.powersuits.common.item.electric.armor.ElectricArmor;
import lehjr.powersuits.common.item.electric.tool.PowerFist;
import lehjr.powersuits.common.item.module.armor.ArmorPlatingModule;
import lehjr.powersuits.common.item.module.armor.EnergyShieldModule;
import lehjr.powersuits.common.item.module.cosmetic.TransparentArmorModule;
import lehjr.powersuits.common.item.module.energy_generation.solar.SolarGeneratorModule;
import lehjr.powersuits.common.item.module.energy_generation.thermal.ThermalGeneratorModule;
import lehjr.powersuits.common.item.module.environmental.*;
import lehjr.powersuits.common.item.module.fluidstorage.CoolantTankModule;
import lehjr.powersuits.common.item.module.miningenchantment.AquaAffinityModule;
import lehjr.powersuits.common.item.module.miningenchantment.FortuneModule;
import lehjr.powersuits.common.item.module.miningenchantment.SilkTouchModule;
import lehjr.powersuits.common.item.module.miningenhancement.SelectiveMiner;
import lehjr.powersuits.common.item.module.miningenhancement.TunnelBoreModule;
import lehjr.powersuits.common.item.module.miningenhancement.VeinMinerModule;
import lehjr.powersuits.common.item.module.movement.*;
import lehjr.powersuits.common.item.module.tool.blockbreaking.*;
import lehjr.powersuits.common.item.module.tool.misc.FlintAndSteelModule;
import lehjr.powersuits.common.item.module.tool.misc.LeafBlowerModule;
import lehjr.powersuits.common.item.module.tool.misc.LuxCapacitorModule;
import lehjr.powersuits.common.item.module.vision.BinocularsModule;
import lehjr.powersuits.common.item.module.vision.NightVisionModule;
import lehjr.powersuits.common.item.module.weapon.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MPSItems {
    /**
     * Items -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Item> MPS_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MPSConstants.MOD_ID);

    /**
     *  BlockItems ---------------------------------------------------------------------------------
     */
    // use directly as a module
    public static final RegistryObject<Item> TINKER_TABLE_ITEM = MPS_ITEMS.register(MPSRegistryNames.TINKER_TABLE.getPath(),
            () -> new TinkerTableItem(MPSBlocks.TINKER_TABLE_BLOCK.get()));

    public static final RegistryObject<Item> LUX_CAPACITOR_ITEM = MPS_ITEMS.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            () -> new BlockItem(MPSBlocks.LUX_CAPACITOR_BLOCK.get(), new Item.Properties()
                    .stacksTo(64)
                    .defaultDurability(-1)
                    .setNoRepair()));

    /**
     * Modular Items ------------------------------------------------------------------------------
     */
    // Tools ==========================================================================================================
    public static final RegistryObject<Item> POWER_FIST_1 = MPS_ITEMS.register(MPSConstants.POWER_FIST_1.getPath(), PowerFist::new);
    public static final RegistryObject<Item> POWER_FIST_2 = MPS_ITEMS.register(MPSConstants.POWER_FIST_2.getPath(), PowerFist::new);
    public static final RegistryObject<Item> POWER_FIST_3 = MPS_ITEMS.register(MPSConstants.POWER_FIST_3.getPath(), PowerFist::new);
    public static final RegistryObject<Item> POWER_FIST_4 = MPS_ITEMS.register(MPSConstants.POWER_FIST_4.getPath(), PowerFist::new);

    // Armor ==========================================================================================================
    // Helmets
    public static final RegistryObject<Item> POWER_ARMOR_HELMET_1 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_HELMET_1.getPath(), () -> new ElectricArmor(ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> POWER_ARMOR_HELMET_2 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_HELMET_2.getPath(), () -> new ElectricArmor(ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> POWER_ARMOR_HELMET_3 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_HELMET_3.getPath(), () -> new ElectricArmor(ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> POWER_ARMOR_HELMET_4 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_HELMET_4.getPath(), () -> new ElectricArmor(ArmorItem.Type.HELMET));

    // Chestplates
    public static final RegistryObject<Item> POWER_ARMOR_CHESTPLATE_1 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_CHESTPLATE_1.getPath(), () -> new ElectricArmor(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> POWER_ARMOR_CHESTPLATE_2 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_CHESTPLATE_2.getPath(), () -> new ElectricArmor(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> POWER_ARMOR_CHESTPLATE_3 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_CHESTPLATE_3.getPath(), () -> new ElectricArmor(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> POWER_ARMOR_CHESTPLATE_4 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_CHESTPLATE_4.getPath(), () -> new ElectricArmor(ArmorItem.Type.CHESTPLATE));

    // Leggings
    public static final RegistryObject<Item> POWER_ARMOR_LEGGINGS_1 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_LEGGINGS_1.getPath(), () -> new ElectricArmor(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> POWER_ARMOR_LEGGINGS_2 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_LEGGINGS_2.getPath(), () -> new ElectricArmor(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> POWER_ARMOR_LEGGINGS_3 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_LEGGINGS_3.getPath(), () -> new ElectricArmor(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> POWER_ARMOR_LEGGINGS_4 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_LEGGINGS_4.getPath(), () -> new ElectricArmor(ArmorItem.Type.LEGGINGS));

    // Boots
    public static final RegistryObject<Item> POWER_ARMOR_BOOTS_1 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_BOOTS_1.getPath(), () -> new ElectricArmor(ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> POWER_ARMOR_BOOTS_2 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_BOOTS_2.getPath(), () -> new ElectricArmor(ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> POWER_ARMOR_BOOTS_3 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_BOOTS_3.getPath(), () -> new ElectricArmor(ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> POWER_ARMOR_BOOTS_4 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_BOOTS_4.getPath(), () -> new ElectricArmor(ArmorItem.Type.BOOTS));

    /**
     * Modules ------------------------------------------------------------------------------------
     */
    // Armor --------------------------------------------------------------------------------------
    public static final RegistryObject<Item> IRON_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.IRON_PLATING_MODULE.getPath(), ArmorPlatingModule::new);
    public static final RegistryObject<Item> DIAMOND_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_PLATING_MODULE.getPath(), ArmorPlatingModule::new);
    public static final RegistryObject<Item> NETHERITE_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_PLATING_MODULE.getPath(), ArmorPlatingModule::new);
    public static final RegistryObject<Item> ENERGY_SHIELD_MODULE = MPS_ITEMS.register(MPSConstants.ENERGY_SHIELD_MODULE.getPath(), EnergyShieldModule::new);

    // Cosmetic -----------------------------------------------------------------------------------
    public static final RegistryObject<Item> TRANSPARENT_ARMOR_MODULE = MPS_ITEMS.register(MPSConstants.TRANSPARENT_ARMOR_MODULE.getPath(), TransparentArmorModule::new);

    // Energy Generation --------------------------------------------------------------------------
    // Heat Generators
    public static RegistryObject<Item> THERMAL_GENERATOR_MODULE_1 = MPS_ITEMS.register(MPSConstants.THERMAL_GENERATOR_MODULE_1.getPath(), ThermalGeneratorModule::new);
    public static RegistryObject<Item> THERMAL_GENERATOR_MODULE_2 = MPS_ITEMS.register(MPSConstants.THERMAL_GENERATOR_MODULE_2.getPath(), ThermalGeneratorModule::new);
    public static RegistryObject<Item> THERMAL_GENERATOR_MODULE_3 = MPS_ITEMS.register(MPSConstants.THERMAL_GENERATOR_MODULE_3.getPath(), ThermalGeneratorModule::new);
    public static RegistryObject<Item> THERMAL_GENERATOR_MODULE_4 = MPS_ITEMS.register(MPSConstants.THERMAL_GENERATOR_MODULE_4.getPath(), ThermalGeneratorModule::new);

    // Solar --------------------------------------------------------------------------------------
    public static RegistryObject<Item> SOLAR_GENERATOR_MODULE_1 = MPS_ITEMS.register(MPSConstants.SOLAR_GENERATOR_MODULE_1.getPath(), SolarGeneratorModule::new);
    public static RegistryObject<Item> SOLAR_GENERATOR_MODULE_2 = MPS_ITEMS.register(MPSConstants.SOLAR_GENERATOR_MODULE_2.getPath(), SolarGeneratorModule::new);
    public static RegistryObject<Item> SOLAR_GENERATOR_MODULE_3 = MPS_ITEMS.register(MPSConstants.SOLAR_GENERATOR_MODULE_3.getPath(), SolarGeneratorModule::new);
    public static RegistryObject<Item> SOLAR_GENERATOR_MODULE_4 = MPS_ITEMS.register(MPSConstants.SOLAR_GENERATOR_MODULE_4.getPath(), SolarGeneratorModule::new);

    // Environmental ------------------------------------------------------------------------------
    public static RegistryObject<Item> ACTIVE_CAMOUFLAGE_MODULE = MPS_ITEMS.register(MPSConstants.ACTIVE_CAMOUFLAGE_MODULE.getPath(), ActiveCamouflageModule::new);
    public static RegistryObject<Item> AUTO_FEEDER_MODULE = MPS_ITEMS.register(MPSConstants.AUTO_FEEDER_MODULE.getPath(), AutoFeederModule::new);

    public static RegistryObject<Item> COOLANT_TANK_MODULE_1 = MPS_ITEMS.register(MPSConstants.COOLANT_TANK_MODULE_1.getPath(), CoolantTankModule::new);
    public static RegistryObject<Item> COOLANT_TANK_MODULE_2 = MPS_ITEMS.register(MPSConstants.COOLANT_TANK_MODULE_2.getPath(), CoolantTankModule::new);
    public static RegistryObject<Item> COOLANT_TANK_MODULE_3 = MPS_ITEMS.register(MPSConstants.COOLANT_TANK_MODULE_3.getPath(), CoolantTankModule::new);
    public static RegistryObject<Item> COOLANT_TANK_MODULE_4 = MPS_ITEMS.register(MPSConstants.COOLANT_TANK_MODULE_4.getPath(), CoolantTankModule::new);

    public static RegistryObject<Item> MAGNET_MODULE = MPS_ITEMS.register(MPSConstants.MAGNET_MODULE.getPath(), MagnetModule::new);
    public static RegistryObject<Item> MOB_REPULSOR_MODULE = MPS_ITEMS.register(MPSConstants.MOB_REPULSOR_MODULE.getPath(), MobRepulsorModule::new);
    public static RegistryObject<Item> PIGLIN_PACIFICATION_MODULE = MPS_ITEMS.register(MPSConstants.PIGLIN_PACIFICATION_MODULE.getPath(), PiglinPacificationModule::new);
    public static RegistryObject<Item> WATER_ELECTROLYZER_MODULE = MPS_ITEMS.register(MPSConstants.WATER_ELECTROLYZER_MODULE.getPath(), WaterElectrolyzerModule::new);

    // Mining Enchantments ------------------------------------------------------------------------
    public static RegistryObject<Item> AQUA_AFFINITY_MODULE = MPS_ITEMS.register(MPSConstants.AQUA_AFFINITY_MODULE.getPath(), AquaAffinityModule::new);
    public static RegistryObject<Item> FORTUNE_MODULE = MPS_ITEMS.register(MPSConstants.FORTUNE_MODULE.getPath(), FortuneModule::new);
    public static RegistryObject<Item> SILK_TOUCH_MODULE = MPS_ITEMS.register(MPSConstants.SILK_TOUCH_MODULE.getPath(), SilkTouchModule::new);

    // Mining Enhancements ------------------------------------------------------------------------
    public static RegistryObject<Item> TUNNEL_BORE_MODULE = MPS_ITEMS.register(MPSConstants.TUNNEL_BORE_MODULE.getPath(), TunnelBoreModule::new);
    public static RegistryObject<Item> VEIN_MINER_MODULE = MPS_ITEMS.register(MPSConstants.VEIN_MINER_MODULE.getPath(), VeinMinerModule::new);
    public static RegistryObject<Item> SELECTIVE_MINER_MODULE = MPS_ITEMS.register(MPSConstants.SELECTIVE_MINER_MODULE.getPath(), SelectiveMiner::new);

    // Movement -----------------------------------------------------------------------------------
    public static RegistryObject<Item> BLINK_DRIVE_MODULE = MPS_ITEMS.register(MPSConstants.BLINK_DRIVE_MODULE.getPath(), BlinkDriveModule::new);
    public static RegistryObject<Item> CLIMB_ASSIST_MODULE = MPS_ITEMS.register(MPSConstants.CLIMB_ASSIST_MODULE.getPath(), ClimbAssistModule::new);
    public static RegistryObject<Item> DIMENSIONAL_RIFT_MODULE = MPS_ITEMS.register(MPSConstants.DIMENSIONAL_RIFT_MODULE.getPath(), DimensionalRiftModule::new);
    public static RegistryObject<Item> FLIGHT_CONTROL_MODULE = MPS_ITEMS.register(MPSConstants.FLIGHT_CONTROL_MODULE.getPath(), FlightControlModule::new);
    public static RegistryObject<Item> GLIDER_MODULE = MPS_ITEMS.register(MPSConstants.GLIDER_MODULE.getPath(), GliderModule::new);
    public static RegistryObject<Item> JET_BOOTS_MODULE = MPS_ITEMS.register(MPSConstants.JETBOOTS_MODULE.getPath(), JetBootsModule::new);
    public static RegistryObject<Item> JETPACK_MODULE = MPS_ITEMS.register(MPSConstants.JETPACK_MODULE.getPath(), JetPackModule::new);
    public static RegistryObject<Item> JUMP_ASSIST_MODULE = MPS_ITEMS.register(MPSConstants.JUMP_ASSIST_MODULE.getPath(), JumpAssistModule::new);
    public static RegistryObject<Item> PARACHUTE_MODULE = MPS_ITEMS.register(MPSConstants.PARACHUTE_MODULE.getPath(), ParachuteModule::new);
    public static RegistryObject<Item> SHOCK_ABSORBER_MODULE = MPS_ITEMS.register(MPSConstants.SHOCK_ABSORBER_MODULE.getPath(), ShockAbsorberModule::new);
    public static RegistryObject<Item> SPRINT_ASSIST_MODULE = MPS_ITEMS.register(MPSConstants.SPRINT_ASSIST_MODULE.getPath(), SprintAssistModule::new);
    public static RegistryObject<Item> SWIM_ASSIST_MODULE = MPS_ITEMS.register(MPSConstants.SWIM_BOOST_MODULE.getPath(), SwimAssistModule::new);

    // Tools --------------------------------------------------------------------------------------
    // Axe
    public static final RegistryObject<Item> STONE_AXE_MODULE = MPS_ITEMS.register(MPSConstants.STONE_AXE_MODULE.getPath(), AxeModule::new);
    public static final RegistryObject<Item> IRON_AXE_MODULE = MPS_ITEMS.register(MPSConstants.IRON_AXE_MODULE.getPath(), AxeModule::new);
    public static final RegistryObject<Item> DIAMOND_AXE_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_AXE_MODULE.getPath(), AxeModule::new);
    public static final RegistryObject<Item> NETHERITE_AXE_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_AXE_MODULE.getPath(), AxeModule::new);

    // Tools --------------------------------------------------------------------------
    // Pickaxe
    public static final RegistryObject<Item> STONE_PICKAXE_MODULE = MPS_ITEMS.register(MPSConstants.STONE_PICKAXE_MODULE.getPath(), PickaxeModule::new);
    public static final RegistryObject<Item> IRON_PICKAXE_MODULE = MPS_ITEMS.register(MPSConstants.IRON_PICKAXE_MODULE.getPath(), PickaxeModule::new);
    public static final RegistryObject<Item> DIAMOND_PICKAXE_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_PICKAXE_MODULE.getPath(), PickaxeModule::new);
    public static final RegistryObject<Item> NETHERITE_PICKAXE_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_PICKAXE_MODULE.getPath(), PickaxeModule::new);

    // Shovel
    public static final RegistryObject<Item> STONE_SHOVEL_MODULE = MPS_ITEMS.register(MPSConstants.STONE_SHOVEL_MODULE.getPath(), ShovelModule::new);
    public static final RegistryObject<Item> IRON_SHOVEL_MODULE = MPS_ITEMS.register(MPSConstants.IRON_SHOVEL_MODULE.getPath(), ShovelModule::new);
    public static final RegistryObject<Item> DIAMOND_SHOVEL_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_SHOVEL_MODULE.getPath(), ShovelModule::new);
    public static final RegistryObject<Item> NETHERITE_SHOVEL_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_SHOVEL_MODULE.getPath(), ShovelModule::new);

    // Farming
    public static final RegistryObject<Item> STONE_ROTOTILLER_MODULE = MPS_ITEMS.register(MPSConstants.ROTOTILLER_MODULE_1.getPath(), HoeModule::new);
    public static final RegistryObject<Item> IRON_ROTOTILLER_MODULE = MPS_ITEMS.register(MPSConstants.ROTOTILLER_MODULE_2.getPath(), HoeModule::new);
    public static final RegistryObject<Item> DIAMOND_ROTOTILLER_MODULE = MPS_ITEMS.register(MPSConstants.ROTOTILLER_MODULE_3.getPath(), HoeModule::new);
    public static final RegistryObject<Item> NETHERITE_ROTOTILLER_MODULE = MPS_ITEMS.register(MPSConstants.ROTOTILLER_MODULE_4.getPath(), HoeModule::new);

    // Misc
    public static final RegistryObject<Item> FLINT_AND_STEEL_MODULE = MPS_ITEMS.register(MPSConstants.FLINT_AND_STEEL_MODULE.getPath(), FlintAndSteelModule::new);
    public static final RegistryObject<Item> LEAF_BLOWER_MODULE = MPS_ITEMS.register(MPSConstants.LEAF_BLOWER_MODULE.getPath(), LeafBlowerModule::new);
    public static final RegistryObject<Item> LUX_CAPACITOR_MODULE = MPS_ITEMS.register(MPSConstants.LUX_CAPACITOR_MODULE.getPath(), LuxCapacitorModule::new);
    public static final RegistryObject<Item> SHEARS_MODULE = MPS_ITEMS.register(MPSConstants.SHEARS_MODULE.getPath(), ShearsModule::new);

    // Vision ------------------------------------------------------------------------------------
    public static final RegistryObject<Item> BINOCULARS_MODULE = MPS_ITEMS.register(MPSConstants.BINOCULARS_MODULE.getPath(), BinocularsModule::new);
    public static final RegistryObject<Item> NIGHTVISION_MODULE = MPS_ITEMS.register(MPSConstants.NIGHT_VISION_MODULE.getPath(), NightVisionModule::new);

    // Weapons ------------------------------------------------------------------------------------
    public static final RegistryObject<Item> BLADE_LAUNCHER_MODULE = MPS_ITEMS.register(MPSConstants.BLADE_LAUNCHER_MODULE.getPath(), BladeLauncherModule::new);
    public static final RegistryObject<Item> LIGHTNING_SUMMONER_MODULE = MPS_ITEMS.register(MPSConstants.LIGHTNING_MODULE.getPath(), LightningModule::new);
    public static final RegistryObject<Item> MELEE_ASSIST_MODULE = MPS_ITEMS.register(MPSConstants.MELEE_ASSIST_MODULE.getPath(), MeleeAssistModule::new);
    public static final RegistryObject<Item> PLASMA_CANNON_MODULE = MPS_ITEMS.register(MPSConstants.PLASMA_CANNON_MODULE.getPath(), PlasmaCannonModule::new);
    public static final RegistryObject<Item> RAILGUN_MODULE = MPS_ITEMS.register(MPSConstants.RAILGUN_MODULE.getPath(), RailgunModule::new);


    // Creative Mode Tab --------------------------------------------------------------------------
    public static final DeferredRegister<CreativeModeTab> MPS_CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MPSConstants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MPS_TAB = MPS_CREATIVE_MODE_TAB.register("creative.mode.tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(POWER_ARMOR_HELMET_1.get()))
                    .title(Component.translatable(MPSConstants.CREATIVE_TAB_TRANSLATION_KEY))
                    .displayItems((parameters, output) -> MPS_ITEMS.getEntries().forEach(item -> output.accept(item.get())))
                    .build());
}
