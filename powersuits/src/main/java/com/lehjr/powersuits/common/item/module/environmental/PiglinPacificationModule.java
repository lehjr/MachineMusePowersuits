package com.lehjr.powersuits.common.item.module.environmental;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import com.lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class PiglinPacificationModule extends AbstractPowerModule {
    public static class PiglinPacificationCap extends PowerModule {
        public PiglinPacificationCap(ItemStack module) {
            super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY);
        }

        @Override
        public boolean isAllowed() {
            return EnvironmentalModuleConfig.piglinPacificationIsAllowed;
        }
    }
}
