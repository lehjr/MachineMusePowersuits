package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

/**
 * Just a spacer for a GUI. Height and width are setup to be locked into permanent values
 */
public class GUISpacer extends RelativeRect implements IGuiFrame {
    public GUISpacer(double widthIn, double heightIn) {
        super(new MusePoint2D(0,0 ), new MusePoint2D(widthIn, heightIn));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public void setTargetDimensions(double left, double top, double right, double bottom) {
        super.setTargetDimensions(left, top, left + getWHFinal().getX(), top + getWHFinal().getY());
    }

    @Override
    public void setTargetDimensions(MusePoint2D ul, MusePoint2D wh) {
        super.setTargetDimensions(ul, getWHFinal());
    }
}
