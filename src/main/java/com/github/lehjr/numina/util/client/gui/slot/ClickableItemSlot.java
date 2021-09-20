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

package com.github.lehjr.numina.util.client.gui.slot;

import com.github.lehjr.numina.util.client.gui.clickable.IClickable;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

/**
 * A container slot in an IInventory
 */
public class ClickableItemSlot extends UniversalSlot implements IClickable {
    protected IPressable onPressed;
    protected IReleasable onReleased;

    boolean isVisible = true;
    boolean isEnabled = true;

    public ClickableItemSlot(IInventory itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        MuseRenderer.drawItemAt(getUL().getX(), getUL().getY(), getItem());
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        if (hitBox(x, y)) {
            return !getItem().isEmpty() ? getItem().getTooltipLines(Minecraft.getInstance().player, ITooltipFlag.TooltipFlags.NORMAL) : null;
        }
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setOnPressed(IPressable onPressed) {
        this.onPressed = onPressed;
    }

    @Override
    public void setOnReleased(IReleasable onReleased) {
        this.onReleased = onReleased;
    }

    @Override
    public void onReleased() {
        if (this.isVisible && this.isEnabled && this.onReleased != null) {
            this.onReleased.onReleased(this);
        }
    }

    @Override
    public void onPressed() {
        if (this.isVisible && this.isEnabled && this.onPressed != null) {
            this.onPressed.onPressed(this);
        }
    }
}