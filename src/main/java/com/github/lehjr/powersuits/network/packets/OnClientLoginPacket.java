package com.github.lehjr.powersuits.network.packets;

import com.github.lehjr.powersuits.event.PlayerLoginHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Workaround for login event being fired server side only now
 */
public class OnClientLoginPacket {
    public OnClientLoginPacket() {
    }

    public static void encode(OnClientLoginPacket msg, PacketBuffer packetBuffer) {
    }

    public static OnClientLoginPacket decode(PacketBuffer packetBuffer) {
        return new OnClientLoginPacket();
    }

    public static void handle(OnClientLoginPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity player = ctx.get().getSender();
            PlayerLoginHandler.clientPlayerLogin(player);
        });
        ctx.get().setPacketHandled(true);
    }
}