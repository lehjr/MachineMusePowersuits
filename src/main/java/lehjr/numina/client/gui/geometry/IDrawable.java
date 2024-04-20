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
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import lehjr.numina.common.math.Color;
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
            bufferbuilder.vertex(matrix4f, vertices.get(), vertices.get(), getZLevel()).color(color.r, color.g, color.b, color.a).endVertex();
        }
    }

    /**
     * Common code for adding vertices to the BufferBuilder
     * @param matrix4f
     * @param vertices
     * @param color a Color to draw in
     */
    default void addVerticesToBuffer(Matrix4f matrix4f, FloatBuffer vertices, Color color) {
        vertices.rewind();
        while(vertices.hasRemaining()) {
            getBufferBuilder().vertex(matrix4f, vertices.get(), vertices.get(), getZLevel()).color(color.r, color.g, color.b, color.a).endVertex();
        }
    }

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
            bufferbuilder.vertex(matrix4f, vertices.get(), vertices.get(), getZLevel()).color(colorBuffer.get(), colorBuffer.get(), colorBuffer.get(), colorBuffer.get()).endVertex();
        }
    }
    default BufferBuilder getBufferBuilder() {
        return Tesselator.getInstance().getBuilder();
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
        BufferBuilder builder = getBufferBuilder();
        builder.begin(mode, format);
        return builder;
    }

    default void postDraw(BufferBuilder builder) {
//        RenderSystem.disableBlend();
////        RenderSystem.enableAlphaTest();
//        RenderSystem.enableTexture();

        BufferUploader.drawWithShader(builder.end());
//        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
