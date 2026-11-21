package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Sets the state for which a specific module will search for when block breaking
 * @param pos
 */
public record BlockPositionPacketServerBound(BlockPos pos)  {
    public static void encode(BlockPositionPacketServerBound msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static BlockPositionPacketServerBound decode(FriendlyByteBuf buf) {
        return new BlockPositionPacketServerBound(buf.readBlockPos());
    }

    public static void handle(BlockPositionPacketServerBound data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final Player player = ctx.get().getSender();
            if (player instanceof ServerPlayer && data.pos != null) {
                try {
                    IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(player.getMainHandItem());
                    if(mci != null) {
//                        NuminaLogger.logDebug("trying to set blockstate");
                        mci.setModuleBlockState(ItemUtils.getRegistryName(mci.getActiveModule()), player.level().getBlockState(data.pos));
                    }
                } catch (Exception e) {
                    NuminaLogger.logException("failed to change block position: ", e);
                }
            }
        });
    }
}
