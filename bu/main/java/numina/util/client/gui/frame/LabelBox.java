package lehjr.numina.util.client.gui.frame;

import com.mojang.blaze3d.matrix.PoseStack;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Color;
import net.minecraft.util.text.TranslatableComponent;

/**
 * Make this 12.5x frame width (157?) and add label render method
 */
public class LabelBox extends GUISpacer {
    Color colour = new Color(4210752);
    TranslatableComponent TranslatableComponent;
    public LabelBox(double width, double height, TranslatableComponent TranslatableComponent) {
        super(width, height);
        this.TranslatableComponent = TranslatableComponent;
    }

    public void setColor(Color colour) {
        this.colour = colour;
    }

    public void renderLabel(PoseStack matrixStack, float offsetX, float offsetY) {
        MuseRenderer.drawCenteredText(matrixStack, TranslatableComponent, centerx() + offsetX, centery() +  offsetY, colour);
    }

    public void setLabel(TranslatableComponent TranslatableComponent) {
        this.TranslatableComponent = TranslatableComponent;
    }
}