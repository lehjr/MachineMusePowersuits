package com.github.lehjr.numina.util.client.gui.gemoetry;

/**
 * @author lehjr
 */
public interface IRect {
    /**
     *  Alternative to spawning a completely new object. Especially handy for GUI's with large constructors
     */
    void setTargetDimensions(double left, double top, double right, double bottom);

    void setTargetDimensions(MusePoint2D ul, MusePoint2D wh);

    default MusePoint2D center() {
        return new MusePoint2D(centerx(), centery());
    }

    MusePoint2D getUL();

    MusePoint2D getULFinal();

    MusePoint2D getWH();

    MusePoint2D getWHFinal();

    double left();

    double finalLeft();

    double top();

    double finalTop();

    double right();

    double finalRight();

    double bottom();

    double finalBottom();

    double width();

    double finalWidth();

    double height();

    double finalHeight();

    IRect setLeft(double value);

    IRect setRight(double value);

    IRect setTop(double value);

    IRect setBottom(double value);

    IRect setWidth(double value);

    IRect setHeight(double value);

    void move(MusePoint2D moveAmount);

    void move(double x, double y);

    void setPosition(MusePoint2D position);

    boolean growFromMiddle();

    default boolean containsPoint(double x, double y) {
        return x > left() && x < right() && y > top() && y < bottom();
    }

    default double centerx() {
        return (left() + right()) / 2.0F;
    }

    default double centery() {
        return (top() + bottom()) / 2.0F;
    }
}