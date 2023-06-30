package net.machinemuse.numina.client.gui.clickable;

import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.render.MuseRenderer;

import java.util.List;

public class ClickableLabel implements IClickable {
    /** run this extra code when pressed */
    IPressable onPressed;
    /** run this extra code when released */
    IReleasable onReleased;

    protected boolean enabled = true;
    protected boolean visible = true;

    protected String label;
    protected MusePoint2D position;
    protected int mode;

    public ClickableLabel(String label, MusePoint2D position) {
        this.label = label;
        this.position = position;
        this.mode = 1;
    }

    public ClickableLabel(String label, MusePoint2D position, int mode) {
        this.label = label;
        this.position = position;
        this.mode = mode;
    }

    public ClickableLabel setMode(int mode) {
        this.mode = mode;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // fixme: don't think this is actually working as intended
    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        if (mode == 0)
            MuseRenderer.drawLeftAlignedStringString(this.label, position.getX(), position.getY() - 4);
        if (mode == 1)
            MuseRenderer.drawCenteredString(this.label, position.getX(), position.getY() - 4);
        if (mode == 2)
            MuseRenderer.drawRightAlignedString(this.label, position.getX(), position.getY() - 4);
    }

    @Override
    public boolean containsPoint(double mouseX, double mouseY) {
        if (label == null || label.isEmpty())
            return false;

        MusePoint2D radius = new MusePoint2D(MuseRenderer.getStringWidth(label) / 2 + 2, 6);
        boolean hitx = Math.abs(position.getX() - mouseX) < radius.getX();
        boolean hity = Math.abs(position.getY() - mouseY) < radius.getY();
        return hitx && hity;
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        return null;
    }

    @Override
    public void move(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    @Override
    public MusePoint2D getPosition() {
        return position;
    }

    @Override
    public void enable() {
        this.enabled = false;
    }

    @Override
    public void disable() {
        this.enabled = false;
    }

    @Override
    public void hide() {
        this.visible = false;
    }

    @Override
    public void show() {
        this.visible = true;
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
