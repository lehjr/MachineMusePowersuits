package com.github.lehjr.numina.dev.crafting.client.gui;

import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NuminaRecipeBookGui extends RecipeBookGui {
    int guiLeft = 0;

    public NuminaRecipeBookGui() {
//        this.recipeBookPage = new RecipeBookPage();
        this.recipeBookPage = new NuminaRecipeBookPage();
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    @Override
    public void initVisuals(boolean widthTooNarrow) {
        System.out.println("initVisuals");

        super.initVisuals(widthTooNarrow);
        guiLeft = (this.width - 147) / 2 - this.xOffset;

//        this.xOffset = widthTooNarrow ? 0 : 86;
//        int i = (this.width - 147) / 2 - this.xOffset;
//        int j = (this.height - 166) / 2;
//
//
//
//        this.stackedContents.clear();
//        this.minecraft.player.inventory.fillStackedContents(this.stackedContents);
//        this.menu.fillCraftSlotsStackedContents(this.stackedContents);
//        String s = this.searchBox != null ? this.searchBox.getValue() : "";
//        this.searchBox = new TextFieldWidget(this.minecraft.font, i + 25, j + 14, 80, 9 + 5, new TranslationTextComponent("itemGroup.search"));
//        this.searchBox.setMaxLength(50);
//        this.searchBox.setBordered(false);
//        this.searchBox.setVisible(true);
//        this.searchBox.setTextColor(16777215);
//        this.searchBox.setValue(s);
//        if(this.recipeBookPage instanceof NuminaRecipeBookPage) {
//            System.out.println("is instance");
//
//            ((NuminaRecipeBookPage)this.recipeBookPage).init(this.minecraft, i, j);
//        } else {
//            System.out.println("is NOT instance");
//
//            this.recipeBookPage.init(this.minecraft, i, j);
//        }
//        this.recipeBookPage.addListener(this);
//        this.filterButton = new ToggleWidget(i + 110, j + 12, 26, 16, this.book.isFiltering(this.menu));
//        this.initFilterButtonTextures();
//        this.tabButtons.clear();
//
//          // this is where we would replace with new NuminaRecipeTabToggleWidgets
//        for(RecipeBookCategories recipebookcategories : this.menu.getRecipeBookCategories()) {
//            this.tabButtons.add(new NuminaRecipeTabToggleWidget(recipebookcategories));
//        }
//
//        if (this.selectedTab != null) {
//            this.selectedTab = this.tabButtons.stream().filter((p_209505_1_) -> {
//                return p_209505_1_.getCategory().equals(this.selectedTab.getCategory());
//            }).findFirst().orElse((RecipeTabToggleWidget)null);
//        }
//
//        if (this.selectedTab == null) {
//            this.selectedTab = this.tabButtons.get(0);
//        }
//
//        this.selectedTab.setStateTriggered(true);
//        this.updateCollections(false);
//        this.updateTabs();
    }
}
