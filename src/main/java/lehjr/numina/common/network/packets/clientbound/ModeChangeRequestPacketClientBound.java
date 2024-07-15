package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.packets.clienthandlers.ModeChangeRequestPacketClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public record ModeChangeRequestPacketClientBound(int mode) implements CustomPacketPayload {
    public static final Type<ModeChangeRequestPacketClientBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "mode_change_request_to_client"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, ModeChangeRequestPacketClientBound> STREAM_CODEC =
            StreamCodec.ofMember(ModeChangeRequestPacketClientBound::write, ModeChangeRequestPacketClientBound::new);
    
    public ModeChangeRequestPacketClientBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readInt());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(mode);
    }

    public static void handle(ModeChangeRequestPacketClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            NuminaLogger.logDebug("trying to enque work here");
            if (FMLEnvironment.dist == Dist.CLIENT) {
                NuminaLogger.logDebug("passed client side check");
                ModeChangeRequestPacketClientHandler.handlePacket(data.mode);
            }
        });
    }
}
