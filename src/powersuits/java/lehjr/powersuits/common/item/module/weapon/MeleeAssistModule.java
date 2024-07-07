package lehjr.powersuits.common.item.module.weapon;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class MeleeAssistModule extends AbstractPowerModule {

    public static class PMCap extends PowerModule {

        public PMCap(ItemStack module) {
            super(module, ModuleCategory.WEAPON, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ()->()->MPSCommonConfig.meleeAssistEnergyConsumptionBase, "FE");
            addBaseProperty(MPSConstants.PUNCH_DAMAGE, ()->()->MPSCommonConfig.meleeAssistPunchDamageBase, "pt");
            addTradeoffProperty(MPSConstants.IMPACT, MPSConstants.ENERGY_CONSUMPTION, ()->()->MPSCommonConfig.meleeAssistEnergyConsumptionImpactMultiplier, "FE");
            addTradeoffProperty(MPSConstants.IMPACT, MPSConstants.PUNCH_DAMAGE, ()->()->MPSCommonConfig.meleeAssistPunchDamageImpactMultiplier, "pt");
            addTradeoffProperty(MPSConstants.CARRY_THROUGH, MPSConstants.ENERGY_CONSUMPTION, ()->()->MPSCommonConfig.meleeAssistEnergyConsumptionCarryThroughMultiplier, "FE");
            addTradeoffProperty(MPSConstants.CARRY_THROUGH, MPSConstants.PUNCH_KNOCKBACK, ()->()-> MPSCommonConfig.meleeAssistPunchKnockBackCarryThroughMultiplier, "P");
        }

        @Override
        public int getTier() {
            return 2;
        }

        @Override
        public boolean isAllowed() {
            return MPSCommonConfig.meleeAssistIsAllowed;
        }
    }
}