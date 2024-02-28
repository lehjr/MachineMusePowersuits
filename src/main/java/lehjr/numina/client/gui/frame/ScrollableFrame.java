package lehjr.numina.client.gui.frame;

import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class ScrollableFrame extends AbstractGuiFrame implements IScrollable {
    protected int buttonSize = 5;
    double scrollAmount = 8;

    protected int totalSize = 0;
    protected double currentScrollPixels = 0;
    public ScrollableFrame(@Nonnull Rect backgrountRect) {
        super(backgrountRect);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (isVisible()) {
            renderBackgroundRect(gfx, mouseX, mouseY, partialTick);
            super.render(gfx, mouseX, mouseY, partialTick);
            preRender(gfx, mouseX, mouseY, partialTick);
            postRender(gfx, mouseX, mouseY, partialTick);
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
        this.currentScrollPixels = (int) MathUtils.clampDouble(scrollPixels, 0, getMaxScrollPixels());
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
