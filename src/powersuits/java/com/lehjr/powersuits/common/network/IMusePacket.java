package com.lehjr.powersuits.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

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
    void encode(T msg, FriendlyByteBuf packetBuffer);

    T decode(FriendlyByteBuf packetBuffer);

    void handle(T message, Supplier<NetworkEvent.Context> ctx);
}