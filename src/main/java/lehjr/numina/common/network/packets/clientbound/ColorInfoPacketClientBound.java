package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.network.packets.clienthandlers.ColorInfoPacketClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ColorInfoPacketClientBound(EquipmentSlot slotType, int[] tagData) {

    public static void encode(ColorInfoPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeVarIntArray(msg.tagData);
    }

    public static ColorInfoPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new ColorInfoPacketClientBound(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readVarIntArray());
    }

    public EquipmentSlot getSlotType() {
        return this.slotType;
    }

    public int[] getTagData() {
        return this.tagData;
    }
    public static class Handler {
        public static void handle(ColorInfoPacketClientBound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    // Make sure it's only executed on the physical client
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ColorInfoPacketClientHandler.handlePacket(msg, ctx))
            );
            ctx.get().setPacketHandled(true);
        }
    }
}