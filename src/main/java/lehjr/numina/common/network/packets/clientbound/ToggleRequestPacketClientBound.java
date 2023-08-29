package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ToggleRequestPacketClientBound(ResourceLocation registryName, boolean toggleval) {
    public static void encode(ToggleRequestPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeUtf(msg.registryName.toString());
        packetBuffer.writeBoolean(msg.toggleval);
    }

    public static ToggleRequestPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new ToggleRequestPacketClientBound(
                new ResourceLocation(packetBuffer.readUtf(500)),
                packetBuffer.readBoolean()
        );
    }

    public static class Handler {
        public static void handle(ToggleRequestPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(()-> {
                ResourceLocation registryName = message.registryName;
                boolean toggleval = message.toggleval;
                Player player = Minecraft.getInstance().player;
                if (player == null) {
                    return;
                }

                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    player.getInventory().getItem(i).getCapability(ForgeCapabilities.ITEM_HANDLER)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
