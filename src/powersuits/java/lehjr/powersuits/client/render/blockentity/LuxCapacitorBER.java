package lehjr.powersuits.client.render.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.client.model.block.LuxCapacitorModelWrapper;
import lehjr.powersuits.client.model.helper.LuxCapHelper;
import lehjr.powersuits.common.base.MPSObjects;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.lwjgl.system.MemoryStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Random;

import static lehjr.numina.common.math.Color.div255;

// TODO: do I really want to go back to this just to avoid color issues?

public class LuxCapacitorBER implements BlockEntityRenderer<LuxCapacitorBlockEntity> {

    public LuxCapacitorBER(BlockEntityRendererProvider.Context context) {
//        context.bakeLayer(new ModelLayerLocation(MPSRegistryNames.LUX_CAPACITOR, "facing:up"));


//powersuits:luxcapacitor#facing=north,waterlogged=true
//powersuits:luxcapacitor#facing=north,waterlogged=false

//powersuits:luxcapacitor#facing=down,waterlogged=true
//powersuits:luxcapacitor#facing=down,waterlogged=false

//powersuits:luxcapacitor#facing=west,waterlogged=false
//powersuits:luxcapacitor#facing=west,waterlogged=true

//powersuits:luxcapacitor#facing=south,waterlogged=true
//powersuits:luxcapacitor#facing=south,waterlogged=false

//powersuits:luxcapacitor#facing=east,waterlogged=false
//powersuits:luxcapacitor#facing=east,waterlogged=true

//powersuits:luxcapacitor#facing=up,waterlogged=false
//powersuits:luxcapacitor#facing=up,waterlogged=true


//powersuits:luxcapacitor#inventory
//powersuits:luxcapacitor_module#inventory

    }
    Random rendom = new Random();
    ResourceLocation white = new ResourceLocation("forge:textures/white.png");

    /**
     * FIXME: try java model?
     * @param entity
     * @param pPartialTick
     * @param poseStack
     * @param bufferSource
     * @param packedLight
     * @param packedOverlay
     */


    @Override
    public void render(LuxCapacitorBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (entity != null && entity.getBlockState() != null) {
            if (entity.getBlockState().hasProperty(LuxCapacitorBlock.FACING)) {
//                poseStack.pushPose();
//                poseStack.scale(16, 16, 16);
//                Minecraft.getInstance().getBlockEntityRenderDispatcher().render();

                Color color = entity.getColor();

//                BakedModel model = Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(entity.getBlockState());
                BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(MPSObjects.LUX_CAPACITOR_ITEM.get());




                if (model != null /*&& model instanceof LuxCapacitorModelWrapper*/) {

                    for (Direction direction : Direction.values() ) {
                        List<BakedQuad> bakedQuads = model.getQuads(null, direction, rendom, LuxCapHelper.getItemModelData(color.getInt()));
                        NuminaLogger.logError("direction: " + direction.getName() +", bakedQuads Size: " + bakedQuads.size());

                        renderQuads(poseStack.last(),
                                bufferSource.getBuffer(RenderType.itemEntityTranslucentCull(white)),
//                            bufferSource.getBuffer(RenderType.translucent()),//(white)),
                                bakedQuads,
//                            LightTexture.FULL_BRIGHT,
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                color);
                    }







//                    List<BakedQuad> bakedQuads =model.getQuads(null, null, rendom, LuxCapHelper.getBlockLensModelData(color.getInt()));
                    List<BakedQuad> bakedQuads = model.getQuads(null, null, rendom, LuxCapHelper.getItemModelData(color.getInt()));
                    NuminaLogger.logError("direction: " + null +", bakedQuads Size: " + bakedQuads.size());

                    renderQuads(poseStack.last(),
                            bufferSource.getBuffer(RenderType.itemEntityTranslucentCull(white)),
//                            bufferSource.getBuffer(RenderType.translucent()),//(white)),
                            bakedQuads,
//                            LightTexture.FULL_BRIGHT,
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            color);
                }


//                poseStack.popPose();
                /**
                 *  tried these render types
                 *  note: renderTypes without texture param wont render
                 *  ------------------------
                 *  entityGlint() // no render
                 *  glint() // no render
                 *  entityTranslucent(white) // no color
                 *  entityTranslucent(white, false) // no color
                 *  entityTranslucentCull(white)  // no color
                 *  translucentMovingBlock() // no color
                 *  RenderType.translucent() // no render
                 *
                 *
                 *
                 *
                 *
                 *
                 */



            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LuxCapacitorBlockEntity pBlockEntity) {
        return BlockEntityRenderer.super.shouldRenderOffScreen(pBlockEntity);
    }

    @Override
    public int getViewDistance() {
        return BlockEntityRenderer.super.getViewDistance();
    }

    @Override
    public boolean shouldRender(LuxCapacitorBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return BlockEntityRenderer.super.shouldRender(pBlockEntity, pCameraPos);
    }


    public void renderQuads(PoseStack.Pose entry,
                            VertexConsumer bufferIn,
                            List<BakedQuad> quadsIn,
                            int combinedLightIn,
                            int combinedOverlayIn,
                            Color color) {
        for (BakedQuad bakedquad : quadsIn) {
            putBulkData(bufferIn, entry, bakedquad, color, new int[]{combinedLightIn, combinedLightIn, combinedLightIn, combinedLightIn}, combinedOverlayIn);
        }
    }

    void putBulkData(VertexConsumer bufferIn, PoseStack.Pose pose, BakedQuad bakedQuad, Color color, int[] lightmap, int packedOverlay) {
        int[] aint = bakedQuad.getVertices();
        Vec3i faceNormal = bakedQuad.getDirection().getNormal();
        Vector3f normal = new Vector3f((float)faceNormal.getX(), (float)faceNormal.getY(), (float)faceNormal.getZ());
        Matrix4f matrix4f = pose.pose();
        normal.transform(pose.normal());
        int intSize = DefaultVertexFormat.BLOCK.getIntegerSize();
        int vertexCount = aint.length / intSize;

        try (MemoryStack memorystack = MemoryStack.stackPush()) {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
            IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for(int vert = 0; vert < vertexCount; ++vert) {
                ((Buffer)intbuffer).clear();
                intbuffer.put(aint, vert * 8, 8);
                float x = bytebuffer.getFloat(0);
                float y = bytebuffer.getFloat(4);
                float z = bytebuffer.getFloat(8);

                float r;
                float g;
                float b;
                float a;
                if (false) {
                    float quadRed = (float)(bytebuffer.get(12) & 255) / 255.0F;
                    float quadGreen = (float)(bytebuffer.get(13) & 255) / 255.0F;
                    float quadBlue = (float)(bytebuffer.get(14) & 255) / 255.0F;
                    float quadAlpha = (float)(bytebuffer.get(15) & 255) / 255.0F;
                    r = quadRed * color.r;
                    g = quadGreen * color.g;
                    b = quadBlue * color.b;
                    a = quadAlpha * color.a;
                } else {
                    r = color.r;
                    g = color.g;
                    b = color.b;
                    a = color.a;
                }
                NuminaLogger.logError("r: " + r + ", g: " + g + ", b: " + b + ", a: " + a);

                int lightmapCoord = bufferIn.applyBakedLighting(lightmap[vert], bytebuffer);
                float u = bytebuffer.getFloat(16);
                float v = bytebuffer.getFloat(20);
                Vector4f pos = new Vector4f(x, y, z, 1.0F);
                pos.transform(matrix4f);
                bufferIn.applyBakedNormals(normal, bytebuffer, pose.normal());
                bufferIn.vertex(pos.x(), pos.y(), pos.z(), r, g, b, a, u, v, packedOverlay, lightmapCoord, normal.x(), normal.y(), normal.z());

//                bufferIn.vertex(matrix, pos.x(), pos.y(), pos.z()).color(r, g, b, a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmapCoord).normal(normal.x(), normal.y(), normal.z()).endVertex();

            }
        }
    }
}
