package lehjr.numina.common.registration;

import lehjr.numina.common.recipe.ingredients.MinEnchantedIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class NuminaIngredientTypes {
    public static void regIngredients(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CraftingHelper.register(MinEnchantedIngredient.Serializer.ID, MinEnchantedIngredient.Serializer.INSTANCE);
        });
    }
}
