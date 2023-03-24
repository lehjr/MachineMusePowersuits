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
import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.client.model.obj.OBJPartData;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.client.model.helper.LuxCapHelper;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Only used for the item model. Not needed for the block model
 */
@OnlyIn(Dist.CLIENT)
public class LuxCapacitorModelWrapper extends BakedModelWrapper<OBJBakedCompositeModel> {
    Color colour;
    private LuxCapacitorItemOverrides overrides;

    public LuxCapacitorModelWrapper(OBJBakedCompositeModel original) {
        super(original);
        this.overrides = new LuxCapacitorItemOverrides(this);
    }


    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData, boolean ignored) {
        if (!extraData.hasProperty(OBJPartData.SUBMODEL_DATA)) {
            extraData = LuxCapHelper.getBlockBaseModelData();
        }
        return originalModel.getQuads(state, side, rand, extraData);
    }


    @Nonnull
    @Override // FIXME : should this one even fire?
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
//        if (state == null) {
//            return originalModel.getQuads(state, side, rand, extraData);
//        }

//        NuminaLogger.logError("getting side here: " + side);
        // FIXME: lense color causes issues in block rendering

//        if (!extraData.hasProperty(OBJPartData.SUBMODEL_DATA)) {
//            extraData = LuxCapHelper.getLensModelData(colour != null ? colour.getInt() : Color.WHITE.getInt());
////                    LuxCapHelper.getLensModelData(colour != null ? colour.getInt() : Color.WHITE.getInt());
//        }
        return empty;//
    }

    static final List<BakedQuad> empty = new ArrayList<>();

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        // setup for item model
        if (state == null) {
            IModelData extraData = LuxCapHelper.getItemModelData(colour != null ? colour.getInt() : Color.WHITE.getInt());
            return originalModel.getQuads(state, side, rand, extraData);
        }
        // setup for block model
        return empty;
    }

    /**
     *  This is needed in order to return this wrapper with the transforms from the base model
     * otherwise the base model is returned from the super method skipping the setting of the lens color
     * @param cameraTransformType
     * @param poseStack
     * @return
     */
    @Override
    public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack) {
        return PerspectiveMapWrapper.handlePerspective(this, originalModel.getModelState(), cameraTransformType, poseStack);
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
            this.itemModel = model;
        }

        @Nullable
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel worldIn, @Nullable LivingEntity entityIn,  int pSeed) {
//            Color colour;
            // this one is just for the launched item
            if (stack.hasTag() && stack.getTag().contains(TagConstants.COLOR, Tag.TAG_INT)) {
                colour = new Color( stack.getTag().getInt(TagConstants.COLOR));
                // this is for the active icon
            } else {
                colour = stack.getCapability(PowerModuleCapability.POWER_MODULE).map(pm -> {
                    float red = (float) pm.applyPropertyModifiers(MPSConstants.RED_HUE);
                    float green = (float) pm.applyPropertyModifiers(MPSConstants.GREEN_HUE);
                    float blue = (float) pm.applyPropertyModifiers(MPSConstants.BLUE_HUE);
                    float alpha = (float) pm.applyPropertyModifiers(MPSConstants.OPACITY);
                    return new Color(red, green, blue, alpha);
                }).orElse(LuxCapacitorBlock.defaultColor);
            }

            if (model instanceof LuxCapacitorModelWrapper) {
                ((LuxCapacitorModelWrapper) model).colour = colour;
                return model;
            }
            itemModel.colour = colour;
            return itemModel;
        }
    }
}