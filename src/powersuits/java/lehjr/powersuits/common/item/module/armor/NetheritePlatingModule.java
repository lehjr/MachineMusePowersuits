package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class NetheritePlatingModule extends AbstractPowerModule {
    public static class NetheriteArmorPlatingCapabilityWrapper extends PowerModule {
        public NetheriteArmorPlatingCapabilityWrapper(ItemStack module) {
            super(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY);

            addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL,
                    () -> () -> MPSCommonConfig.netheritePlatingArmorPhysical,
                    NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);

            addBaseProperty(NuminaConstants.MAXIMUM_HEAT,
                    () -> () -> MPSCommonConfig.netheritePlatingMaxHeat);

            addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE,
                    () -> () -> MPSCommonConfig.netheritePlatingKnockBackResistance);

            addBaseProperty(MPSConstants.ARMOR_TOUGHNESS,
                    () -> () -> MPSCommonConfig.netheritePlatingArmorToughness);
        }

        @Override
        public String getModuleGroup() {
            return "Armor";
        }

        @Override
        public int getTier() {
            return 3;
        }

        @Override
        public boolean isAllowed() {
            return MPSCommonConfig.netheritePlatingIsAllowed;
        }
    }
}