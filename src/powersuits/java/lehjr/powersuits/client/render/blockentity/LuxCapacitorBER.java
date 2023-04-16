package lehjr.powersuits.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.client.event.ModelBakeEventHandler;
import lehjr.powersuits.client.model.block.LuxCapacitorModel2;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.Random;

// TODO: do I really want to go back to this just to avoid color issues?

public class LuxCapacitorBER implements BlockEntityRenderer<LuxCapacitorBlockEntity> {
//    IconModel icon = new IconModel();
    LuxCapacitorModel2 model2 = new LuxCapacitorModel2(RenderType::itemEntityTranslucentCull);
    ResourceLocation dark = new ResourceLocation("powersuits:textures/block/luxdark.png");
    ResourceLocation light = new ResourceLocation("powersuits:textures/block/luxlight.png");
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
    Random random = new Random();
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

    Color darkGray = Color.DARK_GRAY;
    Color lightGray = Color.LIGHT_GRAY;



    @Override
    public void render(LuxCapacitorBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (entity != null && entity.getBlockState() != null) {

            BlockState state = entity.getBlockState();
            if (state.hasProperty(LuxCapacitorBlock.FACING)) {
                Color color = entity.getColor();
//                model2.lens.render(poseStack, bufferSource.getBuffer(RenderType.entitySolid(white)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, color.r, color.g, color.b, color.a);
//                model2.baseLower.render(poseStack, bufferSource.getBuffer(RenderType.entitySolid(dark)), packedLight, packedOverlay,  darkGray.r, darkGray.g, darkGray.b, darkGray.a);
//                model2.baseUpper.render(poseStack, bufferSource.getBuffer(RenderType.entitySolid(light)), packedLight, packedOverlay, lightGray.r, lightGray.g, lightGray.b, lightGray.a);

//                VertexConsumer builder = bufferSource.getBuffer(Sheets.translucentCullBlockSheet());

//                public void render(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {


//                BakedModel model3 = Minecraft.getInstance().getModelManager().getModel();
//
                System.out.println("starting color: " + color);
//
                Direction facing = state.getValue(LuxCapacitorBlock.FACING);
                Map<ModelBakeEventHandler.DIR, List<BakedQuad>> quadmap = ModelBakeEventHandler.INSTANCE.getQuads(facing);


//                for (ModelBakeEventHandler.DIR dir : ModelBakeEventHandler.DIR.values()) {
//                    List<BakedQuad> quads = quadmap.getOrDefault(dir, new ArrayList<>());
//                                        renderQuads(poseStack.last(),
//                            bufferSource.getBuffer(RenderType.entityTranslucentCull(TextureAtlas.LOCATION_BLOCKS)),
//                            quads,
//                            packedLight,
////                            LightTexture.FULL_BRIGHT,
//                            packedOverlay,
////                            OverlayTexture.NO_OVERLAY,
//                            color);
//                }
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
//            putBulkData(bufferIn, entry, bakedquad, color, new int[]{combinedLightIn, combinedLightIn, combinedLightIn, combinedLightIn}, combinedOverlayIn);
            bufferIn.putBulkData(entry, bakedquad, color.r, color.g, color.b, color.a, combinedLightIn, combinedOverlayIn, true);
        }
    }
//
//    // Copy of putBulkData with alpha support
//    void putBulkData(VertexConsumer bufferIn, PoseStack.Pose pose, BakedQuad bakedQuad, Color color, int[] lightmap, int packedOverlay) {
//        int[] aint = bakedQuad.getVertices();
//        Vec3i faceNormal = bakedQuad.getDirection().getNormal();
//        Vector3f normal = new Vector3f((float)faceNormal.getX(), (float)faceNormal.getY(), (float)faceNormal.getZ());
//        Matrix4f matrix4f = pose.pose();
//        normal.transform(pose.normal());
//        int intSize = DefaultVertexFormat.BLOCK.getIntegerSize();
//        int vertexCount = aint.length / intSize;
//
//        try (MemoryStack memorystack = MemoryStack.stackPush()) {
//            /** BLOCK Vertex format DOES NOT show OVERLAY */
//            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
//            IntBuffer intbuffer = bytebuffer.asIntBuffer();
//
//            for(int vert = 0; vert < vertexCount; ++vert) {
//                ((Buffer)intbuffer).clear();
//                intbuffer.put(aint, vert * 8, 8);
//                float x = bytebuffer.getFloat(0);
//                float y = bytebuffer.getFloat(4);
//                float z = bytebuffer.getFloat(8);
//
//                float r;
//                float g;
//                float b;
//                float a;
//                if (false) {
//                    float quadRed = (float)(bytebuffer.get(12) & 255) / 255.0F;
//                    float quadGreen = (float)(bytebuffer.get(13) & 255) / 255.0F;
//                    float quadBlue = (float)(bytebuffer.get(14) & 255) / 255.0F;
//                    float quadAlpha = (float)(bytebuffer.get(15) & 255) / 255.0F;
//                    r = quadRed;// * color.r;
//                    g = quadGreen;// * color.g;
//                    b = quadBlue;// * color.b;
//                    a = quadAlpha;// * color.a;
//                } else {
//                    r = color.r;
//                    g = color.g;
//                    b = color.b;
//                    a = color.a;
//                }
//                int lightmapCoord = bufferIn.applyBakedLighting(lightmap[vert], bytebuffer);
//                float u = bytebuffer.getFloat(16);
//                float v = bytebuffer.getFloat(20);
//                Vector4f pos = new Vector4f(x, y, z, 1.0F);
//                pos.transform(matrix4f);
//                bufferIn.applyBakedNormals(normal, bytebuffer, pose.normal());
//                bufferIn.vertex(
//                        /** POSITION (3)*/
//                        pos.x(), pos.y(), pos.z(),
//                        /** COLOR (4)*/
//                        r, g, b, a, u, v,
//                        /** ERMM ??? (2)*/ // <-- UV1 is OVERLAY, but not part of BlockVertexFormat?
//                        packedOverlay,
//                        /** LIGHTMAP (2)*/
//                        lightmapCoord,
//                        /** NORMAL (3)*/
//                        normal.x(), normal.y(), normal.z());
//            }
//        }
//    }
}
