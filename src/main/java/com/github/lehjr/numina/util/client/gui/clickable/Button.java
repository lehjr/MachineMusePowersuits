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
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;

public class Button extends DrawableRelativeRect implements IClickable {
    protected IPressable onPressed;
    protected IReleasable onReleased;

    protected boolean isEnabled = true;
    protected boolean isVisible = true;
    Colour backgroundColourEnabled;
    Colour backgroundColourDisabled;
    Colour borderColourEnabled;
    Colour borderColourDisabled;

    int highlight = 5;

    public Button(double left, double top, double right, double bottom, boolean growFromMiddle,
                  Colour backgroundColourEnabled,
                  Colour backgroundColourDisabled,
                  Colour borderColourEnabled,
                  Colour borderColourDisabled) {
        super(left, top, right, bottom, growFromMiddle, backgroundColourEnabled, borderColourEnabled);
        this.backgroundColourEnabled = backgroundColourEnabled;
        this.backgroundColourDisabled = backgroundColourDisabled;
        this.borderColourEnabled = borderColourEnabled;
        this.borderColourDisabled = borderColourDisabled;
    }

    public Button(double left, double top, double right, double bottom,
                  Colour backgroundColourEnabled,
                  Colour backgroundColourDisabled,
                  Colour borderColourEnabled,
                  Colour borderColourDisabled) {
        super(left, top, right, bottom, backgroundColourEnabled, borderColourEnabled);
        this.backgroundColourEnabled = backgroundColourEnabled;
        this.backgroundColourDisabled = backgroundColourDisabled;
        this.borderColourEnabled = borderColourEnabled;
        this.borderColourDisabled = borderColourDisabled;
    }

    public Button(MusePoint2D ul, MusePoint2D br,
                  Colour backgroundColourEnabled,
                  Colour backgroundColourDisabled,
                  Colour borderColourEnabled,
                  Colour borderColourDisabled) {
        super(ul, br, backgroundColourEnabled, borderColourEnabled);
        this.backgroundColourEnabled = backgroundColourEnabled;
        this.backgroundColourDisabled = backgroundColourDisabled;
        this.borderColourEnabled = borderColourEnabled;
        this.borderColourDisabled = borderColourDisabled;
    }

    public Button(RelativeRect ref,
                  Colour backgroundColourEnabled,
                  Colour backgroundColourDisabled,
                  Colour borderColourEnabled,
                  Colour borderColourDisabled) {
        super(ref, backgroundColourEnabled, borderColourEnabled);
        this.backgroundColourEnabled = backgroundColourEnabled;
        this.backgroundColourDisabled = backgroundColourDisabled;
        this.borderColourEnabled = borderColourEnabled;

        this.borderColourDisabled = borderColourDisabled;
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTIme) {
        if (isVisible) {
            if (isEnabled()) {
                if (hitBox(mouseX, mouseY)) {
                    super.setBackgroundColour(new Colour(
                            (byte)(this.backgroundColourEnabled.r + highlight < 255 ? this.backgroundColourEnabled.r + highlight : 255),
                            (byte)(this.backgroundColourEnabled.g + highlight < 255 ? this.backgroundColourEnabled.g + highlight : 255),
                            (byte)(this.backgroundColourEnabled.b + highlight < 255 ? this.backgroundColourEnabled.b + highlight : 255),
                            1));
                } else {
                    super.setBackgroundColour(this.backgroundColourEnabled);
                }
                super.setBorderColour(this.borderColourEnabled);
            } else {
                super.setBackgroundColour(backgroundColourDisabled);
                super.setBorderColour(this.borderColourDisabled);
            }
            super.render(matrixStack, mouseX, mouseY, frameTIme);
        }
    }

    @Override
    public boolean hitBox(double x, double y) {
        if (isVisible() && isEnabled())
            return x >= left() && x <= right() && y >= top() && y <= bottom();
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
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