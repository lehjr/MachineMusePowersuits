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

package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.geometry.*;

/**
 * Defines a generic clickable itemStack for a MuseGui.
 *
 * @author MachineMuse
 */
public abstract class Clickable<T extends IRect> implements IClickable, IRectWrapper {
    /** run this extra code when pressed */
    IPressable onPressed;
    /** run this extra code when released */
    IReleasable onReleased;
    boolean isEnabled = true;
    boolean isVisible = true;
    float blitOffset = 0;

    T rect;

    public Clickable(T rect) {
        this.rect = rect;
    }

    public Clickable(double left, double top, double right, double bottom) {
        this.rect = (T) new Rect(left, top, right, bottom);
    }

    public Clickable(double left, double top, double right, double bottom, boolean growFromMiddle) {
        this.rect = (T) new Rect(left, top, right, bottom, growFromMiddle);
    }

    public Clickable(MusePoint2D ul, MusePoint2D br) {
        this.rect = (T) new Rect(ul, br);
    }

    public Clickable(MusePoint2D ul, MusePoint2D br, boolean growFromMiddle) {
        this.rect = (T) new Rect(ul, br, growFromMiddle);
    }

    @Override
    public T getRect() {
        return this.rect;
    }

    @Override
    public void setRect(IRect rect) {
        this.rect = (T) rect;
    }

    @Override
    public float getZLevel() {
        return blitOffset;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        this.blitOffset = zLevel;
        return this;
    }

//    @Override
//    public void moveBy(double x, double y) {
//        IClickable.super.moveBy(x, y);
//    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (rect instanceof IDrawableRect) {
            ((IDrawableRect) rect).render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }


    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public void enable() {
        this.isEnabled = true;
    }

    @Override
    public void disable() {
        this.isEnabled = false;
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
    public void hide() {
        this.isVisible = false;
    }

    @Override
    public void show() {
        this.isVisible = true;
    }

    @Override
    public void enableAndShow() {
        this.enable();
        this.show();
    }

    @Override
    public void disableAndHide() {
        this.disable();
        this.hide();
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
    public void onPressed() {
        if (this.isVisible() && this.isEnabled() && this.onPressed != null) {
            this.onPressed.onPressed(this);
        }
    }

    @Override
    public void onReleased() {
        if (this.isVisible() && this.isEnabled() && this.onReleased != null) {
            this.onReleased.onReleased(this);
        }
    }
}