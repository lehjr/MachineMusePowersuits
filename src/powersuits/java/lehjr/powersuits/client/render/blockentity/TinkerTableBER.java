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

package lehjr.powersuits.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
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
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
                    poseStack.translate(-1, 0, -1);
                    break;
                case SOUTH:
//                    poseStack.mulPose(Vector3f.YP.rotationDegrees(90f));
                    poseStack.mulPose(Vector3f.YP.rotationDegrees( 90));
                    poseStack.translate(-1, 0, 0);
                    break;
                case NORTH:
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(270f));
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