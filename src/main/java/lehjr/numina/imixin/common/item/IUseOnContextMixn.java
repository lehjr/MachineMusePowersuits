package lehjr.numina.imixin.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public interface IUseOnContextMixn {
    private UseOnContext self() {
        return (UseOnContext)this;
    }

    default BlockHitResult machineMusePowersuits$getBlockHitResult() {
        return BlockHitResult.miss(Vec3.ZERO, Direction.DOWN, BlockPos.ZERO);
    }
}
