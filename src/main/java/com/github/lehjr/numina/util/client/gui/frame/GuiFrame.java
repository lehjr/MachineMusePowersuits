package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.math.Colour;

public abstract class GuiFrame extends DrawableRelativeRect implements IGuiFrame {
    float zLevel = 0;
    boolean isEnabled=true;
    boolean isVisible=true;

    public GuiFrame(double left, double top, double right, double bottom, boolean growFromMiddle, Colour backgroundColour, Colour borderColour) {
        super(left, top, right, bottom, growFromMiddle, backgroundColour, borderColour);
    }

    public GuiFrame(RelativeRect ref, Colour backgroundColour, Colour borderColour) {
        super(ref, backgroundColour, borderColour);
    }

    public GuiFrame(double left, double top, double right, double bottom, Colour backgroundColour, Colour borderColour) {
        super(left, top, right, bottom, backgroundColour, borderColour);
    }

    public GuiFrame(MusePoint2D ul, MusePoint2D br, Colour backgroundColour, Colour borderColour) {
        super(ul, br, backgroundColour, borderColour);
    }

    public GuiFrame(Colour backgroundColour, Colour borderColour) {
        super(backgroundColour, borderColour);
    }

    public GuiFrame(Colour backgroundColour, Colour borderColour, boolean growFromMiddle) {
        super(backgroundColour, borderColour, growFromMiddle);
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
