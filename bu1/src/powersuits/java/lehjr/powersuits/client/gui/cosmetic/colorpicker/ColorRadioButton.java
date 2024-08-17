package lehjr.powersuits.client.gui.cosmetic.colorpicker;

import com.lehjr.numina.client.gui.clickable.RadioButton;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.gui.GuiGraphics;

public class ColorRadioButton extends RadioButton {
    public int index = 0;
    public ColorRadioButton(double leftPos, double topPos, Color color) {
        super(IconUtils.getIcon().colorclicker, 8, 8, leftPos, topPos);
    }

    public ColorRadioButton(MusePoint2D pos, Color color) {
        super(IconUtils.getIcon().colorclicker, new MusePoint2D(8,8), pos);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);
    }
}