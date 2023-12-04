package lehjr.numina.imixin.common.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;

public interface IUseOnContextMixn {
    private UseOnContext self() {
        return (UseOnContext)this;
    }

    BlockHitResult machineMusePowersuits$getBlockHitResult();
}
