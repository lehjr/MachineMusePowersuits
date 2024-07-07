package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ModeChangeRequestPacketClientBound;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ModeChangeRequestPacketServerBound(int mode) implements CustomPacketPayload {
    public static final Type<ModeChangeRequestPacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "mode_change_request_to_server"));

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, ModeChangeRequestPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(ModeChangeRequestPacketServerBound::write, ModeChangeRequestPacketServerBound::new);

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(mode);
    }

    public ModeChangeRequestPacketServerBound(FriendlyByteBuf packetBuffer) {
            this(packetBuffer.readInt());
    }

    public static void sendToClient(ServerPlayer entity, int mode) {
        NuminaPackets.sendToPlayer(new ModeChangeRequestPacketClientBound(mode), entity);
    }

    public static void handle(ModeChangeRequestPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player  = ctx.player();;
                ItemUtils.setModeAndSwapIfNeeded(player, data.mode);
                if (player instanceof ServerPlayer) {
                    sendToClient((ServerPlayer) player, data.mode);
                }
        });
    }
}

