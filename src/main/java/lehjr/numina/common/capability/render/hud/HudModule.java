package lehjr.numina.common.capability.render.hud;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.toggleable.ToggleableModule;
import net.minecraft.world.item.ItemStack;

public class HudModule extends ToggleableModule implements IHudModule {
    public HudModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }

    public HudModule(ItemStack module, ModuleCategory category, ModuleTarget target, boolean isAllowed) {
        super(module, category, target, isAllowed);
    }
}
