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

package lehjr.numina.client.model.item.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lehjr.numina.common.capabilities.render.IArmorModelSpecNBT;
import lehjr.numina.common.capabilities.render.ModelSpecNBTCapability;
import lehjr.numina.common.capabilities.render.modelspec.EnumSpecType;
import lehjr.numina.common.math.Colour;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NuminaArmorLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends BipedArmorLayer<T, M, A> {
    public NuminaArmorLayer(IEntityRenderer<T, M> entityRenderer, A modelLeggings, A modelArmor) {
        super(entityRenderer, modelLeggings, modelArmor);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.renderArmorPiece(matrixStackIn, bufferIn, entityIn, EquipmentSlotType.CHEST, packedLightIn, this.getModelFromSlot(EquipmentSlotType.CHEST));
        this.renderArmorPiece(matrixStackIn, bufferIn, entityIn, EquipmentSlotType.LEGS, packedLightIn, this.getModelFromSlot(EquipmentSlotType.LEGS));
        this.renderArmorPiece(matrixStackIn, bufferIn, entityIn, EquipmentSlotType.FEET, packedLightIn, this.getModelFromSlot(EquipmentSlotType.FEET));
        this.renderArmorPiece(matrixStackIn, bufferIn, entityIn, EquipmentSlotType.HEAD, packedLightIn, this.getModelFromSlot(EquipmentSlotType.HEAD));
    }

    private A getModelFromSlot(EquipmentSlotType slot) {
        return this.isLegSlot(slot) ? this.innerModel : this.outerModel;
    }

    private boolean isLegSlot(EquipmentSlotType slotIn) {
        return slotIn == EquipmentSlotType.LEGS;
    }

    @Override
    public void renderArmorPiece(MatrixStack matrixIn, IRenderTypeBuffer bufferIn, T entityIn, EquipmentSlotType  slotIn, int packedLightIn, A model) {
        ItemStack itemstack = entityIn.getItemBySlot( slotIn);
        boolean hasEffect = itemstack.hasFoil();

        if (itemstack.getItem() instanceof ArmorItem) {
            ArmorItem armoritem = (ArmorItem)itemstack.getItem();
            if (armoritem.getSlot() ==  slotIn) {
                // ideally, this would replace the getArmorModel
                if (itemstack.getCapability(ModelSpecNBTCapability.RENDER).isPresent()) {
                    itemstack.getCapability(ModelSpecNBTCapability.RENDER).ifPresent(spec->{
                        // gets the actual model from the
                        A actualModel = this.getArmorModelHook(entityIn, itemstack, slotIn, model);
                        this.getParentModel().copyPropertiesTo(actualModel);
                        this.setPartVisibility(actualModel, slotIn);
                        if (spec.getSpecType() == EnumSpecType.ARMOR_SKIN) {
                            Colour colour = spec.getColorFromItemStack();
                            renderArmor(matrixIn, bufferIn, packedLightIn, hasEffect, actualModel, colour.r, colour.g, colour.b, this.getArmorResource(entityIn, itemstack, slotIn, null));
                            renderArmor(matrixIn, bufferIn, packedLightIn, hasEffect, actualModel, colour.r, colour.g, colour.b, this.getArmorResource(entityIn, itemstack, slotIn, "overlay"));
                        } else {
                            renderArmor(matrixIn, bufferIn, packedLightIn, hasEffect, actualModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(entityIn, itemstack, slotIn, null));
                        }
                    });
                } else {
                    super.renderArmorPiece(matrixIn, bufferIn, entityIn, slotIn, packedLightIn, model);
                }
            }
        }
    }

    /**
     * Sets the render type
     *
     * @param matrixStackIn
     * @param bufferIn
     * @param packedLightIn
     * @param glintIn
     * @param modelIn
     * @param red
     * @param green
     * @param blue
     * @param armorResource
     */
    private void renderArmor(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, boolean glintIn, A modelIn, float red, float green, float blue, ResourceLocation armorResource) {
        RenderType renderType;
        if (armorResource == AtlasTexture.LOCATION_BLOCKS) {
            renderType = Atlases.translucentCullBlockSheet();
        } else {
            renderType = RenderType.entityCutoutNoCull(armorResource);
        }
        IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, renderType, false, glintIn);
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

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
    public ResourceLocation getArmorResource(Entity entity, @Nonnull ItemStack stack, EquipmentSlotType slot, @Nullable String type) {
        return stack.getCapability(ModelSpecNBTCapability.RENDER).map(spec->{
            if (spec.getSpecType() == EnumSpecType.ARMOR_SKIN && spec instanceof IArmorModelSpecNBT) {
                return new ResourceLocation(((IArmorModelSpecNBT) spec).getArmorTexture());
            }
            return AtlasTexture.LOCATION_BLOCKS;
        }).orElse(super.getArmorResource(entity, stack, slot, type));
    }

    @Override
    protected A getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlotType slot, A model) {
        return super.getArmorModelHook(entity, itemStack, slot, model);
    }
}