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

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableMuseRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nullable;

public class RangedSlider extends Clickable {
    final int cornersize = 3;

    private final double height = 16;
    protected double width;
    private String label;

    DrawableMuseRect insideRect;
    DrawableMuseRect outsideRect;

    /**
     * Is this slider control being dragged.
     */
    public boolean dragging = false;

    /**
     * The value of this slider control. Based on a value representing 0 - 100%
     */
    public double sliderValue = 1.0F;
    public double minValue = 0.0F;
    public double maxValue = 5.0F;

    @Nullable
    public ISlider parent = null;

    public RangedSlider(MusePoint2D position, double width, String label, double minVal, double maxVal, double currentVal) {
        this(position, width, label, minVal, maxVal, currentVal, null);
    }

    public RangedSlider(MusePoint2D position, String label, double minVal, double maxVal, double currentVal, ISlider par) {
        this(position, 150, label, minVal, maxVal, currentVal, par);
    }

    public RangedSlider(MusePoint2D position, double width, String label, double minVal, double maxVal, double currentVal, @Nullable ISlider iSlider) {
        this.width = width;
        this.position = position;
        this.label = label;
        createNewRects();
        minValue = minVal;
        maxValue = maxVal;
        sliderValue = (currentVal - minValue) / (maxValue - minValue);
        parent = iSlider;
    }

    void createNewRects() {
        this.insideRect = new DrawableMuseRect(position.getX() - width / 2.0F - cornersize, position.getY() + height * 0.5F, 0, position.getY() + height, Colour.ORANGE, Colour.LIGHT_BLUE);
        this.outsideRect = new DrawableMuseRect(position.getX() - width / 2.0F - cornersize, position.getY() + height * 0.5F, position.getX() + width / 2.0F + cornersize, position.getY() + height, Colour.DARKBLUE, Colour.LIGHT_BLUE);
        this.insideRect.setWidth(6);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, float zLevel) {
        if (this.isVisible) {
            if (label != null) {
                MuseRenderer.drawCenteredString(matrixStack, I18n.format(label), position.getX(), position.getY());
            }

            this.outsideRect.draw(matrixStack, zLevel);
            this.insideRect.setPosition(new MusePoint2D(this.position.getX() + this.width * (this.sliderValue - 0.5F), this.outsideRect.centery()));
            this.insideRect.draw(matrixStack, zLevel);
        }
    }

    public void update(double mouseX, double mouseY) {
        double siderStart = this.sliderValue;
        if (dragging && this.isEnabled() && this.isVisible() && this.hitBox(mouseX, mouseY)) {
            this.sliderValue = MuseMathUtils.clampDouble((mouseX - this.position.getX()) / (this.width -3) + 0.5, 0.0, 1.0);
        } else {
            this.sliderValue = MuseMathUtils.clampDouble(sliderValue, 0.0, 1.0);
        }

        if (siderStart != sliderValue && parent != null) {
            parent.onChangeSliderValue(this);
        }
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void setPosition(MusePoint2D position) {
        super.setPosition(position);
        createNewRects();
    }

    public void setWidth(double widthIn) {
        this.width = widthIn;
        createNewRects();
    }

    public int getValueInt() {
        return (int) Math.round(sliderValue * (maxValue - minValue) + minValue);
    }

    public double getValue() {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    public void setValue(double d) {
        this.sliderValue = (d - minValue) / (maxValue - minValue);
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        update((double)mouseX, (double) mouseY);
        this.dragging = false;
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isEnabled() && this.isVisible() && this.hitBox((double)mouseX, (double)mouseY)) {
            update((double)mouseX, (double)mouseY);
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean hitBox(double x, double y) {
        return Math.abs(position.getX() - x) < width / 2 &&
                Math.abs(position.getY() + 12 - y) < 4;
    }

    public interface ISlider {
        void onChangeSliderValue(RangedSlider slider);
    }
}