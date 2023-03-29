package lehjr.powersuits.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.powersuits.client.model.block.TinkerTableModel;
import lehjr.powersuits.client.model.item.IconModel;
import lehjr.powersuits.client.model.item.PowerFistModel2;
import lehjr.powersuits.common.base.MPSObjects;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MPSBEWLR extends BlockEntityWithoutLevelRenderer {
    TinkerTableModel tinkerTableModel;
    PowerFistModel2 powerFistModelRight;
    PowerFistModel2 powerFistModelLeft;
    public static final ResourceLocation powerFistIcon = new ResourceLocation(MPSConstants.MOD_ID, "textures/item/handitem.png");
    IconModel icon = new IconModel();

    public MPSBEWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), new EntityModelSet());
        tinkerTableModel = new TinkerTableModel();
        powerFistModelRight = new PowerFistModel2(true);
        powerFistModelLeft = new PowerFistModel2(false);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = itemStack.getItem();
        /** Important: the render types used here are not the same for each transform due to issues each one has in different perspectives (rendering issues with the screens) */
        if (item.equals(MPSObjects.TINKER_TABLE_ITEM.get())) {
            switch(transformType) {
                case FIRST_PERSON_LEFT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case FIRST_PERSON_RIGHT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case GUI -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case HEAD -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case FIXED -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case GROUND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case THIRD_PERSON_LEFT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
                case THIRD_PERSON_RIGHT_HAND -> tinkerTableModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
            }
        }

        if (item.equals(MPSObjects.POWER_FIST.get())) {
            if (transformType == ItemTransforms.TransformType.GUI) {
                icon.renderToBuffer(poseStack,
                        buffer.getBuffer(RenderType.entityTranslucentCull(powerFistIcon)),
                        LightTexture.FULL_BRIGHT,
//                        packedOverlay,
                        OverlayTexture.WHITE_OVERLAY_V,
                        1F, 1F, 1F, 1F);
            } else if (transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND) {
                powerFistModelLeft.setNeutralPose();
                powerFistModelLeft.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(PowerFistModel2.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);

            } else {
                powerFistModelRight.setNeutralPose();
                powerFistModelRight.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityTranslucentCull(PowerFistModel2.TEXTURE)), packedLight, packedOverlay, 1F, 1F, 1F, 1F);
            }
        }
    }








}
