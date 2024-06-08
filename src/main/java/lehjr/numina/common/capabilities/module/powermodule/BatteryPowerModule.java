package lehjr.numina.common.capabilities.module.powermodule;

import net.minecraft.world.item.ItemStack;

public class BatteryPowerModule extends PowerModule {
    int tier;
    public BatteryPowerModule(ItemStack module, int tier) {
        super(module, ModuleCategory.ENERGY_STORAGE, ModuleTarget.ALLITEMS);
        this.tier = tier;
    }

    @Override
    public int getTier() {
        return super.getTier();
    }
}
