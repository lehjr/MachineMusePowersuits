package lehjr.powersuits.common.item.module.miningenchantment;


import com.lehjr.numina.common.capabilities.module.enchantment.EnchantmentModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.utils.TagUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import javax.annotation.Nonnull;

public class FortuneModule extends AbstractPowerModule {
    public static class TickingEnchantment extends EnchantmentModule {
        boolean added;
        boolean removed;
        public TickingEnchantment(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MINING_ENCHANTMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.FORTUNE_ENERGY_CONSUMPTION, 500, "FE");
            addTradeoffProperty(MPSConstants.ENCHANTMENT_LEVEL, MPSConstants.FORTUNE_ENERGY_CONSUMPTION, 9500);
            addIntTradeoffProperty(MPSConstants.ENCHANTMENT_LEVEL, MPSConstants.FORTUNE_ENCHANTMENT_LEVEL, 3, "", 1, 1);

            // setting to and loading these just allow values to be persistant when capability reloads
            added = TagUtils.getModuleBoolean(module, "added");
            removed = TagUtils.getModuleBoolean(module, "removed");
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.FORTUNE_ENERGY_CONSUMPTION);
        }

        @Override
        public Enchantment getEnchantment() {
            return Enchantments.FORTUNE;
        }

        @Override
        public int getLevel() {
            return (int) applyPropertyModifiers(MPSConstants.FORTUNE_ENCHANTMENT_LEVEL);
        }

        @Override
        public boolean isAllowed() {
            return true;
        }
    }
}