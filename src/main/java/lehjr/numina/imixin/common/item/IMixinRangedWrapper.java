package lehjr.numina.imixin.common.item;

import com.mojang.datafixers.util.Pair;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

public interface IMixinRangedWrapper {
    private RangedWrapper self() {
        return (RangedWrapper) this;
    }

    boolean numina$contains(int slot);

    Pair<Integer, Integer> numina$getRange();
}
