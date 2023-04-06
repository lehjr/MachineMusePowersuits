///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.numina.client.gui.geometry;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import org.joml.Matrix4f;
//import lehjr.numina.common.math.Color;
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import org.lwjgl.BufferUtils;
//import org.lwjgl.opengl.GL11;
//
//import java.nio.FloatBuffer;
//
//public class DrawableArrow extends Rect implements IDrawableRect {
//    Color backgroundColour;
//    Color borderColour;
//    boolean drawShaft = true;
//    ArrowDirection facing = ArrowDirection.RIGHT;
//    boolean shrinkBorder = true;
//    boolean drawBorer = true;
//
//    public DrawableArrow(float left, float top, float right, float bottom, boolean growFromMiddle,
//                         Color backgroundColour,
//                         Color borderColour) {
//        super(left, top, right, bottom, growFromMiddle);
//        this.backgroundColour = backgroundColour;
//        this.borderColour = borderColour;
//    }
//
//    public DrawableArrow(float left, float top, float right, float bottom,
//                         Color backgroundColour,
//                         Color borderColour) {
//        super(left, top, right, bottom, false);
//        this.backgroundColour = backgroundColour;
//        this.borderColour = borderColour;
//    }
//
//    public DrawableArrow(MusePoint2D ul, MusePoint2D br,
//                         Color backgroundColour,
//                         Color borderColour) {
//        super(ul, br);
//        this.backgroundColour = backgroundColour;
//        this.borderColour = borderColour;
//    }
//
//    /**
//     * determine if the border should be smaller than the background rectangle (like tooltips)
//     * @param shrinkBorder
//     */
//    public void setShrinkBorder(boolean shrinkBorder) {
//        this.shrinkBorder = shrinkBorder;
//    }
//
//    public void setBackgroundColour(Color colour) {
//        this.backgroundColour = colour;
//    }
//
//    public void setDirection(ArrowDirection facing) {
//        this.facing = facing;
//    }
//
//    public void setDrawShaft(boolean drawShaft) {
//        this.drawShaft = drawShaft;
//    }
//
//    public void setDrawBorer(boolean drawBorer) {
//        this.drawBorer = drawBorer;
//    }
//
//    /**
//     arrow vertices starting at tip and following a counter clockwise pattern :
//     ---------------------------------------------------------------------------
//
//     background with arrow shaft:
//     ABG - Draw
//     CDEF - Draw
//
//     background without arrow shaft
//     ABG - Draw
//
//     border with arrow shaft:
//     ABCDEFG - Draw
//
//     border without arrow shaft
//     ABG -Draw
//
//     */
//
//    /**
//     * Arrow tip
//     * @param shrinkBy
//     * @param buffer
//     * @return
//     */
//    FloatBuffer getVertexA(float shrinkBy, FloatBuffer buffer) {
//        switch (this.facing) {
//            case RIGHT:
//                buffer.put((float) (right() - shrinkBy));
//                buffer.put((float) centerY());
//                break;
//            case LEFT:
//                buffer.put((float) (left() + shrinkBy));
//                buffer.put((float) centerY());
//                break;
//            case UP:
//                buffer.put((float) centerX());
//                buffer.put((float) (top() + shrinkBy));
//                break;
//            case DOWN:
//                buffer.put((float) centerX());
//                buffer.put((float) (bottom() - shrinkBy));
//                break;
//        }
//        return buffer;
//    }
//
//    /**
//     * Arrow head part
//     * @param shrinkBy
//     * @param buffer
//     * @return
//     */
//    FloatBuffer getVertexB(float shrinkBy, FloatBuffer buffer) {
//        switch (this.facing) {
//            case RIGHT:
//                /** left top */
//                buffer.put((float) ((drawShaft ? (centerX() + (width() * 0.15F)) : left()) + shrinkBy * 0.5));
//                buffer.put((float) ((centerY() - (height() * 0.4F)) + (drawShaft ? shrinkBy * 2.5 : shrinkBy)));
//                break;
//            case LEFT:
//                /** bottom right */
//                buffer.put((float) ((drawShaft ? (float) (centerX() - (width() * 0.15)) : right()) - shrinkBy * 0.5));
//                buffer.put((float) (centerY() + (height() * 0.4F) - (drawShaft ? shrinkBy * 2.5 : shrinkBy)));
//                break;
//            case UP:
//                /** bottom left */
//                buffer.put((float) (centerX() - (width() * 0.4F) + (drawShaft ? shrinkBy * 2.5 : shrinkBy)));
//                buffer.put((float) ((drawShaft ? (centerY() - (height() * 0.15F)) : bottom()) - shrinkBy * 0.5));
//                break;
//            case DOWN:
//                /** top right */
//                buffer.put((float) (centerX() + (width() * 0.4F) - (drawShaft ? shrinkBy * 2.5 : shrinkBy)));
//                buffer.put((float) ((drawShaft ?  (centerY() + (height() * 0.15F)) : top()) + shrinkBy * 0.5));
//                break;
//        }
//        return buffer;
//    }
//
//    /**
//     * Arrow shaft part
//     * @param shrinkBy
//     * @param buffer
//     * @return
//     */
//    FloatBuffer getVertexC(float shrinkBy, FloatBuffer buffer) {
//        switch (this.facing) {
//            case RIGHT:
//                /** top right */
//                buffer.put((float) (centerX() + (width() * 0.15F) + shrinkBy));
//                buffer.put((float) (centerY() - (height() * 0.15F) + shrinkBy));
//                break;
//
//            case LEFT:
//                /** bottom left */
//                buffer.put((float) (centerX() - (width() * 0.15F) - shrinkBy));
//                buffer.put((float) (centerY() + (height()* 0.15F) - shrinkBy));
//                break;
//
//            case UP:
//                /** top left */
//                buffer.put((float) (centerX() - (width() * 0.15F) + shrinkBy));
//                buffer.put((float) (centerY() - (height()* 0.15F) - shrinkBy));
//                break;
//
//            case DOWN:
//                /** bottom right */
//                buffer.put((float) (centerX() + (width() * 0.15F) - shrinkBy));
//                buffer.put((float) (centerY() + (height()* 0.15F) + shrinkBy));
//                break;
//        }
//        return buffer;
//    }
//
//    /**
//     * Arrow shaft part
//     * @param shrinkBy
//     * @param buffer
//     * @return
//     */
//    FloatBuffer getVertexD(float shrinkBy, FloatBuffer buffer) {
//        switch (this.facing) {
//            case RIGHT:
//                /** top left */
//                buffer.put((float) (left() + shrinkBy));
//                buffer.put((float) (centerY() - (height()* 0.15F) + shrinkBy));
//                break;
//            case LEFT:
//                /** bottom right */
//                buffer.put((float) (right() - shrinkBy));
//                buffer.put((float) (centerY() + (height()* 0.15F) - shrinkBy));
//                break;
//            case UP:
//                /** bottom left */
//                buffer.put((float) (centerX() - width() * 0.15F + shrinkBy));
//                buffer.put((float) (bottom() - shrinkBy));
//                break;
//            case DOWN:
//                /** top right */
//                buffer.put((float) (centerX() + width()* 0.15F - shrinkBy));
//                buffer.put((float) (top() + shrinkBy));
//                break;
//        }
//        return buffer;
//    }
//
//    FloatBuffer getVertexE(float shrinkBy, FloatBuffer buffer) {
//        switch (this.facing) {
//            case RIGHT:
//                /** bottom left */
//                buffer.put((float) (left() + shrinkBy));
//                buffer.put((float) (centerY() + (height()* 0.15F) - shrinkBy));
//                break;
//            case LEFT:
//                /** top right */
//                buffer.put((float) (right() - shrinkBy));
//                buffer.put((float) (centerY() - (height() * 0.15F) + shrinkBy));
//                break;
//            case UP:
//                /** bottom right */
//                buffer.put((float) (centerX() + (width() * 0.15F) - shrinkBy));
//                buffer.put((float) (bottom() - shrinkBy));
//                break;
//            case DOWN:
//                /** top left */
//                buffer.put((float) (centerX() - width()* 0.15F + shrinkBy));
//                buffer.put((float) (top() + shrinkBy));
//                break;
//        }
//        return buffer;
//    }
//
//    /**
//     * Arrow shaft part
//     * @param shrinkBy
//     * @param buffer
//     * @return
//     */
//    FloatBuffer getVertexF(float shrinkBy, FloatBuffer buffer) {
//        switch (this.facing) {
//            case RIGHT:
//                /** bottom right */
//                buffer.put((float) (centerX() + (width() * 0.15F) + shrinkBy));
//                buffer.put((float) (centerY() + (height() * 0.15F) - shrinkBy));
//                break;
//            case LEFT:
//                /** top left */
//                buffer.put((float) (centerX() - (width() * 0.15F) - shrinkBy));
//                buffer.put((float) (centerY() - (height() * 0.15F) + shrinkBy));
//                break;
//            case UP:
//                /** top right */
//                buffer.put((float) (centerX() + width() * 0.15F - shrinkBy));
//                buffer.put((float) (centerY() - (height() * 0.15F) - shrinkBy));
//                break;
//            case DOWN:
//                /** bottom left */
//                buffer.put((float) (centerX() - width()* 0.15F + shrinkBy));
//                buffer.put((float) (centerY() + (height() * 0.15F) + shrinkBy));
//                break;
//        }
//        return buffer;
//    }
//
//    /**
//     * Arrow head part
//     * @param shrinkBy
//     * @param buffer
//     * @return
//     */
//    FloatBuffer getVertexG(float shrinkBy, FloatBuffer buffer) {
//        switch (this.facing) {
//            case RIGHT:
//                /** bottom left */
//                buffer.put((float) ((drawShaft ? (centerX() + (width() * 0.15F)) : left()) + shrinkBy * 0.5));
//                buffer.put((float) ((centerY() + (height() * 0.4F)) - (drawShaft ? shrinkBy * 2.5 : shrinkBy)));
//                break;
//            case LEFT:
//                /** top right */
//                buffer.put((float) ((drawShaft ? (float) (centerX() - (width() * 0.15)) : right()) - shrinkBy * 0.5));
//                buffer.put((float) (centerY() - (height() * 0.4F) + (drawShaft ? shrinkBy * 2.5 : shrinkBy)));
//                break;
//            case UP:
//                /** bottom right */
//                buffer.put((float) (centerX() + (width() * 0.4F) - (drawShaft ? shrinkBy * 2.5 : shrinkBy)));
//                buffer.put((float) ((drawShaft ? (centerY() - (height() * 0.15F)): bottom()) - shrinkBy * 0.5));
//                break;
//
//            case DOWN:
//                /** top left */
//                buffer.put((float) (centerX() - (width() * 0.4F) + (drawShaft ? shrinkBy * 2.5 : shrinkBy)));
//                buffer.put((float) ((drawShaft ? (centerY() + (height() * 0.15F)) : top()) + shrinkBy * 0.5));
//                break;
//        }
//        return buffer;
//    }
//
//    void drawBackground(PoseStack matrixStack) {
//        preDraw(GL11.GL_POLYGON, DefaultVertexFormat.POSITION_COLOR);
//        Matrix4f matrix4f  = matrixStack.last().pose();
//
//        // Arrow head
//        FloatBuffer vertices = BufferUtils.createFloatBuffer(3 /* points */ * 2 /* axis */);
//        getVertexA(0, vertices);
//        getVertexB(0, vertices);
//        getVertexG(0, vertices);
//        vertices.flip();
//        vertices.rewind();
//        addVerticesToBuffer(matrix4f, vertices, backgroundColour);
//        drawTesselator();
//
//        if (this.drawShaft) {
//            preDraw(GL11.GL_POLYGON, DefaultVertexFormat.POSITION_COLOR);
//            vertices = BufferUtils.createFloatBuffer(4 /* points */ * 2 /* axis */);
//            getVertexC(0, vertices);
//            getVertexD(0, vertices);
//            getVertexE(0, vertices);
//            getVertexF(0, vertices);
//            vertices.flip();
//            vertices.rewind();
//            addVerticesToBuffer(matrix4f, vertices, backgroundColour);
//            drawTesselator();
//        }
//        postDraw();
//    }
//
//    void drawBorder(PoseStack matrixStack) {
//        if (drawBorer) {
//            FloatBuffer vertices = BufferUtils.createFloatBuffer(6 + (drawShaft ? 8 : 0));
//            preDraw(GL11.GL_LINE_LOOP, DefaultVertexFormat.POSITION_COLOR);
//            Matrix4f matrix4f = matrixStack.last().pose();
//
//            getVertexA(shrinkBorder ? 2 : 0, vertices);
//            getVertexB(shrinkBorder ? 2 : 0, vertices);
//            if (drawShaft) {
//                getVertexC(shrinkBorder ? 2 : 0, vertices);
//                getVertexD(shrinkBorder ? 2 : 0, vertices);
//                getVertexE(shrinkBorder ? 2 : 0, vertices);
//                getVertexF(shrinkBorder ? 2 : 0, vertices);
//            }
//            getVertexG(shrinkBorder ? 2 : 0, vertices);
//
//            vertices.flip();
//            vertices.rewind();
//            addVerticesToBuffer(matrix4f, vertices, borderColour);
//            drawTesselator();
//
//            postDraw();
//        }
//    }
//
//    float zLevel;
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
//        drawBackground(matrixStack);
//        drawBorder(matrixStack);
//    }
//
//    public void drawBackground(PoseStack matrixStack, float zLevel) {
//        this.zLevel = zLevel;
//        drawBackground(matrixStack);
//    }
//
//    public void drawBorder(PoseStack matrixStack, float zLevel) {
//        this.zLevel = zLevel;
//        drawBorder(matrixStack);
//    }
//
//    public ArrowDirection getFacing() {
//        return facing;
//    }
//
//    @Override
//    public float getZLevel() {
//        return zLevel;
//    }
//
//    @Override
//    public IDrawable setZLevel(float zLevelIn) {
//        this.zLevel = zLevelIn;
//        return this;
//    }
//
//    public enum ArrowDirection {
//        UP(270),
//        DOWN(90),
//        LEFT(180),
//        RIGHT(0);
//
//        int rotation;
//
//        ArrowDirection(int rotation) {
//            this.rotation = rotation;
//        }
//
//        public int getRotation() {
//            return this.rotation;
//        }
//    }
//}