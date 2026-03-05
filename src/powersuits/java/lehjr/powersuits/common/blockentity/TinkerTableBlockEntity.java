package lehjr.powersuits.common.blockentity;

import lehjr.powersuits.common.registration.MPSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TinkerTableBlockEntity extends BlockEntity {
    public TinkerTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MPSBlocks.TINKER_TABLE_BLOCKENTITY_TYPE.get(), pPos, pBlockState);
    }
}
