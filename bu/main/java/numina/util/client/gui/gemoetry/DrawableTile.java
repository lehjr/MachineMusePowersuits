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

package lehjr.numina.util.client.gui.gemoetry;

import com.mojang.blaze3d.matrix.PoseStack;
import lehjr.numina.util.math.Color;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class DrawableTile extends RelativeRect implements IDrawableRect {
    final float lineWidth = 1F;
    Color topBorderColor = new Color(0.216F, 0.216F, 0.216F, 1F);
    Color bottomBorderColor = Color.WHITE.withAlpha(0.8F);
    Color backgroundColor = new Color(0.545F, 0.545F, 0.545F, 1F);
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

    public DrawableTile setTopBorderColor(Color topBorderColor) {
        this.topBorderColor = topBorderColor;
        return this;
    }

    public Color getTopBorderColor() {
        return this.topBorderColor;
    }

    public DrawableTile setBottomBorderColor(Color bottomBorderColor) {
        this.bottomBorderColor = bottomBorderColor;
        return this;
    }

    public Color getBottomBorderColor() {
        return this.bottomBorderColor;
    }

    public DrawableTile setBackgroundColor(Color insideColor) {
        this.backgroundColor = insideColor;
        return this;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public DrawableTile setBorderShrinkValue(float shrinkBy) {
        this.shrinkBoarderBy = shrinkBy;
        return this;
    }

    @Override
    public DrawableTile copyOf() {
        return new DrawableTile(super.left(), super.top(), super.right(), super.bottom(),
               this.growFromMiddle)
                .setBackgroundColor(backgroundColor)
                .setTopBorderColor(topBorderColor)
                .setBottomBorderColor(bottomBorderColor);
    }

    public void internalDraw(PoseStack matrixStack, Color colour, int glMode, double shrinkBy) {
        internalDrawRect(matrixStack,
                left() + shrinkBy,
                top() + shrinkBy,
                right() - shrinkBy,
                bottom() - shrinkBy,
                colour,
                glMode);
    }

    public void internalDrawRect(PoseStack matrixStack, double left, double top, double right, double bottom, Color colourIn, int glMode) {
        preDraw(glMode, DefaultVertexFormats.POSITION_COLOR);
        FloatBuffer vertices = BufferUtils.createFloatBuffer(8);
        Matrix4f matrix4f = matrixStack.last().pose();

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

    public void drawBackground(PoseStack matrixStack) {
        internalDraw(matrixStack, backgroundColor, GL11.GL_QUADS, 0);
    }

    public void drawBorder(PoseStack matrixStack, double shrinkBy) {
        internalDraw(matrixStack, topBorderColor, GL11.GL_LINE_LOOP, shrinkBy);
    }

    /**
     * Unfortunately, the line drawing rounds to nearest whole number
     * @param matrixStack
     * @param shrinkBy
     */
    public void drawDualColorBorder(PoseStack matrixStack, float shrinkBy) {
        float halfWidth = lineWidth * 0.5F;

        //----------------------------------------
        // Top line
        //----------------------------------------
        internalDrawRect(matrixStack,
                left() + shrinkBy - halfWidth,
                top() + shrinkBy - halfWidth,
                right() - shrinkBy + halfWidth,
                top() + shrinkBy + halfWidth,
                topBorderColor,
                GL11.GL_QUADS);

        //----------------------------------------
        // Left line
        //----------------------------------------
        internalDrawRect(matrixStack,
                left() + shrinkBy - halfWidth,
                top() + shrinkBy - halfWidth,
                left() + shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                topBorderColor,
                GL11.GL_QUADS);

        //----------------------------------------
        // Bottom line
        //----------------------------------------
        internalDrawRect(matrixStack,
                left() + shrinkBy - halfWidth,
                bottom() - shrinkBy - halfWidth,
                right() - shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                bottomBorderColor,
                GL11.GL_QUADS);

        //----------------------------------------
        // Right line
        //----------------------------------------
        internalDrawRect(matrixStack,
                right() - shrinkBy - halfWidth,
                top() + shrinkBy - halfWidth,
                right() - shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                bottomBorderColor,
                GL11.GL_QUADS);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float frameTime) {
        drawBackground(matrixStack);
        if (topBorderColor.equals(bottomBorderColor)) {
            drawBorder(matrixStack, shrinkBoarderBy);
        } else {
            drawDualColorBorder(matrixStack, shrinkBoarderBy);
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
        StringBuilder stringbuilder = new StringBuilder(super.toString());
        stringbuilder.append("Background Color: ").append(backgroundColor).append("\n");
        stringbuilder.append("Top Border Color: ").append(topBorderColor).append("\n");
        stringbuilder.append("Bottom Border Color: ").append(bottomBorderColor).append("\n");
        return stringbuilder.toString();
    }
}