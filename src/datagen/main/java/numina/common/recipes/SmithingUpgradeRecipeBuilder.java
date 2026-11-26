package numina.common.recipes;

import com.google.gson.JsonObject;
import lehjr.numina.common.registration.RecipeSerializersRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SmithingUpgradeRecipeBuilder {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final int additionCount;
    private final RecipeCategory category;
    private final Item result;
    private final Map<String, CriterionTriggerInstance> criteria = new LinkedHashMap<>();

    public SmithingUpgradeRecipeBuilder(Ingredient template, Ingredient base, Ingredient addition, int additionCount, RecipeCategory category, Item result) {
        this.category = category;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.additionCount = Math.max(1, additionCount);
        this.result = result;
    }

    public SmithingUpgradeRecipeBuilder(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, Item result) {
        this(template, base, addition, 1, category, result);
    }

    public static SmithingUpgradeRecipeBuilder smithing(
            Ingredient template,
            Ingredient base,
            Ingredient addition,
            int additionCount,
            RecipeCategory category,
            Item result) {
        return new SmithingUpgradeRecipeBuilder(template, base, addition, additionCount, category, result);
    }

    public static SmithingUpgradeRecipeBuilder smithing(
            Ingredient template,
            Ingredient base,
            Ingredient addition,
            RecipeCategory category,
            Item result) {
        return new SmithingUpgradeRecipeBuilder(template, base, addition, 1, category, result);
    }

    public SmithingUpgradeRecipeBuilder unlockedBy(String key, CriterionTriggerInstance criterion) {
        this.criteria.put(key, criterion);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        ResourceLocation loc = getDefaultRecipeId(result);
        this.save(consumer, ResourceLocation.tryBuild(loc.getNamespace(), loc.getPath() + "_smithing_upgrade"));
    }

    static ResourceLocation getDefaultRecipeId(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem());
    }

    public void save(Consumer<FinishedRecipe> consumer, String recipeId) {
        this.save(consumer, ResourceLocation.tryBuild(recipeId));
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        Advancement.Builder advancementBuilder = Advancement.Builder.recipeAdvancement()
                .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(RequirementsStrategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);

        consumer.accept(new Result(
                recipeId,
                this.template,
                this.base,
                this.addition,
                this.additionCount,
                this.result,
                advancementBuilder,
                recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")
        ));
    }

    private void ensureValid(ResourceLocation location) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + location);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient template;
        private final Ingredient base;
        private final Ingredient addition;
        private final int additionCount;
        private final Item result;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition, int additionCount,
                      Item result, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.template = template;
            this.base = base;
            this.addition = addition;
            this.additionCount = additionCount;
            this.result = result;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("template", this.template.toJson());
            json.add("base", this.base.toJson());

            if (this.additionCount > 1) {
                JsonObject additionObj = new JsonObject();
                additionObj.add("ingredient", this.addition.toJson());
                additionObj.addProperty("count", this.additionCount);
                json.add("addition", additionObj);
            } else {
                json.add("addition", this.addition.toJson());
            }

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            json.add("result", resultObj);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializersRegistry.SMITHING_UPGRADE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}