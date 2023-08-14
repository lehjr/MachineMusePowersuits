package lehjr.numina.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copies energy from ingredients to the result ItemStack
 */
public class ShapedEnergyRecipe extends ShapedRecipe {
	public ShapedEnergyRecipe(ShapedRecipe shaped) {
		this(shaped.getId(), shaped.getGroup(), shaped.getWidth(), shaped.getRecipeHeight(), shaped.getIngredients(), shaped.getResultItem());
	}

	// public ShapedRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult) {
	public ShapedEnergyRecipe(final ResourceLocation id, final String group, final int recipeWidth, final int recipeHeight, final NonNullList<Ingredient> ingredients, final ItemStack recipeOutput) {
		super(id, group, recipeWidth, recipeHeight, ingredients, recipeOutput);
	}

	/**
	 * @param inv
	 * @return result with energy from ingredients up to max value
	 */
	@Override
	public ItemStack assemble(final CraftingContainer inv) {
		final ItemStack output = super.assemble(inv).copy(); // Get the default output
		AtomicInteger energy = new AtomicInteger(0);
		for(int i = 0; i < inv.getContainerSize(); ++i) {
			ItemStack itemstack = inv.getItem(i);
			energy.addAndGet(itemstack.getCapability(ForgeCapabilities.ENERGY).map(iEnergyStorage -> iEnergyStorage.getEnergyStored()).orElse(0));
		}

		output.getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> {
			int testEnergy = iEnergyStorage.receiveEnergy(energy.get(), true);

			while (testEnergy > 0) {
				testEnergy -= iEnergyStorage.receiveEnergy(testEnergy, false);
			}
		});
		return output;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializersRegistry.ENERGY_RECIPE_SERIALIZER.get();
	}

	public static class EnergySerializer extends Serializer {
		@Override
		public ShapedEnergyRecipe fromJson(final ResourceLocation recipeID, final JsonObject json) {
			return new ShapedEnergyRecipe(super.fromJson(recipeID, json));
		}

		@Override
		public ShapedEnergyRecipe fromNetwork(final ResourceLocation recipeID, final FriendlyByteBuf buffer) {
			return new ShapedEnergyRecipe(super.fromNetwork(recipeID, buffer));
		}
	}
}
