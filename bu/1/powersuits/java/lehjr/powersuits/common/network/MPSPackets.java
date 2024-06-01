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

package lehjr.powersuits.common.network;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.network.packets.ContainerGuiOpenPacket;
import lehjr.powersuits.common.network.packets.clientbound.CreativeInstallPacketClientBound;
import lehjr.powersuits.common.network.packets.serverbound.CreativeInstallPacketServerBound;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MPSPackets {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL_INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MPSConstants.MOD_ID, "data"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerMPAPackets() {
        int i = 0;

        CHANNEL_INSTANCE.registerMessage(
                i++,
                CreativeInstallPacketClientBound.class,
                CreativeInstallPacketClientBound::write,
                CreativeInstallPacketClientBound::read,
                CreativeInstallPacketClientBound::handle);

        CHANNEL_INSTANCE.registerMessage(
                i++,
                CreativeInstallPacketServerBound.class,
                CreativeInstallPacketServerBound::write,
                CreativeInstallPacketServerBound::read,
                CreativeInstallPacketServerBound.Handler::handle);

//        CHANNEL_INSTANCE.registerMessage(
//                i++,
//                CPlaceRecipePacket.class,
//                CPlaceRecipePacket::encode,
//                CPlaceRecipePacket::decode,
//                CPlaceRecipePacket::handle);
//
//        CHANNEL_INSTANCE.registerMessage(
//                i++,
//                SPlaceGhostRecipePacket.class,
//                SPlaceGhostRecipePacket::encode,
//                SPlaceGhostRecipePacket::decode,
//                SPlaceGhostRecipePacket::handle);


        CHANNEL_INSTANCE.registerMessage(
                i++,
                ContainerGuiOpenPacket.class,
                ContainerGuiOpenPacket::write,
                ContainerGuiOpenPacket::read,
                ContainerGuiOpenPacket::handle);


//        CHANNEL_INSTANCE.registerMessage(
//                i++,
//                MusePacketCosmeticPreset.class,
//                MusePacketCosmeticPreset::encode,
//                MusePacketCosmeticPreset::decode,
//                MusePacketCosmeticPreset::handle);

//        CHANNEL_INSTANCE.registerMessage(
//                i++,
//                MusePacketCosmeticPresetUpdate.class,
//                MusePacketCosmeticPresetUpdate::encode,
//                MusePacketCosmeticPresetUpdate::decode,
//                MusePacketCosmeticPresetUpdate::handle);


        CHANNEL_INSTANCE.registerMessage(
                i++,
                ContainerGuiOpenPacket.class,
                ContainerGuiOpenPacket::write,
                ContainerGuiOpenPacket::read,
                ContainerGuiOpenPacket::handle);
    }

    public SimpleChannel getWrapper() {
        return CHANNEL_INSTANCE;
    }
}
