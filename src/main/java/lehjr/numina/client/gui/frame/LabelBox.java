package lehjr.numina.client.gui.frame;

import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;


/**
 * Make this 12.5x frame width (157?) and add label render method
 */
public class LabelBox extends Rect {
    Color color = new Color(4210752);
    Component translationTextComponent;
    public LabelBox(double width, double height, Component translationTextComponent) {
        super(MusePoint2D.ZERO, new MusePoint2D(width, height));
        this.translationTextComponent = translationTextComponent;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void renderLabel(GuiGraphics gfx, float offsetX, float offsetY) {
        StringUtils.drawCenteredText(gfx, translationTextComponent, centerX() + offsetX, centerY() +  offsetY, color);
    }

    public void setLabel(Component translationTextComponent) {
        this.translationTextComponent = translationTextComponent;
    }
}