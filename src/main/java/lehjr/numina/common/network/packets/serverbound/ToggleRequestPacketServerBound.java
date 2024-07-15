package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ToggleRequestPacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public record ToggleRequestPacketServerBound(ResourceLocation registryName, boolean toggleval) implements CustomPacketPayload {
    public static final Type<ToggleRequestPacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "toggle_request_to_server"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, ToggleRequestPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(ToggleRequestPacketServerBound::write, ToggleRequestPacketServerBound::new);

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeResourceLocation(registryName);
        packetBuffer.writeBoolean(toggleval);
    }

    public ToggleRequestPacketServerBound(FriendlyByteBuf packetBuffer) {
            this(packetBuffer.readResourceLocation(), packetBuffer.readBoolean()
        );
    }

    public static void handle(ToggleRequestPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    NuminaCapabilities.getModularItemOrModeChangingCapability(player.getInventory().getItem(i))
                            .ifPresent(handler -> handler.toggleModule(data.registryName, data.toggleval));
                }
        });
    }
}