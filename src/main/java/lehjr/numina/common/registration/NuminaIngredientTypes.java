package lehjr.numina.common.registration;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.recipe.ingredients.MinEnchantedIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class NuminaIngredientTypes {
    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.INGREDIENT_TYPES, NuminaConstants.MOD_ID);

    public static final Supplier<IngredientType<MinEnchantedIngredient>> MIN_ENCHANTED =
            INGREDIENT_TYPES.register("min_enchanted",
                    // The stream codec parameter is optional, a stream codec will be created from the codec
                    // using ByteBufCodecs#fromCodec or #fromCodecWithRegistries if the stream codec isn't specified.
                    () -> new IngredientType<>(MinEnchantedIngredient.CODEC, MinEnchantedIngredient.STREAM_CODEC));
}
