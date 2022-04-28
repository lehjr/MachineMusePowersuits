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
package com.lehjr.numina.common;

import com.lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import com.lehjr.numina.common.menu.ArmorStandMenu;
import com.lehjr.numina.common.menu.ChargingBaseMenu;
import com.lehjr.numina.common.block.ChargingBase;
import com.lehjr.numina.common.entity.NuminaArmorStand;
import com.lehjr.numina.common.item.Battery;
import com.lehjr.numina.common.item.ComponentItem;
import com.lehjr.numina.common.item.NuminaArmorStandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.lehjr.numina.api.constants.NuminaConstants;
import com.lehjr.numina.api.constants.NuminaRegistryNames;

public class NuminaObjects {
    public static final NuminaCreativeTab creativeTab = new NuminaCreativeTab();

    /**
     * Blocks -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NuminaConstants.MOD_ID);

    public static final RegistryObject<ChargingBase> CHARGING_BASE_BLOCK = BLOCKS.register(NuminaRegistryNames.CHARGING_BASE_REGNAME.getPath(),
            () -> new ChargingBase());

    /**
     * Items --------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuminaConstants.MOD_ID);

    // Components ---------------------------------------------------------------------------------
    public static final RegistryObject<Item> WIRING = ITEMS.register(NuminaRegistryNames.COMPONENT__WIRING__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> SOLENOID = ITEMS.register(NuminaRegistryNames.COMPONENT__SOLENOID__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> SERVO = ITEMS.register(NuminaRegistryNames.COMPONENT__SERVO__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> GLIDER_WING = ITEMS.register(NuminaRegistryNames.COMPONENT__GLIDER_WING__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> ION_THRUSTER = ITEMS.register(NuminaRegistryNames.COMPONENT__ION_THRUSTER__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> PARACHUTE = ITEMS.register(NuminaRegistryNames.COMPONENT__PARACHUTE__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> FIELD_EMITTER = ITEMS.register(NuminaRegistryNames.COMPONENT__FIELD_EMITTER__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> LASER_EMITTER = ITEMS.register(NuminaRegistryNames.COMPONENT__LASER_EMITTER__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> CARBON_MYOFIBER = ITEMS.register(NuminaRegistryNames.COMPONENT__CARBON_MYOFIBER__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> CONTROL_CIRCUIT_BASIC = ITEMS.register(NuminaRegistryNames.COMPONENT__CONTROL_CIRCUIT_BASIC__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> CONTROL_CIRCUIT_ADVANCED = ITEMS.register(NuminaRegistryNames.COMPONENT__CONTROL_CIRCUIT_ADVANCED__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> CONTROL_CIRCUIT_ELITE = ITEMS.register(NuminaRegistryNames.COMPONENT__CONTROL_CIRCUIT_ELITE__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> CONTROL_CIRCUIT_ULTIMATE = ITEMS.register(NuminaRegistryNames.COMPONENT__CONTROL_CIRCUIT_ULTIMATE__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> MYOFIBER_GEL = ITEMS.register(NuminaRegistryNames.COMPONENT__MYOFIBER_GEL__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> ARTIFICIAL_MUSCLE = ITEMS.register(NuminaRegistryNames.COMPONENT__ARTIFICIAL_MUSCLE__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> SOLAR_PANEL = ITEMS.register(NuminaRegistryNames.COMPONENT__SOLAR_PANEL__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> MAGNET = ITEMS.register(NuminaRegistryNames.COMPONENT__MAGNET__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> COMPUTER_CHIP = ITEMS.register(NuminaRegistryNames.COMPONENT__COMPUTER_CHIP__REGNAME.getPath(),
            () -> new ComponentItem());

    public static final RegistryObject<Item> RUBBER_HOSE = ITEMS.register(NuminaRegistryNames.COMPONENT__RUBBER_HOSE__REGNAME.getPath(),
            () -> new ComponentItem());

    // Modules ------------------------------------------------------------------------------------
    // Energy Storage
    public static final RegistryObject<Item> BASIC_BATTERY = ITEMS.register(NuminaRegistryNames.MODULE_BATTERY_BASIC__REGNAME.getPath(),
            () -> new Battery(1000000, 1000000, 1));

    public static final RegistryObject<Item> ADVANCED_BATTERY = ITEMS.register(NuminaRegistryNames.MODULE_BATTERY_ADVANCED__REGNAME.getPath(),
            () -> new Battery(5000000,5000000, 2));

    public static final RegistryObject<Item> ELITE_BATTERY = ITEMS.register(NuminaRegistryNames.MODULE_BATTERY_ELITE__REGNAME.getPath(),
            () -> new Battery(50000000,50000000, 3));

    public static final RegistryObject<Item> ULTIMATE_BATTERY = ITEMS.register(NuminaRegistryNames.MODULE_BATTERY_ULTIMATE__REGNAME.getPath(),
            () -> new Battery(100000000,100000000, 4));


    // Block Items --------------------------------------------------------------------------------
    // Charging base
    public static final RegistryObject<Item> CHARGING_BASE_ITEM = ITEMS.register(NuminaRegistryNames.CHARGING_BASE_REGNAME.getPath(),
            () -> new BlockItem(CHARGING_BASE_BLOCK.get(),
                    new Item.Properties().tab(creativeTab)));

    // Armor stand
    public static final RegistryObject<Item> ARMOR_STAND_ITEM = ITEMS.register(NuminaRegistryNames.ARMORSTAND_REGNAME.getPath(),
            () -> new NuminaArmorStandItem(new Item.Properties().tab(creativeTab)));//;.setISTER(() -> NuminaArmorStandItemRenderer::new)));

    /**
     * Tile Entity Types --------------------------------------------------------------------------
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, NuminaConstants.MOD_ID);

    public static final RegistryObject<BlockEntityType<ChargingBaseBlockEntity>> CHARGING_BASE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register(NuminaRegistryNames.CHARGING_BASE_REGNAME.getPath(),
            () -> BlockEntityType.Builder.of(ChargingBaseBlockEntity::new, CHARGING_BASE_BLOCK.get()).build(null));

    /**
     * Entity Types -------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, NuminaConstants.MOD_ID);

    public static final RegistryObject<EntityType<NuminaArmorStand>> ARMOR_STAND__ENTITY_TYPE = ENTITY_TYPES.register(NuminaRegistryNames.ARMOR_STAND__ENTITY_TYPE_REGNAME.getPath(),
            () -> EntityType.Builder.<NuminaArmorStand>of(NuminaArmorStand::new, MobCategory.CREATURE)
                    .sized(0.5F, 1.975F) // Hitbox Size
                    .build(NuminaRegistryNames.ARMOR_STAND__ENTITY_TYPE_REGNAME.toString()));


    /**
     * Container Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, NuminaConstants.MOD_ID);

    public static final RegistryObject<MenuType<ArmorStandMenu>> ARMOR_STAND_CONTAINER_TYPE = MENU_TYPES.register("armorstand_modding_container",
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                int entityID = data.readInt();
                Entity armorStand = inv.player.level.getEntity(entityID);

                if (armorStand instanceof ArmorStand) {
                    return new ArmorStandMenu(windowId, inv, (ArmorStand) armorStand);
                }
                return null;
            }));

    // FIXME!!
    public static final RegistryObject<MenuType<ChargingBaseMenu>> CHARGING_BASE_CONTAINER_TYPE = MENU_TYPES.register("charging_base", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
//        Level level = inv.player.level;
        return new ChargingBaseMenu(windowId, inv, pos);
    }));
// TODO
//    public static final RegistryObject<ContainerType<MPSContainerScanner>> SCANNER_CONTAINER = MENU_TYPES.register(Constants.NAME_SCANNER, () -> IForgeContainerType.create(MPSContainerScanner::createForClient));

}
