package com.lehjr.numina.client.gui.geometry;

public interface IRect {
    double left();
    IRect setLeft(double value);

    double top();

    IRect setTop(double value);

    double right();

    IRect setRight(double value);

    double bottom();

    IRect setBottom(double value);

    double width();

    IRect setWidth(double value);

    double height();

    IRect setHeight(double value);

    default void setPosition(MusePoint2D positionIn) {
        setUL(positionIn.minus(getWH().times(0.5)));
    }

    MusePoint2D getUL();

    IRect setUL(MusePoint2D ul);

    MusePoint2D getWH();

    IRect setWH(MusePoint2D wh);

    IRect setAbove(IRect belowMe);

    IRect setLeftOf(IRect rightOfMe);

    IRect setBelow(IRect aboveMe);

    IRect setRightOf(IRect leftOfMe);

    default boolean containsPoint(double x, double y) {
        return x > left() && x < right() && y > top() && y < bottom();
    }

    default MusePoint2D center() {
        return new MusePoint2D(centerX(), centerY());
    }

    default double centerX() {
        return (left() + right()) / 2.0;
    }

    default double centerY() {
        return (top() + bottom()) / 2.0;
    }

    default void moveBy(double x, double y) {
        this.moveBy(new MusePoint2D(x, y));
    }
    default void moveBy(MusePoint2D amount) {
        setUL(getUL().plus(amount));
    }
}