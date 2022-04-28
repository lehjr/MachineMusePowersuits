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

package lehjr.powersuits.client.render.entity;

import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.client.render.entity.NuminaEntityRenderer;
import lehjr.numina.constants.NuminaConstants;
import lehjr.numina.util.client.model.helper.ModelHelper;
import lehjr.numina.util.client.model.obj.OBJBakedCompositeModel;
import lehjr.numina.util.math.Color;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.entity.RailgunBoltEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.Random;

public class RailGunBoltRenderer extends NuminaEntityRenderer<RailgunBoltEntity> {


    static final Color colour = new Color(0.631F, 0.615F, 0.58F, 1F);
    static final ResourceLocation modelLocation = new ResourceLocation(MPSConstants.MOD_ID, "models/entity/bolt.obj");
    // NonNullLazy doesn't init until called
    public static final NonNullLazy<OBJBakedCompositeModel> modelBolt = NonNullLazy.of(() -> ModelHelper.loadBakedModel(ModelRotation.X0_Y0, null, modelLocation));
    protected static final Random rand = new Random();

    public RailGunBoltRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }


    @Override
    public void render(RailgunBoltEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
//        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        float size = 10;
        float scale = (float) (size / 16.0F);
        matrixStackIn.pushPose();
//        matrixStackIn.translate(0.5, 0.5, 0.5);
        matrixStackIn.scale(scale, scale, scale);

//        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
//        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(entityYaw  - 90.0F));

        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(entityIn.xRot));

        if(size > 0)  {
            renderBolt(matrixStackIn, bufferIn);
        }

        matrixStackIn.popPose();
    }

    public static void renderBolt(PoseStack matrixStackIn, MultiBufferSource bufferIn) {
        renderBolt(bufferIn, getBoltRenderType(), // fixme get a better render type
                matrixStackIn, /*combinedLight*/0x00F000F0, colour);
    }

    public static void renderBolt(MultiBufferSource bufferIn, RenderType rt, PoseStack matrixStackIn, int packedLightIn, Color colour) {
        renderBolt(bufferIn, rt, matrixStackIn, packedLightIn, OverlayTexture.NO_OVERLAY, colour);
    }

    public static void renderBolt(MultiBufferSource bufferIn, RenderType rt, PoseStack matrixStackIn, int packedLightIn, int overlay, Color colour) {
        VertexConsumer bb = bufferIn.getBuffer(rt);
        for (BakedQuad quad : modelBolt.get().getQuads(null, null, rand, EmptyModelData.INSTANCE)) {
            bb.putBulkData(matrixStackIn.last(), quad, colour.r, colour.g, colour.b, colour.a, packedLightIn, overlay, true);
        }
    }

    private static RenderType getBoltRenderType() {
        return RenderType.entityTranslucentCull(NuminaConstants.TEXTURE_WHITE);
    }

    @Override
    public ResourceLocation getTextureLocation(RailgunBoltEntity entity) {
        return NuminaConstants.TEXTURE_WHITE;
    }
}
