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

import com.mojang.blaze3d.vertex.VertexFormat;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.gui.GuiGraphics;

import java.nio.FloatBuffer;

public class Meter extends DrawableRect {
    Color meterColor;

    public Meter(Color meterColorIn) {
        super(new MusePoint2D(0, 0), new MusePoint2D(0, 0), Color.GREY.withAlpha(0.3F), Color.BLACK.withAlpha(0.8F));
        setWidth(32);
        setHeight(8);
        setSecondBackgroundColor(Color.WHITE.withAlpha(0.3F));
        setShrinkBorder(true);
        this.meterColor = meterColorIn;
    }

    public Meter setMeterColor(Color meterColorIn) {
        this.meterColor = meterColorIn;
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

    public void draw(GuiGraphics gfx, double x, double y, float zLevel, double valueIn) {
        this.setUL(x, y);
        this.value = MathUtils.clampDouble(valueIn, 0, 1);
        this.draw(gfx, zLevel);
    }

    FloatBuffer getMeterVertices() {
        float right = (float) (left() + (width() - 1) * value);
        return getVertices(this.left() +1, this.top() + 1, right, this.bottom() -1);
    }

    public void draw(GuiGraphics gfx, float zLevel) {
        this.zLevel = zLevel;

        // background
        FloatBuffer backgroundVertices = this.getVertices(0.0F);
        FloatBuffer backgroundColors = GradientAndArcCalculator.getColorGradient(this.backgroundColor, this.backgroundColor2, backgroundVertices.limit() * 4);
        this.drawBackground(gfx, backgroundVertices, backgroundColors);

        // meter
        FloatBuffer meterVertices = this.getMeterVertices();
        this.drawBuffer(gfx, meterVertices, meterColor, VertexFormat.Mode.TRIANGLE_FAN);

        // frame
        if (this.shrinkBorder) {
            backgroundVertices = this.getVertices(1.0F);
        } else {
            backgroundVertices.rewind();
        }

        this.drawBorder(gfx, backgroundVertices);
    }
}
