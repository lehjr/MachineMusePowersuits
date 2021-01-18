package com.github.lehjr.numina.client.render.item;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ArmorStandModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

public class MPAArmorStationItemRenderer extends ItemStackTileEntityRenderer {
    ArmorStandModel model = new ArmorStandModel(0.5F);

    public MPAArmorStationItemRenderer() {
        model.bipedHeadwear.showModel = false;
    }

    @Override
    public void func_239207_a_(ItemStack itemStack,
                               ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLightIn, int packedOverlayIn) {
        // push and pop needed here?  does this scale setting need to be here?
        matrixStack.push();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        IVertexBuilder ivertexbuilder1 = ItemRenderer.getEntityGlintVertexBuilder(renderTypeBuffer,  this.model.getRenderType(NuminaConstants.TEXTURE_ARMOR_STAND), false, itemStack.hasEffect());
        this.model.render(matrixStack, ivertexbuilder1, packedLightIn, packedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
    }
}