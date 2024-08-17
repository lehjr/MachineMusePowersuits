package com.lehjr.numina.client.gui.geometry;

import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

public interface IDrawable extends Renderable {
    /**
     * @param gfx
     * @param mouseX
     * @param mouseY
     * @param partialTick
     */
    @Override
    void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick);

    float getZLevel();

    IDrawable setZLevel(float zLevel);

    /**
     * Common code for adding vertices to the BufferBuilder
     * @param matrix4f
     * @param vertices
     * @param color a Color to draw in
     */
    default void addVerticesToBuffer(BufferBuilder bufferbuilder, Matrix4f matrix4f, FloatBuffer vertices, Color color) {
        vertices.rewind();
        while(vertices.hasRemaining()) {
            bufferbuilder.addVertex(matrix4f, vertices.get(), vertices.get(), getZLevel()).setColor(color.r, color.g, color.b, color.a);
        }
    }

//    /**
//     * Common code for adding vertices to the BufferBuilder
//     * @param matrix4f
//     * @param vertices
//     * @param color a Color to draw in
//     */
//    default void addVerticesToBuffer(Matrix4f matrix4f, FloatBuffer vertices, Color color) {
//        vertices.rewind();
//        while(vertices.hasRemaining()) {
//            getTesselator().addVertex(matrix4f, vertices.get(), vertices.get(), getZLevel()).setColor(color.r, color.g, color.b, color.a);
//        }
//    }

    /**
     * Common code for adding vertices to the BufferBuilder
     * @param matrix4f
     * @param vertices
     * @param colorBuffer FloatBuffer of colors
     */
    default void addVerticesToBuffer(BufferBuilder bufferbuilder, Matrix4f matrix4f, FloatBuffer vertices, FloatBuffer colorBuffer) {
        vertices.rewind();
        colorBuffer.rewind();
        while(vertices.hasRemaining() && colorBuffer.hasRemaining()) {
            bufferbuilder.addVertex(matrix4f, vertices.get(), vertices.get(), getZLevel()).setColor(colorBuffer.get(), colorBuffer.get(), colorBuffer.get(), colorBuffer.get());
        }
    }
    default Tesselator getTesselator() {
        return Tesselator.getInstance();
    }

    default BufferBuilder preDraw(VertexFormat.Mode mode, VertexFormat format) {
//        RenderSystem.disableTexture();
//        RenderSystem.enableBlend();
//        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//
        RenderSystem.enableBlend();
//        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        return getTesselator().begin(mode, format);
    }

    default void postDraw(BufferBuilder builder) {
//        RenderSystem.disableBlend();
////        RenderSystem.enableAlphaTest();
//        RenderSystem.enableTexture();

        BufferUploader.drawWithShader(builder.buildOrThrow());
//        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
