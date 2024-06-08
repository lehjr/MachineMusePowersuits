package lehjr.numina.mixin.common.item;

import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RangedWrapper.class)
public class MixinRangedWrapper {
    @Final
    @Shadow
    private int minSlot;

    @Final
    @Shadow
    private int maxSlot;

    public boolean contains(int slot) {
        return slot >= this.minSlot && slot < maxSlot;
    }

    public Pair<Integer, Integer> getRange() {
        return Pair.of(minSlot, maxSlot);
    }


}
