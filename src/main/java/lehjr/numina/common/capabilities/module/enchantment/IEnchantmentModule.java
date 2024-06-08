package lehjr.numina.common.capabilities.module.enchantment;

import lehjr.numina.common.capabilities.module.tickable.IPlayerTickModule;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import javax.annotation.Nonnull;
import java.util.Map;

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