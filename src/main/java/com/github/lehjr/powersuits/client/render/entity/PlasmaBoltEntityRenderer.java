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

package com.github.lehjr.powersuits.client.render.entity;

import com.github.lehjr.numina.client.render.entity.NuminaEntityRenderer;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.client.model.helper.ModelHelper;
import com.github.lehjr.numina.util.client.model.obj.OBJBakedCompositeModel;
import com.github.lehjr.numina.util.client.render.MuseIconUtils;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.entity.PlasmaBallEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.Random;

public class PlasmaBoltEntityRenderer extends NuminaEntityRenderer<PlasmaBallEntity> {
    static final Colour colour1 = new Colour(0.3F, 0.3F, 1F, 0.3F);
    static final Colour colour2 = new Colour(0.4F, 0.4F, 1F, 0.5F);
    static final Colour colour3 = new Colour(0.8F, 0.8F, 1F, 0.7F);
    static final Colour colour4 = new Colour(1F, 1F, 1F, 0.9F);

    static final ResourceLocation modelLocation = new ResourceLocation(NuminaConstants.MOD_ID, "models/item/test/sphere.obj");
    // NonNullLazy doesn't init until called
    public static final NonNullLazy<OBJBakedCompositeModel> modelSphere = NonNullLazy.of(() -> ModelHelper.loadBakedModel(ModelRotation.X0_Y0, null, modelLocation));
    protected static final Random rand = new Random();

    public PlasmaBoltEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(PlasmaBallEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        float size = entityIn.getChargePercent();//.getActualSize();
        System.out.println("size: " + size);


        if(size > 0) {
            renderPlasma(matrixStackIn, bufferIn, size);//12.5F);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(PlasmaBallEntity entity) {
        return NuminaConstants.TEXTURE_WHITE;
    }

    public static void renderPlasma(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, double size) {
        matrixStackIn.push();
        float scalFactor = 3;

        float scale = (float) (size * 0.0625);

        System.out.println("scale: " + scale);





        /*
            max size: 50
            ----------------
            max scale: 2.5
            --------------



            min size: 10
            ---------------
            min scale: 0.5
            ---------------

         */



        matrixStackIn.scale(scale, scale, scale);

        // Lightning renderer // seems to be working?
        for (int i = 0; i < 9; i++) {
            double angle1 = (Math.random() * 2 * Math.PI);
            double angle2 = (Math.random() * 2 * Math.PI);
            double angle3 = (Math.random() * 2 * Math.PI);
            MuseIconUtils.getIcon().lightning.drawLightning(bufferIn, matrixStackIn,
                    (float)(Math.cos(angle1) * 0.5), (float)(Math.sin(angle1) * 0.5), (float)(Math.cos(angle3) * 0.5),
                    (float) (Math.cos(angle2) * 5), (float)(Math.sin(angle2) * 5), (float)(Math.sin(angle3) * 5),
                    new Colour(1F, 1F, 1F, 0.9F));
        }

        // spheres
        {
            int millisPerCycle = 500;
            double timeScale = Math.cos((System.currentTimeMillis() % millisPerCycle) * 2.0 / millisPerCycle - 1.0);
            renderPlasmaBall(matrixStackIn, bufferIn, 4F*scalFactor, colour1.withAlpha(0.15F));
            renderPlasmaBall(matrixStackIn, bufferIn, (float) (3+timeScale /2F)*scalFactor,colour2.withAlpha(0.25F));
            renderPlasmaBall(matrixStackIn, bufferIn, (float) (2+timeScale)*scalFactor,colour3.withAlpha(0.4F));
            renderPlasmaBall(matrixStackIn, bufferIn, (float) (1+timeScale)*scalFactor,colour4.withAlpha(0.75F));
        }
        matrixStackIn.pop();
    }

    static void renderPlasmaBall(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float scale, Colour colour) {
        matrixStackIn.push();
        matrixStackIn.translate(0.5, 0.5, 0.5);
        matrixStackIn.scale(scale, scale, scale);
        renderSphere(bufferIn, getSphereRenderType(), // fixme get a better render type
                matrixStackIn, /*combinedLight*/0x00F000F0, colour);
        matrixStackIn.pop();
    }

    private static RenderType getSphereRenderType() {
        return RenderType.getEntityTranslucentCull(NuminaConstants.TEXTURE_WHITE);
    }

    public static void renderSphere(IRenderTypeBuffer bufferIn, RenderType rt, MatrixStack matrixStackIn, int packedLightIn, Colour colour) {
        renderSphere(bufferIn, rt, matrixStackIn, packedLightIn, OverlayTexture.NO_OVERLAY, colour);
    }

    public static void renderSphere(IRenderTypeBuffer bufferIn, RenderType rt, MatrixStack matrixStackIn, int packedLightIn, int overlay, Colour colour) {
        IVertexBuilder bb = bufferIn.getBuffer(rt);
        for (BakedQuad quad : modelSphere.get().getQuads(null, null, rand, EmptyModelData.INSTANCE)) {
            bb.addVertexData(matrixStackIn.getLast(), quad, colour.r, colour.g, colour.b, colour.a, packedLightIn, overlay, true);
        }
    }
}