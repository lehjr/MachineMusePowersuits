package com.lehjr.numina.common.capabilities.module.enchantment;

import com.lehjr.numina.common.capabilities.module.tickable.IPlayerTickModule;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import javax.annotation.Nonnull;

public interface IEnchantmentModule extends IPlayerTickModule {
    /**
     * enable enchantment
     */
    @Nonnull
    default ItemStack addEnchantment(@Nonnull ItemStack itemStack) {
        // get stored enchantments (NonNull)
        ItemEnchantments itemenchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(itemenchantments);
        mutable.set(getEnchantment(), getLevel());
        ItemEnchantments itemenchantments1 = mutable.toImmutable();
        itemStack.set(DataComponents.ENCHANTMENTS, itemenchantments1);
        return itemStack;
    }

    /**
     * disable enchantment
     */
    @Nonnull
    default ItemStack removeEnchantment(@Nonnull ItemStack itemStack) {
        ItemEnchantments itemenchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(itemenchantments);
        mutable.set(getEnchantment(), -1);
        ItemEnchantments itemenchantments1 = mutable.toImmutable();
        itemStack.set(DataComponents.ENCHANTMENTS, itemenchantments1);
        return itemStack;
    }

    void setAdded(boolean added);

    void setRemoved(boolean removed);

    Holder<Enchantment> getEnchantment();

    int getLevel();
}