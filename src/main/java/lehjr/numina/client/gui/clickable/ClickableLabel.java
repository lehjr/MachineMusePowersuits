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

import lehjr.numina.client.gui.geometry.IDrawable;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.StringUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;


// fixme: revisit and rewrite
public class ClickableLabel extends Clickable {
    protected IPressable onPressed;
    protected IReleasable onReleased;
    Color color = Color.WHITE;
    boolean shadowed = true;

    protected Component label;
    protected JustifyMode mode;

    public ClickableLabel(Component label, Rect rect) {
        super(rect);
        this.label = label;
        this.mode =JustifyMode.CENTERED;
    }

    public ClickableLabel(Component label, MusePoint2D ul) {
        this(label, ul, JustifyMode.CENTERED);
    }

    public ClickableLabel(Component label, MusePoint2D ul, JustifyMode mode) {
        super(ul, ul.plus(StringUtils.getStringWidth(label),
                Math.max(StringUtils.getStringHeight(), 10)));
        this.label = label;
        this.mode = mode;
    }

    public ClickableLabel(double left, double top, double width, Component label, JustifyMode mode) {
        super(left, top, left + width, top + Math.max(StringUtils.getStringHeight(), 10));
        this.label = label;
        this.mode = mode;
    }

    public ClickableLabel setMode(JustifyMode mode) {
        this.mode = mode;
        return this;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setShadowed(boolean shadowed) {
        this.shadowed = shadowed;
    }

    public void setLabel(Component label) {
        this.label = label;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);
        gfx.pose().pushPose();
        gfx.pose().translate(0,0,100);
        if (shadowed) {
            switch (mode) {
                case LEFT:
                    StringUtils.drawLeftAlignedShadowedString(gfx, this.label, centerX(), centerY(), color);
                    break;

                case CENTERED:
                    StringUtils.drawShadowedStringCentered(gfx, this.label, centerX(), centerY(), color);
                    break;

                case RIGHT:
                    StringUtils.drawRightAlignedShadowedString(gfx, this.label, centerX(), centerY(), color);
                    break;
            }
        } else {
            switch (mode) {
                case LEFT:
                    StringUtils.drawLeftAlignedText(gfx, this.label, centerX(), centerY(), color);
                    break;

                case CENTERED:
                    StringUtils.drawCenteredText(gfx, this.label, centerX(), centerY(), color);
                    break;

                case RIGHT:
                    StringUtils.drawRightAlignedText(gfx, this.label, centerX(), centerY(), color);
                    break;
            }
        }
        gfx.pose().popPose();
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
        if (label == null || label.getString().isEmpty()) { // FIXME? check this may not work
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
