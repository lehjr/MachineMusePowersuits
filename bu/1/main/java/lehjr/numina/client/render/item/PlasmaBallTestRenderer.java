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
//package com.lehjr.numina.client.render.item;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import org.joml.Vector3f;
//import com.lehjr.numina.client.model.helper.ModelHelper;
//import com.lehjr.numina.client.model.obj.OBJBakedCompositeModel;
//import com.lehjr.numina.client.render.IconUtils;
//import com.lehjr.numina.common.constants.NuminaConstants;
//import com.lehjr.numina.common.math.Color;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.block.model.BakedQuad;
//import net.minecraft.client.renderer.block.model.ItemTransforms;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.client.resources.model.BlockModelRotation;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.neoforged.neoforge.client.model.data.EmptyModelData;
//import net.neoforged.neoforge.common.util.NonNullLazy;
//
//import java.util.Random;
//
//public class PlasmaBallTestRenderer  extends ItemStackTileEntityRenderer {
//    static final Color color1 = new Color(0.3F, 0.3F, 1F, 0.3F);
//    static final Color color2 = new Color(0.4F, 0.4F, 1F, 0.5F);
//    static final Color color3 = new Color(0.8F, 0.8F, 1F, 0.7F);
//    static final Color color4 = new Color(1F, 1F, 1F, 0.9F);
//
//    static final ResourceLocation modelLocation = ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "models/item/test/sphere.obj");
//    // NonNullLazy doesn't init until called
//    public static final NonNullLazy<OBJBakedCompositeModel> modelSphere = NonNullLazy.of(() -> ModelHelper.loadBakedModel(BlockModelRotation.X0_Y0, null, modelLocation));
//    protected final Random rand = new Random();
//
//    public PlasmaBallTestRenderer() {
//    }
//
//    float size = 50;
//
//    @Override
//    public void renderByItem(ItemStack stack,
//                               ItemDisplayContext transformType,
//                               PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {
//        renderAsItem(matrixStackIn, bufferIn, size, transformType);
//    }
//
//    public void renderAsItem(PoseStack matrixStackIn, MultiBufferSource bufferIn, float boltSizeIn, ItemDisplayContext cameraTransformTypeIn) {
//        if (boltSizeIn != 0) {
//            matrixStackIn.pushPose();
//            if (cameraTransformTypeIn == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || cameraTransformTypeIn == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
//                matrixStackIn.scale(0.0625f, 0.0625f, 0.0625f); // negative scale mirrors the model
//                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-182));
//            } else {
//                matrixStackIn.scale(0.0625f, 0.0625f, 0.0625f);
//                matrixStackIn.translate(0, 0, 20.3f);
////                GL11.glTranslatef(0, 0, 1.3f);
//                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-196F));
//            }
//            //---
//            matrixStackIn.translate(-1, 1, 16);
//            matrixStackIn.pushPose();
//
//            renderPlasma(matrixStackIn, bufferIn, boltSizeIn);
//
//            matrixStackIn.popPose();
//            // ---
//            matrixStackIn.popPose();
//        }
//    }
//
//    public void renderPlasma(PoseStack matrixStackIn, MultiBufferSource bufferIn, float size) {
//        matrixStackIn.pushPose();
//        float scalFactor = 3;
//
////        MuseRenderer.unRotate();
//        float scale = size / 16.0F;
//        matrixStackIn.scale(scale, scale, scale);
//
//        // Lightning renderer // seems to be working?
//        for (int i = 0; i < 9; i++) {
//            double angle1 = (Math.random() * 2 * Math.PI);
//            double angle2 = (Math.random() * 2 * Math.PI);
//            double angle3 = (Math.random() * 2 * Math.PI);
//            IconUtils.getIcon().lightning.drawLightning(bufferIn, matrixStackIn,
//                    (float)(Math.cos(angle1) * 0.5), (float)(Math.sin(angle1) * 0.5), (float)(Math.cos(angle3) * 0.5),
//                    (float) (Math.cos(angle2) * 5), (float)(Math.sin(angle2) * 5), (float)(Math.sin(angle3) * 5),
//                    new Color(1F, 1F, 1F, 0.9F));
//        }
//
//        // spheres
//        {
//            int millisPerCycle = 500;
//            double timeScale = Math.cos((System.currentTimeMillis() % millisPerCycle) * 2.0 / millisPerCycle - 1.0);
//            renderPlasmaBall(matrixStackIn, bufferIn, 4F*scalFactor, color1.withAlpha(0.15F));
//            renderPlasmaBall(matrixStackIn, bufferIn, (float) (3+timeScale /2F)*scalFactor,color2.withAlpha(0.25F));
//            renderPlasmaBall(matrixStackIn, bufferIn, (float) (2+timeScale)*scalFactor,color3.withAlpha(0.4F));
//            renderPlasmaBall(matrixStackIn, bufferIn, (float) (1+timeScale)*scalFactor,color4.withAlpha(0.75F));
//        }
//        matrixStackIn.popPose();
//    }
//
//    private RenderType getSphereRenderType() {
//        return RenderType.entityTranslucentCull(NuminaConstants.TEXTURE_WHITE);
//    }
//
//    void renderPlasmaBall(PoseStack matrixStackIn, MultiBufferSource bufferIn, float scale, Color color) {
//        matrixStackIn.pushPose();
//        matrixStackIn.translate(0.5, 0.5, 0.5);
//        matrixStackIn.scale(scale, scale, scale);
//        renderSphere(bufferIn, getSphereRenderType(), // fixme get a better render type
//                matrixStackIn, /*combinedLight*/0x00F000F0, color);
//        matrixStackIn.popPose();
//    }
//
//    public void renderSphere(MultiBufferSource bufferIn, RenderType rt, PoseStack matrixStackIn, int packedLightIn, Color color) {
//        renderSphere(bufferIn, rt, matrixStackIn, packedLightIn, OverlayTexture.NO_OVERLAY, color);
//    }
//
//    public void renderSphere(MultiBufferSource bufferIn, RenderType rt, PoseStack matrixStackIn, int packedLightIn, int overlay, Color color) {
//        VertexConsumer bb = bufferIn.getBuffer(rt);
//        for (BakedQuad quad : modelSphere.get().getQuads(null, null, rand, ModelData.EMPTY)) {
//            bb.putBulkData(matrixStackIn.last(), quad, color.r, color.g, color.b, color.a, packedLightIn, overlay, true);
//        }
//    }
//}