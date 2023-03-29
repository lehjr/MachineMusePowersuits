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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import lehjr.numina.common.math.Color;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class DrawableTile extends Rect implements IDrawableRect {
    final float lineWidth = 1F;
    Color topBorderColour = new Color(0.216F, 0.216F, 0.216F, 1F);
    Color bottomBorderColour = Color.WHITE.withAlpha(0.8F);
    Color backgroundColour = new Color(0.545F, 0.545F, 0.545F, 1F);
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

    public DrawableTile setTopBorderColour(Color topBorderColour) {
        this.topBorderColour = topBorderColour;
        return this;
    }

    public Color getTopBorderColour() {
        return this.topBorderColour;
    }

    public DrawableTile setBottomBorderColour(Color bottomBorderColour) {
        this.bottomBorderColour = bottomBorderColour;
        return this;
    }

    public Color getBottomBorderColour() {
        return this.bottomBorderColour;
    }

    public DrawableTile setBackgroundColour(Color insideColour) {
        this.backgroundColour = insideColour;
        return this;
    }

    public Color getBackgroundColour() {
        return this.backgroundColour;
    }

    public DrawableTile setBorderShrinkValue(float shrinkBy) {
        this.shrinkBoarderBy = shrinkBy;
        return this;
    }

    public void internalDraw(PoseStack matrixStack, Color colour, VertexFormat.Mode mode, double shrinkBy) {
        internalDrawRect(matrixStack,
                left() + shrinkBy,
                top() + shrinkBy,
                right() - shrinkBy,
                bottom() - shrinkBy,
                colour,
                mode);
    }

    public void internalDrawRect(PoseStack matrixStack, double left, double top, double right, double bottom, Color colourIn, VertexFormat.Mode mode) {
        BufferBuilder builder = preDraw(mode, DefaultVertexFormat.POSITION_COLOR);
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
        addVerticesToBuffer(builder, matrix4f, vertices, colourIn);
        builder.end();
        postDraw(builder);
    }

    public void drawBackground(PoseStack matrixStack) {
        internalDraw(matrixStack, backgroundColour, VertexFormat.Mode.QUADS, 0);
    }

    public void drawBorder(PoseStack matrixStack, double shrinkBy) {
        internalDraw(matrixStack, topBorderColour, VertexFormat.Mode.DEBUG_LINES, shrinkBy);
    }

    /**
     * Unfortunately, the line drawing rounds to the nearest whole number
     * @param matrixStack
     * @param shrinkBy
     */
    public void drawDualColourBorder(PoseStack matrixStack, float shrinkBy) {
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
                VertexFormat.Mode.QUADS);

        //----------------------------------------
        // Left line
        //----------------------------------------
        internalDrawRect(matrixStack,
                left() + shrinkBy - halfWidth,
                top() + shrinkBy - halfWidth,
                left() + shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                topBorderColour,
                VertexFormat.Mode.QUADS);

        //----------------------------------------
        // Bottom line
        //----------------------------------------
        internalDrawRect(matrixStack,
                left() + shrinkBy - halfWidth,
                bottom() - shrinkBy - halfWidth,
                right() - shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                bottomBorderColour,
                VertexFormat.Mode.QUADS);

        //----------------------------------------
        // Right line
        //----------------------------------------
        internalDrawRect(matrixStack,
                right() - shrinkBy - halfWidth,
                top() + shrinkBy - halfWidth,
                right() - shrinkBy + halfWidth,
                bottom() - shrinkBy + halfWidth,
                bottomBorderColour,
                VertexFormat.Mode.QUADS);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//        ShaderInstance oldShader = RenderSystem.getShader();
        drawBackground(matrixStack);
        if (topBorderColour.equals(bottomBorderColour)) {
            drawBorder(matrixStack, shrinkBoarderBy);
        } else {
            drawDualColourBorder(matrixStack, shrinkBoarderBy);
        }
//        RenderSystem.setShader(() -> oldShader);
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
        stringbuilder.append("Background Colour: ").append(backgroundColour).append("\n");
        stringbuilder.append("Top Border Colour: ").append(topBorderColour).append("\n");
        stringbuilder.append("Bottom Border Colour: ").append(bottomBorderColour).append("\n");
        return stringbuilder.toString();
    }
}