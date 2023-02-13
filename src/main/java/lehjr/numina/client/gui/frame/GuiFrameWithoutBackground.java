package lehjr.numina.client.gui.frame;

import lehjr.numina.client.gui.gemoetry.IDrawable;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;

public abstract class GuiFrameWithoutBackground extends Rect implements IGuiFrame {
    public GuiFrameWithoutBackground() {
        super(MusePoint2D.ZERO, MusePoint2D.ZERO);
    }

    float zLevel = 0;
    boolean isEnabled=true;
    boolean isVisible=true;

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
