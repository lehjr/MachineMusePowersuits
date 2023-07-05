package lehjr.numina.common.capabilities.module.blockbreaking;

import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public abstract class ToggleableBlockBreakingModule extends ToggleableModule implements IBlockBreakingModule {
    public ToggleableBlockBreakingModule(@NotNull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> moduleConfigGetterIn, boolean defToggleVal) {
        super(module, category, target, moduleConfigGetterIn, defToggleVal);
    }
}
