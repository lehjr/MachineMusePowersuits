package lehjr.numina.common.registration;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.item.Battery;
import lehjr.numina.common.item.ComponentItem;
import lehjr.numina.common.item.NuminaArmorStandItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.nullness.qual.NonNull;

import static lehjr.numina.common.registration.NuminaBlocks.CHARGING_BASE_BLOCK;

public class NuminaItems {
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

    // Modules ------------------------------------------------------------------------------------
    // Energy Storage
    public static final RegistryObject<Item> BASIC_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_BASIC__REGNAME,
            () -> new Battery());

    public static final RegistryObject<Item> ADVANCED_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ADVANCED__REGNAME,
            () -> new Battery());

    public static final RegistryObject<Item> ELITE_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ELITE__REGNAME,
            () -> new Battery());

    public static final RegistryObject<Item> ULTIMATE_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ULTIMATE__REGNAME,
            () -> new Battery());

    // Block Items --------------------------------------------------------------------------------
    // Charging base
    public static final RegistryObject<Item> CHARGING_BASE_ITEM = NUMINA_ITEMS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> new BlockItem(CHARGING_BASE_BLOCK.get(),
                    new Item.Properties()));

    // Armor stand
    public static final RegistryObject<Item> ARMOR_STAND_ITEM = NUMINA_ITEMS.register(NuminaConstants.ARMORSTAND_REGNAME,
            () -> new NuminaArmorStandItem(new Item.Properties()));//.setISTER(() -> NuminaArmorStandItemRenderer::new)));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NuminaConstants.MOD_ID);
    public static final RegistryObject<CreativeModeTab> NUMINA_TAB = CREATIVE_MODE_TABS.register("creative.mode.tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ARMOR_STAND_ITEM.get()))
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
