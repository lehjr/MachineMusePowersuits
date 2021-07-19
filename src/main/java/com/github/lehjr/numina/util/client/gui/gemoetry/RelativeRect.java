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

package com.github.lehjr.numina.util.client.gui.gemoetry;

public class RelativeRect implements IRect {
    protected RelativeRect rectBelowMe;
    protected RelativeRect rectAboveMe;
    protected RelativeRect rectLeftOfMe;
    protected RelativeRect rectRightOfMe;

    /** top left origin */
    MusePoint2D ul;
    /** width, height */
    MusePoint2D wh;

    final boolean growFromMiddle;

    public RelativeRect() {
        this(false);
    }

    public RelativeRect(boolean growFromMiddle) {
        this(0,0,0,0, growFromMiddle);
    }

    public RelativeRect(MusePoint2D ul, MusePoint2D br) {
        this(ul, br, false);
    }

    public RelativeRect(MusePoint2D ul, MusePoint2D br, boolean growFromMiddle) {
        this.ul = ul;
        this.wh = br.minus(ul);
        this.growFromMiddle = growFromMiddle;
    }

    public RelativeRect(double left, double top, double right, double bottom) {
        this(left, top, right, bottom, false);
    }

    public RelativeRect(double left, double top, double right, double bottom, boolean growFromMiddle) {
        this.ul = new MusePoint2D(left, top);
        this.wh =  new MusePoint2D(right - left, bottom - top);
        this.growFromMiddle = growFromMiddle;
    }

    @Override
    public RelativeRect setUL(MusePoint2D ul) {
        this.ul.setX(ul.getX());
        this.ul.setY(ul.getY());
        return this;
    }

    @Override
    public RelativeRect setWH(MusePoint2D wh) {
        this.wh.setX(wh.getX());
        this.wh.setY(wh.getY());
        return this;
    }

    /**
     * @return if there is a rectangle set left of this one,
     * returns the full grown right side of that plus padding
     */
    @Override
    public double left() {
        if(rectLeftOfMe != null) {
            return rectLeftOfMe.right();
        }
        return ul.getX();
    }

    /**
     * @return if there is a rectangle set above this one,
     * returns the full grown bottom side of that plus padding
     */
    @Override
    public double top() {
        if (rectAboveMe != null) {
            return rectAboveMe.bottom();
        }
        return ul.getY();
    }

    /**
     * @return if there is a rectangle set right of this one,
     * returns the left side of that plus padding
     */
    @Override
    public double right() {
        if (rectRightOfMe != null) {
            return rectRightOfMe.left();
        }
        return left() + wh.getX();
    }

    @Override
    public double bottom() {
        if (rectBelowMe != null) {
            return rectBelowMe.top();
        }
        return top() + wh.getY();
    }

    @Override
    public MusePoint2D getUL() {
        return ul;
    }

    @Override
    public MusePoint2D getWH() {
        return wh;
    }

    @Override
    public double width() {
        return wh.getX();
    }

    @Override
    public double height() {
        return wh.getY();
    }

    @Override
    public RelativeRect setLeft(double value) {
        ul.setX(value);
        return this;
    }

    @Override
    public RelativeRect setRight(double value) {
        wh.setX(value - ul.getX());
        return this;
    }

    @Override
    public RelativeRect setTop(double value) {
        ul.setY(value);
        return this;
    }

    @Override
    public RelativeRect setBottom(double value) {
        wh.setY(value - ul.getY());
        return this;
    }

    @Override
    public RelativeRect setWidth(double value) {
        wh.setX(value);
        return this;
    }

    @Override
    public RelativeRect setHeight(double value) {
        wh.setY(value);
        return this;
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        setPosition(getPosition().plus(moveAmount));
    }

    @Override
    public void move(double x, double y) {
        move(new MusePoint2D(x, y));
    }

    @Override
    public void setPosition(MusePoint2D position) {
        MusePoint2D ulFinal;
        MusePoint2D whFinal;

        // this should be the case unless not initialized yet
        if (wh instanceof FlyFromPointToPoint2D) {
            whFinal = ((FlyFromPointToPoint2D) wh).getFinalPoint();
        } else {
            whFinal = wh.copy();
        }
        ulFinal = position.minus(whFinal.times(0.5F));

        ul.setX(ulFinal.getX());
        ul.setY(ulFinal.getY());
    }

    @Override
    public boolean growFromMiddle() {
        return growFromMiddle;
    }

    public RelativeRect copyOf() {
        return new RelativeRect(this.left(), this.top(), this.right(), this.bottom());
//                                .setBelow(this.belowme)
//                                .setAbove(this.aboveme)
//                                .setLeftOf(this.leftofme)
//                                .setRightOf(this.rightofme);
    }

    /**
     * Sets this rectangle left of another rectangle.
     * @param otherRightOfMe
     * @return this
     */
    @Override
    public RelativeRect setMeLeftof(RelativeRect otherRightOfMe) {
        this.rectRightOfMe = otherRightOfMe;
        return this;
    }

    /**
     * Sets this rectangle right of another rectangle
     * @param otherLeftOfMe
     * @return this
     */
    @Override
    public RelativeRect setMeRightOf(RelativeRect otherLeftOfMe) {
        this.rectLeftOfMe = otherLeftOfMe;
        return this;
    }

    /**
     * Sets this above another rectangle
     * @param otherBelowMe
     * @return this
     */
    @Override
    public RelativeRect setMeAbove(RelativeRect otherBelowMe) {
        this.rectBelowMe = otherBelowMe;
        return this;
    }

    /**
     * Sets this below another rectangle
     * @param otherAboveMe
     * @return this
     */
    @Override
    public RelativeRect setMeBelow(RelativeRect otherAboveMe) {
        this.rectAboveMe = otherAboveMe;
        return this;
    }

    public RelativeRect getRect() {
        return this;
    }

    IRect.IInit onInit;

    @Override
    public void setOnInit(IInit onInit) {
        this.onInit = onInit;
    }

    @Override
    public void onInit() {
        if (this.onInit != null) {
            this.onInit.onInit(this);
        }
    }

    @Override
    public void initGrowth() {
        if (growFromMiddle()) {
            MusePoint2D center = ul.plus(wh.times(0.5F));
            ul = new FlyFromPointToPoint2D(center, getUL(), 200);
            wh = new FlyFromPointToPoint2D(new MusePoint2D(0, 0), getWH(), 200);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(this.getClass()).append(":\n");
        stringbuilder.append("Center: ").append(center()).append("\n");
        stringbuilder.append("Left: ").append(left()).append("\n");
        stringbuilder.append("Right: ").append(right()).append("\n");
        stringbuilder.append("Bottom: ").append(bottom()).append("\n");
        stringbuilder.append("Top: ").append(top()).append("\n");
        stringbuilder.append("Width: ").append(width()).append("\n");
        stringbuilder.append("Height: ").append(height()).append("\n");
        return stringbuilder.toString();
    }
}