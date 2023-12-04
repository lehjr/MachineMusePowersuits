package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.CosmeticInfoPacketClientBound;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

/**
 * Used when changing cosmetic settings.
 * @param slotType
 * @param tagName
 * @param tagData
 */
public record CosmeticInfoPacketServerBound(EquipmentSlot slotType, String tagName, CompoundTag tagData) {

    public static void encode(CosmeticInfoPacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeUtf(msg.tagName);
        packetBuffer.writeNbt(msg.tagData);
    }

    public static CosmeticInfoPacketServerBound decode(FriendlyByteBuf packetBuffer) {
        return new CosmeticInfoPacketServerBound(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readUtf(500),
                packetBuffer.readNbt());
    }

    public static void sendToClient(ServerPlayer entity, EquipmentSlot slotType, String tagName, CompoundTag tagData) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new CosmeticInfoPacketClientBound(slotType, tagName, tagData));
    }

    public static class Handler {
        public static void handle(CosmeticInfoPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                EquipmentSlot slotType = message.slotType;
                String tagName = message.tagName;
                CompoundTag tagData = message.tagData;

                ServerPlayer player = ctx.get().getSender();
                if (player != null) {
                    ItemUtils.getItemFromEntitySlot(player, slotType).getCapability(
                            NuminaCapabilities.RENDER).ifPresent(render -> render.setRenderTag(tagData, tagName));

//                    player.containerMenu.broadcastChanges();
                    sendToClient(player, slotType, tagName, tagData);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}