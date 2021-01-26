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
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;


// fixme: revisit and rewrite
public class ClickableLabel implements IClickable {
    protected IPressable onPressed;
    protected IReleasable onReleased;

    protected String label;
    protected MusePoint2D position;
    protected JustifyMode mode;

    boolean isEnabled = true;
    boolean isVisible = true;

    public ClickableLabel(String label, MusePoint2D position) {
        this.label = label;
        this.position = position;
        this.mode = JustifyMode.CENTERED;
    }

    public ClickableLabel(String label, MusePoint2D position, JustifyMode mode) {
        this.label = label;
        this.position = position;
        this.mode = mode;
    }

    public ClickableLabel setMode(JustifyMode mode) {
        this.mode = mode;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // fixme: don't think this is actually working as intended
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, float zLevel) {
        switch (mode) {
            case LEFT:
                MuseRenderer.drawLeftAlignedStringString(matrixStack, this.label, position.getX(), position.getY() - 4);
                break;

            case CENTERED:
                MuseRenderer.drawCenteredString(matrixStack, this.label, position.getX(), position.getY() - 4);
                break;

            case RIGHT:
                MuseRenderer.drawRightAlignedString(matrixStack, this.label, position.getX(), position.getY() - 4);
                break;
        }
    }

    @Override
    public boolean hitBox(double x, double y) {
        if (label == null || label.isEmpty()) {
            return false;
        }

        MusePoint2D radius = new MusePoint2D((double) (MuseRenderer.getStringWidth(label) / 2F + 2F), 6);
        boolean hitx = Math.abs(position.getX() - x) < radius.getX();
        boolean hity = Math.abs(position.getY() - y) < radius.getY();
        return hitx && hity;
    }

    public enum JustifyMode {
        LEFT,
        CENTERED,
        RIGHT;

        JustifyMode() {
        }
    }


    @Override
    public List<ITextComponent> getToolTip() {
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        isVisible = visible;
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

    @Override
    public MusePoint2D getPosition() {
        return position;
    }

    /**
     * Done this way so that moving positions (like FlyFromPointToPoint) don't (shouldn't) get messed up ...
     * @param position
     */
    @Override
    public void setPosition(MusePoint2D position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }
}
