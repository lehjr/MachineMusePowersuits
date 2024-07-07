package lehjr.powersuits.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import lehjr.powersuits.client.model.block.TinkerTableModel;
import lehjr.powersuits.common.block.TinkerTable;
import lehjr.powersuits.common.blockentity.TinkerTableBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class TinkerTableBER implements BlockEntityRenderer<TinkerTableBlockEntity> {
    TinkerTableModel model;

    public TinkerTableBER(BlockEntityRendererProvider.Context context) {
        model = new TinkerTableModel(TinkerTableModel.createLayer().bakeRoot());
    }

    @Override
    public void render(TinkerTableBlockEntity blockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        if (blockEntity != null) {
            switch (blockEntity.getBlockState().getValue(TinkerTable.FACING)) {
                case WEST:
//                    poseStack.mulPose(Vector3f.YP.rotationDegrees(0f));
                    break;
                case EAST:
                    poseStack.mulPose(Axis.YP.rotationDegrees(180f));
                    poseStack.translate(-1, 0, -1);
                    break;
                case SOUTH:
//                    poseStack.mulPose(Vector3f.YP.rotationDegrees(90f));
                    poseStack.mulPose(Axis.YP.rotationDegrees( 90));
                    poseStack.translate(-1, 0, 0);
                    break;
                case NORTH:
                    poseStack.mulPose(Axis.YP.rotationDegrees(270f));
                    poseStack.translate(0, 0, -1);
                default:
                    break;
            }
        }
        /** the render type used here prevents the translucent panels from culling other BER's like the chest */
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.itemEntityTranslucentCull(TinkerTableModel.TEXTURE)), packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}