package lehjr.numina.common.capability.render.highlight;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.phys.BlockHitResult;

public class Highlight implements IHighlight {
    public Highlight() {
    }

    @Override
    public NonNullList<BlockPos> getBlockPositions(BlockHitResult result) {
        return NonNullList.of(result.getBlockPos());
    }
}