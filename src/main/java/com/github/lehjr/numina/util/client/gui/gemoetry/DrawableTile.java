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
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class DrawableTile extends MuseRelativeRect implements IDrawable {
    final float lineWidth = 1F;
    Colour topBorderColour = new Colour(0.216F, 0.216F, 0.216F, 1F);
    Colour bottomBorderColour = Colour.WHITE.withAlpha(0.8F);
    Colour backgroundColour = new Colour(0.545F, 0.545F, 0.545F, 1F);
    float zLevel = 0;
    float shrinkBoarderBy = 0;

    public DrawableTile(double left, double top, double right, double bottom, boolean growFromMiddle) {
        super(left, top, right, bottom, growFromMiddle);
    }

    public DrawableTile(double left, double top, double right, double bottom) {
        super(left, top, right, bottom, false);
    }

    public DrawableTile(MusePoint2D ul, MusePoint2D br) {
        super(ul, br);
    }

    public DrawableTile setTopBorderColour(Colour topBorderColour) {
        this.topBorderColour = topBorderColour;
        return this;
    }

    public DrawableTile setBottomBorderColour(Colour bottomBorderColour) {
        this.bottomBorderColour = bottomBorderColour;
        return this;
    }

    public DrawableTile setBackgroundColour(Colour insideColour) {
        this.backgroundColour = insideColour;
        return this;
    }


    public DrawableTile setBorderShrinkValue(float shrinkBy) {
        this.shrinkBoarderBy = shrinkBy;
        return this;
    }


    @Override
    public DrawableTile copyOf() {
        return new DrawableTile(super.left(), super.top(), super.right(), super.bottom(),
                (this.ul != this.ulFinal || this.wh != this.whFinal))
                .setBackgroundColour(backgroundColour)
                .setTopBorderColour(topBorderColour)
                .setBottomBorderColour(bottomBorderColour);
    }

    public void internalDraw(MatrixStack matrixStack, Colour colour, int glMode, double shrinkBy) {
        internalDrawRect(matrixStack,
                left() + shrinkBy,
                top() + shrinkBy,
                right() - shrinkBy,
                bottom() - shrinkBy,
                colour,
                glMode);
    }

    public void internalDrawRect(MatrixStack matrixStack, double left, double top, double right, double bottom, Colour colourIn, int glMode) {
        preDraw(glMode, DefaultVertexFormats.POSITION_COLOR);
        FloatBuffer vertices = BufferUtils.createFloatBuffer(8);
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();

        // top right
        vertices.put((float)right);
        vertices.put((float)top);

        // top left
        vertices.put((float)left);
        vertices.put((float)top);

        // bottom left
        vertices.put((float)left);
        vertices.put((float)bottom);

        // bottom right
        vertices.put((float)right);
        vertices.put((float)bottom);

        vertices.flip();
        vertices.rewind();
        addVerticesToBuffer(matrix4f, vertices, colourIn);

        drawTesselator();
        postDraw();
    }

    public void drawBackground(MatrixStack matrixStack) {
        internalDraw(matrixStack, backgroundColour, GL11.GL_QUADS, 0);
    }

    public void drawBorder(MatrixStack matrixStack, double shrinkBy) {
        internalDraw(matrixStack, topBorderColour, GL11.GL_LINE_LOOP, shrinkBy);
    }

    /**
     * Unfortunately, the line drawing rounds to nearest whole number
     * @param matrixStack
     * @param shrinkBy
     */
    public void drawDualColourBorder(MatrixStack matrixStack, float shrinkBy) {
        float halfWidth = lineWidth * 0.5F;

        //----------------------------------------
        // Top line
        //----------------------------------------
        internalDrawRect(matrixStack,
                left() + shrinkBy - halfWidth,
                top() + shrinkBy - halfWidth,
                right() - shrinkBy + halfWidth,
                top() + shrinkBy + halfWidth,
                topBorderColour,
                GL11.GL_QUADS);

        //----------------------------------------
        // Left line
        //----------------------------------------
        internalDrawRect(matrixStack,
                left() + shrinkBy - halfWidth,
                top() + shrinkBy - halfWidth,
                left() + shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                topBorderColour,
                GL11.GL_QUADS);

        //----------------------------------------
        // Bottom line
        //----------------------------------------
        internalDrawRect(matrixStack,
                left() + shrinkBy - halfWidth,
                bottom() - shrinkBy - halfWidth,
                right() - shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                bottomBorderColour,
                GL11.GL_QUADS);

        //----------------------------------------
        // Right line
        //----------------------------------------
        internalDrawRect(matrixStack,
                right() - shrinkBy - halfWidth,
                top() + shrinkBy - halfWidth,
                right() - shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                bottomBorderColour,
                GL11.GL_QUADS);
    }

    public void internalDraw(MatrixStack matrixStack, float zLevel) {
        this.zLevel = zLevel;
        drawBackground(matrixStack);
        if (topBorderColour.equals(bottomBorderColour)) {
            drawBorder(matrixStack, shrinkBoarderBy);
        } else {
            drawDualColourBorder(matrixStack, shrinkBoarderBy);
        }
    }

    @Override
    public float getZLevel() {
        return zLevel;
    }

    @Override
    public IDrawable setZLevel(float zLevelIn) {
        this.zLevel = zLevelIn;
        return this;
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