package lehjr.powersuits.client.gui.modding.cosmetic.colorpicker;

import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Color;
import net.minecraft.client.gui.GuiGraphics;

public class MinusButton extends RadioButton {
    public MinusButton(double leftPos, double topPos) {
        super(IconUtils.INSTANCE.getIcon().minusSign, 8, 4, leftPos, topPos);
    }

    public MinusButton(MusePoint2D ul) {
        super(IconUtils.INSTANCE.getIcon().minusSign, new MusePoint2D(8, 4), ul);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        getIcon().draw(gfx.pose(), this.left(), this.top() - 4, Color.RED); // offset
    }
}