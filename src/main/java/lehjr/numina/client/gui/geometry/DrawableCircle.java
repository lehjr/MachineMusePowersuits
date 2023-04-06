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
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import org.joml.Matrix4f;
//import lehjr.numina.client.render.NuminaRenderState;
//import lehjr.numina.common.base.NuminaLogger;
//import lehjr.numina.common.math.Color;
//import net.minecraft.client.renderer.RenderType;
//import org.lwjgl.BufferUtils;
//import org.lwjgl.opengl.GL11;
//
//import java.nio.FloatBuffer;
//
//public class DrawableCircle<LIGHTMAP_ENABLED> {
//    protected static final RenderState.WriteMaskState COLOR_WRITE = new RenderState.WriteMaskState(true, false);
//
//
//    protected static final RenderState.TransparencyState NO_TRANSPARENCY = new RenderState.TransparencyState("no_transparency", () -> {
//        RenderSystem.disableBlend();
//    }, () -> {
//    });
//
//    // does "something" (makes the outter circles share more color but not as transparent as it needs to bne
//    protected static final RenderState.TransparencyState ADDITIVE_TRANSPARENCY = new RenderState.TransparencyState("additive_transparency", () -> {
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
//    }, () -> {
//        RenderSystem.disableBlend();
//        RenderSystem.defaultBlendFunc();
//    });
//
//    // better but lightning looks like it is rendering behind the plasma not in it
//    protected static final RenderState.TransparencyState LIGHTNING_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
//    }, () -> {
//        RenderSystem.disableBlend();
//        RenderSystem.defaultBlendFunc();
//    });
//    protected static final RenderState.TransparencyState GLINT_TRANSPARENCY = new RenderState.TransparencyState("glint_transparency", () -> {
//        RenderSystem.enableBlend();
//        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
//    }, () -> {
//        RenderSystem.disableBlend();
//        RenderSystem.defaultBlendFunc();
//    });
//    protected static final RenderState.TransparencyState CRUMBLING_TRANSPARENCY = new RenderState.TransparencyState("crumbling_transparency", () -> {
//        RenderSystem.enableBlend();
//        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//    }, () -> {
//        RenderSystem.disableBlend();
//        RenderSystem.defaultBlendFunc();
//    });
//    protected static final RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY = new RenderState.TransparencyState("translucent_transparency", () -> {
//        RenderSystem.enableBlend();
//        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//    }, () -> {
//        RenderSystem.disableBlend();
//        RenderSystem.defaultBlendFunc();
//    });
//
//
//
//
//
//
//
//
//
//
//
//
//    protected static final RenderState.ShadeModelState SHADE_DISABLED = new RenderState.ShadeModelState(false);
//    public static final RenderType PLASMA_BALL = func_228633_a_(
//            "plasma_ball",
//            DefaultVertexFormat.POSITION_COLOR_LIGHTMAP,
//            GL11.GL_TRIANGLE_STRIP, // works
//
//            256,
//            false,
//            false,
//            RenderType.State.func_228694_a_()
//                    .func_228727_a_(COLOR_WRITE)
//                    .func_228723_a_(SHADE_DISABLED)
//                    .func_228726_a_(ADDITIVE_TRANSPARENCY)
////                    .transparency(GLINT_TRANSPARENCY  )
//
////                    .transparency(NuminaRenderState.TRANSLUCENT_TRANSPARENCY)
//                    .func_228716_a_(NuminaRenderState.DIFFUSE_LIGHTING_ENABLED)
////                    .alpha(NuminaRenderState.DEFAULT_ALPHA)
//                    .func_228719_a_(NuminaRenderState.LIGHTMAP_ENABLED)
////                    .overlay(NuminaRenderState.OVERLAY_ENABLED)
//                    .func_228728_a_(true));
//
//    public static final float detail = 4;
//    protected static FloatBuffer points;
//    protected final FloatBuffer colour;
//
//    public DrawableCircle(Color c1, Color c2) {
//        FloatBuffer colourPoints;
//        if (points == null) {
//            colourPoints = GradientAndArcCalculator.getArcPoints(0, (float)(Math.PI * 2 + 0.0001), detail, 0F, 0F, 0F);
//            points = BufferUtils.createFloatBuffer(colourPoints.limit() + 6);
//            points.put(new float[]{0, 0, 0});
//            points.put(colourPoints);
//            colourPoints.rewind();
//            points.put(colourPoints.get());
//            points.put(colourPoints.get());
//            points.put(colourPoints.get());
//            points.flip();
//        }
//        colourPoints = GradientAndArcCalculator.getColourGradient(c1, c1, points.limit() / 3);
//        colour = BufferUtils.createFloatBuffer(colourPoints.limit() + 4); // space for rgba of c2
//        colour.put(c2.asArray());
//        colour.put(colourPoints);
//        colour.flip();
//    }
//
//    public void draw(PoseStack matrixStack, double radius, double x, double y, float zLevel) {
//        float ratio = (System.currentTimeMillis() % 2000) / 2000.0F;
//        colour.rewind();
//        points.rewind();
//        RenderSystem.pushPose();
//        RenderSystem.translated(x, y, 0);
//        RenderSystem.scaled(radius / detail, radius / detail, 1.0);
//        RenderSystem.rotatef((float) (-ratio * 360.0), 0, 0, 1);
//
//        RenderSystem.disableTexture();
//        RenderSystem.enableBlend();
//        RenderSystem.disableAlphaTest();
//        RenderSystem.defaultBlendFunc();
//
//        Tesselator tessellator = Tesselator.getInstance();
//        BufferBuilder buffer = tessellator.getBuilder();
//        buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR_LIGHTMAP);
//        Matrix4f matrix4f = matrixStack.last().pose();
//
//        while (points.hasRemaining() && colour.hasRemaining()) {
//            buffer.vertex(matrix4f, points.get(), points.get(), points.get())
//                    .color(colour.get(), colour.get(), colour.get(), colour.get())
//                    .uv2(0x00F000F0)
//                    .endVertex();
//        }
//        tessellator.end();
//
//        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
//        RenderSystem.enableTexture();
//
//        RenderSystem.popMatrix();
//
////        /**
////         * Specifies the location and organization of a color array.
////         *
////         * @param size    the number of values per vertex that are stored in the array, as well as their component ordering. One of:<br><table><tr><td>3</td><td>4</td><td>{@link GL12#GL_BGRA BGRA}</td></tr></table>
////         * @param type    the data type of the values stored in the array. One of:<br><table><tr><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_SHORT SHORT}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_INT INT}</td><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td></tr><tr><td>{@link #GL_FLOAT FLOAT}</td><td>{@link #GL_DOUBLE DOUBLE}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td><td>{@link GL33#GL_INT_2_10_10_10_REV INT_2_10_10_10_REV}</td></tr></table>
////         * @param stride  the vertex stride in bytes. If specified as zero, then array elements are stored sequentially
////         * @param pointer the color array data
////         *
////         * @see <a target="_blank" href="http://docs.gl/gl3/glColorPointer">Reference Page</a> - <em>This function is deprecated and unavailable in the Core profile</em>
////         */
////        RenderSystem.assertThread(RenderSystem::isOnGameThread);
////        testDraw(radius, x, y);
//
//    }
//
//    void testDraw(double radius, double x, double y) {
//        points.rewind();
//        colour.rewind();
//        GL11.glPushMatrix();
//        GL11.glTranslated(x, y, 0);
//        GL11.glScaled(radius / detail, radius / detail, 1.0);
//        NuminaRenderState.on2D();
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        NuminaRenderState.arraysOnColor();
//        NuminaRenderState.texturelessOn();
//        NuminaRenderState.blendingOn();
//        GL11.glColorPointer(4, GL11.GL_FLOAT, 0, colour);
//        GL11.glVertexPointer(3, GL11.GL_FLOAT,0, points);
//        GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, points.limit() / 3);
//        NuminaRenderState.blendingOff();
//        NuminaRenderState.texturelessOff();
//        NuminaRenderState.arraysOff();
//        NuminaRenderState.off2D();
//        GL11.glPopMatrix();
//    }
//
//
//    public void draw(PoseStack matrixStackIn, MultiBufferSource bufferIn, float radius, float x, float y, float z/*,  int packedLightIn*/) {
//        drawSphere(matrixStackIn, bufferIn, x, y, y, radius, 1, 1);
//
//
//        //        float ratio = (System.currentTimeMillis() % 2000) / 2000.0F;
////        colour.rewind();
////        points.rewind();
////
////        matrixStackIn.push();
////        matrixStackIn.translate(x, y, z);
////        matrixStackIn.scale(radius / detail, radius / detail, 1.0F);
////        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-ratio * 360.0f));
////
////        VertexConsumer vertBuffer = bufferIn.getBuffer(PLASMA_BALL);
////        Matrix4f matrix4f = matrixStackIn.last().pose();
////
////        while (points.hasRemaining() && colour.hasRemaining()) {
////            vertBuffer.pos(matrix4f, points.get(), points.get(), points.get())
////                    .color(colour.get(), colour.get(), colour.get(), colour.get())
////                    .uv2(0x00F000F0)
////                    .endVertex();
////        }
////        matrixStackIn.pop();
//    }
//
//
//    /**
//     *
//     * @param radius The radius of the sphere.
//     * @param stacks The number of subdivisions around the Z axis (similar to lines of longitude).
//     * @param slices The number of subdivisions along the Z axis (similar to lines of latitude).
//     */
//    public void drawSphere(PoseStack matrixStackIn, MultiBufferSource bufferIn,
//                                  float x, float y, float z,
//                                  float radius, int stacks, int slices) {
//        Color colourTest = Color.MAGENTA.withAlpha(0.3F);
//
//        float r0, r1, alpha0, alpha1, x0, x1, y0, y1, z0, z1, beta;
//        float stackStep = (float) (Math.PI / stacks);
//        float sliceStep = (float) (Math.PI / slices);
//
//        matrixStackIn.pushPose();
//        matrixStackIn.translate(x, y, z);
//        VertexConsumer vertBuffer = bufferIn.getBuffer(PLASMA_BALL);
//        Matrix4f matrix4f = matrixStackIn.last().pose();
//
//        int vertices = 0;
//
//        int numVertices = slices * (slices << 1) * 6;
//
//        for (int i = 0; i < stacks; ++i) {
//            alpha0 = (float) (-Math.PI / 2 + i * stackStep);
//            alpha1 = alpha0 + stackStep;
//            r0 = (float) (radius * Math.cos(alpha0));
//            r1 = (float) (radius * Math.cos(alpha1));
//
//            y0 = (float) (radius * Math.sin(alpha0));
//            y1 = (float) (radius * Math.sin(alpha1));
//
//            for (int j = 0; j < (slices << 1); ++j) {
//                beta = j * sliceStep;//  w ww.java2s.com
//                x0 = (float) (r0 * Math.cos(beta));
//                x1 = (float) (r1 * Math.cos(beta));
//
//                z0 = (float) (-r0 * Math.sin(beta));
//                z1 = (float) (-r1 * Math.sin(beta));
//
//                vertBuffer.vertex(matrix4f, x0, y0, z0)
//                        .color(colourTest.r, colourTest.g, colourTest.b, colourTest.a)
//                        .uv2(0x00F000F0)
//                        .endVertex();
//
//                vertBuffer.vertex(matrix4f, x1, y1, z1)
//                        .color(colourTest.r, colourTest.g, colourTest.b, colourTest.a)
//                        .uv2(0x00F000F0)
//                        .endVertex();
//
//                vertices +=6;
//
//                NuminaLogger.logDebug("j: " + j);
//            }
//        }
//        matrixStackIn.popPose();
//        NuminaLogger.logDebug("vertices: " + vertices);
//        NuminaLogger.logDebug("numVertices: " + numVertices);
//    }
//}