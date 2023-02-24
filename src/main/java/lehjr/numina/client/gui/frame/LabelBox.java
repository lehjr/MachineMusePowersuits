package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Make this 12.5x frame width (157?) and add label render method
 */
public class LabelBox extends Rect {
    Colour colour = new Colour(4210752);
    TranslationTextComponent translationTextComponent;
    public LabelBox(double width, double height, TranslationTextComponent translationTextComponent) {
        super(MusePoint2D.ZERO, new MusePoint2D(width, height));
        this.translationTextComponent = translationTextComponent;
    }

    public void setColor(Colour colour) {
        this.colour = colour;
    }

    public void renderLabel(MatrixStack matrixStack, float offsetX, float offsetY) {
        StringUtils.drawCenteredText(matrixStack, translationTextComponent, centerX() + offsetX, centerY() +  offsetY, colour);
    }

    public void setLabel(TranslationTextComponent translationTextComponent) {
        this.translationTextComponent = translationTextComponent;
    }
}