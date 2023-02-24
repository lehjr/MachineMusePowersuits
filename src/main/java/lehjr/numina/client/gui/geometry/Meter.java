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

package lehjr.numina.client.gui.geometry;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.math.MathUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class Meter extends DrawableRect {
    Colour meterColour;

    public Meter(Colour meterColourIn) {
        super(new MusePoint2D(0, 0), new MusePoint2D(0, 0), Colour.GREY.withAlpha(0.3F), Colour.BLACK.withAlpha(0.8F));
        setWidth(32);
        setHeight(8);
        setSecondBackgroundColour(Colour.WHITE.withAlpha(0.3F));
        setShrinkBorder(true);
        this.meterColour = meterColourIn;
    }

    public Meter setMeterColour(Colour meterColourIn) {
        this.meterColour = meterColourIn;
        return this;
    }

    void setUL(double x, double y) {
        this.setTop(y);
        this.setLeft(x);
    }

    @Override
    public float getCornerradius() {
        return 0;
    }

    double value = 0;

    public void draw(MatrixStack matrixStack, double x, double y, float zLevel, double valueIn) {
        this.setUL(x, y);
        this.value = MathUtils.clampDouble(valueIn, 0, 1);
        this.draw(matrixStack, zLevel);
    }

    FloatBuffer getMeterVertices() {
        float right = (float) (left() + (width() - 1) * value);
        return getVertices(this.left() +1, this.top() + 1, right, this.bottom() -1);
    }

    public void draw(MatrixStack matrixStack,float zLevel) {
        this.zLevel = zLevel;

        // background
        FloatBuffer backgroundVertices = this.getVertices(0.0F);
        FloatBuffer backgroundColours = GradientAndArcCalculator.getColourGradient(this.backgroundColour, this.backgroundColour2, backgroundVertices.limit() * 4);
        this.drawBackground(matrixStack, backgroundVertices, backgroundColours);

        // meter
        FloatBuffer meterVertices = this.getMeterVertices();
        this.drawBuffer(matrixStack, meterVertices, meterColour, GL11.GL_TRIANGLE_FAN);

        // frame
        if (this.shrinkBorder) {
            backgroundVertices = this.getVertices(1.0F);
        } else {
            backgroundVertices.rewind();
        }

        this.drawBorder(matrixStack, backgroundVertices);
    }
}
