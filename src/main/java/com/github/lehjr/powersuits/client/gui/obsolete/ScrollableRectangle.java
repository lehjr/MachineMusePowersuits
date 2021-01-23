package com.github.lehjr.powersuits.client.gui.obsolete;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRelativeRect;
import com.mojang.blaze3d.matrix.MatrixStack;

@Deprecated
public class ScrollableRectangle extends MuseRelativeRect {
    public ScrollableRectangle(MuseRelativeRect relativeRect) {
        super(relativeRect.left(), relativeRect.top(), relativeRect.right(), relativeRect.bottom());
    }

    public ScrollableRectangle(double left, double top, double right, double bottom) {
        super(left, top, right, bottom);
    }

    public ScrollableRectangle(double left, double top, double right, float bottom, boolean growFromMiddle) {
        super(left, top, right, bottom, growFromMiddle);
    }

    public ScrollableRectangle(MusePoint2D ul, MusePoint2D br) {
        super(ul, br);
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, float zLevel) {
    }

    public MuseRect getBorder() {
        return this;
    }
}
