package lehjr.powersuits.common.capabilities.item.tool;

import lehjr.numina.common.capability.inventory.modechanging.ModeChangingModularItem;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.powersuits.common.config.MPSCommonConfig;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PowerFistModeChangingWrapper extends ModeChangingModularItem {

    public PowerFistModeChangingWrapper(@Nonnull ItemStack modularItem, int tier) {
        this(modularItem, tier, getActualSize(tier));
    }


    public PowerFistModeChangingWrapper(@Nonnull ItemStack modularItem, int tier, int size) {
        super(modularItem, tier, size);
        Map<ModuleCategory, RangedWrapper> rangedWrapperMap = new HashMap<>();
        rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new RangedWrapper(this, 0, 1));
        rangedWrapperMap.put(ModuleCategory.NONE, new RangedWrapper(this, 1, this.getSlots() ));
        this.setRangedWrapperMap(rangedWrapperMap);
    }

    public static int getActualSize(int tier) {
        if (MPSCommonConfig.COMMON_SPEC.isLoaded()) {
            switch (tier) {
                case 1 -> {
                    return MPSCommonConfig.powerFistInventorySlots1;
                }
                case 2 -> {
                    return MPSCommonConfig.powerFistInventorySlots2;
                }
                case 3 -> {
                    return MPSCommonConfig.powerFistInventorySlots3;
                }
                case 4 -> {
                    return MPSCommonConfig.powerFistInventorySlots4;
                }
            }
        }
        return 4;
    }

    @Override
    public boolean isTool() {
        return true;
    }
}