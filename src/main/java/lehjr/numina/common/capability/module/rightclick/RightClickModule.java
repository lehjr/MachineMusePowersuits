package lehjr.numina.common.capability.module.rightclick;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import net.minecraft.world.item.ItemStack;

public class RightClickModule extends PowerModule implements IRightClickModule {
    public RightClickModule(ItemStack module ,ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }
}