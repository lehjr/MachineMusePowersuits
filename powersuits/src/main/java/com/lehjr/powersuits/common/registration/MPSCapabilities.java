package com.lehjr.powersuits.common.registration;

import com.lehjr.numina.common.capabilities.energy.ModularItemEnergyWrapper;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.render.hud.HudModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.powersuits.common.capabilities.item.armor.PowerArmorHeatWrapper;
import com.lehjr.powersuits.common.capabilities.item.armor.PowerArmorModularItemWrapper;
import com.lehjr.powersuits.common.capabilities.item.armor.PowerArmorRenderWrapper;
import com.lehjr.powersuits.common.capabilities.item.tool.PowerFistHeatWrapper;
import com.lehjr.powersuits.common.capabilities.item.tool.PowerFistModeChangingWrapper;
import com.lehjr.powersuits.common.capabilities.item.tool.PowerFistRenderWrapper;
import com.lehjr.powersuits.common.config.PowerFistConfig;
import com.lehjr.powersuits.common.item.module.armor.ArmorPlatingModule;
import com.lehjr.powersuits.common.item.module.armor.EnergyShieldModule;
import com.lehjr.powersuits.common.item.module.cosmetic.TransparentArmorModule;
import com.lehjr.powersuits.common.item.module.environmental.AutoFeederModule;
import com.lehjr.powersuits.common.item.module.environmental.MobRepulsorModule;
import com.lehjr.powersuits.common.item.module.environmental.WaterElectrolyzerModule;
import com.lehjr.powersuits.common.item.module.miningenchantment.AquaAffinityModule;
import com.lehjr.powersuits.common.item.module.miningenchantment.FortuneModule;
import com.lehjr.powersuits.common.item.module.miningenchantment.SilkTouchModule;
import com.lehjr.powersuits.common.item.module.miningenhancement.SelectiveMiner;
import com.lehjr.powersuits.common.item.module.miningenhancement.TunnelBoreModule;
import com.lehjr.powersuits.common.item.module.movement.BlinkDriveModule;
import com.lehjr.powersuits.common.item.module.movement.ClimbAssistModule;
import com.lehjr.powersuits.common.item.module.movement.DimensionalRiftModule;
import com.lehjr.powersuits.common.item.module.movement.FlightControlModule;
import com.lehjr.powersuits.common.item.module.movement.GliderModule;
import com.lehjr.powersuits.common.item.module.movement.JetBootsModule;
import com.lehjr.powersuits.common.item.module.movement.JetPackModule;
import com.lehjr.powersuits.common.item.module.movement.JumpAssistModule;
import com.lehjr.powersuits.common.item.module.movement.ParachuteModule;
import com.lehjr.powersuits.common.item.module.movement.ShockAbsorberModule;
import com.lehjr.powersuits.common.item.module.movement.SprintAssistModule;
import com.lehjr.powersuits.common.item.module.movement.SwimAssistModule;
import com.lehjr.powersuits.common.item.module.tool.blockbreaking.AxeModule;
import com.lehjr.powersuits.common.item.module.tool.blockbreaking.ShovelModule;
import com.lehjr.powersuits.common.item.module.tool.blockbreaking.HoeModule;
import com.lehjr.powersuits.common.item.module.tool.blockbreaking.PickaxeModule;
import com.lehjr.powersuits.common.item.module.tool.misc.FlintAndSteelModule;
import com.lehjr.powersuits.common.item.module.tool.misc.LeafBlowerModule;
import com.lehjr.powersuits.common.item.module.tool.misc.LuxCapacitorModule;
import com.lehjr.powersuits.common.item.module.tool.misc.ShearsModule;
import com.lehjr.powersuits.common.item.module.vision.BinocularsModule;
import com.lehjr.powersuits.common.item.module.vision.NightVisionModule;
import com.lehjr.powersuits.common.item.module.weapon.BladeLauncherModule;
import com.lehjr.powersuits.common.item.module.weapon.LightningModule;
import com.lehjr.powersuits.common.item.module.weapon.MeleeAssistModule;
import com.lehjr.powersuits.common.item.module.weapon.PlasmaCannonModule;
import com.lehjr.powersuits.common.item.module.weapon.RailgunModule;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class MPSCapabilities {

    /**
     * Note that capabilities load before common config
     * @param event
     */
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Block Entity ===============================================================================================
        // Todo?

        // Armor ======================================================================================================
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
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new ModularItemEnergyWrapper(stack),
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

        // Fluid
        // Todo: chestplate wrappers


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

        // Power Fist =================================================================================================
        // Mode Changing
        if(PowerFistConfig.MPS_POWER_FIST_CONFIG_SPEC.isLoaded()) {
            event.registerItem(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) -> new PowerFistModeChangingWrapper(stack, 1, PowerFistConfig.powerFistInventorySlots1), MPSItems.POWER_FIST_1.get());
            event.registerItem(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) -> new PowerFistModeChangingWrapper(stack, 2, PowerFistConfig.powerFistInventorySlots2), MPSItems.POWER_FIST_2.get());
            event.registerItem(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) -> new PowerFistModeChangingWrapper(stack, 3, PowerFistConfig.powerFistInventorySlots3), MPSItems.POWER_FIST_3.get());
            event.registerItem(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) -> new PowerFistModeChangingWrapper(stack, 4, PowerFistConfig.powerFistInventorySlots4), MPSItems.POWER_FIST_4.get());

            // Energy
            event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, context) -> new ModularItemEnergyWrapper(stack),
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
        }



        // Render
        event.registerItem(NuminaCapabilities.RENDER, (stack, context) -> new PowerFistRenderWrapper(stack),
                MPSItems.POWER_FIST_1.get(),
                MPSItems.POWER_FIST_2.get(),
                MPSItems.POWER_FIST_3.get(),
                MPSItems.POWER_FIST_4.get());

        // Modules ====================================================================================================
        // Armor ------------------------------------------------------------------------------------------------------
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 1), MPSItems.IRON_PLATING_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 2), MPSItems.DIAMOND_PLATING_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 3), MPSItems.NETHERITE_PLATING_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new EnergyShieldModule.EnergyShieldCapabilityWrapper(stack), MPSItems.ENERGY_SHIELD_MODULE.get());

        //Cosmetic ----------------------------------------------------------------------------------------------------
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new TransparentArmorModule.TransParentArmorCap(stack), MPSItems.TRANSPARENT_ARMOR_MODULE.get());

        // Debug ------------------------------------------------------------------------------------------------------


        // Energy Generation ------------------------------------------------------------------------------------------
        // TODO

        // Environmental ----------------------------------------------------------------------------------------------
        // TODO: COOLING
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new AutoFeederModule.Ticker(stack), MPSItems.AUTO_FEEDER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new MobRepulsorModule.Ticker(stack), MPSItems.MOB_REPULSOR_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new WaterElectrolyzerModule.Ticker(stack), MPSItems.WATER_ELECTROLYZER_MODULE.get());

        // Mining Enchantment -----------------------------------------------------------------------------------------
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new AquaAffinityModule.TickingEnchantment(stack), MPSItems.AQUA_AFFINITY_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new FortuneModule.TickingEnchantment(stack), MPSItems.FORTUNE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new SilkTouchModule.TickingEnchantment(stack), MPSItems.SILK_TOUCH_MODULE.get());

        // Mining Enhancement -----------------------------------------------------------------------------------------
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new SelectiveMiner.Enhancement(stack), MPSItems.SELECTIVE_MINER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new TunnelBoreModule.Enhancement(stack), MPSItems.TUNNEL_BORE_MODULE.get());
//        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new VeinMinerModule.Enhancement(stack), MPSItems.TUNNEL_BORE_MODULE.get());

        // Vein Miner



        // Special
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new HudModule(stack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, true), Items.CLOCK);
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new HudModule(stack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, true), Items.COMPASS);
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new HudModule(stack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, true), Items.RECOVERY_COMPASS);




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





        // Movement -------------------------------------------------------------------------------
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new BlinkDriveModule.RightClickie(stack), MPSItems.BLINK_DRIVE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ClimbAssistModule.Ticker(stack), MPSItems.CLIMB_ASSIST_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new DimensionalRiftModule.RightClickie(stack), MPSItems.DIMENSIONAL_RIFT_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new FlightControlModule.Toggler(stack), MPSItems.FLIGHT_CONTROL_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new GliderModule.Ticker(stack), MPSItems.GLIDER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new JetBootsModule.Ticker(stack), MPSItems.JET_BOOTS_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new JetPackModule.Ticker(stack), MPSItems.JETPACK_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new JumpAssistModule.Ticker(stack), MPSItems.JUMP_ASSIST_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ParachuteModule.Ticker(stack), MPSItems.PARACHUTE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShockAbsorberModule.Toggler(stack), MPSItems.SHOCK_ABSORBER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new SprintAssistModule.Ticker(stack), MPSItems.SPRINT_ASSIST_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new SwimAssistModule.Ticker(stack), MPSItems.SWIM_ASSIST_MODULE.get());

        // Tools ----------------------------------------------------------------------------------
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

        // Rototiller
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new HoeModule.RightClickie(stack, 1), MPSItems.STONE_HOE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new HoeModule.RightClickie(stack, 2), MPSItems.IRON_HOE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new HoeModule.RightClickie(stack, 3), MPSItems.DIAMOND_HOE_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new HoeModule.RightClickie(stack, 4), MPSItems.NETHERITE_HOE_MODULE.get());

        //Shovel
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 1), MPSItems.STONE_SHOVEL_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 2), MPSItems.IRON_SHOVEL_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 3), MPSItems.DIAMOND_SHOVEL_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 4), MPSItems.NETHERITE_SHOVEL_MODULE.get());




        // Misc
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new FlintAndSteelModule.RightClickie(stack), MPSItems.FLINT_AND_STEEL_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LeafBlowerModule.RightClickie(stack), MPSItems.LEAF_BLOWER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LuxCapacitorModule.RightClickie(stack), MPSItems.LUX_CAPACITOR_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShearsModule.BlockBreaker(stack), MPSItems.LUX_CAPACITOR_MODULE.get());

        // Vision ---------------------------------------------------------------------------------
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new BinocularsModule.BinocularCap(stack), MPSItems.BINOCULARS_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new NightVisionModule.Ticker(stack), MPSItems.NIGHTVISION_MODULE.get());

        // Weapons --------------------------------------------------------------------------------
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new BladeLauncherModule.RightClickie(stack), MPSItems.BLADE_LAUNCHER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LightningModule.RightClickie(stack), MPSItems.LIGHTNING_SUMMONER_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new MeleeAssistModule.PMCap(stack), MPSItems.MELEE_ASSIST_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PlasmaCannonModule.RightClickie(stack), MPSItems.PLASMA_CANNON_MODULE.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new RailgunModule.Ticker(stack), MPSItems.RAILGUN_MODULE.get());
    }
}
