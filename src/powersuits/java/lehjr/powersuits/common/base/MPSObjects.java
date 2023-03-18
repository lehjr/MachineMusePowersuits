/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.base;


import lehjr.numina.common.base.NuminaLogger;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.block.TinkerTable;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import lehjr.powersuits.common.blockentity.TinkerTableBlockEntity;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import lehjr.powersuits.common.container.InstallSalvageMenu;
import lehjr.powersuits.common.entity.LuxCapacitorEntity;
import lehjr.powersuits.common.entity.PlasmaBallEntity;
import lehjr.powersuits.common.entity.RailgunBoltEntity;
import lehjr.powersuits.common.entity.SpinningBladeEntity;
import lehjr.powersuits.common.item.armor.PowerArmorBoots;
import lehjr.powersuits.common.item.armor.PowerArmorChestplate;
import lehjr.powersuits.common.item.armor.PowerArmorHelmet;
import lehjr.powersuits.common.item.armor.PowerArmorLeggings;
import lehjr.powersuits.common.item.block.TinkerTableItem;
import lehjr.powersuits.common.item.module.armor.*;
import lehjr.powersuits.common.item.module.cosmetic.TransparentArmorModule;
import lehjr.powersuits.common.item.module.energy_generation.AdvancedSolarGeneratorModule;
import lehjr.powersuits.common.item.module.energy_generation.BasicSolarGeneratorModule;
import lehjr.powersuits.common.item.module.energy_generation.KineticGeneratorModule;
import lehjr.powersuits.common.item.module.energy_generation.ThermalGeneratorModule;
import lehjr.powersuits.common.item.module.environmental.*;
import lehjr.powersuits.common.item.module.miningenhancement.*;
import lehjr.powersuits.common.item.module.movement.*;
import lehjr.powersuits.common.item.module.special.InvisibilityModule;
import lehjr.powersuits.common.item.module.special.MagnetModule;
import lehjr.powersuits.common.item.module.special.PiglinPacificationModule;
import lehjr.powersuits.common.item.module.tool.*;
import lehjr.powersuits.common.item.module.vision.BinocularsModule;
import lehjr.powersuits.common.item.module.vision.NightVisionModule;
import lehjr.powersuits.common.item.module.weapon.*;
import lehjr.powersuits.common.item.tool.PowerFist;
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

    public static final RegistryObject<LuxCapacitorBlock> LUX_CAPACITOR_BLOCK = BLOCKS.register(MPSRegistryNames.LUX_CAPACITOR.getPath(), LuxCapacitorBlock::new);


    /**
     * Block Entity Types -------------------------------------------------------------------------
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MPSConstants.MOD_ID);

    public static final RegistryObject<BlockEntityType<TinkerTableBlockEntity>> TINKER_TABLE_BLOCKENTITY_TYPE = BLOCKENTITY_TYPES.register(MPSRegistryNames.TINKER_TABLE.getPath(),
            () -> BlockEntityType.Builder.of(TinkerTableBlockEntity::new, TINKER_TABLE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<LuxCapacitorBlockEntity>> LUX_CAP_BLOCK_ENTITY_TYPE = BLOCKENTITY_TYPES.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            () -> BlockEntityType.Builder.of(LuxCapacitorBlockEntity::new, LUX_CAPACITOR_BLOCK.get()).build(null));

    /**
     * Entity Types ------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MPSConstants.MOD_ID);

    public static final RegistryObject<EntityType<LuxCapacitorEntity>> LUX_CAPACITOR_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.LUX_CAPACITOR.getPath(),
            ()-> EntityType.Builder.<LuxCapacitorEntity>of(LuxCapacitorEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .build(MPSRegistryNames.LUX_CAPACITOR.toString()));

    public static final RegistryObject<EntityType<SpinningBladeEntity>> SPINNING_BLADE_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.SPINNING_BLADE.getPath(),
            ()-> EntityType.Builder.<SpinningBladeEntity>of(SpinningBladeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F) // FIXME! check size
                    .build(MPSRegistryNames.SPINNING_BLADE.toString()));

    public static final RegistryObject<EntityType<PlasmaBallEntity>> PLASMA_BALL_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.PLASMA_BALL.getPath(),
            ()-> EntityType.Builder.<PlasmaBallEntity>of(PlasmaBallEntity::new, MobCategory.MISC)
//                    .size(0.25F, 0.25F)
                    .build(MPSRegistryNames.PLASMA_BALL.toString()));

    public static final RegistryObject<EntityType<RailgunBoltEntity>> RAILGUN_BOLT_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.RAILGUN_BOLT.getPath(),
            ()-> EntityType.Builder.<RailgunBoltEntity>of(RailgunBoltEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .build(MPSRegistryNames.RAILGUN_BOLT.toString()));

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
    public static final RegistryObject<Item> LEATHER_PLATING_MODULE = registerModule(MPSRegistryNames.LEATHER_PLATING_MODULE.getPath(), new LeatherPlatingModule());
    public static final RegistryObject<Item> IRON_PLATING_MODULE = registerModule(MPSRegistryNames.IRON_PLATING_MODULE.getPath(), new IronPlatingModule());
    public static final RegistryObject<Item> DIAMOND_PLATING_MODULE = registerModule(MPSRegistryNames.DIAMOND_PLATING_MODULE.getPath(), new DiamondPlatingModule());
    public static final RegistryObject<Item> NETHERITE_PLATING_MODULE = registerModule(MPSRegistryNames.NETHERITE_PLATING_MODULE.getPath(), new NetheritePlatingModule());
    public static final RegistryObject<Item> ENERGY_SHIELD_MODULE = registerModule(MPSRegistryNames.ENERGY_SHIELD_MODULE.getPath(), new EnergyShieldModule());

    // Cosmetic -----------------------------------------------------------------------------------
    public static final RegistryObject<Item> TRANSPARENT_ARMOR_MODULE = registerModule(MPSRegistryNames.TRANSPARENT_ARMOR_MODULE.getPath(), new TransparentArmorModule());

    // Energy Generation --------------------------------------------------------------------------
    public static final RegistryObject<Item> SOLAR_GENERATOR_MODULE = registerModule(MPSRegistryNames.SOLAR_GENERATOR_MODULE.getPath(), new BasicSolarGeneratorModule());
    public static final RegistryObject<Item> ADVANCED_SOLAR_GENERATOR_MODULE = registerModule(MPSRegistryNames.ADVANCED_SOLAR_GENERATOR_MODULE.getPath(), new AdvancedSolarGeneratorModule());
    public static final RegistryObject<Item> KINETIC_GENERATOR_MODULE = registerModule(MPSRegistryNames.KINETIC_GENERATOR_MODULE.getPath(), new KineticGeneratorModule());
    public static final RegistryObject<Item> THERMAL_GENERATOR_MODULE = registerModule(MPSRegistryNames.THERMAL_GENERATOR_MODULE.getPath(), new ThermalGeneratorModule());

    // Environmental ------------------------------------------------------------------------------
    public static final RegistryObject<Item> AUTO_FEEDER_MODULE = registerModule(MPSRegistryNames.AUTO_FEEDER_MODULE.getPath(), new AutoFeederModule());
    public static final RegistryObject<Item> COOLING_SYSTEM_MODULE = registerModule(MPSRegistryNames.COOLING_SYSTEM_MODULE.getPath(), new CoolingSystemModule());
    public static final RegistryObject<Item> FLUID_TANK_MODULE = registerModule(MPSRegistryNames.FLUID_TANK_MODULE.getPath(), new FluidTankModule());
    public static final RegistryObject<Item> MOB_REPULSOR_MODULE = registerModule(MPSRegistryNames.MOB_REPULSOR_MODULE.getPath(), new MobRepulsorModule());
    public static final RegistryObject<Item> WATER_ELECTROLYZER_MODULE = registerModule(MPSRegistryNames.WATER_ELECTROLYZER_MODULE.getPath(), new WaterElectrolyzerModule());

    // Mining Enhancements ------------------------------------------------------------------------
    public static final RegistryObject<Item> AOE_PICK_UPGRADE_MODULE = registerModule(MPSRegistryNames.AOE_PICK_UPGRADE_MODULE.getPath(), new AOEPickUpgradeModule());
    public static final RegistryObject<Item> AQUA_AFFINITY_MODULE = registerModule(MPSRegistryNames.AQUA_AFFINITY_MODULE.getPath(), new AquaAffinityModule());
    public static final RegistryObject<Item> SILK_TOUCH_MODULE = registerModule(MPSRegistryNames.SILK_TOUCH_MODULE.getPath(), new SilkTouchModule());
    public static final RegistryObject<Item> FORTUNE_MODULE = registerModule(MPSRegistryNames.FORTUNE_MODULE.getPath(), new FortuneModule());
    public static final RegistryObject<Item> VEIN_MINER_MODULE = registerModule(MPSRegistryNames.VEIN_MINER_MODULE.getPath(), new VeinMinerModule());

    // WIP
    public static final RegistryObject<Item> TUNNEL_BORE_MODULE = registerModule(MPSRegistryNames.TUNNEL_BORE_MODULE.getPath(), new TunnelBoreModule());
    public static final RegistryObject<Item> AOE_PICK_UPGRADE_MODULE2 = registerModule(MPSRegistryNames.AOE_PICK_UPGRADE_MODULE2.getPath(), new AoEpickUpgradeModule2());



    // Movement -----------------------------------------------------------------------------------
    public static final RegistryObject<Item> BLINK_DRIVE_MODULE = registerModule(MPSRegistryNames.BLINK_DRIVE_MODULE.getPath(), new BlinkDriveModule());
    public static final RegistryObject<Item> CLIMB_ASSIST_MODULE = registerModule(MPSRegistryNames.CLIMB_ASSIST_MODULE.getPath(), new ClimbAssistModule());
    public static final RegistryObject<Item> DIMENSIONAL_RIFT_MODULE = registerModule(MPSRegistryNames.DIMENSIONAL_RIFT_MODULE.getPath(), new DimensionalRiftModule());
    public static final RegistryObject<Item> FLIGHT_CONTROL_MODULE = registerModule(MPSRegistryNames.FLIGHT_CONTROL_MODULE.getPath(), new FlightControlModule());
    public static final RegistryObject<Item> GLIDER_MODULE = registerModule(MPSRegistryNames.GLIDER_MODULE.getPath(), new GliderModule());
    public static final RegistryObject<Item> JETBOOTS_MODULE = registerModule(MPSRegistryNames.JETBOOTS_MODULE.getPath(), new JetBootsModule());
    public static final RegistryObject<Item> JETPACK_MODULE = registerModule(MPSRegistryNames.JETPACK_MODULE.getPath(), new JetPackModule());
    public static final RegistryObject<Item> JUMP_ASSIST_MODULE = registerModule(MPSRegistryNames.JUMP_ASSIST_MODULE.getPath(), new JumpAssistModule());
    public static final RegistryObject<Item> PARACHUTE_MODULE = registerModule(MPSRegistryNames.PARACHUTE_MODULE.getPath(), new ParachuteModule());
    public static final RegistryObject<Item> SHOCK_ABSORBER_MODULE = registerModule(MPSRegistryNames.SHOCK_ABSORBER_MODULE.getPath(), new ShockAbsorberModule());
    public static final RegistryObject<Item> SPRINT_ASSIST_MODULE = registerModule(MPSRegistryNames.SPRINT_ASSIST_MODULE.getPath(), new SprintAssistModule());
    public static final RegistryObject<Item> SWIM_BOOST_MODULE = registerModule(MPSRegistryNames.SWIM_BOOST_MODULE.getPath(), new SwimAssistModule());

    // Special ------------------------------------------------------------------------------------
    public static final RegistryObject<Item> ACTIVE_CAMOUFLAGE_MODULE = registerModule(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE.getPath(), new InvisibilityModule());
    public static final RegistryObject<Item> MAGNET_MODULE = registerModule(MPSRegistryNames.MAGNET_MODULE.getPath(), new MagnetModule());
    public static final RegistryObject<Item> PIGLIN_PACIFICATION_MODULE = registerModule(MPSRegistryNames.PIGLIN_PACIFICATION_MODULE.getPath(), new PiglinPacificationModule());

    // Vision -------------------------------------------------------------------------------------
    public static final RegistryObject<Item> BINOCULARS_MODULE = registerModule(MPSRegistryNames.BINOCULARS_MODULE.getPath(), new BinocularsModule());
    public static final RegistryObject<Item> NIGHT_VISION_MODULE = registerModule(MPSRegistryNames.NIGHT_VISION_MODULE.getPath(), new NightVisionModule());

    // Tools --------------------------------------------------------------------------
    public static final RegistryObject<Item> AXE_MODULE = registerModule(MPSRegistryNames.AXE_MODULE.getPath(), new AxeModule());
    public static final RegistryObject<Item> DIAMOND_PICK_UPGRADE_MODULE = registerModule(MPSRegistryNames.DIAMOND_PICK_UPGRADE_MODULE.getPath(), new DiamondPickUpgradeModule());
    public static final RegistryObject<Item> FLINT_AND_STEEL_MODULE = registerModule(MPSRegistryNames.FLINT_AND_STEEL_MODULE.getPath(), new FlintAndSteelModule());
    public static final RegistryObject<Item> HOE_MODULE = registerModule(MPSRegistryNames.HOE_MODULE.getPath(), new HoeModule());
    public static final RegistryObject<Item> LEAF_BLOWER_MODULE = registerModule(MPSRegistryNames.LEAF_BLOWER_MODULE.getPath(), new LeafBlowerModule());
    public static final RegistryObject<Item> LUX_CAPACITOR_MODULE = registerModule(MPSRegistryNames.LUX_CAPACITOR_MODULE.getPath(), new LuxCapacitorModule());
    public static final RegistryObject<Item> PICKAXE_MODULE = registerModule(MPSRegistryNames.PICKAXE_MODULE.getPath(), new PickaxeModule());
    public static final RegistryObject<Item> SHEARS_MODULE = registerModule(MPSRegistryNames.SHEARS_MODULE.getPath(), new ShearsModule());
    public static final RegistryObject<Item> SHOVEL_MODULE = registerModule(MPSRegistryNames.SHOVEL_MODULE.getPath(), new ShovelModule());

    // portable tinker table?
    // portable crafting table?



    // Debug --------------------------------------------------------------------------------------
    // todo

    // Weapons ------------------------------------------------------------------------------------
    public static final RegistryObject<Item> BLADE_LAUNCHER_MODULE = registerModule(MPSRegistryNames.BLADE_LAUNCHER_MODULE.getPath(), new BladeLauncherModule());
    public static final RegistryObject<Item> LIGHTNING_MODULE = registerModule(MPSRegistryNames.LIGHTNING_MODULE.getPath(), new LightningModule());
    public static final RegistryObject<Item> MELEE_ASSIST_MODULE = registerModule(MPSRegistryNames.MELEE_ASSIST_MODULE.getPath(), new MeleeAssistModule());
    public static final RegistryObject<Item> PLASMA_CANNON_MODULE = registerModule(MPSRegistryNames.PLASMA_CANNON_MODULE.getPath(), new PlasmaCannonModule());
    public static final RegistryObject<Item> RAILGUN_MODULE = registerModule(MPSRegistryNames.RAILGUN_MODULE.getPath(), new RailgunModule());

    /**
     * AbstractContainerMenu Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, MPSConstants.MOD_ID);

//    // Module crafting/install/salvage GUI
//    public static final RegistryObject<MenuType<InstallSalvageCraftAbstractContainerMenu>> SALVAGE_CRAFT_CONTAINER_TYPE =
//            CONTAINER_TYPES.register(MPSRegistryNames.INSTALL_SALVAGE_CRAFT_CONTAINER_TYPE,
//                    () -> IForgeMenuType.create((windowId, inv, data) -> new InstallSalvageCraftAbstractContainerMenu(windowId, inv)));

    public static final RegistryObject<MenuType<InstallSalvageMenu>> INSTALL_SALVAGE_MENU_TYPE =
            CONTAINER_TYPES.register(MPSRegistryNames.INSTALL_SALVAGE_CONTAINER_TYPE,


                    () -> IForgeMenuType.create((windowId, inv, data) -> {
                        NuminaLogger.logError("doing something with install/salvage");

                        return new InstallSalvageMenu(windowId, inv, data.readEnum(EquipmentSlot.class));
                    }));

    static RegistryObject<Item> registerModule(String regName, Item item) {
        MPSModules.INSTANCE.addModule(MPSRegistryNames.getRegName(regName));
        return ITEMS.register(regName, () -> item);
    }
}