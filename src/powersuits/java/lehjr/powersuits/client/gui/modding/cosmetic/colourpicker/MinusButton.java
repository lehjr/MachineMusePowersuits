package lehjr.powersuits.client.gui.modding.cosmetic.colourpicker;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Colour;

public class MinusButton extends RadioButton {
    public MinusButton(double leftPos, double topPos) {
        super(IconUtils.getIcon().minusSign, 8, 4, leftPos, topPos);
    }

    public MinusButton(MusePoint2D ul) {
        super(IconUtils.getIcon().minusSign, new MusePoint2D(8, 4), ul);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        getIcon().draw(matrixStack, this.left(), this.top() - 4, Colour.RED); // offset
    }
}