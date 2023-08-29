package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ToggleRequestPacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record ToggleRequestPacketServerBound(ResourceLocation registryName, boolean toggleval) {

    public static void encode(ToggleRequestPacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeUtf(msg.registryName.toString());
        packetBuffer.writeBoolean(msg.toggleval);
    }

    public static ToggleRequestPacketServerBound decode(FriendlyByteBuf packetBuffer) {
        return new ToggleRequestPacketServerBound(
                new ResourceLocation(packetBuffer.readUtf(500)),
                packetBuffer.readBoolean()
        );
    }

    public static void sendToClient(ServerPlayer entity, ResourceLocation registryName, boolean active) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new ToggleRequestPacketClientBound(registryName, active));
    }

    public static class Handler {
        public static void handle(ToggleRequestPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            final ServerPlayer player = ctx.get().getSender();

            if (player == null || player.getServer() == null)
                return;

            ctx.get().enqueueWork(()-> {
                ResourceLocation registryName = message.registryName;
                boolean toggleval = message.toggleval;

                if (player == null) {
                    return;
                }

                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    player.getInventory().getItem(i).getCapability(ForgeCapabilities.ITEM_HANDLER)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
                }
                sendToClient(player, registryName, toggleval);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
