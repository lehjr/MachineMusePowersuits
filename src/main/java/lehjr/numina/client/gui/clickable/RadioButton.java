package lehjr.numina.client.gui.clickable;

import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Color;
import net.minecraft.client.gui.GuiGraphics;

public class RadioButton extends AbstractIconButton {
    public boolean isSelected = false;
    Color color = Color.WHITE;

    public RadioButton(GuiIcon.DrawableGuiIcon icon, double width, double height, double leftPos, double topPos) {
        super(icon, width, height, leftPos, topPos);
    }

    public RadioButton(GuiIcon.DrawableGuiIcon icon, MusePoint2D wh, MusePoint2D ul) {
        super(icon, wh, ul);
    }


    public RadioButton setColor(Color color) {
        this.color = color;
        return this;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (isEnabled() && isVisible()) {
            getIcon().draw(gfx.pose(), this.left(), this.top(), color);
            if (isSelected) {
                IconUtils.getIcon().armordisplayselect.draw(gfx.pose(), this.left(), this.top(), Color.WHITE);
            }
        }
    }
}