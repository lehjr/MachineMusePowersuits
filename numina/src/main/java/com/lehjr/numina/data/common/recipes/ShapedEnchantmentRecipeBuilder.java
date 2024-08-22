package com.lehjr.numina.data.common.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.recipe.ShapedEnchantmentRecipe;
import com.lehjr.numina.common.recipe.ingredients.MinEnchantedIngredient;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShapedEnchantmentRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private int count;
    private final Item result;
    private final ItemStack resultStack; // Neo: add stack result support
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    @Nullable
    private String group;
    private boolean showNotification = true;

    public ShapedEnchantmentRecipeBuilder(RecipeCategory pCategory, ItemLike result, int count) {
        this(pCategory, new ItemStack(result, count));
    }

    public ShapedEnchantmentRecipeBuilder(RecipeCategory category, ItemStack result) {
        this.category = category;
        this.result = result.getItem();
        this.count = result.getCount();
        this.resultStack = result;
    }


    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedEnchantmentRecipeBuilder shaped(RecipeCategory category, ItemLike result, int count) {
        return new ShapedEnchantmentRecipeBuilder(category, result, count);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedEnchantmentRecipeBuilder shaped(RecipeCategory category, ItemLike result) {
        return shaped(category, result, 1);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedEnchantmentRecipeBuilder shaped(RecipeCategory category, ItemStack result) {
        return new ShapedEnchantmentRecipeBuilder(category, result);
    }

    // --------------------------------------------------------------------------------------------
    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedEnchantmentRecipeBuilder define(Character character, TagKey<Item> tag) {
        return this.define(character, Ingredient.of(tag));
    }


    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedEnchantmentRecipeBuilder define(Character character, Holder<Enchantment> enchantment, int enchLevel) {
        NuminaLogger.logError("Trying to define enchanted ingredient with exclusive tag");

        Map<Holder<Enchantment>, Integer> enchantments = getNewEnchantmentsMap();

        if (!enchantments.isEmpty()) {
            for(Map.Entry<Holder<Enchantment>, Integer> entry: enchantments.entrySet()) {
                // FIXME!!
                if(entry.getKey().is(enchantment)) {
                    if(entry.getValue() < enchLevel) {
                        enchantments.replace(enchantment, enchLevel);
                    }
                }
            }
        } else {
            enchantments.put(enchantment, enchLevel);
        }

        MinEnchantedIngredient ingredient = new MinEnchantedIngredient(enchantments);
        return this.define(character, ingredient.toVanilla());
    }


    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedEnchantmentRecipeBuilder define(Character character, ItemLike item) {
        return this.define(character, Ingredient.of(item));
    }

    public static HashMap<Holder<Enchantment>, Integer> getNewEnchantmentsMap() {
        return new HashMap<>();
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedEnchantmentRecipeBuilder define(Character pSymbol, Ingredient ingredient) {
        if (this.key.containsKey(pSymbol)) {
            throw new IllegalArgumentException("Symbol '" + pSymbol + "' is already defined!");
        } else if (pSymbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(pSymbol, ingredient);
            return this;
        }
    }

    /**
     * Adds a new entry to the patterns for this recipe.
     */
    public ShapedEnchantmentRecipeBuilder pattern(String pPattern) {
        if (!this.rows.isEmpty() && pPattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pPattern);
            return this;
        }
    }

    public ShapedEnchantmentRecipeBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
        this.criteria.put(pName, pCriterion);
        return this;
    }

    public ShapedEnchantmentRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public ShapedEnchantmentRecipeBuilder showNotification(boolean pShowNotification) {
        this.showNotification = pShowNotification;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation location) {
        ShapedRecipePattern shapedrecipepattern = this.ensureValid(location);
        Advancement.Builder advancement$builder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location))
            .rewards(AdvancementRewards.Builder.recipe(location))
            .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancement$builder::addCriterion);
        ShapedEnchantmentRecipe shapedrecipe = new ShapedEnchantmentRecipe(
            Objects.requireNonNullElse(this.group, ""),
            RecipeBuilder.determineBookCategory(this.category),
            shapedrecipepattern,
            this.resultStack,
            this.showNotification
        );
        recipeOutput.accept(location, shapedrecipe, advancement$builder.build(location.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private ShapedRecipePattern ensureValid(ResourceLocation pLoaction) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pLoaction);
        } else {
            return ShapedRecipePattern.of(this.key, this.rows);
        }
    }
}
