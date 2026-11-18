package lehjr.numina.common.capabilities.module.externalitems;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class OtherModItemsAsModules extends RightClickModule implements IOtherModItemsAsModules {
    final int tier;
    public OtherModItemsAsModules(ItemStack module, ModuleCategory category) {
        this(module, category, 1);
    }

    public OtherModItemsAsModules(ItemStack module, ModuleTarget target, ModuleCategory category) {
        this(module, target, category, 1);
    }

    public OtherModItemsAsModules(ItemStack module, ModuleTarget target, ModuleCategory category, int tier) {
        super(module, category, target);
        this.tier = tier;
    }

    public OtherModItemsAsModules(ItemStack module, ModuleCategory category, int tier) {
        super(module, category, ModuleTarget.TOOLONLY);
        this.tier = tier;
    }

    @Override
    public boolean isModuleOnline() {
        return true;
    }

    @Override
    public void setModuleStack(@Nonnull ItemStack stack) {
        super.module = stack;
    }

    @Override
    public int getTier() {
        return tier;
    }
}
