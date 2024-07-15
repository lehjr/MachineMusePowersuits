package lehjr.numina.common.base;

import com.mojang.serialization.Codec;
import lehjr.numina.common.block.ChargingBase;
import lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.player.keystates.PlayerKeyStateStorage;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.container.ArmorStandMenu;
import lehjr.numina.common.container.ChargingBaseMenu;
import lehjr.numina.common.entity.NuminaArmorStand;
import lehjr.numina.common.item.Battery;
import lehjr.numina.common.item.ComponentItem;
import lehjr.numina.common.item.NuminaArmorStandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
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
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NuminaObjects {
    /**
     * Blocks -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> NUMINA_BLOCKS = DeferredRegister.createBlocks(NuminaConstants.MOD_ID);
    public static final DeferredHolder<Block, ChargingBase> CHARGING_BASE_BLOCK = NUMINA_BLOCKS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            ChargingBase::new);

    /**
     * Block Entity Types --------------------------------------------------------------------------
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NuminaConstants.MOD_ID);


    public static final Supplier<BlockEntityType<ChargingBaseBlockEntity>> CHARGING_BASE_BLOCK_ENTITY = BLOCKENTITY_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> BlockEntityType.Builder.of(ChargingBaseBlockEntity::new, CHARGING_BASE_BLOCK.get()).build(null));

    /**
     * Items --------------------------------------------------------------------------------------
     */
    public static final DeferredRegister.Items NUMINA_ITEMS = DeferredRegister.createItems(NuminaConstants.MOD_ID);

    // Block Items --------------------------------------------------------------------------------
    // Charging base
    public static final DeferredHolder<Item, BlockItem> CHARGING_BASE_ITEM = NUMINA_ITEMS.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> new BlockItem(CHARGING_BASE_BLOCK.get(),
                    new Item.Properties()));

    // Armor Stand --------------------------------------------------------------------------------
    public static final DeferredHolder<Item, NuminaArmorStandItem> ARMOR_STAND_ITEM = NUMINA_ITEMS.register(NuminaConstants.ARMORSTAND_REGNAME,
            () -> new NuminaArmorStandItem(new Item.Properties()));//.setISTER(() -> NuminaArmorStandItemRenderer::new)));

    // Modules ------------------------------------------------------------------------------------
    // Energy Storage
    public static final DeferredHolder<Item, Battery> BASIC_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_BASIC__REGNAME,
            () -> new Battery(1));

    public static final DeferredHolder<Item, Battery> ADVANCED_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ADVANCED__REGNAME,
            () -> new Battery(2));

    public static final DeferredHolder<Item, Battery> ELITE_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ELITE__REGNAME,
            () -> new Battery(3));

    public static final DeferredHolder<Item, Battery> ULTIMATE_BATTERY = NUMINA_ITEMS.register(NuminaConstants.MODULE_BATTERY_ULTIMATE__REGNAME,
            () -> new Battery(4));

    // Components ---------------------------------------------------------------------------------
    public static final DeferredHolder<Item, ComponentItem> ARTIFICIAL_MUSCLE = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__ARTIFICIAL_MUSCLE__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> CARBON_MYOFIBER = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__CARBON_MYOFIBER__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> COMPUTER_CHIP = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__COMPUTER_CHIP__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> CONTROL_CIRCUIT1 = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__CONTROL_CIRCUIT_1__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> CONTROL_CIRCUIT2 = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__CONTROL_CIRCUIT_2__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> CONTROL_CIRCUIT3 = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__CONTROL_CIRCUIT_3__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> CONTROL_CIRCUIT4 = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__CONTROL_CIRCUIT_4__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> FIELD_EMITTER = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__FIELD_EMITTER__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> GLIDER_WING = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__GLIDER_WING__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> ION_THRUSTER = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__ION_THRUSTER__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> LASER_EMITTER = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__LASER_EMITTER__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> MAGNET = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__MAGNET__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> MYOFIBER_GEL = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__MYOFIBER_GEL__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> PARACHUTE = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__PARACHUTE__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> RUBBER_HOSE = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__RUBBER_HOSE__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> SERVO = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__SERVO__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> SOLAR_PANEL = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__SOLAR_PANEL__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> SOLENOID = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__SOLENOID__REGNAME,
            ComponentItem::new);
    public static final DeferredHolder<Item, ComponentItem> WIRING = NUMINA_ITEMS.register(NuminaConstants.COMPONENT__WIRING__REGNAME,
            ComponentItem::new);

    /**
     * Entity Types -------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NuminaConstants.MOD_ID);

    public static final Supplier<EntityType<NuminaArmorStand>> ARMOR_STAND__ENTITY_TYPE = ENTITY_TYPES.register(NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME,
            () -> EntityType.Builder.<NuminaArmorStand>of(NuminaArmorStand::new, MobCategory.CREATURE)
                    .sized(0.5F, 1.975F) // Hitbox Size
                    .build(new ResourceLocation(NuminaConstants.MOD_ID, NuminaConstants.ARMOR_STAND__ENTITY_TYPE_REGNAME).toString()));

    /**
     * AbstractContainerMenu Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, NuminaConstants.MOD_ID);

    public static final Supplier<MenuType<ArmorStandMenu>> ARMOR_STAND_CONTAINER_TYPE = MENU_TYPES.register("armorstand_modding_container",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                int entityID = data.readInt();
                Entity armorStand = inv.player.level().getEntity(entityID);
                if (armorStand instanceof ArmorStand) {
                    return new ArmorStandMenu(windowId, inv, (ArmorStand) armorStand);
                }
                return null;
            }));

    public static final Supplier<MenuType<ChargingBaseMenu>> CHARGING_BASE_CONTAINER_TYPE = MENU_TYPES.register(NuminaConstants.CHARGING_BASE_REGNAME,
            () -> IMenuTypeExtension.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new ChargingBaseMenu(windowId, inv.player, pos);
            }));


    // DataComponentType
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, NuminaConstants.MOD_ID);

    public static final DataComponentType<Integer> ENERGY = register("energy", builder ->
            builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));

    public static final DataComponentType<Boolean> ONLINE = register("is_online", builder ->
            builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static final DataComponentType<Integer> COLOR = register("color", builder ->
            builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));

    public static final DataComponentType<Double> HEAT = register("heat",
            builder -> builder.persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));

    public static final DataComponentType<CompoundTag> MODULAR_ITEM_CODEC =
            register("modular_item", builder ->builder.persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG));

    public static final DataComponentType<CompoundTag> POWERMODULE_ITEM_CODEC =
            register("power_module", builder ->builder.persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG));

    public static final DataComponentType<CompoundTag> MODEL_SPEC_ITEM_CODEC =
            register("model_spec", builder ->builder.persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG));

    public static final DataComponentType<Byte> KEY_STATES = register("key_states", builder ->
            builder.persistent(Codec.BYTE).networkSynchronized(ByteBufCodecs.BYTE));

    private static <T> DataComponentType<T> register(String name, Consumer<DataComponentType.Builder<T>> customizer) {
        var builder = DataComponentType.<T>builder();
        customizer.accept(builder);
        var componentType = builder.build();
        DATA_COMPONENT_TYPES.register(name, () -> componentType);
        return componentType;
    }

    // Create the DeferredRegister for attachment types
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, NuminaConstants.MOD_ID);

    // Serialization via INBTSerializable
    private static final Supplier<AttachmentType<PlayerKeyStateStorage>> KEYSTATE_HANDLER = ATTACHMENT_TYPES.register(
            "keystate", () -> AttachmentType.serializable(() -> new PlayerKeyStateStorage()).build());












//    public static final Codec<Double> POSITIVE_DOUBLE = doubleRangeMinExclusiveWithMessage(
//            0.0F, Double.MAX_VALUE, p_339597_ -> "Value must be positive: " + p_339597_
//    );




//    private static Codec<Double> doubleRangeMinExclusiveWithMessage(double min, double max, Function<Double, String> pErrorMessage) {
//        return Codec.DOUBLE
//                .validate(
//                        aDouble -> aDouble.compareTo(min) > 0 && aDouble.compareTo(max) <= 0
//                                ? DataResult.success(aDouble)
//                                : DataResult.error(() -> pErrorMessage.apply(aDouble))
//                );


//    // Serialization via INBTSerializable
//    private static final Supplier<AttachmentType<ItemStackHandler>> HANDLER = ATTACHMENT_TYPES.register(
//            "handler", () -> AttachmentType.serializable(() -> new ItemStackHandler(1)).build());
//    // Serialization via codec
//    private static final Supplier<AttachmentType<Byte>> MANA = ATTACHMENT_TYPES.register(
//            "mana", () -> AttachmentType.builder(() -> 0).serialize(Codec.BYTE).build());




    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Batteries --------------------------------------------------------------------------------------------------
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new Battery.BatteryEnergyStorage(stack, ENERGY, 1), NuminaObjects.BASIC_BATTERY.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new Battery.BatteryEnergyStorage(stack, ENERGY, 2), NuminaObjects.ADVANCED_BATTERY.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new Battery.BatteryEnergyStorage(stack, ENERGY, 3), NuminaObjects.ELITE_BATTERY.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new Battery.BatteryEnergyStorage(stack, ENERGY, 4), NuminaObjects.ULTIMATE_BATTERY.get());

        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new Battery.BatteryPowerModule(stack, 1), NuminaObjects.BASIC_BATTERY.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new Battery.BatteryPowerModule(stack, 2), NuminaObjects.ADVANCED_BATTERY.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new Battery.BatteryPowerModule(stack, 3), NuminaObjects.ELITE_BATTERY.get());
        event.registerItem(NuminaCapabilities.Module.POWER_MODULE, (stack, ctx) -> new Battery.BatteryPowerModule(stack, 4), NuminaObjects.ULTIMATE_BATTERY.get());

        // Blocks -----------------------------------------------------------------------------------------------------
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CHARGING_BASE_BLOCK_ENTITY.get(), (o, direction) -> o.getItemHandler());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, CHARGING_BASE_BLOCK_ENTITY.get(), (o, direction) -> o.getEnergyHandler());

        // Entities ---------------------------------------------------------------------------------------------------
        event.registerEntity(NuminaCapabilities.PLAYER_KEYSTATES, EntityType.PLAYER, (player, context)-> player.getData(KEYSTATE_HANDLER));
    }




    public static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerDataComponentType(
            String name,
            Supplier<UnaryOperator<DataComponentType.Builder<T>>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.get().apply(DataComponentType.builder()).build());
    }


    public static final DeferredRegister<CreativeModeTab> NUMINA_CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NuminaConstants.MOD_ID);

    public static final Supplier<CreativeModeTab> NUMINA_TAB = NUMINA_CREATIVE_MODE_TAB.register("creative.mode.tab",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(NuminaObjects.ARMOR_STAND_ITEM.get()))
//            ()-> CreativeModeTab.builder().icon(()->new ItemStack(Items.STICK))
                    .title(Component.translatable(NuminaConstants.CREATIVE_TAB_TRANSLATION_KEY))
                    .displayItems((parameters, output) -> {
                        // Components ------------------------------------------------------------------
                        output.accept(ARTIFICIAL_MUSCLE.get());
                        output.accept(CARBON_MYOFIBER.get());
                        output.accept(COMPUTER_CHIP.get());
                        output.accept(CONTROL_CIRCUIT1.get());
                        output.accept(CONTROL_CIRCUIT2.get());
                        output.accept(CONTROL_CIRCUIT3.get());
                        output.accept(CONTROL_CIRCUIT4.get());
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

                        // Armor Stand --------------------------------------------------------------------------------
                        output.accept(ARMOR_STAND_ITEM.get());

                        // Block Items --------------------------------------------------------------------------------
                        // Charging base
                        output.accept(CHARGING_BASE_ITEM.get());
                    })
                    .build());

    @NonNull
    static ItemStack getBattery(Item item) {
        ItemStack out = new ItemStack(item);
        NuminaCapabilities.getCapability(out, Capabilities.EnergyStorage.ITEM).ifPresent(energy->{
            int max = energy.getMaxEnergyStored();
            energy.receiveEnergy(max, false);
        });
        return out;
    }

}
