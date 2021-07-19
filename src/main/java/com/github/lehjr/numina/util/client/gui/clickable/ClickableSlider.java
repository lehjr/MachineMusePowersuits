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

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:08 AM, 06/05/13
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 *
 * TODO: revisit and rewrite
 *
 */
public class ClickableSlider extends Clickable {
    final int cornersize = 3;
    private double valueInternal = 0;
    MusePoint2D pos;
    double width;
    private String id;
    private ITextComponent label;
    DrawableRelativeRect insideRect;
    DrawableRelativeRect outsideRect;

    public ClickableSlider(MusePoint2D pos, double width, String id, ITextComponent label) {
        this.pos = pos;
        this.width = width;
        this.id = id;
        this.setPosition(pos);
        this.insideRect = new DrawableRelativeRect(getPosition().getX() - width / 2.0 - cornersize, getPosition().getY() + 8, 0, getPosition().getY() + 16, Colour.ORANGE, Colour.LIGHT_BLUE);
        this.outsideRect = new DrawableRelativeRect(getPosition().getX() - width / 2.0 - cornersize, getPosition().getY() + 8, getPosition().getX() + width / 2.0F + cornersize, getPosition().getY() + 16, Colour.DARKBLUE, Colour.LIGHT_BLUE);
        this.label = label;
    }

    public String id() {
        return this.id;
    }

    public void setLabel(ITextComponent label) {
        this.label = label;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        MuseRenderer.drawCenteredText(matrixStack, label, (float)getPosition().getX(), (float)getPosition().getY(), Colour.WHITE);
        this.insideRect.setRight(getPosition().getX() + width * ((float)getValue() - 0.5F) + cornersize);
        this.outsideRect.render(matrixStack, mouseX, mouseY, frameTime);
        this.insideRect.render(matrixStack, mouseX, mouseY, frameTime);
    }

    @Override
    public boolean hitBox(double x, double y) {
        return Math.abs(getPosition().getX() - x) < width / 2 &&
                Math.abs(getPosition().getY() + 12 - y) < 4;
    }

    @Override
    public void onPressed() {
        // TODO: this
    }

    public double getValue() {
        return valueInternal;
    }

    public void setValue(double v) {
        valueInternal = v;
    }

    public void setValueByX(double x) {
        valueInternal = MuseMathUtils.clampDouble((x - pos.getX()) / width + 0.5F, 0, 1);
    }
}