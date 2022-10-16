package lehjr.numina.client.gui.recipe;

import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * Just a quick and dirty way of adding the tier and "group" to the recipe list for faster sorting
 * None of the parent fields need to be accessible since they're still populated the same way
 */
@OnlyIn(Dist.CLIENT)
public class ModuleRecipeGroup extends RecipeList {
    final int tier;
    @Nonnull
    final String group;

    public ModuleRecipeGroup(RecipeList iRecipes, int tier, @Nonnull String group) {
        super(iRecipes.getRecipes());
        this.tier = tier;
        this.group = group;
    }

    @Override
    public boolean hasKnownRecipes() {
        return super.hasKnownRecipes();
    }

    public int getTier() {
        return tier;
    }

    public String getGroup() {
        return group;
    }
}