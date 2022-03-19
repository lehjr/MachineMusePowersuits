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

package lehjr.numina.util.client.model.obj;

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
            builder.addAll(entry.getValue().getQuads(state, side, rand, OBJPartData.getOBJPartData(extraData, entry.getKey())));
        }
        return builder.build();
    }

    public IModelTransform getModelTransforms() {
        return transforms;
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
        return false;//diffuseLighting; ?
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