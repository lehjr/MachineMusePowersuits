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
        this.bipedHeadwear.showModel = false;

        // This may not actually be needed
        this.bipedHead.showModel = equipmentSlot == EquipmentSlotType.HEAD;
        this.bipedBody.showModel = equipmentSlot == EquipmentSlotType.CHEST;
        this.bipedRightArm.showModel = equipmentSlot == EquipmentSlotType.CHEST;
        this.bipedLeftArm.showModel = equipmentSlot == EquipmentSlotType.CHEST;
        this.bipedRightLeg.showModel = equipmentSlot == EquipmentSlotType.LEGS;
        this.bipedLeftLeg.showModel = equipmentSlot == EquipmentSlotType.LEGS;
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return super.getHeadParts();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return super.getBodyParts();
    }

    // packed overlay is for texture UV's ... see OverlayTexture.getPackedUV
    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.getHeadParts().forEach((part) -> part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
        this.getBodyParts().forEach((part) -> part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
    }

    public void init() {
        clearAndAddChild(bipedHead, 0.0F, 24.0F, 0.0F);
        clearAndAddChild(bipedBody,0.0F, 24.0F, 0.0F);
        clearAndAddChild(bipedRightArm,5.0F, 24.0F, 0.0F);
        clearAndAddChild(bipedLeftArm,-5.0F, 24.0F, 0.0F);
        clearAndAddChild(bipedRightLeg,1.9F, 12.0F, 0.0F);
        clearAndAddChild(bipedLeftLeg,-1.9F, 12.0F, 0.0F);
        bipedHeadwear.cubeList.clear();
    }

    public void clearAndAddChild(ModelRenderer mr, float x, float y, float z) {
        mr.cubeList.clear();
        RenderPart rp = new RenderPart(this, mr);
        mr.addChild(rp);
        rp.setRotationPoint(x, y, z);
    }
}