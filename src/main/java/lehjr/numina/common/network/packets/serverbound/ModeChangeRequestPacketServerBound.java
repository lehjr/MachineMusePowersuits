package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ModeChangeRequestPacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record ModeChangeRequestPacketServerBound(int mode, int slot) {
    public static void encode(ModeChangeRequestPacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.mode);
        packetBuffer.writeInt(msg.slot);
    }

    public static ModeChangeRequestPacketServerBound decode(FriendlyByteBuf packetBuffer) {
        return new ModeChangeRequestPacketServerBound(
                packetBuffer.readInt(),
                packetBuffer.readInt()
        );
    }

    public static void sendToClient(ServerPlayer entity, int mode, int slot) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new ModeChangeRequestPacketClientBound(mode, slot));
    }

    public static class Handler {
        public static void handle(ModeChangeRequestPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            final Player player = ctx.get().getSender();
            ctx.get().enqueueWork(() -> {
                int slot = message.slot;
                int mode = message.mode;
                if (slot > -1 && slot < 9) {
                    player.getInventory().items.get(slot).getCapability(ForgeCapabilities.ITEM_HANDLER)
                            .filter(IModeChangingItem.class::isInstance)
                            .map(IModeChangingItem.class::cast)
                            .ifPresent(handler -> handler.setActiveMode(mode));
                    if (player instanceof ServerPlayer) {
                        sendToClient((ServerPlayer) player, mode, slot);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
