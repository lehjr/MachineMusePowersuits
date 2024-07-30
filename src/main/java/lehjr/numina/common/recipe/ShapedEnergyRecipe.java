package lehjr.numina.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lehjr.numina.common.base.NuminaLogger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.concurrent.atomic.AtomicInteger;

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
	public boolean matches(CraftingContainer pInv, Level pLevel) {
		return super.matches(pInv, pLevel);
	}

	@Override
	public ItemStack assemble(CraftingContainer container, HolderLookup.Provider provider) {
		int energyDevices = 0;
		final ItemStack output = super.assemble(container, provider).copy(); // Get the default output
		int energy = 0;
		for(int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemstack = container.getItem(i);
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
				testEnergy = energyStorage.receiveEnergy(energy, true);
			} else {
				testEnergy = energyStorage.getMaxEnergyStored();
			}

			while (testEnergy > 0 || energyStorage.getEnergyStored() != energyStorage.getMaxEnergyStored()) {
				testEnergy -= energyStorage.receiveEnergy(testEnergy, false);
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
								Codec.STRING.optionalFieldOf("group", "").forGetter(ShapedRecipe::getGroup),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(ShapedRecipe::category),
								ShapedRecipePattern.MAP_CODEC.forGetter(shapedRecipe -> shapedRecipe.pattern),
								ItemStack.STRICT_CODEC.fieldOf("result").forGetter(shapedRecipe -> shapedRecipe.result),
								Codec.BOOL.optionalFieldOf("show_notification", Boolean.valueOf(true)).forGetter(ShapedRecipe::showNotification)
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
