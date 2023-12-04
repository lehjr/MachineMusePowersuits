package lehjr.numina.common.capabilities.module.externalitems;

import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public class OtherModItemsAsModules extends RightClickModule implements IOtherModItemsAsModules {
    public OtherModItemsAsModules(@NotNull ItemStack module, ModuleCategory category, Callable<IConfig> moduleConfigGetterIn) {
        super(module, category, ModuleTarget.TOOLONLY, moduleConfigGetterIn);
    }

    @Override
    public void setModuleStack(@NotNull ItemStack stack) {
        super.module = stack;
    }
}