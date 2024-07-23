package lehjr.powersuits.common.item.module.armor;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.PlayerTickModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.ArmorModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class EnergyShieldModule extends AbstractPowerModule {
    public static class EnergyShieldCapabilityWrapper extends PlayerTickModule {
        public EnergyShieldCapabilityWrapper(ItemStack module) {
            super(module, ModuleCategory.ARMOR, ModuleTarget.ARMORONLY);
            addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_VALUE_ENERGY, ArmorModuleConfig.energyShieldFieldStrengthMultiplier, NuminaConstants.MODULE_TRADEOFF_PREFIX + MPSConstants.ARMOR_POINTS);
            addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_ENERGY_CONSUMPTION, ArmorModuleConfig.energyShieldEnergyConsumptionMultiplier, "FE");
            addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, NuminaConstants.MAXIMUM_HEAT, ArmorModuleConfig.energyShieldMaxHeatMultiplier);
            addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.ARMOR_TOUGHNESS, ArmorModuleConfig.energyShieldMaxHeatMultiplier);
            addTradeoffProperty(MPSConstants.MODULE_FIELD_STRENGTH, MPSConstants.KNOCKBACK_RESISTANCE, ArmorModuleConfig.energyShieldKnockBackResistanceMultiplier);
        }

        @Override
        public String getModuleGroup() {
            return "Armor";
        }

        @Override
        public int getTier() {
            return 4;
        }

        @Override
        public boolean isAllowed() {
            return ArmorModuleConfig.energyShieldIsAllowed;
        }

        @Override
        public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item) {
            double energy = ElectricItemUtils.getPlayerEnergy(player);
            double energyUsage = applyPropertyModifiers(MPSConstants.ARMOR_ENERGY_CONSUMPTION);

            if (energy < energyUsage) {
                // turn off module if energy is too low. This will fire on both sides so no need to sync
                IModularItem mi = item.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
                if (mi !=null) {
                    mi.toggleModule(MPSConstants.ENERGY_SHIELD_MODULE, false);
                }
            }
        }
    }
}
