package lehjr.numina.common.capabilities.render.chameleon;

import lehjr.numina.common.capabilities.CapabilityUpdate;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.BlockPositionPacketServerBound;
import lehjr.numina.common.network.packets.serverbound.BlockStateClearPacketServerBound;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

/**
 * Just a module that renders different based on the block it's set to break
 */
public interface IChameleon extends CapabilityUpdate {

    BlockState getTargetBlockState(Level level);

    @Nonnull
    default ItemStack getStackToRender(Level level) {
        return new ItemStack(getTargetBlockState(level).getBlock());
    }

    default void setTargetBlockStateByPos(BlockPos pos) {
        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new BlockPositionPacketServerBound(pos));
    }

    default void setClearTargetBlockState() {
        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new BlockStateClearPacketServerBound());
    }
}