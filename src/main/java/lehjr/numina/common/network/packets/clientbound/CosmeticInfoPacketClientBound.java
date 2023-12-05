package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.network.packets.clienthandlers.CosmeticInfoPacketClientHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record CosmeticInfoPacketClientBound(EquipmentSlot slotType, String tagName, CompoundTag tagData) {

    public static void encode(CosmeticInfoPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeUtf(msg.tagName);
        packetBuffer.writeNbt(msg.tagData);
    }

    public EquipmentSlot getSlotType() {
        return slotType;
    }

    public String getTagName() {
        return tagName;
    }

    public CompoundTag getTagData() {
        return tagData;
    }

    public static CosmeticInfoPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new CosmeticInfoPacketClientBound(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readUtf(500),
                packetBuffer.readNbt());
    }

    public static class Handler {
        public static void handle(CosmeticInfoPacketClientBound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    // Make sure it's only executed on the physical client
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CosmeticInfoPacketClientHandler.handlePacket(msg, ctx))
            );
            ctx.get().setPacketHandled(true);
        }
    }
}