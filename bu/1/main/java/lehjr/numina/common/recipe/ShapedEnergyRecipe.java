package lehjr.numina.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copies energy from ingredients to the result ItemStack
 */
public class ShapedEnergyRecipe extends ShapedRecipe {
	public ShapedEnergyRecipe(ShapedRecipe shaped) {
		super(
				shaped.getId(),
				shaped.getGroup(),
				shaped.category(),
				shaped.getWidth(),
				shaped.getRecipeHeight(),
				shaped.getIngredients(),
				shaped.getResultItem(null),
				shaped.showNotification()
		);
	}

	@Override
	public @Nonnull ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		final ItemStack output = super.assemble(container, registryAccess).copy(); // Get the default output
		AtomicInteger energy = new AtomicInteger(0);
		for(int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemstack = container.getItem(i);
			energy.addAndGet(itemstack.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0));
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
