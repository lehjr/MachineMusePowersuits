package com.lehjr.numina.client.gui.clickable;

import com.lehjr.numina.client.gui.NuminaIcons;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.gui.GuiGraphics;

public class RadioButton extends AbstractIconButton {
    public boolean isSelected = false;
    Color color = Color.WHITE;

    public RadioButton(NuminaIcons.DrawableIcon icon, double width, double height, double leftPos, double topPos) {
        super(icon, width, height, leftPos, topPos);
    }

    public RadioButton(NuminaIcons.DrawableIcon icon, MusePoint2D wh, MusePoint2D ul) {
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