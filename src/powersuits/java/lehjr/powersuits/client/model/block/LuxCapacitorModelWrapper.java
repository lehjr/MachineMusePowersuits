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

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.model.helper.LuxCapHelper;
import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.TagUtils;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Only used for the item model. Not needed for the block model
 */
@OnlyIn(Dist.CLIENT)
public class LuxCapacitorModelWrapper extends BakedModelWrapper<OBJBakedCompositeModel> {
    Color color;
    private final LuxCapacitorItemOverrides overrides;

    public LuxCapacitorModelWrapper(OBJBakedCompositeModel original) {
        super(original);
        this.overrides = new LuxCapacitorItemOverrides(this);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@org.jetbrains.annotations.Nullable BlockState state, @org.jetbrains.annotations.Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @org.jetbrains.annotations.Nullable RenderType renderType) {
        if (extraData == ModelData.EMPTY) {
            extraData = LuxCapHelper.getItemModelData(color != null ? color.getARGBInt() : Color.WHITE.getARGBInt());
        }
//        NuminaLogger.logDebug("color: " + color);

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
    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
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
        LuxCapacitorModelWrapper itemModel;
        public LuxCapacitorItemOverrides(LuxCapacitorModelWrapper model) {
            super();
            this.itemModel = model;
        }

        @Nullable
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel worldIn, @Nullable LivingEntity entityIn,  int pSeed) {
            IPowerModule pm = stack.getCapability(NuminaCapabilities.Module.POWER_MODULE);
            if (pm != null) {
                float red = (float) pm.applyPropertyModifiers(MPSConstants.RED_HUE);
                float green = (float) pm.applyPropertyModifiers(MPSConstants.GREEN_HUE);
                float blue = (float) pm.applyPropertyModifiers(MPSConstants.BLUE_HUE);
                float alpha = (float) pm.applyPropertyModifiers(MPSConstants.OPACITY);
                color = new Color(red, green, blue, alpha);
            } else {
                color = TagUtils.getColorOrDefault(stack, LuxCapacitorBlock.defaultColor);
            }

            itemModel.color = color;
            return itemModel;
        }
    }
}