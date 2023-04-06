package lehjr.numina.client.model.obj;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class OBJBakedCompositeModel implements IDynamicBakedModel {
    private final ImmutableMap<String, OBJBakedPart> bakedParts; // store the quads for each part

    private final boolean isAmbientOcclusion;
    private final boolean isGui3d;
    private final boolean diffuseLighting;
    private final TextureAtlasSprite particle;
    private final ItemOverrides overrides;

    private final ModelState transforms;

    public OBJBakedCompositeModel(
            boolean diffuseLighting,
            boolean isGui3d,
            boolean isAmbientOcclusion,
            TextureAtlasSprite particle,
            ImmutableMap<String, OBJBakedPart> bakedParts, // store the quads for each part
            ModelState combinedTransform,
            ItemOverrides overrides) {
        this.diffuseLighting = diffuseLighting;
        this.isGui3d = isGui3d;
        this.isAmbientOcclusion = isAmbientOcclusion;
        this.bakedParts = bakedParts;
        this.particle = particle;
        this.transforms = combinedTransform;
        this.overrides = overrides;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand) {
        return IDynamicBakedModel.super.getQuads(state, side, rand);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        for (Map.Entry<String, OBJBakedPart> entry : bakedParts.entrySet()) {
            builder.addAll(entry.getValue().getQuads(state, side, rand, OBJPartData.getOBJPartData(extraData, entry.getKey()), renderType));
        }
        return builder.build();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return isAmbientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return isGui3d;
    }

    @Override
    public boolean usesBlockLight() {
        return diffuseLighting;
    }

    public ModelState getModelState() {
        return transforms;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return particle;
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    @Nullable
    public OBJBakedPart getPart(String name) {
        return bakedParts.get(name);
    }
}
