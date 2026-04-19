package lehjr.numina.common.registration;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.container.slot.SlotBackgrounds;
import lehjr.numina.common.item.Battery;
import lehjr.numina.common.item.ComponentItem;
import lehjr.numina.common.item.NuminaArmorStandItem;
import lehjr.numina.common.utils.IconUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class NuminaItems {
    public static final DeferredRegister.Items NUMINA_ITEMS = DeferredRegister.createItems(NuminaConstants.MOD_ID);

    // Block Items --------------------------------------------------------------------------------
    // Charging base
    public static final DeferredHolder<Item, BlockItem> CHARGING_BASE_ITEM = NUMINA_ITEMS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> new BlockItem(NuminaBlocks.CHARGING_BASE_BLOCK.get(), new Item.Properties().stacksTo(1)
                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                .component(NuminaCodecs.ENERGY, 0)));
    // Armor Stand --------------------------------------------------------------------------------
    public static final DeferredHolder<Item, NuminaArmorStandItem> ARMOR_STAND_ITEM = NUMINA_ITEMS.register(NuminaConstants.ARMORSTAND_REGNAME,
            () -> new NuminaArmorStandItem(new Item.Properties()));//.setISTER(() -> NuminaArmorStandItemRenderer::new)));

    // Modules ------------------------------------------------------------------------------------
    // Energy Storage
    public static final DeferredHolder<Item, Battery> BATTERY_1 = registerBattery(NuminaConstants.MODULE_BATTERY_1__REGNAME);
    public static final DeferredHolder<Item, Battery> BATTERY_2 = registerBattery(NuminaConstants.MODULE_BATTERY_2__REGNAME);
    public static final DeferredHolder<Item, Battery> BATTERY_3 = registerBattery(NuminaConstants.MODULE_BATTERY_3__REGNAME);
    public static final DeferredHolder<Item, Battery> BATTERY_4 = registerBattery(NuminaConstants.MODULE_BATTERY_4__REGNAME);

    // Components ---------------------------------------------------------------------------------
    public static final DeferredHolder<Item, ComponentItem> ARTIFICIAL_MUSCLE = registerComponent(NuminaConstants.COMPONENT__ARTIFICIAL_MUSCLE__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CARBON_MYOFIBER = registerComponent(NuminaConstants.COMPONENT__CARBON_MYOFIBER__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CAPACITOR_1 = registerComponent(NuminaConstants.COMPONENT__CAPACAITOR_1__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CAPACITOR_2 = registerComponent(NuminaConstants.COMPONENT__CAPACAITOR_2__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CAPACITOR_3 = registerComponent(NuminaConstants.COMPONENT__CAPACAITOR_3__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CAPACITOR_4 = registerComponent(NuminaConstants.COMPONENT__CAPACAITOR_4__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> COMPUTER_CHIP = registerComponent(NuminaConstants.COMPONENT__COMPUTER_CHIP__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CONTROL_CIRCUIT_1 = registerComponent(NuminaConstants.COMPONENT__CONTROL_CIRCUIT_1__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CONTROL_CIRCUIT_2 = registerComponent(NuminaConstants.COMPONENT__CONTROL_CIRCUIT_2__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CONTROL_CIRCUIT_3 = registerComponent(NuminaConstants.COMPONENT__CONTROL_CIRCUIT_3__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> CONTROL_CIRCUIT_4 = registerComponent(NuminaConstants.COMPONENT__CONTROL_CIRCUIT_4__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> FIELD_EMITTER = registerComponent(NuminaConstants.COMPONENT__FIELD_EMITTER__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> GLIDER_WING = registerComponent(NuminaConstants.COMPONENT__GLIDER_WING__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> ION_THRUSTER = registerComponent(NuminaConstants.COMPONENT__ION_THRUSTER__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> LASER_EMITTER = registerComponent(NuminaConstants.COMPONENT__LASER_EMITTER__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> MAGNET = registerComponent(NuminaConstants.COMPONENT__MAGNET__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> MYOFIBER_GEL = registerComponent(NuminaConstants.COMPONENT__MYOFIBER_GEL__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> PARACHUTE = registerComponent(NuminaConstants.COMPONENT__PARACHUTE__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> PLATING_IRON = registerComponent(NuminaConstants.COMPONENT__PLATING_IRON__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> PLATING_DIAMOND = registerComponent(NuminaConstants.COMPONENT__PLATING_DIAMOND__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> PLATING_NETHERITE = registerComponent(NuminaConstants.COMPONENT__PLATING_NETHERITE__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> RUBBER_HOSE = registerComponent(NuminaConstants.COMPONENT__RUBBER_HOSE__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> SERVO = registerComponent(NuminaConstants.COMPONENT__SERVO__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> SOLAR_PANEL = registerComponent(NuminaConstants.COMPONENT__SOLAR_PANEL__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> SOLENOID = registerComponent(NuminaConstants.COMPONENT__SOLENOID__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> WIRING_COPPER = registerComponent(NuminaConstants.COMPONENT__WIRING_COPPER__REGNAME);
    public static final DeferredHolder<Item, ComponentItem> WIRING_GOLD = registerComponent(NuminaConstants.COMPONENT__WIRING_GOLD__REGNAME);

    // Smithing Upgrades ==============================================================================================
    public static final DeferredHolder<Item, SmithingTemplateItem> IRON_SMITHING_UPGRADE_TEMPLATE = NUMINA_ITEMS.register(NuminaConstants.IRON_SMITHING_TEMPLATE.getPath(),
        ()-> new SmithingTemplateItem(
            NuminaConstants.IRON_UPGRADE_APPLIES_TO,
            NuminaConstants.IRON_UPGRADE_INGREDIENTS,
            NuminaConstants.IRON_UPGRADE,
            NuminaConstants.IRON_UPGRADE_BASE_SLOT_DESCRIPTION,
            NuminaConstants.IRON_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
            createNetheriteUpgradeIconList(),
            createNetheriteUpgradeMaterialList()));
    public static final DeferredHolder<Item, SmithingTemplateItem> DIAMOND_SMITHING_UPGRADE_TEMPLATE = NUMINA_ITEMS.register(NuminaConstants.DIAMOND_SMITHING_TEMPLATE.getPath(),
        ()-> new SmithingTemplateItem(
            NuminaConstants.DIAMOND_UPGRADE_APPLIES_TO,
            NuminaConstants.DIAMOND_UPGRADE_INGREDIENTS,
            NuminaConstants.DIAMOND_UPGRADE,
            NuminaConstants.DIAMOND_UPGRADE_BASE_SLOT_DESCRIPTION,
            NuminaConstants.DIAMOND_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
            createNetheriteUpgradeIconList(),
            createNetheriteUpgradeMaterialList()));

    public static final DeferredHolder<Item, SmithingTemplateItem> NETHERITE_SMITHING_UPGRADE_TEMPLATE = NUMINA_ITEMS.register(NuminaConstants.NETHERITE_SMITHING_TEMPLATE.getPath(),
        ()-> new SmithingTemplateItem(
            NuminaConstants.NETHERITE_UPGRADE_APPLIES_TO,
            NuminaConstants.NETHERITE_UPGRADE_INGREDIENTS,
            NuminaConstants.NETHERITE_UPGRADE,
            NuminaConstants.NETHERITE_UPGRADE_BASE_SLOT_DESCRIPTION,
            NuminaConstants.NETHERITE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
            createNetheriteUpgradeIconList(),
            createNetheriteUpgradeMaterialList()));


    private static List<ResourceLocation> createNetheriteUpgradeIconList() {
        return List.of(
            IconUtils.EMPTY_SLOT_HELMET,
            SlotBackgrounds.EMPTY_SLOT_SWORD,
            IconUtils.EMPTY_SLOT_CHESTPLATE,
            SlotBackgrounds.EMPTY_SLOT_PICKAXE,
            IconUtils.EMPTY_SLOT_LEGGINGS,
            SlotBackgrounds.EMPTY_SLOT_AXE,
            IconUtils.EMPTY_SLOT_BOOTS,
            SlotBackgrounds.EMPTY_SLOT_HOE,
            SlotBackgrounds.EMPTY_SLOT_SHOVEL);
    }

    private static List<ResourceLocation> createNetheriteUpgradeMaterialList() {
        return List.of(SlotBackgrounds.EMPTY_SLOT_INGOT);
    }

    static DeferredHolder<Item, ComponentItem> registerComponent(String name) {
        return NUMINA_ITEMS.register(name, ComponentItem::new);
    }

    static DeferredHolder<Item, Battery> registerBattery(String name) {
        return NUMINA_ITEMS.register(name, Battery::new);
    }

    public static final DeferredRegister<CreativeModeTab> NUMINA_CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NuminaConstants.MOD_ID);

    public static final Supplier<CreativeModeTab> NUMINA_TAB = NUMINA_CREATIVE_MODE_TAB.register("creative.mode.tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ARMOR_STAND_ITEM.get()))
//            ()-> CreativeModeTab.builder().icon(()->new ItemStack(Items.STICK))
                    .title(Component.translatable(NuminaConstants.CREATIVE_TAB_TRANSLATION_KEY))
                    .displayItems((parameters, output) -> {
                        // Components ------------------------------------------------------------------
                        output.accept(ARTIFICIAL_MUSCLE.get());
                        output.accept(CARBON_MYOFIBER.get());
                        output.accept(CAPACITOR_1.get());
                        output.accept(CAPACITOR_2.get());
                        output.accept(CAPACITOR_3.get());
                        output.accept(CAPACITOR_4.get());
                        output.accept(COMPUTER_CHIP.get());
                        output.accept(CONTROL_CIRCUIT_1.get());
                        output.accept(CONTROL_CIRCUIT_2.get());
                        output.accept(CONTROL_CIRCUIT_3.get());
                        output.accept(CONTROL_CIRCUIT_4.get());
                        output.accept(FIELD_EMITTER.get());
                        output.accept(GLIDER_WING.get());
                        output.accept(ION_THRUSTER.get());
                        output.accept(LASER_EMITTER.get());
                        output.accept(MAGNET.get());
                        output.accept(MYOFIBER_GEL.get());
                        output.accept(PARACHUTE.get());
                        output.accept(PLATING_IRON.get());
                        output.accept(PLATING_DIAMOND.get());
                        output.accept(PLATING_NETHERITE.get());
                        output.accept(RUBBER_HOSE.get());
                        output.accept(SERVO.get());
                        output.accept(SOLAR_PANEL.get());
                        output.accept(SOLENOID.get());
                        output.accept(WIRING_COPPER.get());
                        output.accept(WIRING_GOLD.get());

                        // Smithing Templates -------------------------------------------------------------------------
                        output.accept(IRON_SMITHING_UPGRADE_TEMPLATE.get());
                        output.accept(DIAMOND_SMITHING_UPGRADE_TEMPLATE.get());
                        output.accept(NETHERITE_SMITHING_UPGRADE_TEMPLATE.get());

                        // Modules ------------------------------------------------------------------------------------
                        // Energy Storage
                        output.accept(BATTERY_1.get());
                        output.accept(getBattery(BATTERY_1.get()));

                        output.accept(BATTERY_2.get());
                        output.accept(getBattery(BATTERY_2.get()));

                        output.accept(BATTERY_3.get());
                        output.accept(getBattery(BATTERY_3.get()));

                        output.accept(BATTERY_4.get());
                        output.accept(getBattery(BATTERY_4.get()));

                        // Armor Stand --------------------------------------------------------------------------------
                        output.accept(ARMOR_STAND_ITEM.get());

                        // Block Items --------------------------------------------------------------------------------
                        // Charging base
                        output.accept(CHARGING_BASE_ITEM.get());
                    })
                    .build());

    @Nonnull
    static ItemStack getBattery(Item item) {
        ItemStack out = new ItemStack(item);
        IEnergyStorage energyStorage = out.getCapability(Capabilities.EnergyStorage.ITEM);
        if(energyStorage != null) {
            int max = energyStorage.getMaxEnergyStored();
            while (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
                energyStorage.receiveEnergy(max, false);
            }
        }
        return out;
    }
}
