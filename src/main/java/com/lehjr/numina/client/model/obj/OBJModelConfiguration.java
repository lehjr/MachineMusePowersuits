package com.lehjr.numina.client.model.obj;

import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;
import org.jetbrains.annotations.Nullable;

/**
 * This is just the bare minimum needed to bake an OBJ model not attached to a block or item.
 */
public class OBJModelConfiguration implements IModelConfiguration {
    private final ResourceLocation modelLocation;

    String modelName;
    boolean isShadedInGui = true;
    boolean isSideLit = true;
    boolean useSmoothLighting = true;
    ModelState combinedTransform = BlockModelRotation.X0_Y0;

    public OBJModelConfiguration(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Override
    public String getModelName() {
        return modelLocation.toString();
    }






//    /**
//     * Resolves the final texture name, taking into account texture aliases and replacements.
//     *
//     * @param nameIn The name of a texture channel.
//     * @return The location of the texture, or the missing texture if not found.
//     */
//    @Override
//    public RenderMaterial resolveTexture(String nameIn) {
//        if (startsWithHash(nameIn)) {
//            nameIn = nameIn.substring(1);
//        }
//
//        List<String> list = Lists.newArrayList();
//
//        while(true) {
//            Either<RenderMaterial, String> either = this.findTexture(nameIn);
//            Optional<RenderMaterial> optional = either.left();
//            if (optional.isPresent()) {
//                return optional.get();
//            }
//
//            nameIn = either.right().get();
//            if (list.contains(nameIn)) {
//                MPALibLogger.getLogger().warn("Unable to resolve texture due to reference chain {}->{} in {}", Joiner.on("->").join(list), nameIn, this.modelName);
//                return new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, MPALibConstants.TEXTURE_WHITE_SHORT);
//            }
//
//            list.add(nameIn);
//        }
//    }
//
//    private static boolean startsWithHash(String strIn) {
//        return strIn.charAt(0) == '#';
//    }
//
//    private Either<RenderMaterial, String> findTexture(String nameIn) {
//        return Either.left(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, MPALibConstants.TEXTURE_WHITE_SHORT));
//    }



















    //FIXME!! replace with this to simplify code?
    @Override
    public Material resolveTexture(String name) {
        return new Material(TextureAtlas.LOCATION_BLOCKS, NuminaConstants.TEXTURE_WHITE_SHORT);
    }



    public OBJModelConfiguration setCombinedTransform(ModelState combinedTransform) {
        this.combinedTransform = combinedTransform;
        return this;
    }

    @Nullable
    @Override
    public UnbakedModel getOwnerModel() {
        return null;
    }


    @Override
    public boolean isTexturePresent(String name) {
        return false;
    }



    OBJModelConfiguration setIsShadedInGui(boolean isShadedInGui) {
        this.isShadedInGui = isShadedInGui;
        return this;
    }

    @Override
    public boolean isShadedInGui() {
        return isShadedInGui;
    }

    @Override
    public boolean isSideLit() {
        return isSideLit;
    }

    public OBJModelConfiguration setIsSideLit(boolean isSideLit) {
        this.isSideLit = isSideLit;
        return this;
    }

    public OBJModelConfiguration setUseSmoothLighting(boolean useSmoothLighting) {
        this.useSmoothLighting = useSmoothLighting;
        return this;
    }

    @Override
    public boolean useSmoothLighting() {
        return useSmoothLighting;
    }


    /**
     * Gets the vanilla camera transforms data.
     * Do not use for non-vanilla code. For general usage, prefer getCombinedState.
     */
    @Override
    public ItemTransforms getCameraTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }

    /**
     * @return The combined transformation state including vanilla and forge transforms data.
     */
    @Override
    public ModelState getCombinedTransform() {
        return combinedTransform;
    }

    @Override
    public boolean getPartVisibility(IModelGeometryPart part, boolean fallback) {
        return IModelConfiguration.super.getPartVisibility(part, fallback);
    }

    @Override
    public boolean getPartVisibility(IModelGeometryPart part) {
        return IModelConfiguration.super.getPartVisibility(part);
    }
}
