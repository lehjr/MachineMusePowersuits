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

package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.MathUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * This packet has no client bound counterpart because none is needed
 * @param keyData
 */
public record PlayerUpdatePacketServerBound(byte keyData) implements CustomPacketPayload {
    public static final Type<PlayerUpdatePacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "player_update_to_server"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, PlayerUpdatePacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(PlayerUpdatePacketServerBound::write, PlayerUpdatePacketServerBound::new);
    
    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeByte(keyData);
    }

    public PlayerUpdatePacketServerBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readByte());
    }

    public static void handle(PlayerUpdatePacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            Optional.ofNullable(player.getCapability(NuminaCapabilities.PLAYER_KEYSTATES)).ifPresent(playerCap -> {
                boolean[] boolArray = MathUtils.byteToBooleanArray(data.keyData);
                playerCap.setForwardKeyState(boolArray[0]);
                playerCap.setReverseKeyState(boolArray[1]);
                playerCap.setLeftStrafeKeyState(boolArray[2]);
                playerCap.setRightStrafeKeyState(boolArray[3]);
                playerCap.setDownKeyState(boolArray[4]);
                playerCap.setJumpKeyState(boolArray[5]);
            });
        });
    }
}
