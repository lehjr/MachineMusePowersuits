package lehjr.powersuits.client.gui.modding.cosmetic.colourpicker;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.clickable.RadioButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.math.Color;

public class PlusButton extends RadioButton {
    public PlusButton(double leftPos, double topPos) {
        super(IconUtils.getIcon().plusSign, 8, 8, leftPos, topPos);
    }

    public PlusButton(MusePoint2D ul) {
        super(IconUtils.getIcon().plusSign, new MusePoint2D(8,8), ul);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        getIcon().draw(matrixStack, this.left(), this.top(), Color.GREEN);
    }
}