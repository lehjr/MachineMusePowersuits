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

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import lehjr.numina.util.client.gui.gemoetry.IDrawable;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Colour;
import net.minecraft.util.text.ITextComponent;

/**
 * @author MachineMuse
 */
public class ClickableButton extends DrawableRelativeRect implements IClickable {
    boolean isVisible = true;
    boolean isEnabled = true;
    protected ITextComponent label;
    protected MusePoint2D radius;

    private Colour enabledBorder  = new Colour(0.3F, 0.3F, 0.3F, 1);
    private Colour enabledBackground = new Colour(0.5F, 0.6F, 0.8F, 1);
    private Colour disabledBorder = new Colour(0.8F, 0.6F, 0.6F, 1);
    private Colour disabledBackground = new Colour(0.8F, 0.3F, 0.3F, 1);
    private IPressable onPressed;
    private IReleasable onReleased;

    public ClickableButton(ITextComponent label, MusePoint2D position, boolean enabled) {
        super(0,0,0, 0, Colour.BLACK, Colour.BLACK);
        this.label = label;
        this.setPosition(position);

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

        setLeft(position.getX() - radius.getX());
        setTop(position.getY() - radius.getY());
        setWidth(radius.getX() * 2);
        setHeight(radius.getY() * 2);
        setBorderColour(enabledBorder);
        setBackgroundColour(enabledBackground);
        this.setEnabled(enabled);
    }

    public ClickableButton setEnabledBorder(Colour enabledBorder) {
        this.enabledBorder = enabledBorder;
        return this;
    }

    public ClickableButton setEnabledBackground(Colour enabledBackground) {
        this.enabledBackground = enabledBackground;
        return this;
    }

    public ClickableButton setDisabledBorder(Colour disabledBorder) {
        this.disabledBorder = disabledBorder;
        return this;
    }

    public ClickableButton setDisabledBackground(Colour disabledBackground) {
        this.disabledBackground = disabledBackground;
        return this;
    }

    void setBackgroundColour(Colour backgroundColour, boolean hovered) {
        super.setBackgroundColour(hovered? backgroundColour.copy().lighten(0.10F) : backgroundColour);
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     *
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderButton(matrixStack, mouseX, mouseY, partialTicks);
        renderText(matrixStack, mouseX, mouseY);
    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        return null;
    }

    /**
     * Container based GUI's should use the separate button and text renderer
     * Call this from the container GUI's main render loop
     *
     * @param mouseX
     * @param mouseY
     * @param frameTIme
     */
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float frameTIme) {
        if (isVisible) {

            setBackgroundColour(isEnabled() ? enabledBackground : disabledBackground, containsPoint(mouseX, mouseY));



            this.setBorderColour(isEnabled() ? enabledBorder : disabledBorder);


            super.render(matrixStack, mouseX, mouseY, frameTIme);
        }
    }


    /**
     * Container based GUI's should use the separate button and text renderer
     * Call this from the container GUI's drawGuiContainerForegroundLayer
     * @param mouseX
     * @param mouseY
     */
    public void renderText(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (isVisible()) {
            if (label.getString().contains("\n")) {
                String[] s = label.getString().split("\n");
                for (int i = 0; i < s.length; i++) {
                    MuseRenderer.drawShadowedStringCentered(matrixStack, s[i], getPosition().getX(), getPosition().getY() + (i * MuseRenderer.getStringHeight() + 2));
                }
            } else {
                MuseRenderer.drawShadowedStringCentered(matrixStack, this.label.getString(), getPosition().getX(), getPosition().getY());
            }
        }
    }

    public MusePoint2D getRadius () {
        return radius.copy();
    }

    @Override
    public boolean hitBox(double x, double y) {
        return containsPoint(x, y);
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

    public ClickableButton setLable(ITextComponent label) {
        this.label = label;
        return this;
    }

    public ITextComponent getLabel() {
        return label;
    }
}
