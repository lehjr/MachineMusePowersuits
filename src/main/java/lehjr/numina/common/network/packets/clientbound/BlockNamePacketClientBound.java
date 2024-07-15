package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.packets.clienthandlers.BlockNamePacketClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public record BlockNamePacketClientBound(ResourceLocation regName) implements CustomPacketPayload {
    public static final Type<BlockNamePacketClientBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "block_name_to_client"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, BlockNamePacketClientBound> STREAM_CODEC =
            StreamCodec.ofMember(BlockNamePacketClientBound::write, BlockNamePacketClientBound::new);

    public BlockNamePacketClientBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readResourceLocation());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeResourceLocation(regName);
    }

    public static void handle(BlockNamePacketClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                BlockNamePacketClientHandler.handlePacket(data.regName);
            }
        });
    }
}