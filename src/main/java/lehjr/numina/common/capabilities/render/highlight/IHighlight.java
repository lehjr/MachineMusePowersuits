package lehjr.numina.common.capabilities.render.highlight;

import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

/**
 * used on the client side only
 */
public interface IHighlight {
    NonNullList<BlockPos> getBlockPositions(BlockRayTraceResult result);
}