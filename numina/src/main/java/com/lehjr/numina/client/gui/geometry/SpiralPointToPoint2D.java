package com.lehjr.numina.client.gui.geometry;

public class SpiralPointToPoint2D extends MusePoint2D {
    protected final MusePoint2D center;
    protected final long spawnTime;
    protected final double timeTo;
    protected final boolean outwards;
    protected final double spiral = Math.PI;
    protected double radius;
    protected double rotation;

    public SpiralPointToPoint2D(double x, double y,
                                double x2, double y2,
                                double timeTo, boolean outwards) {
        super(x2, y2);
        center = new MusePoint2D(x, y);
        this.radius = this.distanceTo(center);
        this.rotation = (double) Math.atan2(y2 - y, x2 - x);
        spawnTime = System.currentTimeMillis();
        this.timeTo = timeTo;
        this.outwards = outwards;
    }

    public SpiralPointToPoint2D(double x, double y,
                                double x2, double y2,
                                double timeTo) {
        this(x, y, x2, y2, timeTo, true);
    }

    public SpiralPointToPoint2D(MusePoint2D center,
                                MusePoint2D target,
                                double timeTo, boolean outwards) {
        this(center.x(), center.y(), target.x(), target.y(), timeTo, outwards);
    }

    public SpiralPointToPoint2D(MusePoint2D center,
                                MusePoint2D target,
                                double timeTo) {
        this(center, target, timeTo, true);
    }

    public SpiralPointToPoint2D(MusePoint2D center,
                                double radius, double rotation,
                                double timeTo, boolean outward) {
        this(center.x(), center.y(), (double) (radius * Math.cos(rotation)), (double)(radius * Math.sin(rotation)), timeTo, outward);
        this.radius = radius;
        this.rotation = rotation;
    }

    public SpiralPointToPoint2D(MusePoint2D center,
                                double radius, double rotation,
                                double timeTo) {
        this(center, radius, rotation, timeTo, true);
    }

    private double getRatio() {
        long elapsed = System.currentTimeMillis() - spawnTime;
        double ratio = elapsed / timeTo;
        if (ratio > 1.0F) {
            ratio = 1.0F;
        }
        if (outwards) {
            return ratio;
        } else {
            return 1.0F - ratio;
        }
    }

    private double getTheta() {
        return (double) (rotation + (spiral * (1.0F - getRatio())));
    }

    @Override
    public double x() {
        //getX = r × cos(θ)
        return (double) (center.x() + (radius * getRatio()) * Math.cos(getTheta()));
    }

    @Override
    public double y() {
        //getY = r × sin(θ)
        return (double) (center.y() + (radius * getRatio()) * Math.sin(getTheta()));
    }

    @Override
    public SpiralPointToPoint2D plus(MusePoint2D b) {
        super.plus(b);
        return this;
    }

    @Override
    public SpiralPointToPoint2D plus(double x, double y) {
        super.plus(x, y);
        return this;
    }

    @Override
    public SpiralPointToPoint2D minus(MusePoint2D b) {
        super.minus(b);
        return this;
    }

    @Override
    public SpiralPointToPoint2D minus(double x, double y) {
        super.minus(x, y);
        return this;
    }

    @Override
    public SpiralPointToPoint2D times(double scalefactor) {
        super.times(scalefactor);
        return this;
    }

    @Override
    public String toString() {
        return "\ntarget.X: " + x + ", target.Y: " + y +
                "\nactualX: " + x() + "actualX: " + y() +
                "\nrotation: " + rotation +
                "\nradius: " + radius;
    }
}