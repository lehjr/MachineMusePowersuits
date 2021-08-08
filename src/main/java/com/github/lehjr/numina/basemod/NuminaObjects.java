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

package com.github.lehjr.numina.basemod;

import com.github.lehjr.numina.block.ChargingBaseBlock;
import com.github.lehjr.numina.client.render.item.NuminaArmorStandItemRenderer;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.container.ArmorStandContainer;
import com.github.lehjr.numina.container.ChargingBaseContainer;
import com.github.lehjr.numina.entity.NuminaArmorStandEntity;
import com.github.lehjr.numina.item.Battery;
import com.github.lehjr.numina.item.ItemComponent;
import com.github.lehjr.numina.item.NuminaArmorStandItem;
import com.github.lehjr.numina.tileentity.ChargingBaseTileEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.PlayerContainer;
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

import java.util.HashMap;
import java.util.Map;

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

//    // TEST ITEM TO BE REMOVED
//    public static final RegistryObject<Item> PLASMA_BALL = ITEMS.register("plasma_ball",
//            () -> new PlasmaBallTest());

    // Modules ------------------------------------------------------------------------------------
    // Energy Storage
    public static final RegistryObject<Item> BASIC_BATTERY = ITEMS.register(NuminaConstants.MODULE_BATTERY_BASIC__REGNAME,
            () -> new Battery(1000000, 1000000, 1));

    public static final RegistryObject<Item> ADVANCED_BATTERY = ITEMS.register(NuminaConstants.MODULE_BATTERY_ADVANCED__REGNAME,
            () -> new Battery(5000000,5000000, 2));

    public static final RegistryObject<Item> ELITE_BATTERY = ITEMS.register(NuminaConstants.MODULE_BATTERY_ELITE__REGNAME,
            () -> new Battery(50000000,50000000, 3));

    public static final RegistryObject<Item> ULTIMATE_BATTERY = ITEMS.register(NuminaConstants.MODULE_BATTERY_ULTIMATE__REGNAME,
            () -> new Battery(100000000,100000000, 4));

    // Block Items --------------------------------------------------------------------------------
    // Charging base
    public static final RegistryObject<Item> CHARGING_BASE_ITEM = ITEMS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> new BlockItem(CHARGING_BASE_BLOCK.get(),
                    new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

    // Armor stand
    public static final RegistryObject<Item> ARMOR_STAND_ITEM = ITEMS.register(NuminaConstants.ARMORSTAND_REGNAME,
            () -> new NuminaArmorStandItem(new Item.Properties().tab(ItemGroup.TAB_DECORATIONS).setISTER(() -> NuminaArmorStandItemRenderer::new)));


    /**
     * Tile Entity Types --------------------------------------------------------------------------
     */
    public static final DeferredRegister<TileEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, NuminaConstants.MOD_ID);

    public static final RegistryObject<TileEntityType<ChargingBaseTileEntity>> CHARGING_BASE_TILE = TILE_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> TileEntityType.Builder.of(ChargingBaseTileEntity::new, CHARGING_BASE_BLOCK.get()).build(null));


    /**
     * Entity Types -------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, NuminaConstants.MOD_ID);

    public static final RegistryObject<EntityType<NuminaArmorStandEntity>> ARMOR_WORKSTATION__ENTITY_TYPE = ENTITY_TYPES.register(NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME,
            () -> EntityType.Builder.<NuminaArmorStandEntity>of(NuminaArmorStandEntity::new, EntityClassification.CREATURE)
                    .sized(0.5F, 1.975F) // Hitbox Size
                    .build(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME).toString()));

    /**
     * Container Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, NuminaConstants.MOD_ID);

    public static final RegistryObject<ContainerType<ArmorStandContainer>> ARMOR_STAND_CONTAINER_TYPE = CONTAINER_TYPES.register("armorstand_modding_container",
            () -> IForgeContainerType.create((windowId, inv, data) -> {
                int entityID = data.readInt();
                Entity armorStand = inv.player.level.getEntity(entityID);

                if (armorStand instanceof ArmorStandEntity) {
                    return new ArmorStandContainer(windowId, inv, (ArmorStandEntity) armorStand);
                }
                return null;
            }));

    public static final RegistryObject<ContainerType<ChargingBaseContainer>> CHARGING_BASE_CONTAINER_TYPE = CONTAINER_TYPES.register("charging_base", () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.level;
        return new ChargingBaseContainer(windowId, world, pos, inv, inv.player);
    }));

    /** Container background icons */
    public static final Map<EquipmentSlotType, ResourceLocation> ARMOR_SLOT_TEXTURES = new HashMap<EquipmentSlotType, ResourceLocation>(){{
        put(EquipmentSlotType.HEAD, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET);
        put(EquipmentSlotType.CHEST, PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE);
        put(EquipmentSlotType.LEGS, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS);
        put(EquipmentSlotType.FEET, PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS);
        put(EquipmentSlotType.OFFHAND, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
        put(EquipmentSlotType.MAINHAND, NuminaConstants.WEAPON_SLOT_BACKGROUND); //FIXME: broken for slot rendering, actually crashes
    }};

    public static final Pair<ResourceLocation, ResourceLocation> getSlotBackground(EquipmentSlotType slotType) {
        switch (slotType) {
            case MAINHAND:
//                return Pair.of(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, ARMOR_SLOT_TEXTURES.get(slotType)); // FIXME: broken for slot rendering, actually crashes
                 return Pair.of(PlayerContainer.BLOCK_ATLAS, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
            default:
                return Pair.of(PlayerContainer.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES.get(slotType));
        }
    }

}