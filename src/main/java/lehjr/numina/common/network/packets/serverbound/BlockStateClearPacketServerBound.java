package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Clears the state for which a certain module would be searching for
 */
public class BlockStateClearPacketServerBound {
    public static void encode(BlockStateClearPacketServerBound msg, FriendlyByteBuf buf) {}

    public static  BlockStateClearPacketServerBound decode(FriendlyByteBuf buf) {
        return new BlockStateClearPacketServerBound();
    }

    public static void handle(BlockStateClearPacketServerBound ignored, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final Player player = ctx.get().getSender();
            if (player instanceof ServerPlayer) {
                try {
                    IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(player.getMainHandItem());
                    if(mci != null) {
                        mci.setModuleBlockState(ItemUtils.getRegistryName(mci.getActiveModule()), Blocks.AIR.defaultBlockState());
                    }
                } catch (Exception e) {
                    NuminaLogger.logException("failed to clear blockstate: ", e);
                }
            }
        });
    }
}
