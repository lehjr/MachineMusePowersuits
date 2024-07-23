package lehjr.numina.common.capability.module.toggleable;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.world.item.ItemStack;

public class ToggleableModule extends PowerModule implements IToggleableModule {
    boolean online;

    public ToggleableModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
        load();
    }

    public ToggleableModule(ItemStack module, ModuleCategory category, ModuleTarget target, boolean isAllowed) {
        super(module, category, target, isAllowed);
        load();
    }

    @Override
    public ItemStack toggleModule(boolean online) {
        this.online = online;
        return save();
    }

    @Override
    public boolean isModuleOnline() {
        return online;
    }

    @Override
    public ItemStack save() {
        return TagUtils.setModuleIsOnline(getModule(), online);
    }

    @Override
    public void load() {
        this.online = TagUtils.getModuleIsOnline(getModule());
    }
}