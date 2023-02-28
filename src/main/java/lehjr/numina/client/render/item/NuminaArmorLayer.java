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

package lehjr.numina.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.capabilities.render.IArmorModelSpecNBT;
import lehjr.numina.common.capabilities.render.ModelSpecCapability;
import lehjr.numina.common.capabilities.render.modelspec.SpecType;
import lehjr.numina.common.math.Color;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
public class NuminaArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    public NuminaArmorLayer(RenderLayerParent<T, M> entityRenderer, A modelLeggings, A modelArmor) {
        super(entityRenderer, modelLeggings, modelArmor);
    }


    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.renderArmorPiece(matrixStackIn, bufferIn, entityIn, EquipmentSlot.CHEST, packedLightIn, this.getModelFromSlot(EquipmentSlot.CHEST));
        this.renderArmorPiece(matrixStackIn, bufferIn, entityIn, EquipmentSlot.LEGS, packedLightIn, this.getModelFromSlot(EquipmentSlot.LEGS));
        this.renderArmorPiece(matrixStackIn, bufferIn, entityIn, EquipmentSlot.FEET, packedLightIn, this.getModelFromSlot(EquipmentSlot.FEET));
        this.renderArmorPiece(matrixStackIn, bufferIn, entityIn, EquipmentSlot.HEAD, packedLightIn, this.getModelFromSlot(EquipmentSlot.HEAD));
    }

    private A getModelFromSlot(EquipmentSlot slot) {
        return this.isLegSlot(slot) ? this.innerModel : this.outerModel;
    }

    private boolean isLegSlot(EquipmentSlot slotIn) {
        return slotIn == EquipmentSlot.LEGS;
    }

    @Override
    public void renderArmorPiece(PoseStack matrixIn, MultiBufferSource bufferIn, T entityIn, EquipmentSlot slotIn, int packedLightIn, A model) {
        ItemStack itemstack = entityIn.getItemBySlot( slotIn);
        boolean hasEffect = itemstack.hasFoil();

        if (itemstack.getItem() instanceof ArmorItem) {
            Model actualModel = getArmorModelHook(entityIn, itemstack, slotIn, model);
            ArmorItem armoritem = (ArmorItem)itemstack.getItem();
            if (armoritem.getSlot() ==  slotIn) {

                this.getParentModel().copyPropertiesTo(model);
                this.setPartVisibility(model, slotIn);

                // ideally, this would replace the getArmorModel
                if (itemstack.getCapability(ModelSpecCapability.RENDER).isPresent()) {
                    itemstack.getCapability(ModelSpecCapability.RENDER).ifPresent(spec->{
                        if (spec.getSpecType() == SpecType.ARMOR_SKIN) {
                            Color colour = spec.getColorFromItemStack();
                            this.renderModel(matrixIn, bufferIn, packedLightIn, hasEffect, (A)actualModel, colour.r, colour.g, colour.b, ((IArmorModelSpecNBT) spec).getArmorTexture());
                            this.renderModel(matrixIn, bufferIn, packedLightIn, hasEffect, (A)actualModel, colour.r, colour.g, colour.b, ((IArmorModelSpecNBT) spec).getArmorTexture());
                        } else {
                            this.renderModel(matrixIn, bufferIn, packedLightIn, hasEffect, (A)actualModel, 1.0F, 1.0F, 1.0F, TextureAtlas.LOCATION_BLOCKS);
                        }
                    });
                } else {
                    super.renderArmorPiece(matrixIn, bufferIn, entityIn, slotIn, packedLightIn, model);
                }
            }
        }
    }

////
//    @Override
//    public void renderArmorPiece(PoseStack matrixIn, MultiBufferSource bufferIn, T entityIn, EquipmentSlot  slotIn, int packedLightIn, A model) {
//        ItemStack itemstack = entityIn.getItemBySlot( slotIn);
//        boolean hasEffect = itemstack.hasFoil();
//
//        if (itemstack.getItem() instanceof ArmorItem) {
//            ArmorItem armoritem = (ArmorItem)itemstack.getItem();
//            if (armoritem.getSlot() ==  slotIn) {
//                // ideally, this would replace the getArmorModel
//                if (itemstack.getCapability(ModelSpecCapability.RENDER).isPresent()) {
//                    itemstack.getCapability(ModelSpecCapability.RENDER).ifPresent(spec->{
//                        // gets the actual model from the
//                        Model actualModel = this.getArmorModelHook(entityIn, itemstack, slotIn, model);
//                        this.getParentModel().copyPropertiesTo(actualModel);
//                        this.setPartVisibility(actualModel, slotIn);
//                        if (spec.getSpecType() == SpecType.ARMOR_SKIN) {
//                            Color colour = spec.getColorFromItemStack();
//                            renderModel(matrixIn, bufferIn, packedLightIn, hasEffect, actualModel, colour.r, colour.g, colour.b, this.getArmorResource(entityIn, itemstack, slotIn, null));
//                            renderModel(matrixIn, bufferIn, packedLightIn, hasEffect, actualModel, colour.r, colour.g, colour.b, this.getArmorResource(entityIn, itemstack, slotIn, "overlay"));
//                        } else {
//                            renderModel(matrixIn, bufferIn, packedLightIn, hasEffect, actualModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(entityIn, itemstack, slotIn, null));
//                        }
//                    });
//                } else {
//                    super.renderArmorPiece(matrixIn, bufferIn, entityIn, slotIn, packedLightIn, model);
//                }
//            }
//        }
//    }

//    /**
//     * Sets the render type
//     *
//     * @param matrixStackIn
//     * @param bufferIn
//     * @param packedLightIn
//     * @param glintIn
//     * @param modelIn
//     * @param red
//     * @param green
//     * @param blue
//     * @param armorResource
//     */

    private void renderModel(PoseStack matrixIn, MultiBufferSource bufferIn, int packedLightIn, boolean hasEffect, Model model, float red, float green, float blue, ResourceLocation armorResource) {
        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(armorResource), false, hasEffect);
        model.renderToBuffer(matrixIn, vertexconsumer, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }


//    private void renderArmor(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, A modelIn, float red, float green, float blue, ResourceLocation armorResource) {
//        RenderType renderType;
//        if (armorResource == TextureAtlas.field_110575_b) {
//            renderType = Atlases.func_228785_j_();
//        } else {
//            renderType = RenderType.func_228640_c_(armorResource);
//        }
//        VertexConsumer ivertexbuilder = ItemRenderer.func_229113_a_(bufferIn, renderType, false, glintIn);
//        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
//    }

    /**
     * More generic ForgeHook version of the above function, it allows for Items to have more control over what texture they provide.
     *
     * @param entity Entity wearing the armor
     * @param stack  ItemStack for the armor
     * @param slot   Slot ID that the item is in
     * @param type   Subtype, can be null or "overlay"
     * @return ResourceLocation pointing at the armor's texture
     */
    @Override
    public ResourceLocation getArmorResource(Entity entity, @Nonnull ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        return stack.getCapability(ModelSpecCapability.RENDER).map(spec->{
            if (spec.getSpecType() == SpecType.ARMOR_SKIN && spec instanceof IArmorModelSpecNBT) {
                return ((IArmorModelSpecNBT) spec).getArmorTexture();
            }
            return TextureAtlas.LOCATION_BLOCKS;
        }).orElse(super.getArmorResource(entity, stack, slot, type));
    }

    @Override
    protected Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model) {
        return super.getArmorModelHook(entity, itemStack, slot, model);
    }
}