package com.github.lehjr.powersuits.dev.crafting.client.gui.recipebooktest;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import net.minecraft.client.gui.recipebook.RecipeList;

import java.util.List;

/**
 * Holds a list of module recipes for a certain EnumModuleCategory
 */
public class RecipeBox extends RelativeRect {
    EnumModuleCategory category;
    List<RecipeList> recipes;

    public RecipeBox(EnumModuleCategory category, List<RecipeList> recipes) {
        super();
        setWidth(157);
        this.category = category;

        /*
            Note: recipe widgets are 25 x 25 but there should be spacers between
         */
    }

}
