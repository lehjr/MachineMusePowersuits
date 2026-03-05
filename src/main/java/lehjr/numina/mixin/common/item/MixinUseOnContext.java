package lehjr.numina.mixin.common.item;

import lehjr.numina.imixin.common.item.IUseOnContextMixn;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(UseOnContext.class)
public class MixinUseOnContext implements IUseOnContextMixn {
    @Final
    @Shadow
    private BlockHitResult hitResult;


    @Override
    public BlockHitResult machineMusePowersuits$getBlockHitResult() {
        return hitResult;
    }
}
