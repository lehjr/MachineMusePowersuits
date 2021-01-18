package com.github.lehjr.numina.basemod;

import com.github.lehjr.numina.block.ChargingBaseBlock;
import com.github.lehjr.numina.client.render.item.MPAArmorStationItemRenderer;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.container.ArmorStandContainer;
import com.github.lehjr.numina.container.ChargingBaseContainer;
import com.github.lehjr.numina.container.CraftingContainer;
import com.github.lehjr.numina.entity.MPAArmorStandEntity;
import com.github.lehjr.numina.item.Battery;
import com.github.lehjr.numina.item.ItemComponent;
import com.github.lehjr.numina.item.MPAArmorStandItem;
import com.github.lehjr.numina.item.PlasmaBallTest;
import com.github.lehjr.numina.tileentity.ChargingBaseTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NuminaObjects {
    public static final NuminaCreativeTab creativeTab = new NuminaCreativeTab();

    /**
     * Blocks -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NuminaConstants.MOD_ID);

    public static final RegistryObject<ChargingBaseBlock> CHARGING_BASE_BLOCK = BLOCKS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> new ChargingBaseBlock());

    /**
     * Items --------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuminaConstants.MOD_ID);

    // Components ---------------------------------------------------------------------------------
    public static final RegistryObject<Item> WIRING = ITEMS.register(NuminaConstants.COMPONENT__WIRING__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> SOLENOID = ITEMS.register(NuminaConstants.COMPONENT__SOLENOID__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> SERVO = ITEMS.register(NuminaConstants.COMPONENT__SERVO__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> GLIDER_WING = ITEMS.register(NuminaConstants.COMPONENT__GLIDER_WING__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> ION_THRUSTER = ITEMS.register(NuminaConstants.COMPONENT__ION_THRUSTER__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> PARACHUTE = ITEMS.register(NuminaConstants.COMPONENT__PARACHUTE__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> FIELD_EMITTER = ITEMS.register(NuminaConstants.COMPONENT__FIELD_EMITTER__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> LASER_EMITTER = ITEMS.register(NuminaConstants.COMPONENT__LASER_EMITTER__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> CARBON_MYOFIBER = ITEMS.register(NuminaConstants.COMPONENT__CARBON_MYOFIBER__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> CONTROL_CIRCUIT = ITEMS.register(NuminaConstants.COMPONENT__CONTROL_CIRCUIT__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> MYOFIBER_GEL = ITEMS.register(NuminaConstants.COMPONENT__MYOFIBER_GEL__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> ARTIFICIAL_MUSCLE = ITEMS.register(NuminaConstants.COMPONENT__ARTIFICIAL_MUSCLE__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> SOLAR_PANEL = ITEMS.register(NuminaConstants.COMPONENT__SOLAR_PANEL__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> MAGNET = ITEMS.register(NuminaConstants.COMPONENT__MAGNET__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> COMPUTER_CHIP = ITEMS.register(NuminaConstants.COMPONENT__COMPUTER_CHIP__REGNAME,
            () -> new ItemComponent());

    public static final RegistryObject<Item> RUBBER_HOSE = ITEMS.register(NuminaConstants.COMPONENT__RUBBER_HOSE__REGNAME,
            () -> new ItemComponent());

    // TEST ITEM TO BE REMOVED
    public static final RegistryObject<Item> PLASMA_BALL = ITEMS.register("plasma_ball",
            () -> new PlasmaBallTest());

    // Modules ------------------------------------------------------------------------------------
    // Energy Storage
    public static final RegistryObject<Item> BASIC_BATTERY = ITEMS.register(NuminaConstants.MODULE_BATTERY_BASIC__REGNAME,
            () -> new Battery(1000000, 1000000));

    public static final RegistryObject<Item> ADVANCED_BATTERY = ITEMS.register(NuminaConstants.MODULE_BATTERY_ADVANCED__REGNAME,
            () -> new Battery(5000000,5000000));

    public static final RegistryObject<Item> ELITE_BATTERY = ITEMS.register(NuminaConstants.MODULE_BATTERY_ELITE__REGNAME,
            () -> new Battery(50000000,50000000));

    public static final RegistryObject<Item> ULTIMATE_BATTERY = ITEMS.register(NuminaConstants.MODULE_BATTERY_ULTIMATE__REGNAME,
            () -> new Battery(100000000,100000000));

    // Block Items --------------------------------------------------------------------------------
    // Charging base
    public static final RegistryObject<Item> CHARGING_BASE_ITEM = ITEMS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> new BlockItem(CHARGING_BASE_BLOCK.get(),
                    new Item.Properties().group(ItemGroup.DECORATIONS)));

    // Armor stand
    public static final RegistryObject<Item> ARMOR_STAND_ITEM = ITEMS.register(NuminaConstants.ARMORSTAND_REGNAME,
            () -> new MPAArmorStandItem(new Item.Properties().group(ItemGroup.DECORATIONS).setISTER(() -> MPAArmorStationItemRenderer::new)));


    /**
     * Tile Entity Types --------------------------------------------------------------------------
     */
    public static final DeferredRegister<TileEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, NuminaConstants.MOD_ID);

    public static final RegistryObject<TileEntityType<ChargingBaseTileEntity>> CHARGING_BASE_TILE = TILE_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> TileEntityType.Builder.create(ChargingBaseTileEntity::new, CHARGING_BASE_BLOCK.get()).build(null));


    /**
     * Entity Types -------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, NuminaConstants.MOD_ID);

    public static final RegistryObject<EntityType<MPAArmorStandEntity>> ARMOR_WORKSTATION__ENTITY_TYPE = ENTITY_TYPES.register(NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME,
            () -> EntityType.Builder.<MPAArmorStandEntity>create(MPAArmorStandEntity::new, EntityClassification.CREATURE)
                    .size(0.5F, 1.975F) // Hitbox Size
                    .build(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME).toString()));

    /**
     * Container Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, NuminaConstants.MOD_ID);

    public static final RegistryObject<ContainerType<ArmorStandContainer>> ARMOR_STAND_CONTAINER_TYPE = CONTAINER_TYPES.register("armorstand_modding_container",
            () -> IForgeContainerType.create((windowId, inv, data) -> {
                int entityID = data.readInt();
                Entity armorStand = inv.player.world.getEntityByID(entityID);

                if (armorStand instanceof ArmorStandEntity) {
                    return new ArmorStandContainer(windowId, inv, (ArmorStandEntity) armorStand);
                }
                return null;
            }));

    public static final RegistryObject<ContainerType<ChargingBaseContainer>> CHARGING_BASE_CONTAINER_TYPE = CONTAINER_TYPES.register("charging_base", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new ChargingBaseContainer(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<ContainerType<CraftingContainer>> CRAFTING_CONTAINER_TYPE = CONTAINER_TYPES.register("crafting_container",
            () -> IForgeContainerType.create((windowId, inv, data) -> {
//        BlockPos pos = data.readBlockPos();
                return new CraftingContainer(windowId, inv);
            }));
}