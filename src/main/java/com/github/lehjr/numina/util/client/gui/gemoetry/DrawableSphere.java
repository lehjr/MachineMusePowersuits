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
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.math.vector.Matrix4f;

import java.nio.FloatBuffer;

public class DrawableSphere {
    final float radius = 4;
    protected static FloatBuffer points;
    Colour colour;

    public DrawableSphere(Colour colour) {
        this.colour = colour;
        points = GradientAndArcCalculator.getSphereVertices(20 ,radius);
    }

    public void draw(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float radiusIn, float x, float y, float z) {
        points.rewind();
        float scale = radiusIn/this.radius;

        matrixStackIn.push();
        matrixStackIn.translate(x, y, z);
        matrixStackIn.scale(scale, scale, scale);
        IVertexBuilder vertBuffer = bufferIn.getBuffer(DrawableCircle.PLASMA_BALL);
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();

        while (points.hasRemaining()) {
            vertBuffer.pos(matrix4f, points.get(), points.get(), points.get())
                    .color(colour.r, colour.g, colour.b, colour.a)
                    .lightmap(0x00F000F0)
                    .endVertex();
        }
        matrixStackIn.pop();
    }
}
