package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.config.MPSCommonConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;

public class ArmorPlatingModule extends AbstractPowerModule {
    public static class ArmorPlatingCapabilityWrapper extends PowerModule {
        int tier;


        public ArmorPlatingCapabilityWrapper(ItemStack module, int tier) {
            super(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY);
            this.tier = tier;
            switch (tier) {
                case 1: {
                    addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, () -> () -> MPSCommonConfig.ironPlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                    addBaseProperty(NuminaConstants.MAXIMUM_HEAT, () -> () -> MPSCommonConfig.ironPlatingMaxHeat);
                    addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, () -> () -> MPSCommonConfig.ironPlatingKnockBackResistance);
                    addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, () -> () -> MPSCommonConfig.ironPlatingArmorToughness);
                }
                case 2: {
                    addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, ()-> () -> MPSCommonConfig.diamondPlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                    addBaseProperty(NuminaConstants.MAXIMUM_HEAT, ()-> () -> MPSCommonConfig.diamondPlatingMaxHeat);
                    addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, ()-> () -> MPSCommonConfig.diamondPlatingKnockBackResistance);
                    addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, ()-> () -> MPSCommonConfig.diamondPlatingArmorToughness);
                }
                case 3: {
                    addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, () -> () -> MPSCommonConfig.netheritePlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                    addBaseProperty(NuminaConstants.MAXIMUM_HEAT, () -> () -> MPSCommonConfig.netheritePlatingMaxHeat);
                    addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, () -> () -> MPSCommonConfig.netheritePlatingKnockBackResistance);
                    addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, () -> () -> MPSCommonConfig.netheritePlatingArmorToughness);
                }
            }
        }

        @Override
        public String getModuleGroup() {
            return "Armor";
        }

        @Override
        public int getTier() {
            return tier;
        }

        @Override
        public boolean isAllowed() {
            return switch (tier) {
                case 1 -> MPSCommonConfig.ironPlatingIsAllowed;
                case 2-> MPSCommonConfig.diamondPlatingIsAllowed;
                case 3-> MPSCommonConfig.netheritePlatingIsAllowed;
                default -> false;
            };
        }
    }
}
