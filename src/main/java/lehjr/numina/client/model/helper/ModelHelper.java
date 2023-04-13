package lehjr.numina.client.model.helper;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Transformation;
import forge.NuminaObjLoader;
import forge.NuminaObjModel;
import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.QuadTransformers;
import net.minecraftforge.common.util.TransformationHelper;
import org.joml.Vector3f;

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

//    public static Function<ObjMaterialLibrary.Material, TextureAtlasSprite> whiteTextureGetter() {
//        return (iHateNamingPointlessVariables)-> IForgeModelBaker.White.instance();
//    }

    @Nullable
    public static NuminaObjModel getOBJModel(ResourceLocation location, int attempt) {
        NuminaObjModel model;
        try {
            model = NuminaObjLoader.INSTANCE.loadModel(
                    new NuminaObjModel.ModelSettings(location, true, true, true, false, null));
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
    public static OBJBakedCompositeModel loadBakedModel(ModelState modelState,
                                                        ItemOverrides overrides,
                                                        ResourceLocation modelLocation) {
        NuminaObjModel model = getOBJModel(modelLocation, 0);

//        if (model != null) {
//            StandaloneGeometryBakingContext
//
//
//            // OBJBakedCompositeModel bake(IGeometryBakingContext owner, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
//
//            OBJBakedCompositeModel bakedModel = model.bake(new OBJModelConfiguration(modelLocation)/*.setCombinedTransform(modelState)*/,
//                    ForgeModelBakery.instance(),
//                    ForgeModelBakery.defaultTextureGetter(),
//                    modelState,
//                    overrides,
//                    modelLocation);
//            return bakedModel;
//        }
        return null;
    }

    /*
     * Here we can color the quads or change the transform using the setup below.
     * This is better than changing material colors for Wavefront models because it means that you can use a single material for the entire model
     * instead of unique ones for each group. It also means you don't necessarily need a Wavefront model.
     */
    public static List<BakedQuad> getColoredQuadsWithGlowAndTransform(List<BakedQuad> quadList, Color color, final Transformation transform, boolean glow) {
        if (!quadList.isEmpty()) {
            if (glow) {
                QuadTransformers.settingMaxEmissivity().andThen(QuadTransformers.applyingColor(color.getARGBInt())).andThen(QuadTransformers.applying(transform)).processInPlace(quadList);
            } else {
                QuadTransformers.applyingColor(color.getARGBInt()).andThen(QuadTransformers.applying(transform)).processInPlace(quadList);
            }
        }
        return quadList;
    }

    public static BakedQuad colouredQuadWithGlowAndTransform(Color color, BakedQuad quad, boolean glow, Transformation transform) {
        if (glow)
            QuadTransformers.settingMaxEmissivity().andThen(QuadTransformers.applyingColor(color.getARGBInt())).andThen(QuadTransformers.applying(transform)).processInPlace(quad);
        else
            QuadTransformers.applyingColor(color.getARGBInt()).andThen(QuadTransformers.applying(transform)).processInPlace(quad);
        return quad;
    }

    public static List<BakedQuad> getColoredQuadsWithGlow(List<BakedQuad> quadList, Color color, boolean glow) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        quadList.forEach(quad -> {
            if (glow)
                QuadTransformers.settingMaxEmissivity().andThen(QuadTransformers.applyingColor(color.getARGBInt())).processInPlace(quad);
            else QuadTransformers.applyingColor(color.getARGBInt()).processInPlace(quad);
            builder.add(quad);
        });
        return builder.build();
    }

    public static List<BakedQuad> getColoredQuads(List<BakedQuad> quadList, Color color) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        quadList.forEach(quad -> {
            QuadTransformers.applyingColor(color.getARGBInt()).processInPlace(quad);
            builder.add(quad);
        });
        return builder.build();
    }
}
