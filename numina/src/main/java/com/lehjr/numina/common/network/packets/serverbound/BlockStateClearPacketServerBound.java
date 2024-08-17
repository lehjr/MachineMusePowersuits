package com.lehjr.numina.common.network.packets.serverbound;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

/**
 * Clears the state for which a certain module would be searching for
 */
public class BlockStateClearPacketServerBound implements CustomPacketPayload {
    public BlockStateClearPacketServerBound() {
    }
    public static final CustomPacketPayload.Type<BlockStateClearPacketServerBound> ID = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "block_state_clear_to_sever"));

    @Override
    @Nonnull
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, BlockStateClearPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(BlockStateClearPacketServerBound::write, BlockStateClearPacketServerBound::new);

    public BlockStateClearPacketServerBound(FriendlyByteBuf packetBuffer) {
        this();
    }

    public void write(FriendlyByteBuf packetBuffer) {
    }

    public static void handle(BlockStateClearPacketServerBound ignored, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (player instanceof ServerPlayer) {
                try {
                    IModeChangingItem mci = ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.MAINHAND).getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
                    if(mci != null) {
                        mci.setModuleBlockState(ItemUtils.getRegistryName(mci.getActiveModule()), Blocks.AIR.defaultBlockState());
                    }
                } catch (Exception e) {
                    NuminaLogger.logException("failed to clear blockstate: ", e);
                }
            }
        }).exceptionally(e -> {
            // Handle exception
            ctx.disconnect(Component.translatable(NuminaConstants.MOD_ID + ".networking.failed", e.getMessage()));
            return null;
        });
    }
}