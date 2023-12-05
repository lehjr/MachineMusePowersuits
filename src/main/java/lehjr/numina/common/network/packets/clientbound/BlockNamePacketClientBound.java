package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.network.packets.clienthandlers.BlockNamePacketClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record BlockNamePacketClientBound(ResourceLocation regName) {
    public static void encode(BlockNamePacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeResourceLocation(msg.regName);
    }

    public static BlockNamePacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new BlockNamePacketClientBound(packetBuffer.readResourceLocation());
    }

    public ResourceLocation getRegName() {
        return this.regName;
    }

    public static class Handler {
        public static void handle(BlockNamePacketClientBound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    // Make sure it's only executed on the physical client
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> BlockNamePacketClientHandler.handlePacket(msg, ctx))
            );
            ctx.get().setPacketHandled(true);
        }
    }
}
