package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.packets.clienthandlers.ToggleRequestPacketClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ToggleRequestPacketClientBound(ResourceLocation registryName, boolean toggleval) implements CustomPacketPayload {
    public static final Type<ToggleRequestPacketClientBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "ToggleRequestClient"));

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, ToggleRequestPacketClientBound> STREAM_CODEC =
            StreamCodec.ofMember(ToggleRequestPacketClientBound::write, ToggleRequestPacketClientBound::new);

    public ToggleRequestPacketClientBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readResourceLocation(), packetBuffer.readBoolean());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeResourceLocation(registryName);
        packetBuffer.writeBoolean(toggleval);
    }

    public static void handle(ToggleRequestPacketClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ToggleRequestPacketClientHandler.handlePacket(data.registryName, data.toggleval);
            }
        });
    }
}