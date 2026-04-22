package lehjr.numina.common.jei;

import lehjr.numina.common.recipe.SmithingUpgradeRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;

public class SmithingUpgradeCategoryExtension implements ISmithingCategoryExtension<SmithingUpgradeRecipe> {

    @Override
    public <T extends IIngredientAcceptor<T>> void setTemplate(SmithingUpgradeRecipe recipe, T acceptor) {
        acceptor.addIngredients(recipe.templateIngredient());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setBase(SmithingUpgradeRecipe recipe, T acceptor) {
        acceptor.addIngredients(recipe.baseIngredient());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setAddition(SmithingUpgradeRecipe recipe, T acceptor) {
        acceptor.addItemStack(recipe.additionIngredientStack());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(SmithingUpgradeRecipe recipe, T acceptor) {
        acceptor.addItemStack(recipe.resultStack());
    }
}
