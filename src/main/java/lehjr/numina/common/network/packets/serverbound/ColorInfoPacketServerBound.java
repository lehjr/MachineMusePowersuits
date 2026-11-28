package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ColorInfoPacketClientBound;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent;

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
        NuminaPackets.sendToPlayer( new ColorInfoPacketClientBound(slotType, tagData), entity);
    }

    public static class Handler {
        public static void handle(ColorInfoPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final ServerPlayer player = ctx.get().getSender();
                EquipmentSlot slotType = message.slotType;
                int[] tagData = message.tagData;
                ItemUtils.getItemFromEntitySlot(player, slotType)
                        .getCapability(NuminaCapabilities.RENDER)
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
