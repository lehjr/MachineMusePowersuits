package lehjr.mpsrecipecreator.client.gui;

import com.google.gson.*;
import lehjr.mpsrecipecreator.container.MPARCContainer;
import lehjr.numina.common.tags.NBT2Json;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lehjr
 */
public class RecipeGen {
    private Map<Integer, Integer> oreTagIndices = new HashMap<>();
    public Map<Integer, Boolean> useOredict = new HashMap<>();
    MPARCContainer container;
    RecipeOptionsFrame recipeOptions;

    public RecipeGen(MPARCContainer containerIn, RecipeOptionsFrame recipeOptionsIn) {
        this.container = containerIn;
        this.recipeOptions = recipeOptionsIn;
        reset();
    }

    public void reset() {
        oreTagIndices = new HashMap<>();
        useOredict = new HashMap<>();
        for (int id = 0; id < 10; id ++) {
            useOredict.put(id, false);
        }
    }

    /**
     * @param slot index
     * @return ItemStack from container slot
     */
    @Nonnull
    ItemStack getStack(int slot) {
        return container.getSlot(slot).getItem();
    }

    public int setOreDictIndexForward(int slot) {
        return setOreTagIndex(slot, oreTagIndices.getOrDefault(slot, 0) + 1);
    }

    public int setOreDictIndexReverse(int slot) {
        return setOreTagIndex(slot, oreTagIndices.getOrDefault(slot, 0) - 1);
    }

    public int getOreIndex(int slot) {
        return oreTagIndices.getOrDefault(slot, 0);
    }

    /**
     * Set the index of the tag list for the stack in the slot
     * @param slot
     * @param index
     * @return
     */
    public int setOreTagIndex(int slot, int index) {
        ItemStack stack = getStack(slot);
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            final ArrayList<ResourceLocation> ids = new ArrayList<>(ItemTags.getAllTags().getMatchingTags(item));
            if (!ids.isEmpty()) {
                if (!(index < ids.size()) || index < 0) {
                    index = 0;
                }
                oreTagIndices.put(slot, index);
            }
        } else {
            index = -1;
        }
        return index;
    }

    /**
     * Gets the JsonObject representing the ItemStack.
     * Does not fetch oredict (now tags) data
     *
     * @param stack
     * @return
     */
    JsonObject getStackJson(@Nonnull ItemStack stack) {
        JsonObject stackJson = new JsonObject();
        if (!stack.isEmpty()) {
            // fail here, but not gracefully I guess
            if (stack.getItem().getRegistryName() == null) {
                throw new IllegalStateException("PLEASE REPORT: Item not empty, but getRegistryName null? Debug info: " + stack);
            }

            stackJson.addProperty("item", stack.getItem().getRegistryName().toString());
            if (stack.hasTag()) {
                JsonObject tagJson = NBT2Json.CompoundNBT2Json(stack.getTag(), new JsonObject());
                if (tagJson.size() > 0) {
                    stackJson.add("nbt", tagJson);
                }
            }

            // set the stack count
            if (stack.getCount() > 1) {
                stackJson.addProperty("count", stack.getCount());
            }
        }
        return stackJson;
    }

    /**
     * @param slot
     * @return
     */
    public JsonObject getOreJson(int slot) {
        ItemStack stack = getStack(slot);
        if (slot == 0) {
            return getStackJson(stack);
        }
        JsonObject stackJson = new JsonObject();

        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            final ArrayList<ResourceLocation> ids = new ArrayList<>(ItemTags.getAllTags().getMatchingTags(item));
            if (!ids.isEmpty()) {
                int index = 0;
                if (oreTagIndices.containsKey(slot)) {
                    index = oreTagIndices.get(slot);
                }
                stackJson.addProperty("tag", ids.get(index).toString());
            }

            if (stackJson.size() == 0) {
                stackJson = getStackJson(stack);
            }

            // set the stack count
            if (stack.getCount() > 1) {
                stackJson.addProperty("count", stack.getCount());
            }
        }
        return stackJson;
    }

    /**
     *
     * @param slot
     * @return the string for display in the text bar
     */
    public String getStackToken(int slot) {
        boolean usingOreDict = useOredict.getOrDefault(slot, false);
        if (slot < 0 || slot > 10) {
            return "No slot selected";
        }

        ItemStack stack = getStack(slot);
        // return empty slot string
        if (stack.isEmpty()) {
            return "empty";
        }

        // fail here, but not gracefully I guess
        if (stack.getItem().getRegistryName() == null) {
            throw new IllegalStateException("PLEASE REPORT: Item not empty, but getRegistryName null? Debug info: " + stack);
        }

        String stackName = stack.getItem().getRegistryName().toString();
        StringBuilder builder = new StringBuilder();
        if (usingOreDict) {
            Item item = stack.getItem();
            List<ResourceLocation> ids = ItemTags.getAllTags().getMatchingTags(item).stream().collect(Collectors.toList());
            stackName = "tag: " + ids.get(oreTagIndices.getOrDefault(slot, 0));
        }
        builder.append(stackName);
        if (stack.getCount() > 1) {
            builder.append(" * ").append(stack.getCount());
        }
        return builder.toString();
    }

    public JsonObject getStackJson(int slot) {
        boolean usingOreDict = useOredict.getOrDefault(slot, false);
        JsonObject stackJson = new JsonObject();
        if (usingOreDict) {
            stackJson = getOreJson(slot);
        }
        if (stackJson.size() == 0) {
            return getStackJson(getStack(slot));
        }
        return stackJson;
    }

    public String getFileName() {
        ItemStack resultStack = getStack(0);

        if (resultStack.isEmpty()) {
            return "Recipe Invalid";
        } else {
            String resultRegName = resultStack.getDisplayName()
//                    .getUnformattedComponentText() // broken?
                    .getString()
                    .replace(".tile", "")
                    .replace(".", "_")
                    .replace(" ", "_")
                    .replace(":", "_")
                    .toLowerCase();
            return resultRegName;
        }
    }

    public String getRecipeJson() {
        String backupChars = "#@$%^&*(){}";
        JsonObject recipeJson = new JsonObject();
        ItemStack resultStack = getStack(0);
        recipeJson.add("result", getStackJson(resultStack));
        JsonArray conditions = recipeOptions.conditionsFrame.getJsonArray();

        if (conditions.size() > 1) {
            JsonObject forgeAnd = new JsonObject();
            forgeAnd.addProperty("type", "forge:and");
            forgeAnd.add("values", conditions);
            JsonArray array = new JsonArray();
            array.add(forgeAnd);
            recipeJson.add("conditions", array);
        } else if (conditions.size() > 0) {
            recipeJson.add("conditions", conditions);
        }

        if (recipeOptions.isShapeless()) {
            recipeJson.addProperty("type", "minecraft:crafting_shapeless");

            JsonArray ingredients = new JsonArray();
            for (int i = 1; i < 11; i++) {
                if (!this.container.getSlot(i).getItem().isEmpty()) {
                    JsonObject ingredient = getStackJson(i);

                    boolean match = false;
                    // add first ingredient without checking
                    if (ingredients.size() == 0) {
                        ingredients.add(getStackJson(i));

                        // check if ingredient is already in the list
                    } else {
                        for(int index = 0; i < ingredients.size(); index++) {
                            JsonObject jsonObject = ingredients.get(index).getAsJsonObject();
                            if (jsonObject.has("item") && ingredient.has("item")) {
                                if (jsonObject.getAsJsonObject().get("item").getAsString().equals(ingredient.get("item").getAsString())) {
                                    match = true;
                                }
                            } else if (jsonObject.getAsJsonObject().has("tag") && ingredient.has("tag")) {
                                if (jsonObject.get("tag").getAsString().equals(ingredient.get("tag").getAsString())) {
                                    match = true;
                                }
                            }
                            if (match) {
                                int count = 1;
                                if (jsonObject.getAsJsonObject().has("count")) {
                                    count = jsonObject.getAsJsonObject().get("count").getAsInt();
                                }

                                if (ingredient.has("count")) {
                                    count += ingredient.get("count").getAsInt();
                                } else {
                                    count += 1;
                                }

                                if (count != 1) {
                                    jsonObject.getAsJsonObject().addProperty("count", count);
                                    ingredients.set(index, jsonObject);
                                }
                                break;
                            } else {
                                ingredients.add(getStackJson(i));
                            }
                        }
                    }
                }
            }
            recipeJson.add("ingredients", ingredients);
        } else {
            // TODO: disable oredict for output
//            if (getStackJson(0).has("item")) {
            recipeJson.addProperty("type", "minecraft:crafting_shaped");
//            } else {
//                recipeJson.addProperty("type", "mpa_shaped_ore");
//            }

            // only in shaped recipes
            if (recipeOptions.isMirrored()) {
                recipeJson.addProperty("mirrored", true);
            }

            Map<String, JsonObject> keys = new HashMap<>();
            String[] pattern = {"   ", "   ", "   "};

            char character;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int i = row * 3 + col + 1;

                    if (!this.container.getSlot(i).getItem().isEmpty()) {
                        JsonObject ingredient = getStackJson(i);
                        String ingredientString = "";

                        if (ingredient.has("item")) {
                            String[] ingredientStringList = ingredient.get("item").getAsString().split(":");
                            ingredientString = ingredientStringList[ingredientStringList.length - 1].toUpperCase();
                        } else if (ingredient.has("tag")) {
                            ingredientString = ingredient.get("tag").getAsString().toUpperCase();
                        }

                        boolean keyFound = false;
                        for (int index = 0; i < ingredientString.length(); index++) {
                            character = ingredientString.charAt(index);
                            String key = String.valueOf(character).toUpperCase();

                            // add key to map
                            if (keys.isEmpty() || !keys.containsKey(key)) {
                                keys.put(key, ingredient);
                                StringBuilder sb = new StringBuilder(pattern[row]);
                                sb.setCharAt(col, character);
                                pattern[row] = sb.toString();
                                keyFound = true;
                                break;

                                // check if key is already in map and json is a match, else new key needed
                            } else if (keys.containsKey(key)) {
                                JsonObject object = keys.get(key);
                                if (object.equals(ingredient)) {
                                    StringBuilder sb = new StringBuilder(pattern[row]);
                                    sb.setCharAt(col, character);
                                    pattern[row] = sb.toString();
                                    keyFound = true;
                                    break;
                                    // check next letter
                                } else {
                                    continue;
                                }
                            }
                        }

                        // fallback on a set of backup characters
                        if (!keyFound) {
                            for (int index = 0; i < backupChars.length(); index++) {
                                character = backupChars.charAt(index);
                                String key = String.valueOf(character);
                                keys.put(key, ingredient);
                                StringBuilder sb = new StringBuilder(pattern[row]);
                                sb.setCharAt(col, character);
                                pattern[row] = sb.toString();
                                break;
                            }
                        }
                    }
                }
            }

            JsonArray patternArray = new JsonArray();
            for (String line : pattern) {
                patternArray.add(line);
            }
            recipeJson.add("pattern", patternArray);

            JsonObject keysJson = new JsonObject();
            for (String key : keys.keySet()) {
                keysJson.add(key, keys.get(key));
            }
            recipeJson.add("key", keysJson);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(recipeJson.toString());
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }
}
