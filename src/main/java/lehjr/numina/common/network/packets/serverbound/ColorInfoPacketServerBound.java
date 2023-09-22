package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ColorInfoPacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record ColorInfoPacketServerBound(EquipmentSlot slotType, int[] tagData) {
    public static void encode(ColorInfoPacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeVarIntArray(msg.tagData);
    }

    public static ColorInfoPacketServerBound decode(FriendlyByteBuf packetBuffer) {
        return new ColorInfoPacketServerBound(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readVarIntArray());
    }

    public static void sendToClient(ServerPlayer entity, EquipmentSlot slotType, int[] tagData) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new ColorInfoPacketClientBound(slotType, tagData));
    }

    public static class Handler {
        public static void handle(ColorInfoPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final ServerPlayer player = ctx.get().getSender();
                EquipmentSlot slotType = message.slotType;
                int[] tagData = message.tagData;
                player.getItemBySlot(slotType).getCapability(NuminaCapabilities.RENDER)
                        .ifPresent(render -> {
                            render.setColorArray(tagData);
                        });
//                player.containerMenu.broadcastChanges();
                sendToClient(player, slotType, tagData); // this seems faster than letting changes propagate through player.containerMenu mechanics

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
