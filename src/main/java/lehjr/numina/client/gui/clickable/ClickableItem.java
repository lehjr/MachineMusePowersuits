///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.numina.client.gui.clickable;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import lehjr.numina.client.gui.gemoetry.MusePoint2D;
//import lehjr.numina.client.render.NuminaRenderer;
//import lehjr.numina.common.math.Colour;
//import lehjr.numina.common.string.StringUtils;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.text.ITextComponent;
//
//import javax.annotation.Nonnull;
//import java.util.List;
//
///**
// * Used in GUI's as a button
// * @author MachineMuse
// */
//public class ClickableItem extends Clickable {
//    public static final int offsetx = 8;
//    public static final int offsety = 8;
//    public int inventorySlot;
//    protected boolean isEnabled;
//    protected boolean isVisible;
//    public Integer containerIndex;
//    PlayerInventory itemHandler;
//
//    public ClickableItem(MusePoint2D pos, int inventorySlot) {
//        super(pos);
//        this.inventorySlot = inventorySlot;
//        this.isEnabled = true;
//        this.isVisible = true;
//        this.itemHandler = Minecraft.getInstance().player.inventory;
//    }
//
//    @Override
//    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        NuminaRenderer.drawItemAt(
//                centerX() - offsetx,
//                centerY() - offsety, getStack());
//        if (inventorySlot > 35 || itemHandler.getSelected() == getStack()) {
//            matrixStack.pushPose();
//            matrixStack.translate(0,0,310);
//            StringUtils.drawShadowedString(matrixStack, "e", centerX() + 3, centerY() + 1, Colour.DARK_GREEN);
//            matrixStack.popPose();
//        }
//    }
//
//    @Override
//    public boolean containsPoint(double x, double y) {
//        boolean hitx = Math.abs(x - centerX()) < offsetx;
//        boolean hity = Math.abs(y - centerY()) < offsety;
//        return hitx && hity;
//    }
//
//    @Override
//    public void setEnabled(boolean b) {
//        this.isEnabled = b;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    @Override
//    public void setVisible(boolean b) {
//        isVisible = b;
//    }
//
//    @Override
//    public boolean isVisible() {
//        return isVisible;
//    }
//
//    @Nonnull
//    public ItemStack getStack() {
//        return itemHandler.getItem(inventorySlot);
//    }
//
//    public int getSlotIndex() {
//        return inventorySlot;
//    }
//
//    @Override
//    public List<ITextComponent> getToolTip(int x, int y) {
//        if (containsPoint(x, y)) {
//            return getStack().getTooltipLines(Minecraft.getInstance().player, ITooltipFlag.TooltipFlags.NORMAL);
//        }
//        return null;
//    }
//}
