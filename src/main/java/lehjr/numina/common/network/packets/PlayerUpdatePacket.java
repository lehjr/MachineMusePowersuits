/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.network.packets;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerUpdatePacket {
    byte data;

    public PlayerUpdatePacket(byte data) {
        this.data = data;
    }

    public static void encode(PlayerUpdatePacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeByte(msg.data);
    }

    public static PlayerUpdatePacket decode(FriendlyByteBuf packetBuffer) {
        return new PlayerUpdatePacket(packetBuffer.readByte());
    }

    public static void handle(PlayerUpdatePacket message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayer player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            player.getCapability(NuminaCapabilities.PLAYER_KEYSTATES).ifPresent(playerCap -> {
                boolean[] boolArray = MathUtils.byteToBooleanArray(message.data);
                playerCap.setForwardKeyState(boolArray[0]);
                playerCap.setReverseKeyState(boolArray[1]);
                playerCap.setLeftStrafeKeyState(boolArray[2]);
                playerCap.setRightStrafeKeyState(boolArray[3]);
                playerCap.setDownKeyState(boolArray[4]);
                playerCap.setJumpKeyState(boolArray[5]);

            });
        });
        ctx.get().setPacketHandled(true);
    }
}