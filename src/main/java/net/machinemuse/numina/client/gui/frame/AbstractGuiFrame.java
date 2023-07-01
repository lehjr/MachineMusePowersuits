package net.machinemuse.numina.client.gui.frame;

import net.machinemuse.numina.client.gui.geometry.IDrawableRect;
import net.machinemuse.numina.client.gui.geometry.IRect;

import javax.annotation.Nonnull;
import java.util.List;

public class AbstractGuiFrame<T extends IRect> implements IGuiFrame {
    protected boolean isVisible = true;
    protected boolean isEnabled = true;
    protected float zLevel = 0;
    T backgrountRect;

    public AbstractGuiFrame(@Nonnull T backgrountRect) {
        this.backgrountRect = backgrountRect;
    }

    public void renderBackgroundRect(double mouseX, double mouseY, float partialTicks) {
        if (backgrountRect instanceof IDrawableRect) {
            ((IDrawableRect) backgrountRect).render(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void enable() {
        this.isEnabled = true;
    }

    @Override
    public void disable() {
        this.isEnabled = false;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void show() {
        this.isVisible = true;
    }

    @Override
    public void hide() {
        this.isVisible = false;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        return null;
    }

    @Override
    public IRect getRect() {
        return this.backgrountRect;
    }

    @Override
    public void setRect(@Nonnull IRect rect) {
        this.backgrountRect = (T) rect;
    }
}
