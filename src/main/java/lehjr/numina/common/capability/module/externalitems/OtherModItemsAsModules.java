package lehjr.numina.common.capability.module.externalitems;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.RightClickModule;
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