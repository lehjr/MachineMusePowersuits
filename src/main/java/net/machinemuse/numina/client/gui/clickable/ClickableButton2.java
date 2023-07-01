package net.machinemuse.numina.client.gui.clickable;

import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.render.MuseRenderer;
import net.machinemuse.numina.common.math.Colour;

import java.util.List;

public class ClickableButton2 extends DrawableMuseRect implements IClickable {
    /** run this extra code when pressed */
    IPressable onPressed;
    /** run this extra code when released */
    IReleasable onReleased;
    boolean enabled = true;
    boolean visible = true;

    public String label;

    Colour labelColor = Colour.WHITE;


    public ClickableButton2(double left, double top,double width, boolean enabled) {
        super(left, top, left + width, top + 20, new Colour(0.5F, 0.6F, 0.8F, 1), new Colour(0.3F, 0.3F, 0.3F, 1));
        this.enabled = enabled;
    }

    public ClickableButton2(double left, double top,double width, boolean enabled, Colour insideColour, Colour outsideColour) {
        super(left, top, left + width, top + 20, insideColour, outsideColour);
        this.enabled = enabled;
    }

    public ClickableButton2(double left, double top, double width) {
        super(left, top, left + width, top + 20, new Colour(0.5F, 0.6F, 0.8F, 1), new Colour(0.3F, 0.3F, 0.3F, 1));
    }

    public ClickableButton2(double left, double top, double width, Colour insideColour, Colour outsideColour) {
        super(left, top, left + width, top + 20, insideColour, outsideColour);
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        if (visible) {
            Colour topcolour;
            Colour bottomcolour;
            if (isEnabled()) {
                topcolour = new Colour(0.5F, 0.6F, 0.8F, 1);
                bottomcolour = new Colour(0.3F, 0.3F, 0.3F, 1);
            } else {
                topcolour = new Colour(0.8F, 0.3F, 0.3F, 1);
                bottomcolour = new Colour(0.8F, 0.6F, 0.6F, 1);
            }
            setOutsideColour(topcolour);
            setInsideColour(bottomcolour);
            super.render(mouseX, mouseY, partialTicks);
            MuseRenderer.drawCenteredString(this.label, centerX(), centerY() - 4);
        }
    }

    @Override
    public void move(double x, double y) {
    }

    @Override
    public MusePoint2D getPosition() {
        return center();
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
    public List getToolTip(double mouseX, double mouseY) {
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

    @Override
    public boolean containsPoint(double x, double y) {
        return super.containsPoint(x, y);
    }
}
