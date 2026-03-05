package lehjr.powersuits.common.item.module.environmental;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
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
