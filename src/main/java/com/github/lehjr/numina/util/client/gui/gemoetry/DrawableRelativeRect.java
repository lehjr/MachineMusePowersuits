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

import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class DrawableRelativeRect extends RelativeRect implements IDrawable {
    Colour backgroundColour;
    Colour borderColour;
    Colour backgroundColour2 = null;
    float cornerradius = 3;
    float zLevel = 1;
    boolean shrinkBorder = true;

    public DrawableRelativeRect(double left, double top, double right, double bottom, boolean growFromMiddle,
                                Colour backgroundColour,
                                Colour borderColour) {
        super(left, top, right, bottom, growFromMiddle);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    public DrawableRelativeRect(RelativeRect ref, Colour backgroundColour, Colour borderColour) {
        super(ref.left(), ref.top(), ref.right(), ref.bottom(), ref.growFromMiddle());
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    public DrawableRelativeRect(double left, double top, double right, double bottom,
                                Colour backgroundColour,
                                Colour borderColour) {
        super(left, top, right, bottom, false);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    public DrawableRelativeRect(MusePoint2D ul, MusePoint2D br,
                                Colour backgroundColour,
                                Colour borderColour) {
        super(ul, br);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    @Override
    public DrawableRelativeRect copyOf() {
        return new DrawableRelativeRect(super.left(), super.top(), super.right(), super.bottom(),
                (this.ul != this.ulFinal || this.wh != this.whFinal) , backgroundColour, borderColour);
    }

    /**
     * determine if the border should be smaller than the background rectangle (like tooltips)
     *
     * @param shrinkBorder
     */
    public void setShrinkBorder(boolean shrinkBorder) {
        this.shrinkBorder = shrinkBorder;
    }





















//    @Override
//    public DrawableRelativeRect<T> setLeft(double value) {
//        super.setLeft(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setRight(double value) {
//        super.setRight(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setTop(double value) {
//        super.setTop(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setBottom(double value) {
//        super.setBottom(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setWidth(double value) {
//        super.setWidth(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setHeight(double value) {
//        super.setHeight(value);
//        return this;
//    }
//
    public DrawableRelativeRect setBackgroundColour(Colour backgroundColour) {
        this.backgroundColour = backgroundColour;
        return this;
    }

    public DrawableRelativeRect setSecondBackgroundColour(Colour backgroundColour2In) {
        backgroundColour2 = backgroundColour2In;
        return this;
    }

    public DrawableRelativeRect setBorderColour(Colour borderColour) {
        this.borderColour = borderColour;
        return this;
    }

    public FloatBuffer preDraw(double shrinkBy) {
        return preDraw(left() + shrinkBy, top() + shrinkBy, right() - shrinkBy, bottom() - shrinkBy);
    }

    public FloatBuffer preDraw(double left, double top, double right, double bottom) {
        FloatBuffer vertices;
        // top left corner
        FloatBuffer corner = GradientAndArcCalculator.getArcPoints(
                (float)Math.PI,
                (float)(3.0 * Math.PI / 2.0),
                (float)getCornerradius(),
                (float)(left + getCornerradius()),
                (float)(top + getCornerradius()));

        vertices = BufferUtils.createFloatBuffer(corner.limit() * 4);
        vertices.put(corner);

        // bottom left corner
        corner = GradientAndArcCalculator.getArcPoints(
                (float)(3.0 * Math.PI / 2.0F),
                (float)(2.0 * Math.PI),
                (float)getCornerradius(),
                (float)(left + getCornerradius()),
                (float)(bottom - getCornerradius()));
        vertices.put(corner);

        // bottom right corner
        corner = GradientAndArcCalculator.getArcPoints(
                0,
                (float) (Math.PI / 2.0),
                (float)getCornerradius(),
                (float)(right - getCornerradius()),
                (float)(bottom - getCornerradius()));
        vertices.put(corner);

        // top right corner
        corner = GradientAndArcCalculator.getArcPoints(
                (float) (Math.PI / 2.0),
                (float) Math.PI,
                (float)getCornerradius(),
                (float)(right - getCornerradius()),
                (float)(top + getCornerradius()));
        vertices.put(corner);
        vertices.flip();

        return vertices;
    }

    public void drawBackground(MatrixStack matrixStack, FloatBuffer vertices) {
        drawBuffer(matrixStack, vertices, backgroundColour, GL11.GL_TRIANGLE_FAN);
    }

    public void drawBackground(MatrixStack matrixStack, FloatBuffer vertices, FloatBuffer colours) {
        drawBuffer(matrixStack, vertices, colours, GL11.GL_TRIANGLE_FAN);
    }

    public void drawBorder(MatrixStack matrixStack, FloatBuffer vertices) {
        drawBuffer(matrixStack, vertices, borderColour, GL11.GL_LINE_LOOP);
    }

    void drawBuffer(MatrixStack matrixStack, FloatBuffer vertices, Colour colour, int glMode) {
        preDraw(glMode, DefaultVertexFormats.POSITION_COLOR);
        addVerticesToBuffer(matrixStack.last().pose(), vertices, colour);
        drawTesselator();
        postDraw();
    }
//    void drawBuffer(MatrixStack matrixStack, FloatBuffer vertices, Colour colour, int glMode) {
//        RenderSystem.disableTexture();
//        RenderSystem.enableBlend();
//        RenderSystem.disableAlphaTest();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.shadeModel(GL11.GL_SMOOTH);
//
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuilder();
//        buffer.begin(glMode, DefaultVertexFormats.POSITION_COLOR);
//
//        while (vertices.hasRemaining()) {
//            buffer.vertex(matrixStack.last().pose(), vertices.get(), vertices.get(), zLevel).color(colour.r, colour.g, colour.b, colour.a).endVertex();
//        }
//
//        tessellator.end();
//
//        RenderSystem.shadeModel(GL11.GL_FLAT);
//        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
//        RenderSystem.enableTexture();
//    }


    void drawBuffer(MatrixStack matrixStack, FloatBuffer vertices, FloatBuffer colours, int glMode) {
        preDraw(glMode, DefaultVertexFormats.POSITION_COLOR);
        addVerticesToBuffer(matrixStack.last().pose(), vertices, colours);
        drawTesselator();
        postDraw();
    }

//    void drawBuffer(MatrixStack matrixStack, FloatBuffer vertices, FloatBuffer colours, int glMode) {
//        RenderSystem.disableTexture();
//        RenderSystem.enableBlend();
//        RenderSystem.disableAlphaTest();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.shadeModel(GL11.GL_SMOOTH);
//
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuilder();
//        buffer.begin(glMode, DefaultVertexFormats.POSITION_COLOR);
//
//        while (vertices.hasRemaining() && colours.hasRemaining()) {
//            buffer.vertex(matrixStack.last().pose(), vertices.get(), vertices.get(), zLevel).color(colours.get(), colours.get(), colours.get(), colours.get()).endVertex();
//        }
//        tessellator.end();
//
//        RenderSystem.shadeModel(GL11.GL_FLAT);
//        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
//        RenderSystem.enableTexture();
//    }

    // FIXME!!! still need to address gradient direction
    public void draw(MatrixStack matrixStack, float zLevel) {
        this.zLevel = zLevel;
        FloatBuffer vertices = preDraw(0);

        if (backgroundColour2 != null) {
            FloatBuffer colours = GradientAndArcCalculator.getColourGradient(backgroundColour,
                    backgroundColour2, vertices.limit() * 4);
            drawBackground(matrixStack, vertices, colours);
        } else {
            drawBackground(matrixStack, vertices);
        }

        if (shrinkBorder) {
            vertices = preDraw(1);
        } else {
            vertices.rewind();
        }
        drawBorder(matrixStack, vertices);
    }


//-----------------------------------------------------------------
//    @Override
//    public DrawableRelativeRect copyOf() {
//        return new DrawableRelativeRect(super.left(), super.top(), super.right(), super.bottom(),
//                (this.ul != this.ulFinal || this.wh != this.whFinal), backgroundColour, borderColour).setBackgroundColour(backgroundColour2);
//    }
//
//    @Override
//    public DrawableRelativeRect setLeft(double value) {
//        super.setLeft(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setRight(double value) {
//        super.setRight(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setTop(double value) {
//        super.setTop(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setBottom(double value) {
//        super.setBottom(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setWidth(double value) {
//        super.setWidth(value);
//        return this;
//    }
//
//    @Override
//    public DrawableRelativeRect setHeight(double value) {
//        super.setHeight(value);
//        return this;
//    }



    public float getCornerradius() {
        return cornerradius;
    }

    public DrawableRelativeRect setCornerradius(float cornerradiusIn) {
        this.cornerradius = cornerradiusIn;
        return this;
    }

    public FloatBuffer getVertices(double shrinkBy) {
        return getVertices(left() + shrinkBy, top() + shrinkBy, right() - shrinkBy, bottom() - shrinkBy);
    }

    public FloatBuffer getVertices(double left, double top, double right, double bottom) {
        FloatBuffer vertices;
        // top left corner
        FloatBuffer corner = GradientAndArcCalculator.getArcPoints(
                (float) Math.PI,
                (float) (3.0 * Math.PI / 2.0),
                (float) getCornerradius(),
                (float) (left + getCornerradius()),
                (float) (top + getCornerradius()));

        vertices = BufferUtils.createFloatBuffer(corner.limit() * 4);
        vertices.put(corner);

        // bottom left corner
        corner = GradientAndArcCalculator.getArcPoints(
                (float) (3.0 * Math.PI / 2.0F),
                (float) (2.0 * Math.PI),
                (float) getCornerradius(),
                (float) (left + getCornerradius()),
                (float) (bottom - getCornerradius()));
        vertices.put(corner);

        // bottom right corner
        corner = GradientAndArcCalculator.getArcPoints(
                0,
                (float) (Math.PI / 2.0),
                (float) getCornerradius(),
                (float) (right - getCornerradius()),
                (float) (bottom - getCornerradius()));
        vertices.put(corner);

        // top right corner
        corner = GradientAndArcCalculator.getArcPoints(
                (float) (Math.PI / 2.0),
                (float) Math.PI,
                (float) getCornerradius(),
                (float) (right - getCornerradius()),
                (float) (top + getCornerradius()));
        vertices.put(corner);
        vertices.flip();

        return vertices;
    }

    public void setzLevel(float zLevel) {
        this.zLevel = zLevel;
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

    @Override
    public float getZLevel() {
        return this.zLevel;
    }

    @Override
    public IDrawable setZLevel(float zLevelIn) {
        zLevel = zLevelIn;
        return this;
    }
}