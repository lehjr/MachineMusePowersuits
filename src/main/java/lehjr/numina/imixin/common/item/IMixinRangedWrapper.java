package lehjr.numina.imixin.common.item;

import com.mojang.datafixers.util.Pair;
import net.minecraftforge.items.wrapper.RangedWrapper;

public interface IMixinRangedWrapper {
    private RangedWrapper self() {
        return (RangedWrapper) this;
    }

    boolean numina$contains(int slot);

    Pair<Integer, Integer> numina$getRange();
}
