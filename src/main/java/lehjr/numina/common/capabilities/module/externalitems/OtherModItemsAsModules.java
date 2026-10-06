package lehjr.numina.common.capabilities.module.externalitems;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class OtherModItemsAsModules extends RightClickModule implements IOtherModItemsAsModules {
    final int tier;
    public OtherModItemsAsModules(ItemStack module, ModuleCategory category, boolean allowed) {
        this(module, category, 1, allowed);
    }

    public OtherModItemsAsModules(ItemStack module, ModuleTarget target, ModuleCategory category, boolean allowed) {
        this(module, target, category, 1, allowed);
    }

    public OtherModItemsAsModules(ItemStack module, ModuleTarget target, ModuleCategory category, int tier, boolean allowed) {
        super(module, category, target, allowed);
        this.tier = tier;
    }

    public OtherModItemsAsModules(ItemStack module, ModuleCategory category, int tier, boolean allowed) {
        super(module, category, ModuleTarget.TOOLONLY,  allowed);
        this.tier = tier;
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
