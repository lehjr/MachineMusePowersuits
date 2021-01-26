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
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class DrawableArrow extends MuseRelativeRect {
    Colour backgroundColour;
    Colour borderColour;
    boolean drawShaft = true;
    ArrowDirection facing = ArrowDirection.RIGHT;
    boolean shrinkBorder = true;

    public DrawableArrow(float left, float top, float right, float bottom, boolean growFromMiddle,
                         Colour backgroundColour,
                         Colour borderColour) {
        super(left, top, right, bottom, growFromMiddle);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    public DrawableArrow(float left, float top, float right, float bottom,
                         Colour backgroundColour,
                         Colour borderColour) {
        super(left, top, right, bottom, false);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    public DrawableArrow(MusePoint2D ul, MusePoint2D br,
                         Colour backgroundColour,
                         Colour borderColour) {
        super(ul, br);
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    public DrawableArrow(MuseRelativeRect ref,
                         Colour backgroundColour,
                         Colour borderColour) {
        super(ref.left(), ref.top(), ref.right(), ref.bottom(), ref.growFromMiddle());
        this.backgroundColour = backgroundColour;
        this.borderColour = borderColour;
    }

    /**
     * determine if the border should be smaller than the background rectangle (like tooltips)
     * @param shrinkBorder
     */
    public void setShrinkBorder(boolean shrinkBorder) {
        this.shrinkBorder = shrinkBorder;
    }

    public void setBackgroundColour(Colour colour) {
        this.backgroundColour = colour;
    }

    public void setDirection(ArrowDirection facing) {
        this.facing = facing;
    }

    public void setDrawShaft(boolean drawShaft) {
        this.drawShaft = drawShaft;
    }

    FloatBuffer arrowHeadVertices(float shrinkBy) {
        /**
         * ( 3 vertices X 2 axis ) because the zLevel is supplied elsewhere.
         */
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3 /* points */ * 2 /* axis */);

        switch (this.facing) {
            case RIGHT:
                /** left top */
                buffer.put((float) ((drawShaft ? (centerx() + (width() * 0.15F)) : left()) + shrinkBy));
                buffer.put((float) ((centery() - (height() * 0.4F)) + shrinkBy));

                /** tip */
                buffer.put((float) (right() - shrinkBy));
                buffer.put((float) centery());

                /** left bottom */
                buffer.put((float) ((drawShaft ? (centerx() + (width() * 0.15F)) : left()) + shrinkBy));
                buffer.put((float) ((centery() + (height() * 0.4F)) - shrinkBy));
                break;

            case DOWN:
                /** top right */
                buffer.put((float) (centerx() + (width() * 0.4F) - shrinkBy));
                buffer.put((float) ((drawShaft ?  (centery() + (height() * 0.15F)) : top()) + shrinkBy));

                /** tip */
                buffer.put((float) centerx());
                buffer.put((float) (bottom() - shrinkBy));

                /** top left */
                buffer.put((float) (centerx() - (width() * 0.4F) + shrinkBy));
                buffer.put((float) ((drawShaft ? (centery() + (height() * 0.15F)) : top()) + shrinkBy));
                break;

            case LEFT:
                /** bottom right */
                buffer.put((float) ((drawShaft ? (float) (centerx() - (width() * 0.15)) : right()) + shrinkBy));
                buffer.put((float) (centery() + (height() * 0.4F) - shrinkBy));

                /** tip */
                buffer.put((float) (left() - shrinkBy));
                buffer.put((float) centery());

                /** top right */
                buffer.put((float) ((drawShaft ? (float) (centerx() - (width() * 0.15)) : right()) + shrinkBy));
                buffer.put((float) (centery() - (height() * 0.4F) + shrinkBy));
                break;
            case UP:
                /** bottom left */
                buffer.put((float) (centerx() - (width() * 0.4F) + shrinkBy));
                buffer.put((float) ((drawShaft ? (centery() - (height() * 0.15F)) : bottom()) - shrinkBy));

                /** point */
                buffer.put((float) centerx());
                buffer.put((float) (top() + shrinkBy));

                /** bottom right */
                buffer.put((float) (centerx() + (width() * 0.4F) - shrinkBy));
                buffer.put((float) ((drawShaft ? (centery() - (height() * 0.15F)): bottom()) - shrinkBy));
                break;
        }
        buffer.flip();
        return buffer;
    }

    FloatBuffer arrowShaftVerticesPt1(float shrinkBy) {
        /**
         * (2 vertices X 2 axis ) because the zLevel is supplied elsewhere.
         */
        FloatBuffer buffer = BufferUtils.createFloatBuffer(2 /* points */ * 2 /* axis */);

        switch (this.facing) {
            case RIGHT:
                /** top left */
                buffer.put((float) (left() + shrinkBy));
                buffer.put((float) (centery() - (height()* 0.15F) + shrinkBy));

                /** top right */
                buffer.put((float) (centerx() + (width() * 0.15F) + shrinkBy));
                buffer.put((float) (centery() - (height() * 0.15F) + shrinkBy));
                break;
            case DOWN:
                /** top right */
                buffer.put((float) (centerx() + width()* 0.15F - shrinkBy));
                buffer.put((float) (top() + shrinkBy));

                /** bottom right */
                buffer.put((float) (centerx() + (width() * 0.15F) - shrinkBy));
                buffer.put((float) (centery() + (height()* 0.15F) + shrinkBy));
                break;

            case LEFT:
                /** bottom right */
                buffer.put((float) (right() - shrinkBy));
                buffer.put((float) (centery() + (height()* 0.15F) - shrinkBy));

                /** bottom left */
                buffer.put((float) (centerx() - (width() * 0.15F) + shrinkBy));
                buffer.put((float) (centery() + (height()* 0.15F) - shrinkBy));
                break;

            case UP:
                /** bottom left */
                buffer.put((float) (centerx() - width() * 0.15F + shrinkBy));
                buffer.put((float) (bottom() - shrinkBy));

                /** top left */
                buffer.put((float) (centerx() - (width() * 0.15F) + shrinkBy));
                buffer.put((float) (centery() - (height()* 0.15F) - shrinkBy));
                break;
        }
        buffer.flip();
        return buffer;
    }

    FloatBuffer arrowShaftVerticesPt2(float shrinkBy) {
        /**
         * (2 vertices X 2 axis ) because the zLevel is supplied elsewhere.
         */
        FloatBuffer buffer = BufferUtils.createFloatBuffer(2 /* points */ * 2 /* axis */);

        switch (this.facing) {
            case RIGHT:
                /** bottom right */
                buffer.put((float) (centerx() + (width() * 0.15F) + shrinkBy));
                buffer.put((float) (centery() + (height() * 0.15F) - shrinkBy));

                /** bottom left */
                buffer.put((float) (left() + shrinkBy));
                buffer.put((float) (centery() + (height()* 0.15F) - shrinkBy));
                break;

            case DOWN:
                /** bottom left */
                buffer.put((float) (centerx() - width()* 0.15F + shrinkBy));
                buffer.put((float) (centery() + (height()* 0.15F) + shrinkBy));

                /** top left */
                buffer.put((float) (centerx() - width()* 0.15F + shrinkBy));
                buffer.put((float) (top() + shrinkBy));
                break;

            case LEFT:
                /** top left */
                buffer.put((float) (centerx() - (width() * 0.15F) + shrinkBy));
                buffer.put((float) (centery() - (height()* 0.15F) + shrinkBy));

                /** top right */
                buffer.put((float) (right() - shrinkBy));
                buffer.put((float) (centery() - (height()* 0.15F) + shrinkBy));
                break;

            case UP:
                /** top right */
                buffer.put((float) (centerx() + width()* 0.15F - shrinkBy));
                buffer.put((float) (centery() - (height()* 0.15F) - shrinkBy));

                /** bottom right */
                buffer.put((float) (centerx() + (width() * 0.15F) - shrinkBy));
                buffer.put((float) (bottom() - shrinkBy));
                break;
        }
        buffer.flip();
        return buffer;
    }

    void drawBackground(MatrixStack matrixStack) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);

        FloatBuffer vertices = arrowHeadVertices(0);
        while (vertices.hasRemaining()) {
            buffer.pos(matrixStack.getLast().getMatrix(), vertices.get(), vertices.get(), zLevel).color(backgroundColour.r, backgroundColour.g, backgroundColour.b, backgroundColour.a).endVertex();
        }
        tessellator.draw();

        if (drawShaft) {
            buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);

            vertices = arrowShaftVerticesPt1(0);
            while (vertices.hasRemaining()) {
                buffer.pos(matrixStack.getLast().getMatrix(), vertices.get(), vertices.get(), zLevel).color(backgroundColour.r, backgroundColour.g, backgroundColour.b, backgroundColour.a).endVertex();
            }

            vertices = arrowShaftVerticesPt2( 0);
            while (vertices.hasRemaining()) {
                buffer.pos(matrixStack.getLast().getMatrix(), vertices.get(), vertices.get(), zLevel).color(backgroundColour.r, backgroundColour.g, backgroundColour.b, backgroundColour.a).endVertex();
            }
            tessellator.draw();
        }

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    void drawBorder(MatrixStack matrixStack) {
        FloatBuffer vertices = BufferUtils.createFloatBuffer(6 + (drawShaft ? 8 : 0));
        if (drawShaft) {
            vertices.put(arrowShaftVerticesPt1(shrinkBorder ? 1 : 0));
        }

        vertices.put(arrowHeadVertices(shrinkBorder ? 1 : 0));

        if (drawShaft) {
            vertices.put(arrowShaftVerticesPt2(shrinkBorder ? 1 : 0));
        }
        vertices.rewind();

        FloatBuffer borderColours = GradientAndArcCalculator.getColourGradient(borderColour,
                borderColour.withAlpha(1), vertices.limit());

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);

        while (vertices.hasRemaining() && borderColours.hasRemaining()) {
            buffer.pos(matrixStack.getLast().getMatrix(), vertices.get(), vertices.get(), zLevel).color(borderColours.get(), borderColours.get(), borderColours.get(), borderColours.get()).endVertex();
        }

        tessellator.draw();
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    float zLevel;
    public void draw(MatrixStack matrixStack, float zLevel) {
        this.zLevel = zLevel;
        drawBackground(matrixStack);
        drawBorder(matrixStack);
    }

    public ArrowDirection getFacing(Matrix4f matrix4f) {
        return facing;
    }

    public enum ArrowDirection {
        UP(270),
        DOWN(90),
        LEFT(180),
        RIGHT(0);

        int rotation;

        ArrowDirection(int rotation) {
            this.rotation = rotation;
        }

        public int getRotation() {
            return this.rotation;
        }
    }
}