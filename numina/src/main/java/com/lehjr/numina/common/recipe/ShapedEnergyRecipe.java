package com.lehjr.numina.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * Copies energy from ingredients to the result ItemStack
 */
public class ShapedEnergyRecipe extends ShapedRecipe {
	public ShapedEnergyRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
		super(group, category, pattern, result, showNotification);
	}

	@Override
	public boolean isSpecial() {
		return super.isSpecial();
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		return matches(input);
	}

	public boolean matches(CraftingInput input) {
		for (int i = 0; i <= input.width() - this.pattern.width(); i++) {
			for (int j = 0; j <= input.height() - this.pattern.height(); j++) {
				if (this.matches(input, i, j, true)) {
                    return true;
				}

				if (this.matches(input, i, j, false)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean matches(CraftingInput input, int startX, int startY, boolean reverse) {
		for (int col = 0; col < input.width(); col++) {
			for (int row = 0; row < input.height(); row++) {
				int k = col - startX;
				int l = row - startY;
				Ingredient ingredient = Ingredient.EMPTY;
				if (k >= 0 && l >= 0 && k < this.pattern.width() && l < this.pattern.height()) {
					if (reverse) {
						ingredient = this.pattern.ingredients().get(this.pattern.width() - k - 1 + l * this.pattern.width());
					} else {
						ingredient = this.pattern.ingredients().get(k + l * this.pattern.width());
					}
				}

				// batteries can't be stacked so check if ingredient is the same
				if(ingredient.getItems().length == 1) {
					ItemStack gridItem = input.getItem(col + row * input.width());
					ItemStack ingredientStack = ingredient.getItems()[0];
					if(!ingredientStack.is(gridItem.getItemHolder())) {
						return false;
					}
				}

				if (!ingredient.test(input.getItem(col + row * input.width()))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
		int energyDevices = 0;
		final ItemStack output = result.copy(); // Get the default output
		int energy = 0;
		for(int i = 0; i < input.ingredientCount(); ++i) {
			ItemStack itemstack = input.getItem(i);
			IEnergyStorage energyStorage = itemstack.getCapability(Capabilities.EnergyStorage.ITEM);
			if(energyStorage != null) {
				energy += energyStorage.getEnergyStored();
				energyDevices += 1;
			}
		}

		IEnergyStorage energyStorage = output.getCapability(Capabilities.EnergyStorage.ITEM);
		if (energyStorage != null) {
			int testEnergy;
			if (energyDevices > 0) {
				testEnergy = energy;
			} else {
				testEnergy = energyStorage.getMaxEnergyStored();
			}

			while (testEnergy > 0 || energyStorage.getEnergyStored() != energyStorage.getMaxEnergyStored()) {
				testEnergy -= energyStorage.receiveEnergy(testEnergy, false);

				if(testEnergy == 0 || energyStorage.getEnergyStored() == energyStorage.getMaxEnergyStored()) {
					break;
				}
			}
		}
		return output.copy();
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersRegistry.ENERGY_RECIPE_SERIALIZER.get();
	}

	public static class EnergySerializer implements RecipeSerializer<ShapedEnergyRecipe> {
		public static final MapCodec<ShapedEnergyRecipe> CODEC = RecordCodecBuilder.mapCodec(
				recipeInstance -> recipeInstance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(ShapedEnergyRecipe::getGroup),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(ShapedEnergyRecipe::category),
								ShapedRecipePattern.MAP_CODEC.forGetter(shapedRecipe -> shapedRecipe.pattern),
								ItemStack.STRICT_CODEC.fieldOf("result").forGetter(shapedRecipe -> shapedRecipe.result),
								Codec.BOOL.optionalFieldOf("show_notification", Boolean.valueOf(true)).forGetter(ShapedEnergyRecipe::showNotification)
						)
						.apply(recipeInstance, ShapedEnergyRecipe::new)
		);

		public static final StreamCodec<RegistryFriendlyByteBuf, ShapedEnergyRecipe> STREAM_CODEC = StreamCodec.of(EnergySerializer::toNetwork, EnergySerializer::fromNetwork);

		@Override
		public MapCodec<ShapedEnergyRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShapedEnergyRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static ShapedEnergyRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
			String group = byteBuf.readUtf();
			CraftingBookCategory craftingbookcategory = byteBuf.readEnum(CraftingBookCategory.class);
			ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(byteBuf);
			ItemStack result = ItemStack.STREAM_CODEC.decode(byteBuf);
			boolean showNotification = byteBuf.readBoolean();
			return new ShapedEnergyRecipe(group, craftingbookcategory, shapedrecipepattern, result, showNotification);
		}

		private static void toNetwork(RegistryFriendlyByteBuf byteBuf, ShapedEnergyRecipe energyRecipe) {
			byteBuf.writeUtf(energyRecipe.getGroup());
			byteBuf.writeEnum(energyRecipe.category());
			ShapedRecipePattern.STREAM_CODEC.encode(byteBuf, energyRecipe.pattern);
			ItemStack.STREAM_CODEC.encode(byteBuf, energyRecipe.result);
			byteBuf.writeBoolean(energyRecipe.showNotification());
		}
	}
}
