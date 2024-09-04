package com.lehjr.numina.common.network.packets.serverbound;

import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

/**
 * Used when changing cosmetic settings.
 * @param slotType
 * @param tagName
 * @param tagData
 */
public record CosmeticInfoPacketServerBound(EquipmentSlot slotType, String tagName, CompoundTag tagData) implements CustomPacketPayload {
    public static final Type<CosmeticInfoPacketServerBound> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "cosmetic_info_to_server"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, CosmeticInfoPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(CosmeticInfoPacketServerBound::write, CosmeticInfoPacketServerBound::new);

    public CosmeticInfoPacketServerBound(FriendlyByteBuf packetBuffer) {
        this(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readUtf(500),
                packetBuffer.readNbt());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeUtf(tagName);
        packetBuffer.writeNbt(tagData);
    }

//    public static void sendToClient(ServerPlayer entity, EquipmentSlot slotType, String tagName, CompoundTag tagData) {
//        NuminaPackets.sendToPlayer(new CosmeticInfoPacketServerBound(slotType, tagName, tagData), entity);
//    }

    public static void handle(CosmeticInfoPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            IModelSpec iModelSpec = ItemUtils.getItemFromEntitySlot(player, data.slotType).getCapability(NuminaCapabilities.RENDER);
            if(iModelSpec != null) {
                ItemStack newStack = iModelSpec.setRenderTag(data.tagData, data.tagName);
                player.setItemSlot(data.slotType, newStack);
            }
        });
    }
}
