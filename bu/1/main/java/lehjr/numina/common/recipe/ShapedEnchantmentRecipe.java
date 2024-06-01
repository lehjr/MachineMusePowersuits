package lehjr.numina.common.recipe;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class ShapedEnchantmentRecipe extends ShapedRecipe {

    public ShapedEnchantmentRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result) {
        super(group, category, pattern, result, true);
    }

    public ShapedEnchantmentRecipe(ShapedRecipe shaped) {
        super(shaped.getId(), shaped.getGroup(), shaped.category(), shaped.getWidth(), shaped.getRecipeHeight(), shaped.getIngredients(), shaped.getResultItem(null), shaped.showNotification());
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        return super.assemble(container, registryAccess);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.ENCHANTMENT_RECIPE_SERIALIZER.get();
    }

    /**
     * fixes enchantent lvl tag being wrong type when loaded from Json.
     * @param itemStack
     * @return
     */
    public static ItemStack enchantmentFixer(@Nonnull ItemStack itemStack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
        ListTag listTag = itemStack.getEnchantmentTags();
        for (Tag tag : listTag) {
            if (tag instanceof CompoundTag) {
                ResourceLocation enchantmentID = EnchantmentHelper.getEnchantmentId((CompoundTag) tag);
                int lvl = ((CompoundTag) tag).contains("lvl") ? ((CompoundTag) tag).getInt("lvl") : 1;
                Enchantment enchantment = BuiltInRegistries.ENCHANTMENT.get(enchantmentID);
                enchantments.put(enchantment, lvl);
            }
        }
        EnchantmentHelper.setEnchantments(enchantments, itemStack);
        return itemStack;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    @Override
    public boolean matches(CraftingContainer container, Level level) {
        for(int i = 0; i < container.getWidth(); ++i) {
            for(int j = 0; j < container.getHeight(); ++j) {
                int k = i - pWidth;
                int l = j - pHeight;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (pMirrored) {
                        ingredient = this.recipeItems.get(this.width - k - 1 + l * this.width);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.width);
                    }
                }

                ItemStack testStack = container.getItem(i + j * container.getWidth());
                if (!test(testStack, ingredient)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean test(@Nullable ItemStack testStack, Ingredient ingredient) {
        // use enchantment helper instead of calling ItemStack#getEnchantments because that fails on books
        Map<Enchantment, Integer> testStackEnchantments = EnchantmentHelper.getEnchantments(testStack);
        if (testStack == null) {
            return false;
        } else {
//            ingredient.dissolve(); // fixme?
            if (ingredient.getItems().length == 0) {
                return testStack.isEmpty();
            } else {
                for(ItemStack itemstack : ingredient.getItems()) {
                    // use enchantment helper instead of calling ItemStack#getEnchantments because that fails on books
                    // really don't even care if the enchantment is on a tool instead of a book
                    Map<Enchantment, Integer> ingredientStackEnchantments = EnchantmentHelper.getEnchantments(itemstack);
                    if (ingredientStackEnchantments.size() > 0 && testStackEnchantments.size() > 0) {
                        boolean match = true;
                        // as long as the test stack has the required ingredients at the required level then allow it
                        for (Enchantment enchantment : ingredientStackEnchantments.keySet()) {
                            if (ingredientStackEnchantments.getOrDefault(enchantment, 1) > testStackEnchantments.getOrDefault(enchantment, 0)) {
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

    static Map<String, Ingredient> keyFromJson(JsonObject pKeyEntry) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : pKeyEntry.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            if (entry.getValue().toString().contains("StoredEnchantments")) {
                JsonElement element = entry.getValue();
                Ingredient ing;
                if (element instanceof JsonObject) {
                    ItemStack ingStack = itemStackFromJson((JsonObject) element);
                    ingStack = enchantmentFixer(ingStack);
                    ing = Ingredient.of(ingStack);
                } else {
                    ing = Ingredient.fromJson(entry.getValue());
                }
                map.put(entry.getKey(), ing);
            } else {
                map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
            }
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static class EnchantmentSerializer extends Serializer {


        @Override
        public ShapedEnchantmentRecipe fromJson(ResourceLocation id, JsonObject pJson) {
            String group = GsonHelper.getAsString(pJson, "group", "");
            Map<String, Ingredient> map = keyFromJson(GsonHelper.getAsJsonObject(pJson, "key"));
            String[] astring = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(pJson, "pattern")));
            int width = astring[0].length();
            int height = astring.length;
            NonNullList<Ingredient> ingredients = ShapedRecipe.dissolvePattern(astring, map, width, height);
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
            return new ShapedEnchantmentRecipe(
                    id,
                    group,
                    CraftingBookCategory.EQUIPMENT, // fixme: using as a place holder until working
                    width,
                    height,
                    ingredients,
                    result);
        }


//        public static Ingredient fromNetwork(FriendlyByteBuf pBuffer) {
//            var size = pBuffer.readVarInt();
//            if (size == -1) return net.minecraftforge.common.crafting.CraftingHelper.getIngredient(pBuffer.readResourceLocation(), pBuffer);
//            final ItemStack ingStack = enchantmentFixer(pBuffer.readItem());
//            return Ingredient.fromValues(Stream.generate(() -> new Ingredient.ItemValue(ingStack)).limit(size));
//        }

        // TODO: check if anything needed here
//        @Override
//        public void toNetwork(FriendlyByteBuf pBuffer, ShapedRecipe pRecipe) {
//            super.toNetwork(pBuffer, pRecipe);
//        }

        // FIXME: is this needed?
//        @Override
//        public ShapedEnchantmentRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
//            NuminaLogger.logDebug("from network");
//
//
//            int i = pBuffer.readVarInt();
//            int j = pBuffer.readVarInt();
//            String s = pBuffer.readUtf();
//            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);
//
//            for(int k = 0; k < nonnulllist.size(); ++k) {
//                // FIXME: check if ingredient has enchantment tag and delegate to corresponding handler
//                Ingredient ing = fromNetwork(pBuffer);
//                if (!ing.isEmpty()) {
//                    ItemStack[] ingItems = ing.getItems();
//                    // todo: parse list and check for enchantment book. Then parse the enchantments and repair them
//
//                }
//
//
//
//                nonnulllist.set(k, ing); // FIXME: check if
//            }
//
//            ItemStack itemstack = pBuffer.readItem();
//            return new ShapedEnchantmentRecipe(pRecipeId, s, i, j, nonnulllist, itemstack);
//
//
//
//
////            return new ShapedEnchantmentRecipe(super.fromNetwork(pRecipeId, pBuffer));
//        }
    }
}
