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
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
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
    final int FULL_BRIGHTNESS = 0XF000F0; // 15728880 also used in vanilla rendering item in gui

    // replace division operation with multiplication
    final float div255 = 0.003921569F;
    ModelPart parent;

    public RenderPart(Model model, ModelPart parent) {
        super(new ArrayList<>(), new HashMap<>());
        this.parent = parent;
    }

    @Override
    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.visible) {
            matrixStackIn.pushPose();
            this.translateAndRotate(matrixStackIn);
            this.doRendering(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.popPose();
        }
    }

    @Override
    public void translateAndRotate(PoseStack matrixStackIn) {
        matrixStackIn.translate(
                this.x * 0.0625F, // left/right??
                this.y * 0.0625F, // up/down
                this.z * 0.0625F); // forward/backwards
        if (this.zRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.ZP.rotation(this.zRot));
        }

        if (this.yRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.YP.rotation(this.yRot));
        }

        if (this.xRot != 0.0F) {
            matrixStackIn.mulPose(Vector3f.XP.rotation(this.xRot));
        }

        matrixStackIn.mulPose(TransformationHelper.quatFromXYZ(new Vector3f(180, 0, 0), true));
    }

    private void doRendering(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        CompoundTag renderSpec = ArmorModelInstance.getInstance().getRenderSpec();
        if (renderSpec != null) {
            PoseStack.Pose entry = matrixStackIn.last();

            int[] colours = renderSpec.getIntArray(NuminaConstants.TAG_COLOURS);

            if (colours.length == 0) {
                colours = new int[]{Color.WHITE.getInt()};
            }

            int partColor;
            for (CompoundTag nbt : NBTTagAccessor.getValues(renderSpec)) {
                PartSpecBase part = ModelRegistry.getInstance().getPart(nbt);
                if (part != null && part instanceof ModelPartSpec) {
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
                        if (transform != Transformation.identity()) {
                            PoseStack stack = new PoseStack();
                            transform.push(stack);
                            // Apply the transformation to the real matrix stack
                            Matrix4f tMat = stack.last().pose();
                            Matrix3f nMat = stack.last().normal();
                            matrixStackIn.last().pose().multiply(tMat);
                            matrixStackIn.last().normal().mul(nMat);
                        }

                        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
                        Random random = new Random();
                        long i = 42L;
                        random.setSeed(i);
                        builder.addAll(((ModelPartSpec) part).getPart().getQuads(null, null, random));

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
                Vector4f pos = new Vector4f(x * 0.0625F, y * 0.0625F, z * 0.0625F, 1.0F); // scales to 1/16 like the TexturedQuads but with multiplication (faster than division)
                pos.transform(matrix4f);
                bufferIn.applyBakedNormals(normal, bytebuffer, matrixEntry.normal());


                bufferIn.vertex(pos.x(), pos.y(), pos.z(), red, green, blue, alpha, f9, f10, overlayCoords, lightmapCoord, normal.x(), normal.y(), normal.z());
            }
        }
    }
}