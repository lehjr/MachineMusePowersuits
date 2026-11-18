package lehjr.numina.common.capabilities.module.toggleable;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import net.minecraft.world.item.ItemStack;

public abstract class ToggleableModule extends PowerModule implements IToggleableModule {
    public ToggleableModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }
}
