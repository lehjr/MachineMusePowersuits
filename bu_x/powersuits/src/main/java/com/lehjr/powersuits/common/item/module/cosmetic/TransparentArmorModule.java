package com.lehjr.powersuits.common.item.module.cosmetic;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import com.lehjr.powersuits.common.config.CosmeticModuleConfig;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
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