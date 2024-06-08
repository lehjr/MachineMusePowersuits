package lehjr.numina.common.capabilities.module.rightclick;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import net.minecraft.world.item.ItemStack;

public class RightClickModule extends PowerModule implements IRightClickModule {
    public RightClickModule(ItemStack module ,ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }
}