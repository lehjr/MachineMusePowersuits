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

package lehjr.numina.common.network;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.packets.clientbound.*;
import lehjr.numina.common.network.packets.serverbound.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class NuminaPackets {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL_INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NuminaConstants.MOD_ID, "data"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerNuminaPackets() {
        int i =0;
        // -----------------------------------------------------
        CHANNEL_INSTANCE.registerMessage(
                i++,
                BlockNamePacketServerBound.class,
                BlockNamePacketServerBound::encode,
                BlockNamePacketServerBound::decode,
                BlockNamePacketServerBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        CHANNEL_INSTANCE.registerMessage(
                i++,
                BlockNamePacketClientBound.class,
                BlockNamePacketClientBound::encode,
                BlockNamePacketClientBound::decode,
                BlockNamePacketClientBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        // -----------------------------------------------------
        CHANNEL_INSTANCE.registerMessage(
                i++,
                ColorInfoPacketServerBound.class,
                ColorInfoPacketServerBound::encode,
                ColorInfoPacketServerBound::decode,
                ColorInfoPacketServerBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        CHANNEL_INSTANCE.registerMessage(
                i++,
                ColorInfoPacketClientBound.class,
                ColorInfoPacketClientBound::encode,
                ColorInfoPacketClientBound::decode,
                ColorInfoPacketClientBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        // -----------------------------------------------------
        CHANNEL_INSTANCE.registerMessage(
                i++,
                CosmeticInfoPacketServerBound.class,
                CosmeticInfoPacketServerBound::encode,
                CosmeticInfoPacketServerBound::decode,
                CosmeticInfoPacketServerBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        CHANNEL_INSTANCE.registerMessage(
                i++,
                CosmeticInfoPacketClientBound.class,
                CosmeticInfoPacketClientBound::encode,
                CosmeticInfoPacketClientBound::decode,
                CosmeticInfoPacketClientBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        // -----------------------------------------------------
        CHANNEL_INSTANCE.registerMessage(
                i++,
                ToggleRequestPacketServerBound.class,
                ToggleRequestPacketServerBound::encode,
                ToggleRequestPacketServerBound::decode,
                ToggleRequestPacketServerBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        CHANNEL_INSTANCE.registerMessage(
                i++,
                ToggleRequestPacketClientBound.class,
                ToggleRequestPacketClientBound::encode,
                ToggleRequestPacketClientBound::decode,
                ToggleRequestPacketClientBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        // -----------------------------------------------------
        CHANNEL_INSTANCE.registerMessage(
                i++,
                ModeChangeRequestPacketServerBound.class,
                ModeChangeRequestPacketServerBound::encode,
                ModeChangeRequestPacketServerBound::decode,
                ModeChangeRequestPacketServerBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        CHANNEL_INSTANCE.registerMessage(
                i++,
                ModeChangeRequestPacketClientBound.class,
                ModeChangeRequestPacketClientBound::encode,
                ModeChangeRequestPacketClientBound::decode,
                ModeChangeRequestPacketClientBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        // -----------------------------------------------------
        CHANNEL_INSTANCE.registerMessage(
                i++,
                PlayerUpdatePacketServerBound.class,
                PlayerUpdatePacketServerBound::encode,
                PlayerUpdatePacketServerBound::decode,
                PlayerUpdatePacketServerBound::handle);

        // -----------------------------------------------------
        CHANNEL_INSTANCE.registerMessage(
                i++,
                TweakRequestDoublePacketServerBound.class,
                TweakRequestDoublePacketServerBound::encode,
                TweakRequestDoublePacketServerBound::decode,
                TweakRequestDoublePacketServerBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        CHANNEL_INSTANCE.registerMessage(
                i++,
                TweakRequestDoublePacketClientBound.class,
                TweakRequestDoublePacketClientBound::encode,
                TweakRequestDoublePacketClientBound::decode,
                TweakRequestDoublePacketClientBound.Handler::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public SimpleChannel getWrapper() {
        return CHANNEL_INSTANCE;
    }
}