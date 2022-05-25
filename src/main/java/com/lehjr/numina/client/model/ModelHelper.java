package com.lehjr.numina.client.model;

import com.google.common.collect.ImmutableList;
import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.math.Transformation;
import com.mojang.math.Vector4f;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.VertexTransformer;

import java.util.List;

public class ModelHelper {

    /*
     * Here we can color the quads or change the transform using the setup below.
     * This is better than changing material colors for Wavefront models because it means that you can use a single material for the entire model
     * instead of unique ones for each group. It also means you don't necessarily need a Wavefront model.
     */
    public static List<BakedQuad> getColoredQuadsWithGlowAndTransform(List<BakedQuad> quadList, Color colour, final Transformation transform, boolean glow) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        quadList.forEach(quad -> builder.add(colouredQuadWithGlowAndTransform(colour, quad, !glow, transform)));
        return builder.build();
    }

    public static BakedQuad colouredQuadWithGlowAndTransform(Color colour, BakedQuad quad, boolean applyDifuse, Transformation transform) {
        QuadTransformer transformer = new QuadTransformer(colour, transform, quad.getSprite(), applyDifuse);
        quad.pipe(transformer);
        return transformer.build();
    }
    public static List<BakedQuad> getColoredQuadsWithGlow(List<BakedQuad> quadList, Color color, boolean glow) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        quadList.forEach(quad -> builder.add(colorQuad(color, quad, !glow)));
        return builder.build();
    }

    public static List<BakedQuad> getColoredQuads(List<BakedQuad> quadList, Color color) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        for (BakedQuad quad : quadList) {
            builder.add(colorQuad(color, quad, quad.isShade()));
        }
        return builder.build();
    }

    public static BakedQuad colorQuad(Color color, BakedQuad quad, boolean applyDifuse) {
        QuadTransformer transformer = new QuadTransformer(color, quad.getSprite(), applyDifuse);
        quad.pipe(transformer);
        return transformer.build();
    }

    // see TRSRTransformer as example
    private static class QuadTransformer extends VertexTransformer {
        Color colour;
        Boolean applyDiffuse;
        Transformation transform;

        public QuadTransformer(Color colour, TextureAtlasSprite texture, boolean applyDiffuse) {
            super(new BakedQuadBuilder(texture));
            this.colour = colour;
            this.applyDiffuse = applyDiffuse;
        }

        public QuadTransformer(Color colour, final Transformation transform, TextureAtlasSprite texture, boolean applyDiffuse) {
            super(new BakedQuadBuilder(texture));
            this.transform = transform;
            this.colour = colour;
            this.applyDiffuse = applyDiffuse;
        }

        @Override
        public void put(int element, float... data) {
            VertexFormatElement.Usage usage = parent.getVertexFormat().getElements().get(element).getUsage();
            // change color
            if (colour != null && usage == VertexFormatElement.Usage.COLOR && data.length >= 4) {
                data[0] = colour.r;
                data[1] = colour.g;
                data[2] = colour.b;
                data[3] = colour.a;
                super.put(element, data);
                // transform normals and position
            } else if (transform != null && usage == VertexFormatElement.Usage.POSITION && data.length >= 4) {
                Vector4f pos = new Vector4f(data[0], data[1], data[2], data[3]);
                transform.transformPosition(pos);
                data[0] = pos.x();
                data[1] = pos.y();
                data[2] = pos.z();
                data[3] = pos.w();
                parent.put(element, data);
            } else {
                super.put(element, data);
            }
        }

        @Override
        public void setApplyDiffuseLighting(boolean diffuse) {
            super.setApplyDiffuseLighting(applyDiffuse != null ? applyDiffuse : diffuse);
        }

        public BakedQuad build() {
            return ((BakedQuadBuilder) parent).build();
        }
    }
}
