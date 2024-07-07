package lehjr.powersuits.common.network;

import lehjr.powersuits.common.network.packets.clientbound.CreativeInstallPacketClientBound;
import lehjr.powersuits.common.network.packets.serverbound.ContainerGuiOpenPacket;
import lehjr.powersuits.common.network.packets.serverbound.CreativeInstallPacketServerBound;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class MPSPackets {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.2.3");

        registrar.playToServer(
                CreativeInstallPacketServerBound.ID,
                CreativeInstallPacketServerBound.STREAM_CODEC,
                CreativeInstallPacketServerBound::handle);

        registrar.playToClient(
                CreativeInstallPacketClientBound.ID,
                CreativeInstallPacketClientBound.STREAM_CODEC,
                CreativeInstallPacketClientBound::handle);
//
//        //        CHANNEL_INSTANCE.registerMessage(
////                i++,
////                CPlaceRecipePacket.class,
////                CPlaceRecipePacket::encode,
////                CPlaceRecipePacket::decode,
////                CPlaceRecipePacket::handle);
////
////        CHANNEL_INSTANCE.registerMessage(
////                i++,
////                SPlaceGhostRecipePacket.class,
////                SPlaceGhostRecipePacket::encode,
////                SPlaceGhostRecipePacket::decode,
////                SPlaceGhostRecipePacket::handle);
//
        registrar.playToServer(
                        ContainerGuiOpenPacket.ID,
                        ContainerGuiOpenPacket.STREAM_CODEC,
                        ContainerGuiOpenPacket::handle);
//
//
//
//
////        CHANNEL_INSTANCE.registerMessage(
////                i++,
////                MusePacketCosmeticPreset.class,
////                MusePacketCosmeticPreset::encode,
////                MusePacketCosmeticPreset::decode,
////                MusePacketCosmeticPreset::handle);
//
////        CHANNEL_INSTANCE.registerMessage(
////                i++,
////                MusePacketCosmeticPresetUpdate.class,
////                MusePacketCosmeticPresetUpdate::encode,
////                MusePacketCosmeticPresetUpdate::decode,
////                MusePacketCosmeticPresetUpdate::handle);
//
//
//        CHANNEL_INSTANCE.registerMessage(
//                i++,
//                ContainerGuiOpenPacket.class,
//                ContainerGuiOpenPacket::write,
//                ContainerGuiOpenPacket::read,
//                ContainerGuiOpenPacket::handle);


    }

    public static void sendToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }


    public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }
}

