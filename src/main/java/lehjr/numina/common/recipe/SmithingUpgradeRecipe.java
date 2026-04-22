package lehjr.numina.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.registration.RecipeSerializersRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.stream.Stream;

public class SmithingUpgradeRecipe implements SmithingRecipe {
    final Ingredient template;
    final Ingredient base;
    final SizedIngredient addition;
    final ItemStack result;

    public SmithingUpgradeRecipe(Ingredient template, Ingredient base, SizedIngredient addition, ItemStack result) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public SizedIngredient getAdditionalIngredient() {
        return addition;
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
            ItemStack out = ingredients[0];
            for (int i = 1; i < ingredients.length; i++) {
                ItemStack current = ingredients[i];
                if(out.is(current.getItem())) {
                    out.setCount(out.getCount() + current.getCount());
                }
            }
            return out;
        }

        return ItemStack.EMPTY;
    }

    public ItemStack resultStack() {
        return this.result;
    }

    public boolean matches(SmithingRecipeInput input, Level level) {
        return this.template.test(input.template()) && this.base.test(input.base()) && this.addition.test(input.addition());
    }

    /**
     * Modules should be reset but modular items should have their contents and cosmetic settings carried over
     * @param input
     * @param registries
     * @return
     */
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        ItemStack itemstack = input.base().transmuteCopy(this.result.getItem(), this.result.getCount());
        itemstack.applyComponents(this.result.getComponentsPatch());

        ItemStack baseItem = input.base();
        IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(baseItem);
        if(iModularItem != null) {
            // Copy the render tag
            CompoundTag renderTag = null;
            IModelSpec spec = itemstack.getCapability(NuminaCapabilities.RENDER);
            if(spec != null) {
                renderTag = spec.getRenderTag();
            }

            // make a copy the installed modules
            NonNullList<ItemStack> modules = NonNullList.create();
            for (int slot=0; slot < iModularItem.getSlots(); slot++) {
                ItemStack module = iModularItem.getStackInSlot(slot);
                if (!module.isEmpty()) {
                    modules.add(module);
                }
            }

            IModularItem iModularItemDest = NuminaCapabilities.getModularItemOrModeChangingCapability(itemstack);
            if(iModularItemDest != null && !modules.isEmpty()) {
                // Copy the render tag
                IModelSpec specDest = itemstack.getCapability(NuminaCapabilities.RENDER);
                if(renderTag != null && !renderTag.isEmpty() && specDest != null) {
                    specDest.setRenderTag(renderTag, NuminaConstants.RENDER_TAG);
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

    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    public boolean isTemplateIngredient(ItemStack stack) {
        return this.template.test(stack);
    }

    public boolean isBaseIngredient(ItemStack stack) {
        return this.base.test(stack);
    }

    public boolean isAdditionIngredient(ItemStack stack) {
        return this.addition.test(stack);
    }

    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.SMITHING_UPGRADE_SERIALIZER.get();
    }

    public boolean isIncomplete() {
        return Stream.of(this.template, this.base).anyMatch(Ingredient::hasNoItems) || addition.count() == 0;
    }

    public static class SmithingUpgradeSerializer implements RecipeSerializer<SmithingUpgradeRecipe> {
        private static final MapCodec<SmithingUpgradeRecipe> CODEC =
            RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Ingredient.CODEC.fieldOf("template").forGetter((recipe) -> recipe.template),
                    Ingredient.CODEC.fieldOf("base").forGetter((recipe) -> recipe.base),
                    SizedIngredient.NESTED_CODEC.fieldOf("addition").forGetter((recipe) -> recipe.addition),
                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter((recipe) -> recipe.result)
                ).apply(instance, SmithingUpgradeRecipe::new)
            );

        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingUpgradeRecipe> STREAM_CODEC =
            StreamCodec.of(SmithingUpgradeSerializer::toNetwork, SmithingUpgradeSerializer::fromNetwork);

        public SmithingUpgradeSerializer() {
        }

        public MapCodec<SmithingUpgradeRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, SmithingUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SmithingUpgradeRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            SizedIngredient addition = SizedIngredient.STREAM_CODEC.decode(buffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            return new SmithingUpgradeRecipe(template, base, addition, itemstack);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SmithingUpgradeRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.addition);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }
    }
}
