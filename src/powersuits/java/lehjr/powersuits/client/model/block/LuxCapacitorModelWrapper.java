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

package lehjr.powersuits.client.model.block;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.client.model.helper.LuxCapHelper;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.renderer.RenderStateShard.*;

/**
 * Only used for the item model. Not needed for the block model
 */
@OnlyIn(Dist.CLIENT)
public class LuxCapacitorModelWrapper extends BakedModelWrapper<OBJBakedCompositeModel> {
    Color color;
    private LuxCapacitorItemOverrides overrides;

    public LuxCapacitorModelWrapper(OBJBakedCompositeModel original) {
        super(original);
        this.overrides = new LuxCapacitorItemOverrides(this);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@org.jetbrains.annotations.Nullable BlockState state, @org.jetbrains.annotations.Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @org.jetbrains.annotations.Nullable RenderType renderType) {
        if (extraData == ModelData.EMPTY) {
            extraData = LuxCapHelper.getItemModelData(color != null ? color.getARGBInt() : Color.WHITE.getARGBInt());
        }
//        System.out.println("color: " + color);

        return super.getQuads(state, side, rand, extraData, renderType); // BLOCKS render at line 126 of ModelBlockRenderer.tesselateWithoutAO(net.minecraft.world.level.BlockAndTintGetter, net.minecraft.client.resources.model.BakedModel, net.minecraft.world.level.block.state.BlockState, net.minecraft.core.BlockPos, com.mojang.blaze3d.vertex.PoseStack, com.mojang.blaze3d.vertex.VertexConsumer, boolean, net.minecraft.util.RandomSource, long, int, net.minecraftforge.client.model.data.ModelData, net.minecraft.client.renderer.RenderType)
    }

    static final List<BakedQuad> empty = new ArrayList<>();

    @Override // don't cache the quads for blocks
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        // State should be null for items
        if(state == null) {
            ModelData extraData = LuxCapHelper.getItemModelData(color != null ? color.getARGBInt() : Color.WHITE.getAGBRInt());
            return getQuads(state, side, rand, extraData, RenderTypeGroup.EMPTY.entity());
        }
        // the block version should be using the other method anyway
        return getQuads(state, side, rand, LuxCapHelper.getBlockBaseModelData(), RenderTypeGroup.EMPTY.entity());
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }


    @Override
    public @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
        return super.getModelData(level, pos, state, modelData);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return ChunkRenderTypeSet.of(RenderType.translucent());// super.getRenderTypes(state, rand, data);
//        return ChunkRenderTypeSet.of(RenderType.entityTranslucentCull(TextureAtlas.LOCATION_BLOCKS));
    }

    /**
     * Note: required to return this instead of "originalModel"
     * @param cameraTransformType
     * @param poseStack
     * @param applyLeftHandTransform
     * @return
     */
    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
        return this;
    }

    /**
     * Also required to return this
     * @param itemStack
     * @param fabulous
     * @return
     */
    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        return List.of(this);
    }

    /**
     * required to set Lens color
     */
    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    private class LuxCapacitorItemOverrides extends ItemOverrides {

        // FIXME!!!!! item renderer still not getting quads from this



        LuxCapacitorModelWrapper itemModel;
        public LuxCapacitorItemOverrides(LuxCapacitorModelWrapper model) {
            super();
            this.itemModel = model;
        }

        @Nullable
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel worldIn, @Nullable LivingEntity entityIn,  int pSeed) {
//            Color colour;
            // this one is just for the launched item
            if (stack.hasTag() && stack.getTag().contains(TagConstants.COLOR, Tag.TAG_INT)) {
                color = new Color( stack.getTag().getInt(TagConstants.COLOR));
                // this is for the active icon
            } else {
                color = stack.getCapability(NuminaCapabilities.POWER_MODULE).map(pm -> {
                    float red = (float) pm.applyPropertyModifiers(MPSConstants.RED_HUE);
                    float green = (float) pm.applyPropertyModifiers(MPSConstants.GREEN_HUE);
                    float blue = (float) pm.applyPropertyModifiers(MPSConstants.BLUE_HUE);
                    float alpha = (float) pm.applyPropertyModifiers(MPSConstants.OPACITY);
                    return new Color(red, green, blue, alpha);
                }).orElse(LuxCapacitorBlock.defaultColor);
            }

            itemModel.color = color;
            return itemModel;
        }
    }
}