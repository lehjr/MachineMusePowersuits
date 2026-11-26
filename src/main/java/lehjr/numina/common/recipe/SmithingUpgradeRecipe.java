package lehjr.numina.common.recipe;

import com.google.gson.JsonObject;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.registration.RecipeSerializersRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class SmithingUpgradeRecipe implements SmithingRecipe {
    private final ResourceLocation id;
    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;
    final int additionCount;
    final ItemStack result;

    public SmithingUpgradeRecipe(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition, int additionCount, ItemStack result) {
        this.id = id;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.additionCount = Math.max(1, additionCount);
        this.result = result;
    }

    public SmithingUpgradeRecipe(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition, ItemStack result) {
        this(id, template, base, addition, 1, result);
    }

    public Ingredient getAdditionalIngredient() {
        return this.addition;
    }

    public int getAdditionCount() {
        return this.additionCount;
    }

    public Ingredient templateIngredient() {
        return this.template;
    }

    public Ingredient baseIngredient() {
        return this.base;
    }

    public ItemStack additionIngredientStack() {
        ItemStack[] ingredients = this.addition.getItems();

        if (ingredients.length > 0) {
            ItemStack out = ingredients[0].copy();
            out.setCount(this.additionCount);
            return out;
        }

        return ItemStack.EMPTY;
    }

    public ItemStack resultStack() {
        return this.result;
    }

    @Override
    public boolean matches(Container container, Level level) {
        ItemStack additionStack = container.getItem(2);
        return this.template.test(container.getItem(0))
                && this.base.test(container.getItem(1))
                && this.addition.test(additionStack)
                && additionStack.getCount() >= this.additionCount;
    }

    /**
     * Modules should be reset but modular items should have their contents and cosmetic settings carried over
     */
    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack itemstack = this.result.copy();
        ItemStack baseItem = container.getItem(1);

        if (baseItem.hasTag()) {
            CompoundTag baseTag = baseItem.getTag().copy();
            if (itemstack.hasTag()) {
                baseTag.merge(itemstack.getTag());
            }
            itemstack.setTag(baseTag);
        }

        IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(baseItem);
        if (iModularItem != null) {
            // Copy the render tag from baseItem
            CompoundTag renderTag = null;
            IModelSpec spec = baseItem.getCapability(NuminaCapabilities.RENDER).resolve().orElse(null);
            if (spec != null) {
                renderTag = spec.getRenderTag();
            }

            // Make a copy of the installed modules
            NonNullList<ItemStack> modules = NonNullList.create();
            for (int slot = 0; slot < iModularItem.getSlots(); slot++) {
                ItemStack module = iModularItem.getStackInSlot(slot);
                if (!module.isEmpty()) {
                    modules.add(module.copy());
                }
            }

            IModularItem iModularItemDest = NuminaCapabilities.getModularItemOrModeChangingCapability(itemstack);
            if (iModularItemDest != null && !modules.isEmpty()) {
                // Apply the render tag to the upgraded item
                IModelSpec specDest = itemstack.getCapability(NuminaCapabilities.RENDER).resolve().orElse(null);
                if (renderTag != null && !renderTag.isEmpty() && specDest != null) {
                    specDest.setRenderTag(renderTag.copy(), NuminaConstants.RENDER_TAG);
                }
                // Copy the installed modules
                for (ItemStack module : modules) {
                    for (int slot = 0; slot < iModularItemDest.getSlots(); slot++) {
                        if (iModularItemDest.isModuleValidForPlacement(slot, module)) {
                            iModularItemDest.insertItem(slot, module, false);
                            break;
                        }
                    }
                }
            }
        }
        return itemstack;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return this.base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return this.addition.test(stack);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.SMITHING_UPGRADE_SERIALIZER.get();
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(this.template, this.base, this.addition).anyMatch(Ingredient::isEmpty) || this.additionCount <= 0;
    }

    public static class SmithingUpgradeSerializer implements RecipeSerializer<SmithingUpgradeRecipe> {
        @Override
        public SmithingUpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient template = Ingredient.fromJson(GsonHelper.getNonNull(json, "template"));
            Ingredient base = Ingredient.fromJson(GsonHelper.getNonNull(json, "base"));

            JsonObject additionObj = GsonHelper.getAsJsonObject(json, "addition");
            Ingredient addition = additionObj.has("ingredient")
                    ? Ingredient.fromJson(additionObj.get("ingredient"))
                    : Ingredient.fromJson(additionObj);
            int count = GsonHelper.getAsInt(additionObj, "count", 1);

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new SmithingUpgradeRecipe(recipeId, template, base, addition, count, result);
        }

        @Override
        @Nullable
        public SmithingUpgradeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient template = Ingredient.fromNetwork(buffer);
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            int count = buffer.readVarInt();
            ItemStack result = buffer.readItem();
            return new SmithingUpgradeRecipe(recipeId, template, base, addition, count, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SmithingUpgradeRecipe recipe) {
            recipe.template.toNetwork(buffer);
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeVarInt(recipe.additionCount);
            buffer.writeItem(recipe.result);
        }
    }
}
