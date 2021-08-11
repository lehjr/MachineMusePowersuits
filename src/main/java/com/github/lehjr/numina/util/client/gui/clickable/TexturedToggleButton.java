package com.github.lehjr.numina.util.client.gui.clickable;

import com.github.lehjr.numina.util.client.gui.GuiIcon;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;

public class TexturedToggleButton extends AbstractTexturedButton {
    protected boolean isStateOn = false;
    protected double texDiffX = 0;
    protected double texDiffY = 0;

    public TexturedToggleButton(double left, double top, double right, double bottom, boolean growFromMiddle, Colour backgroundColourEnabled, Colour backgroundColourDisabled, Colour borderColourEnabled, Colour borderColourDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(left, top, right, bottom, growFromMiddle, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedToggleButton(double left, double top, double right, double bottom, Colour backgroundColourEnabled, Colour backgroundColourDisabled, Colour borderColourEnabled, Colour borderColourDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(left, top, right, bottom, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedToggleButton(MusePoint2D ul, MusePoint2D br, Colour backgroundColourEnabled, Colour backgroundColourDisabled, Colour borderColourEnabled, Colour borderColourDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(ul, br, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedToggleButton(RelativeRect ref, Colour backgroundColourEnabled, Colour backgroundColourDisabled, Colour borderColourEnabled, Colour borderColourDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(ref, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedToggleButton setState(boolean state) {
        this.isStateOn = state;
        return this;
    }

    public boolean getState() {
        return isStateOn;
    }

    public TexturedToggleButton setTexDiffX(double diff) {
        this.texDiffX = diff;
        return this;
    }

    public TexturedToggleButton setTexDiffY(double diff) {
        this.texDiffY = diff;
        return this;
    }

    @Override
    public void onPressed() {
        this.isStateOn =! this.isStateOn;
        super.onPressed();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if(this.isVisible) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            Colour color;
            if (this.isEnabled) {
                color = this.hitBox(mouseX, mouseY) ? Colour.LIGHT_BLUE.withAlpha(0.6F) : Colour.WHITE;
            } else {
                color = Colour.RED.withAlpha(0.6F);
            }
            GuiIcon.renderTextureWithColour(this.textureLocation, matrixStack, left(), right(), top(), bottom(), getZLevel(),
                    iconWidth, iconHeight, texStartX + (isStateOn ? texDiffX : 0), texStartY + (isStateOn ? texDiffY : 0), textureWidth, textureHeight, color);
        }
    }
}
