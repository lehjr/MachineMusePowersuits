package com.lehjr.numina.common.capabilities.module.toggleable;

import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.utils.TagUtils;
import net.minecraft.world.item.ItemStack;

public interface IToggleableModule extends IPowerModule {
    default ItemStack toggleModule(boolean online) {
        return TagUtils.setModuleIsOnline(getModule(), online);
    }

    @Override
    default boolean isModuleOnline() {
        return TagUtils.getModuleIsOnline(getModule());
    }
}