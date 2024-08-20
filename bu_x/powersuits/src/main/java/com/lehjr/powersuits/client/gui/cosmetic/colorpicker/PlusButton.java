package com.lehjr.powersuits.client.gui.cosmetic.colorpicker;

import com.lehjr.numina.client.gui.clickable.RadioButton;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.gui.GuiGraphics;

public class PlusButton extends RadioButton {
    public PlusButton(double leftPos, double topPos) {
        super(IconUtils.INSTANCE.getIcon().plusSign, 8, 8, leftPos, topPos);
    }

    public PlusButton(MusePoint2D ul) {
        super(IconUtils.INSTANCE.getIcon().plusSign, new MusePoint2D(8,8), ul);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        getIcon().draw(gfx.pose(), this.left(), this.top(), Color.GREEN);
    }
}