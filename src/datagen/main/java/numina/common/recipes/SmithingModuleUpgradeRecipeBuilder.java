package numina.common.recipes;

import lehjr.numina.common.recipe.SmithingModuleUpgradeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmithingModuleUpgradeRecipeBuilder {
    private final Ingredient template;
    private final Ingredient base;
    private final SizedIngredient addition;
    private final RecipeCategory category;
    private final Item result;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public SmithingModuleUpgradeRecipeBuilder(Ingredient template, Ingredient base, SizedIngredient addition, RecipeCategory category, Item result) {
        this.category = category;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public static SmithingModuleUpgradeRecipeBuilder smithing(
                                                                Ingredient template,
                                                                Ingredient base,
                                                                SizedIngredient addition,
                                                                RecipeCategory category,
                                                                Item result) {
        return new SmithingModuleUpgradeRecipeBuilder(template, base, addition, category, result);
    }

    public SmithingModuleUpgradeRecipeBuilder unlockedBy(String key, Criterion<?> criterion) {
        this.criteria.put(key, criterion);
        return this;
    }

    public void save(RecipeOutput recipeOutput) {
        this.save(recipeOutput, getDefaultRecipeId(result));
    }

    static ResourceLocation getDefaultRecipeId(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem());
    }

    public void save(RecipeOutput recipeOutput, String recipeId) {
        this.save(recipeOutput, ResourceLocation.parse(recipeId));
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        Advancement.Builder advancementBuilder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
            .rewards(AdvancementRewards.Builder.recipe(recipeId))
            .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);
        SmithingModuleUpgradeRecipe smithingtransformrecipe = new SmithingModuleUpgradeRecipe(this.template, this.base, this.addition, new ItemStack(this.result));
        recipeOutput.accept(recipeId, smithingtransformrecipe, advancementBuilder.build(recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation location) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + location);
        }
    }
}
