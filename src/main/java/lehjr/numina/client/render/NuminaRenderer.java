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

package lehjr.numina.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import lehjr.numina.client.gui.geometry.SwirlyMuseCircle;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * Contains a bunch of random OpenGL-related functions, accessed statically.
 *
 *
 *
 * @author MachineMuse
 */
public abstract class NuminaRenderer {
    protected static SwirlyMuseCircle selectionCircle;
    /**
     * Does the rotating green circle around the selection, e.g. in GUI.
     *
     *
     * @param matrixStack
     * @param xoffset
     * @param yoffset
     * @param radius
     * @param zLevel
     */
    public static void drawCircleAround(PoseStack matrixStack, double xoffset, double yoffset, double radius, float zLevel) {
        if (selectionCircle == null) {
            selectionCircle = new SwirlyMuseCircle(new Color(0.0f, 1.0f, 0.0f, 0.0f), new Color(0.8f, 1.0f, 0.8f, 1.0f));
        }
        selectionCircle.draw(matrixStack, (float) radius, xoffset, yoffset, zLevel);
    }

    public static ItemRenderer getItemRenderer() {
        return Minecraft.getInstance().getItemRenderer();
    }

    public TextureManager getTextureManager() {
        return Minecraft.getInstance().getTextureManager();
    }

    static ItemModelShaper getItemModelShaper() {
        return getItemRenderer().getItemModelShaper();
    }

    /**
     * Makes the appropriate openGL calls and draws an itemStack and overlay using the default icon
     */
    public static void drawItemAt(double x, double y, @Nonnull ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            getItemRenderer().renderAndDecorateItem(itemStack, (int) x, (int) y);
            getItemRenderer().renderGuiItemDecorations(StringUtils.getFontRenderer(), itemStack, (int) x, (int) y, (String) null);
        }
    }

    public static void drawItemAt(PoseStack matrixStack, double x, double y, @Nonnull ItemStack itemStack, Color colour) {
        if (!itemStack.isEmpty()) {
            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStack, (int) x, (int) y);
            Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(StringUtils.getFontRenderer(), itemStack, (int) x, (int) y, (String) null);
        }
    }

    static BakedModel getModel(@Nonnull ItemStack itemStack) {
        Player player = Minecraft.getInstance().player;
        return getItemRenderer().getModel(itemStack, player.level, player, 0);
    }

    public static void drawModuleAt(PoseStack matrixStackIn, double x, double y, @Nonnull ItemStack itemStack, boolean active) {
        if (!itemStack.isEmpty()) {
            BakedModel model = getModel(itemStack);
            renderGuiItem(itemStack, matrixStackIn, (float)x, (float) y, model, active? Color.WHITE : Color.DARK_GREY.withAlpha(0.5F));
        }
    }

    // FIXME: PoseStack usage here seems dubious
    public static void renderGuiItem(ItemStack pStack, PoseStack matrixStackIn, float x, float y, BakedModel bakedModel, Color color) {
        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)x, (double)y, (double)(100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
        posestack.translate(8.0D, 8.0D, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F, 16.0F, 16.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack(); //
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedModel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        renderItem(pStack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedModel, color);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public static void renderItem(ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model, Color colour) {
        if (!itemStack.isEmpty()) {
            matrixStack.pushPose();
            boolean flag = transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.FIXED;
            if (flag) {
                if (itemStack.is(Items.TRIDENT)) {
                    model = getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
                } else if (itemStack.is(Items.SPYGLASS)) {
                    model = getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
                }
            }

            model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrixStack, model, transformType, leftHand);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);
            if (!model.isCustomRenderer() && (!itemStack.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (transformType != ItemTransforms.TransformType.GUI && !transformType.firstPerson() && itemStack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)itemStack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                if (model.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(getItemRenderer(), model, itemStack, matrixStack, buffer, combinedLight, combinedOverlay, flag1); }
                else {
                    RenderType rendertype = ItemBlockRenderTypes.getRenderType(itemStack, flag1);
                    VertexConsumer vertexconsumer;
                    if (itemStack.is(Items.COMPASS) && itemStack.hasFoil()) {
                        matrixStack.pushPose();
                        PoseStack.Pose posestack$pose = matrixStack.last();
                        if (transformType == ItemTransforms.TransformType.GUI) {
                            posestack$pose.pose().multiply(0.5F);
                        } else if (transformType.firstPerson()) {
                            posestack$pose.pose().multiply(0.75F);
                        }

                        if (flag1) {
                            vertexconsumer = getItemRenderer().getCompassFoilBufferDirect(buffer, rendertype, posestack$pose);
                        } else {
                            vertexconsumer = getItemRenderer().getCompassFoilBuffer(buffer, rendertype, posestack$pose);
                        }

                        matrixStack.popPose();
                    } else if (flag1) {
                        vertexconsumer = getItemRenderer().getFoilBufferDirect(buffer, rendertype, true, itemStack.hasFoil());
                    } else {
                        vertexconsumer = getItemRenderer().getFoilBuffer(buffer, rendertype, true, itemStack.hasFoil());
                    }

                    renderModel(model, itemStack, combinedLight, combinedOverlay, matrixStack, vertexconsumer, colour);
                }
            } else {
                net.minecraftforge.client.RenderProperties.get(itemStack).getItemStackRenderer().renderByItem(itemStack, transformType, matrixStack, buffer, combinedLight, combinedOverlay);
            }

            matrixStack.popPose();
        }
    }

    public static void renderModel(BakedModel modelIn, ItemStack stack, int combinedLightIn, int combinedOverlayIn, PoseStack matrixStackIn, VertexConsumer bufferIn, Color color) {
        Random random = new Random();
        long i = 42L;

        for(Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderQuads(matrixStackIn, bufferIn, modelIn.getQuads((BlockState)null, direction, random), stack, combinedLightIn, combinedOverlayIn, color);
        }

        random.setSeed(42L);
        renderQuads(matrixStackIn, bufferIn, modelIn.getQuads((BlockState)null, (Direction)null, random), stack, combinedLightIn, combinedOverlayIn, color);
    }

    public static void renderQuads(PoseStack matrixStackIn, VertexConsumer bufferIn, List<BakedQuad> quadsIn, ItemStack itemStackIn, int combinedLightIn, int combinedOverlayIn, Color color) {
        if (color == null) {
            Minecraft.getInstance().getItemRenderer().renderQuadList(matrixStackIn, bufferIn, quadsIn, itemStackIn, combinedLightIn, combinedOverlayIn);
        } else {
            PoseStack.Pose matrixstack$entry = matrixStackIn.last();

            for (BakedQuad bakedquad : quadsIn) {
                bufferIn.putBulkData(matrixstack$entry, bakedquad, color.r, color.g, color.b, color.a, combinedLightIn, combinedOverlayIn, true);
            }
        }
    }


    /** AbstractContainerMenu background icons */



    public static void drawLightning(double x1, double y1, double z1, double x2, double y2, double z2, Color colour) {
        drawLightningTextured(x1, y1, z1, x2, y2, z2, colour);
    }


    public static void drawMPDLightning(PoseStack poseStack, float x1, float y1, float z1, float x2, float y2, float z2, Color colour, double displacement,
                                        double detail) {
        Matrix4f matrix4f = poseStack.last().pose();
        ShaderInstance oldShader = RenderSystem.getShader();
        float lineWidth = RenderSystem.getShaderLineWidth();
        RenderSystem.lineWidth(1F);

        drawMPDLightning(matrix4f, x1, y1, z1, x2, y2, z2, colour, displacement * 0.5F, detail);

        RenderSystem.lineWidth(lineWidth);
        RenderSystem.setShader(() -> oldShader);
    }

    public static void drawMPDLightning(Matrix4f matrix4f,
                                        float x1, float y1, float z1,
                                        float x2, float y2, float z2,
                                        Color colour,
                                        double displacement,
                                        double detail) {
        if (displacement < detail) {
            RenderSystem.disableTexture();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);


            RenderSystem.setShader(GameRenderer::getPositionColorShader);



//            GL11.glEnable(GL11.GL_LINE_SMOOTH);
//            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);

            Tesselator tesselator = RenderSystem.renderThreadTesselator();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

            colour.addToVertex(bufferbuilder.vertex(matrix4f, x1, y1, z1)).endVertex();
            colour.addToVertex(bufferbuilder.vertex(matrix4f, x2, y2, z2)).endVertex();

            tesselator.end();

            RenderSystem.enableDepthTest();
            RenderSystem.enableTexture();
            RenderSystem.depthMask(true);
        } else {
            float mid_x = (x1 + x2)  * 0.5F;
            float mid_y = (y1 + y2) * 0.5F;
            float mid_z = (z1 + z2) * 0.5F;
            mid_x += (Math.random() - 0.5) * displacement;
            mid_y += (Math.random() - 0.5) * displacement;
            mid_z += (Math.random() - 0.5) * displacement;
            drawMPDLightning(matrix4f, x1, y1, z1, mid_x, mid_y, mid_z, colour, displacement * 0.5F, detail);
            drawMPDLightning(matrix4f, mid_x, mid_y, mid_z, x2, y2, z2, colour, displacement * 0.5F, detail);
        }
    }

    public static void drawLightningTextured(double x1, double y1, double z1, double x2, double y2, double z2, Color colour) {
        double tx = x2 - x1, ty = y2 - y1, tz = z2 - z1;

//        double ax = 0, ay = 0, az = 0;
//        double bx = 0, by = 0, bz = 0;
//        double cx = 0, cy = 0, cz = 0;
//
//        double jagfactor = 0.3;
//        RenderState.on2D();
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        MuseTextureUtils.pushTexture(Config.LIGHTNING_TEXTURE);
//        RenderState.blendingOn();
//        colour.doGL();
//        GL11.glBegin(GL11.GL_QUADS);
//        while (Math.abs(cx) < Math.abs(tx) && Math.abs(cy) < Math.abs(ty) && Math.abs(cz) < Math.abs(tz)) {
//            ax = x1 + cx;
//            ay = y1 + cy;
//            az = z1 + cz;
//            cx += Math.random() * tx * jagfactor - 0.1 * tx;
//            cy += Math.random() * ty * jagfactor - 0.1 * ty;
//            cz += Math.random() * tz * jagfactor - 0.1 * tz;
//            bx = x1 + cx;
//            by = y1 + cy;
//            bz = z1 + cz;
//
//            int index = (int) (Math.random() * 50);
//
//            drawLightningBetweenPointsFast(ax, ay, az, bx, by, bz, index);
//        }
//        GL11.glEnd();
//        RenderState.blendingOff();
//        RenderState.off2D();
    }

    public static void drawLightningBetweenPointsFast(double x1, double y1, double z1, double x2, double y2, double z2, int index) {

        double u1 = index / 50.0;
        double u2 = u1 + 0.02;
        double px = (y1 - y2) * 0.125;
        double py = (x2 - x1) * 0.125;
        GL11.glTexCoord2d(u1, 0);
        GL11.glVertex3d(x1 - px, y1 - py, z1);
        GL11.glTexCoord2d(u2, 0);
        GL11.glVertex3d(x1 + px, y1 + py, z1);
        GL11.glTexCoord2d(u1, 1);
        GL11.glVertex3d(x2 - px, y2 - py, z2);
        GL11.glTexCoord2d(u2, 1);
        GL11.glVertex3d(x2 + px, y2 + py, z2);
    }
}