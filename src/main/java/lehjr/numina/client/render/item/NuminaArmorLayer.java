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
import com.mojang.math.Transformation;
import lehjr.numina.client.model.helper.ModelTransformCalibration;
import lehjr.numina.client.model.item.armor.ArmorModelInstance;
import lehjr.numina.client.model.item.armor.HighPolyArmor;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.capabilities.render.modelspec.JavaPartSpec;
import lehjr.numina.common.capabilities.render.modelspec.NuminaModelSpecRegistry;
import lehjr.numina.common.capabilities.render.modelspec.ObjlPartSpec;
import lehjr.numina.common.capabilities.render.modelspec.PartSpecBase;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.tags.NBTTagAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Optional;

public class NuminaArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    ModelTransformCalibration CALIBRATION;

    public NuminaArmorLayer(RenderLayerParent<T, M> entityRenderer, A modelLeggings, A modelArmor) {
        super(entityRenderer, modelLeggings, modelArmor);
        this.CALIBRATION = ModelTransformCalibration.CALIBRATION;
    }

    Optional<IModelSpec> getRenderCapability(ItemStack itemStack) {
        return itemStack.getCapability(NuminaCapabilities.RENDER)
                .filter(IModelSpec.class::isInstance)
                .map(IModelSpec.class::cast);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Arrays.stream(EquipmentSlot.values()).filter(equipmentSlot -> equipmentSlot.isArmor()).forEach(slot -> {
            renderArmorPiece(matrixStackIn, bufferIn, entityIn, slot, packedLightIn, this.getModelFromSlot(slot));
        });
    }

    private A getModelFromSlot(EquipmentSlot slot) {
        return this.isLegSlot(slot) ? this.innerModel : this.outerModel;
    }

    private boolean isLegSlot(EquipmentSlot slotIn) {
        return slotIn == EquipmentSlot.LEGS;
    }

    @Override
    protected void setPartVisibility(A model, EquipmentSlot pSlot) {
        model.setAllVisible(false);
        switch (pSlot) {
            case HEAD:
                model.head.visible = true;
                model.hat.visible = true;
                break;
            case CHEST:
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
                break;
            case LEGS:
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
                break;
            case FEET:
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
        }
    }

    @Override
    public void renderArmorPiece(PoseStack poseStack, MultiBufferSource bufferIn, T entityIn, EquipmentSlot slotIn, int packedLightIn, A model) {
        ItemStack itemstack = entityIn.getItemBySlot( slotIn);
        Optional<IModelSpec> renderCapabity = getRenderCapability(itemstack);

        if (itemstack.getItem() instanceof ArmorItem armoritem && itemstack.getCapability(NuminaCapabilities.RENDER).isPresent()) {
            if (armoritem.getSlot() == slotIn) {
                renderCapabity.ifPresent(renderCap->{
                    CompoundTag renderTag = renderCap.getRenderTag();
                    if (renderTag != null && !renderTag.isEmpty()) {
                        int[] colors = renderTag.getIntArray(TagConstants.COLORS);
                        if (colors.length == 0) {
                            colors = new int[]{Color.WHITE.getARGBInt()};
                        }

                        for (CompoundTag tag : NBTTagAccessor.getValues(renderTag)) {
                            PartSpecBase partSpec = NuminaModelSpecRegistry.getInstance().getPart(tag);
                            if (partSpec != null) {
//                                System.out.println("partSpec class: " + partSpec.getClass());

                                int partColor;
                                int ix = partSpec.getColourIndex(tag);
                                // checks the range of the index to avoid errors OpenGL or crashing
                                if (ix < colors.length && ix >= 0) {
                                    partColor = colors[ix];
                                } else {
                                    partColor = -1;
                                }
                                Color color = new Color(partColor);
                                boolean glow = partSpec.getGlow(tag);

                                if (partSpec instanceof JavaPartSpec) {
                                    ResourceLocation location = ((JavaPartSpec) partSpec).getTextureLocation();
                                    this.getParentModel().copyPropertiesTo(model);
                                    ModelPart part = partSpec.getBinding().getTarget().apply(model);
                                    poseStack.pushPose();
                                    if (part != null) {
                                        part.translateAndRotate(poseStack);
                                        VertexConsumer consumer = getVertexConsumer(bufferIn, location, glow);
                                        part.compile(poseStack.last(), consumer,
                                                glow ? NuminaConstants.FULL_BRIGHTNESS : packedLightIn,
                                                OverlayTexture.NO_OVERLAY,
                                                color.r, color.g, color.b, color.a);
                                    }
                                    poseStack.popPose();
                                } else if (partSpec instanceof ObjlPartSpec) {

                                    Transformation transform = CALIBRATION.getTransform();
                                    if (transform != Transformation.identity()) {
                                        poseStack.pushTransformation(transform);
                                    }


                                    HighPolyArmor highPolyArmor = ArmorModelInstance.getInstance();
                                    highPolyArmor.copyPropertiesFrom(getParentModel());
                                    VertexConsumer consumer = getVertexConsumer(bufferIn, TextureAtlas.LOCATION_BLOCKS, glow);
                                    highPolyArmor.renderToBuffer((ObjlPartSpec) partSpec, tag, poseStack, consumer, glow ? NuminaConstants.FULL_BRIGHTNESS : packedLightIn, OverlayTexture.NO_OVERLAY, color);

                                    if (transform != Transformation.identity()) {
                                        poseStack.popPose();
                                    }

                                }
                            } else {
                                System.out.println("render tag with null partSpec: " + tag);
                            }
                        }
                    }
                });
            }
        } else {
            super.renderArmorPiece(poseStack, bufferIn, entityIn, slotIn, packedLightIn, model);
        }
    }

    VertexConsumer getVertexConsumer(MultiBufferSource buffer, ResourceLocation location, boolean glow) {
        if (glow) {
//            return ItemRenderer.getArmorFoilBuffer(buffer, RenderType.beaconBeam(location, true), false, glow);
            return buffer.getBuffer(RenderType.beaconBeam(location, true));
        }
//        return ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucentCull(location), false, glow);
        return buffer.getBuffer(RenderType.entityTranslucentCull(location));
    }
}

