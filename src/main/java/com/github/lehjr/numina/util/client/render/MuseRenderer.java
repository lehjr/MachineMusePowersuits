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

package com.github.lehjr.numina.util.client.render;

import com.github.lehjr.numina.util.client.gui.clickable.IClickable;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.SwirlyMuseCircle;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Contains a bunch of random OpenGL-related functions, accessed statically.
 *
 *
 *
 * @author MachineMuse
 */
public abstract class MuseRenderer {
    protected static SwirlyMuseCircle selectionCircle;
    static boolean messagedAboutSlick = false;

    public static ItemRenderer getItemRenderer() {
        return Minecraft.getInstance().getItemRenderer();
    }

    public TextureManager getTextureManager() {
        return Minecraft.getInstance().getTextureManager();
    }

    static IBakedModel getModel(@Nonnull ItemStack itemStack) {
        PlayerEntity player = Minecraft.getInstance().player;
        return getItemRenderer().getModel(itemStack, player.level, player);
    }

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
    public static void drawCircleAround(MatrixStack matrixStack, double xoffset, double yoffset, double radius, float zLevel) {
        if (selectionCircle == null) {
            selectionCircle = new SwirlyMuseCircle(new Colour(0.0f, 1.0f, 0.0f, 0.0f), new Colour(0.8f, 1.0f, 0.8f, 1.0f));
        }
        selectionCircle.draw(matrixStack, radius, xoffset, yoffset, zLevel);
    }


    // FIXME: need lighting/shading for this module model


    public static void drawModuleAt(MatrixStack matrixStackIn, double x, double y, @Nonnull ItemStack itemStack, boolean active) {
        if (!itemStack.isEmpty()) {
            IBakedModel model = getModel(itemStack);
            renderItemModelIntoGUI(itemStack, matrixStackIn, (float)x, (float) y, model, active? Colour.WHITE : Colour.DARK_GREY.withAlpha(0.5F));
        }
    }

    /**
     * Makes the appropriate openGL calls and draws an itemStack and overlay using the default icon
     */
    public static void drawItemAt(double x, double y, @Nonnull ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            getItemRenderer().renderAndDecorateItem(itemStack, (int) x, (int) y);
            getItemRenderer().renderGuiItemDecorations(getFontRenderer(), itemStack, (int) x, (int) y, (String) null);
        }
    }

    public static void drawItemAt(MatrixStack matrixStack, double x, double y, @Nonnull ItemStack itemStack, Colour colour) {
        if (!itemStack.isEmpty()) {

            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStack, (int) x, (int) y);
            Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(getFontRenderer(), itemStack, (int) x, (int) y, (String) null);
        }
    }

    // Ripped from Minecraft's Item Renderer
    protected static void renderItemModelIntoGUI(ItemStack stack, MatrixStack matrixStack, float x, float y, IBakedModel bakedmodel, Colour colour) {
        RenderSystem.pushMatrix();
        Minecraft.getInstance().getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
        Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.translatef((float)x, (float)y, 100.0F + Minecraft.getInstance().getItemRenderer().blitOffset);
        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(16.0F, 16.0F, 16.0F);
        IRenderTypeBuffer.Impl bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            RenderHelper.setupForFlatItems();
        }
        renderItem(stack, ItemCameraTransforms.TransformType.GUI, false, matrixStack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel, colour);
        bufferSource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            RenderHelper.setupFor3DItems();
        }

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }

    public static void renderItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformTypeIn, boolean leftHand, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IBakedModel modelIn, Colour colour) {
        if (!itemStackIn.isEmpty()) {
            matrixStackIn.pushPose();
            boolean flag = transformTypeIn == ItemCameraTransforms.TransformType.GUI || transformTypeIn == ItemCameraTransforms.TransformType.GROUND || transformTypeIn == ItemCameraTransforms.TransformType.FIXED;
            if (itemStackIn.getItem() == Items.TRIDENT && flag) {
                modelIn = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
            }

            modelIn = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrixStackIn, modelIn, transformTypeIn, leftHand);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            if (!modelIn.isCustomRenderer() && (itemStackIn.getItem() != Items.TRIDENT || flag)) {
                boolean flag1;
                if (transformTypeIn != ItemCameraTransforms.TransformType.GUI && !transformTypeIn.firstPerson() && itemStackIn.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)itemStackIn.getItem()).getBlock();
                    flag1 = !(block instanceof BreakableBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                if (modelIn.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(Minecraft.getInstance().getItemRenderer(), modelIn, itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, flag1); }
                else {
                    RenderType rendertype = RenderTypeLookup.getRenderType(itemStackIn, flag1);
                    IVertexBuilder ivertexbuilder;
                    if (itemStackIn.getItem() == Items.COMPASS && itemStackIn.hasFoil()) {
                        matrixStackIn.pushPose();
                        MatrixStack.Entry matrixstack$entry = matrixStackIn.last();
                        if (transformTypeIn == ItemCameraTransforms.TransformType.GUI) {
                            matrixstack$entry.pose().multiply(0.5F);
                        } else if (transformTypeIn.firstPerson()) {
                            matrixstack$entry.pose().multiply(0.75F);
                        }

                        if (flag1) {
                            ivertexbuilder = Minecraft.getInstance().getItemRenderer().getCompassFoilBufferDirect(bufferIn, rendertype, matrixstack$entry);
                        } else {
                            ivertexbuilder = Minecraft.getInstance().getItemRenderer().getCompassFoilBuffer(bufferIn, rendertype, matrixstack$entry);
                        }

                        matrixStackIn.popPose();
                    } else if (flag1) {
                        ivertexbuilder = Minecraft.getInstance().getItemRenderer().getFoilBufferDirect(bufferIn, rendertype, true, itemStackIn.hasFoil());
                    } else {
                        ivertexbuilder = Minecraft.getInstance().getItemRenderer().getFoilBuffer(bufferIn, rendertype, true, itemStackIn.hasFoil());
                    }

                    renderModel(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, ivertexbuilder, colour);
                }
            } else {
                itemStackIn.getItem().getItemStackTileEntityRenderer().renderByItem(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }

            matrixStackIn.popPose();
        }
    }

    public static void renderModel(IBakedModel modelIn, ItemStack stack, int combinedLightIn, int combinedOverlayIn, MatrixStack matrixStackIn, IVertexBuilder bufferIn, Colour colour) {
        Random random = new Random();
        long i = 42L;

        for(Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderQuads(matrixStackIn, bufferIn, modelIn.getQuads((BlockState)null, direction, random), stack, combinedLightIn, combinedOverlayIn, colour);
        }

        random.setSeed(42L);
        renderQuads(matrixStackIn, bufferIn, modelIn.getQuads((BlockState)null, (Direction)null, random), stack, combinedLightIn, combinedOverlayIn, colour);
    }

    public static void renderQuads(MatrixStack matrixStackIn, IVertexBuilder bufferIn, List<BakedQuad> quadsIn, ItemStack itemStackIn, int combinedLightIn, int combinedOverlayIn, Colour colour) {
        if (colour == null) {
            Minecraft.getInstance().getItemRenderer().renderQuadList(matrixStackIn, bufferIn, quadsIn, itemStackIn, combinedLightIn, combinedOverlayIn);
        } else {
            MatrixStack.Entry matrixstack$entry = matrixStackIn.last();

            for (BakedQuad bakedquad : quadsIn) {
                bufferIn.addVertexData(matrixstack$entry, bakedquad, colour.r, colour.g, colour.b, colour.a, combinedLightIn, combinedOverlayIn, true);
            }
        }
    }

    /**
     * Renders the stack size and/or damage bar for the given ItemStack.
     */
    public void renderGuiItemDecorations(MatrixStack matrixStack, FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text) {
        if (!stack.isEmpty()) {
            if (stack.getCount() != 1 || text != null) {
                String s = text == null ? String.valueOf(stack.getCount()) : text;
                matrixStack.pushPose();
                matrixStack.translate(0.0D, 0.0D, (double)(Minecraft.getInstance().getItemRenderer().blitOffset + 200.0F));
                IRenderTypeBuffer.Impl typeBuffer = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
                fr.drawInBatch(s, (float)(xPosition + 19 - 2 - fr.width(s)), (float)(yPosition + 6 + 3), 16777215, true, matrixStack.last().pose(), typeBuffer, false, 0, 15728880);
                typeBuffer.endBatch();
                matrixStack.popPose();
            }

            if (stack.getItem().showDurabilityBar(stack)) {
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableAlphaTest();
                RenderSystem.disableBlend();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuilder();
                double health = stack.getItem().getDurabilityForDisplay(stack);
                int i = Math.round(13.0F - (float)health * 13.0F);
                int j = stack.getItem().getRGBDurabilityForDisplay(stack);
                draw(bufferbuilder,matrixStack.last().pose(), xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
                draw(bufferbuilder, matrixStack.last().pose(),xPosition + 2, yPosition + 13, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
                RenderSystem.enableBlend();
                RenderSystem.enableAlphaTest();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

            ClientPlayerEntity clientplayerentity = Minecraft.getInstance().player;
            float f3 = clientplayerentity == null ? 0.0F : clientplayerentity.getCooldowns().getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());
            if (f3 > 0.0F) {
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                Tessellator tessellator1 = Tessellator.getInstance();
                BufferBuilder bufferbuilder1 = tessellator1.getBuilder();
                draw(bufferbuilder1, matrixStack.last().pose(), xPosition, yPosition + MathHelper.floor(16.0F * (1.0F - f3)), 16, MathHelper.ceil(16.0F * f3), 255, 255, 255, 127);
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }
        }
    }

    /**
     * Draw with the WorldRenderer
     */
    private void draw(BufferBuilder renderer, Matrix4f matrix4f, float x, float y, float width, int height, int red, int green, int blue, int alpha) {
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        renderer.vertex(matrix4f, (x + 0), (y + 0), 0.0F).color(red, green, blue, alpha).endVertex();
        renderer.vertex(matrix4f, (x + 0), (y + height), 0.F).color(red, green, blue, alpha).endVertex();
        renderer.vertex(matrix4f, (x + width), (y + height), 0.0F).color(red, green, blue, alpha).endVertex();
        renderer.vertex(matrix4f, (x + width), (y + 0), 0.0F).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().end();
    }


















    public static void drawLineBetween(IClickable firstClickable, IClickable secondClickable, Colour gradientColour, float zLevel) {
        long varia = System.currentTimeMillis() % 2000 - 1000;
        // ranges from
        // -1000 to 1000
        // and around,
        // period = 2
        // seconds
        float gradientRatio = 1.0F - ((varia + 1000) % 1000) / 1000.0F;
        MusePoint2D midpoint = (firstClickable.getPosition().minus(secondClickable.getPosition()).times((float)Math.abs(varia / 1000.0))
                .plus(secondClickable.getPosition()));
        MusePoint2D firstpoint, secondpoint;
        if (varia < 0) {
            firstpoint = secondClickable.getPosition();
            secondpoint = firstClickable.getPosition();
        } else {
            firstpoint = firstClickable.getPosition();
            secondpoint = secondClickable.getPosition();
        }

        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

//        RenderSystem.lineWidth(3.0F);

        buffer.vertex(midpoint.getX(), midpoint.getY(), zLevel + 10)
                .color(gradientColour.r, gradientColour.g, gradientColour.b, gradientRatio)
                .endVertex();

        buffer.vertex(firstpoint.getX(), firstpoint.getY(), zLevel + 10)
                .color(gradientColour.r, gradientColour.g, gradientColour.b, 0.0F)
                .endVertex();

        buffer.vertex(secondpoint.getX(), secondpoint.getY(), zLevel + 10)
                .color(gradientColour.r, gradientColour.g, gradientColour.b, gradientRatio)
                .endVertex();

        buffer.vertex(midpoint.getX(), midpoint.getY(), zLevel + 10)
                .color(1.0F, 1.0F, 1.0F, 1.0F)
                .endVertex();
        tessellator.end();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }








    //    public static void drawLightning(IRenderTypeBuffer bufferIn, MatrixStack matrixStack, float x1, float y1, float z1, float x2, float y2, float z2, Colour colour) {
//        drawLightningTextured(bufferIn.getBuffer(NuminaRenderState.LIGHTNING_TEX), matrixStack.last().pose(), x1, y1, z1, x2, y2, z2, colour);
//    }
//
//    public static void drawLightningTextured(IVertexBuilder bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, Colour colour) {
//        Minecraft minecraft = Minecraft.getInstance();
//        TextureManager textureManager = minecraft.getTextureManager();
//        textureManager.bindTexture(NuminaConstants.LIGHTNING_TEXTURE);
//
////        float tx = x2 - x1, ty = y2 - y1, tz = z2 - z1;
////        float ax, ay, az;
////        float bx, by, bz;
////        float cx = 0, cy = 0, cz = 0;
////        float jagfactor = 0.3F;
////        while (Math.abs(cx) < Math.abs(tx) && Math.abs(cy) < Math.abs(ty) && Math.abs(cz) < Math.abs(tz)) {
////            ax = x1 + cx;
////            ay = y1 + cy;
////            az = z1 + cz;
////            cx += Math.random() * tx * jagfactor - 0.1 * tx;
////            cy += Math.random() * ty * jagfactor - 0.1 * ty;
////            cz += Math.random() * tz * jagfactor - 0.1 * tz;
////            bx = x1 + cx;
////            by = y1 + cy;
////            bz = z1 + cz;
////
////            int index = getRandomNumber(0, 50);
////            drawLightningBetweenPointsFast(bufferIn, matrix4f, ax, ay, az, bx, by, bz, index, colour);
////        }
//
//        int index = getRandomNumber(0, 50);
//        drawLightningBetweenPointsFast(bufferIn, matrix4f, x1, y1, z1, x2, y2, z2, index, colour);
//    }
//
//    public static void drawLightningBetweenPointsFast(IVertexBuilder bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, int index, Colour colour) {
//        float u1 = index / 50.0F;
//        float u2 = u1 + 0.02F;
//        float px = (y1 - y2) * 0.125F;
//        float py = (x2 - x1) * 0.125F;
//
//        bufferIn.pos(matrix4f, x1 - px, y1 - py, z1)
//                .color(colour.r, colour.g, colour.b, colour.a)
//                .uv(u1, 0) // left top
//                .uv2(0x00F000F0).endVertex();
//
//        bufferIn.pos(matrix4f, x1 + px, y1 + py, z1)
//                .color(colour.r, colour.g, colour.b, colour.a)
//                .uv(u2, 0) // right top
//                .uv2(0x00F000F0).endVertex();
//
//        bufferIn.pos(matrix4f, x2 - px, y2 - py, z2)
//                .color(colour.r, colour.g, colour.b, colour.a)
//                .uv(u1, 1) // left bottom
//                .uv2(0x00F000F0).endVertex();
//
//        bufferIn.pos(matrix4f, x2 + px, y2 + py, z2)
//                .color(colour.r, colour.g, colour.b, colour.a)
//                .uv(u2, 1) // right bottom
//                .uv2(0x00F000F0).endVertex();
//    }
//
//    static int getRandomNumber(int min, int max) {
//        return (int) ((Math.random() * (max - min)) + min);
//    }
//
    public static void unRotate() {
        BillboardHelper.unRotate();
    }

















    /**
     * Singleton pattern for FontRenderer
     */
    public static FontRenderer getFontRenderer() {
        return Minecraft.getInstance().font;
    }

    public static double getStringWidth(String s) {
        return getFontRenderer().width(s);
    }

    public static double getStringHeight() {
        return getFontRenderer().lineHeight;
    }

    public static double getStringWidth(ITextProperties s) {
        return getFontRenderer().width(s);
    }

    public static double getStringWidth(IReorderingProcessor s) {
        return getFontRenderer().width(s);
    }


    public static void drawShadowedStringsJustified(MatrixStack matrixStack, List<String> words, double x1, double x2, double y) {
        int totalwidth = 0;
        for (String word : words) {
            totalwidth += getStringWidth(word);
        }

        double spacing = (x2 - x1 - totalwidth) / (words.size() - 1);

        double currentwidth = 0;
        for (String word : words) {
            MuseRenderer.drawShadowedString(matrixStack, word, x1 + currentwidth, y);
            currentwidth += getStringWidth(word) + spacing;
        }
    }









    public static void drawLeftAlignedText(MatrixStack matrixStack, ITextComponent text, double x, double y, Colour colour) {
        drawText(matrixStack, text, x, y - (getStringHeight() * 0.5), colour);
    }

    public static void drawLeftAlignedText(MatrixStack matrixStack, String s, double x, double y, Colour colour) {
        drawText(matrixStack, s, x, y - (getStringHeight() * 0.5), colour);
    }

    public static void drawRightAlignedText(MatrixStack matrixStack, ITextComponent text, double x, double y, Colour colour) {
        drawText(matrixStack, text, x - getStringWidth(text), y - (getStringHeight() * 0.5), colour);
    }

    public static void drawRightAlignedText(MatrixStack matrixStack, String s, double x, double y, Colour colour) {
        drawText(matrixStack, s, x - getStringWidth(s), y - (getStringHeight() * 0.5), colour);
    }

    public static void drawCenteredText(MatrixStack matrixStack, ITextComponent component, double x, double y, Colour colour) {
        drawCenteredText(matrixStack, component, (float) x, (float) y, colour);
    }

    public static void drawCenteredText(MatrixStack matrixStack, ITextComponent component, float x, float y, Colour colour) {
        drawText(matrixStack, component, ((x - getFontRenderer().width(component) / 2)), (y - (getStringHeight() * 0.5F)), colour);
    }

    public static void drawCenteredText(MatrixStack matrixStack, String s, double x, double y, Colour colour) {
        drawText(matrixStack, s, ((x - getFontRenderer().width(s) / 2)), (y - (getStringHeight() * 0.5F)), colour);
    }

    public static void drawCenteredText(MatrixStack matrixStack, String s, float x, float y, Colour colour) {
        drawText(matrixStack, s, ((x - getFontRenderer().width(s) / 2)), (y - (getStringHeight() * 0.5F)), colour);
    }

    public static void drawText(MatrixStack matrixStack, ITextComponent component, double x, double y, Colour colour) {
        getFontRenderer().draw(matrixStack, component,  (float) x, (float) y, colour.getInt());
    }

    public static void drawText(MatrixStack matrixStack, String s, double x, double y, Colour colour) {
        getFontRenderer().draw(matrixStack, s,  (float) x, (float) y, colour.getInt());
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string such that the xcoord is halfway through the string
     */
    public static void drawShadowedStringCentered(MatrixStack matrixStack, ITextComponent text, double x, double y, Colour colour) {
        drawShadowedString(matrixStack, text, x - getStringWidth(text) / 2, y - (getStringHeight() * 0.5), colour);
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string such that the xcoord is halfway through the string
     */
    public static void drawShadowedStringCentered(MatrixStack matrixStack, ITextComponent text, double x, double y) {
        drawShadowedString(matrixStack, text, x - getStringWidth(text) / 2, y - (getStringHeight() * 0.5));
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string such that the xcoord is halfway through the string
     */
    public static void drawShadowedStringCentered(MatrixStack matrixStack, String s, double x, double y, Colour colour) {
        drawShadowedString(matrixStack, s, x - getStringWidth(s) / 2, y - (getStringHeight() * 0.5), colour);
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string such that the xcoord is halfway through the string
     */
    public static void drawShadowedStringCentered(MatrixStack matrixStack, String s, double x, double y) {
        drawShadowedString(matrixStack, s, x - getStringWidth(s) / 2, y - (getStringHeight() * 0.5));
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string such that the xcoord is halfway through the string
     */
    public static void drawRightAlignedShadowedString(MatrixStack matrixStack, ITextComponent text, double x, double y) {
        drawShadowedString(matrixStack, text, x - getStringWidth(text), y - (getStringHeight() * 0.5));
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string such that the xcoord is halfway through the string
     */
    public static void drawRightAlignedShadowedString(MatrixStack matrixStack, String s, double x, double y, Colour colour) {
        drawShadowedString(matrixStack, s, x - getStringWidth(s), y - (getStringHeight() * 0.5), colour);
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string such that the xcoord is halfway through the string
     */
    public static void drawRightAlignedShadowedString(MatrixStack matrixStack, String s, double x, double y) {
        drawShadowedString(matrixStack, s, x - getStringWidth(s), y - (getStringHeight() * 0.5));
    }

    public static void drawLeftAlignedShadowedString(MatrixStack matrixStack, ITextComponent text, double x, double y) {
        drawShadowedString(matrixStack, text, x, y - (getStringHeight() * 0.5));
    }

    public static void drawLeftAlignedShadowedString(MatrixStack matrixStack, ITextComponent text, double x, double y, Colour colour) {
        drawShadowedString(matrixStack, text, x, y - (getStringHeight() * 0.5), colour);
    }

    public static void drawLeftAlignedShadowedString(MatrixStack matrixStack, String s, double x, double y) {
        drawShadowedString(matrixStack, s, x, y - (getStringHeight() * 0.5));
    }

    public static void drawLeftAlignedShadowedString(MatrixStack matrixStack, String s, double x, double y, Colour colour) {
        drawShadowedString(matrixStack, s, x, y - (getStringHeight() * 0.5), colour);
    }

    public static void drawShadowedString(MatrixStack matrixStack, ITextComponent s, double x, double y) {
        drawShadowedString(matrixStack, s, x, y, Colour.WHITE);
    }

    public static void drawShadowedString(MatrixStack matrixStack, String s, double x, double y) {
        drawShadowedString(matrixStack, s, x, y, Colour.WHITE);
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string at the specified coords
     */
    public static void drawShadowedString(MatrixStack matrixStack, ITextComponent s, double x, double y, Colour c) {
        getFontRenderer().drawShadow(matrixStack, s, (int) x, (int) y, c.getInt());
    }

    /**
     * Does the necessary openGL calls and calls the Minecraft font renderer to draw a string at the specified coords
     */
    public static void drawShadowedString(MatrixStack matrixStack, String s, double x, double y, Colour c) {
        getFontRenderer().drawShadow(matrixStack, s, (int) x, (int) y, c.getInt());
    }
}
