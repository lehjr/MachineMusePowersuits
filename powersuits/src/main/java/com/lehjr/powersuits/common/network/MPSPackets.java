package com.lehjr.powersuits.common.network;

import com.lehjr.powersuits.common.network.packets.clientbound.CreativeInstallPacketClientBound;
import com.lehjr.powersuits.common.network.packets.clientbound.ToggleableModuleListClientBound;
import com.lehjr.powersuits.common.network.packets.serverbound.ContainerGuiOpenPacket;
import com.lehjr.powersuits.common.network.packets.serverbound.CreativeInstallPacketServerBound;
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


        registrar.playToServer(
            ContainerGuiOpenPacket.ID,
            ContainerGuiOpenPacket.STREAM_CODEC,
            ContainerGuiOpenPacket::handle);

        registrar.playToClient(ToggleableModuleListClientBound.ID,
            ToggleableModuleListClientBound.STREAM_CODEC,
            ToggleableModuleListClientBound::handle);
    }

    public static void sendToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }


    public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }
}

