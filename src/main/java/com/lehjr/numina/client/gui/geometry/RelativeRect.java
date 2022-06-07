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

public class RelativeRect implements IRect{
    protected IRect rectBelowMe;
    protected IRect rectAboveMe;
    protected IRect rectLeftOfMe;
    protected IRect rectRightOfMe;

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
    public RelativeRect init(double left, double top, double right, double bottom) {
        this.ul.setX(left);
        this.ul.setY(top);
        this.wh.setX(right - left);
        this.wh.setY(bottom - top);
        initGrowth();
        this.onInit();
        return this;
    }

    @Override
    public IRect setUL(MusePoint2D ul) {
        this.ul = ul;
        doThisOnChange();
        return this;
    }

    @Override
    public IRect setWH(MusePoint2D wh) {
        this.wh = wh;
        doThisOnChange();
        return this;
    }

    /**
     * @return if there is a rectangle set above this one,
     * returns the full grown bottom side of that
     */
    @Override
    public double top() {
        if (rectAboveMe != null) {
            return rectAboveMe.bottom();
        }
        return ul.getY();
    }

    @Override
    public double finalTop() {
        if (rectAboveMe != null) {
            return rectAboveMe.finalBottom();
        }

        // this should be the case unless not initialized yet
        if (ul instanceof FlyFromPointToPoint2D) {
            return ((FlyFromPointToPoint2D) ul).getFinalY();
        }

        return ul.getY();
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
    @Override
    public double finalLeft() {
        if(rectLeftOfMe != null) {
            return rectLeftOfMe.finalRight();
        }

        // this should be the case unless not initialized yet
        if (ul instanceof FlyFromPointToPoint2D) {
            return ((FlyFromPointToPoint2D) ul).getFinalX();
        }
        return ul.getX();
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
    public double finalRight() {
        if (rectRightOfMe != null) {
            return rectRightOfMe.finalLeft();
        }
        return finalLeft() + finalWidth();
    }

    @Override
    public double bottom() {
        if (rectBelowMe != null) {
            return rectBelowMe.top();
        }
        return top() + height();
    }

    @Override
    public double finalBottom() {
        if (rectBelowMe != null) {
            return rectBelowMe.top();
        }
        return finalTop() + finalHeight();
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
    public double finalWidth() {
        // this should be the case unless not initialized yet
        if (wh instanceof FlyFromPointToPoint2D) {
            return ((FlyFromPointToPoint2D) wh).getFinalX();
        } else {
            return wh.getX();
        }
    }

    @Override
    public double height() {
        return wh.getY();
    }

    @Override
    public double finalHeight() {
        // this should be the case unless not initialized yet
        if (wh instanceof FlyFromPointToPoint2D) {
            return ((FlyFromPointToPoint2D) wh).getFinalY();
        } else {
            return wh.getY();
        }
    }

    @Override
    public IRect setLeft(double value) {
        ul.setX(value);
        doThisOnChange();
        return this;
    }

    @Override
    public IRect setRight(double value) {
        setLeft(value - finalWidth());
        doThisOnChange();
        return this;
    }

    @Override
    public IRect setTop(double value) {
        ul.setY(value);
        doThisOnChange();
        return this;
    }

    @Override
    public IRect setBottom(double value) {
        setTop(value - finalHeight());
        doThisOnChange();
        return this;

    }

    @Override
    public IRect setWidth(double value) {
        wh.setX(value);
        doThisOnChange();
        return this;
    }

    @Override
    public IRect setHeight(double value) {
        wh.setY(value);
        doThisOnChange();
        return this;
    }

    @Override
    public void move(MusePoint2D moveAmount) {
        move(moveAmount.getX(), moveAmount.getY());
        doThisOnChange();
    }

    @Override
    public void move(double x, double y) {
        ul.setX(finalLeft() + x);
        ul.setY(finalTop() + y);
        doThisOnChange();
    }

    @Override
    public void setPosition(MusePoint2D positionIn) {
        if (!wh.equals(MusePoint2D.ZERO)) {
            setUL(positionIn.minus(finalWidth() * 0.5, finalHeight() * 0.5));
        } else {
            ul = positionIn;
        }
        doThisOnChange();
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
    public IRect setMeLeftOf(IRect otherRightOfMe) {
        this.rectRightOfMe = otherRightOfMe;
        doThisOnChange();
        return this;
    }

    /**
     * Sets this rectangle right of another rectangle
     * @param otherLeftOfMe
     * @return this
     */
    @Override
    public IRect setMeRightOf(IRect otherLeftOfMe) {
        this.rectLeftOfMe = otherLeftOfMe;
        doThisOnChange();
        return this;
    }

    /**
     * Sets this above another rectangle
     * @param otherBelowMe
     * @return this
     */
    @Override
    public IRect setMeAbove(IRect otherBelowMe) {
        this.rectBelowMe = otherBelowMe;
        doThisOnChange();
        return this;
    }

    /**
     * Sets this below another rectangle
     * @param otherAboveMe
     * @return this
     */
    @Override
    public IRect setMeBelow(IRect otherAboveMe) {
        this.rectAboveMe = otherAboveMe;
        doThisOnChange();
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
    public void setDoThisOnChange(IDoThis iDoThis) {
        this.iDoThis = iDoThis;
    }

    IRect.IDoThis iDoThis;
    @Override
    public void doThisOnChange() {
        if (this.iDoThis != null) {
            this.iDoThis.doThis(this);
        }
    }

    @Override
    public void initGrowth() {
        if (growFromMiddle()) {
            MusePoint2D center = ul.plus(wh.times(0.5F));
            ul = new FlyFromPointToPoint2D(center, getUL(), 200);
            wh = new FlyFromPointToPoint2D(new MusePoint2D(0, 0), getWH(), 200);
        }
        doThisOnChange();
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(this.getClass()).append(":\n");
        stringbuilder.append("Center: ").append(center()).append("\n");

        stringbuilder.append("Left: ").append(left()).append("\n");
        stringbuilder.append("Final Left: ").append(finalLeft()).append("\n");

        stringbuilder.append("Right: ").append(right()).append("\n");
        stringbuilder.append("Final Right: ").append(finalRight()).append("\n");

        stringbuilder.append("Bottom: ").append(bottom()).append("\n");
        stringbuilder.append("Final Bottom: ").append(finalBottom()).append("\n");

        stringbuilder.append("Top: ").append(top()).append("\n");
        stringbuilder.append("Final Top: ").append(finalTop()).append("\n");

        stringbuilder.append("Width: ").append(width()).append("\n");
        stringbuilder.append("Final Width: ").append(finalWidth()).append("\n");

        stringbuilder.append("Height: ").append(height()).append("\n");
        stringbuilder.append("Final Height: ").append(finalHeight()).append("\n");
        return stringbuilder.toString();
    }
}