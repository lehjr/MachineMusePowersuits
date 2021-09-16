package com.github.lehjr.powersuits.client.gui.common;

import com.github.lehjr.numina.util.client.gui.frame.GUISpacer;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Make this 12.5x frame width (157?) and add label render method
 */
public class LabelBox extends GUISpacer {
    Colour colour = new Colour(4210752);
    TranslationTextComponent translationTextComponent;
    public LabelBox(double width, double height, TranslationTextComponent translationTextComponent) {
        super(width, height);
        this.translationTextComponent = translationTextComponent;
    }

    public void setColor(Colour colour) {
        this.colour = colour;
    }

    public void renderLabel(MatrixStack matrixStack, float offsetX, float offsetY) {
        MuseRenderer.drawCenteredText(matrixStack, translationTextComponent, centerx() + offsetX, centery() +  offsetY, colour);
    }

    public void setLabel(TranslationTextComponent translationTextComponent) {
        this.translationTextComponent = translationTextComponent;
    }
}