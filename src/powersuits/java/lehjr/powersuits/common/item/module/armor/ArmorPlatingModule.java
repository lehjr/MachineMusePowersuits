package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.powersuits.common.config.ArmorModuleConfig;
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
                    addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, ArmorModuleConfig.ironPlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                    addBaseProperty(NuminaConstants.MAXIMUM_HEAT, ArmorModuleConfig.ironPlatingMaxHeat);
                    addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, ArmorModuleConfig.ironPlatingKnockBackResistance);
                    addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, ArmorModuleConfig.ironPlatingArmorToughness);
                }
                case 2: {
                    addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL,  ArmorModuleConfig.diamondPlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                    addBaseProperty(NuminaConstants.MAXIMUM_HEAT,  ArmorModuleConfig.diamondPlatingMaxHeat);
                    addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE,  ArmorModuleConfig.diamondPlatingKnockBackResistance);
                    addBaseProperty(MPSConstants.ARMOR_TOUGHNESS,  ArmorModuleConfig.diamondPlatingArmorToughness);
                }
                case 3: {
                    addBaseProperty(MPSConstants.ARMOR_VALUE_PHYSICAL, ArmorModuleConfig.netheritePlatingArmorPhysical, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
                    addBaseProperty(NuminaConstants.MAXIMUM_HEAT, ArmorModuleConfig.netheritePlatingMaxHeat);
                    addBaseProperty(MPSConstants.KNOCKBACK_RESISTANCE, ArmorModuleConfig.netheritePlatingKnockBackResistance);
                    addBaseProperty(MPSConstants.ARMOR_TOUGHNESS, ArmorModuleConfig.netheritePlatingArmorToughness);
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
                case 1 -> ArmorModuleConfig.ironPlatingIsAllowed;
                case 2-> ArmorModuleConfig.diamondPlatingIsAllowed;
                case 3-> ArmorModuleConfig.netheritePlatingIsAllowed;
                default -> false;
            };
        }
    }
}
