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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 9:24 PM, 11/07/13
 * <p>
 * Ported to Java by lehjr on 11/7/16.
 * <p>
 */
@OnlyIn(Dist.CLIENT)
public class HighPolyArmor<T extends LivingEntity> extends HumanoidModel<T> {
    public CompoundTag renderSpec = null;
    public EquipmentSlot visibleSection = EquipmentSlot.HEAD;
    Map<ModelPart, RenderPart> mpToRp = new HashMap<>();
    List<ModelPart> headParts = new ArrayList<>();
    List<ModelPart> bodyParts = new ArrayList<>();
//    ModelTransformCalibration CALIBRATION;

    public HighPolyArmor(ModelPart root) {
        super(root, RenderType::itemEntityTranslucentCull);
//        this.CALIBRATION = new ModelTransformCalibration();
        init();
    }


    public CompoundTag getRenderSpec() {
        return this.renderSpec;
    }

    public void setRenderSpec(CompoundTag nbt) {
        renderSpec = nbt;
    }

    public EquipmentSlot getVisibleSection() {
        return this.visibleSection;
    }

    public void setVisibleSection(EquipmentSlot equipmentSlot) {
        this.visibleSection = equipmentSlot;
        this.hat.visible = false;

        // This may not actually be needed
        this.head.visible = equipmentSlot == EquipmentSlot.HEAD;
        this.body.visible = equipmentSlot == EquipmentSlot.CHEST;
        this.rightArm.visible = equipmentSlot == EquipmentSlot.CHEST;
        this.leftArm.visible = equipmentSlot == EquipmentSlot.CHEST;
        this.rightLeg.visible = equipmentSlot == EquipmentSlot.LEGS;
        this.leftLeg.visible = equipmentSlot == EquipmentSlot.LEGS;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return this.headParts;
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return this.bodyParts;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
//        Transformation transform = CALIBRATION.getTransform();
//        if (transform != Transformation.identity()) {
//            transform.push(poseStack);
//        }

        try {
            this.headParts().forEach((part) -> {
                if (part != null && part instanceof RenderPart) {
                    poseStack.pushPose();
                    float offset = ((RenderPart) part).yOffset * MathUtils.DIV_16F;
                    poseStack.translate(0,  -offset, 0);

                    part.render(poseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);

                    poseStack.popPose();

                }
            });

        } catch (Exception ignored) {
            NuminaLogger.logException("error rendering head parts: ", ignored);
        }

        try {
            this.bodyParts().forEach((part) -> {
                if (part != null && part instanceof RenderPart) {
                    poseStack.pushPose();
                    float offset = ((RenderPart) part).yOffset * MathUtils.DIV_16F;
                    poseStack.translate(0,  -offset, 0);
                    part.render(poseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
                    poseStack.popPose();
                }
            });
        } catch (Exception ignored) {
            NuminaLogger.logException("error rendering body parts: ", ignored);
        }
//        if (transform != Transformation.identity()) {
//            poseStack.popPose();
//        }
    }

    @Override
    protected ModelPart getArm(HumanoidArm pSide) {
        return mpToRp.get(super.getArm(pSide));
    }

    @Override
    public ModelPart getHead() {
        return mpToRp.get(head);
    }

    public <M extends HumanoidModel<T>> void copyPropertiesFrom(M otherModel) {
        if (!mpToRp.isEmpty()) {
            this.leftArmPose = otherModel.leftArmPose;
            this.rightArmPose = otherModel.rightArmPose;
            this.crouching = otherModel.crouching;
            mpToRp.get(head).copyFrom(otherModel.head);
            mpToRp.get(body).copyFrom(otherModel.body);
            mpToRp.get(rightArm).copyFrom(otherModel.rightArm);
            mpToRp.get(leftArm).copyFrom(otherModel.leftArm);
            mpToRp.get(rightLeg).copyFrom(otherModel.rightLeg);
            mpToRp.get(leftLeg).copyFrom(otherModel.leftLeg);
        }
    }

    public void init() {
        clearAndAddChildWithInitialOffsets(head, "head", 0.0F, 25.0F, 0.0F);
        clearAndAddChildWithInitialOffsets(body, "body", 0.0F, 24.0F, 0.0F);
        clearAndAddChildWithInitialOffsets(rightArm, "right_arm", 5.0F, 24.0F, 0.0F);
        clearAndAddChildWithInitialOffsets(leftArm, "left_arm",-5.0F, 24.0F, 0.0F);
        clearAndAddChildWithInitialOffsets(rightLeg, "right_leg",1.9F, 12.0F, 0.0F);
        clearAndAddChildWithInitialOffsets(leftLeg, "left_leg", -1.9F, 12.0F, 0.0F);
    }

    public void clearAndAddChildWithInitialOffsets(ModelPart mp, String partName, float x, float y, float z) {
        RenderPart rp = new RenderPart(this, mp);
        rp.setPos(x, y, z);

        rp.xOffset = x;
        rp.yOffset = y;
        rp.zOffset = z;
        mp.children = new HashMap<>() {{
            put("mps_" + partName, rp);
        }};

        mpToRp.put(mp, rp);

        switch(partName) {
            case "head":
                headParts.add(rp);
                break;
            case "body":
            case "right_arm":
            case "left_arm":
            case "right_leg":
            case "left_leg":
                bodyParts.add(rp);
                break;
            default:
                break;
        }
    }
}