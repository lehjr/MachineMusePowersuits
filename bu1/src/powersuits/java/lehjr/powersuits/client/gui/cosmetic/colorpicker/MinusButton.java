package lehjr.powersuits.client.gui.cosmetic.colorpicker;

import com.lehjr.numina.client.gui.clickable.RadioButton;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.gui.GuiGraphics;

public class MinusButton extends RadioButton {
    public MinusButton(double leftPos, double topPos) {
        super(IconUtils.getIcon().minusSign, 8, 4, leftPos, topPos);
    }

    public MinusButton(MusePoint2D ul) {
        super(IconUtils.getIcon().minusSign, new MusePoint2D(8, 4), ul);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        getIcon().draw(gfx.pose(), this.left(), this.top() - 4, Color.RED); // offset
    }
}