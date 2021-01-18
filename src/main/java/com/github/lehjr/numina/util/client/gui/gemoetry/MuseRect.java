/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.client.gui.gemoetry;

public class MuseRect implements IRect {
    /** Note: separate "target values" are because window based sizes don't initialize properly in the constructor */
    /** target upper, left point */
    MusePoint2D ulFinal;//
    /** target width and height */
    MusePoint2D whFinal;
    /** top left origin */
    MusePoint2D ul;
    /** width, height */
    MusePoint2D wh;

    final boolean growFromMiddle;

    public MuseRect(double left, double top, double right, double bottom, boolean growFromMiddle) {
        ulFinal = new MusePoint2D(left, top);
        whFinal = new MusePoint2D(right - left, bottom - top);
        ul = ulFinal.copy();
        wh = whFinal.copy();
        this.growFromMiddle = growFromMiddle;
    }

    /**
     *  Alternative to spawning a completely new object. Especially handy for GUI's with large constructors
     */
    public void setTargetDimensions(double left, double top, double right, double bottom) {
        ulFinal = new MusePoint2D(left, top);
        whFinal = new MusePoint2D(right - left, bottom - top);
        grow();
    }

    public void setTargetDimensions(MusePoint2D ul, MusePoint2D wh) {
        ulFinal = ul;
        whFinal = wh;
        grow();
    }

    public MuseRect(double left, double top, double right, double bottom) {
        this(left, top, right, bottom, false);
    }

    public MuseRect(MusePoint2D ul, MusePoint2D br, boolean growFromMiddle) {
        this.ulFinal = this.ul = ul;
        this.whFinal = this.wh = br.minus(ul);
        this.growFromMiddle = growFromMiddle;
    }

    public MuseRect(MusePoint2D ul, MusePoint2D br) {
        this.ulFinal = this.ul = ul;
        this.whFinal = this.wh = br.minus(ul);
        this.growFromMiddle = false;
    }

    /**
     * call after setTargetDimensions
     */
    void grow() {
        if (growFromMiddle) {
            MusePoint2D center = ulFinal.plus(whFinal.times(0.5F));
            this.ul = new FlyFromPointToPoint2D(center, ulFinal, 200);
            this.wh = new FlyFromPointToPoint2D(new MusePoint2D(0, 0), whFinal, 200);
        } else {
            this.ul = this.ulFinal.copy();
            this.wh = this.whFinal.copy();
        }
    }

    public MuseRect copyOf() {
        return new MuseRect(this.left(), this.top(), this.right(), this.bottom(), (this.ul != this.ulFinal || this.wh != this.whFinal));
    }

    @Override
    public MusePoint2D getUL() {
        return ul;
    }

    @Override
    public MusePoint2D getULFinal() {
        return ulFinal;
    }

    @Override
    public MusePoint2D getWH() {
        return wh;
    }

    @Override
    public MusePoint2D getWHFinal() {
        return whFinal;
    }

    @Override
    public double left() {
        return ul.getX();
    }

    @Override
    public double finalLeft() {
        return ulFinal.getX();
    }

    @Override
    public double top() {
        return ul.getY();
    }

    @Override
    public double finalTop() {
        return ulFinal.getY();
    }

    @Override
    public double right() {
        return ul.getX() + wh.getX();
    }

    @Override
    public double finalRight() {
        return ulFinal.getX() + whFinal.getX();
    }

    @Override
    public double bottom() {
        return ul.getY() + wh.getY();
    }

    @Override
    public double finalBottom() {
        return ulFinal.getY() + whFinal.getY();
    }

    @Override
    public double width() {
        return wh.getX();
    }

    @Override
    public double finalWidth() {
        return whFinal.getX();
    }

    @Override
    public double height() {
        return wh.getY();
    }

    @Override
    public double finalHeight() {
        return whFinal.getY();
    }

    @Override
    public IRect setLeft(double value) {
        ul.setX(value);
        ulFinal.setX(value);
        return this;
    }

    @Override
    public IRect setRight(double value) {
        wh.setX(value - ul.getX());
        whFinal.setX(value - ulFinal.getX());
        return this;
    }

    @Override
    public IRect setTop(double value) {
        ul.setY(value);
        ulFinal.setY(value);
        return this;
    }

    @Override
    public IRect setBottom(double value) {
        wh.setY(value - ul.getY());
        whFinal.setY(value - ulFinal.getY());
        return this;
    }

    @Override
    public IRect setWidth(double value) {
        wh.setX(value);
        whFinal.setX(value);
        return this;
    }

    @Override
    public IRect setHeight(double value) {
        wh.setY(value);
        whFinal.setY(value);
        return this;
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        ulFinal = whFinal.plus(moveAmount);
        whFinal = whFinal.plus(moveAmount);
        grow();
    }

    @Override
    public void move(double x, double y) {
        ulFinal = whFinal.plus(x, y);
        whFinal = whFinal.plus(x, y);
        grow();
    }

    @Override
    public void setPosition(MusePoint2D position) {
        ulFinal = position.minus(whFinal.times(0.5F));
        grow();
    }

    @Override
    public boolean growFromMiddle() {
        return growFromMiddle;
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(this.getClass()).append(":\n");
        stringbuilder.append("Center: ").append(center()).append("\n");
        stringbuilder.append("Left: ").append(left()).append("\n");
        stringbuilder.append("FinalLeft: ").append(finalLeft()).append("\n");
        stringbuilder.append("Right: ").append(right()).append("\n");
        stringbuilder.append("FinalRight: ").append(finalRight()).append("\n");
        stringbuilder.append("Bottom: ").append(bottom()).append("\n");
        stringbuilder.append("FinalBottom: ").append(finalBottom()).append("\n");
        stringbuilder.append("Top: ").append(top()).append("\n");
        stringbuilder.append("FinalTop: ").append(finalTop()).append("\n");
        stringbuilder.append("Width: ").append(left()).append("\n");
        stringbuilder.append("FinalWidthLeft: ").append(left()).append("\n");
        stringbuilder.append("Height: ").append(height()).append("\n");
        stringbuilder.append("FinalHeight: ").append(finalHeight()).append("\n");
        return stringbuilder.toString();
    }
}