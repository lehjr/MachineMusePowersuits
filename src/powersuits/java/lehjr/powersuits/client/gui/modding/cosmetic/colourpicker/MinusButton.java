package lehjr.powersuits.client.gui.modding.cosmetic.colorpicker;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Color;

public class MinusButton extends RadioButton {
    public MinusButton(double leftPos, double topPos) {
        super(IconUtils.getIcon().minusSign, 8, 4, leftPos, topPos);
    }

    public MinusButton(MusePoint2D ul) {
        super(IconUtils.getIcon().minusSign, new MusePoint2D(8, 4), ul);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        getIcon().draw(matrixStack, this.left(), this.top() - 4, Color.RED); // offset
    }
}