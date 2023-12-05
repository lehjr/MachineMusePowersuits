package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.network.packets.clienthandlers.ModeChangeRequestPacketClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ModeChangeRequestPacketClientBound(int mode) {
    public static void encode(ModeChangeRequestPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.mode);
    }

    public int getMode() {
        return this.getMode();
    }

    public static ModeChangeRequestPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new ModeChangeRequestPacketClientBound(
                packetBuffer.readInt()
        );
    }

    public static class Handler {
        public static void handle(ModeChangeRequestPacketClientBound msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    // Make sure it's only executed on the physical client
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ModeChangeRequestPacketClientHandler.handlePacket(msg, ctx))
            );
            ctx.get().setPacketHandled(true);
        }
    }
}