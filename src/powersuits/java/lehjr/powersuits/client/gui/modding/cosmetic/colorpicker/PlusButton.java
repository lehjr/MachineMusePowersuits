package lehjr.powersuits.client.gui.modding.cosmetic.colorpicker;

import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Color;
import net.minecraft.client.gui.GuiGraphics;

public class PlusButton extends RadioButton {
    public PlusButton(double leftPos, double topPos) {
        super(IconUtils.getIcon().plusSign, 8, 8, leftPos, topPos);
    }

    public PlusButton(MusePoint2D ul) {
        super(IconUtils.getIcon().plusSign, new MusePoint2D(8,8), ul);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        getIcon().draw(gfx.pose(), this.left(), this.top(), Color.GREEN);
    }
}