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

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.client.model.obj.OBJPartData;
import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.common.math.Colour;
import lehjr.powersuits.client.model.helper.LuxCapHelper;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Only used for the item model. Not needed for the block model
 */
@OnlyIn(Dist.CLIENT)
public class LuxCapacitorModelWrapper extends BakedModelWrapper<OBJBakedCompositeModel> {
    Colour colour;
    private LuxCapacitorItemOverrideList overrides;

    public LuxCapacitorModelWrapper(OBJBakedCompositeModel original) {
        super(original);
        this.overrides = new LuxCapacitorItemOverrideList(this);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if (!extraData.hasProperty(OBJPartData.SUBMODEL_DATA)) {
            extraData = LuxCapHelper.getModelData(colour != null ? colour.getInt() : Colour.WHITE.getInt());
        }
        return originalModel.getQuads(state, side, rand, extraData);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        IModelData extraData = LuxCapHelper.getModelData(colour != null ? colour.getInt() : Colour.WHITE.getInt());
        return originalModel.getQuads(state, side, rand, extraData);
    }

    /**
     *  This is needed in order to return this wrapper with the transforms from the base model
     * otherwise the base model is returned from the super method skipping the setting of the lens color
     * @param cameraTransformType
     * @param mat
     * @return
     */
    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        return PerspectiveMapWrapper.handlePerspective(this, ((OBJBakedCompositeModel)this.originalModel).getModelTransforms(), cameraTransformType, mat);
    }

    /**
     * required to set Lens color
     */
    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    private class LuxCapacitorItemOverrideList extends ItemOverrideList {
        LuxCapacitorModelWrapper itemModel;
        public LuxCapacitorItemOverrideList(LuxCapacitorModelWrapper model) {
            this.itemModel = model;
        }

        @Nullable
        @Override
        public IBakedModel resolve(IBakedModel model, ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
            Colour colour;
            // this one is just for the launched item
            if (stack.hasTag() && stack.getTag().contains("colour", Constants.NBT.TAG_INT)) {
                colour = new Colour( stack.getTag().getInt("colour"));
            // this is for the active icon
            } else {
                colour = stack.getCapability(PowerModuleCapability.POWER_MODULE).map(pm -> {
                    float red = (float) pm.applyPropertyModifiers(MPSConstants.RED_HUE);
                    float green = (float) pm.applyPropertyModifiers(MPSConstants.GREEN_HUE);
                    float blue = (float) pm.applyPropertyModifiers(MPSConstants.BLUE_HUE);
                    float alpha = (float) pm.applyPropertyModifiers(MPSConstants.OPACITY);
                    return new Colour(red, green, blue, alpha);
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