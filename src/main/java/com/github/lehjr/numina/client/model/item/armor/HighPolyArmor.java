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

package com.github.lehjr.numina.client.model.item.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 9:24 PM, 11/07/13
 * <p>
 * Ported to Java by lehjr on 11/7/16.
 * <p>
 * FIXME: IMPORTANT!!!!: Note that SmartMoving will mess up the rendering here and the armor's yaw will not change with the player's yaw but will be fine with it not installed.
 */
@OnlyIn(Dist.CLIENT)
public class HighPolyArmor extends BipedModel {
    public CompoundNBT renderSpec = null;
    public EquipmentSlotType visibleSection = EquipmentSlotType.HEAD;

    public HighPolyArmor() {
        super(0);
        init();
    }

    public CompoundNBT getRenderSpec() {
        return this.renderSpec;
    }

    public void setRenderSpec(CompoundNBT nbt) {
        renderSpec = nbt;
    }

    public EquipmentSlotType getVisibleSection() {
        return this.visibleSection;
    }

    public void setVisibleSection(EquipmentSlotType equipmentSlot) {
        this.visibleSection = equipmentSlot;
        this.hat.visible = false;

        // This may not actually be needed
        this.head.visible = equipmentSlot == EquipmentSlotType.HEAD;
        this.body.visible = equipmentSlot == EquipmentSlotType.CHEST;
        this.rightArm.visible = equipmentSlot == EquipmentSlotType.CHEST;
        this.leftArm.visible = equipmentSlot == EquipmentSlotType.CHEST;
        this.rightLeg.visible = equipmentSlot == EquipmentSlotType.LEGS;
        this.leftLeg.visible = equipmentSlot == EquipmentSlotType.LEGS;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return super.headParts();
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return super.bodyParts();
    }

    // packed overlay is for texture UV's ... see OverlayTexture.getPackedUV
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.headParts().forEach((part) -> part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
        this.bodyParts().forEach((part) -> part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
    }

    public void init() {
        clearAndAddChild(head, 0.0F, 24.0F, 0.0F);
        clearAndAddChild(body,0.0F, 24.0F, 0.0F);
        clearAndAddChild(rightArm,5.0F, 24.0F, 0.0F);
        clearAndAddChild(leftArm,-5.0F, 24.0F, 0.0F);
        clearAndAddChild(rightLeg,1.9F, 12.0F, 0.0F);
        clearAndAddChild(leftLeg,-1.9F, 12.0F, 0.0F);
        hat.cubes.clear();
    }

    public void clearAndAddChild(ModelRenderer mr, float x, float y, float z) {
        mr.cubes.clear();
        RenderPart rp = new RenderPart(this, mr);
        mr.addChild(rp);
        rp.setPos(x, y, z);
    }
}