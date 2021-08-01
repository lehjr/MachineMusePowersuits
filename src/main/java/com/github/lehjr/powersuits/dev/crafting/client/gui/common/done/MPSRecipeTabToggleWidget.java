package com.github.lehjr.powersuits.dev.crafting.client.gui.common.done;

import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.util.client.gui.gemoetry.*;
import com.github.lehjr.numina.util.client.render.MuseIconUtils;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeTabToggleWidget;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 *
 */
public class MPSRecipeTabToggleWidget extends RecipeTabToggleWidget implements IDrawableRect {
    private final Colour activeColor = Colour.GREY_GUI_BACKGROUND;
    private final Colour inactiveColor = Colour.DARK_GREY.withAlpha(0.8F);
    DrawableRelativeRect tabRectangle = new DrawableRelativeRect(0, 0, 0, 27, inactiveColor, Colour.BLACK);
    Minecraft minecraft;
    @Nonnull
    ItemStack icon;
    private final RecipeBookCategories category;
    EquipmentSlotType slotType;

    public MPSRecipeTabToggleWidget(@Nonnull ItemStack iconIn, EquipmentSlotType slotType) {
        super(RecipeBookCategories.CRAFTING_SEARCH);
        this.icon = iconIn;
        this.category = RecipeBookCategories.CRAFTING_SEARCH;
        tabRectangle.initGrowth();
        this.slotType = slotType;
        this.minecraft = Minecraft.getInstance();
    }

    public EquipmentSlotType getSlotType() {
        return this.slotType;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        int xChange = this.isStateTriggered ? 2 : 0;
        tabRectangle.setLeft(this.x - xChange);
        tabRectangle.setTop(this.y);
        tabRectangle.setWidth(28+ xChange);
        tabRectangle.setBackgroundColour(this.isStateTriggered ? activeColor : inactiveColor);
        tabRectangle.render(matrixStack, mouseX, mouseY, frameTime);
        this.renderIcon(matrixStack);
    }

    /**
     * Renders the item icons for the tabs. Some tabs have 2 icons, some just one.
     */
    private void renderIcon(MatrixStack matrixStack) {
        int offset = this.isStateTriggered ? -5 : -3;
        RenderSystem.disableDepthTest();
        if (this.icon.isEmpty()) {
            if (EquipmentSlotType.MAINHAND.equals(slotType)) {
                MuseIconUtils.drawIconAt(x + (this.isStateTriggered ? 4 : 5), y + 7, MuseIconUtils.getIcon().weaponSlotBackground.getSprite(), Colour.WHITE);
            } else {
                Pair<ResourceLocation, ResourceLocation> pair = NuminaObjects.getSlotBackground(slotType);
                if (pair != null) {
                    TextureAtlasSprite textureatlassprite = this.minecraft.getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
                    Minecraft.getInstance().getTextureManager().bind(textureatlassprite.atlas().location());
                    blit(matrixStack, x + (this.isStateTriggered ? 5 : 6), y + 7, this.getBlitOffset(), 16, 16, textureatlassprite);
                }
            }
            RenderSystem.enableDepthTest();
        } else {
            minecraft.getItemRenderer().renderAndDecorateItem(icon, this.x + 9 + offset, this.y + 6);
        }
    }

    @Override
    public void setPosition(int x, int y) {
        tabRectangle.setUL(new MusePoint2D(x, y));
        super.setPosition(x, y);
    }

    @Override
    public MusePoint2D getUL() {
        return tabRectangle.getUL();
    }

    @Override
    public MusePoint2D getWH() {
        return tabRectangle.getWH();
    }

    @Override
    public double left() {
        return tabRectangle.left();
    }

    @Override
    public double finalLeft() {
        return tabRectangle.finalLeft();
    }

    @Override
    public double top() {
        return tabRectangle.top();
    }

    @Override
    public double finalTop() {
        return tabRectangle.finalTop();
    }

    @Override
    public double right() {
        return tabRectangle.right();
    }

    @Override
    public double finalRight() {
        return tabRectangle.finalRight();
    }

    @Override
    public double bottom() {
        return tabRectangle.bottom();
    }

    @Override
    public double finalBottom() {
        return tabRectangle.finalBottom();
    }

    @Override
    public double width() {
        return tabRectangle.width();
    }

    @Override
    public double finalWidth() {
        return tabRectangle.finalWidth();
    }

    @Override
    public double height() {
        return tabRectangle.height();
    }

    @Override
    public double finalHeight() {
        return tabRectangle.finalHeight();
    }

    @Override
    public IRect setUL(MusePoint2D musePoint2D) {
        super.setPosition((int)musePoint2D.getX(), (int)musePoint2D.getY());
        return tabRectangle.setUL(musePoint2D);
    }

    @Override
    public IRect setWH(MusePoint2D musePoint2D) {
        return tabRectangle;
    }

    @Override
    public IRect setLeft(double v) {
        super.setPosition((int)v, y);
        return tabRectangle.setLeft(v);
    }

    @Override
    public IRect setRight(double v) {
        return setLeft(v - finalWidth());
    }

    @Override
    public IRect setTop(double v) {
        super.setPosition(x, (int)v);
        return tabRectangle.setTop(v);
    }

    @Override
    public IRect setBottom(double v) {
        return setTop(v - finalHeight());
    }

    @Override
    public IRect setWidth(double v) {
        return tabRectangle;
    }

    @Override
    public IRect setHeight(double v) {
        return tabRectangle;
    }

    @Override
    public void move(MusePoint2D musePoint2D) {
        tabRectangle.move(musePoint2D);
        super.setPosition((int)tabRectangle.finalLeft(), (int)tabRectangle.finalTop());
    }

    @Override
    public void move(double v, double v1) {
        tabRectangle.move(v, v1);
        super.setPosition((int)tabRectangle.finalLeft(), (int)tabRectangle.finalTop());
    }

    @Override
    public void setPosition(MusePoint2D musePoint2D) {
        tabRectangle.setPosition(musePoint2D);
        super.setPosition((int)tabRectangle.finalLeft(), (int)tabRectangle.finalTop());
    }

    @Override
    public boolean growFromMiddle() {
        return tabRectangle.growFromMiddle();
    }

    @Override
    public void initGrowth() {
        tabRectangle.initGrowth();
        super.setPosition((int)tabRectangle.finalLeft(), (int)tabRectangle.finalTop());
    }

    @Override
    public IRect setMeLeftOf(IRect relativeRect) {
        tabRectangle.setMeLeftOf(relativeRect);
        super.setPosition((int)tabRectangle.finalLeft(), (int)tabRectangle.finalTop());
        return tabRectangle;
    }

    @Override
    public IRect setMeRightOf(IRect relativeRect) {
        tabRectangle.setMeRightOf(relativeRect);
        super.setPosition((int)tabRectangle.finalLeft(), (int)tabRectangle.finalTop());
        return tabRectangle;
    }

    @Override
    public IRect setMeAbove(IRect relativeRect) {
        tabRectangle.setMeAbove(relativeRect);
        super.setPosition((int)tabRectangle.finalLeft(), (int)tabRectangle.finalTop());
        return tabRectangle;
    }

    @Override
    public IRect setMeBelow(IRect relativeRect) {
        tabRectangle.setMeBelow(relativeRect);
        super.setPosition((int)tabRectangle.finalLeft(), (int)tabRectangle.finalTop());
        return tabRectangle;
    }

    @Override
    public void setOnInit(IInit iInit) {

    }

    @Override
    public void onInit() {

    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float v) {
        return tabRectangle;
    }
}