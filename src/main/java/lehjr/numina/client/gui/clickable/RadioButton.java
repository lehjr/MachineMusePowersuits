package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Color;

public class RadioButton extends AbstractIconButton {
    public boolean isSelected = false;
    Color colour = Color.WHITE;

    public RadioButton(GuiIcon.DrawableGuiIcon icon, double width, double height, double leftPos, double topPos) {
        super(icon, width, height, leftPos, topPos);
    }

    public RadioButton(GuiIcon.DrawableGuiIcon icon, MusePoint2D wh, MusePoint2D ul) {
        super(icon, wh, ul);
    }


    public RadioButton setColour(Color colour) {
        this.colour = colour;
        return this;
    }

    public Color getColour() {
        return colour;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        if (isEnabled() && isVisible()) {
            getIcon().draw(matrixStack, this.left(), this.top(), colour);
            if (isSelected) {
                IconUtils.getIcon().selectedArmorOverlay.draw(matrixStack, this.left(), this.top(), Color.WHITE);
            }
        }
    }
}