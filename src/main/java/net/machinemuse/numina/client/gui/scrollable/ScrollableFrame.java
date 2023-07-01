package net.machinemuse.numina.client.gui.scrollable;

import net.machinemuse.numina.client.gui.frame.AbstractGuiFrame;
import net.machinemuse.numina.client.gui.frame.IScrollable;
import net.machinemuse.numina.client.gui.geometry.IRect;
import net.machinemuse.numina.common.math.MuseMathUtils;

import javax.annotation.Nonnull;

public class ScrollableFrame extends AbstractGuiFrame implements IScrollable {
    protected int buttonSize = 5;
    double scrollAmount = 8;
    protected int totalSize = 0;
    protected double currentScrollPixels = 0;
    public ScrollableFrame(@Nonnull IRect rect) {
        super(rect);
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTick) {
        if (isVisible()) {
            renderBackgroundRect(mouseX, mouseY, partialTick);
            super.render(mouseX, mouseY, partialTick);
            preRender(mouseX, mouseY, partialTick);
            postRender(mouseX, mouseY, partialTick);
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
        this.currentScrollPixels = MuseMathUtils.clampDouble(scrollPixels, 0, getMaxScrollPixels());
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
        this.scrollAmount = (int) MuseMathUtils.clampDouble(scrollAmount, 0, getMaxScrollPixels());
    }
}
