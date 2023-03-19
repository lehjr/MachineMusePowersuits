package lehjr.powersuits.client.render.blockentity;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.client.model.helper.LuxCapHelper;
import lehjr.powersuits.common.block.LuxCapacitorBlock;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
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


    @Override
    public void render(LuxCapacitorBlockEntity entity, float pPartialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (entity != null && entity.getBlockState() != null) {
            if (entity.getBlockState().hasProperty(LuxCapacitorBlock.FACING)) {
                Direction facing = entity.getBlockState().getValue(LuxCapacitorBlock.FACING);
                Color color = entity.getColor();


                BakedModel model = Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(entity.getBlockState());
                if (model != null) {
                    List<BakedQuad> bakedQuads = model.getQuads(entity.getBlockState(), null, rendom, LuxCapHelper.getBlockLensModelData(Color.WHITE.getInt()));
                    NuminaLogger.logError("bakedQuads size: " + bakedQuads.size());



                    renderQuads(poseStack.last(),
                            bufferSource.getBuffer(RenderType.itemEntityTranslucentCull(white)),
                            bakedQuads,
                            LightTexture.FULL_BRIGHT,
                            packedOverlay,
                            color.getInt());
                }
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
                            int colour) {
        float a = (float) (colour >> 24 & 255) * div255;
        float r = (float) (colour >> 16 & 255) * div255;
        float g = (float) (colour >> 8 & 255) * div255;
        float b = (float) (colour & 255) * div255;

        for (BakedQuad bakedquad : quadsIn) {
            addVertexData(bufferIn, entry, bakedquad, combinedLightIn, combinedOverlayIn, r, g, b, a);
        }
    }

    // Copy of addQuad with alpha support
    void addVertexData(VertexConsumer bufferIn,
                       PoseStack.Pose matrixEntry,
                       BakedQuad bakedQuad,
                       int lightmapCoordIn,
                       int overlayCoords, float red, float green, float blue, float alpha) {
        int[] aint = bakedQuad.getVertices();
        Vec3i faceNormal = bakedQuad.getDirection().getNormal();
        Vector3f normal = new Vector3f((float) faceNormal.getX(), (float) faceNormal.getY(), (float) faceNormal.getZ());
        Matrix4f matrix4f = matrixEntry.pose();// same as TexturedQuad renderer
        normal.transform(matrixEntry.normal()); // normals different here

        float scale = 0.0625F;

        int intSize = DefaultVertexFormat.BLOCK.getIntegerSize();
//        int intSize = DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP.getIntegerSize();

        int vertexCount = aint.length / intSize;

        try (MemoryStack memorystack = MemoryStack.stackPush()) {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
//            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP.getVertexSize());
            IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for (int v = 0; v < vertexCount; ++v) {
                ((Buffer) intbuffer).clear();
                intbuffer.put(aint, v * 8, 8);
                float x = bytebuffer.getFloat(0);
                float y = bytebuffer.getFloat(4);
                float z = bytebuffer.getFloat(8);
                int lightmapCoord = bufferIn.applyBakedLighting(lightmapCoordIn, bytebuffer);
                float f9 = bytebuffer.getFloat(16);
                float f10 = bytebuffer.getFloat(20);

                /** scaled like TexturedQuads, but using multiplication instead of division due to speed advantage.  */
                Vector4f pos = new Vector4f(x * scale, y * scale, z * scale, 1.0F); // scales to 1/16 like the TexturedQuads but with multiplication (faster than division)
                pos.transform(matrix4f);
                bufferIn.applyBakedNormals(normal, bytebuffer, matrixEntry.normal());


                bufferIn.vertex(pos.x(), pos.y(), pos.z(), red, green, blue, alpha, f9, f10, overlayCoords, lightmapCoord, normal.x(), normal.y(), normal.z());
            }
        }
    }
}
