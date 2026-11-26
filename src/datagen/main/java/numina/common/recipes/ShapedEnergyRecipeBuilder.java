package numina.common.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
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
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ShapedEnergyRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final int count;
    private final Item result;
    private final ItemStack resultStack;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Map<String, CriterionTriggerInstance> criteria = new LinkedHashMap<>();

    @Nullable
    private String group;
    private boolean showNotification = true;

    public ShapedEnergyRecipeBuilder(RecipeCategory pCategory, ItemLike result, int count) {
        this(pCategory, new ItemStack(result, count));
    }

    public ShapedEnergyRecipeBuilder(RecipeCategory category, ItemStack result) {
        this.category = category;
        this.result = result.getItem();
        this.count = result.getCount();
        this.resultStack = result;
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedEnergyRecipeBuilder shaped(RecipeCategory category, ItemLike result, int count) {
        return new ShapedEnergyRecipeBuilder(category, result, count);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedEnergyRecipeBuilder shaped(RecipeCategory category, ItemLike result) {
        return shaped(category, result, 1);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedEnergyRecipeBuilder shaped(RecipeCategory category, ItemStack result) {
        return new ShapedEnergyRecipeBuilder(category, result);
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedEnergyRecipeBuilder define(Character character, TagKey<Item> tag) {
        return this.define(character, Ingredient.of(tag));
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedEnergyRecipeBuilder define(Character character, ItemLike item) {
        return this.define(character, Ingredient.of(item));
    }

    /**
     * Adds a key to the recipe pattern.
     */
    public ShapedEnergyRecipeBuilder define(Character pSymbol, Ingredient ingredient) {
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
    public ShapedEnergyRecipeBuilder pattern(String pPattern) {
        if (!this.rows.isEmpty() && pPattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pPattern);
            return this;
        }
    }

    @Override
    public ShapedEnergyRecipeBuilder unlockedBy(String pName, CriterionTriggerInstance pCriterion) {
        this.criteria.put(pName, pCriterion);
        return this;
    }

    @Override
    public ShapedEnergyRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public ShapedEnergyRecipeBuilder showNotification(boolean pShowNotification) {
        this.showNotification = pShowNotification;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        Advancement.Builder advancementBuilder = Advancement.Builder.recipeAdvancement()
                .parent(ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId))
                .requirements(RequirementsStrategy.OR);

        this.criteria.forEach(advancementBuilder::addCriterion);

        pFinishedRecipeConsumer.accept(new Result(
                pRecipeId,
                this.resultStack,
                this.group == null ? "" : this.group,
                determineBookCategory(this.category),
                this.rows,
                this.key,
                advancementBuilder,
                pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"),
                this.showNotification
        ));
    }

    private static CraftingBookCategory determineBookCategory(RecipeCategory category) {
        return switch (category) {
            case BUILDING_BLOCKS -> CraftingBookCategory.BUILDING;
            case TOOLS, COMBAT -> CraftingBookCategory.EQUIPMENT;
            case REDSTONE -> CraftingBookCategory.REDSTONE;
            default -> CraftingBookCategory.MISC;
        };
    }

    private void ensureValid(ResourceLocation pLocation) {
        if (this.rows.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + pLocation + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for (String s : this.rows) {
                for (int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + pLocation + " uses undefined symbol '" + c0 + "'");
                    }
                    set.remove(c0);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + pLocation);
            } else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
                throw new IllegalStateException("Shaped recipe " + pLocation + " only takes in a single item - should it be a shapeless recipe instead?");
            } else if (this.criteria.isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + pLocation);
            }
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final String group;
        private final CraftingBookCategory category;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final boolean showNotification;

        public Result(ResourceLocation pId, ItemStack pResult, String pGroup, CraftingBookCategory pCategory,
                      List<String> pPattern, Map<Character, Ingredient> pKey, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId, boolean pShowNotification) {
            this.id = pId;
            this.result = pResult;
            this.group = pGroup;
            this.category = pCategory;
            this.pattern = pPattern;
            this.key = pKey;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.showNotification = pShowNotification;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            pJson.addProperty("category", this.category.getSerializedName());

            JsonArray jsonArray = new JsonArray();
            for (String row : this.pattern) {
                jsonArray.add(row);
            }
            pJson.add("pattern", jsonArray);

            JsonObject keyJson = new JsonObject();
            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                keyJson.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }
            pJson.add("key", keyJson);

            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result.getItem()).toString());
            if (this.result.getCount() > 1) {
                resultJson.addProperty("count", this.result.getCount());
            }
            if (this.result.hasTag()) {
                resultJson.addProperty("nbt", this.result.getTag().toString());
            }
            pJson.add("result", resultJson);
            pJson.addProperty("show_notification", this.showNotification);
        }
        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializersRegistry.ENERGY_RECIPE_SERIALIZER.get();
        }
        @Override
        public ResourceLocation getId() {
            return this.id;
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