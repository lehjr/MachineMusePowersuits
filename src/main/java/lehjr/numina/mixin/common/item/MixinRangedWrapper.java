package lehjr.numina.mixin.common.item;

import com.mojang.datafixers.util.Pair;
import lehjr.numina.imixin.common.item.IMixinRangedWrapper;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RangedWrapper.class)
public class MixinRangedWrapper implements IMixinRangedWrapper {
    @Final
    @Shadow
    private int minSlot;

    @Final
    @Shadow
    private int maxSlot;

    @Override
    public boolean numina$contains(int slot) {
        return slot >= minSlot && slot < maxSlot;
    }

    @Override
    public Pair<Integer, Integer> numina$getRange() {
        return Pair.of(minSlot, maxSlot);
    }
}
