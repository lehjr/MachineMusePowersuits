package com.github.lehjr.powersuits.client.render.entity;

import com.github.lehjr.numina.client.render.entity.NuminaEntityRenderer;
import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.client.model.helper.ModelHelper;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.entity.RailgunBoltEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import forge.OBJBakedCompositeModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
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


    static final Colour colour = new Colour(0.631F, 0.615F, 0.58F, 1F);
    static final ResourceLocation modelLocation = new ResourceLocation(MPSConstants.MOD_ID, "models/entity/bolt.obj");
    // NonNullLazy doesn't init until called
    public static final NonNullLazy<OBJBakedCompositeModel> modelBolt = NonNullLazy.of(() -> ModelHelper.loadBakedModel(ModelRotation.X0_Y0, null, modelLocation));
    protected static final Random rand = new Random();

    public RailGunBoltRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }


    @Override
    public void render(RailgunBoltEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        float size = 10;
        float scale = (float) (size / 16.0F);
        matrixStackIn.push();
//        matrixStackIn.translate(0.5, 0.5, 0.5);
        matrixStackIn.scale(scale, scale, scale);

//        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
//        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));

        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(entityYaw  - 90.0F));

        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(entityIn.rotationPitch));

        if(size > 0)  {
            renderBolt(matrixStackIn, bufferIn);
        }

        matrixStackIn.pop();
    }

    public static void renderBolt(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn) {
        renderBolt(bufferIn, getBoltRenderType(), // fixme get a better render type
                matrixStackIn, /*combinedLight*/0x00F000F0, colour);
    }

    public static void renderBolt(IRenderTypeBuffer bufferIn, RenderType rt, MatrixStack matrixStackIn, int packedLightIn, Colour colour) {
        renderBolt(bufferIn, rt, matrixStackIn, packedLightIn, OverlayTexture.NO_OVERLAY, colour);
    }

    public static void renderBolt(IRenderTypeBuffer bufferIn, RenderType rt, MatrixStack matrixStackIn, int packedLightIn, int overlay, Colour colour) {
        IVertexBuilder bb = bufferIn.getBuffer(rt);
        for (BakedQuad quad : modelBolt.get().getQuads(null, null, rand, EmptyModelData.INSTANCE)) {
            bb.addVertexData(matrixStackIn.getLast(), quad, colour.r, colour.g, colour.b, colour.a, packedLightIn, overlay, true);
        }
    }

    private static RenderType getBoltRenderType() {
        return RenderType.getEntityTranslucentCull(NuminaConstants.TEXTURE_WHITE);
    }

    @Override
    public ResourceLocation getEntityTexture(RailgunBoltEntity entity) {
        return NuminaConstants.TEXTURE_WHITE;
    }
}
