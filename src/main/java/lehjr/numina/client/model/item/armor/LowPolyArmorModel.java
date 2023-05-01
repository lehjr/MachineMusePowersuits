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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LowPolyArmorModel <T extends LivingEntity> extends HumanoidModel<T> {
    public CompoundTag renderSpec = null;
    public EquipmentSlot visibleSection = EquipmentSlot.HEAD;
    Map<ModelPart, RenderOBJPart> mpToRp = new HashMap<>();
    List<ModelPart> headParts = new ArrayList<>();
    List<ModelPart> bodyParts = new ArrayList<>();
//    ModelTransformCalibration CALIBRATION;

    public LowPolyArmorModel(ModelPart root) {
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
//        if (this.young) {
//            poseStack.pushPose();
//            if (this.scaleHead) {
//                float f = 1.5F / this.babyHeadScale;
//                poseStack.scale(f, f, f);
//            }
//
//            poseStack.translate(0.0F, this.babyYHeadOffset * MathUtils.DIV_16F, this.babyZHeadOffset * MathUtils.DIV_16F);
//            this.headParts().forEach((p_102081_) -> {
//                p_102081_.render(poseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
//            });
//            poseStack.popPose();
//            poseStack.pushPose();
//            float f1 = 1.0F / this.babyBodyScale;
//            poseStack.scale(f1, f1, f1);
//            poseStack.translate(0.0F, this.bodyYOffset * MathUtils.DIV_16F, 0.0F);
//            this.bodyParts().forEach((p_102071_) -> {
//                p_102071_.render(poseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
//            });
//            poseStack.popPose();
//        } else {
            this.headParts().forEach((p_102061_) -> {
                p_102061_.render(poseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
            });
            this.bodyParts().forEach((p_102051_) -> {
                p_102051_.render(poseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
            });
//        }



//        Transformation transform = CALIBRATION.getTransform();
//        if (transform != Transformation.identity()) {
//            transform.push(poseStack);
//        }

        try {
            this.headParts().forEach((part) -> {
                if (part != null && part instanceof RenderOBJPart) {
                    poseStack.pushPose();
                    float offset = ((RenderOBJPart) part).yOffset * MathUtils.DIV_16F;
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
                if (part != null && part instanceof RenderOBJPart) {
                    poseStack.pushPose();
                    float offset = ((RenderOBJPart) part).yOffset * MathUtils.DIV_16F;
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
        RenderOBJPart rp = new RenderOBJPart(this, mp);
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
