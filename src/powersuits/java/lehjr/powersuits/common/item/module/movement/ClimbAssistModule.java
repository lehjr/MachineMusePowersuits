package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import lehjr.powersuits.common.config.module.MovementModuleConfig;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class ClimbAssistModule extends AbstractPowerModule {
    public static class ClimbAssistCap extends ToggleableModule {
        public ClimbAssistCap(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.LEGSONLY);
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.climbAssistModuleIsAllowed;
        }
    }
}
