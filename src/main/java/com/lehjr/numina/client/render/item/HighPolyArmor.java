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

package com.lehjr.numina.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public class HighPolyArmor<T extends LivingEntity> extends HumanoidModel<T> {
    public CompoundTag renderSpec = null;
    public EquipmentSlot visibleSection = EquipmentSlot.HEAD;

    public HighPolyArmor(ModelPart pRoot) {
        super(pRoot);
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
        return super.headParts();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return super.bodyParts();
    }

    // packed overlay is for texture UV's ... see OverlayTexture.getPackedUV
    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.headParts().forEach((part) -> part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
        this.bodyParts().forEach((part) -> part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
    }

    public void init() {
        clearAndAddChild(head, "head", 0.0F, 24.0F, 0.0F);
        clearAndAddChild(body, "body", 0.0F, 24.0F, 0.0F);
        clearAndAddChild(rightArm, "right_leg", 5.0F, 24.0F, 0.0F);
        clearAndAddChild(leftArm, "left_arm",-5.0F, 24.0F, 0.0F);
        clearAndAddChild(rightLeg, "right_leg",1.9F, 12.0F, 0.0F);
        clearAndAddChild(leftLeg, "left_leg", -1.9F, 12.0F, 0.0F);
        hat.cubes.clear();
    }

    public void clearAndAddChild(ModelPart mp, String partName, float x, float y, float z) {
        mp.cubes.clear();
        RenderPart rp = new RenderPart(this, mp);
        rp.children = new HashMap<>() {{
            put(partName, rp);
        }};
        rp.setPos(x, y, z);
    }
}