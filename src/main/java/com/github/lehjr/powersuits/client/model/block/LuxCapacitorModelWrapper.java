package com.github.lehjr.powersuits.client.model.block;

import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.block.LuxCapacitorBlock;
import com.github.lehjr.powersuits.client.model.helper.LuxCapHelper;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.mojang.blaze3d.matrix.MatrixStack;
import forge.OBJBakedCompositeModel;
import forge.OBJPartData;
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
        return PerspectiveMapWrapper.handlePerspective(this, originalModel.getTransforms(), cameraTransformType, mat);
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
        public IBakedModel getOverrideModel(IBakedModel model, ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
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