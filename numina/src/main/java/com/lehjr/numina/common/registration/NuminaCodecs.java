package com.lehjr.numina.common.registration;

import com.lehjr.numina.common.capabilities.player.keystates.PlayerKeyStateStorage;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NuminaCodecs {

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

    public static DataComponentType<SimpleFluidContent> ITEM_FLUID_CODEC = register("item_fluid",
            builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));


    private static <T> DataComponentType<T> register(String name, Consumer<DataComponentType.Builder<T>> customizer) {
        var builder = DataComponentType.<T>builder();
        customizer.accept(builder);
        var componentType = builder.build();
        DATA_COMPONENT_TYPES.register(name, () -> componentType);
        return componentType;
    }

    // Create the DeferredRegister for attachment types
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, NuminaConstants.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> COLOR_ATTACHMENT = ATTACHMENT_TYPES.register(
        // ID of the attachment type
        "color",
        () -> AttachmentType
            // A supplier for your default value, e.g. zero
            .builder(() -> 0)
            // A codec for serialization, this can be omitted if you don't want to serialize anything
            .serialize(Codec.INT)
            // Build the builder
            .build()
    );


    // Serialization via INBTSerializable
    public static final Supplier<AttachmentType<PlayerKeyStateStorage>> KEYSTATE_HANDLER = ATTACHMENT_TYPES.register(
            "keystate", () -> AttachmentType.serializable(() -> new PlayerKeyStateStorage()).build());

    public static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerDataComponentType(
            String name,
            Supplier<UnaryOperator<DataComponentType.Builder<T>>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.get().apply(DataComponentType.builder()).build());
    }

}
