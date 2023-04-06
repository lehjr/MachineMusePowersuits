///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.numina.client.model.obj;
//
//import lehjr.numina.common.constants.NuminaConstants;
//import net.minecraft.client.renderer.block.model.ItemTransforms;
//import net.minecraft.client.renderer.texture.TextureAtlas;
//import net.minecraft.client.resources.model.BlockModelRotation;
//import net.minecraft.client.resources.model.Material;
//import net.minecraft.client.resources.model.ModelState;
//import net.minecraft.client.resources.model.UnbakedModel;
//import net.minecraft.resources.ResourceLocation;
//import org.jetbrains.annotations.Nullable;
//
///**
// * This is just the bare minimum needed to bake an OBJ model not attached to a block or item.
// */
//public class OBJModelConfiguration implements ModelConfiguration {
//    private final ResourceLocation modelLocation;
//
//    String modelName;
//    boolean isShadedInGui = true;
//    boolean isSideLit = true;
//    boolean useSmoothLighting = true;
//    ModelState combinedTransform = BlockModelRotation.X0_Y0;
//
//    public OBJModelConfiguration(ResourceLocation modelLocation) {
//        this.modelLocation = modelLocation;
//    }
//
//    @Override
//    public String getModelName() {
//        return modelLocation.toString();
//    }
//
//    //FIXME!! replace with this to simplify code?
//    @Override
//    public Material resolveTexture(String name) {
//        return new Material(TextureAtlas.LOCATION_BLOCKS, NuminaConstants.TEXTURE_WHITE_SHORT);
//    }
//
//    public OBJModelConfiguration setCombinedTransform(ModelState combinedTransform) {
//        this.combinedTransform = combinedTransform;
//        return this;
//    }
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
//
//
//    OBJModelConfiguration setIsShadedInGui(boolean isShadedInGui) {
//        this.isShadedInGui = isShadedInGui;
//        return this;
//    }
//
//    @Override
//    public boolean isShadedInGui() {
//        return isShadedInGui;
//    }
//
//    @Override
//    public boolean isSideLit() {
//        return isSideLit;
//    }
//
//    public OBJModelConfiguration setIsSideLit(boolean isSideLit) {
//        this.isSideLit = isSideLit;
//        return this;
//    }
//
//    public OBJModelConfiguration setUseSmoothLighting(boolean useSmoothLighting) {
//        this.useSmoothLighting = useSmoothLighting;
//        return this;
//    }
//
//    @Override
//    public boolean useSmoothLighting() {
//        return useSmoothLighting;
//    }
//
//
//    /**
//     * Gets the vanilla camera transforms data.
//     * Do not use for non-vanilla code. For general usage, prefer getCombinedState.
//     */
//    @Override
//    public ItemTransforms getCameraTransforms() {
//        return ItemTransforms.NO_TRANSFORMS;
//    }
//
//    /**
//     * @return The combined transformation state including vanilla and forge transforms data.
//     */
//    @Override
//    public ModelState getCombinedTransform() {
//        return combinedTransform;
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
//}
//
