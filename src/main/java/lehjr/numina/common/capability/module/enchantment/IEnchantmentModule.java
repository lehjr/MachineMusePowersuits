package lehjr.numina.common.capability.module.enchantment;

import lehjr.numina.common.capability.module.tickable.IPlayerTickModule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import javax.annotation.Nonnull;

public interface IEnchantmentModule extends IPlayerTickModule {
    /**
     * enable enchantment
     */
    @Nonnull
    default ItemStack addEnchantment(@Nonnull ItemStack itemStack) {
        // FIXME!!!
//        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
//        enchantments.put(getEnchantment(), getLevel());
//        EnchantmentHelper.setEnchantments(enchantments, itemStack);
        return itemStack;
    }

    /**
     * disable enchantment
     */
    @Nonnull
    default ItemStack removeEnchantment(@Nonnull ItemStack itemStack) {
        // FIXME!!

//        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
//        enchantments.remove(getEnchantment());
//        EnchantmentHelper.setEnchantments(enchantments, itemStack);
        return itemStack;
    }

    void setAdded(boolean added);

    void setRemoved(boolean removed);

    Enchantment getEnchantment();

    int getLevel();
}