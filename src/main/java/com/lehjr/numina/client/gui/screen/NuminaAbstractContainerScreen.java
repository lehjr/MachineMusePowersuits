package com.lehjr.numina.client.gui.screen;

import com.lehjr.numina.client.gui.IIConProvider;
import com.lehjr.numina.client.gui.frame.IGuiScreen;
import com.lehjr.numina.client.gui.geometry.IRect;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public abstract class NuminaAbstractContainerScreen<T extends AbstractContainerMenu, R extends IRect> extends AbstractContainerScreen<T> implements IGuiScreen {
    IRect rect;
    public NuminaAbstractContainerScreen(T menu, R rect, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.rect = rect;
    }

    @Override
    protected void init() {
        super.init();
        rect.setWH(imageWidth, imageHeight);
        rect.setUL(leftPos, topPos);
    }

    @Override
    protected abstract void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY);

    @Override
    public void renderSlot(PoseStack matrixStack, Slot slot) {
//        if (slot!= null && slot instanceof IHideableSlot) {
//            if (slot.isActive()) {
//                super.renderSlot(matrixStack, slot);
//            }
//        } else {
            super.renderSlot(matrixStack, slot);
//        }

        if (slot instanceof IIConProvider && slot.getItem().isEmpty() && slot.isActive() ) {
            this.setBlitOffset(100);
            this.itemRenderer.blitOffset = 100.0F;
            ((IIConProvider) slot).drawIconAt(matrixStack, slot.x, slot.y, Color.WHITE);
            this.itemRenderer.blitOffset = 0.0F;
            this.setBlitOffset(0);
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        IGuiScreen.super.renderLabels(matrixStack, mouseX, mouseY);
    }

    @Override
    public void setRect(IRect rect) {
        this.rect = rect;
    }

    @Override
    public IRect getRect() {
        return rect;
    }

    @Override
    public IRect setLeft(double value) {
        this.leftPos = (int) value;
        return IGuiScreen.super.setLeft(value);
    }

    @Override
    public IRect setTop(double value) {
        this.leftPos = (int) value;
        return IGuiScreen.super.setTop(value);
    }

    @Override
    public void doThisOnChange() {
        iDoThis.doThis(this);
    }

    IDoThis iDoThis = null;
    @Override
    public void setDoThisOnChange(IDoThis iDoThis) {
        this.iDoThis = iDoThis;
    }

    @Override
    public int getScreenWidth() {
        return this.width;
    }

    @Override
    public int getScreenHeight() {
        return this.height;
    }

    @Override
    public int getImageWidth() {
        return this.imageWidth;
    }

    @Override
    public int getImageHeight() {
        return this.imageHeight;
    }
}
