package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ModeChangeRequestPacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record ModeChangeRequestPacketServerBound(int mode) {
    public static void encode(ModeChangeRequestPacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.mode);
    }

    public static ModeChangeRequestPacketServerBound decode(FriendlyByteBuf packetBuffer) {
        return new ModeChangeRequestPacketServerBound(
                packetBuffer.readInt()
        );
    }

    public static void sendToClient(ServerPlayer entity, int mode) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new ModeChangeRequestPacketClientBound(mode));
    }

    public static class Handler {
        public static void handle(ModeChangeRequestPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            final ServerPlayer player = ctx.get().getSender();
            ctx.get().enqueueWork(() -> {
                int mode = message.mode;
                if (player != null) {
                    ItemUtils.setModeAndSwapIfNeeded(player, mode);
                    if (player instanceof ServerPlayer) {
                        sendToClient(player, mode);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
