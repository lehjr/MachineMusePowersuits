package com.lehjr.numina.common.network.packets.clientbound;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.network.packets.clienthandlers.CosmeticInfoPacketClientHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record CosmeticInfoPacketClientBound(EquipmentSlot slotType, String tagName, CompoundTag tagData) implements CustomPacketPayload {
    public static final Type<CosmeticInfoPacketClientBound> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "cosmetic_info_to_client"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, CosmeticInfoPacketClientBound> STREAM_CODEC =
            StreamCodec.ofMember(CosmeticInfoPacketClientBound::write, CosmeticInfoPacketClientBound::new);

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeUtf(tagName);
        packetBuffer.writeNbt(tagData);
    }

    public CosmeticInfoPacketClientBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readUtf(500),
                packetBuffer.readNbt());
    }

    public static void handle(CosmeticInfoPacketClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                CosmeticInfoPacketClientHandler.handlePacket(data.slotType, data.tagName, data.tagData);
            }
        });
    }
}