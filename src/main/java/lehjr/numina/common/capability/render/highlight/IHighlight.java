package lehjr.numina.common.capability.render.highlight;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.phys.BlockHitResult;

/**
 * used on the client side only
 */
public interface IHighlight {
    NonNullList<BlockPos> getBlockPositions(BlockHitResult result);
}