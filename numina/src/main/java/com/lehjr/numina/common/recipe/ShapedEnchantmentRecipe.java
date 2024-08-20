package com.lehjr.numina.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ShapedEnchantmentRecipe extends ShapedRecipe {

    public ShapedEnchantmentRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result) {
        super(group, category, pattern, result, true);
    }

    public ShapedEnchantmentRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
        super(group, category, pattern, result, showNotification);
    }

    public ShapedEnchantmentRecipe(ShapedRecipe shaped) {
        super(shaped.getGroup(), shaped.category(), shaped.pattern, shaped.getResultItem(null), shaped.showNotification());
    }

    @Override
    public ItemStack assemble(CraftingInput craftingContainer, HolderLookup.Provider provider) {
        return super.assemble(craftingContainer, provider);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.ENCHANTMENT_RECIPE_SERIALIZER.get();
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    @Override
    public boolean matches(CraftingInput container, Level level) {
        return super.matches(container, level);
    }

    public boolean test(@Nullable ItemStack testStack, Ingredient ingredient) {
        if (testStack == null) {
            return false;
        } else {
            ItemEnchantments testStackEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(testStack);

//            ingredient.dissolve(); // fixme?
            if (ingredient.getItems().length == 0) {
                return testStack.isEmpty();
            } else {
                for(ItemStack itemstack : ingredient.getItems()) {
                    // use enchantment helper instead of calling ItemStack#getEnchantments because that fails on books
                    // really don't even care if the enchantment is on a tool instead of a book
                    ItemEnchantments ingredientStackEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(itemstack);
                    if (!ingredientStackEnchantments.isEmpty() && !testStackEnchantments.isEmpty()) {
                        boolean match = true;
                        // as long as the test stack has the required ingredients at the required level then allow it

                        for (Holder<Enchantment> enchantmentHolder : ingredientStackEnchantments.keySet()) {
                            if (ingredientStackEnchantments.getLevel(enchantmentHolder) > testStackEnchantments.getLevel(enchantmentHolder)) {
                                match = false;
                            }
                        }
                        return match;
                    } else if (itemstack.is(testStack.getItem())) {
                        return true;
                    }
                }
                return false;
            }
        }
    }

    public static class EnchantmentSerializer implements RecipeSerializer<ShapedEnchantmentRecipe> {
        public static final MapCodec<ShapedEnchantmentRecipe> CODEC = RecordCodecBuilder.mapCodec(
                apply -> apply.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.getGroup()),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(recipe -> recipe.category()),
                                ShapedRecipePattern.MAP_CODEC.forGetter(recipe -> recipe.pattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.getResultItem(null)),
                                Codec.BOOL.optionalFieldOf("show_notification", Boolean.valueOf(true)).forGetter(recipe -> recipe.showNotification())
                        )
                        .apply(apply, ShapedEnchantmentRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, ShapedEnchantmentRecipe> STREAM_CODEC = StreamCodec.of(
                EnchantmentSerializer::toNetwork, EnchantmentSerializer::fromNetwork
        );

        @Override
        public MapCodec<ShapedEnchantmentRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapedEnchantmentRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ShapedEnchantmentRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
            String s = byteBuf.readUtf();
            CraftingBookCategory craftingbookcategory = byteBuf.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(byteBuf);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(byteBuf);
            boolean flag = byteBuf.readBoolean();

            // (String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult)
            return new ShapedEnchantmentRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, flag);
        }

        private static void toNetwork(RegistryFriendlyByteBuf byteBuf, ShapedEnchantmentRecipe recipe) {
            byteBuf.writeUtf(recipe.getGroup());
            byteBuf.writeEnum(recipe.category());
            ShapedRecipePattern.STREAM_CODEC.encode(byteBuf, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(byteBuf, recipe.getResultItem(null));
            byteBuf.writeBoolean(recipe.showNotification());
        }
    }
}
