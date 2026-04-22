package lehjr.numina.common.jei;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.recipe.SmithingUpgradeRecipe;
import lehjr.numina.common.registration.RecipeSerializersRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class SmithingUpgradeJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        NuminaLogger.logDebug("SmithingUpgradeJEIPlugin");
        return RecipeSerializersRegistry.SMITHING_UPGRADE_SERIALIZER.getId();
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        NuminaLogger.logDebug("SmithingUpgradeJEIPlugin registration");
        registration.getSmithingCategory().addExtension(SmithingUpgradeRecipe.class, new SmithingUpgradeCategoryExtension());
    }
}
