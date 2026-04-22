//package numina.common.recipes;
//
//import net.minecraft.advancements.Advancement;
//import net.minecraft.advancements.AdvancementRequirements;
//import net.minecraft.advancements.AdvancementRewards;
//import net.minecraft.advancements.Criterion;
//import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
//import net.minecraft.data.recipes.RecipeCategory;
//import net.minecraft.data.recipes.RecipeOutput;
//import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.SmithingTransformRecipe;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class SmithingModularItemUpgradeRecipeBuilder {
//    package net.minecraft.data.recipes;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import net.minecraft.advancements.Advancement;
//import net.minecraft.advancements.AdvancementRequirements;
//import net.minecraft.advancements.AdvancementRewards;
//import net.minecraft.advancements.Criterion;
//import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.SmithingTransformRecipe;
//
//    public class SmithingTransformRecipeBuilder {
//        private final Ingredient template;
//        private final Ingredient base;
//        private final Ingredient addition;
//        private final RecipeCategory category;
//        private final Item result;
//        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
//
//        public SmithingTransformRecipeBuilder(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, Item result) {
//            this.category = category;
//            this.template = template;
//            this.base = base;
//            this.addition = addition;
//            this.result = result;
//        }
//
//        public static net.minecraft.data.recipes.SmithingTransformRecipeBuilder smithing(
//            Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, Item result
//        ) {
//            return new net.minecraft.data.recipes.SmithingTransformRecipeBuilder(template, base, addition, category, result);
//        }
//
//        public net.minecraft.data.recipes.SmithingTransformRecipeBuilder unlocks(String key, Criterion<?> criterion) {
//            this.criteria.put(key, criterion);
//            return this;
//        }
//
//        public void save(RecipeOutput recipeOutput, String recipeId) {
//            this.save(recipeOutput, ResourceLocation.parse(recipeId));
//        }
//
//        public void save(RecipeOutput recipeOutput, ResourceLocation recipeId) {
//            this.ensureValid(recipeId);
//            Advancement.Builder advancement$builder = recipeOutput.advancement()
//                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
//                .rewards(AdvancementRewards.Builder.recipe(recipeId))
//                .requirements(AdvancementRequirements.Strategy.OR);
//            this.criteria.forEach(advancement$builder::addCriterion);
//            SmithingTransformRecipe smithingtransformrecipe = new SmithingTransformRecipe(this.template, this.base, this.addition, new ItemStack(this.result));
//            recipeOutput.accept(recipeId, smithingtransformrecipe, advancement$builder.build(recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
//        }
//
//        private void ensureValid(ResourceLocation location) {
//            if (this.criteria.isEmpty()) {
//                throw new IllegalStateException("No way of obtaining recipe " + location);
//            }
//        }
//    }
//
//}
