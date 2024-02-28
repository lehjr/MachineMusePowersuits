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

package lehjr.numina.common.base;

import lehjr.numina.common.block.ChargingBase;
import lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.container.ArmorStandMenu;
import lehjr.numina.common.container.ChargingBaseMenu;
import lehjr.numina.common.entity.NuminaArmorStand;
import lehjr.numina.common.item.Battery;
import lehjr.numina.common.item.ComponentItem;
import lehjr.numina.common.item.NuminaArmorStandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.nullness.qual.NonNull;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NuminaObjects {

    /**
     * Blocks -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> NUMINA_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NuminaConstants.MOD_ID);

    public static final RegistryObject<ChargingBase> CHARGING_BASE_BLOCK = NUMINA_BLOCKS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            ChargingBase::new);

    /**
     * Items --------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Item> NUMINA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuminaConstants.MOD_ID);

    // Components ---------------------------------------------------------------------------------
    public static final RegistryObject<Item> ARTIFICIAL_MUSCLE = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__ARTIFICIAL_MUSCLE__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> CARBON_MYOFIBER = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__CARBON_MYOFIBER__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> COMPUTER_CHIP = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__COMPUTER_CHIP__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> CONTROL_CIRCUIT = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__CONTROL_CIRCUIT__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> FIELD_EMITTER = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__FIELD_EMITTER__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> GLIDER_WING = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__GLIDER_WING__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> ION_THRUSTER = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__ION_THRUSTER__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> LASER_EMITTER = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__LASER_EMITTER__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> MAGNET = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__MAGNET__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> MYOFIBER_GEL = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__MYOFIBER_GEL__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> PARACHUTE = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__PARACHUTE__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> RUBBER_HOSE = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__RUBBER_HOSE__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> SERVO = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__SERVO__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> SOLAR_PANEL = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__SOLAR_PANEL__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> SOLENOID = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__SOLENOID__REGNAME,
            ComponentItem::new);
    public static final RegistryObject<Item> WIRING = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__WIRING__REGNAME,
            ComponentItem::new);

//    // TEST ITEM TO BE REMOVED
//    public static final RegistryObject<Item> PLASMA_BALL = ITEMS.register("plasma_ball",
//            () -> new PlasmaBallTest());

    // Modules ------------------------------------------------------------------------------------
    // Energy Storage
    public static final RegistryObject<Item> BASIC_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_BASIC__REGNAME,
            () -> new Battery(1000000, 1000000, 1));

    public static final RegistryObject<Item> ADVANCED_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ADVANCED__REGNAME,
            () -> new Battery(5000000,5000000, 2));

    public static final RegistryObject<Item> ELITE_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ELITE__REGNAME,
            () -> new Battery(50000000,50000000, 3));

    public static final RegistryObject<Item> ULTIMATE_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ULTIMATE__REGNAME,
            () -> new Battery(100000000,100000000, 4));

    // Block Items --------------------------------------------------------------------------------
    // Charging base
    public static final RegistryObject<Item> CHARGING_BASE_ITEM = NUMINA_ITEMS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> new BlockItem(CHARGING_BASE_BLOCK.get(),
                    new Item.Properties()));

    // Armor stand
    public static final RegistryObject<Item> ARMOR_STAND_ITEM = NUMINA_ITEMS.register(NuminaConstants.ARMORSTAND_REGNAME,
            () -> new NuminaArmorStandItem(new Item.Properties()));//.setISTER(() -> NuminaArmorStandItemRenderer::new)));


    /**
     * Block Entity Types --------------------------------------------------------------------------
     */
    public static final DeferredRegister<BlockEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NuminaConstants.MOD_ID);

    public static final RegistryObject<BlockEntityType<ChargingBaseBlockEntity>> CHARGING_BASE_BLOCK_ENTITY = TILE_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> BlockEntityType.Builder.of(ChargingBaseBlockEntity::new, CHARGING_BASE_BLOCK.get()).build(null));


    /**
     * Entity Types -------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NuminaConstants.MOD_ID);

    public static final RegistryObject<EntityType<NuminaArmorStand>> ARMOR_STAND__ENTITY_TYPE = ENTITY_TYPES.register(NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME,
            () -> EntityType.Builder.<NuminaArmorStand>of(NuminaArmorStand::new, MobCategory.CREATURE)
                    .sized(0.5F, 1.975F) // Hitbox Size
                    .build(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME).toString()));

    /**
     * AbstractContainerMenu Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, NuminaConstants.MOD_ID);

    public static final RegistryObject<MenuType<ArmorStandMenu>> ARMOR_STAND_CONTAINER_TYPE = MENU_TYPES.register("armorstand_modding_container",
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                int entityID = data.readInt();
                Entity armorStand = inv.player.level().getEntity(entityID);
                if (armorStand instanceof ArmorStand) {
                    return new ArmorStandMenu(windowId, inv, (ArmorStand) armorStand);
                }
                return null;
            }));

    public static final RegistryObject<MenuType<ChargingBaseMenu>> CHARGING_BASE_CONTAINER_TYPE = MENU_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new ChargingBaseMenu(windowId, pos, inv);
            }));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NuminaConstants.MOD_ID);
    public static final RegistryObject<CreativeModeTab> NUMINA_TAB = CREATIVE_MODE_TABS.register("creative.mode.tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(NuminaObjects.ARMOR_STAND_ITEM.get()))
                    .title(Component.translatable(NuminaConstants.CREATIVE_TAB_TRANSLATION_KEY))
                    .displayItems((parameters, output) -> {
                        // Components ------------------------------------------------------------------
                        output.accept(ARTIFICIAL_MUSCLE.get());
                        output.accept(CARBON_MYOFIBER.get());
                        output.accept(COMPUTER_CHIP.get());
                        output.accept(CONTROL_CIRCUIT.get());
                        output.accept(FIELD_EMITTER.get());
                        output.accept(GLIDER_WING.get());
                        output.accept(ION_THRUSTER.get());
                        output.accept(LASER_EMITTER.get());
                        output.accept(MAGNET.get());
                        output.accept(MYOFIBER_GEL.get());
                        output.accept(PARACHUTE.get());
                        output.accept(RUBBER_HOSE.get());
                        output.accept(SERVO.get());
                        output.accept(SOLAR_PANEL.get());
                        output.accept(SOLENOID.get());
                        output.accept(WIRING.get());

                        // Modules ------------------------------------------------------------------------------------
                        // Energy Storage
                        output.accept(BASIC_BATTERY.get());
                        output.accept(getBattery(BASIC_BATTERY.get()));

                        output.accept(ADVANCED_BATTERY.get());
                        output.accept(getBattery(ADVANCED_BATTERY.get()));

                        output.accept(ELITE_BATTERY.get());
                        output.accept(getBattery(ELITE_BATTERY.get()));

                        output.accept(ULTIMATE_BATTERY.get());
                        output.accept(getBattery(ULTIMATE_BATTERY.get()));

                        // Block Items --------------------------------------------------------------------------------
                        // Charging base
                        output.accept(CHARGING_BASE_ITEM.get());
                        output.accept(ARMOR_STAND_ITEM.get());
                    })
                    .build());

    @NonNull
    static ItemStack getBattery(Item item) {
        ItemStack out = new ItemStack(item);
        out.getCapability(ForgeCapabilities.ENERGY).ifPresent(energy->{
            int max = energy.getMaxEnergyStored();
            energy.receiveEnergy(max, false);
        });
        return out;
    }

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}