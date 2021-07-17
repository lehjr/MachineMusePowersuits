//package com.github.lehjr.numina.dev.crafting.client.gui;
//
//import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
//import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
//import com.github.lehjr.numina.util.math.Colour;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.platform.GlStateManager;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.recipebook.RecipeTabToggleWidget;
//import net.minecraft.client.renderer.ItemRenderer;
//import net.minecraft.client.util.RecipeBookCategories;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.NonNullList;
//
///**
// *
// */
//public class NuminaRecipeTabToggleWidget extends RecipeTabToggleWidget {
//    private final Colour activeColor = Colour.GREY_GUI_BACKGROUND;
//    private final Colour inactiveColor = Colour.DARK_GREY.withAlpha(0.8F);
//    DrawableRelativeRect tabRectangle = new DrawableRelativeRect(0, 0, 0, 0, inactiveColor, Colour.LIGHT_BLUE.withAlpha(0.8F));;
//    NonNullList<ItemStack> displayStacks;
//    private final RecipeBookCategories category;
//
//
//    public NuminaRecipeTabToggleWidget(NonNullList<ItemStack> displayStacksIn) {
//        super(RecipeBookCategories.CRAFTING_SEARCH);
//        this.displayStacks = displayStacksIn;
//        this.category = RecipeBookCategories.CRAFTING_SEARCH;
//    }
//
//    public NuminaRecipeTabToggleWidget(RecipeBookCategories categories) {
//        super(categories);
//        this.category = categories;
//        this.displayStacks = NonNullList.create();
//    }
//
//    @Override
//    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        int xChange = this.isStateTriggered ? 2 : 0;
//        tabRectangle.setTargetDimensions(new MusePoint2D(this.x - xChange, this.y), new MusePoint2D(28+ xChange, 27));
//        tabRectangle.setBackgroundColour(this.isStateTriggered ? activeColor : inactiveColor);
//        tabRectangle.draw(matrixStack, 0);
//        Minecraft minecraft = Minecraft.getInstance();
//
////        // render the item models
////        RenderHelper.enableGUIStandardItemLighting();
//        GlStateManager._disableLighting();
//        this.renderIcon(minecraft.getItemRenderer());
////        GlStateManager.enableLighting();
////        RenderHelper.disableStandardItemLighting();
//    }
//
//
//
//
//    /*
//    package com.github.lehjr.modularpowerarmor.client.gui.crafting;
//
//import com.github.lehjr.mpalib.client.gui.geometry.DrawableRect;
//import com.github.lehjr.mpalib.client.gui.geometry.Point2D;
//import com.github.lehjr.mpalib.client.sound.Musique;
//import com.github.lehjr.mpalib.client.sound.SoundDictionary;
//import com.github.lehjr.mpalib.math.Colour;
//import com.mojang.blaze3d.platform.GlStateManager;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.recipebook.RecipeTabToggleWidget;
//import net.minecraft.client.renderer.ItemRenderer;
//import net.minecraft.client.renderer.RenderHelper;
//import net.minecraft.client.util.RecipeBookCategories;
//import net.minecraft.item.ItemStack;
//
//import java.util.List;
//
//public class MPSRecipeTabToggleWidget extends RecipeTabToggleWidget {
//    private final RecipeBookCategories category;
//    private final Colour activeColor = new Colour(0.1F, 0.3F, 0.4F, 0.7F);
//    private final Colour inactiveColor = Colour.DARKBLUE.withAlpha(0.8);
//
//    DrawableRect tabRectangle;
//    public MPSRecipeTabToggleWidget(RecipeBookCategories category) {
//        super(category);
//        this.initTextureValues(153, 2, 35, 0, MPSRecipeBookGui.RECIPE_BOOK);
//        this.category = category;
//        this.tabRectangle = new DrawableRect(0, 0, 0, 0, inactiveColor, Colour.LIGHTBLUE.withAlpha(0.8));
//    }
//
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
//        if (this.active && this.visible) {
//            if (this.isValidClickButton(mouseButton)) {
//                boolean flag = this.clicked(mouseX, mouseY);
//                if (flag) {
//                    Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
//                    this.onClick(mouseX, mouseY);
//                    return true;
//                }
//            }
//
//            return false;
//        } else {
//            return false;
//        }
//    }
//     */
//    /**
//     * Renders the item icons for the tabs. Some tabs have 2 icons, some just one.
//     *
//     * @param renderer
//     */
//    private void renderIcon(ItemRenderer renderer) {
//        int offset = this.isStateTriggered ? -4 : -2;
//
//        if (displayStacks.isEmpty()) {
//            displayStacks.addAll(this.category.getIconItems());
//        }
//
//        if (displayStacks.size() == 1) {
//            renderer.renderAndDecorateItem(displayStacks.get(0), this.x + 9 + offset, this.y + 5);
//        } else if (displayStacks.size() == 2) {
//            renderer.renderAndDecorateItem(displayStacks.get(0), this.x + 3 + offset, this.y + 5);
//            renderer.renderAndDecorateItem(displayStacks.get(1), this.x + 14 + offset, this.y + 5);
//        }
//    }
//}