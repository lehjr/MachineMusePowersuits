package com.lehjr.numina.client.gui.geometry;

public interface IRectWrapper<T extends IRect> extends IRect {

    T getRect();

    void setRect(T rect);

    default double left() {
        return getRect().left();
    }

    default IRect setLeft(double value) {
        return getRect().setLeft(value);
    }

    default double top() {
        return getRect().top();
    }

    default IRect setTop(double value) {
        return getRect().setTop(value);
    }

    default double right() {
        return getRect().right();
    }

    default IRect setRight(double value) {
        return getRect().setRight(value);
    }

    default double bottom() {
        return getRect().bottom();
    }

    default IRect setBottom(double value) {
        return getRect().setBottom(value);
    }

    default double width() {
        return getRect().width();
    }

    default IRect setWidth(double value) {
        return getRect().setWidth(value);
    }

    default double height() {
        return getRect().height();
    }

    default IRect setHeight(double value) {
        return getRect().setHeight(value);
    }

    default MusePoint2D getUL() {
        return getRect().getUL();
    }

    default IRect setUL(MusePoint2D ul) {
        return getRect().setUL(ul);
    }

    default MusePoint2D getWH() {
        return getRect().getWH();
    }

    default IRect setWH(MusePoint2D wh) {
        return getRect().setWH(wh);
    }

    default IRect setAbove(IRect belowMe) {
        return getRect().setAbove(belowMe);
    }

    default IRect setLeftOf(IRect rightOfMe) {
        return getRect().setLeftOf(rightOfMe);
    }

    default IRect setBelow(IRect aboveMe) {
        return getRect().setBelow(aboveMe);
    }

    default IRect setRightOf(IRect leftOfMe) {
        return getRect().setRightOf(leftOfMe);
    }
}
