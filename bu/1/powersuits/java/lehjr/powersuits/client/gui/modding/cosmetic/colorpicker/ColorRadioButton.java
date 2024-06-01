package lehjr.powersuits.client.gui.modding.cosmetic.colorpicker;

import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Color;
import net.minecraft.client.gui.GuiGraphics;

public class ColorRadioButton extends RadioButton {
    public int index = 0;
    public ColorRadioButton(double leftPos, double topPos, Color color) {
        super(IconUtils.INSTANCE.getIcon().colorclicker, 8, 8, leftPos, topPos);
    }

    public ColorRadioButton(MusePoint2D pos, Color color) {
        super(IconUtils.INSTANCE.getIcon().colorclicker, new MusePoint2D(8,8), pos);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);
    }
}