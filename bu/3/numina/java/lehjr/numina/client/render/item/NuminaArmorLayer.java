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
import lehjr.numina.common.capabilities.render.modelspec.*;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.tags.NBTTagAccessor;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;


public class NuminaArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    ModelTransformCalibration CALIBRATION;

    public NuminaArmorLayer(RenderLayerParent<T, M> entityRenderer, HumanoidArmorLayer<T, M, A> vanillaLayer, ModelManager manager) {
        super(entityRenderer, vanillaLayer.innerModel, vanillaLayer.outerModel, manager);
                this.CALIBRATION = ModelTransformCalibration.CALIBRATION;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Arrays.stream(EquipmentSlot.values()).filter(equipmentSlot -> equipmentSlot.getType() == EquipmentSlot.Type.ARMOR).forEach(slot ->
                renderArmorPiece(matrixStackIn, bufferIn, entityIn, slot, packedLightIn, this.getModelFromSlot(slot)));
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
        ItemStack itemstack = ItemUtils.getItemFromEntitySlot(entityIn, slotIn);
        Optional<IModelSpec> renderCapabity = getRenderCapability(itemstack);
        if (itemstack.getItem() instanceof ArmorItem armoritem && NuminaCapabilities.getCapability(itemstack, NuminaCapabilities.RENDER).isPresent()) {
            if (armoritem.getType().getSlot() == slotIn) {
                if (doesBypassRender(itemstack)) {
                    return;
                }

                renderCapabity.ifPresent(renderCap->{
                    CompoundTag renderTag = renderCap.getRenderTagOrDefault();
//                    if (renderTag == null || renderTag.isEmpty()) {
//                        renderTag = renderCap.getDefaultRenderTag();
//                        if (renderTag != null && !renderTag.isEmpty()) {
//                            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CosmeticInfoPacketServerBound(slotIn, NuminaConstants.RENDER, renderTag));
//                        }
//                    }

                    if (renderTag != null && !renderTag.isEmpty()) {
                        int[] colors = renderTag.getIntArray(NuminaConstants.COLORS);
                        if (colors.length == 0) {
                            colors = new int[]{Color.WHITE.getARGBInt()};
                        }

                        for (CompoundTag tag : NBTTagAccessor.getValues(renderTag)) {
                            PartSpecBase partSpec = NuminaModelSpecRegistry.getInstance().getPart(tag);
                            if (partSpec != null) {
//                                NuminaLogger.logDebug("partSpec class: " + partSpec.getClass());

                                int partColor;
                                int ix = partSpec.getColorIndex(tag);
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
                                } else if (partSpec instanceof ObjPartSpec) {
                                    Transformation transform = CALIBRATION.getTransform();
                                    if (transform != Transformation.identity()) {
                                        poseStack.pushTransformation(transform);
                                    }

                                    HighPolyArmor highPolyArmor = ArmorModelInstance.getInstance();
                                    highPolyArmor.copyPropertiesFrom(getParentModel());
                                    VertexConsumer consumer = getVertexConsumer(bufferIn, TextureAtlas.LOCATION_BLOCKS, glow);
                                    highPolyArmor.renderToBuffer((ObjPartSpec) partSpec, tag, poseStack, consumer, glow ? NuminaConstants.FULL_BRIGHTNESS : packedLightIn, OverlayTexture.NO_OVERLAY, color);

                                    if (transform != Transformation.identity()) {
                                        poseStack.popPose();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        } else {
            super.renderArmorPiece(poseStack, bufferIn, entityIn, slotIn, packedLightIn, model);
        }
    }

    // FIXME: move to Numina
    final ResourceLocation powersuitsTransparentArmor = new ResourceLocation("powersuits", "transparent_armor");

    boolean doesBypassRender(@Nonnull ItemStack itemStack) {
        return NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.MODULAR_ITEM)
                .map(handler -> handler.isModuleOnline(powersuitsTransparentArmor))
                .orElse(false);
    }

    VertexConsumer getVertexConsumer(MultiBufferSource buffer, ResourceLocation location, boolean glow) {
        if (glow) {
//            return ItemRenderer.getArmorFoilBuffer(buffer, RenderType.beaconBeam(location, true), false, glow);
            return buffer.getBuffer(RenderType.beaconBeam(location, true));
        }
//        return ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucentCull(location), false, glow);
        return buffer.getBuffer(RenderType.entityTranslucentCull(location));
    }

    Optional<IModelSpec> getRenderCapability(ItemStack itemStack) {
        return NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.RENDER)
                .map(IModelSpec.class::cast);
    }
}

