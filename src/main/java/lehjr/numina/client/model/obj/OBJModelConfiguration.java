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

package lehjr.numina.client.model.obj;

import com.mojang.math.Transformation;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import org.jetbrains.annotations.Nullable;

/**
 * This is just the bare minimum needed to bake an OBJ model not attached to a block or item.
 */
public class OBJModelConfiguration implements IGeometryBakingContext {
    private final ResourceLocation modelLocation;
    boolean isGui3d = true;
    boolean useBlockLight = true;
    boolean useAmbientOcclusion = true;

    ItemTransforms transforms = ItemTransforms.NO_TRANSFORMS;

    ModelState blockTransform = BlockModelRotation.X0_Y0;

    public OBJModelConfiguration(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    public OBJModelConfiguration setBlockTransform(ModelState blockTransform) {
        this.blockTransform = blockTransform;
        return this;
    }

    @Override
    public String getModelName() {
        return modelLocation.toString();
    }

    @Override
    public boolean hasMaterial(String name) {
        return false;
    }

    @Override
    public Material getMaterial(String name) {
        return new Material(TextureAtlas.LOCATION_BLOCKS, NuminaConstants.TEXTURE_WHITE_SHORT); // FIXME???
    }
    //    @Override
//    public Material resolveTexture(String name) {
//        return new Material(TextureAtlas.LOCATION_BLOCKS, NuminaConstants.TEXTURE_WHITE_SHORT);
//    }


    @Override
    public boolean isGui3d() {
        return isGui3d;
    }

    public OBJModelConfiguration isGui3d(boolean isGui3d) {
        this.isGui3d = isGui3d;
        return this;
    }

    @Override
    public boolean useBlockLight() {
        return useBlockLight;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return useAmbientOcclusion;
    }

    @Override
    public ItemTransforms getTransforms() {
        return transforms;
    }

    /**
     * FIXME: not really sure yet what will go here
     * @return
     */
    @Override
    public Transformation getRootTransform() {
        return Transformation.identity();
    }

    @Override
    public @Nullable ResourceLocation getRenderTypeHint() {
        return null;
    }

    /**
     * Used for Baking
     * @param component The component for which to query visibility
     * @param fallback  The default visibility if an override isn't found
     * @return
     */
    @Override
    public boolean isComponentVisible(String component, boolean fallback) {
        return true;
    }

//    //FIXME!! replace with this to simplify code?

//

//
//    @Nullable
//    @Override
//    public UnbakedModel getOwnerModel() {
//        return null;
//    }
//
//
//    @Override
//    public boolean isTexturePresent(String name) {
//        return false;
//    }

//
//    @Override
//    public boolean isSideLit() {
//        return isSideLit;
//    }
//
//    @Override
//    public boolean useSmoothLighting() {
//        return useSmoothLighting;
//    }


//
//    @Override
//    public boolean getPartVisibility(IModelGeometryPart part, boolean fallback) {
//        return IModelConfiguration.super.getPartVisibility(part, fallback);
//    }
//
//    @Override
//    public boolean getPartVisibility(IModelGeometryPart part) {
//        return IModelConfiguration.super.getPartVisibility(part);
//    }
}

