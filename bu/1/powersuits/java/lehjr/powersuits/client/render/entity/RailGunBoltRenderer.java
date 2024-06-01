///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.powersuits.client.render.entity;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.mojang.math.Axis;
//import org.joml.Vector3f;
//import lehjr.numina.client.model.helper.ModelHelper;
//import lehjr.numina.client.model.obj.OBJBakedCompositeModel;
//import lehjr.numina.common.constants.NuminaConstants;
//import lehjr.numina.common.math.Color;
//import lehjr.powersuits.common.constants.MPSConstants;
//import lehjr.powersuits.common.entity.RailgunBoltEntity;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.block.model.BakedQuad;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.client.resources.model.BlockModelRotation;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.RandomSource;
//import net.minecraftforge.common.util.NonNullLazy;
//
//public class RailGunBoltRenderer extends net.minecraft.client.renderer.entity.EntityRenderer<RailgunBoltEntity> {
//
//
//    static final Color color = new Color(0.631F, 0.615F, 0.58F, 1F);
//    static final ResourceLocation modelLocation = new ResourceLocation(MPSConstants.MOD_ID, "models/entity/bolt.obj");
//    // NonNullLazy doesn't init until called
//    public static final NonNullLazy<OBJBakedCompositeModel> modelBolt = NonNullLazy.of(() -> ModelHelper.loadBakedModel(BlockModelRotation.X0_Y0, null, modelLocation));
//    protected static final RandomSource rand = RandomSource.create();
//
//    public RailGunBoltRenderer(EntityRendererProvider.Context renderManager) {
//        super(renderManager);
//    }
//
//
//    @Override
//    public void render(RailgunBoltEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
////        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//
//        float size = 10;
//        float scale = (float) (size / 16.0F);
//        matrixStackIn.pushPose();
////        matrixStackIn.translate(0.5, 0.5, 0.5);
//        matrixStackIn.scale(scale, scale, scale);
//
////        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
////        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
//
//        matrixStackIn.mulPose(Axis.XP.rotationDegrees(entityYaw  - 90.0F));
//        matrixStackIn.mulPose(Axis.ZP.rotationDegrees(entityIn.xRotO));
//
//        if(size > 0)  {
//            renderBolt(matrixStackIn, bufferIn);
//        }
//
//        matrixStackIn.popPose();
//    }
//
//    public static void renderBolt(PoseStack matrixStackIn, MultiBufferSource bufferIn) {
//        renderBolt(bufferIn, getBoltRenderType(), // fixme get a better render type
//                matrixStackIn, /*combinedLight*/0x00F000F0, color);
//    }
//
//    public static void renderBolt(MultiBufferSource bufferIn, RenderType rt, PoseStack matrixStackIn, int packedLightIn, Color color) {
//        renderBolt(bufferIn, rt, matrixStackIn, packedLightIn, OverlayTexture.NO_OVERLAY, color);
//    }
//
//    public static void renderBolt(MultiBufferSource bufferIn, RenderType rt, PoseStack matrixStackIn, int packedLightIn, int overlay, Color color) {
//        VertexConsumer bb = bufferIn.getBuffer(rt);
//        if (modelBolt.get() == null) {
//            return;
//        }
//
//        for (BakedQuad quad : modelBolt.get().getQuads(null, null, rand)) {/*, ModelData.EMPTY)) {*/
//            bb.putBulkData(matrixStackIn.last(), quad, color.r, color.g, color.b, color.a, packedLightIn, overlay, true);
//        }
//    }
//
//    private static RenderType getBoltRenderType() {
//        return RenderType.entityTranslucentCull(NuminaConstants.TEXTURE_WHITE);
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(RailgunBoltEntity entity) {
//        return NuminaConstants.TEXTURE_WHITE;
//    }
//}
