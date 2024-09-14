package com.lehjr.powersuits.common.item.module.weapon;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import com.lehjr.powersuits.common.config.module.WeaponModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class MeleeAssistModule extends AbstractPowerModule {

    public static class PMCap extends PowerModule {

        public PMCap(ItemStack module) {
            super(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, WeaponModuleConfig.meleeAssistEnergyConsumptionBase, "FE");
            addBaseProperty(MPSConstants.PUNCH_DAMAGE, WeaponModuleConfig.meleeAssistPunchDamageBase, "pt");
            addTradeoffProperty(MPSConstants.IMPACT, MPSConstants.ENERGY_CONSUMPTION, WeaponModuleConfig.meleeAssistEnergyConsumptionImpactMultiplier, "FE");
            addTradeoffProperty(MPSConstants.IMPACT, MPSConstants.PUNCH_DAMAGE, WeaponModuleConfig.meleeAssistPunchDamageImpactMultiplier, "pt");
            addTradeoffProperty(MPSConstants.CARRY_THROUGH, MPSConstants.ENERGY_CONSUMPTION, WeaponModuleConfig.meleeAssistEnergyConsumptionCarryThroughMultiplier, "FE");
            addTradeoffProperty(MPSConstants.CARRY_THROUGH, MPSConstants.PUNCH_KNOCKBACK,  WeaponModuleConfig.meleeAssistPunchKnockBackCarryThroughMultiplier, "P");
        }

        @Override
        public int getTier() {
            return 2;
        }

        @Override
        public boolean isAllowed() {
            return WeaponModuleConfig.meleeAssistIsAllowed;
        }
    }
}
