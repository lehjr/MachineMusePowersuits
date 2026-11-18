package lehjr.powersuits.common.item.module.cosmetic;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import lehjr.powersuits.common.config.module.CosmeticModuleConfig;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class TransparentArmorModule extends AbstractPowerModule {

    public static class TransparentArmorCap extends ToggleableModule {
        public TransparentArmorCap(ItemStack module) {
            super(module, ModuleCategory.COSMETIC, ModuleTarget.ARMORONLY);
        }

        @Override
        public int getTier() {
            return 2;
        }

        @Override
        public String getModuleGroup() {
            return "";
        }

        @Override
        public boolean isAllowed() {
            return CosmeticModuleConfig.isTransparentArmorAllowed;
        }
    }
}
