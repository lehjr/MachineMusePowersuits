package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.network.packets.clienthandlers.ToggleRequestPacketClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ToggleRequestPacketClientBound(ResourceLocation registryName, boolean toggleval) {
    public static void encode(ToggleRequestPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeUtf(msg.registryName.toString());
        packetBuffer.writeBoolean(msg.toggleval);
    }

    public static ToggleRequestPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new ToggleRequestPacketClientBound(
                new ResourceLocation(packetBuffer.readUtf(500)),
                packetBuffer.readBoolean()
        );
    }

    public static class Handler {
        public static void handle(ToggleRequestPacketClientBound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    // Make sure it's only executed on the physical client
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ToggleRequestPacketClientHandler.handlePacket(msg, ctx))
            );
            ctx.get().setPacketHandled(true);
        }
    }
}
