package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.network.packets.clienthandlers.TweakRequestDoublePacketClientHander;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record TweakRequestDoublePacketClientBound(EquipmentSlot type, ResourceLocation moduleRegName, String tweakName, double tweakValue) {
    public static void encode(TweakRequestDoublePacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.type);
        packetBuffer.writeResourceLocation(msg.moduleRegName);
        packetBuffer.writeUtf(msg.tweakName);
        packetBuffer.writeDouble(msg.tweakValue);
    }

    public static TweakRequestDoublePacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new TweakRequestDoublePacketClientBound(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readResourceLocation(),
                packetBuffer.readUtf(500),
                packetBuffer.readDouble());
    }

    public static class Handler {
        public static void handle(TweakRequestDoublePacketClientBound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    // Make sure it's only executed on the physical client
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TweakRequestDoublePacketClientHander.handlePacket(msg, ctx))
            );
            ctx.get().setPacketHandled(true);
        }
    }
}
