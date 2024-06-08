package lehjr.numina.common.capabilities.render.hud;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.world.item.ItemStack;

public class HudModule extends ToggleableModule implements IHudModule {
    public HudModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }
}
