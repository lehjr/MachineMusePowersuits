package lehjr.powersuits.common.capabilities.item.tool;

import lehjr.numina.common.capability.inventory.modechanging.ModeChangingModularItem;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PowerFistModeChangingWrapper extends ModeChangingModularItem {
    public PowerFistModeChangingWrapper(@Nonnull ItemStack modularItem, int tier, int size) {
        super(modularItem, tier, (int)Mth.absMax(size, 4));
        Map<ModuleCategory, RangedWrapper> rangedWrapperMap = new HashMap<>();
        if (size > 0) {
            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 0, 1));
            if (size > 1) {
                rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 1, this.getSlots()));
            }
        }
        this.setRangedWrapperMap(rangedWrapperMap);
    }

    @Override
    public boolean isTool() {
        return true;
    }
}