package com.github.lehjr.numina.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.lehjr.numina.recipe.RecipeSerializersRegistry.TEST_RECIPE_SERIALIZER;

/**
 * Copies energy from ingredients to the result ItemStack
 */
public class ShapedEnergyRecipe extends ShapedRecipe {
	public ShapedEnergyRecipe(ShapedRecipe shaped) {
		this(shaped.getId(), shaped.getGroup(), shaped.getWidth(), shaped.getHeight(), shaped.getIngredients(), shaped.getRecipeOutput());
	}

	public ShapedEnergyRecipe(final ResourceLocation id, final String group, final int recipeWidth, final int recipeHeight, final NonNullList<Ingredient> ingredients, final ItemStack recipeOutput) {
		super(id, group, recipeWidth, recipeHeight, ingredients, recipeOutput);
	}

	/**
	 * @param inv
	 * @return result with energy from ingredients up to max value
	 */
	@Override
	public ItemStack getCraftingResult(final CraftingInventory inv) {
		final ItemStack output = super.getCraftingResult(inv).copy(); // Get the default output
		AtomicInteger energy = new AtomicInteger(0);
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			energy.addAndGet(itemstack.getCapability(CapabilityEnergy.ENERGY).map(iEnergyStorage -> iEnergyStorage.getEnergyStored()).orElse(0));
		}

		output.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> {
			int testEnergy = iEnergyStorage.receiveEnergy(energy.get(), true);

			while (testEnergy > 0) {
				testEnergy -= iEnergyStorage.receiveEnergy(testEnergy, false);
			}
		});
		return output;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return TEST_RECIPE_SERIALIZER.get();
	}

	public static class EnergySerializer extends Serializer {
		@Override
		public ShapedEnergyRecipe read(final ResourceLocation recipeID, final JsonObject json) {
			return new ShapedEnergyRecipe(super.read(recipeID, json));
		}

		@Override
		public ShapedEnergyRecipe read(final ResourceLocation recipeID, final PacketBuffer buffer) {
			return new ShapedEnergyRecipe(super.read(recipeID, buffer));
		}
	}
}
