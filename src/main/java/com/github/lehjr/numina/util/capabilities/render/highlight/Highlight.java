package com.github.lehjr.numina.util.capabilities.render.highlight;

import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

public class Highlight implements IHighlight {
    public Highlight() {
    }

    @Override
    public NonNullList<BlockPos> getBlockPositions(BlockRayTraceResult result) {
        return NonNullList.of(result.getBlockPos());
    }
}