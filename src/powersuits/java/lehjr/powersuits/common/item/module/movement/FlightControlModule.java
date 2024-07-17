package lehjr.powersuits.common.item.module.movement;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.toggleable.ToggleableModule;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class FlightControlModule extends AbstractPowerModule {
    public static class Toggler extends ToggleableModule {
        public Toggler(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.HEADONLY);
            addTradeoffProperty(MPSConstants.VERTICALITY, MPSConstants.FLIGHT_VERTICALITY, 1.0F, "%");
        }

        @Override
        public boolean isAllowed() {
            return true;
        }
    }
}