package lehjr.numina.client.gui.frame.fixed;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.frame.IGuiFrame;
import lehjr.numina.client.gui.gemoetry.*;

import javax.annotation.Nonnull;

public class AbstractGuiFrame<T extends Rect> implements IGuiFrame {
    protected boolean isVisible = true;
    protected boolean isEnabled = true;
    protected float zLevel = 0;
    T backgrountRect;

    public AbstractGuiFrame(@Nonnull T backgrountRect) {
        this.backgrountRect = backgrountRect;
    }

    public void renderBackgroundRect(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (backgrountRect instanceof IDrawableRect) {
            ((IDrawableRect) backgrountRect).render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public float getZLevel() {
        return backgrountRect instanceof IDrawableRect ? ((IDrawableRect) backgrountRect).getZLevel() : zLevel;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        if (backgrountRect instanceof IDrawableRect) {
            ((IDrawableRect) backgrountRect).setZLevel(zLevel);
        }
        this.zLevel = zLevel;
        return this;
    }

    @Override
    public double top() {
        return backgrountRect.top();
    }

    @Override
    public IRect setTop(double value) {
        backgrountRect.setTop(value);
        return this;
    }

    @Override
    public double left() {
        return backgrountRect.left();
    }

    @Override
    public IRect setLeft(double value) {
        backgrountRect.setLeft(value);
        return this;
    }

    @Override
    public double bottom() {
        return backgrountRect.bottom();
    }

    @Override
    public IRect setBottom(double value) {
        backgrountRect.setBottom(value);
        return this;
    }

    @Override
    public double right() {
        return backgrountRect.right();
    }

    @Override
    public IRect setRight(double value) {
        backgrountRect.setRight(value);
        return this;
    }

    @Override
    public double width() {
        return backgrountRect.width();
    }

    @Override
    public IRect setWidth(double value) {
        backgrountRect.setWidth(value);
        return this;
    }

    @Override
    public double height() {
        return backgrountRect.height();
    }

    @Override
    public IRect setHeight(double value) {
        backgrountRect.setHeight(value);
        return this;
    }

    @Override
    public MusePoint2D getUL() {
        return backgrountRect.getUL();
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        backgrountRect.setUL(ul);
        return this;
    }

    @Override
    public MusePoint2D getWH() {
        return backgrountRect.getWH();
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        backgrountRect.setWH(wh);
        return this;
    }

    @Override
    public IRect setAbove(IRect belowMe) {
        backgrountRect.setAbove(belowMe);
        return this;
    }

    @Override
    public IRect setLeftOf(IRect rightOfMe) {
        backgrountRect.setLeftOf(rightOfMe);
        return this;
    }

    @Override
    public IRect setBelow(IRect aboveMe) {
        backgrountRect.setBelow(aboveMe);
        return this;
    }

    @Override
    public IRect setRightOf(IRect leftOfMe) {
        backgrountRect.setRightOf(leftOfMe);
        return this;
    }


    public Rect getBackgroundRect() {
        return backgrountRect;
    }
}
