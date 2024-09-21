package com.lehjr.numina.common.capabilities.module.toggleable;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import com.lehjr.numina.common.utils.TagUtils;
import net.minecraft.world.item.ItemStack;

public class ToggleableModule extends PowerModule implements IToggleableModule {
    boolean online;

    public ToggleableModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }

    public ToggleableModule(ItemStack module, ModuleCategory category, ModuleTarget target, boolean isAllowed) {
        super(module, category, target, isAllowed);
    }
}
