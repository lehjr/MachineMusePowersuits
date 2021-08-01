//package com.github.lehjr.powersuits.client.gui.dev.common;
//
//import com.github.lehjr.numina.util.client.gui.clickable.ClickableArrow;
//import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableArrow;
//import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
//import com.github.lehjr.numina.util.client.sound.Musique;
//import com.github.lehjr.numina.util.client.sound.SoundDictionary;
//import com.github.lehjr.numina.util.math.Colour;
//import com.google.common.collect.Lists;
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.recipebook.*;
//import net.minecraft.client.renderer.RenderHelper;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.RecipeBook;
//
//import javax.annotation.Nullable;
//import java.util.List;
//
//public class MPSRecipeBookPage extends RecipeBookPage {
//    private final List<MPSRecipeWidget> buttons = Lists.newArrayListWithCapacity(20);
//    private MPSRecipeWidget hoveredButton;
//    private final RecipeOverlayGui overlay = new RecipeOverlayGui();
//    private Minecraft minecraft;
//    private final List<IRecipeUpdateListener> listeners = Lists.newArrayList();
//    private List<RecipeList> recipeLists;
//    private ClickableArrow forwardArrow;
//    private ClickableArrow backArrow;
//    private int totalPages;
//    private int currentPage;
//    private RecipeBook recipeBook;
//    private IRecipe<?> lastClickedRecipe;
//    private RecipeList lastClickedRecipeList;
//    private final Colour arrowBorderColour = Colour.LIGHT_BLUE.withAlpha(0.8F);
//    private final Colour arrowNormalBackGound = new Colour(0.1F, 0.3F, 0.4F, 0.7F);
//    private final Colour arrowHighlightedBackground = Colour.WHITE;
//
//
//    public MPSRecipeBookPage() {
//        for(int i = 0; i < 20; ++i) {
//            this.buttons.add(new MPSRecipeWidget());
//        }
//
//        forwardArrow = new ClickableArrow(0, 0, 0, 0, true, arrowNormalBackGound, arrowHighlightedBackground, arrowBorderColour);
//        forwardArrow.setDrawShaft(false);
//
//        backArrow = new ClickableArrow(0, 0, 0, 0, true, arrowNormalBackGound, arrowHighlightedBackground, arrowBorderColour);
//        backArrow.setDrawShaft(false);
//        backArrow.setDirection(DrawableArrow.ArrowDirection.LEFT);
//    }
//
//    public void init(Minecraft minecraft, int x, int y) {
//        this.minecraft = minecraft;
//        this.recipeBook = minecraft.player.getRecipeBook();
//
//        for(int i = 0; i < this.buttons.size(); ++i) {
//            this.buttons.get(i).setPosition(x + 11 + 25 * (i % 5), y + 31 + 25 * (i / 5));
//        }
//
//        this.forwardArrow.setTargetDimensions(new MusePoint2D(x + 93, y + 137), new MusePoint2D(12, 17));
//        this.backArrow.setTargetDimensions(new MusePoint2D(x + 38, y + 137), new MusePoint2D(12, 17));
//    }
//
//    public void addListener(RecipeBookGui bookGui) {
//        this.listeners.remove(bookGui);
//        this.listeners.add(bookGui);
//    }
//
//    public void updateCollections(List<RecipeList> recipeLists, boolean p_194192_2_) {
//        this.recipeLists = recipeLists;
//        this.totalPages = (int)Math.ceil((double)recipeLists.size() / 20.0D);
//        if (this.totalPages <= this.currentPage || p_194192_2_) {
//            this.currentPage = 0;
//        }
//
//        this.updateButtonsForPage();
//    }
//
//    private void updateButtonsForPage() {
//        int i = 20 * this.currentPage;
//
//        for(int j = 0; j < this.buttons.size(); ++j) {
//            MPSRecipeWidget recipewidget = this.buttons.get(j);
//            if (i + j < this.recipeLists.size()) {
//                RecipeList recipelist = this.recipeLists.get(i + j);
//                recipewidget.init(recipelist, this);
//                recipewidget.visible = true;
//            } else {
//                recipewidget.visible = false;
//            }
//        }
//
//        this.updateArrowButtons();
//    }
//
//    private void updateArrowButtons() {
//        this.forwardArrow.setVisible(this.totalPages > 1 && this.currentPage < this.totalPages - 1);
//        this.backArrow.setVisible(this.totalPages > 1 && this.currentPage > 0);
//    }
//
//    @Override
//    public void render/*render*/(MatrixStack matrixStack, int x, int y, int mouseX, int mouseY, float partialTicks) {
//        if (this.totalPages > 1) {
//            String s = this.currentPage + 1 + "/" + this.totalPages;
//            int i = this.minecraft.font.width(s);
//            this.minecraft.font.draw(matrixStack, s, (float)(x - i / 2 + 73), (float)(y + 141), -1);
//        }
//
//        RenderHelper.turnOff();
//        this.hoveredButton = null;
//
//        for(MPSRecipeWidget recipewidget : this.buttons) {
//            recipewidget.render(matrixStack, mouseX, mouseY, partialTicks);
//            if (recipewidget.visible && recipewidget.isHovered()) {
//                this.hoveredButton = recipewidget;
//            }
//        }
//
//        forwardArrow.render(matrixStack, mouseX, mouseY, partialTicks, 0);
//        backArrow.render(matrixStack, mouseX, mouseY, partialTicks, 0);
//        this.overlay.render(matrixStack, mouseX, mouseY, partialTicks);
//    }
////
////    public void renderTooltip(int mouseX, int mouseY) {
////        if (this.minecraft.currentScreen != null && this.hoveredButton != null && !this.overlay.isVisible()) {
////            this.minecraft.currentScreen.renderTooltip(this.hoveredButton.getToolTipText(this.minecraft.currentScreen), mouseX, mouseY);
////        }
////    }
//
//    @Override
//    public void renderTooltip/*renderTooltip*/(MatrixStack matrixStack, int mouseX, int mouseY) {
//        if (this.minecraft.screen != null && this.hoveredButton != null && !this.overlay.isVisible()) {
//            this.minecraft.screen.renderComponentTooltip(matrixStack, this.hoveredButton.getTooltipText(this.minecraft.screen), mouseX, mouseY);
//        }
//    }
//
//    @Override
//    @Nullable
//    public IRecipe<?> getLastClickedRecipe() {
//        return this.lastClickedRecipe;
//    }
//
//    @Override
//    @Nullable
//    public RecipeList getLastClickedRecipeCollection() {
//        return this.lastClickedRecipeList;
//    }
//
//    @Override
//    public void setInvisible() {
//        this.overlay.setVisible(false);
//    }
//
//    @Override
//    public boolean /*mouseClicked */mouseClicked(double mouseX, double mouseY, int mouseButton, int p_198955_6_, int p_198955_7_, int p_198955_8_, int p_198955_9_) {
//        this.lastClickedRecipe = null;
//        this.lastClickedRecipeList = null;
//        if (this.overlay.isVisible()) {
//            if (this.overlay.mouseClicked(mouseX, mouseY, mouseButton)) {
//                this.lastClickedRecipe = this.overlay.getLastRecipeClicked();
//                this.lastClickedRecipeList = this.overlay.getRecipeCollection();
//            } else {
//                this.overlay.setVisible(false);
//            }
//            return true;
//        } else if (forwardArrow.hitBox(mouseX, mouseY) && mouseButton == 0) {
//            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
//            ++this.currentPage;
//            this.updateButtonsForPage();
//            return true;
//
//        } else if (this.backArrow.hitBox(mouseX, mouseY) && mouseButton == 0) {
//            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
//            --this.currentPage;
//            this.updateButtonsForPage();
//            return true;
//        } else {
//            for(MPSRecipeWidget recipewidget : this.buttons) {
//                if (recipewidget.mouseClicked(mouseX, mouseY, mouseButton)) {
//                    if (mouseButton == 0) {
//                        System.out.println("doing something here");
//
//                        this.lastClickedRecipe = recipewidget.getRecipe();
//                        this.lastClickedRecipeList = recipewidget.getCollection();
//                    } else if (mouseButton == 1 && !this.overlay.isVisible() && !recipewidget.isOnlyOption()) {
//                        this.overlay./*init*/init(this.minecraft, recipewidget.getCollection(), recipewidget.x, recipewidget.y, p_198955_6_ + p_198955_8_ / 2, p_198955_7_ + 13 + p_198955_9_ / 2, (float)recipewidget.getWidth());
//
//                        System.out.println("doing something here instead");
//                    }
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
//
//    @Override
//    public void recipesShown(List<IRecipe<?>> p_194195_1_) {
//        for(IRecipeUpdateListener irecipeupdatelistener : this.listeners) {
//            irecipeupdatelistener.recipesShown(p_194195_1_);
//        }
//
//    }
//
//    @Override
//    public Minecraft getMinecraft() {
//        return this.minecraft;
//    }
//
//    @Override
//    public RecipeBook getRecipeBook() {
//        return this.recipeBook;
//    }
//}