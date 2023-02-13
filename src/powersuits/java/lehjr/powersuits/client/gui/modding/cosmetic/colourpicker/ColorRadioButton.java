package lehjr.powersuits.client.gui.modding.cosmetic.colourpicker;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Colour;

public class ColorRadioButton extends RadioButton {
    public int index = 0;
    public ColorRadioButton(double leftPos, double topPos, Colour colour) {
        super(IconUtils.getIcon().armorColourPatch, 8, 8, leftPos, topPos);
    }

    public ColorRadioButton(MusePoint2D pos, Colour colour) {
        super(IconUtils.getIcon().armorColourPatch, new MusePoint2D(8,8), pos);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        super.render(matrixStack, mouseX, mouseY, frameTime);
    }
}