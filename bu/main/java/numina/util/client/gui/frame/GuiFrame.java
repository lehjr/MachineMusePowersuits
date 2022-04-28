package lehjr.numina.util.client.gui.frame;

import lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import lehjr.numina.util.client.gui.gemoetry.IDrawable;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import lehjr.numina.util.math.Color;

public abstract class GuiFrame extends DrawableRelativeRect implements IGuiFrame {
    float zLevel = 0;
    boolean isEnabled=true;
    boolean isVisible=true;

    public GuiFrame(double left, double top, double right, double bottom, boolean growFromMiddle, Color backgroundColor, Color borderColor) {
        super(left, top, right, bottom, growFromMiddle, backgroundColor, borderColor);
    }

    public GuiFrame(RelativeRect ref, Color backgroundColor, Color borderColor) {
        super(ref, backgroundColor, borderColor);
    }

    public GuiFrame(double left, double top, double right, double bottom, Color backgroundColor, Color borderColor) {
        super(left, top, right, bottom, backgroundColor, borderColor);
    }

    public GuiFrame(MusePoint2D ul, MusePoint2D br, Color backgroundColor, Color borderColor) {
        super(ul, br, backgroundColor, borderColor);
    }

    public GuiFrame(Color backgroundColor, Color borderColor) {
        super(backgroundColor, borderColor);
    }

    public GuiFrame(Color backgroundColor, Color borderColor, boolean growFromMiddle) {
        super(backgroundColor, borderColor, growFromMiddle);
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
