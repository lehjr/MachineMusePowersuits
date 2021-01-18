package forge;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static forge.OBJPartData.getOBJPartData;

public class OBJBakedCompositeModel implements IDynamicBakedModel {
    private final ImmutableMap<String, OBJBakedPart> bakedParts; // store the quads for each part

    private final boolean isAmbientOcclusion;
    private final boolean isGui3d;
    private final boolean diffuseLighting;
    private final TextureAtlasSprite particle;
    private final ItemOverrideList overrides;

    private final IModelTransform transforms;

    public OBJBakedCompositeModel(
                                boolean diffuseLighting,
                                boolean isGui3d,
                                  boolean isAmbientOcclusion,
                                  TextureAtlasSprite particle,
                                  ImmutableMap<String, OBJBakedPart> bakedParts, // store the quads for each part
                                  IModelTransform combinedTransform,
                                  ItemOverrideList overrides) {
        this.diffuseLighting = diffuseLighting;
        this.isGui3d = isGui3d;
        this.isAmbientOcclusion = isAmbientOcclusion;
        this.bakedParts = bakedParts;
        this.particle = particle;
        this.transforms = combinedTransform;
        this.overrides = overrides;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        for (Map.Entry<String, OBJBakedPart> entry : bakedParts.entrySet()) {
            builder.addAll(entry.getValue().getQuads(state, side, rand, getOBJPartData(extraData, entry.getKey())));
        }
        return builder.build();
    }

    public IModelTransform getTransforms() {
        return transforms;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return isAmbientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return isGui3d;
    }

    @Override
    public boolean isSideLit() {
        return false;//diffuseLighting; ?
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return particle;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    @Override
    public boolean doesHandlePerspectives() {
        return true;
    }

    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        return PerspectiveMapWrapper.handlePerspective(this, transforms, cameraTransformType, mat);
    }

    @Nullable
    public OBJBakedPart getPart(String name) {
        return bakedParts.get(name);
    }
}