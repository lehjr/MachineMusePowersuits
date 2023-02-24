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
import lehjr.numina.client.gui.geometry.IDrawable;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.util.text.ITextComponent;


// fixme: revisit and rewrite
public class ClickableLabel extends Clickable {
    protected IPressable onPressed;
    protected IReleasable onReleased;
    Colour colour = Colour.WHITE;
    boolean shadowed = true;

    protected ITextComponent label;
    protected JustifyMode mode;

    public ClickableLabel(ITextComponent label, Rect rect) {
        super(rect);
        this.label = label;
        this.mode =JustifyMode.CENTERED;
    }

    public ClickableLabel(ITextComponent label, MusePoint2D ul) {
        this(label, ul, JustifyMode.CENTERED);
    }

    public ClickableLabel(ITextComponent label, MusePoint2D ul, JustifyMode mode) {
        super(ul, ul.plus(StringUtils.getStringWidth(label),
                Math.max(StringUtils.getStringHeight(), 10)));
        this.label = label;
        this.mode = mode;
    }

    public ClickableLabel(double left, double top, double width, ITextComponent label, JustifyMode mode) {
        super(left, top, left + width, top + Math.max(StringUtils.getStringHeight(), 10));
        this.label = label;
        this.mode = mode;
    }

    public ClickableLabel setMode(JustifyMode mode) {
        this.mode = mode;
        return this;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setShadowed(boolean shadowed) {
        this.shadowed = shadowed;
    }

    public void setLabel(ITextComponent label) {
        this.label = label;
    }

    // fixme: this isn't actually working as intended
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        matrixStack.pushPose();
        matrixStack.translate(0,0,100);
        if (shadowed) {
            switch (mode) {
                case LEFT:
                    StringUtils.drawLeftAlignedShadowedString(matrixStack, this.label, centerX(), centerY(), colour);
                    break;

                case CENTERED:
                    StringUtils.drawShadowedStringCentered(matrixStack, this.label, centerX(), centerY(), colour);
                    break;

                case RIGHT:
                    StringUtils.drawRightAlignedShadowedString(matrixStack, this.label, centerX(), centerY(), colour);
                    break;
            }
        } else {
            switch (mode) {
                case LEFT:
                    StringUtils.drawLeftAlignedText(matrixStack, this.label, centerX(), centerY(), colour);
                    break;

                case CENTERED:
                    StringUtils.drawCenteredText(matrixStack, this.label, centerX(), centerY(), colour);
                    break;

                case RIGHT:
                    StringUtils.drawRightAlignedText(matrixStack, this.label, centerX(), centerY(), colour);
                    break;
            }
        }
        matrixStack.popPose();
    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        return null;
    }

    @Override
    public boolean containsPoint(double x, double y) {
        if (label == null || label.getContents().isEmpty()) {
            return false;
        }
        MusePoint2D radius = new MusePoint2D((double) (StringUtils.getStringWidth(label) / 2F + 2F), StringUtils.getStringHeight());
        return Math.abs(centerX() - x) < radius.x() && Math.abs(centerY() - y) < radius.y();
    }

    public enum JustifyMode {
        LEFT,
        CENTERED,
        RIGHT;

        JustifyMode() {
        }
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
}
