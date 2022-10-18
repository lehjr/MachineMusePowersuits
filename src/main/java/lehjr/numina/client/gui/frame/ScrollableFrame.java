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

package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.gemoetry.DrawableTile;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.RelativeRect;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class ScrollableFrame extends DrawableTile implements IScrollable {
    protected int buttonSize = 5;
    protected int totalSize;
    protected int currentScrollPixels;
    protected boolean visible = true;
    protected boolean enabled = true;
    protected float zLevel;
    boolean drawBackground = false;
    boolean drawBorder = false;

    public ScrollableFrame() {
        super(0,0,0,0);
    }

    public ScrollableFrame(Colour background, Colour topBorder, Colour bottomBorder) {
        super(0,0,0,0);
        setBackgroundColour(background);
        setBottomBorderColour(bottomBorder);
        setTopBorderColour(topBorder);
    }

    public ScrollableFrame(MusePoint2D topleft, MusePoint2D bottomright, Colour background, Colour topBorder, Colour bottomBorder) {
        super(topleft, bottomright);
        setBackgroundColour(background);
        setBottomBorderColour(bottomBorder);
        setTopBorderColour(topBorder);
    }

    @Override
    public int getButtonSize() {
        return buttonSize;
    }

    @Override
    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    void setDrawBorder(boolean drawBorder) {
         this.drawBorder = drawBorder;
    }

    @Override
    public RelativeRect getRect() {
        return this;
    }

    @Override
    public int getTotalSize() {
        return this.totalSize;
    }

    @Override
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int getCurrentScrollPixels() {
        return this.currentScrollPixels;
    }

    @Override
    public void setCurrentScrollPixels(int scrollPixels) {
        this.currentScrollPixels = scrollPixels;
    }

    public int getMaxScrollPixels() {
        return (int) Math.max(totalSize - height(), 0);
    }

    public double getScrollAmount() {
        return 8;
    }

    public void setScrollAmount(double scrollAmount) {
        this.currentScrollPixels = (int) MathUtils.clampDouble(scrollAmount, 0, getMaxScrollPixels());
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (isVisible()) {
            if (drawBackground) {
                this.drawBackground(matrixStack);
            }
            if (drawBorder) {
                this.drawBorder(matrixStack, 0);
            }
            super.render(matrixStack, mouseX, mouseY, frameTime);
            preRender(matrixStack, mouseX, mouseY, frameTime);
            postRender(mouseX, mouseY, frameTime);
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }
}