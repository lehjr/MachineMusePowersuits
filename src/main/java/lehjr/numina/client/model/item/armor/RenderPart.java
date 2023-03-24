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

package lehjr.numina.client.model.item.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.*;
import lehjr.numina.common.capabilities.render.modelspec.ModelPartSpec;
import lehjr.numina.common.capabilities.render.modelspec.ModelRegistry;
import lehjr.numina.common.capabilities.render.modelspec.ModelSpec;
import lehjr.numina.common.capabilities.render.modelspec.PartSpecBase;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.common.tags.NBTTagAccessor;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.model.TransformationHelper;
import org.lwjgl.system.MemoryStack;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:16 AM, 29/04/13
 * <p>
 * Ported to Java by lehjr on 11/6/16.
 */
@OnlyIn(Dist.CLIENT)
public class RenderPart extends ModelPart {
    public float xOffset = 0;
    public float yOffset = 0;
    public float zOffset = 0;
    public float xRotOffset = 0;
    public float yRotOffset = 0;
    public float zRotOffset = 0;
    final int FULL_BRIGHTNESS = 0XF000F0; // 15728880 also used in vanilla rendering item in gui

    // replace division operation with multiplication
    final float div255 = 0.003921569F;
    ModelPart parent;

    /***
     * 1.16.5 used the Consumer interface to set the parent which in turn was used to set the cubes
     * @param base
     * @param parent
     */
    public RenderPart(Model base, ModelPart parent) {
        super(new ArrayList<>(), new HashMap<>());
        this.parent = parent;
    }

    @Override
    public void render(PoseStack pPoseStack, VertexConsumer pVertexConsumer, int pPackedLight, int pPackedOverlay) {
        this.render(pPoseStack, pVertexConsumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }


    @Override
    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.visible) {
            this.translateAndRotate(matrixStackIn);
            // render actual parts...
            this.doRendering(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    @Override
    public void translateAndRotate(PoseStack matrixStackIn) {
//        matrixStackIn.pushPose();
        matrixStackIn.translate(
                (this.xOffset + this.x) * MathUtils.DIV_16F, // left/right??
                (this.yOffset + this.y) * MathUtils.DIV_16F, // up/down
                (this.zOffset + this.z) * MathUtils.DIV_16F); // forward/backwards

        // forward/backwards axis
        if (this.zRot + zRotOffset != 0.0F) {
            matrixStackIn.mulPose(Vector3f.ZP.rotation(this.zRot + zRotOffset));
        }

        // up/down axis
        if (this.yRot + yRotOffset != 0.0F) {
            matrixStackIn.mulPose(Vector3f.YP.rotation(this.yRot + yRotOffset));
        }

        // left/right axis
        if (this.xRot + xRotOffset != 0.0F) {
            matrixStackIn.mulPose(Vector3f.XP.rotation(this.xRot + xRotOffset));
        }
        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(180, 0, 0), true));
        matrixStackIn.translate(0,  - yOffset * MathUtils.DIV_16F, 0);
    }

    PoseStack getCopy(PoseStack poseStack) {
        PoseStack stack = new PoseStack();
        // Apply the transformation to the real matrix stack
        Matrix4f tMat = poseStack.last().pose();
        Matrix3f nMat = poseStack.last().normal();
        stack.last().pose().multiply(tMat);
        stack.last().normal().mul(nMat);

        return stack;
    }

    void applyTransform(PoseStack poseStack, Transformation transformation) {
        if (transformation != Transformation.identity()) {
            PoseStack stack = new PoseStack();
            transformation.push(stack);
            // Apply the transformation to the real matrix stack
            Matrix4f tMat = stack.last().pose();
            Matrix3f nMat = stack.last().normal();
            poseStack.last().pose().multiply(tMat);
            poseStack.last().normal().mul(nMat);
        }
    }

    private void doRendering(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        CompoundTag renderSpec = ArmorModelInstance.getInstance().getRenderSpec();
        if (renderSpec != null) {
            int[] colours = renderSpec.getIntArray(TagConstants.COLORS);

            if (colours.length == 0) {
                colours = new int[]{Color.WHITE.getInt()};
            }

            int partColor;
            for (CompoundTag nbt : NBTTagAccessor.getValues(renderSpec)) {
                PoseStack workingStack = getCopy(matrixStackIn);

                PartSpecBase part = ModelRegistry.getInstance().getPart(nbt);
                if (part /* != null && part */ instanceof ModelPartSpec) {
                    // TODO slim model?
                    if (part.getBinding().getSlot() == ArmorModelInstance.getInstance().getVisibleSection()
                            && part.getBinding().getTarget().apply(ArmorModelInstance.getInstance()) == parent) {
                        int ix = part.getColourIndex(nbt);
                        // checks the range of the index to avoid errors OpenGL or crashing
                        if (ix < colours.length && ix >= 0) {
                            partColor = colours[ix];
                        } else {
                            partColor = -1;
                        }

                        Transformation transform = ((ModelSpec) part.spec).getTransform(ItemTransforms.TransformType.NONE);
                        applyTransform(workingStack, transform);

                        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
                        Random random = new Random();
                        long i = 42L;
                        random.setSeed(i);
                        builder.addAll(((ModelPartSpec) part).getPart().getQuads(null, null, random));

                        PoseStack.Pose entry = workingStack.last();

                        renderQuads(entry,
                                bufferIn,
                                builder.build(),
                                ((ModelPartSpec) part).getGlow() ? FULL_BRIGHTNESS : packedLightIn,
                                OverlayTexture.NO_OVERLAY,
                                partColor);
                    }
                }
            }
        }
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

            for (int vert = 0; vert < vertexCount; ++vert) {
                ((Buffer) intbuffer).clear();
                intbuffer.put(aint, vert * 8, 8);
                float x = bytebuffer.getFloat(0);
                float y = bytebuffer.getFloat(4);
                float z = bytebuffer.getFloat(8);
                int lightmapCoord = bufferIn.applyBakedLighting(lightmapCoordIn, bytebuffer);
                float u = bytebuffer.getFloat(16);
                float v = bytebuffer.getFloat(20);

                /** scaled like TexturedQuads, but using multiplication instead of division due to speed advantage.  */
                Vector4f pos = new Vector4f(x * scale, y * scale, z * scale, 1.0F); // scales to 1/16 like the TexturedQuads but with multiplication (faster than division)
                pos.transform(matrix4f);
                bufferIn.applyBakedNormals(normal, bytebuffer, matrixEntry.normal());
                bufferIn.vertex(pos.x(), pos.y(), pos.z(), red, green, blue, alpha, u, v, overlayCoords, lightmapCoord, normal.x(), normal.y(), normal.z());
            }
        }
    }
}