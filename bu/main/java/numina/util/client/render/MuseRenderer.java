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

package lehjr.numina.util.client.render;

import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import lehjr.numina.util.client.gui.clickable.IClickable;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.gui.gemoetry.SwirlyMuseCircle;
import lehjr.numina.util.math.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemTransforms.;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.Player;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.Component;
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
        selectionCircle.draw(matrixStack, radius, xoffset, yoffset, zLevel);
    }


    // FIXME: need lighting/shading for this module model




    /**
     * Makes the appropriate openGL calls and draws an itemStack and overlay using the default icon
     */
    public static void drawItemAt(double x, double y, @Nonnull ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            getItemRenderer().renderAndDecorateItem(itemStack, (int) x, (int) y);
            getItemRenderer().renderGuiItemDecorations(getFontRenderer(), itemStack, (int) x, (int) y, (String) null);
        }
    }

    public static void drawItemAt(PoseStack matrixStack, double x, double y, @Nonnull ItemStack itemStack, Color colour) {
        if (!itemStack.isEmpty()) {

            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStack, (int) x, (int) y);
            Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(getFontRenderer(), itemStack, (int) x, (int) y, (String) null);
        }
    }








    /**
     * Renders the stack size and/or damage bar for the given ItemStack.
     */
    public void renderGuiItemDecorations(PoseStack matrixStack, FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text) {
        if (!stack.isEmpty()) {
            if (stack.getCount() != 1 || text != null) {
                String s = text == null ? String.valueOf(stack.getCount()) : text;
                matrixStack.pushPose();
                matrixStack.translate(0.0D, 0.0D, (double)(Minecraft.getInstance().getItemRenderer().blitOffset + 200.0F));
                MultiBufferSource.Impl typeBuffer = MultiBufferSource.immediate(Tessellator.getInstance().getBuilder());
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

            ClientPlayer clientplayerentity = Minecraft.getInstance().player;
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


















    public static void drawLineBetween(IClickable firstClickable, IClickable secondClickable, Color gradientColor, float zLevel) {
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
                .color(gradientColor.r, gradientColor.g, gradientColor.b, gradientRatio)
                .endVertex();

        buffer.vertex(firstpoint.getX(), firstpoint.getY(), zLevel + 10)
                .color(gradientColor.r, gradientColor.g, gradientColor.b, 0.0F)
                .endVertex();

        buffer.vertex(secondpoint.getX(), secondpoint.getY(), zLevel + 10)
                .color(gradientColor.r, gradientColor.g, gradientColor.b, gradientRatio)
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








    //    public static void drawLightning(MultiBufferSource bufferIn, PoseStack matrixStack, float x1, float y1, float z1, float x2, float y2, float z2, Color colour) {
//        drawLightningTextured(bufferIn.getBuffer(NuminaRenderState.LIGHTNING_TEX), matrixStack.last().pose(), x1, y1, z1, x2, y2, z2, colour);
//    }
//
//    public static void drawLightningTextured(VertexConsumer bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, Color colour) {
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
//    public static void drawLightningBetweenPointsFast(VertexConsumer bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, int index, Color colour) {
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


































}
