package com.lehjr.powersuits.common.item.module.miningenchantment;

import com.lehjr.numina.common.capabilities.module.enchantment.EnchantmentModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;


// Note: tried as an enchantment, but failed to function properly due to how block breaking code works
public class AquaAffinityModule extends AbstractPowerModule {
    public static class TickingEnchantment extends EnchantmentModule {
        public TickingEnchantment(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MINING_ENCHANTMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 50000, "FE");
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public Holder<Enchantment> getEnchantment(Level level) {
            return level.holderOrThrow(Enchantments.AQUA_AFFINITY);
        }

        @Override
        public boolean isAllowed() {
            return true;
        }
    }
}