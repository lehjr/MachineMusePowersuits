package lehjr.powersuits.common.item.module.movement;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class ShockAbsorberModule extends AbstractPowerModule {
    public static class Toggler extends ToggleableModule {
        public Toggler(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.FEETONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 0, "FE/m");
            addTradeoffProperty(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, 100);
            addBaseProperty(MPSConstants.MULTIPLIER, 0, "%");
            addTradeoffProperty(MPSConstants.POWER, MPSConstants.MULTIPLIER, 10);
        }

        @Override
        public boolean isAllowed() {
            return true;
        }
    }
}