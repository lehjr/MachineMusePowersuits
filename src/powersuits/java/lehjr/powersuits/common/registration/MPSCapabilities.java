package lehjr.powersuits.common.registration;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.powersuits.common.capabilities.item.armor.PowerArmorEnergyWrapper;
import lehjr.powersuits.common.capabilities.item.armor.PowerArmorHeatWrapper;
import lehjr.powersuits.common.capabilities.item.armor.PowerArmorModularItemWrapper;
import lehjr.powersuits.common.capabilities.item.armor.PowerArmorRenderWrapper;
import lehjr.powersuits.common.capabilities.item.tool.PowerFistEnergyWrapper;
import lehjr.powersuits.common.capabilities.item.tool.PowerFistHeatWrapper;
import lehjr.powersuits.common.capabilities.item.tool.PowerFistModeChangingWrapper;
import lehjr.powersuits.common.capabilities.item.tool.PowerFistRenderWrapper;
import lehjr.powersuits.common.item.module.armor.ArmorPlatingModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.chopping.AxeModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.digging.ShovelModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.mining.PickaxeModule;
import lehjr.powersuits.common.item.module.tool.misc.FlintAndSteelModule;
import lehjr.powersuits.common.item.module.tool.misc.LeafBlowerModule;
import lehjr.powersuits.common.item.module.tool.misc.LuxCapacitorModule;
import lehjr.powersuits.common.item.module.vision.BinocularsModule;
import lehjr.powersuits.common.item.module.vision.NightVisionModule;
import lehjr.powersuits.common.item.module.weapon.*;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class MPSCapabilities {

    /**
     * Note that capabilities load before common config
     * @param event
     */
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        // Armor ----------------------------------------------------------------------------------
        event.registerItem(NuminaCapabilities.Inventory.MODULAR_ITEM, (stack, ctx)-> new PowerArmorModularItemWrapper(stack, 1),
                MPSItems.POWER_ARMOR_HELMET_1.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_1.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_1.get(),
                MPSItems.POWER_ARMOR_BOOTS_1.get());

        event.registerItem(NuminaCapabilities.Inventory.MODULAR_ITEM, (stack, ctx)-> new PowerArmorModularItemWrapper(stack, 2),
                MPSItems.POWER_ARMOR_HELMET_2.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_2.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_2.get(),
                MPSItems.POWER_ARMOR_BOOTS_2.get());

        event.registerItem(NuminaCapabilities.Inventory.MODULAR_ITEM, (stack, ctx)-> new PowerArmorModularItemWrapper(stack, 3),
                MPSItems.POWER_ARMOR_HELMET_3.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_3.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_3.get(),
                MPSItems.POWER_ARMOR_BOOTS_3.get());

        event.registerItem(NuminaCapabilities.Inventory.MODULAR_ITEM, (stack, ctx)-> new PowerArmorModularItemWrapper(stack, 4),
                MPSItems.POWER_ARMOR_HELMET_4.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_4.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_4.get(),
                MPSItems.POWER_ARMOR_BOOTS_4.get());

        // Energy
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new PowerArmorEnergyWrapper(stack),
                MPSItems.POWER_ARMOR_HELMET_1.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_1.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_1.get(),
                MPSItems.POWER_ARMOR_BOOTS_1.get(),

                MPSItems.POWER_ARMOR_HELMET_2.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_2.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_2.get(),
                MPSItems.POWER_ARMOR_BOOTS_2.get(),

                MPSItems.POWER_ARMOR_HELMET_3.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_3.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_3.get(),
                MPSItems.POWER_ARMOR_BOOTS_3.get(),

                MPSItems.POWER_ARMOR_HELMET_4.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_4.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_4.get(),
                MPSItems.POWER_ARMOR_BOOTS_4.get());

        // Heat
        event.registerItem(NuminaCapabilities.HEAT, (stack, context)-> new PowerArmorHeatWrapper(stack, 1),
                MPSItems.POWER_ARMOR_HELMET_1.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_1.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_1.get(),
                MPSItems.POWER_ARMOR_BOOTS_1.get());

        event.registerItem(NuminaCapabilities.HEAT, (stack, context)-> new PowerArmorHeatWrapper(stack, 2),
                MPSItems.POWER_ARMOR_HELMET_2.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_2.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_2.get(),
                MPSItems.POWER_ARMOR_BOOTS_2.get());

        event.registerItem(NuminaCapabilities.HEAT, (stack, context)-> new PowerArmorHeatWrapper(stack, 3),
                MPSItems.POWER_ARMOR_HELMET_3.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_3.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_3.get(),
                MPSItems.POWER_ARMOR_BOOTS_3.get());

        event.registerItem(NuminaCapabilities.HEAT, (stack, context)-> new PowerArmorHeatWrapper(stack, 4),
                MPSItems.POWER_ARMOR_HELMET_4.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_4.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_4.get(),
                MPSItems.POWER_ARMOR_BOOTS_4.get());

        // Render
        event.registerItem(NuminaCapabilities.RENDER, (stack, ctx) -> new PowerArmorRenderWrapper(stack),
                MPSItems.POWER_ARMOR_HELMET_1.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_1.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_1.get(),
                MPSItems.POWER_ARMOR_BOOTS_1.get(),

                MPSItems.POWER_ARMOR_HELMET_2.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_2.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_2.get(),
                MPSItems.POWER_ARMOR_BOOTS_2.get(),

                MPSItems.POWER_ARMOR_HELMET_3.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_3.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_3.get(),
                MPSItems.POWER_ARMOR_BOOTS_3.get(),

                MPSItems.POWER_ARMOR_HELMET_4.get(),
                MPSItems.POWER_ARMOR_CHESTPLATE_4.get(),
                MPSItems.POWER_ARMOR_LEGGINGS_4.get(),
                MPSItems.POWER_ARMOR_BOOTS_4.get());

        // Power Fist -------------------------------------------------------------------------------------------------
        // Mode Changing
        event.registerItem(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) ->
                        new PowerFistModeChangingWrapper(stack, 1), MPSItems.POWER_FIST_1.get());

        event.registerItem(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) ->
                new PowerFistModeChangingWrapper(stack, 2), MPSItems.POWER_FIST_2.get());

        event.registerItem(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) ->
                new PowerFistModeChangingWrapper(stack, 3), MPSItems.POWER_FIST_3.get());

        event.registerItem(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) ->
                new PowerFistModeChangingWrapper(stack, 4), MPSItems.POWER_FIST_4.get());

        // Energy
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, context) -> new PowerFistEnergyWrapper(stack),
                MPSItems.POWER_FIST_1.get(),
                MPSItems.POWER_FIST_2.get(),
                MPSItems.POWER_FIST_3.get(),
                MPSItems.POWER_FIST_4.get());

        // Heat
        event.registerItem(NuminaCapabilities.HEAT, (stack, context) ->
                new PowerFistHeatWrapper(stack, 1), MPSItems.POWER_FIST_1.get());

        event.registerItem(NuminaCapabilities.HEAT, (stack, context) ->
                new PowerFistHeatWrapper(stack, 2), MPSItems.POWER_FIST_2.get());

        event.registerItem(NuminaCapabilities.HEAT, (stack, context) ->
                new PowerFistHeatWrapper(stack, 3), MPSItems.POWER_FIST_3.get());

        event.registerItem(NuminaCapabilities.HEAT, (stack, context) ->
                new PowerFistHeatWrapper(stack, 4), MPSItems.POWER_FIST_4.get());

        // Render
        event.registerItem(NuminaCapabilities.RENDER, (stack, context) -> new PowerFistRenderWrapper(stack),
                MPSItems.POWER_FIST_1.get(),
                MPSItems.POWER_FIST_2.get(),
                MPSItems.POWER_FIST_3.get(),
                MPSItems.POWER_FIST_4.get());









        // Modules --------------------------------------------------------------------------------
        // Armor
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 1), MPSItems.IRON_PLATING_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 2), MPSItems.DIAMOND_PLATING_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 3), MPSItems.NETHERITE_PLATING_MODULE.get());


        // Cosmetic

//        // Debug (LOL)         // Test successful!!
//        event.registerItem(Capabilities.ItemHandler.ITEM, (stack, ctx)-> {
//            if (stack.is(MPSItems.DEBUG_ITEM.value())) {
////                var contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
//
//
//
////                int size = ModularItemArmorWrapper.getActualSize(stack, 1);
////                if(size == 0) {
////                    size = contents.getSlots();
////                }
////                //
////                var items = NonNullList.withSize(size, new ItemStack(NuminaObjects.ULTIMATE_BATTERY.get()));
////                NuminaLogger.logDebug("modularItem items size: " + items.size());
////                contents.copyInto(items);
////                return new ModularItemArmorWrapper(stack, 1, items);
//                return new ModularItem2(stack, 27);
////            }
//            return null;
//        }, MPSItems.DEBUG_ITEM.get());

        // Axe
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new AxeModule.BlockBreaker(stack, 1), MPSItems.STONE_AXE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new AxeModule.BlockBreaker(stack, 2), MPSItems.IRON_AXE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new AxeModule.BlockBreaker(stack, 3), MPSItems.DIAMOND_AXE_MODULE.value());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new AxeModule.BlockBreaker(stack, 4), MPSItems.NETHERITE_AXE_MODULE.get());

        // Pickaxe
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PickaxeModule.BlockBreaker(stack, 1), MPSItems.STONE_PICKAXE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PickaxeModule.BlockBreaker(stack, 2), MPSItems.IRON_PICKAXE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PickaxeModule.BlockBreaker(stack, 3), MPSItems.DIAMOND_PICKAXE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PickaxeModule.BlockBreaker(stack, 4), MPSItems.NETHERITE_PICKAXE_MODULE.get());

        //Shovel
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 1), MPSItems.STONE_SHOVEL_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 2), MPSItems.IRON_SHOVEL_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 3), MPSItems.DIAMOND_SHOVEL_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 4), MPSItems.NETHERITE_SHOVEL_MODULE.get());


        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new FlintAndSteelModule.RightClickie(stack), MPSItems.LEAF_BLOWER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LeafBlowerModule.RightClickie(stack), MPSItems.LEAF_BLOWER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LuxCapacitorModule.RightClickie(stack), MPSItems.LUX_CAPACITOR_MODULE.get());

        // Vision
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new BinocularsModule.BinocularCap(stack), MPSItems.BINOCULARS_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new NightVisionModule.Ticker(stack), MPSItems.BINOCULARS_MODULE.get());

        // Weapons
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new BladeLauncherModule.RightClickie(stack), MPSItems.BLADE_LAUNCHER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LightningModule.RightClickie(stack), MPSItems.LIGHTNING_SUMMONER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new MeleeAssistModule.PMCap(stack), MPSItems.MELEE_ASSIST_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PlasmaCannonModule.RightClickie(stack), MPSItems.PLASMA_CANNON_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new RailgunModule.Ticker(stack), MPSItems.RAILGUN_MODULE.get());
    }
}
