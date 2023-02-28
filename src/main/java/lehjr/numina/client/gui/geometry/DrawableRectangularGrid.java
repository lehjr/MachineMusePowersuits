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
//package lehjr.numina.client.gui.gemoetry;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.systems.RenderSystem;
//import lehjr.numina.common.math.Colour;
//import com.mojang.blaze3d.vertex.BufferBuilder;
//import com.mojang.blaze3d.vertex.Tesselator;
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import com.mojang.math.Matrix4f;
//import org.lwjgl.opengl.GL11;
//
//import java.nio.FloatBuffer;
//
//public class DrawableRectangularGrid extends DrawableRect {
//    public float zLevel = 0;
//
//    Colour gridColour;
//    int gridHeight;
//    int gridWidth;
//    Double horizontalSegmentSize;
//    Double verticleSegmentSize;
//    final Rect[] boxes;
//
//    public DrawableRectangularGrid(double left, double top, double right, double bottom, boolean growFromMiddle,
//                                   Colour insideColour,
//                                   Colour outsideColour,
//                                   Colour gridColour,
//                                   int gridHeight,
//                                   int gridWidth) {
//        super(left, top, right, bottom, growFromMiddle, insideColour, outsideColour);
//        this.gridColour = gridColour;
//        this.gridHeight = gridHeight;
//        this.gridWidth = gridWidth;
//        this.boxes = new Rect[gridHeight*gridWidth];
//        setBoxes();
//    }
//
//    public DrawableRectangularGrid(double left, double top, double right, double bottom,
//                                   Colour insideColour,
//                                   Colour outsideColour,
//                                   Colour gridColour,
//                                   int gridHeight,
//                                   int gridWidth) {
//        super(left, top, right, bottom, false, insideColour, outsideColour);
//        this.gridColour = gridColour;
//        this.gridHeight = gridHeight;
//        this.gridWidth = gridWidth;
//        this.boxes = new Rect[gridHeight*gridWidth];
//        setBoxes();
//    }
//
//    public DrawableRectangularGrid(MusePoint2D ul, MusePoint2D br,
//                                   Colour insideColour,
//                                   Colour outsideColour,
//                                   Colour gridColour,
//                                   int gridHeight,
//                                   int gridWidth) {
//        super(ul, br, insideColour, outsideColour);
//        this.gridColour = gridColour;
//        this.gridHeight = gridHeight;
//        this.gridWidth = gridWidth;
//        this.boxes = new Rect[gridHeight*gridWidth];
//        setBoxes();
//    }
//
//    void setBoxes() {
//        for (int i = 0; i < boxes.length; i++) {
//            boxes[i] = new Rect(0, 0, 0, 0);
//        }
//    }
//
//
//    public Rect[] getBoxes() {
//        return boxes;
//    }
//
//    void setupGrid() {
//        horizontalSegmentSize = (double) (width() / gridWidth);
//        verticleSegmentSize = (double) (height() / gridHeight);
//        int i = 0;
//
//        // These boxes provide centers for the slots
//        for (int y = 0; y < gridHeight; y++) {
//            for (int x = 0; x < gridWidth; x++) {
//                boxes[i].setLeft(horizontalSegmentSize * x);
//                boxes[i].setTop(verticleSegmentSize * y);
//                boxes[i].setWidth(horizontalSegmentSize);
//                boxes[i].setHeight(verticleSegmentSize);
//
//                if (i >0) {
//                    if (x > 0)
//                        boxes[i].setRightOf(boxes[i-1]);
//                    if (y > 0){
//                        boxes[i].setBelow(boxes[i-gridWidth]);
//                    }
//                }
//                i++;
//            }
//        }
//    }
//
//    void drawGrid(PoseStack matrixStack) {
//
//        // reinitialize values on "growFromCenter" or resize
//        boolean needInt = false;
//        for (int i = 0; i < boxes.length; i++) {
//            if (boxes[i] == null) {
//                needInt = true;
//                break;
//            }
//        }
//
//        if (needInt) {
//            setBoxes();
//        }
//
//        if (horizontalSegmentSize == null || verticleSegmentSize == null /*|| (!doneGrowing())*/) {
//            setupGrid();
//        }
//
//        RenderSystem.disableTexture();
//        RenderSystem.enableBlend();
//        RenderSystem.disableAlphaTest();
//        RenderSystem.defaultBlendFunc();
//        GL11.glEnable(GL11.GL_SMOOTH);
//
//        Tesselator tessellator = Tesselator.getInstance();
//        BufferBuilder buffer = tessellator.getBuilder();
//        buffer.begin(GL11.GL_LINES, DefaultVertexFormat.POSITION_COLOR);
//
//        Matrix4f matrix4f = matrixStack.last().pose();
//
//        // Horizontal lines
//        if (gridHeight >1) {
//            for (double y = (double) (verticleSegmentSize + top()); y < bottom(); y += verticleSegmentSize) {
//                buffer.vertex(matrix4f, (float)left(), (float) y, zLevel).color(gridColour.r, gridColour.g, gridColour.b, gridColour.a).endVertex();
//                buffer.vertex(matrix4f, (float)right(), (float) y, zLevel).color(gridColour.r, gridColour.g, gridColour.b, gridColour.a).endVertex();
//            }
//        }
//
//        // Vertical lines
//        if(gridWidth > 1) {
//            for (double x = (double) (horizontalSegmentSize + left()); x < right(); x += horizontalSegmentSize) {
//                buffer.vertex(matrix4f, (float) x, (float) top(), zLevel).color(gridColour.r, gridColour.g, gridColour.b, gridColour.a).endVertex();
//                buffer.vertex(matrix4f, (float) x, (float) bottom(), zLevel).color(gridColour.r, gridColour.g, gridColour.b, gridColour.a).endVertex();
//            }
//        }
//
//        tessellator.end();
//
//        GL11.glEnable(GL11.GL_FLAT);
//        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
//        RenderSystem.enableTexture();
//    }
//
//    @Override
//    public DrawableRect setLeft(double value) {
//        double diff = value - left();
//        super.setLeft(value);
//        for (Rect box : boxes) {
//            if (box != null) {
//                box.setLeft(box.left() + diff);
//            }
//        }
//        return this;
//    }
//
//    @Override
//    public void render(PoseStack matrixStack, int mouseX, int mouseY, float frameTime) {
//        FloatBuffer vertices = preDraw(0);
//        drawBackground(matrixStack, vertices);
//        drawGrid(matrixStack);
//        drawBorder(matrixStack, vertices);
//    }
//}