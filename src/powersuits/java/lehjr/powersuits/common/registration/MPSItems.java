package lehjr.powersuits.common.registration;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.block.TinkerTableItem;
import lehjr.powersuits.common.item.debug.MPSDebugItem;
import lehjr.powersuits.common.item.electric.armor.AbstractElectricArmor;
import lehjr.powersuits.common.item.electric.tool.PowerFist;
import lehjr.powersuits.common.item.module.armor.ArmorPlatingModule;
import lehjr.powersuits.common.item.module.armor.EnergyShieldModule;
import lehjr.powersuits.common.item.module.cosmetic.TransparentArmorModule;
import lehjr.powersuits.common.item.module.debug.DebugModule;
import lehjr.powersuits.common.item.module.miningenchantment.AquaAffinityModule;
import lehjr.powersuits.common.item.module.miningenchantment.FortuneModule;
import lehjr.powersuits.common.item.module.miningenchantment.SilkTouchModule;
import lehjr.powersuits.common.item.module.miningenhancement.AdvancedVeinMiner;
import lehjr.powersuits.common.item.module.miningenhancement.TunnelBoreModule;
import lehjr.powersuits.common.item.module.miningenhancement.VeinMinerModule;
import lehjr.powersuits.common.item.module.movement.*;
import lehjr.powersuits.common.item.module.special.InvisibilityModule;
import lehjr.powersuits.common.item.module.special.MagnetModule;
import lehjr.powersuits.common.item.module.special.PiglinPacificationModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.chopping.AxeModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.digging.ShovelModule;
import lehjr.powersuits.common.item.module.tool.blockbreaking.mining.PickaxeModule;
import lehjr.powersuits.common.item.module.tool.misc.FlintAndSteelModule;
import lehjr.powersuits.common.item.module.tool.misc.LeafBlowerModule;
import lehjr.powersuits.common.item.module.tool.misc.LuxCapacitorModule;
import lehjr.powersuits.common.item.module.vision.BinocularsModule;
import lehjr.powersuits.common.item.module.vision.NightVisionModule;
import lehjr.powersuits.common.item.module.weapon.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = MPSConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MPSItems {
    public static final DeferredRegister.Items MPS_ITEMS = DeferredRegister.createItems(MPSConstants.MOD_ID);
    /**
     * Debug Item
     */
    public static final DeferredHolder<Item, MPSDebugItem> DEBUG_ITEM = MPS_ITEMS.register("debugitem",
            MPSDebugItem::new);


    /**
     * Block Items --------------------------------------------------------------------------------
     */
    // use directly as a module
    public static final DeferredHolder<Item, BlockItem> TINKER_TABLE_ITEM = MPS_ITEMS.register(MPSConstants.TINKER_TABLE.getPath(),
            () -> new TinkerTableItem(MPSBlocks.TINKER_TABLE_BLOCK.get()));

    public static final DeferredHolder<Item, BlockItem> LUX_CAPACITOR_ITEM = MPS_ITEMS.register(MPSConstants.LUX_CAPACITOR.getPath(),
            () -> new BlockItem(MPSBlocks.LUX_CAPACITOR_BLOCK.get(), new Item.Properties()
                    .stacksTo(64)
                    .setNoRepair()));

    /**
     * Tools --------------------------------------------------------------------------------------
     */
    public static final DeferredHolder<Item, PowerFist> POWER_FIST_1 = MPS_ITEMS.register(MPSConstants.POWER_FIST_1.getPath(),
            PowerFist::new);

    public static final DeferredHolder<Item, PowerFist> POWER_FIST_2 = MPS_ITEMS.register(MPSConstants.POWER_FIST_2.getPath(),
            PowerFist::new);

    public static final DeferredHolder<Item, PowerFist> POWER_FIST_3 = MPS_ITEMS.register(MPSConstants.POWER_FIST_3.getPath(),
            PowerFist::new);

    public static final DeferredHolder<Item, PowerFist> POWER_FIST_4 = MPS_ITEMS.register(MPSConstants.POWER_FIST_4.getPath(),
            PowerFist::new);

    /**
     * Armor --------------------------------------------------------------------------------------
     */
    // Primitive Modular Armor
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_HELMET_1 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_HELMET_1.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.HELMET));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_CHESTPLATE_1 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_CHESTPLATE_1.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.CHESTPLATE));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_LEGGINGS_1 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_LEGGINGS_1.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.LEGGINGS));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_BOOTS_1 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_BOOTS_1.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.BOOTS));

    // Prototype Power Armor
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_HELMET_2 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_HELMET_2.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.HELMET));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_CHESTPLATE_2 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_CHESTPLATE_2.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.CHESTPLATE));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_LEGGINGS_2 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_LEGGINGS_2.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.LEGGINGS));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_BOOTS_2 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_BOOTS_2.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.BOOTS));

    // Power Armor
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_HELMET_3 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_HELMET_3.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.HELMET));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_CHESTPLATE_3 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_CHESTPLATE_3.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.CHESTPLATE));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_LEGGINGS_3 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_LEGGINGS_3.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.LEGGINGS));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_BOOTS_3 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_BOOTS_3.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.BOOTS));

    // Power Armor MK2
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_HELMET_4 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_HELMET_4.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.HELMET));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_CHESTPLATE_4 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_CHESTPLATE_4.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.CHESTPLATE));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_LEGGINGS_4 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_LEGGINGS_4.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.LEGGINGS));
    public static final DeferredHolder<Item, AbstractElectricArmor> POWER_ARMOR_BOOTS_4 = MPS_ITEMS.register(MPSConstants.POWER_ARMOR_BOOTS_4.getPath(),
            () -> new AbstractElectricArmor(ArmorItem.Type.BOOTS));

    /**
     * Modules ------------------------------------------------------------------------------------
     */
    // Armor --------------------------------------------------------------------------------------
    public static final DeferredHolder<Item, ArmorPlatingModule> IRON_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.IRON_PLATING_MODULE.getPath(),
            ArmorPlatingModule::new);
    public static final DeferredHolder<Item, ArmorPlatingModule> DIAMOND_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_PLATING_MODULE.getPath(),
            ArmorPlatingModule::new);
    public static final DeferredHolder<Item, ArmorPlatingModule> NETHERITE_PLATING_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_PLATING_MODULE.getPath(),
            ArmorPlatingModule::new);
    public static final DeferredHolder<Item, EnergyShieldModule> ENERGY_SHIELD_MODULE = MPS_ITEMS.register(MPSConstants.ENERGY_SHIELD_MODULE.getPath(),
            EnergyShieldModule::new);

    // Cosmetic -----------------------------------------------------------------------------------
    public static final DeferredHolder<Item, TransparentArmorModule> TRANSPARENT_ARMOR_MODULE = MPS_ITEMS.register(MPSConstants.TRANSPARENT_ARMOR_MODULE.getPath(),
            TransparentArmorModule::new);

    // Debug --------------------------------------------------------------------------------------
    public static final DeferredHolder<Item, DebugModule> DEBUG_MODULE = MPS_ITEMS.register("debugmodule",
            DebugModule::new);

    public static final DeferredRegister<CreativeModeTab> MPS_CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MPSConstants.MOD_ID);

    // Tools --------------------------------------------------------------------------------------
    // Axe
    public static final DeferredHolder<Item, AxeModule> STONE_AXE_MODULE = MPS_ITEMS.register(MPSConstants.STONE_AXE_MODULE.getPath(),
            AxeModule::new);

    public static final DeferredHolder<Item, AxeModule> IRON_AXE_MODULE = MPS_ITEMS.register(MPSConstants.IRON_AXE_MODULE.getPath(),
            AxeModule::new);

    public static final DeferredHolder<Item, AxeModule> DIAMOND_AXE_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_AXE_MODULE.getPath(),
            AxeModule::new);

    public static final DeferredHolder<Item, AxeModule> NETHERITE_AXE_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_AXE_MODULE.getPath(),
            AxeModule::new);

    // Pickaxe
    public static final DeferredHolder<Item, PickaxeModule> STONE_PICKAXE_MODULE = MPS_ITEMS.register(MPSConstants.STONE_PICKAXE_MODULE.getPath(),
            PickaxeModule::new);

    public static final DeferredHolder<Item, PickaxeModule> IRON_PICKAXE_MODULE = MPS_ITEMS.register(MPSConstants.IRON_PICKAXE_MODULE.getPath(),
            PickaxeModule::new);

    public static final DeferredHolder<Item, PickaxeModule> DIAMOND_PICKAXE_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_PICKAXE_MODULE.getPath(),
            PickaxeModule::new);

    public static final DeferredHolder<Item, PickaxeModule> NETHERITE_PICKAXE_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_PICKAXE_MODULE.getPath(),
            PickaxeModule::new);

    // Shovel
    public static final DeferredHolder<Item, ShovelModule> STONE_SHOVEL_MODULE = MPS_ITEMS.register(MPSConstants.STONE_SHOVEL_MODULE.getPath(),
            ShovelModule::new);

    public static final DeferredHolder<Item, ShovelModule> IRON_SHOVEL_MODULE = MPS_ITEMS.register(MPSConstants.IRON_SHOVEL_MODULE.getPath(),
            ShovelModule::new);

    public static final DeferredHolder<Item, ShovelModule> DIAMOND_SHOVEL_MODULE = MPS_ITEMS.register(MPSConstants.DIAMOND_SHOVEL_MODULE.getPath(),
            ShovelModule::new);

    public static final DeferredHolder<Item, ShovelModule> NETHERITE_SHOVEL_MODULE = MPS_ITEMS.register(MPSConstants.NETHERITE_SHOVEL_MODULE.getPath(),
            ShovelModule::new);

    // Misc
    public static final DeferredHolder<Item, FlintAndSteelModule> FLINT_AND_STEAL_MODULE = MPS_ITEMS.register(MPSConstants.FLINT_AND_STEEL_MODULE.getPath(),
            FlintAndSteelModule::new);
    public static final DeferredHolder<Item, LeafBlowerModule> LEAF_BLOWER_MODULE = MPS_ITEMS.register(MPSConstants.LEAF_BLOWER_MODULE.getPath(),
            LeafBlowerModule::new);
    public static final DeferredHolder<Item, LuxCapacitorModule> LUX_CAPACITOR_MODULE = MPS_ITEMS.register(MPSConstants.LUX_CAPACITOR_MODULE.getPath(),
            LuxCapacitorModule::new);

    // Vision ------------------------------------------------------------------------------------
    public static final DeferredHolder<Item, BinocularsModule> BINOCULARS_MODULE = MPS_ITEMS.register(MPSConstants.BINOCULARS_MODULE.getPath(),
            BinocularsModule::new);

    public static final DeferredHolder<Item, NightVisionModule> NIGHTVISION_MODULE = MPS_ITEMS.register(MPSConstants.NIGHT_VISION_MODULE.getPath(),
            NightVisionModule::new);

    // Weapons ------------------------------------------------------------------------------------
    public static final DeferredHolder<Item, BladeLauncherModule> BLADE_LAUNCHER_MODULE = MPS_ITEMS.register(MPSConstants.BLADE_LAUNCHER_MODULE.getPath(),
            BladeLauncherModule::new);

    public static final DeferredHolder<Item, LightningModule> LIGHTNING_SUMMONER_MODULE = MPS_ITEMS.register(MPSConstants.LIGHTNING_MODULE.getPath(),
            LightningModule::new);

    public static final DeferredHolder<Item, MeleeAssistModule> MELEE_ASSIST_MODULE = MPS_ITEMS.register(MPSConstants.MELEE_ASSIST_MODULE.getPath(),
            MeleeAssistModule::new);

    public static final DeferredHolder<Item, PlasmaCannonModule> PLASMA_CANNON_MODULE = MPS_ITEMS.register(MPSConstants.PLASMA_CANNON_MODULE.getPath(),
            PlasmaCannonModule::new);

    public static final DeferredHolder<Item, RailgunModule> RAILGUN_MODULE = MPS_ITEMS.register(MPSConstants.RAILGUN_MODULE.getPath(),
            RailgunModule::new);

    public static final DeferredHolder<Item, SonicWeaponModule> SONIC_WEAPON_MODULE = MPS_ITEMS.register(MPSConstants.SONIC_WEAPON_MODULE.getPath(),
            SonicWeaponModule::new);

    // Creative Mode Tab --------------------------------------------------------------------------
    public static final Supplier<CreativeModeTab> MPS_TAB = MPS_CREATIVE_MODE_TAB.register("creative.mode.tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(IRON_PLATING_MODULE.get()))
                    .title(Component.translatable(MPSConstants.CREATIVE_TAB_TRANSLATION_KEY))
                    .displayItems((parameters, output) -> {
                        output.accept(DEBUG_ITEM.get());
                        // Block Items ------------------------------------------------------------
                        output.accept(TINKER_TABLE_ITEM.get());
                        output.accept(LUX_CAPACITOR_ITEM.get());

                        // Power Fist -------------------------------------------------------------
                        output.accept(POWER_FIST_1.get());
                        output.accept(POWER_FIST_2.get());
                        output.accept(POWER_FIST_3.get());
                        output.accept(POWER_FIST_4.get());

                        // Armor ------------------------------------------------------------------
                        output.accept(POWER_ARMOR_HELMET_1.get());
                        output.accept(POWER_ARMOR_CHESTPLATE_1.get());
                        output.accept(POWER_ARMOR_LEGGINGS_1.get());
                        output.accept(POWER_ARMOR_BOOTS_1.get());

                        output.accept(POWER_ARMOR_HELMET_2.get());
                        output.accept(POWER_ARMOR_CHESTPLATE_2.get());
                        output.accept(POWER_ARMOR_LEGGINGS_2.get());
                        output.accept(POWER_ARMOR_BOOTS_2.get());

                        output.accept(POWER_ARMOR_HELMET_3.get());
                        output.accept(POWER_ARMOR_CHESTPLATE_3.get());
                        output.accept(POWER_ARMOR_LEGGINGS_3.get());
                        output.accept(POWER_ARMOR_HELMET_4.get());

                        output.accept(POWER_ARMOR_CHESTPLATE_4.get());
                        output.accept(POWER_ARMOR_LEGGINGS_4.get());
                        output.accept(POWER_ARMOR_BOOTS_3.get());
                        output.accept(POWER_ARMOR_BOOTS_4.get());


                        // Modules ----------------------------------------------------------------
                        // Armor
                        output.accept(IRON_PLATING_MODULE.get());
                        output.accept(DIAMOND_PLATING_MODULE.get());
                        output.accept(NETHERITE_PLATING_MODULE.get());
                        output.accept(ENERGY_SHIELD_MODULE.get());
                        //Cosmetic
                        output.accept(TRANSPARENT_ARMOR_MODULE.get());
                        // Debug
                        output.accept(DEBUG_MODULE.get());

                        // Energy Generation

                        // Environmental
                        // cooling system prototype (bucket with a string?)
                        // cooling system prototype Mk2
                        // cooling system Mk3
                        // Cooling system Mk4

                        // Mining Enchantment
//                        AquaAffinityModule
//                        FortuneModule
//                        SilkTouchModule

                        // Mining Enhancement
//                        AdvancedVeinMiner
//                        TunnelBoreModule
//                        VeinMinerModule

                        // Movement
//                        BlinkDriveModule
//                        ClimbAssistModule
//                        DimensionalRiftModule
//                        FlightControlModule
//                        GliderModule
//                        JetBootsModule
//                        JumpAssistModule
//                        ParachuteModule
//                        ShockAbsorberModule
//                        SprintAssistModule
//                        SwimAssistModule

                        // Special
//                        InvisibilityModule
//                        MagnetModule
//                        PiglinPacificationModule

                        // Axe
                        output.accept(STONE_AXE_MODULE.get());
                        output.accept(IRON_AXE_MODULE.get());
                        output.accept(DIAMOND_AXE_MODULE.get());
                        output.accept(NETHERITE_AXE_MODULE.get());

                        // Shovel
                        output.accept(STONE_SHOVEL_MODULE.get());
                        output.accept(IRON_SHOVEL_MODULE.get());
                        output.accept(DIAMOND_SHOVEL_MODULE.get());
                        output.accept(NETHERITE_SHOVEL_MODULE.get());



                        // Farming
                        // hoe/rototiller
                        // shears

                        // PickAxe
                        output.accept(STONE_PICKAXE_MODULE.get());
                        output.accept(IRON_PICKAXE_MODULE.get());
                        output.accept(DIAMOND_PICKAXE_MODULE.get());
                        output.accept(NETHERITE_PICKAXE_MODULE.get());

                        // Misc
                        output.accept(FLINT_AND_STEAL_MODULE.get());
                        output.accept(LEAF_BLOWER_MODULE.get());
                        output.accept(LUX_CAPACITOR_MODULE.get());

                        // Vision
                        output.accept(BINOCULARS_MODULE.get());
                        output.accept(NIGHTVISION_MODULE.get());

                        // Weapon
                        output.accept(BLADE_LAUNCHER_MODULE.get());
                        output.accept(LIGHTNING_SUMMONER_MODULE.get());
                        output.accept(MELEE_ASSIST_MODULE.get());
                        output.accept(PLASMA_CANNON_MODULE.get());
                        output.accept(RAILGUN_MODULE.get());
                        output.accept(SONIC_WEAPON_MODULE.get());
                    })
                    .build());



    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
//            event.accept(EXAMPLE_BLOCK_ITEM);
    }

}
