package com.lehjr.numina.common.registration;

import com.lehjr.numina.common.capabilities.heat.IHeatStorage;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
import com.lehjr.numina.common.capabilities.render.color.IColorTag;
import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.config.NuminaCommonConfig;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.item.Battery;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NuminaCapabilities {
    public static final class Inventory {
        public static final ItemCapability<IModularItem, Void> MODULAR_ITEM = ItemCapability.createVoid(create("modular_item"), IModularItem.class);
        public static final ItemCapability<IModeChangingItem, Void> MODE_CHANGING_MODULAR_ITEM = ItemCapability.createVoid(create("mode_changing_modular_tem"), IModeChangingItem.class);
    }

    public static final class Module {
        public static final ItemCapability<IPowerModule, Void> POWER_MODULE = ItemCapability.createVoid(create("powermodule"), IPowerModule.class);
        public static final ItemCapability<IOtherModItemsAsModules, Void> EXTERNAL_MOD_ITEMS_AS_MODULES = ItemCapability.createVoid(create("external_mod_items"), IOtherModItemsAsModules.class);
    }

    //    public static final ItemCapability<IHighlight, Void> HIGHLIGHT = ItemCapability.createVoid(create("highlight"), IHighlight.class);
//    public static final ItemCapability<IChameleon, Void> CHAMELEON = ItemCapability.createVoid(create("chameleon"), IChameleon.class);

    public static final ItemCapability<IModelSpec, Void> RENDER = ItemCapability.createVoid(create("render"), IModelSpec.class);

    public static final EntityCapability<IPlayerKeyStates, Void> PLAYER_KEYSTATES = EntityCapability.createVoid(create("keystates"), IPlayerKeyStates.class);

    public static final ItemCapability<IHeatStorage, Void> HEAT = ItemCapability.createVoid(create("heat"), IHeatStorage.class);

    public static final class ColorCap {
        public static final ItemCapability<IColorTag, Void> COLOR_ITEM = ItemCapability.createVoid(create("color"), IColorTag.class);
        public static final BlockCapability<IColorTag, Void> COLOR_BLOCK = BlockCapability.createVoid(create("color"), IColorTag.class);
    }

    @Nullable
    public static IModularItem getModularItemOrModeChangingCapability(ItemStack modularItem) {
        IModularItem cap = modularItem.getCapability(Inventory.MODE_CHANGING_MODULAR_ITEM);
        if (cap != null) {
            return cap;
        }
        return modularItem.getCapability(Inventory.MODULAR_ITEM);
    }

    // Note: path can only be lower case or underscore
    private static ResourceLocation create(String path) {
        return ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, path);
    }

    public static HolderLookup.Provider getProvider(@Nonnull Level level) {
        return level.registryAccess();
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Batteries --------------------------------------------------------------------------------------------------
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new ComponentEnergyStorage(stack, NuminaCodecs.ENERGY, NuminaCommonConfig.batteryMaxEnergy1, NuminaCommonConfig.batteryMaxTransfer1), NuminaItems.BATTERY_1.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new ComponentEnergyStorage(stack, NuminaCodecs.ENERGY, NuminaCommonConfig.batteryMaxEnergy2, NuminaCommonConfig.batteryMaxTransfer2), NuminaItems.BATTERY_2.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new ComponentEnergyStorage(stack, NuminaCodecs.ENERGY, NuminaCommonConfig.batteryMaxEnergy3, NuminaCommonConfig.batteryMaxTransfer3), NuminaItems.BATTERY_3.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new ComponentEnergyStorage(stack, NuminaCodecs.ENERGY, NuminaCommonConfig.batteryMaxEnergy4, NuminaCommonConfig.batteryMaxTransfer4), NuminaItems.BATTERY_4.get());

        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new Battery.BatteryPowerModule(stack, 1), NuminaItems.BATTERY_1.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new Battery.BatteryPowerModule(stack, 2), NuminaItems.BATTERY_2.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new Battery.BatteryPowerModule(stack, 3), NuminaItems.BATTERY_3.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new Battery.BatteryPowerModule(stack, 4), NuminaItems.BATTERY_4.get());

        // Blocks -----------------------------------------------------------------------------------------------------
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, NuminaBlockEntities.CHARGING_BASE_BLOCK_ENTITY.get(), (o, direction) -> o.getItemHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, NuminaBlockEntities.CHARGING_BASE_BLOCK_ENTITY.get(), (o, direction) -> o.getEnergyHandler());

        // Entities ---------------------------------------------------------------------------------------------------
        event.registerEntity(NuminaCapabilities.PLAYER_KEYSTATES, EntityType.PLAYER, (player, context)-> player.getData(NuminaCodecs.KEYSTATE_HANDLER));
    }










}
