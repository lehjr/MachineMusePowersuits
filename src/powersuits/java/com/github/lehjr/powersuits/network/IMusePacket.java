package com.github.lehjr.powersuits.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Currently unused.
 * Thinking about how to use this...
 * registering requires static methods...
 *              static methods vs instance.method
 *
 * @param <T>
 */
public interface IMusePacket<T extends IMusePacket> {
    void encode(T msg, PacketBuffer packetBuffer);

    T decode(PacketBuffer packetBuffer);

    void handle(T message, Supplier<NetworkEvent.Context> ctx);
}