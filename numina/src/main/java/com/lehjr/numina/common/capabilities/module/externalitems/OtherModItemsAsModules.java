package com.lehjr.numina.common.capabilities.module.externalitems;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class OtherModItemsAsModules extends RightClickModule implements IOtherModItemsAsModules {
    public OtherModItemsAsModules(ItemStack module, ModuleCategory category) {
        super(module, category, ModuleTarget.TOOLONLY);
    }

    public OtherModItemsAsModules(ItemStack module, ModuleTarget target, ModuleCategory category) {
        super(module, category, target);
    }

    @Override
    public void setModuleStack(@Nonnull ItemStack stack) {

    }
}
