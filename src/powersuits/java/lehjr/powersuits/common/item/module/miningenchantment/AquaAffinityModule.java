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
        public Enchantment getEnchantment() {
            return Enchantments.AQUA_AFFINITY;
        }
    }
}