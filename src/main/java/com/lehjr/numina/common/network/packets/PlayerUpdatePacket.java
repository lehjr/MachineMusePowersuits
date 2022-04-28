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

package com.lehjr.numina.common.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import com.lehjr.numina.api.capabilities.player.CapabilityPlayerKeyStates;

import java.util.function.Supplier;

public class PlayerUpdatePacket {
    boolean forwardKeyState;
    byte strafeKeyState;
    boolean downKeyState;
    boolean jumpKeyState;

    public PlayerUpdatePacket(
            boolean forwardKeyState,
            byte strafeKeyState,
            boolean downKeyState,
            boolean jumpKeyState) {

        this.forwardKeyState = forwardKeyState;
        this.strafeKeyState = strafeKeyState;
        this.downKeyState =  downKeyState;
        this.jumpKeyState = jumpKeyState;
    }

    public static void encode(PlayerUpdatePacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeBoolean(msg.forwardKeyState);
        packetBuffer.writeByte(msg.strafeKeyState);
        packetBuffer.writeBoolean(msg.downKeyState);
        packetBuffer.writeBoolean(msg.jumpKeyState);
    }

    public static PlayerUpdatePacket decode(FriendlyByteBuf packetBuffer) {
        return new PlayerUpdatePacket(
                packetBuffer.readBoolean(),
                packetBuffer.readByte(),
                packetBuffer.readBoolean(),
                packetBuffer.readBoolean()
        );
    }

    public static void handle(PlayerUpdatePacket message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayer player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            player.getCapability(CapabilityPlayerKeyStates.PLAYER_KEYSTATES).ifPresent(playerCap ->{
                playerCap.setForwardKeyState(message.forwardKeyState);
                playerCap.setStrafeKeyState(message.strafeKeyState);
                playerCap.setDownKeyState(message.downKeyState);
                playerCap.setJumpKeyState(message.jumpKeyState);

            });
        });
        ctx.get().setPacketHandled(true);
    }
}