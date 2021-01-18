package com.github.lehjr.numina.util.container;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class NuminaRecipeBookContainer<C> extends RecipeBookContainer {
    public NuminaRecipeBookContainer(ContainerType containerType, int windowId) {
        super(containerType, windowId);
    }

    @Override
    public void func_217056_a(boolean placeAll, IRecipe recipe, ServerPlayerEntity player) {
        (new NuminaServerRecipePlacer(this)).place(player, recipe, placeAll);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public RecipeBookCategory func_241850_m() {
        return RecipeBookCategory.CRAFTING;
    }
}