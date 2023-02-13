package lehjr.numina.client.gui.frame;

import lehjr.numina.client.gui.gemoetry.DrawableRect;
import lehjr.numina.client.gui.gemoetry.IDrawable;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.common.math.Colour;

public abstract class GuiFrame extends DrawableRect implements IGuiFrame {
    float zLevel = 0;
    boolean isEnabled=true;
    boolean isVisible=true;

    public GuiFrame(double left, double top, double right, double bottom, boolean growFromMiddle, Colour backgroundColour, Colour borderColour) {
        super(left, top, right, bottom, growFromMiddle, backgroundColour, borderColour);
    }

    public GuiFrame(Rect ref, Colour backgroundColour, Colour borderColour) {
        super(ref, backgroundColour, borderColour);
    }

    public GuiFrame(double left, double top, double right, double bottom, Colour backgroundColour, Colour borderColour) {
        super(left, top, right, bottom, backgroundColour, borderColour);
    }

    public GuiFrame(MusePoint2D ul, MusePoint2D br, Colour backgroundColour, Colour borderColour) {
        super(ul, br, backgroundColour, borderColour);
    }

    @Override
    public float getZLevel() {
        return zLevel;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        this.zLevel = zLevel;
        return this;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }
}
