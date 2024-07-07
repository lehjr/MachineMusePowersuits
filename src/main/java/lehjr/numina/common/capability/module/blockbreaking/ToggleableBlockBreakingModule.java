package lehjr.numina.common.capability.module.blockbreaking;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.toggleable.ToggleableModule;
import net.minecraft.world.item.ItemStack;

public abstract class ToggleableBlockBreakingModule extends ToggleableModule implements IBlockBreakingModule {
    public ToggleableBlockBreakingModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }
}
