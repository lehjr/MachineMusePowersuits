package com.lehjr.numina.client.render.item;

import com.lehjr.numina.client.model.helper.ModelTransformCalibration;
import com.lehjr.numina.client.model.item.armor.ArmorModelInstance;
import com.lehjr.numina.client.model.item.armor.HighPolyArmor;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.capabilities.render.modelspec.JavaPartSpec;
import com.lehjr.numina.common.capabilities.render.modelspec.NuminaModelSpecRegistry;
import com.lehjr.numina.common.capabilities.render.modelspec.ObjPartSpec;
import com.lehjr.numina.common.capabilities.render.modelspec.PartSpecBase;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.tags.NBTTagAccessor;
import com.lehjr.numina.common.utils.ItemUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;


public class NuminaArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    ModelTransformCalibration CALIBRATION;

    public NuminaArmorLayer(RenderLayerParent<T, M> entityRenderer, HumanoidArmorLayer<T, M, A> vanillaLayer, ModelManager manager) {
        super(entityRenderer, vanillaLayer.innerModel, vanillaLayer.outerModel, manager);
        this.CALIBRATION = ModelTransformCalibration.CALIBRATION;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Arrays.stream(EquipmentSlot.values()).filter(equipmentSlot -> equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR).forEach(slot ->
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
        IModelSpec renderCapability = getRenderCapability(itemstack);
        if (itemstack.getItem() instanceof ArmorItem armoritem && renderCapability != null) {
            if (armoritem.getType().getSlot() == slotIn) {
                if (doesBypassRender(itemstack)) {
                    return;
                }


                    CompoundTag renderTag = renderCapability.getRenderTagOrDefault();
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
                                                color.getARGBInt());
                                    }
                                    poseStack.popPose();
                                } else if (partSpec instanceof ObjPartSpec) {
                                    Transformation transform = CALIBRATION.getTransform();
                                    if (transform != Transformation.identity()) {
                                        poseStack.pushTransformation(transform);
                                    }

                                    HighPolyArmor highPolyArmor = ArmorModelInstance.getInstance();
                                    highPolyArmor.copyPropertiesFrom(getParentModel());
                                    VertexConsumer consumer = getVertexConsumer(bufferIn, InventoryMenu.BLOCK_ATLAS, glow);
                                    highPolyArmor.renderToBuffer((ObjPartSpec) partSpec, tag, poseStack, consumer, glow ? NuminaConstants.FULL_BRIGHTNESS : packedLightIn, OverlayTexture.NO_OVERLAY, color);

                                    if (transform != Transformation.identity()) {
                                        poseStack.popPose();
                                    }
                                }
                            }
                        }
                    }
            }
        } else {
            super.renderArmorPiece(poseStack, bufferIn, entityIn, slotIn, packedLightIn, model);
        }
    }

    // FIXME: move to Numina
    final ResourceLocation powersuitsTransparentArmor = ResourceLocation.fromNamespaceAndPath("powersuits", "transparent_armor");

    boolean doesBypassRender(@Nonnull ItemStack itemStack) {
        IModularItem iModularItem = itemStack.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
        if (iModularItem != null) {
            return iModularItem.isModuleOnline(powersuitsTransparentArmor);
        }
        return false;
    }

    VertexConsumer getVertexConsumer(MultiBufferSource buffer, ResourceLocation location, boolean glow) {
        if (glow) {
//            return ItemRenderer.getArmorFoilBuffer(buffer, RenderType.beaconBeam(location, true), false, glow);
            return buffer.getBuffer(RenderType.beaconBeam(location, true));
        }
//        return ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucentCull(location), false, glow);
        return buffer.getBuffer(RenderType.entityTranslucentCull(location));
    }

    @Nullable
    IModelSpec getRenderCapability(ItemStack itemStack) {
        return itemStack.getCapability(NuminaCapabilities.RENDER);
    }
}

