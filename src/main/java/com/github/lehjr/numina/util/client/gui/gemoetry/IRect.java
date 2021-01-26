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

/**
 * @author lehjr
 */
public interface IRect {
    /**
     *  Alternative to spawning a completely new object. Especially handy for GUI's with large constructors
     */
    void setTargetDimensions(double left, double top, double right, double bottom);

    void setTargetDimensions(MusePoint2D ul, MusePoint2D wh);

    default MusePoint2D center() {
        return new MusePoint2D(centerx(), centery());
    }

    MusePoint2D getUL();

    MusePoint2D getULFinal();

    MusePoint2D getWH();

    MusePoint2D getWHFinal();

    double left();

    double finalLeft();

    double top();

    double finalTop();

    double right();

    double finalRight();

    double bottom();

    double finalBottom();

    double width();

    double finalWidth();

    double height();

    double finalHeight();

    IRect setLeft(double value);

    IRect setRight(double value);

    IRect setTop(double value);

    IRect setBottom(double value);

    IRect setWidth(double value);

    IRect setHeight(double value);

    void move(MusePoint2D moveAmount);

    void move(double x, double y);

    void setPosition(MusePoint2D position);

    boolean growFromMiddle();

    default boolean containsPoint(double x, double y) {
        return x > left() && x < right() && y > top() && y < bottom();
    }

    default double centerx() {
        return (left() + right()) / 2.0F;
    }

    default double centery() {
        return (top() + bottom()) / 2.0F;
    }
}