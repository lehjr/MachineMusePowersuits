package lehjr.numina.client.gui.clickable;

import lehjr.numina.client.gui.geometry.*;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Defines a generic clickable itemStack for a MuseGui.
 *
 * @author MachineMuse
 */
public abstract class Clickable<T extends IRect> implements IClickable, IRectWrapper {
    /** run this extra code when pressed */
    IPressable onPressed;
    /** run this extra code when released */
    IReleasable onReleased;
    boolean isEnabled = true;
    boolean isVisible = true;
    float blitOffset = 0;

    T rect;

    public Clickable(T rect) {
        this.rect = rect;
    }

    public Clickable(double left, double top, double right, double bottom) {
        this.rect = (T) new Rect(left, top, right, bottom);
    }

    public Clickable(double left, double top, double right, double bottom, boolean growFromMiddle) {
        this.rect = (T) new Rect(left, top, right, bottom, growFromMiddle);
    }

    public Clickable(MusePoint2D ul, MusePoint2D br) {
        this.rect = (T) new Rect(ul, br);
    }

    public Clickable(MusePoint2D ul, MusePoint2D br, boolean growFromMiddle) {
        this.rect = (T) new Rect(ul, br, growFromMiddle);
    }

    @Override
    public T getRect() {
        return this.rect;
    }

    @Override
    public void setRect(IRect rect) {
        this.rect = (T) rect;
    }

    @Override
    public float getZLevel() {
        return blitOffset;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        this.blitOffset = zLevel;
        return this;
    }

//    @Override
//    public void moveBy(double x, double y) {
//        IClickable.super.moveBy(x, y);
//    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (rect instanceof IDrawableRect) {
            ((IDrawableRect) rect).render(gfx, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
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
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void hide() {
        this.isVisible = false;
    }

    @Override
    public void show() {
        this.isVisible = true;
    }

    @Override
    public void enableAndShow() {
        this.enable();
        this.show();
    }

    @Override
    public void disableAndHide() {
        this.disable();
        this.hide();
    }

    @Override
    public void setOnPressed(IPressable onPressed) {
        this.onPressed = onPressed;
    }

    @Override
    public void setOnReleased(IReleasable onReleased) {
        this.onReleased = onReleased;
    }

    @Override
    public void onPressed() {
        if (this.isVisible() && this.isEnabled() && this.onPressed != null) {
            this.onPressed.onPressed(this);
        }
    }

    @Override
    public void onReleased() {
        if (this.isVisible() && this.isEnabled() && this.onReleased != null) {
            this.onReleased.onReleased(this);
        }
    }
}