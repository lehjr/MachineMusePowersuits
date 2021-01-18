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
