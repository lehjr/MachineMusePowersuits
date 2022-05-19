/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.lehjr.numina.client.gui.geometry;

public class FlyFromPointToPoint2D extends MusePoint2D {
    protected final MusePoint2D prev;
    protected final long spawnTime;
    protected final double timeTo;

    public FlyFromPointToPoint2D(double x, double y, double x2, double y2, double timeTo) {
        super(x2, y2);
        this.prev = new MusePoint2D(x, y);
        this.spawnTime = System.currentTimeMillis();
        this.timeTo = timeTo;
    }

    public FlyFromPointToPoint2D(MusePoint2D prev, MusePoint2D target, double timeTo) {
        this(prev.getX(), prev.getY(), target.getX(), target.getY(), timeTo);
    }

    public double getX() {
        return this.doRatio(this.prev.x, this.x);
    }

    public double getY() {
        return this.doRatio(this.prev.y, this.y);
    }

    public double getFinalX() {
        return super.getX();
    }

    public double getFinalY() {
        return super.getY();
    }

    public MusePoint2D getFinalPoint() {
        return new MusePoint2D(getFinalX(), getFinalY());
    }

    public boolean doneFlying() {
        return getRatio() > 1;
    }

    double getRatio() {
        long elapsed = System.currentTimeMillis() - this.spawnTime;
        return (double)elapsed / this.timeTo;
    }

    public double doRatio(double val1, double val2) {
        double ratio = getRatio();
        return ratio > 1.0D ? val2 : val2 * ratio + val1 * (1.0D - ratio);
    }
}