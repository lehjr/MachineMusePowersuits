package lehjr.numina.common.capabilities.module.toggleable;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.world.item.ItemStack;

public interface IToggleableModule extends IPowerModule {
    default ItemStack toggleModule(boolean online) {
        return TagUtils.setModuleIsOnline(getModule(), online);
    }


    default ItemStack toggleModule(boolean online, IModularItem item, int moduleIndex) {
        return getModule();
    }

    @Override
    default boolean isModuleOnline() {
        return TagUtils.getModuleIsOnline(getModule());
    }
}
