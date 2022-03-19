package lehjr.numina.util.client.gui.gemoetry;

import net.minecraft.client.gui.widget.Widget;

import javax.annotation.Nonnull;

/**
 * A wrapper for vanilla widgets used for positioning
 * @param <T>
 */
public class WidgetWrapper<T extends Widget> extends RelativeRect {
    T widget;

    public WidgetWrapper(@Nonnull T widget) {
        this.widget = widget;
        super.setWidth(widget.getWidth());
        super.setHeight(widget.getHeight());
    }

    public T getWidget() {
        return widget;
    }

    void setWidgetPosition() {
        widget.x = (int) finalLeft();
        widget.y = (int) finalTop();
    }

    void setWidgetWidthHeight() {
        widget.setWidth((int) finalWidth());
        widget.setHeight((int) finalHeight());
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        super.setUL(ul);
        setWidgetPosition();
        return this;
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        super.setWH(wh);
        setWidgetWidthHeight();
        return this;
    }

    @Override
    public IRect setLeft(double value) {
        return super.setLeft(value);
    }

    @Override
    public IRect setRight(double value) {
        return super.setRight(value);
    }

    @Override
    public IRect setTop(double value) {
        return super.setTop(value);
    }

    @Override
    public IRect setBottom(double value) {
        return super.setBottom(value);
    }

    @Override
    public IRect setWidth(double value) {
        return super.setWidth(value);
    }

    @Override
    public IRect setHeight(double value) {
        return super.setHeight(value);
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        super.move(moveAmount);
        setWidgetPosition();
    }

    @Override
    public void move(double x, double y) {
        super.move(x, y);
        setWidgetPosition();
    }

    @Override
    public void setPosition(MusePoint2D positionIn) {
        super.setPosition(positionIn);
        setWidgetPosition();
    }

    @Override
    public boolean growFromMiddle() {
        return super.growFromMiddle();
    }

    @Override
    public IRect setMeLeftOf(IRect otherRightOfMe) {
        super.setMeLeftOf(otherRightOfMe);
        setWidgetPosition();
        return this;
    }

    @Override
    public IRect setMeRightOf(IRect otherLeftOfMe) {
        super.setMeRightOf(otherLeftOfMe);
        setWidgetPosition();
        return this;
    }

    @Override
    public IRect setMeAbove(IRect otherBelowMe) {
        super.setMeAbove(otherBelowMe);
        setWidgetPosition();
        return this;
    }

    @Override
    public IRect setMeBelow(IRect otherAboveMe) {
        super.setMeBelow(otherAboveMe);
        setWidgetPosition();
        return this;
    }

    @Override
    public RelativeRect getRect() {
        return super.getRect();
    }
}
