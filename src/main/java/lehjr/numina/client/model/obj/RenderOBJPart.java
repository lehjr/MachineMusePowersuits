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

//package lehjr.numina.client.model.obj;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import lehjr.numina.common.capabilities.render.modelspec.ObjPartSpec;
//import lehjr.numina.common.math.Color;
//import lehjr.numina.common.utils.MathUtils;
//import net.minecraft.client.model.Model;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.renderer.block.model.BakedQuad;
//import net.minecraft.core.Vec3i;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.util.RandomSource;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import net.neoforged.neoforge.common.util.TransformationHelper;
//import org.joml.Matrix4f;
//import org.joml.Vector3f;
//import org.joml.Vector4f;
//import org.lwjgl.system.MemoryStack;
//
//import java.nio.Buffer;
//import java.nio.ByteBuffer;
//import java.nio.IntBuffer;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Author: MachineMuse (Claire Semple)
// * Created: 4:16 AM, 29/04/13
// * <p>
// * Ported to Java by lehjr on 11/6/16.
// */
//@OnlyIn(Dist.CLIENT)
//public class RenderOBJPart extends ModelPart {
//    public float xOffset = 0;
//    public float yOffset = 0;
//    public float zOffset = 0;
//    public float xRotOffset = 0;
//    public float yRotOffset = 0;
//    public float zRotOffset = 0;
//    final int FULL_BRIGHTNESS = 0XF000F0; // 15728880 also used in vanilla rendering item in gui
//
//    // replace division operation with multiplication
//    final float div255 = 0.003921569F;
//    ModelPart parent;
//
//    /***
//     * 1.16.5 used the Consumer interface to set the parent which in turn was used to set the cubes
//     * @param base
//     * @param parent
//     */
//    public RenderOBJPart(Model base, ModelPart parent) {
//        super(new ArrayList<>(), new HashMap<>());
//        this.parent = parent;
//        // FIXME: for code refinement see net.minecraft.client.model.geom.ModelPart
//    }
//
//    @Override
//    public void copyFrom(ModelPart pModelPart) {
//        this.xScale = pModelPart.xScale;
//        this.yScale = pModelPart.yScale;
//        this.zScale = pModelPart.zScale;
//        this.xRot = pModelPart.xRot + this.xRotOffset;
//        this.yRot = pModelPart.yRot + this.yRotOffset;
//        this.zRot = pModelPart.zRot + this.zRotOffset;
//        this.x = pModelPart.x + this.xOffset;
//        this.y = pModelPart.y + this.yOffset;
//        this.z = pModelPart.z + this.zOffset;
//    }
//
//    public void render(ObjPartSpec partSpec, CompoundTag tag, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, Color color) {
//        partSpec.getPart().ifPresent(objBakedPart -> {
//            if (this.visible) {
//                ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
//                RandomSource random = RandomSource.create(42L);
//                builder.addAll(objBakedPart.getQuads(null, null, random));
//
//                poseStack.pushPose();
//                translateAndRotate(poseStack);
//
//                renderQuads(poseStack.last(),
//                        buffer,
//                        builder.build(),
//                        packedLight,
//                        packedOverlay,
//                        color.getARGBInt());
//                poseStack.popPose();
//            }
//        });
//    }
//
//    @Override
//    public void translateAndRotate(PoseStack matrixStackIn) {
//        matrixStackIn.translate(0,  - yOffset * MathUtils.DIV_16F, 0);
//        super.translateAndRotate(matrixStackIn);
//        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(180, 0, 0), true));
//        matrixStackIn.translate(0,  - yOffset * MathUtils.DIV_16F, 0);
//    }
//
//    public void renderQuads(PoseStack.Pose entry,
//                            VertexConsumer bufferIn,
//                            List<BakedQuad> quadsIn,
//                            int combinedLightIn,
//                            int combinedOverlayIn,
//                            int color) {
//
//        for (BakedQuad bakedquad : quadsIn) {
//            addVertexData(bufferIn, entry, bakedquad, combinedLightIn, combinedOverlayIn, color);
//        }
//    }
//
//    // Copy of addQuad with alpha support
//    void addVertexData(VertexConsumer bufferIn,
//                       PoseStack.Pose pose,
//                       BakedQuad bakedQuad,
//                       int lightmapCoordIn,
//                       int packedOverlay, int color) {
//        int[] vertices = bakedQuad.getVertices();
//        Vec3i faceNormal = bakedQuad.getDirection().getNormal();
//        Matrix4f matrix4f = pose.pose();
////        Vector3f normal = pose.normal().transform(new Vector3f((float)faceNormal.getX(), (float)faceNormal.getY(), (float)faceNormal.getZ()));
//        Vector3f normal = pose.transformNormal((float)faceNormal.getX(), (float)faceNormal.getY(), (float)faceNormal.getZ(), new Vector3f());
//        float scale = 0.0625F;
//
//        int intSize = DefaultVertexFormat.BLOCK.getVertexSize();
////        int intSize = DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP.getIntegerSize();
////        int vertexCount = vertices.length / intSize;
//        int vertexCount = (int) (vertices.length * 0.125);
//        try (MemoryStack memorystack = MemoryStack.stackPush()) {
//            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
//            IntBuffer intbuffer = bytebuffer.asIntBuffer();
//
//            for (int vert = 0; vert < vertexCount; ++vert) {
//                ((Buffer) intbuffer).clear();
//                intbuffer.put(vertices, vert * 8, 8);
//                float x = bytebuffer.getFloat(0);
//                float y = bytebuffer.getFloat(4);
//                float z = bytebuffer.getFloat(8);
//                int packedLight = bufferIn.applyBakedLighting(lightmapCoordIn, bytebuffer);
////                int packedLight = applyBakedLighting(pCombinedLights[k], bytebuffer);
//                float u = bytebuffer.getFloat(16);
//                float v = bytebuffer.getFloat(20);
//
//                /** scaled like TexturedQuads, but using multiplication instead of division due to speed advantage.  */
//                Vector4f pos = matrix4f.transform(new Vector4f(x * scale, y * scale, z * scale, 1.0F));
//                bufferIn.applyBakedNormals(normal, bytebuffer, pose.normal());
//                bufferIn.addVertex(pos.x(), pos.y(), pos.z(), color, u, v, packedOverlay, packedLight, normal.x(), normal.y(), normal.z());
//            }
//        }
//    }
//
//    @Override
//    public void render(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay) {
//    }
//}
package lehjr.numina.client.model.obj;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.common.capabilities.render.modelspec.ObjPartSpec;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.MathUtils;
import net.forge.client.QuadTransformers;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.IQuadTransformer;
import net.neoforged.neoforge.common.util.TransformationHelper;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RenderOBJPart extends ModelPart {
    public float xOffset = 0;
    public float yOffset = 0;
    public float zOffset = 0;
    public float xRotOffset = 0;
    public float yRotOffset = 0;
    public float zRotOffset = 0;

    ModelPart parent;

    public RenderOBJPart(Model base, ModelPart parent) {
        super(new ArrayList<>(), new HashMap<>());
        this.parent = parent;
    }

    @Override
    public void copyFrom(ModelPart pModelPart) {
        this.xScale = pModelPart.xScale;
        this.yScale = pModelPart.yScale;
        this.zScale = pModelPart.zScale;
        this.xRot = pModelPart.xRot + this.xRotOffset;
        this.yRot = pModelPart.yRot + this.yRotOffset;
        this.zRot = pModelPart.zRot + this.zRotOffset;
        this.x = pModelPart.x + this.xOffset;
        this.y = pModelPart.y + this.yOffset;
        this.z = pModelPart.z + this.zOffset;
    }

    public void render(ObjPartSpec partSpec, CompoundTag tag, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, Color color, boolean glow) {
        partSpec.getPart().ifPresent(objBakedPart -> {
            if (this.visible) {
                ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
                RandomSource random = RandomSource.create(42L);
                builder.addAll(objBakedPart.getQuads(null, null, random));

                poseStack.pushPose();
                translateAndRotate(poseStack);

                renderQuads(poseStack.last(),
                    buffer,
                    builder.build(),
                    glow ? LightTexture.FULL_BRIGHT : packedLight,
                    packedOverlay,
                    color.getARGBInt(),
                    glow);
                poseStack.popPose();
            }
        });
    }

    @Override
    public void translateAndRotate(PoseStack matrixStackIn) {
        matrixStackIn.translate(0, -yOffset * MathUtils.DIV_16F, 0);
        super.translateAndRotate(matrixStackIn);
        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(180, 0, 0), true));
        matrixStackIn.translate(0, -yOffset * MathUtils.DIV_16F, 0);
    }

    public void renderQuads(PoseStack.Pose entry,
        VertexConsumer bufferIn,
        List<BakedQuad> quadsIn,
        int combinedLightIn,
        int combinedOverlayIn,
        int color,
        boolean glow) {
        for (BakedQuad bakedquad : quadsIn) {
            BakedQuad quadToRender = bakedquad;

            // CRUCIAL: Intercept the hardcoded material attributes by copying the primitive data array
            if (glow) {
                int[] clonedVertices = java.util.Arrays.copyOf(bakedquad.getVertices(), bakedquad.getVertices().length);
                quadToRender = new BakedQuad(
                    clonedVertices,
                    bakedquad.getTintIndex(),
                    bakedquad.getDirection(),
                    bakedquad.getSprite(),
                    false // Force block shadow occlusion to false
                );

                // Inject the packed lighting parameter natively into the quad index stride
                QuadTransformers.applyingLightmap(combinedLightIn).processInPlace(quadToRender); //
            }

            addVertexData(bufferIn, entry, quadToRender, combinedLightIn, combinedOverlayIn, color, glow);
        }
    }

    void addVertexData(VertexConsumer bufferIn,
        PoseStack.Pose pose,
        BakedQuad bakedQuad,
        int lightmapCoordIn,
        int packedOverlay,
        int color,
        boolean glow) {
        int[] vertices = bakedQuad.getVertices();
        Matrix4f matrix4f = pose.pose();
        float scale = 0.0625F;

        // Process vertices safely using explicit structural index extractions
        for (int i = 0; i < 4; i++) {
            int offset = i * IQuadTransformer.STRIDE; //

            // 1. Position Coordinates
            float x = Float.intBitsToFloat(vertices[offset + IQuadTransformer.POSITION]); //
            float y = Float.intBitsToFloat(vertices[offset + IQuadTransformer.POSITION + 1]); //
            float z = Float.intBitsToFloat(vertices[offset + IQuadTransformer.POSITION + 2]); //

            // 2. Texture Maps (UV)
            float u = Float.intBitsToFloat(vertices[offset + IQuadTransformer.UV0]); //
            float v = Float.intBitsToFloat(vertices[offset + IQuadTransformer.UV0 + 1]); //

            // 3. Normal Vector Transformation
            int normalIn = vertices[offset + IQuadTransformer.NORMAL]; //
            float nx = ((byte) (normalIn & 0xFF)) / 127.0f; //
            float ny = ((byte) ((normalIn >> 8) & 0xFF)) / 127.0f; //
            float nz = ((byte) ((normalIn >> 16) & 0xFF)) / 127.0f; //
            Vector3f normal = pose.transformNormal(nx, ny, nz, new Vector3f());

            // 4. Fallback Light Configuration
            int packedLight = glow ? LightTexture.FULL_BRIGHT : lightmapCoordIn;

            // 5. Apply the standard PoseStack changes
            Vector4f pos = matrix4f.transform(new Vector4f(x * scale, y * scale, z * scale, 1.0F));

            // 6. Push safely to the entity buffer pipeline
            bufferIn.addVertex(
                pos.x(), pos.y(), pos.z(),
                color,
                u, v,
                packedOverlay,
                packedLight,
                normal.x(), normal.y(), normal.z()
            );
        }
    }

    @Override
    public void render(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay) {
    }
}
