package lehjr.numina.common.capabilities.render.hud;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class HudModule extends ToggleableModule implements IHudModule {
    public HudModule(@NotNull ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }
}
