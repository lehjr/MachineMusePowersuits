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

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import lehjr.numina.common.math.Color;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class DrawableRect extends Rect implements IDrawableRect {
    Color backgroundColor;
    Color borderColor;
    Color backgroundColor2 = null;
    float cornerradius = 3;
    public float zLevel = 1;
    boolean shrinkBorder = true;

    public DrawableRect(double left, double top, double right, double bottom, boolean growFromMiddle,
                        Color backgroundColor,
                        Color borderColor) {
        super(left, top, right, bottom, growFromMiddle);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    public DrawableRect(Rect ref, Color backgroundColor, Color borderColor) {
        super(ref.getUL(), ref.getWH());
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    public DrawableRect(double left, double top, double right, double bottom,
                        Color backgroundColor,
                        Color borderColor) {
        super(left, top, right, bottom, false);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
    }

    public DrawableRect(MusePoint2D ul, MusePoint2D br,
                        Color backgroundColor,
                        Color borderColor) {
        super(ul, br);
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
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

    /**
     * determine if the border should be smaller than the background rectangle (like tooltips)
     *
     * @param shrinkBorder
     */
    public void setShrinkBorder(boolean shrinkBorder) {
        this.shrinkBorder = shrinkBorder;
    }

    public DrawableRect setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public DrawableRect setSecondBackgroundColor(Color backgroundColor2In) {
        backgroundColor2 = backgroundColor2In;
        return this;
    }

    public DrawableRect setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
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
                getCornerradius(),
                (float)(left + getCornerradius()),
                (float)(top + getCornerradius()));

        vertices = BufferUtils.createFloatBuffer(corner.limit() * 4);
        vertices.put(corner);

        // bottom left corner
        corner = GradientAndArcCalculator.getArcPoints(
                (float)(3.0 * Math.PI / 2.0F),
                (float)(2.0 * Math.PI),
                getCornerradius(),
                (float)(left + getCornerradius()),
                (float)(bottom - getCornerradius()));
        vertices.put(corner);

        // bottom right corner
        corner = GradientAndArcCalculator.getArcPoints(
                0,
                (float) (Math.PI / 2.0),
                getCornerradius(),
                (float)(right - getCornerradius()),
                (float)(bottom - getCornerradius()));
        vertices.put(corner);

        // top right corner
        corner = GradientAndArcCalculator.getArcPoints(
                (float) (Math.PI / 2.0),
                (float) Math.PI,
                getCornerradius(),
                (float)(right - getCornerradius()),
                (float)(top + getCornerradius()));
        vertices.put(corner);
        vertices.flip();

        return vertices;
    }

    public void drawBackground(GuiGraphics gfx, FloatBuffer vertices) {
        drawBuffer(gfx, vertices, backgroundColor, VertexFormat.Mode.TRIANGLE_FAN);
    }

    public void drawBackground(GuiGraphics gfx, FloatBuffer vertices, FloatBuffer colors) {
        drawBuffer(gfx, vertices, colors, VertexFormat.Mode.TRIANGLE_FAN);
    }

    public void drawBorder(GuiGraphics gfx, FloatBuffer vertices) {
        drawBuffer(gfx, vertices, borderColor, VertexFormat.Mode.DEBUG_LINE_STRIP); // FIXME!!!!
    }

    void drawBuffer(GuiGraphics gfx, FloatBuffer vertices, Color color, VertexFormat.Mode glMode) {
        BufferBuilder builder = preDraw(glMode, DefaultVertexFormat.POSITION_COLOR);
        addVerticesToBuffer(builder, gfx.pose().last().pose(), vertices, color);
        postDraw(builder);
    }

    void drawBuffer(GuiGraphics gfx, FloatBuffer vertices, FloatBuffer colors, VertexFormat.Mode glMode) {
        BufferBuilder builder = preDraw(glMode, DefaultVertexFormat.POSITION_COLOR);
        addVerticesToBuffer(builder, gfx.pose().last().pose(), vertices, colors);
        postDraw(builder);
    }

    public float getCornerradius() {
        return cornerradius;
    }

    public DrawableRect setCornerradius(float cornerradiusIn) {
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
                getCornerradius(),
                (float) (left + getCornerradius()),
                (float) (top + getCornerradius()));

        vertices = BufferUtils.createFloatBuffer(corner.limit() * 4);
        vertices.put(corner);

        // bottom left corner
        corner = GradientAndArcCalculator.getArcPoints(
                (float) (3.0 * Math.PI / 2.0F),
                (float) (2.0 * Math.PI),
                getCornerradius(),
                (float) (left + getCornerradius()),
                (float) (bottom - getCornerradius()));
        vertices.put(corner);

        // bottom right corner
        corner = GradientAndArcCalculator.getArcPoints(
                0,
                (float) (Math.PI / 2.0),
                getCornerradius(),
                (float) (right - getCornerradius()),
                (float) (bottom - getCornerradius()));
        vertices.put(corner);

        // top right corner
        corner = GradientAndArcCalculator.getArcPoints(
                (float) (Math.PI / 2.0),
                (float) Math.PI,
                getCornerradius(),
                (float) (right - getCornerradius()),
                (float) (top + getCornerradius()));
        vertices.put(corner);
        vertices.flip();

        return vertices;
    }

    @Override
    public void preRender(GuiGraphics gfx, int mouseX, int mouseY, float frameTIme) {
        IDrawableRect.super.preRender(gfx, mouseX, mouseY, frameTIme);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
//        ShaderInstance oldShader = RenderSystem.getShader();
        FloatBuffer vertices = preDraw(0);

        if (backgroundColor2 != null) {
            FloatBuffer colors = GradientAndArcCalculator.getColorGradient(backgroundColor,
                    backgroundColor2, vertices.limit() * 4);
            drawBackground(gfx, vertices, colors);
        } else {
            drawBackground(gfx, vertices);
        }

        if (shrinkBorder) {
            vertices = preDraw(1);
        } else {
            vertices.rewind();
        }
        drawBorder(gfx, vertices);
//        RenderSystem.setShader(() -> oldShader);
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(super.toString());
        stringbuilder.append("Background Color: ").append(backgroundColor.toString()).append("\n");
        stringbuilder.append("Background Color 2: ").append(backgroundColor2 == null? "null" : backgroundColor2.toString()).append("\n");
        stringbuilder.append("Border Color: ").append(borderColor.toString()).append("\n");
        return stringbuilder.toString();
    }
}