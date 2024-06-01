package lehjr.numina.common.capabilities.module.hud;

import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public class HudModule extends ToggleableModule implements IHudModule {
    public HudModule(@NotNull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> moduleConfigGetterIn, boolean defToggleVal) {
        super(module, category, target, moduleConfigGetterIn, defToggleVal);
    }
}
