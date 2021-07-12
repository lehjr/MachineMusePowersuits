/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.client.gui.clickable;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Used in GUI's as a button
 * @author MachineMuse
 */
public class ClickableItem extends Clickable {
    public static final int offsetx = 8;
    public static final int offsety = 8;
    public int inventorySlot;
    protected boolean isEnabled;
    protected boolean isVisible;
    public Integer containerIndex;
    PlayerInventory itemHandler;

    public ClickableItem(MusePoint2D pos, int inventorySlot) {
        super(pos);
        this.inventorySlot = inventorySlot;
        this.isEnabled = true;
        this.isVisible = true;
        this.itemHandler = Minecraft.getInstance().player.inventory;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, float zLevel) {
        MuseRenderer.drawItemAt(
                getPosition().getX() - offsetx,
                getPosition().getY() - offsety, getStack());
        if (inventorySlot > 35 || itemHandler.getSelected() == getStack()) {
            matrixStack.pushPose();
            matrixStack.translate(0,0,310);
            MuseRenderer.drawString(matrixStack, "e", getPosition().getX() + 3, getPosition().getY() + 1, Colour.DARK_GREEN);
            matrixStack.popPose();
        }
    }

    @Override
    public void move(MusePoint2D position) {
        this.position = position;
    }

    @Override
    public boolean hitBox(double x, double y) {
        boolean hitx = Math.abs(x - getPosition().getX()) < offsetx;
        boolean hity = Math.abs(y - getPosition().getY()) < offsety;
        return hitx && hity;
    }

    @Override
    public void setEnabled(boolean b) {
        this.isEnabled = b;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean b) {
        isVisible = b;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Nonnull
    public ItemStack getStack() {
        return itemHandler.getItem(inventorySlot);
    }

    public int getSlotIndex() {
        return inventorySlot;
    }

    @Override
    public List<ITextComponent> getToolTip() {
        return getStack().getTooltipLines(Minecraft.getInstance().player, ITooltipFlag.TooltipFlags.NORMAL);
    }
}
