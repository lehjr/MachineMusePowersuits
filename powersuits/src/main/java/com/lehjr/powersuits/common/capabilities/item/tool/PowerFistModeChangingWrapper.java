package com.lehjr.powersuits.common.capabilities.item.tool;

import com.lehjr.numina.common.capabilities.inventory.modechanging.ModeChangingModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
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
                rangedWrapperMap.put(ModuleCategory.PICKAXE, new RangedWrapper(this, 1, 2));
            }
            if (size > 2) {
                rangedWrapperMap.put(ModuleCategory.SHOVEL, new RangedWrapper(this, 2, 3));
            }
            if (size > 3) {
                rangedWrapperMap.put(ModuleCategory.AXE, new RangedWrapper(this, 3, 4));
            }
            if (size > 4) {
                rangedWrapperMap.put(ModuleCategory.HOE, new RangedWrapper(this, 4, 5));
            }
            if (size > 5) {
                rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 5, this.getSlots()));
            }
        }
        this.setRangedWrapperMap(rangedWrapperMap);
    }

    @Override
    public boolean isTool() {
        return true;
    }
}
