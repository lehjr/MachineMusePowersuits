package com.lehjr.numina.common.network.packets.serverbound;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

/**
 * Sets the state for which a specific module will search for when block breaking
 * @param pos
 */
public record BlockPositionPacketServerBound(BlockPos pos) implements CustomPacketPayload {
    public static final Type<BlockPositionPacketServerBound> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "block_pos_to_sever"));

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

    public static void handle(BlockPositionPacketServerBound data, IPayloadContext ctx) {
        NuminaLogger.logDebug("trying to set blockstate pt1");

        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (player instanceof ServerPlayer && data.pos != null) {
                try {
                    IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(player.getMainHandItem());
                    if(mci != null) {
                        NuminaLogger.logDebug("trying to set blockstate");
                        mci.setModuleBlockState(ItemUtils.getRegistryName(mci.getActiveModule()), player.level().getBlockState(data.pos));
                    }
                } catch (Exception e) {
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
