package com.github.lehjr.numina.util.client.gui.gemoetry;

/**
 * Base class for Points. The main reason for this is to have a
 * pass-by-reference coordinate with getter/setter functions so that points with
 * more elaborate behaviour can be implemented - such as for open/close
 * animations.
 *
 * @author MachineMuse
 */
public class MusePoint2D {
    protected double x;
    protected double y;

    public MusePoint2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public MusePoint2D(MusePoint2D p) {
        this(p.x, p.y);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public MusePoint2D plus(MusePoint2D b) {
        return new MusePoint2D(this.getX() + b.getX(), this.getY() + b.getY());
    }

    public MusePoint2D plus(double x, double y) {
        return new MusePoint2D(this.getX() + x, this.getY() + y);
    }

    public MusePoint2D minus(MusePoint2D b) {
        return new MusePoint2D(this.getX() - b.getX(), this.getY() - b.getY());
    }

    public MusePoint2D minus(double x, double y) {
        return new MusePoint2D(this.getX() - x, this.getY() - y);
    }

    public MusePoint2D times(double scalefactor) {
        return new MusePoint2D(this.getX() * scalefactor, this.getY() * scalefactor);
    }

    public boolean equals(MusePoint2D other) {
        return this.getX() == other.getX() && this.getY() == other.getY();
    }

    public double distance() {
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
    }

    public double distanceTo(MusePoint2D position) {
        return Math.sqrt(this.distanceSq(position));
    }

    public double distanceSq(MusePoint2D position) {
        double xdist = position.getX() - this.getX();
        double ydist = position.getY() - this.getY();
        return xdist * xdist + ydist * ydist;
    }

    public MusePoint2D normalize() {
        double distance = this.distance();
        return new MusePoint2D(this.getX() / distance, this.getY() / distance);
    }

    public MusePoint2D midpoint(MusePoint2D target) {
        return new MusePoint2D((this.getX() + target.getX()) / 2.0D, (this.getY() + target.getY()) / 2.0D);
    }

    public MusePoint2D copy() {
        return new MusePoint2D(this.getX(), this.getY());
    }

    public String toString() {
        return "x: " + this.x + ", y: " + this.y;
    }
}