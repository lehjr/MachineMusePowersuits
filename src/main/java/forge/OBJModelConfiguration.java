package forge;

import com.github.lehjr.numina.constants.NuminaConstants;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;

import javax.annotation.Nullable;

/**
 * This is just the bare minimum needed to bake an OBJ model not attached to a block or item.
 */
public class OBJModelConfiguration implements IModelConfiguration {
    String modelName;
    boolean isShadedInGui = true;
    boolean isSideLit = true;
    boolean useSmoothLighting = true;
    IModelTransform combinedTransform = ModelRotation.X0_Y0;


    private final ResourceLocation modelLocation;

    public OBJModelConfiguration(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Nullable
    @Override
    public IUnbakedModel getOwnerModel() {
        return null;
    }

    @Override
    public String getModelName() {
        return modelLocation.toString();
    }

    @Override
    public boolean isTexturePresent(String name) {
        return false;
    }

    //FIXME!! replace with this to simplify code?
    @Override
    public RenderMaterial resolveTexture(String name) {
        return new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, NuminaConstants.TEXTURE_WHITE_SHORT);
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

    OBJModelConfiguration setIsShadedInGui(boolean isShadedInGui) {
        this.isShadedInGui = isShadedInGui;
        return this;
    }

    @Override
    public boolean isShadedInGui() {
        return isShadedInGui;
    }

    public OBJModelConfiguration setIsSideLit(boolean isSideLit) {
        this.isSideLit = isSideLit;
        return this;
    }

    @Override
    public boolean isSideLit() {
        return isSideLit;
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
    public ItemCameraTransforms getCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    public OBJModelConfiguration setCombinedTransform(IModelTransform combinedTransform) {
        this.combinedTransform = combinedTransform;
        return this;
    }

    /**
     * @return The combined transformation state including vanilla and forge transforms data.
     */
    @Override
    public IModelTransform getCombinedTransform() {
        return combinedTransform;
    }
}
