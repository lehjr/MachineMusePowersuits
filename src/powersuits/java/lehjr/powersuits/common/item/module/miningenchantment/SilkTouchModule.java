package lehjr.powersuits.common.item.module.miningenchantment;

import lehjr.numina.common.capabilities.module.enchantment.EnchantmentModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.powersuits.common.config.module.MiningEnchantmentModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;


public class SilkTouchModule extends AbstractPowerModule {
    public static class TickingEnchantment extends EnchantmentModule {
        public TickingEnchantment(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MINING_ENCHANTMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION, MiningEnchantmentModuleConfig.silkTouchModuleEnergyConsumptionBase, "FE");
        }

        @Override
        public int getEnergyUsage() {
            return (int) (applyPropertyModifiers(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION) * getLevel());
        }

        @Override
        public Holder<Enchantment> getEnchantment(Level level) {
            return level.holderOrThrow(Enchantments.SILK_TOUCH);
        }

        @Override
        public boolean isAllowed() {
            return MiningEnchantmentModuleConfig.silkTouchModuleIsAllowed;
        }
    }
}
