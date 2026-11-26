package lehjr.numina.common.recipe;

import com.google.gson.JsonObject;
import lehjr.numina.common.registration.RecipeSerializersRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

/**
 * Copies energy from ingredients to the result ItemStack
 */
public class ShapedEnergyRecipe extends ShapedRecipe {
	public ShapedEnergyRecipe(ShapedRecipe base) {
		this(
				base.getId(),
				base.getGroup(),
				base.category(),
				base.getWidth(),
				base.getHeight(),
				base.getIngredients(),
				base.getResultItem(RegistryAccess.EMPTY),
				base.showNotification());
	}

	public ShapedEnergyRecipe(ResourceLocation id, String group, CraftingBookCategory category, int width, int height,
							  NonNullList<Ingredient> recipeItems, ItemStack result, boolean showNotification) {
		super(id, group, category, width, height, recipeItems, result, showNotification);
	}

	@Override
	public ItemStack assemble(CraftingContainer input, RegistryAccess registryAccess) {
		int energyDevices = 0;
		final ItemStack output = this.getResultItem(registryAccess).copy();
		int energy = 0;
		for (int i = 0; i < input.getContainerSize(); ++i) {
			ItemStack itemstack = input.getItem(i);
			IEnergyStorage energyStorage = itemstack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
			if (energyStorage != null) {
				energy += energyStorage.getEnergyStored();
				energyDevices += 1;
			}
		}

		IEnergyStorage energyStorage = output.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
		if (energyStorage != null && energyStorage.getMaxEnergyStored() > 0) {
			int testEnergy = energyDevices > 0 ? energy : energyStorage.getMaxEnergyStored();

			while (testEnergy > 0 && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
				int received = energyStorage.receiveEnergy(testEnergy, false);
				if (received <= 0) {
					break;
				}
				testEnergy -= received;
			}
		}
		return output;
	}

	@Override
	public boolean matches(CraftingContainer input, Level level) {
		return matches(input);
	}

	public boolean matches(CraftingContainer input) {
		for (int i = 0; i <= input.getWidth() - this.getWidth(); i++) {
			for (int j = 0; j <= input.getHeight() - this.getHeight(); j++) {
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

	public boolean matches(CraftingContainer input, int startX, int startY, boolean reverse) {
		for (int col = 0; col < input.getWidth(); col++) {
			for (int row = 0; row < input.getHeight(); row++) {
				int k = col - startX;
				int l = row - startY;
				Ingredient ingredient = Ingredient.EMPTY;
				if (k >= 0 && l >= 0 && k < this.getWidth() && l < this.getHeight()) {
					if (reverse) {
						ingredient = this.getIngredients().get(this.getWidth() - k - 1 + l * this.getWidth());
					} else {
						ingredient = this.getIngredients().get(k + l * this.getWidth());
					}
				}

				// batteries can't be stacked so check if ingredient is the same
				if (ingredient.getItems().length == 1) {
					ItemStack gridItem = input.getItem(col + row * input.getWidth());
					ItemStack ingredientStack = ingredient.getItems()[0];
					if (!ItemStack.isSameItem(ingredientStack, gridItem)) {
						return false;
					}
				}

				if (!ingredient.test(input.getItem(col + row * input.getWidth()))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isSpecial() {
		return super.isSpecial();
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersRegistry.ENERGY_RECIPE_SERIALIZER.get();
	}

	public static class EnergySerializer implements RecipeSerializer<ShapedEnergyRecipe> {
		@Override
		public ShapedEnergyRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
			return new ShapedEnergyRecipe(RecipeSerializer.SHAPED_RECIPE.fromJson(recipeId, serializedRecipe));
		}

		@Override
		@Nullable
		public ShapedEnergyRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			ShapedRecipe base = RecipeSerializer.SHAPED_RECIPE.fromNetwork(recipeId, buffer);
			return base == null ? null : new ShapedEnergyRecipe(base);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, ShapedEnergyRecipe recipe) {
			RecipeSerializer.SHAPED_RECIPE.toNetwork(buffer, recipe);
		}
	}
}