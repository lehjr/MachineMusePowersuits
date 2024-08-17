package com.lehjr.numina.common.network;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.network.packets.clientbound.*;
import com.lehjr.numina.common.network.packets.serverbound.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NuminaPackets {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(NuminaConstants.MOD_ID)
                .versioned("1.2.3")
                .optional();

        // -----------------------------------------------------
        registrar.playToServer(
                BlockPositionPacketServerBound.ID,
                BlockPositionPacketServerBound.STREAM_CODEC,
                BlockPositionPacketServerBound::handle);

        registrar.playToServer(
                BlockStateClearPacketServerBound.ID,
                BlockStateClearPacketServerBound.STREAM_CODEC,
                BlockStateClearPacketServerBound::handle);

        // -----------------------------------------------------
        registrar.playToServer(
                ColorInfoPacketServerBound.ID,
                ColorInfoPacketServerBound.STREAM_CODEC,
                ColorInfoPacketServerBound::handle);

        registrar.playToClient(
                ColorInfoPacketClientBound.ID,
                ColorInfoPacketClientBound.STREAM_CODEC,
                ColorInfoPacketClientBound::handle);

        // -----------------------------------------------------
        registrar.playToServer(
                CosmeticInfoPacketServerBound.ID,
                CosmeticInfoPacketServerBound.STREAM_CODEC,
                CosmeticInfoPacketServerBound::handle);

        registrar.playToClient(
                CosmeticInfoPacketClientBound.ID,
                CosmeticInfoPacketClientBound.STREAM_CODEC,
                CosmeticInfoPacketClientBound::handle);

        // -----------------------------------------------------
        registrar.playToServer(
                ToggleRequestPacketServerBound.ID,
                ToggleRequestPacketServerBound.STREAM_CODEC,
                ToggleRequestPacketServerBound::handle);

        registrar.playToClient(
                ToggleRequestPacketClientBound.ID,
                ToggleRequestPacketClientBound.STREAM_CODEC,
                ToggleRequestPacketClientBound::handle);

        // -----------------------------------------------------
        registrar.playToServer(
                ModeChangeRequestPacketServerBound.ID,
                ModeChangeRequestPacketServerBound.STREAM_CODEC,
                ModeChangeRequestPacketServerBound::handle);

        registrar.playToClient(
                ModeChangeRequestPacketClientBound.ID,
                ModeChangeRequestPacketClientBound.STREAM_CODEC,
                ModeChangeRequestPacketClientBound::handle);

        // -----------------------------------------------------
        registrar.playToServer(
                PlayerUpdatePacketServerBound.ID,
                PlayerUpdatePacketServerBound.STREAM_CODEC,
                PlayerUpdatePacketServerBound::handle);

        // -----------------------------------------------------
        registrar.playToServer(
                TweakRequestDoublePacketServerBound.ID,
                TweakRequestDoublePacketServerBound.STREAM_CODEC,
                TweakRequestDoublePacketServerBound::handle);

        registrar.playToClient(
                TweakRequestDoublePacketClientBound.ID,
                TweakRequestDoublePacketClientBound.STREAM_CODEC,
                TweakRequestDoublePacketClientBound::handle);

//        //------------------------------------------------------
//        registrar.play(
//                i++,
//                PlayerHandStorageSyncResponseClientBound.ID,
//                PlayerHandStorageSyncResponseClientBound::create,
//                PlayerHandStorageSyncResponseClientBound.Handler::handle,
//                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
//        //------------------------------------------------------
//        registrar.play(
//                i++,
//                PlayerHandStorageSyncRequestServerBound.ID,
//                PlayerHandStorageSyncRequestServerBound::create,
//                PlayerHandStorageSyncRequestServerBound.Handler::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER));

    }

    public static void sendToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }


    public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }
}