package com.lehjr.numina.common.network.packets.serverbound;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record ModeChangeRequestPacketServerBound(int mode) implements CustomPacketPayload {
    public static final Type<ModeChangeRequestPacketServerBound> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "mode_change_request_to_server"));

    @Override
    @Nonnull
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

    // So far does not appear to be needed
//    public static void sendToClient(ServerPlayer entity, int mode) {
//        NuminaPackets.sendToPlayer(new ModeChangeRequestPacketClientBound(mode), entity);
//    }

    public static void handle(ModeChangeRequestPacketServerBound data, @Nonnull IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
                    Player player  = ctx.player();;
                    boolean handled = ItemUtils.setModeAndSwapIfNeeded(player, data.mode);
                    // Hold off on this for now, causes server hangs... and really shouldn't need it if data sync works correctly
//                if (player instanceof ServerPlayer) {
//                    sendToClient((ServerPlayer) player, data.mode);
//                }
                })
                .exceptionally(e -> {
                    // Handle exception
                    ctx.disconnect(Component.translatable(NuminaConstants.MOD_ID + ".networking.failed", e.getMessage()));
                    return null;
                });
    }
}

