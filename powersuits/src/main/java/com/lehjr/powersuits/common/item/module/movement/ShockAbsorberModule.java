package com.lehjr.powersuits.common.item.module.movement;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import com.lehjr.powersuits.common.config.module.MovementModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class ShockAbsorberModule extends AbstractPowerModule {
    public static class Toggler extends ToggleableModule {
        public Toggler(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MOVEMENT, ModuleTarget.FEETONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.shockAbsorberEnergyConsumptionBase, "FE/m");
            addTradeoffProperty(MPSConstants.POWER, MPSConstants.ENERGY_CONSUMPTION, MovementModuleConfig.shockAbsorberEnergyConsumptionPowerMultiplier);
            addBaseProperty(MPSConstants.DISTANCE_REDUCTION, MovementModuleConfig.shockAbsorberDistanceReductionBase, "%");
            addTradeoffProperty(MPSConstants.POWER, MPSConstants.DISTANCE_REDUCTION, MovementModuleConfig.shockAbsorberDistanceReductionPowerMultiplier);
        }

        @Override
        public boolean isAllowed() {
            return MovementModuleConfig.shockAbsorberModuleIsAllowed;
        }
    }
}
