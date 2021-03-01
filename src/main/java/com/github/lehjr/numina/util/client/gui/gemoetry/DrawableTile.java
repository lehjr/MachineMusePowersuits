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
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class DrawableTile extends MuseRelativeRect {
    Colour topBorderColour = new Colour(0.216F, 0.216F, 0.216F, 1F);
    Colour bottomBorderColour = Colour.WHITE;
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

    void draw(MatrixStack matrix4f, Colour colour, int glMode, double shrinkBy) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(glMode, DefaultVertexFormats.POSITION_COLOR);

        buffer.pos(matrix4f.getLast().getMatrix(), (float)(right() - shrinkBy), (float) (top() + shrinkBy), zLevel).color(colour.r, colour.g, colour.b, colour.a).endVertex();
        buffer.pos(matrix4f.getLast().getMatrix(),(float)(left() + shrinkBy), (float) (top() + shrinkBy), zLevel).color(colour.r, colour.g, colour.b, colour.a).endVertex();
        buffer.pos(matrix4f.getLast().getMatrix(),(float)(left() + shrinkBy), (float) (bottom() - shrinkBy), zLevel).color(colour.r, colour.g, colour.b, colour.a).endVertex();
        buffer.pos(matrix4f.getLast().getMatrix(),(float)(right() - shrinkBy), (float) (bottom() - shrinkBy), zLevel).color(colour.r, colour.g, colour.b, colour.a).endVertex();
        tessellator.draw();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    public void drawBackground(MatrixStack matrixStack) {
        draw(matrixStack, backgroundColour, GL11.GL_QUADS, 0);
    }

    public void drawBorder(MatrixStack matrixStack, double shrinkBy) {
        draw(matrixStack, topBorderColour, GL11.GL_LINE_LOOP, shrinkBy);
    }

    public void drawDualColourBorder(MatrixStack matrixStack, float shrinkBy) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

        RenderSystem.lineWidth(2.0F);

        // top right -> top left
        buffer.pos(matrixStack.getLast().getMatrix(), (float)(right() - shrinkBy), (float) (top() + shrinkBy), zLevel).color(topBorderColour.r, topBorderColour.g, topBorderColour.b, topBorderColour.a).endVertex();
        buffer.pos(matrixStack.getLast().getMatrix(),(float)(left() + shrinkBy), (float) (top() + shrinkBy), zLevel).color(topBorderColour.r, topBorderColour.g, topBorderColour.b, topBorderColour.a).endVertex();

        // top left -> bottom left
        buffer.pos(matrixStack.getLast().getMatrix(),(float)(left() + shrinkBy), (float) (top() + shrinkBy), zLevel).color(topBorderColour.r, topBorderColour.g, topBorderColour.b, topBorderColour.a).endVertex();
        buffer.pos(matrixStack.getLast().getMatrix(),(float)(left() + shrinkBy), (float) (bottom() - shrinkBy), zLevel).color(topBorderColour.r, topBorderColour.g, topBorderColour.b, topBorderColour.a).endVertex();

        // bottom left -> bottom right
        buffer.pos(matrixStack.getLast().getMatrix(),(float)(left() + shrinkBy), (float) (bottom() - shrinkBy), zLevel).color(bottomBorderColour.r, bottomBorderColour.g, bottomBorderColour.b, bottomBorderColour.a).endVertex();
        buffer.pos(matrixStack.getLast().getMatrix(),(float)(right() - shrinkBy), (float) (bottom() - shrinkBy), zLevel).color(bottomBorderColour.r, bottomBorderColour.g, bottomBorderColour.b, bottomBorderColour.a).endVertex();

        // bottom right -> top right
        buffer.pos(matrixStack.getLast().getMatrix(),(float)(right() - shrinkBy), (float) (bottom() - shrinkBy), zLevel).color(bottomBorderColour.r, bottomBorderColour.g, bottomBorderColour.b, bottomBorderColour.a).endVertex();
        buffer.pos(matrixStack.getLast().getMatrix(), (float)(right() - shrinkBy), (float) (top() + shrinkBy), zLevel).color(bottomBorderColour.r, bottomBorderColour.g, bottomBorderColour.b, bottomBorderColour.a).endVertex();

        tessellator.draw();

        RenderSystem.lineWidth(1);
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    public void draw(MatrixStack matrixStack, float zLevel) {
        this.zLevel = zLevel;
        drawBackground(matrixStack);
        if (topBorderColour == bottomBorderColour) {
            drawBorder(matrixStack, shrinkBoarderBy);
        } else {
            drawDualColourBorder(matrixStack, shrinkBoarderBy);
        }
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