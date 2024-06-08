package lehjr.numina.common.capabilities.module.externalitems;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OtherModItemsAsModules extends RightClickModule implements IOtherModItemsAsModules {
    public OtherModItemsAsModules(ItemStack module, ModuleCategory category) {
        super(module, category, ModuleTarget.TOOLONLY);
    }

    public OtherModItemsAsModules(ItemStack module, ModuleTarget target, ModuleCategory category) {
        super(module, category, target);
    }

    @Override
    public void setModuleStack(@NotNull ItemStack stack) {

    }
}