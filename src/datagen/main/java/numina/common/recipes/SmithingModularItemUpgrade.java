//package numina.common.recipes;
//
//import com.mojang.serialization.MapCodec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//import net.minecraft.world.item.crafting.SmithingRecipe;
//import net.minecraft.world.item.crafting.SmithingRecipeInput;
//import net.minecraft.world.item.crafting.SmithingTransformRecipe;
//import net.minecraft.world.level.Level;
//
//import java.util.stream.Stream;
//
//public class SmithingModularItemUpgrade {
//
////package net.minecraft.world.item.crafting;
//
////import com.mojang.serialization.MapCodec;
////import com.mojang.serialization.codecs.RecordCodecBuilder;
////import java.util.stream.Stream;
////import net.minecraft.core.HolderLookup;
////import net.minecraft.network.RegistryFriendlyByteBuf;
////import net.minecraft.network.codec.StreamCodec;
////import net.minecraft.world.item.ItemStack;
////import net.minecraft.world.level.Level;
//
//    public class SmithingTransformRecipe implements SmithingRecipe {
//        final Ingredient template;
//        final Ingredient base;
//        final Ingredient addition;
//        final ItemStack result;
//
//        public SmithingTransformRecipe(Ingredient template, Ingredient base, Ingredient addition, ItemStack result) {
//            this.template = template;
//            this.base = base;
//            this.addition = addition;
//            this.result = result;
//        }
//
//        public boolean matches(SmithingRecipeInput input, Level level) {
//            return this.template.test(input.template()) && this.base.test(input.base()) && this.addition.test(input.addition());
//        }
//
//        public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
//            ItemStack itemstack = input.base().transmuteCopy(this.result.getItem(), this.result.getCount());
//            itemstack.applyComponents(this.result.getComponentsPatch());
//            return itemstack;
//        }
//
//        public ItemStack getResultItem(HolderLookup.Provider registries) {
//            return this.result;
//        }
//
//        public boolean isTemplateIngredient(ItemStack stack) {
//            return this.template.test(stack);
//        }
//
//        public boolean isBaseIngredient(ItemStack stack) {
//            return this.base.test(stack);
//        }
//
//        public boolean isAdditionIngredient(ItemStack stack) {
//            return this.addition.test(stack);
//        }
//
//        public RecipeSerializer<?> getSerializer() {
//            return RecipeSerializer.SMITHING_TRANSFORM;
//        }
//
//        public boolean isIncomplete() {
//            return Stream.of(this.template, this.base, this.addition).anyMatch(Ingredient::hasNoItems);
//        }
//
//        public static class Serializer implements RecipeSerializer<net.minecraft.world.item.crafting.SmithingTransformRecipe> {
//            private static final MapCodec<net.minecraft.world.item.crafting.SmithingTransformRecipe> CODEC = RecordCodecBuilder.mapCodec((p_340782_) -> p_340782_.group(Ingredient.CODEC.fieldOf("template").forGetter((p_301310_) -> p_301310_.template), Ingredient.CODEC.fieldOf("base").forGetter((p_300938_) -> p_300938_.base), Ingredient.CODEC.fieldOf("addition").forGetter((p_301153_) -> p_301153_.addition), ItemStack.STRICT_CODEC.fieldOf("result").forGetter((p_300935_) -> p_300935_.result)).apply(p_340782_, net.minecraft.world.item.crafting.SmithingTransformRecipe::new));
//            public static final StreamCodec<RegistryFriendlyByteBuf, net.minecraft.world.item.crafting.SmithingTransformRecipe> STREAM_CODEC = StreamCodec.of(
//                net.minecraft.world.item.crafting.SmithingTransformRecipe.Serializer::toNetwork, net.minecraft.world.item.crafting.SmithingTransformRecipe.Serializer::fromNetwork);
//
//            public Serializer() {
//            }
//
//            public MapCodec<net.minecraft.world.item.crafting.SmithingTransformRecipe> codec() {
//                return CODEC;
//            }
//
//            public StreamCodec<RegistryFriendlyByteBuf, net.minecraft.world.item.crafting.SmithingTransformRecipe> streamCodec() {
//                return STREAM_CODEC;
//            }
//
//            private static net.minecraft.world.item.crafting.SmithingTransformRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
//                Ingredient ingredient = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
//                Ingredient ingredient1 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
//                Ingredient ingredient2 = (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
//                ItemStack itemstack = (ItemStack)ItemStack.STREAM_CODEC.decode(buffer);
//                return new net.minecraft.world.item.crafting.SmithingTransformRecipe(ingredient, ingredient1, ingredient2, itemstack);
//            }
//
//            private static void toNetwork(RegistryFriendlyByteBuf buffer, net.minecraft.world.item.crafting.SmithingTransformRecipe recipe) {
//                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
//                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.base);
//                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
//                ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
//            }
//        }
//    }
//
//
//}
