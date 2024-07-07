package lehjr.numina.common.capability.module.toggleable;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ToggleableModule extends PowerModule implements IToggleableModule {
    boolean online;

    public ToggleableModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
        load(getModuleTag());
    }

    @Override
    public void toggleModule(boolean online) {
        this.online = online;
        save();
    }

    @Override
    public boolean isModuleOnline() {
        return online;
    }

    @Override
    public void save() {
        TagUtils.setModuleBoolean(getModule(), NuminaConstants.TAG_ONLINE, online);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.online = TagUtils.getModuleBoolean(getModule(), NuminaConstants.TAG_ONLINE);
    }
}