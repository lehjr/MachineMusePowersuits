package com.github.lehjr.numina.util.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

/**
 * Created by Claire Semple on 9/9/2014.
 * <p>
 * Ported to Java by lehjr on 10/24/16.
 */
public final class NuminaPlayerUtils {
    public static void resetFloatKickTicks(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity) player;
            entityPlayerMP.connection.floatingTickCount = 0;
        }
    }
}