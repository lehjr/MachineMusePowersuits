package com.lehjr.numina.common.recipe.ingredients;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.registration.NuminaIngredientTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Neoforge enchanted ingredient example, but with ItemStack since ItemStack is ignored anyway
 */
public class MinEnchantedIngredient implements ICustomIngredient {
    final ItemStack item = new ItemStack(Items.ENCHANTED_BOOK);
    private final Map<Holder<Enchantment>, Integer> enchantments;
    // The codec for serializing the ingredient.
    public static final MapCodec<MinEnchantedIngredient> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
//            ItemStack.SIMPLE_ITEM_CODEC.fieldOf("item").forGetter(itemValue -> itemValue.item),
            Codec.unboundedMap(Enchantment.CODEC, Codec.INT).optionalFieldOf("enchantments", Map.of()).forGetter(enchantedIngredient -> enchantedIngredient.enchantments)
//            Codec.unboundedMap(Enchantment.CODEC, Codec.INT).optionalFieldOf("exclusive_set/mining", Map.of()).forGetter(enchantedIngredient -> enchantedIngredient.enchantments)
        ).apply(instance, MinEnchantedIngredient::new));

    // EnchantmentTags.MINING_EXCLUSIVE) = "exclusive_set/mining"

    // Create a stream codec from the regular codec. In some cases, it might make sense to define
    // a new stream codec from scratch.
    public static final StreamCodec<RegistryFriendlyByteBuf, MinEnchantedIngredient> STREAM_CODEC =
            ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    // Allow passing in a pre-existing map of enchantments to levels.
    public MinEnchantedIngredient(Map<Holder<Enchantment>, Integer> enchantments) {
        NuminaLogger.logError("enchantments size init: " + enchantments.size());

        this.enchantments = enchantments;
    }

    // Check if the passed ItemStack matches our ingredient by verifying the item is in the tag
    // and by testing for presence of all required enchantments with at least the required level.
    //
    // Nope, just check if enchantments match and ignore the ItemStack
    @Override
    public boolean test(ItemStack stack) {
        return /*stack.is(tag) && */enchantments.keySet()
                .stream()
                .allMatch(ench -> stack.getEnchantmentLevel(ench) >= enchantments.get(ench));
    }

    // Determines whether this ingredient performs NBT or data component matching (false) or not (true).
    // Also determines whether a stream codec is used for syncing, more on this later.
    // We query enchantments on the stack, therefore our ingredient is not simple.
    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return NuminaIngredientTypes.MIN_ENCHANTED.get();
    }

    @Override
    public Ingredient toVanilla() {
        return ICustomIngredient.super.toVanilla();
    }

    // Returns a stream of items that match this ingredient. Mostly for display purposes.
    // There's a few good practices to follow here:
    // - Always include at least one item stack, to prevent accidental recognition as empty.
    // - Include each accepted Item at least once.
    // - If #isSimple is true, this should be exact and contain every item stack that matches.
    //   If not, this should be as exact as possible, but doesn't need to be super accurate.
    // In our case, we use all items in the tag, each with the required enchantments.
    @Override
    public Stream<ItemStack> getItems() {
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(item);

        // Enchant all stacks with all enchantments.
        for (ItemStack stack : stacks) {
            enchantments.keySet().forEach(ench -> stack.enchant(ench, enchantments.get(ench)));
        }
        // Return stream variant of the list.
        return stacks.stream();
    }
}
