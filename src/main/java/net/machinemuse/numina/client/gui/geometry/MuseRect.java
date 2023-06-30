package net.machinemuse.numina.client.gui.geometry;

public class MuseRect implements IRect{
    MusePoint2D ul;
    MusePoint2D wh;
    protected IRect belowMe;
    protected IRect aboveMe;
    protected IRect leftOfMe;
    protected IRect rightOfMe;

    public MuseRect(double left, double top, double right, double bottom, boolean growFromMiddle) {
        ul = new MusePoint2D(left, top);
        wh = new MusePoint2D(right - left, bottom - top);
        if (growFromMiddle) {
            MusePoint2D center = ul.plus(wh.times(0.5));
            this.ul = new FlyFromPointToPoint2D(center, ul, 200);
            this.wh = new FlyFromPointToPoint2D(new MusePoint2D(0, 0), wh, 200);
        }
    }

    public MuseRect(double left, double top, double right, double bottom) {
        this(left, top, right, bottom, false);
    }

    public MuseRect(MusePoint2D ul, MusePoint2D br) {
        this.ul = ul;
        this.wh = br.minus(ul);
    }

    public MuseRect copyOf() {
        return new MuseRect(this.left(), this.top(), this.right(), this.bottom());
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        this.ul = ul;
        return this;
    }

    @Override
    public MusePoint2D getUL() {
        return ul;
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        this.wh = wh;
        return this;
    }

    @Override
    public MusePoint2D getWH() {
        return wh;
    }

    @Override
    public IRect setHeight(double value) {
        wh.setY(value);
        return this;
    }

    @Override
    public double height() {
        return wh.y;
    }

    @Override
    public IRect setWidth(double value) {
        wh.setX(value);
        return this;
    }

    @Override
    public double width() {
        return wh.x;
    }

    @Override
    public IRect setLeft(double value) {
        ul.setX(value);
        return this;
    }

    @Override
    public double left() {
        if (rightOfMe != null) {
            return rightOfMe.right();
        }
        return ul.getX();
    }

    @Override
    public IRect setRight(double value) {
        wh.setX(value - ul.x);
        return this;
    }

    @Override
    public double right() {
        if (leftOfMe != null) {
            return leftOfMe.left();
        }
        return left() + wh.getX();
    }


    @Override
    public IRect setTop(double value) {
        ul.setY(value);
        return this;
    }

    @Override
    public double top() {
        if (belowMe != null) {
            return belowMe.bottom();
        }
        return ul.getY();
    }

    @Override
    public IRect setBottom(double value) {
        wh.setY(value - ul.y);
        return this;
    }

    @Override
    public double bottom() {
        if (aboveMe != null) {
            return aboveMe.top();
        }
        return top() + wh.getY();
    }

    @Override
    public IRect setBelow(IRect aboveMe) {
        this.aboveMe = aboveMe;
        return this;
    }

    @Override
    public IRect setAbove(IRect belowMe) {
        this.belowMe = belowMe;
        return this;
    }

    @Override
    public IRect setLeftOf(IRect rightOfMe) {
        this.rightOfMe = rightOfMe;
        return this;
    }

    @Override
    public IRect setRightOf(IRect leftOfMe) {
        this.leftOfMe = leftOfMe;
        return this;
    }
}