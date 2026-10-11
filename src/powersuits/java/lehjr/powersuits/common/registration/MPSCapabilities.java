package lehjr.powersuits.common.registration;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.energy.ModularItemEnergyWrapper;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.externalitems.OtherModItemsAsModules;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.common.capabilities.render.color.ColorAttachmentStorage;
import lehjr.numina.common.capabilities.render.hud.HudModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.powersuits.common.capabilities.armor.PowerArmorFluidWrapper;
import lehjr.powersuits.common.capabilities.armor.PowerArmorHeatWrapper;
import lehjr.powersuits.common.capabilities.armor.PowerArmorModularItemWrapper;
import lehjr.powersuits.common.capabilities.armor.PowerArmorRenderWrapper;
import lehjr.powersuits.common.capabilities.module.PortableCraftingCapability;
import lehjr.powersuits.common.capabilities.module.TinkerTableCap;
import lehjr.powersuits.common.capabilities.tool.PowerFistHeatWrapper;
import lehjr.powersuits.common.capabilities.tool.PowerFistModeChangingWrapper;
import lehjr.powersuits.common.capabilities.tool.PowerFistRenderWrapper;
import lehjr.powersuits.common.config.PowerFistConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.armor.ArmorPlatingModule;
import lehjr.powersuits.common.item.module.armor.EnergyShieldModule;
import lehjr.powersuits.common.item.module.cosmetic.TransparentArmorModule;
import lehjr.powersuits.common.item.module.energygeneration.heat.ThermalGeneratorModule;
import lehjr.powersuits.common.item.module.energygeneration.solar.SolarGeneratorModule;
import lehjr.powersuits.common.item.module.environmental.ActiveCamouflageModule;
import lehjr.powersuits.common.item.module.environmental.AutoFeederModule;
import lehjr.powersuits.common.item.module.environmental.MagnetModule;
import lehjr.powersuits.common.item.module.environmental.MobRepulsorModule;
import lehjr.powersuits.common.item.module.environmental.PiglinPacificationModule;
import lehjr.powersuits.common.item.module.environmental.WaterElectrolyzerModule;
import lehjr.powersuits.common.item.module.fluidstorage.CoolantTankModule;
import lehjr.powersuits.common.item.module.miningenchantment.AquaAffinityModule;
import lehjr.powersuits.common.item.module.miningenchantment.FortuneModule;
import lehjr.powersuits.common.item.module.miningenchantment.SilkTouchModule;
import lehjr.powersuits.common.item.module.miningenhancement.SelectiveMiner;
import lehjr.powersuits.common.item.module.miningenhancement.TunnelBoreModule;
import lehjr.powersuits.common.item.module.miningenhancement.VeinMinerModule;
import lehjr.powersuits.common.item.module.movement.BlinkDriveModule;
import lehjr.powersuits.common.item.module.movement.ClimbAssistModule;
import lehjr.powersuits.common.item.module.movement.DimensionalRiftModule;
import lehjr.powersuits.common.item.module.movement.FlightControlModule;
import lehjr.powersuits.common.item.module.movement.GliderModule;
import lehjr.powersuits.common.item.module.movement.JetBootsModule;
import lehjr.powersuits.common.item.module.movement.JetPackModule;
import lehjr.powersuits.common.item.module.movement.JumpAssistModule;
import lehjr.powersuits.common.item.module.movement.ParachuteModule;
import lehjr.powersuits.common.item.module.movement.ShockAbsorberModule;
import lehjr.powersuits.common.item.module.movement.SprintAssistModule;
import lehjr.powersuits.common.item.module.movement.SwimAssistModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.AxeModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.HoeModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.PickaxeModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.ShearsModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.ShovelModule;
import lehjr.powersuits.common.item.module.tool.misc.FlintAndSteelModule;
import lehjr.powersuits.common.item.module.tool.misc.LeafBlowerModule;
import lehjr.powersuits.common.item.module.tool.misc.LuxCapacitorModule;
import lehjr.powersuits.common.item.module.vision.BinocularsModule;
import lehjr.powersuits.common.item.module.vision.NightVisionModule;
import lehjr.powersuits.common.item.module.weapon.BladeLauncherModule;
import lehjr.powersuits.common.item.module.weapon.LightningModule;
import lehjr.powersuits.common.item.module.weapon.MeleeAssistModule;
import lehjr.powersuits.common.item.module.weapon.PlasmaCannonModule;
import lehjr.powersuits.common.item.module.weapon.RailgunModule;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MPSCapabilities {
    public static List<ItemLike> toggleableModules = new ArrayList<>();
    /**
     * Note that capabilities load before common config
     * @param event
     */
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Block Entity ===============================================================================================
        event.registerBlockEntity(NuminaCapabilities.ColorCap.COLOR_BLOCK, MPSBlocks.LUX_CAP_BLOCK_ENTITY_TYPE.get(), (o, direction)-> new ColorAttachmentStorage(o));

        // Armor ======================================================================================================
        registerItem(event, NuminaCapabilities.Inventory.MODULAR_ITEM, (stack, ctx)-> new PowerArmorModularItemWrapper(stack, 1),
            MPSItems.POWER_ARMOR_HELMET_1.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_1.get(),
            MPSItems.POWER_ARMOR_LEGGINGS_1.get(),
            MPSItems.POWER_ARMOR_BOOTS_1.get());

        registerItem(event, NuminaCapabilities.Inventory.MODULAR_ITEM, (stack, ctx)-> new PowerArmorModularItemWrapper(stack, 2),
            MPSItems.POWER_ARMOR_HELMET_2.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_2.get(),
            MPSItems.POWER_ARMOR_LEGGINGS_2.get(),
            MPSItems.POWER_ARMOR_BOOTS_2.get());

        registerItem(event, NuminaCapabilities.Inventory.MODULAR_ITEM, (stack, ctx)-> new PowerArmorModularItemWrapper(stack, 3),
            MPSItems.POWER_ARMOR_HELMET_3.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_3.get(),
            MPSItems.POWER_ARMOR_LEGGINGS_3.get(),
            MPSItems.POWER_ARMOR_BOOTS_3.get());

        registerItem(event, NuminaCapabilities.Inventory.MODULAR_ITEM, (stack, ctx)-> new PowerArmorModularItemWrapper(stack, 4),
            MPSItems.POWER_ARMOR_HELMET_4.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_4.get(),
            MPSItems.POWER_ARMOR_LEGGINGS_4.get(),
            MPSItems.POWER_ARMOR_BOOTS_4.get());

        // Energy
        registerItem(event, Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new ModularItemEnergyWrapper(stack),
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
        registerItem(event, NuminaCapabilities.HEAT, (stack, context)-> new PowerArmorHeatWrapper(stack, 1),
            MPSItems.POWER_ARMOR_HELMET_1.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_1.get(),
            MPSItems.POWER_ARMOR_LEGGINGS_1.get(),
            MPSItems.POWER_ARMOR_BOOTS_1.get());

        registerItem(event, NuminaCapabilities.HEAT, (stack, context)-> new PowerArmorHeatWrapper(stack, 2),
            MPSItems.POWER_ARMOR_HELMET_2.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_2.get(),
            MPSItems.POWER_ARMOR_LEGGINGS_2.get(),
            MPSItems.POWER_ARMOR_BOOTS_2.get());

        registerItem(event, NuminaCapabilities.HEAT, (stack, context)-> new PowerArmorHeatWrapper(stack, 3),
            MPSItems.POWER_ARMOR_HELMET_3.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_3.get(),
            MPSItems.POWER_ARMOR_LEGGINGS_3.get(),
            MPSItems.POWER_ARMOR_BOOTS_3.get());

        registerItem(event, NuminaCapabilities.HEAT, (stack, context)-> new PowerArmorHeatWrapper(stack, 4),
            MPSItems.POWER_ARMOR_HELMET_4.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_4.get(),
            MPSItems.POWER_ARMOR_LEGGINGS_4.get(),
            MPSItems.POWER_ARMOR_BOOTS_4.get());

        // Fluid
        registerItem(event, Capabilities.FluidHandler.ITEM, (stack, context) -> new PowerArmorFluidWrapper(stack),
            MPSItems.POWER_ARMOR_CHESTPLATE_1.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_2.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_3.get(),
            MPSItems.POWER_ARMOR_CHESTPLATE_4.get());

        registerItem(event, Capabilities.FluidHandler.ITEM, (stack, context) ->new CoolantTankModule.FluidHandlerItemStackMPS(stack, 1), MPSItems.COOLANT_TANK_MODULE_1.get());
        registerItem(event, Capabilities.FluidHandler.ITEM, (stack, context) ->new CoolantTankModule.FluidHandlerItemStackMPS(stack, 2), MPSItems.COOLANT_TANK_MODULE_2.get());
        registerItem(event, Capabilities.FluidHandler.ITEM, (stack, context) ->new CoolantTankModule.FluidHandlerItemStackMPS(stack, 3), MPSItems.COOLANT_TANK_MODULE_3.get());
        registerItem(event, Capabilities.FluidHandler.ITEM, (stack, context) ->new CoolantTankModule.FluidHandlerItemStackMPS(stack, 4), MPSItems.COOLANT_TANK_MODULE_4.get());

        // Render
        registerItem(event, NuminaCapabilities.RENDER, (stack, ctx) -> new PowerArmorRenderWrapper(stack),
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
        if(PowerFistConfig.POWER_FIST_CONFIG_SPEC.isLoaded()) {
            registerItem(event, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) -> new PowerFistModeChangingWrapper(stack, 1, PowerFistConfig.powerFistInventorySlots1), MPSItems.POWER_FIST_1.get());
            registerItem(event, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) -> new PowerFistModeChangingWrapper(stack, 2, PowerFistConfig.powerFistInventorySlots2), MPSItems.POWER_FIST_2.get());
            registerItem(event, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) -> new PowerFistModeChangingWrapper(stack, 3, PowerFistConfig.powerFistInventorySlots3), MPSItems.POWER_FIST_3.get());
            registerItem(event, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM, (stack, context) -> new PowerFistModeChangingWrapper(stack, 4, PowerFistConfig.powerFistInventorySlots4), MPSItems.POWER_FIST_4.get());

            // Energy
            registerItem(event, Capabilities.EnergyStorage.ITEM, (stack, context) -> new ModularItemEnergyWrapper(stack),
                MPSItems.POWER_FIST_1.get(),
                MPSItems.POWER_FIST_2.get(),
                MPSItems.POWER_FIST_3.get(),
                MPSItems.POWER_FIST_4.get());

            // Heat
            registerItem(event, NuminaCapabilities.HEAT, (stack, context) ->
                new PowerFistHeatWrapper(stack, 1), MPSItems.POWER_FIST_1.get());

            registerItem(event, NuminaCapabilities.HEAT, (stack, context) ->
                new PowerFistHeatWrapper(stack, 2), MPSItems.POWER_FIST_2.get());

            registerItem(event, NuminaCapabilities.HEAT, (stack, context) ->
                new PowerFistHeatWrapper(stack, 3), MPSItems.POWER_FIST_3.get());

            registerItem(event, NuminaCapabilities.HEAT, (stack, context) ->
                new PowerFistHeatWrapper(stack, 4), MPSItems.POWER_FIST_4.get());
        }

        // Render
        registerItem(event, NuminaCapabilities.RENDER, (stack, context) -> new PowerFistRenderWrapper(stack),
            MPSItems.POWER_FIST_1.get(),
            MPSItems.POWER_FIST_2.get(),
            MPSItems.POWER_FIST_3.get(),
            MPSItems.POWER_FIST_4.get());

        // Modules ====================================================================================================
        // Armor ------------------------------------------------------------------------------------------------------
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 1), MPSItems.IRON_PLATING_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 2), MPSItems.DIAMOND_PLATING_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ArmorPlatingModule.ArmorPlatingCapabilityWrapper(stack, 3), MPSItems.NETHERITE_PLATING_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new EnergyShieldModule.EnergyShieldCapabilityWrapper(stack), MPSItems.ENERGY_SHIELD_MODULE.get());

        //Cosmetic ----------------------------------------------------------------------------------------------------
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new TransparentArmorModule.TransParentArmorCap(stack), MPSItems.TRANSPARENT_ARMOR_MODULE.get());

        // Debug ------------------------------------------------------------------------------------------------------


        // Energy Generation ------------------------------------------------------------------------------------------

        // Thermal
        //FIXME!!!
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ThermalGeneratorModule.ThermalGeneratorTickingCapability(stack, 1), MPSItems.THERMAL_GENERATOR_MODULE_1.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ThermalGeneratorModule.ThermalGeneratorTickingCapability(stack, 2), MPSItems.THERMAL_GENERATOR_MODULE_2.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ThermalGeneratorModule.ThermalGeneratorTickingCapability(stack, 3), MPSItems.THERMAL_GENERATOR_MODULE_3.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ThermalGeneratorModule.ThermalGeneratorTickingCapability(stack, 4), MPSItems.THERMAL_GENERATOR_MODULE_4.get());

        // Solar
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new SolarGeneratorModule.SolarGeneratorTickingCapability(stack, 1), MPSItems.SOLAR_GENERATOR_MODULE_1.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new SolarGeneratorModule.SolarGeneratorTickingCapability(stack, 2), MPSItems.SOLAR_GENERATOR_MODULE_2.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new SolarGeneratorModule.SolarGeneratorTickingCapability(stack, 3), MPSItems.SOLAR_GENERATOR_MODULE_3.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new SolarGeneratorModule.SolarGeneratorTickingCapability(stack, 4), MPSItems.SOLAR_GENERATOR_MODULE_4.get());


        // Environmental ----------------------------------------------------------------------------------------------
        // TODO: COOLING
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new AutoFeederModule.Ticker(stack), MPSItems.AUTO_FEEDER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new ActiveCamouflageModule.Ticker(stack), MPSItems.ACTIVE_CAMOUFLAGE_MODULE.get());

        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new CoolantTankModule.Ticker(stack, 1), MPSItems.COOLANT_TANK_MODULE_1.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new CoolantTankModule.Ticker(stack,  2), MPSItems.COOLANT_TANK_MODULE_2.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new CoolantTankModule.Ticker(stack, 3), MPSItems.COOLANT_TANK_MODULE_3.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new CoolantTankModule.Ticker(stack, 4), MPSItems.COOLANT_TANK_MODULE_4.get());

        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new MagnetModule.Ticker(stack), MPSItems.MAGNET_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new MobRepulsorModule.Ticker(stack), MPSItems.MOB_REPULSOR_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new PiglinPacificationModule.PiglinPacificationCap(stack), MPSItems.PIGLIN_PACIFICATION_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new WaterElectrolyzerModule.Ticker(stack), MPSItems.WATER_ELECTROLYZER_MODULE.get());

        // Mining Enchantment -----------------------------------------------------------------------------------------
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new AquaAffinityModule.TickingEnchantment(stack), MPSItems.AQUA_AFFINITY_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new FortuneModule.TickingEnchantment(stack), MPSItems.FORTUNE_MODULE.get());

        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new SilkTouchModule.TickingEnchantment(stack), MPSItems.SILK_TOUCH_MODULE.get());

        // Mining Enhancement -----------------------------------------------------------------------------------------
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new SelectiveMiner.Enhancement(stack), MPSItems.SELECTIVE_MINER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new TunnelBoreModule.Enhancement(stack), MPSItems.TUNNEL_BORE_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new VeinMinerModule.Enhancement(stack), MPSItems.VEIN_MINER_MODULE.get());

        // Special
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new HudModule(stack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, true), Items.CLOCK);
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new HudModule(stack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, true), Items.COMPASS);
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new HudModule(stack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, true), Items.RECOVERY_COMPASS);

        Item ae2_compass = BuiltInRegistries.ITEM.get(MPSConstants.AE2_METEOR_COMPASS);
        if(ae2_compass != Items.AIR){
            registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx)-> new HudModule(stack, ModuleCategory.SPECIAL, ModuleTarget.HEADONLY, true), ae2_compass);
        }
        // Cosmetic

        // Movement -------------------------------------------------------------------------------
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new BlinkDriveModule.RightClickie(stack), MPSItems.BLINK_DRIVE_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ClimbAssistModule.ClimbAssistCap(stack), MPSItems.CLIMB_ASSIST_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new DimensionalRiftModule.RightClickie(stack), MPSItems.DIMENSIONAL_RIFT_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new FlightControlModule.Toggler(stack), MPSItems.FLIGHT_CONTROL_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new GliderModule.Ticker(stack), MPSItems.GLIDER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new JetBootsModule.Ticker(stack), MPSItems.JET_BOOTS_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new JetPackModule.Ticker(stack), MPSItems.JETPACK_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new JumpAssistModule.Ticker(stack), MPSItems.JUMP_ASSIST_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ParachuteModule.Ticker(stack), MPSItems.PARACHUTE_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShockAbsorberModule.Toggler(stack), MPSItems.SHOCK_ABSORBER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new SprintAssistModule.Ticker(stack), MPSItems.SPRINT_ASSIST_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new SwimAssistModule.Ticker(stack), MPSItems.SWIM_ASSIST_MODULE.get());

        // Tools ----------------------------------------------------------------------------------
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new TinkerTableCap(stack), MPSItems.TINKER_TABLE_ITEM.get());

        // Axe
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new AxeModule.BlockBreaker(stack, 1), MPSItems.STONE_AXE_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new AxeModule.BlockBreaker(stack, 2), MPSItems.IRON_AXE_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new AxeModule.BlockBreaker(stack, 3), MPSItems.DIAMOND_AXE_MODULE.value());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new AxeModule.BlockBreaker(stack, 4), MPSItems.NETHERITE_AXE_MODULE.get());

        // Pickaxe
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PickaxeModule.BlockBreaker(stack, 1), MPSItems.STONE_PICKAXE_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PickaxeModule.BlockBreaker(stack, 2), MPSItems.IRON_PICKAXE_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PickaxeModule.BlockBreaker(stack, 3), MPSItems.DIAMOND_PICKAXE_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PickaxeModule.BlockBreaker(stack, 4), MPSItems.NETHERITE_PICKAXE_MODULE.get());

        // Rototiller
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new HoeModule.RightClickie(stack, 1), MPSItems.STONE_ROTOTILLER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new HoeModule.RightClickie(stack, 2), MPSItems.IRON_ROTOTILLER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new HoeModule.RightClickie(stack, 3), MPSItems.DIAMOND_ROTOTILLER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new HoeModule.RightClickie(stack, 4), MPSItems.NETHERITE_ROTOTILLER_MODULE.get());

        //Shovel
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 1), MPSItems.STONE_SHOVEL_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 2), MPSItems.IRON_SHOVEL_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 3), MPSItems.DIAMOND_SHOVEL_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShovelModule.BlockBreaker(stack, 4), MPSItems.NETHERITE_SHOVEL_MODULE.get());

        // portable crafting table
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PortableCraftingCapability.RightClickie(stack), Items.CRAFTING_TABLE);

        // Misc
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new FlintAndSteelModule.RightClickie(stack), MPSItems.FLINT_AND_STEEL_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LeafBlowerModule.Ticker(stack), MPSItems.LEAF_BLOWER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LuxCapacitorModule.RightClickie(stack), MPSItems.LUX_CAPACITOR_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new ShearsModule.BlockBreaker(stack), MPSItems.SHEARS_MODULE.get());

        // Vision ---------------------------------------------------------------------------------
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new BinocularsModule.BinocularCap(stack), MPSItems.BINOCULARS_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new NightVisionModule.Ticker(stack), MPSItems.NIGHTVISION_MODULE.get());

        // Weapons --------------------------------------------------------------------------------
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new BladeLauncherModule.RightClickie(stack), MPSItems.BLADE_LAUNCHER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new LightningModule.RightClickie(stack), MPSItems.LIGHTNING_SUMMONER_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new MeleeAssistModule.PMCap(stack), MPSItems.MELEE_ASSIST_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new PlasmaCannonModule.RightClickie(stack), MPSItems.PLASMA_CANNON_MODULE.get());
        registerItem(event, NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new RailgunModule.Ticker(stack), MPSItems.RAILGUN_MODULE.get());

        // External Mod Items ---------------------------------------------------------------------
        final ResourceLocation COPPER_WAND = ResourceLocation.fromNamespaceAndPath("wands", "copper_wand");
        final ResourceLocation IRON_WAND = ResourceLocation.fromNamespaceAndPath("wands", "iron_wand");
        final ResourceLocation DIAMOND_WAND = ResourceLocation.fromNamespaceAndPath("wands", "diamond_wand");
        final ResourceLocation NETHERITE_WAND = ResourceLocation.fromNamespaceAndPath("wands", "netherite_wand");

        Item copper_wand = BuiltInRegistries.ITEM.get(COPPER_WAND);
        if(copper_wand != Items.AIR) {
            registerItem(event, NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES, (stack, ctx) -> new OtherModItemsAsModules(stack, ModuleCategory.TOOL, 1, true), copper_wand);
            Item iron_wand = BuiltInRegistries.ITEM.get(IRON_WAND);
            if(iron_wand != Items.AIR) {
                registerItem(event, NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES, (stack, ctx) -> new OtherModItemsAsModules(stack, ModuleCategory.TOOL, 2, true), iron_wand);

                Item diamond_wand = BuiltInRegistries.ITEM.get(DIAMOND_WAND);
                if(diamond_wand != Items.AIR) {
                    registerItem(event, NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES, (stack, ctx) -> new OtherModItemsAsModules(stack, ModuleCategory.TOOL, 3, true), diamond_wand);

                    Item netherite_wand = BuiltInRegistries.ITEM.get(NETHERITE_WAND);
                    if(netherite_wand != Items.AIR) {
                        registerItem(event, NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES, (stack, ctx) -> new OtherModItemsAsModules(stack, ModuleCategory.TOOL, 4, true), netherite_wand);
                    }
                }
            }
        }

        NuminaLogger.logDebug("finished with " + toggleableModules.size() + " modules");
    }

    static <T, C extends @Nullable Object> void registerItem(RegisterCapabilitiesEvent event, ItemCapability<T, C> capability, ICapabilityProvider<ItemStack, C, T> provider, ItemLike... items){
        event.registerItem(capability, provider, items);

        for (ItemLike item : items){
            ItemStack stack = new ItemStack(item);
            IPowerModule cap = stack.getCapability(NuminaCapabilities.Module.POWER_MODULE);
            if(cap instanceof IToggleableModule) {
                addToggleableModule(item);
            }
        }
    }

    public static void addToggleableModule(ItemLike item){
        if(item != Items.AIR && !toggleableModules.contains(item)) {
            NuminaLogger.logDebug("adding toggleable module: " + item);
            toggleableModules.add(item);
        }
    }
}
