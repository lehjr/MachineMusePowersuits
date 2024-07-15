package lehjr.powersuits.common.item.module.miningenchantment;

import lehjr.numina.common.capability.module.enchantment.EnchantmentModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import javax.annotation.Nonnull;


public class SilkTouchModule extends AbstractPowerModule {
    public static class TickingEnchantment extends EnchantmentModule {
        public TickingEnchantment(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MINING_ENCHANTMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION, 50000, "FE");
        }

        @Override
        public int getEnergyUsage() {
            return (int) (applyPropertyModifiers(MPSConstants.SILK_TOUCH_ENERGY_CONSUMPTION) * getLevel());
        }

        @Override
        public Enchantment getEnchantment() {
            return Enchantments.SILK_TOUCH;
        }
    }
}