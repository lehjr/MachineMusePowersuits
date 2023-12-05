package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.network.packets.clientbound.ModeChangeRequestPacketClientBound;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ModeChangeRequestPacketClientHandler {    public static void handlePacket(ModeChangeRequestPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
        final Player player = Minecraft.getInstance().player;
        ctx.get().enqueueWork(() -> {
            int mode = message.getMode();
            if (player != null) {
                ItemUtils.setModeAndSwapIfNeeded(player, mode);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
