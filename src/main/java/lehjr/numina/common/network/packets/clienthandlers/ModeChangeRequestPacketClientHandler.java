package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ModeChangeRequestPacketClientHandler {
    public static void handlePacket(int mode) {
        final Player player = Minecraft.getInstance().player;
        if (player != null) {
            ItemUtils.setModeAndSwapIfNeeded(player, mode);
        }
    }
}
