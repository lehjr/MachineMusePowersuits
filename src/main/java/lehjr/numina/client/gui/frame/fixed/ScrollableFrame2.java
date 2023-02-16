package lehjr.numina.client.gui.frame.fixed;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.frame.AbstractGuiFrame;
import lehjr.numina.client.gui.frame.IScrollable;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.common.math.MathUtils;

import javax.annotation.Nonnull;

public class ScrollableFrame2 extends AbstractGuiFrame implements IScrollable {
    protected int buttonSize = 5;
    double scrollAmount = 8;

    protected int totalSize;
    protected double currentScrollPixels;
    public ScrollableFrame2(@Nonnull Rect backgrountRect) {
        super(backgrountRect);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (isVisible()) {
            renderBackgroundRect(matrixStack, mouseX, mouseY, frameTime);
            super.render(matrixStack, mouseX, mouseY, frameTime);
            preRender(matrixStack, mouseX, mouseY, frameTime);
            postRender(mouseX, mouseY, frameTime);
        }
    }

    @Override
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int getButtonSize() {
        return buttonSize;
    }

    @Override
    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    @Override
    public int getTotalSize() {
        return totalSize;
    }

    @Override
    public double getCurrentScrollPixels() {
        return currentScrollPixels;
    }

    @Override
    public void setCurrentScrollPixels(double scrollPixels) {
        this.currentScrollPixels = scrollPixels;
    }

    @Override
    public double getMaxScrollPixels() {
        return (int) Math.max(totalSize - height(), 0);
    }

    @Override
    public double getScrollAmount() {
        return scrollAmount;
    }

    @Override
    public void setScrollAmount(double scrollAmount) {
        this.scrollAmount = (int) MathUtils.clampDouble(scrollAmount, 0, getMaxScrollPixels());
    }
}
