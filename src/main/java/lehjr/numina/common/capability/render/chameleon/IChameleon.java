package lehjr.numina.common.capability.render.chameleon;

import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.BlockPositionPacketServerBound;
import lehjr.numina.common.network.packets.serverbound.BlockStateClearPacketServerBound;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

/**
 * Just a module that renders different based on the block it's set to break
 */
public interface IChameleon {

    BlockState getTargetBlockState();

    @Nonnull
    default ItemStack getStackToRender() {
        return new ItemStack(getTargetBlockState().getBlock());
    }

    default void setTargetBlockStateByPos(BlockPos pos) {
        NuminaPackets.sendToServer(new BlockPositionPacketServerBound(pos));
    }

    default void setClearTargetBlockState() {
        NuminaPackets.sendToServer(new BlockStateClearPacketServerBound());
    }
}