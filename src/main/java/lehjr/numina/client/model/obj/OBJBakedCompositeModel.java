package lehjr.numina.client.model.obj;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class OBJBakedCompositeModel implements IDynamicBakedModel {
    private final ImmutableMap<String, OBJBakedPart> bakedParts; // store the quads for each part

    private final boolean useAmbientOcclusion;
    private final boolean isGui3d;
    private final boolean useBlockLight;
    private final TextureAtlasSprite particle;
    private final ItemOverrides overrides;
    private final ItemTransforms transforms;

    public OBJBakedCompositeModel(
            boolean useAmbientOcclusion,
            boolean useBlockLight,
            boolean isGui3d,
            TextureAtlasSprite particle,
            ImmutableMap<String, OBJBakedPart> bakedParts, // store the quads for each part
            ItemTransforms combinedTransform,
            ItemOverrides overrides) {
        this.useAmbientOcclusion = useAmbientOcclusion;
        this.useBlockLight = useBlockLight;
        this.isGui3d = isGui3d;




        this.bakedParts = bakedParts;
        this.particle = particle;
        this.transforms = combinedTransform;
        this.overrides = overrides;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand) {
//        NuminaLogger.logDebug("OBJBakedCompositeModel getting quads and state is: " + state + ", this class: " + this.getClass());
        return IDynamicBakedModel.super.getQuads(state, side, rand);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
//        NuminaLogger.logDebug("OBJBakedCompositeModel getting quads with extraData and state is: " + state + ", this class: " + this.getClass());

        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
        for (Map.Entry<String, OBJBakedPart> entry : bakedParts.entrySet()) {
            builder.addAll(entry.getValue().getQuads(state, side, rand, OBJPartData.getOBJPartData(extraData, entry.getKey()), renderType));
        }
        return builder.build();
    }

    @Override
    public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
        return List.of(RenderType.entityTranslucentCull(TextureAtlas.LOCATION_BLOCKS));
    }

    @Override
    public boolean useAmbientOcclusion() {
        return useAmbientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return isGui3d;
    }

    @Override
    public boolean usesBlockLight() {
        return useBlockLight;
    }

    @Override
    public ItemTransforms getTransforms() {
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
