package lehjr.powersuits.common.item.module.cosmetic;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.toggleable.ToggleableModule;
import lehjr.powersuits.common.config.CosmeticModuleConfig;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class TransparentArmorModule extends AbstractPowerModule {

    public static class TransParentArmorCap extends ToggleableModule {
        public TransParentArmorCap(ItemStack module) {
            super(module, ModuleCategory.COSMETIC, ModuleTarget.ARMORONLY);
        }

        @Override
        public int getTier() {
            return 2;
        }

        @Override
        public boolean isAllowed() {
            return CosmeticModuleConfig.isTransparentArmorAllowed;
        }
    }
}