package lehjr.numina.client.model.helper;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import forge.NuminaOBJLoader;
import forge.NuminaOBJModel;
import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.client.model.obj.OBJModelConfiguration;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TransformationHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class ModelHelper {
    public static Transformation get(float transformX, float transformY, float transformZ, float angleX, float angleY, float angleZ, float scaleX, float scaleY, float scaleZ) {
        // (@Nullable Vector3f translationIn,
        // @Nullable Quaternion rotationLeftIn,
        // @Nullable Vector3f scaleIn,
        // @Nullable Quaternion rotationRightIn)


        return new Transformation(
                // Transform
                new Vector3f(transformX / 16, transformY / 16, transformZ / 16),
                // Angles
                TransformationHelper.quatFromXYZ(new Vector3f(angleX, angleY, angleZ), true),

                // Scale
                new Vector3f(scaleX, scaleY, scaleZ),
                null);
    }

    public static Transformation get(float transformX, float transformY, float transformZ, float angleX, float angleY, float angleZ, float scale) {
        return get(transformX, transformY, transformZ, angleX, angleY, angleZ, scale, scale, scale);
    }

    /**
     * Simple transformation for armor models. Powerfist (and shield?) will need one of these for every conceivable case except GUI which will be an icon
     */
    public static Transformation getTransform(@Nullable Vector3f translation, @Nullable Vector3f rotation, @Nullable Vector3f scale) {
        if (translation == null)
            translation = new Vector3f(0, 0, 0);
        if (rotation == null)
            rotation = new Vector3f(0, 0, 0);
        if (scale == null)
            scale = new Vector3f(1, 1, 1);


        /// Transformation(@Nullable Vector3f translationIn, @Nullable Quaternion rotationLeftIn, @Nullable Vector3f scaleIn, @Nullable Quaternion rotationRightIn)

        return new Transformation(
                // Transform
                new Vector3f(translation.x() / 16, translation.y() / 16, translation.z() / 16),
                // Angles
                TransformationHelper.quatFromXYZ(rotation, true),
                // Scale
                scale,
                null);
    }



    /**
     * Get the default texture getter the models will be baked with.
     */
    public static Function<ResourceLocation, TextureAtlasSprite> defaultTextureGetter(ResourceLocation location) {
        return Minecraft.getInstance().getTextureAtlas(location);
    }

    public static Function<Material, TextureAtlasSprite> whiteTextureGetter() {
        return (iHateNamingPointlessVariables)->ForgeModelBakery.White.instance();
    }

    @Nullable
    public static NuminaOBJModel getOBJModel(ResourceLocation location, int attempt) {
        NuminaOBJModel model;
        try {
            model = NuminaOBJLoader.INSTANCE.loadModel(
                    new NuminaOBJModel.ModelSettings(location, true, true, true, true, null));
        } catch (Exception e) {
            if (attempt < 6) {
                model = getOBJModel(location, attempt + 1);
                NuminaLogger.logError("Model loading failed on attempt #" + attempt + "  :( " + location.toString());
            } else {
                model = null;
                NuminaLogger.logError("Failed to load model. " + e);
            }
        }
        NuminaLogger.logDebug("got model");
        return model;
    }

    @Nullable
    public static OBJBakedCompositeModel loadBakedModel(ModelState modelTransform,
                                                        ItemOverrides overrides,
                                                        ResourceLocation modelLocation) {
        NuminaOBJModel model = getOBJModel(modelLocation, 0);

        if (model != null) {
            OBJBakedCompositeModel bakedModel = model.bake(new OBJModelConfiguration(modelLocation)/*.setCombinedTransform(modelTransform)*/,
                    ForgeModelBakery.instance(),
                    ForgeModelBakery.defaultTextureGetter(),
                    modelTransform,
                    overrides,
                    modelLocation);
            return bakedModel;
        }
        return null;
    }

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
        quadList.forEach(quad -> {
            builder.add(colorQuad(color, quad, !glow));

        });
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
            super(new BakedQuadBuilder(texture)); // using baked quad builder with a vertex format of Blocks, but using color format of items??? FAIL??
            this.colour = colour;
            this.applyDiffuse = applyDiffuse;
        }

        public QuadTransformer(Color colour, final Transformation transform, TextureAtlasSprite texture, boolean applyDiffuse) {
            super(new BakedQuadBuilder(texture));

            if(colour != null) {
                super.setQuadTint(Color.WHITE.getInt());
            }

            this.transform = transform;
            this.colour = colour;
            this.applyDiffuse = applyDiffuse;
//            VertexFormat.Mode.

//            public static final VertexFormat BLOCK = new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder().put("Position", ELEMENT_POSITION).put("Color", ELEMENT_COLOR).put("UV0", ELEMENT_UV0).put("UV2", ELEMENT_UV2).put("Normal", ELEMENT_NORMAL).put("Padding", ELEMENT_PADDING).build());


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
