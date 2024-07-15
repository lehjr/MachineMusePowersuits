package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.BlockNamePacketClientBound;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public record BlockPositionPacketServerBound(BlockPos pos) implements CustomPacketPayload {
    public static final Type<BlockPositionPacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "block_pos_to_sever"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, BlockPositionPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(BlockPositionPacketServerBound::write, BlockPositionPacketServerBound::new);

    public BlockPositionPacketServerBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readBlockPos());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeBlockPos(pos);
    }

    // No longer needed due to proper sync implementation
//    public static void sendToClient(ServerPlayer entity, ResourceLocation regName) {
//        NuminaPackets.sendToPlayer(new BlockNamePacketClientBound(regName), entity);
//    }

    public static void handle(BlockPositionPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (player instanceof ServerPlayer && data.pos != null) {
                try {
                    NuminaCapabilities.getModeChangingModularItemCapability(ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.MAINHAND))
                            .ifPresent(handler -> handler.setModuleBlockState(ItemUtils.getRegistryName(handler.getActiveModule()), player.level()
                                    .getBlockState(data.pos)));
                }catch (Exception e) {
                    NuminaLogger.logException("failed to change block position: ", e);
                }
            }
        }).exceptionally(e -> {
            // Handle exception
            ctx.disconnect(Component.translatable(NuminaConstants.MOD_ID + ".networking.failed", e.getMessage()));
            return null;
        });
    }
}
