package com.github.lehjr.powersuits.network.packets;

import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:28 PM, 5/6/13
 * <p>
 * Ported to Java by lehjr on 11/14/16.
 */
public class ColourInfoPacket {
    protected int itemSlot;
    protected int[] tagData;

    public ColourInfoPacket() {
    }

    public ColourInfoPacket(int itemSlot, int[] tagData) {
        this.itemSlot = itemSlot;
        this.tagData = tagData;
    }

    public static void encode(ColourInfoPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeInt(msg.itemSlot);
        packetBuffer.writeVarIntArray(msg.tagData);
    }

    public static ColourInfoPacket decode(PacketBuffer packetBuffer) {
        return new ColourInfoPacket(packetBuffer.readInt(), packetBuffer.readVarIntArray());
    }

    public static void handle(ColourInfoPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity player = ctx.get().getSender();
            int itemSlot = message.itemSlot;
            int[] tagData = message.tagData;

            player.inventory.getStackInSlot(itemSlot).getCapability(ModelSpecNBTCapability.RENDER).ifPresent(
                    render -> {
                        render.setColorArray(tagData);
                    });
        });
        ctx.get().setPacketHandled(true);
    }
}