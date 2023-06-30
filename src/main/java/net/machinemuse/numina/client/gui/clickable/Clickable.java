package net.machinemuse.numina.client.gui.clickable;

import net.machinemuse.numina.client.gui.geometry.MusePoint2D;

import java.util.List;

/**
 * Defines a generic clickable item for a MuseGui.
 *
 * @author MachineMuse
 */
public abstract class Clickable implements IClickable {
    /** run this extra code when pressed */
    IPressable onPressed;
    /** run this extra code when released */
    IReleasable onReleased;

    protected MusePoint2D position;
    protected boolean enabled = true;
    protected boolean visible = true;

    public Clickable() {
        position = new MusePoint2D(0, 0);
    }

    public Clickable(MusePoint2D point) {
        position = point;
    }

    @Override
    public MusePoint2D getPosition() {
        return position;
    }

    public void setPosition(MusePoint2D position) {
        this.position = position;
    }

    @Override
    public void move(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        return null;
    }

    @Override
    public void show() {
        this.visible = true;
    }

    @Override
    public void hide() {
        this.visible = false;
    }

    @Override
    public void enable() {
        this.enabled = true;
    }

    @Override
    public void disable() {
        this.enabled = false;
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