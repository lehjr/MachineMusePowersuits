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

package lehjr.numina.client.gui.gemoetry;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.common.math.Colour;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

public interface IDrawable extends IRenderable {
    /**
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param frameTime
     */
    void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime);

    float getZLevel();

    IDrawable setZLevel(float zLevel);

    /**
     * Common code for adding vertices to the BufferBuilder
     * @param matrix4f
     * @param vertices
     * @param colour a Colour to draw in
     */
    default void addVerticesToBuffer(Matrix4f matrix4f, FloatBuffer vertices, Colour colour) {
        vertices.rewind();
        while(vertices.hasRemaining()) {
            getBufferBuilder().vertex(matrix4f, vertices.get(), vertices.get(), getZLevel()).color(colour.r, colour.g, colour.b, colour.a).endVertex();
        }
    }

    /**
     * Common code for adding vertices to the BufferBuilder
     * @param matrix4f
     * @param vertices
     * @param colour a Colour to draw in
     */
    default void addVerticesToBuffer(Matrix4f matrix4f, DoubleBuffer vertices, Colour colour) {
        vertices.rewind();
        Vector4f vector4f = new Vector4f((float)vertices.get(), (float)vertices.get(), getZLevel(), 1.0F);
        vector4f.transform(matrix4f);
        while(vertices.hasRemaining()) {
            getBufferBuilder().vertex((double)vector4f.x(), (double)vector4f.y(), (double)vector4f.z()).color(colour.r, colour.g, colour.b, colour.a).endVertex();
        }
    }

    /**
     * Common code for adding vertices to the BufferBuilder
     * @param matrix4f
     * @param vertices
     * @param colourBuffer FloatBuffer of colors
     */
    default void addVerticesToBuffer(Matrix4f matrix4f, FloatBuffer vertices, FloatBuffer colourBuffer) {
        vertices.rewind();
        colourBuffer.rewind();
        while(vertices.hasRemaining() && colourBuffer.hasRemaining()) {
            getBufferBuilder().vertex(matrix4f, vertices.get(), vertices.get(), getZLevel()).color(colourBuffer.get(), colourBuffer.get(), colourBuffer.get(), colourBuffer.get()).endVertex();
        }
    }

    default Tessellator getTessellator() {
        return Tessellator.getInstance();
    }

    default BufferBuilder getBufferBuilder() {
        return getTessellator().getBuilder();
    }

    default void preDraw(int glMode, VertexFormat format) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);
        getBufferBuilder().begin(glMode, format);
    }

    default void postDraw() {
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    default void drawTesselator() {
        getTessellator().end();
    }
}
