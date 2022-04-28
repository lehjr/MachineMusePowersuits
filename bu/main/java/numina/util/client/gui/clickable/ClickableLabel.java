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

package lehjr.numina.util.client.gui.clickable;

import com.mojang.blaze3d.matrix.PoseStack;
import lehjr.numina.util.client.gui.gemoetry.IDrawable;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Color;


// fixme: revisit and rewrite
public class ClickableLabel extends Clickable {
    protected IPressable onPressed;
    protected IReleasable onReleased;
    Color colour = Color.WHITE;
    boolean shadowed = true;

    protected String label;
    protected JustifyMode mode;

    public ClickableLabel(String label, MusePoint2D position) {
        this.label = label;
        this.mode = JustifyMode.CENTERED;
        super.setWidth(MuseRenderer.getStringWidth(label));
        super.setHeight(MuseRenderer.getStringHeight());
        super.setPosition(position);
    }

    public ClickableLabel(String label, MusePoint2D position, JustifyMode mode) {
        this.label = label;
        super.setWidth(MuseRenderer.getStringWidth(label));
        super.setHeight(MuseRenderer.getStringHeight());
        super.setPosition(position);
        this.mode = mode;
    }

    public ClickableLabel setMode(JustifyMode mode) {
        this.mode = mode;
        return this;
    }

    public void setColor(Color colour) {
        this.colour = colour;
    }

    public void setShadowed(boolean shadowed) {
        this.shadowed = shadowed;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // fixme: this isn't actually working as intended
    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        matrixStack.pushPose();
        matrixStack.translate(0,0,100);
        if (shadowed) {
            switch (mode) {
                case LEFT:
                    MuseRenderer.drawLeftAlignedShadowedString(matrixStack, this.label, centerx(), centery(), colour);
                    break;

                case CENTERED:
                    MuseRenderer.drawShadowedStringCentered(matrixStack, this.label, centerx(), centery(), colour);
                    break;

                case RIGHT:
                    MuseRenderer.drawRightAlignedShadowedString(matrixStack, this.label, centerx(), centery(), colour);
                    break;
            }
        } else {
            switch (mode) {
                case LEFT:
                    MuseRenderer.drawLeftAlignedText(matrixStack, this.label, centerx(), centery(), colour);
                    break;

                case CENTERED:
                    MuseRenderer.drawCenteredText(matrixStack, this.label, centerx(), centery(), colour);
                    break;

                case RIGHT:
                    MuseRenderer.drawRightAlignedText(matrixStack, this.label, centerx(), centery(), colour);
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
    public boolean hitBox(double x, double y) {
        if (label == null || label.isEmpty()) {
            return false;
        }
        MusePoint2D radius = new MusePoint2D((double) (MuseRenderer.getStringWidth(label) / 2F + 2F), MuseRenderer.getStringHeight());
        return Math.abs(centerx() - x) < radius.getX() && Math.abs(centery() - y) < radius.getY();
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
