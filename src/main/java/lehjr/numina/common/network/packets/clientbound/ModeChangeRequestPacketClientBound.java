package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ModeChangeRequestPacketClientBound(int mode, int slot) {
    public static void encode(ModeChangeRequestPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.mode);
        packetBuffer.writeInt(msg.slot);
    }

    public static ModeChangeRequestPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new ModeChangeRequestPacketClientBound(
                packetBuffer.readInt(),
                packetBuffer.readInt()
        );
    }

    public static class Handler {
        public static void handle(ModeChangeRequestPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
            final Player player = Minecraft.getInstance().player;
            ctx.get().enqueueWork(() -> {
                int slot = message.slot;
                int mode = message.mode;
                if (slot > -1 && slot < 9) {
                    player.getInventory().items.get(slot).getCapability(ForgeCapabilities.ITEM_HANDLER)
                            .filter(IModeChangingItem.class::isInstance)
                            .map(IModeChangingItem.class::cast)
                            .ifPresent(handler -> handler.setActiveMode(mode));
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}