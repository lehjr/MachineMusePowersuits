package lehjr.numina.common.capabilities.module.blockbreaking;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class ToggleableBlockBreakingModule extends ToggleableModule implements IBlockBreakingModule {
    public ToggleableBlockBreakingModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }
}
