package lehjr.numina.client.gui.gemoetry;

public class Rect implements IRect {
    MusePoint2D ul;
    MusePoint2D wh;
    protected IRect belowMe;
    protected IRect aboveMe;
    protected IRect leftOfMe;
    protected IRect rightOfMe;

    public Rect(double left, double top, double right, double bottom) {
        this(left, top, right, bottom, false);
    }

    public Rect(double left, double top, double right, double bottom, boolean growFromMiddle) {
        this(new MusePoint2D(left, top), new MusePoint2D(right, bottom), growFromMiddle);
    }

    public Rect(MusePoint2D ul, MusePoint2D br) {
        this(ul, br, false);
    }

    public Rect(MusePoint2D ul, MusePoint2D br, boolean growFromMiddle) {
        this.ul = ul;
        this.wh = br.minus(ul);
        if (growFromMiddle) {
            MusePoint2D center = ul.plus(wh.times(0.5));
            this.ul = new FlyFromPointToPoint2D(center, ul, 200);
            this.wh = new FlyFromPointToPoint2D(new MusePoint2D(0, 0), wh, 200);
        }
    }

    @Override
    public double top() {
        if (aboveMe != null) {
            return aboveMe.bottom();
        }
        return ul.y();
    }

    @Override
    public Rect setTop(double value) {
        ul.setY(value);
        return this;
    }

    @Override
    public double left() {
        if (leftOfMe != null) {
            return leftOfMe.right();
        }
        return ul.x();
    }

    @Override
    public Rect setLeft(double value) {
        ul.setX(value);
        return this;
    }

    @Override
    public double bottom() {
        if (belowMe != null) {
            return belowMe.top();
        }
        return top() + wh.y();
    }

    @Override
    public Rect setBottom(double value) {
        wh.y = value - ul.y();
        return this;
    }

    @Override
    public double right() {
        if (rightOfMe != null) {
            return rightOfMe.left();
        }
        return left() + wh.x();
    }

    @Override
    public Rect setRight(double value) {
        wh.x = value - ul.x();
        return this;
    }

    @Override
    public double width() {
        return wh.x();
    }

    @Override
    public double height() {
        return wh.y();
    }

    @Override
    public Rect setWidth(double value) {
        wh.setX(value);
        return this;
    }

    @Override
    public Rect setHeight(double value) {
        wh.setY(value);
        return this;
    }

    @Override
    public MusePoint2D getUL() {
        return ul;
    }

    @Override
    public Rect setUL(MusePoint2D ul) {
        this.ul = ul;
        return this;
    }

    @Override
    public MusePoint2D getWH() {
        return wh;
    }

    @Override
    public Rect setWH(MusePoint2D wh) {
        this.wh = wh;
        return this;
    }

    @Override
    public Rect setAbove(IRect belowMe) {
        this.belowMe = belowMe;
        return this;
    }

    @Override
    public Rect setLeftOf(IRect rightOfMe) {
        this.rightOfMe = rightOfMe;
        return this;
    }

    @Override
    public Rect setBelow(IRect aboveMe) {
        this.aboveMe = aboveMe;
        return this;
    }

    @Override
    public Rect setRightOf(IRect leftOfMe) {
        this.leftOfMe = leftOfMe;
        return this;
    }

    @Override
    public void moveBy(MusePoint2D amount) {
        IRect.super.moveBy(amount);
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(this.getClass()).append(":\n");
        stringbuilder.append("Left: ").append(left()).append("\n");
        stringbuilder.append("Top: ").append(top()).append("\n");
        stringbuilder.append("Right: ").append(right()).append("\n");
        stringbuilder.append("Bottom: ").append(bottom()).append("\n");
        stringbuilder.append("Width: ").append(width()).append("\n");
        stringbuilder.append("Height: ").append(height()).append("\n");
        stringbuilder.append("UL: ").append(getUL()).append("\n");
        stringbuilder.append("WH: ").append(getWH()).append("\n");

//        stringbuilder.append("isLeftOf: ").append(rightOfMe).append("\n");
//        stringbuilder.append("isRightOf: ").append(leftOfMe).append("\n");
//        stringbuilder.append("isBelow: ").append(aboveMe).append("\n");
//        stringbuilder.append("isAbove: ").append(belowMe).append("\n");

        return stringbuilder.toString();
    }
}