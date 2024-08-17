package com.lehjr.numina.common.network.packets.clienthandlers;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ModeChangeRequestPacketClientHandler {
    public static void handlePacket(int mode) {
        final Player player = Minecraft.getInstance().player;
        if (player != null) {
            NuminaLogger.logDebug("trying to handle packet client side");
            ItemUtils.setModeAndSwapIfNeeded(player, mode);
            NuminaLogger.logDebug("supposedly updated mode client side");
        }
    }
}
