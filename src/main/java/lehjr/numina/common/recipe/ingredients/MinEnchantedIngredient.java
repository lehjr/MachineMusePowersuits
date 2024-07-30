package lehjr.numina.common.recipe.ingredients;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lehjr.numina.common.base.NuminaObjects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;

import java.util.*;
import java.util.stream.Stream;

/**
 * Neoforge enchanted ingredient example
 */
public class MinEnchantedIngredient implements ICustomIngredient {
    private final TagKey<Item> tag;
    private final Map<Holder<Enchantment>, Integer> enchantments;
    // The codec for serializing the ingredient.
    public static final MapCodec<MinEnchantedIngredient> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(TagKey.codec(Registries.ITEM).fieldOf("tag")
                            .forGetter(enchantedIngredient -> enchantedIngredient.tag),
            Codec.unboundedMap(BuiltInRegistries.ENCHANTMENT.holderByNameCodec(), Codec.INT)
                    .optionalFieldOf("enchantments", Map.of())
                    .forGetter(e -> e.enchantments)
    ).apply(instance, MinEnchantedIngredient::new));


    // Create a stream codec from the regular codec. In some cases, it might make sense to define
    // a new stream codec from scratch.
    public static final StreamCodec<RegistryFriendlyByteBuf, MinEnchantedIngredient> STREAM_CODEC =
            ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    // Allow passing in a pre-existing map of enchantments to levels.
    public MinEnchantedIngredient(TagKey<Item> tag, Map<Holder<Enchantment>, Integer> enchantments) {
        this.tag = tag;
        this.enchantments = enchantments;
    }

    public MinEnchantedIngredient(TagKey<Item> tag) {
        this(tag, new HashMap<>());
    }


    // Make this chainable for easy use.
    public MinEnchantedIngredient with(Holder<Enchantment> enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

//    public static MinEnchantedIngredient of(ItemLike... pItems) {
//        return of(Arrays.stream(pItems).map(ItemStack::new));
//    }
//
//    public static MinEnchantedIngredient of(ItemStack... pStacks) {
//        return of(Arrays.stream(pStacks));
//    }

//    public static MinEnchantedIngredient of(Stream<ItemStack> pStacks) {
//        return fromValues(pStacks.filter(p_43944_ -> !p_43944_.isEmpty()).map(Ingredient.ItemValue::new));
//    }

//    public static MinEnchantedIngredient of(TagKey<Item> pTag) {
//        return fromValues(Stream.of(new Ingredient.TagValue(pTag)));
//    }















    // Check if the passed ItemStack matches our ingredient by verifying the item is in the tag
    // and by testing for presence of all required enchantments with at least the required level.
    //
    // Nope, just check if enchantments match and ignore the ItemStack
    @Override
    public boolean test(ItemStack stack) {
        return /*stack.is(tag) && */enchantments.keySet()
                .stream()
                .allMatch(ench -> stack.getEnchantmentLevel(ench.value()) >= enchantments.get(ench));
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
        return NuminaObjects.MIN_ENCHANTED.get();
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
        // Get a list of item stacks, one stack per item in the tag.
        List<ItemStack> stacks = BuiltInRegistries.ITEM
                .getOrCreateTag(tag)
                .stream()
                .map(ItemStack::new)
                .toList();
        // Enchant all stacks with all enchantments.
        for (ItemStack stack : stacks) {
            enchantments.keySet().forEach(ench -> stack.enchant(ench.value(), enchantments.get(ench)));
        }
        // Return stream variant of the list.
        return stacks.stream();
    }



//    // Neo: Do not extend this interface. For custom ingredient behaviors see ICustomIngredient.
//    public interface Value {
//        MapCodec<Ingredient.Value> MAP_CODEC = NeoForgeExtraCodecs.xor(Ingredient.ItemValue.MAP_CODEC, Ingredient.TagValue.MAP_CODEC)
//                .xmap(either -> either.map(itemValue -> itemValue, tagValue -> tagValue), value -> {
//                    if (value instanceof Ingredient.TagValue ingredient$tagvalue) {
//                        return Either.right(ingredient$tagvalue);
//                    } else if (value instanceof Ingredient.ItemValue ingredient$itemvalue) {
//                        return Either.left(ingredient$itemvalue);
//                    } else {
//                        throw new UnsupportedOperationException("This is neither an item value nor a tag value.");
//                    }
//                });
//        Codec<Ingredient.Value> CODEC = MAP_CODEC.codec();
//
//        Collection<ItemStack> getItems();
//    }
}
