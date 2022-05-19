package com.lehjr.powersuits.common.base;

import com.lehjr.powersuits.common.block.LuxCapacitor;
import com.lehjr.powersuits.common.block.TinkerTable;
import com.lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
import com.lehjr.powersuits.common.entity.LuxCapacitorEntity;
import com.lehjr.powersuits.common.entity.PlasmaBallEntity;
import com.lehjr.powersuits.common.entity.RailgunBoltEntity;
import com.lehjr.powersuits.common.entity.SpinningBladeEntity;
import com.lehjr.powersuits.common.item.armor.PowerArmorBoots;
import com.lehjr.powersuits.common.item.armor.PowerArmorChestplate;
import com.lehjr.powersuits.common.item.armor.PowerArmorHelmet;
import com.lehjr.powersuits.common.item.armor.PowerArmorLeggings;
import com.lehjr.powersuits.common.item.block.TinkerTableItem;
import com.lehjr.powersuits.common.item.handheld.PowerFist;
import com.lehjr.powersuits.common.item.module.armor.DiamondPlatingModule;
import com.lehjr.powersuits.common.item.module.armor.EnergyShieldModule;
import com.lehjr.powersuits.common.item.module.armor.IronPlatingModule;
import com.lehjr.powersuits.common.item.module.armor.LeatherPlatingModule;
import com.lehjr.powersuits.common.item.module.cosmetic.TransparentArmorModule;
import com.lehjr.powersuits.common.item.module.energygeneration.AdvancedSolarGeneratorModule;
import com.lehjr.powersuits.common.item.module.energygeneration.BasicSolarGeneratorModule;
import com.lehjr.powersuits.common.item.module.energygeneration.KineticGeneratorModule;
import com.lehjr.powersuits.common.item.module.energygeneration.ThermalGeneratorModule;
import com.lehjr.powersuits.common.item.module.environmental.*;
import com.lehjr.powersuits.common.item.module.miningenhancement.*;
import com.lehjr.powersuits.common.item.module.movement.*;
import com.lehjr.powersuits.common.item.module.special.InvisibilityModule;
import com.lehjr.powersuits.common.item.module.special.MagnetModule;
import com.lehjr.powersuits.common.item.module.special.PiglinPacificationModule;
import com.lehjr.powersuits.common.item.module.tool.*;
import com.lehjr.powersuits.common.item.module.vision.BinocularsModule;
import com.lehjr.powersuits.common.item.module.vision.NightVisionModule;
import com.lehjr.powersuits.common.item.module.weapon.*;
import com.lehjr.powersuits.common.menu.InstallSalvageMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MPSObjects {
    public static final MPSCreativeTab creativeTab = new MPSCreativeTab();

    /**
     * Blocks ------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MPSConstants.MOD_ID);

    public static final RegistryObject<TinkerTable> TINKER_TABLE_BLOCK = BLOCKS.register(MPSRegistryNames.TINKER_TABLE.getPath(), TinkerTable::new);

    public static final RegistryObject<LuxCapacitor> LUX_CAPACITOR_BLOCK = BLOCKS.register(MPSRegistryNames.LUX_CAPACITOR.getPath(), LuxCapacitor::new);

    /**
     * Entity Types ------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MPSConstants.MOD_ID);

    //    private static final RegistryObject<EntityType<TestEntity>> TEST_ENTITY = ENTITIES.register("test_entity", () -> EntityType.Builder.of(TestEntity::new, MobCategory.CREATURE).sized(16.0F, 8.0F).clientTrackingRange(10).build("test_entity"));
//
//
    public static final RegistryObject<EntityType<LuxCapacitorEntity>> LUX_CAPACITOR_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            ()-> EntityType.Builder.<LuxCapacitorEntity>of(LuxCapacitorEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .build(MPSRegistryNames.LUX_CAPACITOR.toString()));

    public static final RegistryObject<EntityType<PlasmaBallEntity>> PLASMA_BALL_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.PLASMA_BALL.getPath(),
            ()-> EntityType.Builder.<PlasmaBallEntity>of(PlasmaBallEntity::new, MobCategory.MISC)
//                    .size(0.25F, 0.25F)
                    .build(MPSRegistryNames.PLASMA_BALL.toString()));

    public static final RegistryObject<EntityType<RailgunBoltEntity>> RAILGUN_BOLT_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.RAILGUN_BOLT.getPath(),
            ()-> EntityType.Builder.<RailgunBoltEntity>of(RailgunBoltEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .build(MPSRegistryNames.RAILGUN_BOLT.toString()));
    public static final RegistryObject<EntityType<SpinningBladeEntity>> SPINNING_BLADE_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.SPINNING_BLADE.getPath(),
            ()-> EntityType.Builder.<SpinningBladeEntity>of(SpinningBladeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F) // FIXME! check size
                    .build(MPSRegistryNames.SPINNING_BLADE.getPath()));


    /**
     * Block Entity Types -------------------------------------------------------------------------
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MPSConstants.MOD_ID);

//    public static final RegistryObject<BlockEntityType<TinkerTableBlockEntity>> TINKER_TABLE_TILE_TYPE = TILE_TYPES.register(MPSRegistryNames.TINKER_TABLE,
//            () -> BlockEntityType.Builder.of(TinkerTableBlockEntity::new, TINKER_TABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<LuxCapacitorBlockEntity>> LUX_CAP_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            () -> BlockEntityType.Builder.of(LuxCapacitorBlockEntity::new, LUX_CAPACITOR_BLOCK.get()).build(null));









    /**
     * Items -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MPSConstants.MOD_ID);

    /* BlockItems --------------------------------------------------------------------------------- */
    // use directly as a module
    public static final RegistryObject<Item> TINKER_TABLE_ITEM = ITEMS.register(MPSRegistryNames.TINKER_TABLE.getPath(),
            () -> new TinkerTableItem(TINKER_TABLE_BLOCK.get()));

    public static final RegistryObject<Item> LUX_CAPACITOR_ITEM = ITEMS.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            () -> new BlockItem(LUX_CAPACITOR_BLOCK.get(), new Item.Properties()
                    .tab(MPSObjects.creativeTab)
                    .stacksTo(64)
                    .defaultDurability(-1)
                    .setNoRepair()));

    /* Armor -------------------------------------------------------------------------------------- */
    public static final RegistryObject<Item> POWER_ARMOR_HELMET = ITEMS.register(MPSRegistryNames.POWER_ARMOR_HELMET.getPath(),
            () -> new PowerArmorHelmet());

    public static final RegistryObject<Item> POWER_ARMOR_CHESTPLATE = ITEMS.register(MPSRegistryNames.POWER_ARMOR_CHESTPLATE.getPath(),
            () -> new PowerArmorChestplate());

    public static final RegistryObject<Item> POWER_ARMOR_LEGGINGS = ITEMS.register(MPSRegistryNames.POWER_ARMOR_LEGGINGS.getPath(),
            () -> new PowerArmorLeggings());

    public static final RegistryObject<Item> POWER_ARMOR_BOOTS = ITEMS.register(MPSRegistryNames.POWER_ARMOR_BOOTS.getPath(),
            () -> new PowerArmorBoots());

    /* HandHeld ----------------------------------------------------------------------------------- */
    public static final RegistryObject<Item> POWER_FIST = ITEMS.register(MPSRegistryNames.POWER_FIST.getPath(),
            () -> new PowerFist());

    /* Modules ------------------------------------------------------------------------------------ */
    // Armor --------------------------------------------------------------------------------------
    public static final RegistryObject<Item> LEATHER_PLATING_MODULE = registerModule(MPSRegistryNames.LEATHER_PLATING_MODULE, new LeatherPlatingModule());
    public static final RegistryObject<Item> IRON_PLATING_MODULE = registerModule(MPSRegistryNames.IRON_PLATING_MODULE, new IronPlatingModule());
    public static final RegistryObject<Item> DIAMOND_PLATING_MODULE = registerModule(MPSRegistryNames.DIAMOND_PLATING_MODULE, new DiamondPlatingModule());
    public static final RegistryObject<Item> ENERGY_SHIELD_MODULE = registerModule(MPSRegistryNames.ENERGY_SHIELD_MODULE, new EnergyShieldModule());

    // Cosmetic -----------------------------------------------------------------------------------
    public static final RegistryObject<Item> TRANSPARENT_ARMOR_MODULE = registerModule(MPSRegistryNames.TRANSPARENT_ARMOR_MODULE, new TransparentArmorModule());

    // Energy Generation --------------------------------------------------------------------------
    public static final RegistryObject<Item> SOLAR_GENERATOR_MODULE = registerModule(MPSRegistryNames.SOLAR_GENERATOR_MODULE, new BasicSolarGeneratorModule());
    public static final RegistryObject<Item> ADVANCED_SOLAR_GENERATOR_MODULE = registerModule(MPSRegistryNames.ADVANCED_SOLAR_GENERATOR_MODULE, new AdvancedSolarGeneratorModule());
    public static final RegistryObject<Item> KINETIC_GENERATOR_MODULE = registerModule(MPSRegistryNames.KINETIC_GENERATOR_MODULE, new KineticGeneratorModule());
    public static final RegistryObject<Item> THERMAL_GENERATOR_MODULE = registerModule(MPSRegistryNames.THERMAL_GENERATOR_MODULE, new ThermalGeneratorModule());

    // Environmental ------------------------------------------------------------------------------
    public static final RegistryObject<Item> AUTO_FEEDER_MODULE = registerModule(MPSRegistryNames.AUTO_FEEDER_MODULE, new AutoFeederModule());
    public static final RegistryObject<Item> COOLING_SYSTEM_MODULE = registerModule(MPSRegistryNames.COOLING_SYSTEM_MODULE, new CoolingSystemModule());
    public static final RegistryObject<Item> FLUID_TANK_MODULE = registerModule(MPSRegistryNames.FLUID_TANK_MODULE, new FluidTankModule());
    public static final RegistryObject<Item> MOB_REPULSOR_MODULE = registerModule(MPSRegistryNames.MOB_REPULSOR_MODULE, new MobRepulsorModule());
    public static final RegistryObject<Item> WATER_ELECTROLYZER_MODULE = registerModule(MPSRegistryNames.WATER_ELECTROLYZER_MODULE, new WaterElectrolyzerModule());

    // Mining Enhancements ------------------------------------------------------------------------
    public static final RegistryObject<Item> AOE_PICK_UPGRADE_MODULE = registerModule(MPSRegistryNames.AOE_PICK_UPGRADE_MODULE, new AOEPickUpgradeModule());
    public static final RegistryObject<Item> AQUA_AFFINITY_MODULE = registerModule(MPSRegistryNames.AQUA_AFFINITY_MODULE, new AquaAffinityModule());
    public static final RegistryObject<Item> SILK_TOUCH_MODULE = registerModule(MPSRegistryNames.SILK_TOUCH_MODULE, new SilkTouchModule());
    public static final RegistryObject<Item> FORTUNE_MODULE = registerModule(MPSRegistryNames.FORTUNE_MODULE, new FortuneModule());
    public static final RegistryObject<Item> VEIN_MINER_MODULE = registerModule(MPSRegistryNames.VEIN_MINER_MODULE, new VeinMinerModule());

    // WIP
    public static final RegistryObject<Item> TUNNEL_BORE_MODULE = registerModule(MPSRegistryNames.TUNNEL_BORE_MODULE, new TunnelBoreModule());
    public static final RegistryObject<Item> AOE_PICK_UPGRADE_MODULE2 = registerModule(MPSRegistryNames.AOE_PICK_UPGRADE_MODULE2, new AoEpickUpgradeModule2());

    // Movement -----------------------------------------------------------------------------------
    public static final RegistryObject<Item> BLINK_DRIVE_MODULE = registerModule(MPSRegistryNames.BLINK_DRIVE_MODULE, new BlinkDriveModule());
    public static final RegistryObject<Item> CLIMB_ASSIST_MODULE = registerModule(MPSRegistryNames.CLIMB_ASSIST_MODULE, new ClimbAssistModule());
    public static final RegistryObject<Item> DIMENSIONAL_RIFT_MODULE = registerModule(MPSRegistryNames.DIMENSIONAL_RIFT_MODULE, new DimensionalRiftModule());
    public static final RegistryObject<Item> FLIGHT_CONTROL_MODULE = registerModule(MPSRegistryNames.FLIGHT_CONTROL_MODULE, new FlightControlModule());
    public static final RegistryObject<Item> GLIDER_MODULE = registerModule(MPSRegistryNames.GLIDER_MODULE, new GliderModule());
    public static final RegistryObject<Item> JETBOOTS_MODULE = registerModule(MPSRegistryNames.JETBOOTS_MODULE, new JetBootsModule());
    public static final RegistryObject<Item> JETPACK_MODULE = registerModule(MPSRegistryNames.JETPACK_MODULE, new JetPackModule());
    public static final RegistryObject<Item> JUMP_ASSIST_MODULE = registerModule(MPSRegistryNames.JUMP_ASSIST_MODULE, new JumpAssistModule());
    public static final RegistryObject<Item> PARACHUTE_MODULE = registerModule(MPSRegistryNames.PARACHUTE_MODULE, new ParachuteModule());
    public static final RegistryObject<Item> SHOCK_ABSORBER_MODULE = registerModule(MPSRegistryNames.SHOCK_ABSORBER_MODULE, new ShockAbsorberModule());
    public static final RegistryObject<Item> SPRINT_ASSIST_MODULE = registerModule(MPSRegistryNames.SPRINT_ASSIST_MODULE, new SprintAssistModule());
    public static final RegistryObject<Item> SWIM_BOOST_MODULE = registerModule(MPSRegistryNames.SWIM_BOOST_MODULE, new SwimAssistModule());

    // Special ------------------------------------------------------------------------------------
    public static final RegistryObject<Item> ACTIVE_CAMOUFLAGE_MODULE = registerModule(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE, new InvisibilityModule());
    public static final RegistryObject<Item> MAGNET_MODULE = registerModule(MPSRegistryNames.MAGNET_MODULE, new MagnetModule());
    public static final RegistryObject<Item> PIGLIN_PACIFICATION_MODULE = registerModule(MPSRegistryNames.PIGLIN_PACIFICATION_MODULE, new PiglinPacificationModule());

    // Vision -------------------------------------------------------------------------------------
    public static final RegistryObject<Item> BINOCULARS_MODULE = registerModule(MPSRegistryNames.BINOCULARS_MODULE, new BinocularsModule());
    public static final RegistryObject<Item> NIGHT_VISION_MODULE = registerModule(MPSRegistryNames.NIGHT_VISION_MODULE, new NightVisionModule());

    // Tools --------------------------------------------------------------------------
    public static final RegistryObject<Item> AXE_MODULE = registerModule(MPSRegistryNames.AXE_MODULE, new AxeModule());
    public static final RegistryObject<Item> DIAMOND_PICK_UPGRADE_MODULE = registerModule(MPSRegistryNames.DIAMOND_PICK_UPGRADE_MODULE, new DiamondPickUpgradeModule());
    public static final RegistryObject<Item> FLINT_AND_STEEL_MODULE = registerModule(MPSRegistryNames.FLINT_AND_STEEL_MODULE, new FlintAndSteelModule());
    public static final RegistryObject<Item> HOE_MODULE = registerModule(MPSRegistryNames.HOE_MODULE, new HoeModule());
    public static final RegistryObject<Item> LEAF_BLOWER_MODULE = registerModule(MPSRegistryNames.LEAF_BLOWER_MODULE, new LeafBlowerModule());
    public static final RegistryObject<Item> LUX_CAPACITOR_MODULE = registerModule(MPSRegistryNames.LUX_CAPACITOR_MODULE, new LuxCapacitorModule());
    public static final RegistryObject<Item> PICKAXE_MODULE = registerModule(MPSRegistryNames.PICKAXE_MODULE, new PickaxeModule());
    public static final RegistryObject<Item> SHEARS_MODULE = registerModule(MPSRegistryNames.SHEARS_MODULE, new ShearsModule());
    public static final RegistryObject<Item> SHOVEL_MODULE = registerModule(MPSRegistryNames.SHOVEL_MODULE, new ShovelModule());

    // Weapons ------------------------------------------------------------------------------------
    public static final RegistryObject<Item> BLADE_LAUNCHER_MODULE = registerModule(MPSRegistryNames.BLADE_LAUNCHER_MODULE, new BladeLauncherModule());
    public static final RegistryObject<Item> LIGHTNING_MODULE = registerModule(MPSRegistryNames.LIGHTNING_MODULE, new LightningModule());
    public static final RegistryObject<Item> MELEE_ASSIST_MODULE = registerModule(MPSRegistryNames.MELEE_ASSIST_MODULE, new MeleeAssistModule());
    public static final RegistryObject<Item> PLASMA_CANNON_MODULE = registerModule(MPSRegistryNames.PLASMA_CANNON_MODULE, new PlasmaCannonModule());
    public static final RegistryObject<Item> RAILGUN_MODULE = registerModule(MPSRegistryNames.RAILGUN_MODULE, new RailgunModule());



    /**
     * Menu Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, MPSConstants.MOD_ID);

//    // Module crafting/install/salvage GUI
//    public static final RegistryObject<ContainerType<InstallSalvageCraftContainer>> SALVAGE_CRAFT_CONTAINER_TYPE =
//            CONTAINER_TYPES.register(MPSRegistryNames.INSTALL_SALVAGE_CRAFT_CONTAINER_TYPE,
//                    () -> IForgeContainerType.create((windowId, inv, data) -> new InstallSalvageCraftContainer(windowId, inv)));

    public static final RegistryObject<MenuType<InstallSalvageMenu>> INSTALL_SALVAGE_MENU_TYPE =
            MENU_TYPES.register(MPSRegistryNames.INSTALL_SALVAGE_MENU_TYPE,
                    () -> IForgeMenuType.create((windowId, inv, data) -> new InstallSalvageMenu(windowId, inv, data.readEnum(EquipmentSlot.class))));







    static RegistryObject<Item> registerModule(ResourceLocation regName, Item item) {
//        MPSModules.INSTANCE.addModule(MPSRegistryNames.getRegName(regName));
        return ITEMS.register(regName.getPath(), () -> item);
    }
}
