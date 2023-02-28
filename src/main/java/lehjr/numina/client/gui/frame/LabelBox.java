package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * Make this 12.5x frame width (157?) and add label render method
 */
public class LabelBox extends Rect {
    Color colour = new Color(4210752);
    TranslatableComponent translationTextComponent;
    public LabelBox(double width, double height, TranslatableComponent translationTextComponent) {
        super(MusePoint2D.ZERO, new MusePoint2D(width, height));
        this.translationTextComponent = translationTextComponent;
    }

    public void setColor(Color colour) {
        this.colour = colour;
    }

    public void renderLabel(PoseStack matrixStack, float offsetX, float offsetY) {
        StringUtils.drawCenteredText(matrixStack, translationTextComponent, centerX() + offsetX, centerY() +  offsetY, colour);
    }

    public void setLabel(TranslatableComponent translationTextComponent) {
        this.translationTextComponent = translationTextComponent;
    }
}