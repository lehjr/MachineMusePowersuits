package lehjr.numina.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.core.HolderLookup;
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

public class SmithingModuleUpgradeRecipe implements SmithingRecipe, SmithingRecipeWithSizedAdditionalIngredient {
    final Ingredient template;
    final Ingredient base;
    final SizedIngredient addition;
    final ItemStack result;

    public SmithingModuleUpgradeRecipe(Ingredient template, Ingredient base, SizedIngredient addition, ItemStack result) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public SizedIngredient getAdditionalIngredient() {
        return addition;
    }

    public boolean matches(SmithingRecipeInput input, Level level) {
        NuminaLogger.logDebug("SmithingModuleUpgradeRecipe matches recipe input");

        return this.template.test(input.template()) && this.base.test(input.base()) && this.addition.test(input.addition());
    }

    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        ItemStack itemstack = input.base().transmuteCopy(this.result.getItem(), this.result.getCount());
        itemstack.applyComponents(this.result.getComponentsPatch());
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
        return RecipeSerializersRegistry.SMITHING_MODULE_UPGRADE_SERIALIZER.get();
    }

    public boolean isIncomplete() {
        NuminaLogger.logDebug("SmithingModuleUpgradeRecipe isIncomplete template: " + this.template.hasNoItems());
        NuminaLogger.logDebug("SmithingModuleUpgradeRecipe isIncomplete base: " + this.base.hasNoItems());
        NuminaLogger.logDebug("SmithingModuleUpgradeRecipe isIncomplete addition: " + (this.addition.count() == 0));


        // FIXME!!!! figure out how to actually check the current count vs required count

        return Stream.of(this.template, this.base).anyMatch(Ingredient::hasNoItems) || addition.count() == 0;
    }

    public static class ModuleSmithingUpgradeSerializer implements RecipeSerializer<SmithingModuleUpgradeRecipe> {
        private static final MapCodec<SmithingModuleUpgradeRecipe> CODEC =
            RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Ingredient.CODEC.fieldOf("template").forGetter((recipe) -> recipe.template),
                    Ingredient.CODEC.fieldOf("base").forGetter((recipe) -> recipe.base),
                    SizedIngredient.NESTED_CODEC.fieldOf("addition").forGetter((recipe) -> recipe.addition),
                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter((recipe) -> recipe.result)
                ).apply(instance, SmithingModuleUpgradeRecipe::new)
            );

        public static final StreamCodec<RegistryFriendlyByteBuf, SmithingModuleUpgradeRecipe> STREAM_CODEC =
            StreamCodec.of(SmithingModuleUpgradeRecipe.ModuleSmithingUpgradeSerializer::toNetwork, SmithingModuleUpgradeRecipe.ModuleSmithingUpgradeSerializer::fromNetwork);

        public ModuleSmithingUpgradeSerializer() {
        }

        public MapCodec<SmithingModuleUpgradeRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, SmithingModuleUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SmithingModuleUpgradeRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient base = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            SizedIngredient addition = SizedIngredient.STREAM_CODEC.decode(buffer);
            NuminaLogger.logDebug("addition size: " + addition.count());

            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            return new SmithingModuleUpgradeRecipe(template, base, addition, itemstack);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SmithingModuleUpgradeRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
            SizedIngredient.STREAM_CODEC.encode(buffer, recipe.addition);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }
    }
}
