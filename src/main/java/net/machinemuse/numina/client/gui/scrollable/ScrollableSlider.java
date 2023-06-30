package net.machinemuse.numina.client.gui.scrollable;

import net.machinemuse.numina.client.gui.clickable.ClickableSlider;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.geometry.MuseRect;

public class ScrollableSlider extends ScrollableRectangle {
    ClickableSlider slider;

    public ScrollableSlider(ClickableSlider slider, MuseRect rect) {
        super(rect);
        this.slider = slider;
    }

    public ScrollableSlider(ClickableSlider slider, double left, double top, double right, double bottom) {
        super(left, top, right, bottom);
        this.slider = slider;
    }

    public ScrollableSlider(ClickableSlider slider, double left, double top, double right, double bottom, boolean growFromMiddle) {
        super(left, top, right, bottom, growFromMiddle);
        this.slider = slider;
    }

    public ScrollableSlider(ClickableSlider slider, MusePoint2D ul, MusePoint2D br) {
        super(ul, br);
        this.slider = slider;
    }

    public double getValue() {
        return slider.getValue();
    }

    public void setValue(double value) {
        slider.setValue(value);
    }

    public ClickableSlider getSlider() {
        return slider;
    }

    public boolean hitBox(double x, double y) {
        return slider.containsPoint(x, y);
    }

    @Override
    public void render(double mouseX, double mouseY, float partialTicks) {
        slider.render(mouseX, mouseY, partialTicks);
    }
}