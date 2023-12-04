package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.item.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ModeChangeRequestPacketClientBound(int mode) {
    public static void encode(ModeChangeRequestPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.mode);
    }

    public static ModeChangeRequestPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new ModeChangeRequestPacketClientBound(
                packetBuffer.readInt()
        );
    }

    public static class Handler {
        public static void handle(ModeChangeRequestPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
            final Player player = Minecraft.getInstance().player;
            ctx.get().enqueueWork(() -> {
                int mode = message.mode;
                if (player != null) {
                    ItemUtils.setModeAndSwapIfNeeded(player, mode);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}