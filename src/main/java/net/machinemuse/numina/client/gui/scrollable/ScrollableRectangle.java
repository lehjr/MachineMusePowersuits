package net.machinemuse.numina.client.gui.scrollable;

import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.numina.client.gui.geometry.MuseRect;

public class ScrollableRectangle extends MuseRect {
    public ScrollableRectangle(MuseRect relativeRect) {
        super(relativeRect.left(), relativeRect.top(), relativeRect.right(), relativeRect.bottom());
    }

    public ScrollableRectangle(double left, double top, double right, double bottom) {
        super(left, top, right, bottom);
    }

    public ScrollableRectangle(double left, double top, double right, double bottom, boolean growFromMiddle) {
        super(left, top, right, bottom, growFromMiddle);
    }

    public ScrollableRectangle(MusePoint2D ul, MusePoint2D br) {
        super(ul, br);
    }

    public void render(double mouseX, double mouseY, float partialTicks) {
    }

    public MuseRect getBorder() {
        return this;
    }
}
