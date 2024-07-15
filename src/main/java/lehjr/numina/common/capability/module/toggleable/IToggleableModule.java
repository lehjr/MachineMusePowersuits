package lehjr.numina.common.capability.module.toggleable;

import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.world.item.ItemStack;

public interface IToggleableModule extends IPowerModule {
    default ItemStack toggleModule(boolean online) {
        return TagUtils.setModuleIsOnline(getModule(), online);
    }

    @Override
    default boolean isModuleOnline() {
        return TagUtils.getModuleIsOnline(getModule());
    }
}