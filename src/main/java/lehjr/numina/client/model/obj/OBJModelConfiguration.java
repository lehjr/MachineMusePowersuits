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

import lehjr.numina.common.constants.NuminaConstants;
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
        return new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, NuminaConstants.TEXTURE_WHITE_SHORT);
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
        return ItemCameraTransforms.NO_TRANSFORMS;
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
