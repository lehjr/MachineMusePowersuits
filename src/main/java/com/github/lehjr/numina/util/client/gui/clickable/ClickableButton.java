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
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

/**
 * @author MachineMuse
 */
public class ClickableButton extends Clickable {
    protected ITextComponent label;
    protected MusePoint2D radius;
    protected DrawableMuseRect rect;
    private final Colour enabledBorder  = new Colour(0.3F, 0.3F, 0.3F, 1);
    private final Colour enabledBackground = new Colour(0.5F, 0.6F, 0.8F, 1);
    private final Colour disabledBorder = new Colour(0.8F, 0.6F, 0.6F, 1);
    private final Colour disabledBackground = new Colour(0.8F, 0.3F, 0.3F, 1);

    public ClickableButton(ITextComponent label, MusePoint2D position, boolean enabled) {
        this.label = label;
        this.position = position;

        if (label.getString().contains("\n")) {
            String[] x = label.getString().split("\n");

            int longestIndex = 0;
            for (int i = 0; i < x.length; i++) {
                if (x[i].length() > x[longestIndex].length())
                    longestIndex = i;
            }
            this.radius = new MusePoint2D((float) (MuseRenderer.getStringWidth(x[longestIndex]) / 2F + 2F), 6 * x.length);
        } else {
            this.radius = new MusePoint2D((float) (MuseRenderer.getStringWidth(label.getString()) / 2F + 2F), 6);
        }

        this.rect = new DrawableMuseRect(
                position.getX() - radius.getX(),
                position.getY() - radius.getY(),
                position.getX() + radius.getX(),
                position.getY() + radius.getY(),
                enabledBorder,
                enabledBackground
        );
        this.setEnabled(enabled);
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     *
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, float zLevel) {
        renderButton(matrixStack, mouseX, mouseY, partialTicks, zLevel);
        renderText(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     * Call this from the container GUI's main render loop
     *
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, float zLevel) {
        if (isVisible) {
            this.rect.setLeft(position.getX() - radius.getX());
            this.rect.setTop(position.getY() - radius.getY());
            this.rect.setRight(position.getX() + radius.getX());
            this.rect.setBottom(position.getY() + radius.getY());
            this.rect.setBorderColour(isEnabled() ? enabledBorder : disabledBorder);
            this.rect.setBackgroundColour(isEnabled() ? enabledBackground : disabledBackground);
            this.rect.draw(matrixStack, zLevel);
        }
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     * Call this from the container GUI's drawGuiContainerForegroundLayer
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    public void renderText(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isVisible) {
            if (label.getString().contains("\n")) {
                String[] s = label.getString().split("\n");
                for (int i = 0; i < s.length; i++) {
                    MuseRenderer.drawCenteredString(matrixStack, s[i], position.getX(), position.getY() - (4 * s.length) + (i * 8));
                }
            } else {
                MuseRenderer.drawCenteredString(matrixStack, this.label.getString(), position.getX(), position.getY() - 4);
            }
        }
    }

    public MusePoint2D getRadius () {
        return radius.copy();
    }

    @Override
    public boolean hitBox(double x, double y) {
        boolean hitx = Math.abs(position.getX() - x) < radius.getX();
        boolean hity = Math.abs(position.getY() - y) < radius.getY();
        return hitx && hity;
    }

    @Override
    public List<ITextComponent> getToolTip() {
        return null;
    }

    public void buttonOn() {
        this.setEnabled(true);
        this.setVisible(true);
    }

    public void buttonOff() {
        this.setEnabled(false);
        this.setVisible(false);
    }

    public ClickableButton setLable(ITextComponent label) {
        this.label = label;
        return this;
    }

    public ITextComponent getLabel() {
        return label;
    }
}
