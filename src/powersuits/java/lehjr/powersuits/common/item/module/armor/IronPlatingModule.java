package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class IronPlatingModule extends AbstractPowerModule {
    public static class IronArmorPlatingCapabilityWrapper extends PowerModule {
        public IronArmorPlatingCapabilityWrapper(ItemStack module) {
            super(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY);

            addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL,
                    ()-> () -> MPSCommonConfig.ironPlatingArmorPhysical,
                    NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);

            addBaseProperty(NuminaConstants.MAXIMUM_HEAT,
                    ()-> () -> MPSCommonConfig.ironPlatingMaxHeat);

            addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE,
                    ()-> () -> MPSCommonConfig.ironPlatingKnockBackResistance);

            addBaseProperty(MPSConstants.ARMOR_TOUGHNESS,
                    ()-> () -> MPSCommonConfig.ironPlatingArmorToughness);
        }

        @Override
        public String getModuleGroup() {
            return "Armor";
        }

        @Override
        public int getTier() {
            return 1;
        }

        @Override
        public boolean isAllowed() {
            return MPSCommonConfig.ironPlatingIsAllowed;
        }
    }
}