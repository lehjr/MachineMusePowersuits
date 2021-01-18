package com.github.lehjr.numina.util.client.gui.gemoetry;

import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class Meter extends DrawableMuseRect {
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
    public double getCornerradius() {
        return 0;
    }

    double value = 0;

    public void draw(MatrixStack matrixStack, double x, double y, float zLevel, double valueIn) {
        this.setUL(x, y);
        this.value = MuseMathUtils.clampDouble(valueIn, 0, 1);
        this.draw(matrixStack, zLevel);
    }

    FloatBuffer getMeterVertices() {
        float right = (float) (finalLeft() + (finalWidth() - 1) * value);
        return preDraw(this.finalLeft() +1, this.finalTop() + 1, right, this.finalBottom() -1);
    }

    public void draw(MatrixStack matrixStack,float zLevel) {
        this.zLevel = zLevel;

        // background
        FloatBuffer backgroundVertices = this.preDraw(0.0F);
        FloatBuffer backgroundColours = GradientAndArcCalculator.getColourGradient(this.backgroundColour, this.backgroundColour2, backgroundVertices.limit() * 4);
        this.drawBackground(matrixStack, backgroundVertices, backgroundColours);

        // meter
        FloatBuffer meterVertices = this.getMeterVertices();
        this.drawBuffer(matrixStack, meterVertices, meterColour, GL11.GL_TRIANGLE_FAN);

        // frame
        if (this.shrinkBorder) {
            backgroundVertices = this.preDraw(1.0F);
        } else {
            backgroundVertices.rewind();
        }

        this.drawBorder(matrixStack, backgroundVertices);
    }
}
