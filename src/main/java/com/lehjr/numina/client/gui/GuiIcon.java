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

package com.lehjr.numina.client.gui;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.client.render.NuminaSpriteUploader;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 4:01 AM, 30/04/13
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class GuiIcon {
    private static final String iconPrefix = "gui";
    private final NuminaSpriteUploader spriteUploader;

    public final DrawableGuiIcon checkmark;
    public final DrawableGuiIcon transparentArmor;
    public final DrawableGuiIcon normalArmor;
    public final DrawableGuiIcon glowArmor;
    public final DrawableGuiIcon selectedArmorOverlay;
    public final DrawableGuiIcon armorColorPatch;
    public final DrawableGuiIcon minusSign;
    public final DrawableGuiIcon plusSign;
    public final DrawableGuiIcon glassTexture;
    public final DrawableGuiIcon lightning;
    public final DrawableGuiIcon weaponSlotBackground;
    public final DrawableGuiIcon energyStorageBackground;
    public final DrawableGuiIcon energyGenerationBackground;
    public GuiIcon(NuminaSpriteUploader spriteUploader) {
        this.spriteUploader = spriteUploader;
        checkmark = registerIcon("checkmark", 16, 16);
        transparentArmor = registerIcon("transparentarmor", 8, 8);
        normalArmor = registerIcon("normalarmor", 8, 8);
        glowArmor= registerIcon("glowarmor", 8, 8);
        selectedArmorOverlay = registerIcon("armordisplayselect", 8, 8);
        armorColorPatch = registerIcon("colourclicker", 8, 8);
        minusSign = registerIcon("minussign", 8, 8);
        plusSign= registerIcon("plussign", 8, 8);
        glassTexture = registerIcon("glass", 1, 8);
        lightning = registerIcon("lightning", 800, 62);
        weaponSlotBackground = registerIcon("weapon", 16, 16);
        energyStorageBackground = registerIcon("energystorage",32, 32);
        energyGenerationBackground = registerIcon("energygeneration",32, 32);
    }

    private DrawableGuiIcon registerIcon(String name, int width, int height) {
        ResourceLocation location = new ResourceLocation(NuminaConstants.MOD_ID, name);
        spriteUploader.registerIcon(location);
        return new DrawableGuiIcon(location, width, height);
    }

    // Todo?
//    private DrawableGuiIcon registerSprite(String name, int width, int height, String prefix) {
//        ResourceLocation location = new ResourceLocation(NuminaConstants.MOD_ID, name);
//        spriteUploader.registerIcon(location);
//        return new DrawableGuiIcon(location, width, height, prefix);
//    }

    public class DrawableGuiIcon {
        final ResourceLocation location;
        private final int width;
        private final int height;

        protected DrawableGuiIcon(ResourceLocation locationIn, int width, int height) {
            this.location = locationIn;
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void draw(PoseStack matrixStack, double x, double y, Color colour) {
            draw(matrixStack, x, y, 0, 0, 0, 0, colour);
        }

        public void renderIconScaledWithColor(PoseStack matrixStack,
                                              double posLeft,
                                              double posTop,
                                              double width,
                                              double height, Color colour) {
            renderIconScaledWithColor(matrixStack, posLeft, posTop, width, height, getMinecraft().screen.getBlitOffset(), colour);
        }

        public void renderIconScaledWithColor(PoseStack matrixStack,
                                              double posLeft,
                                              double posTop,
                                              double width,
                                              double height,
                                              float blitOffset,
                                              Color colour) {

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            colour.setShaderColor();
            RenderSystem.setShaderTexture(0, NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
            TextureAtlasSprite icon = spriteUploader.getSprite(location);
            innerBlit(matrixStack.last().pose(), posLeft, posLeft + width, posTop, posTop + height, blitOffset, icon.getU0(), icon.getU1(), icon.getV0(), icon.getV1(), colour);
        }


        public void draw(PoseStack matrixStack, double xOffset, double yOffset, double maskTop, double maskBottom, double maskLeft, double maskRight, Color colour) {
            double textureWidth = this.width;
            double textureHeight = this.height;

            bindTexture();
            TextureAtlasSprite icon = spriteUploader.getSprite(location);
            float blitOffset = getMinecraft().screen.getBlitOffset();

            double posLeft = xOffset + maskLeft;
            double posTop = yOffset + maskTop;
            double width = textureWidth - maskRight - maskLeft;
            double height = textureHeight - maskBottom - maskTop;

            double posRight = posLeft + width;
            double posBottom = posTop + height;

            double uSize = icon.getU1() - icon.getU0();
            double vSize = icon.getV1() - icon.getV0();
            float minU = (float) (icon.getU0() + uSize * (maskLeft / textureWidth));
            float minV = (float) (icon.getV0() + vSize * (maskTop / textureHeight));
            float maxU = (float) (icon.getU1() - uSize * (maskRight / textureWidth));
            float maxV = (float) (icon.getV1() - vSize * (maskBottom / textureHeight));

            RenderSystem.enableBlend();
//            RenderSystem.disableAlphaTest();
            RenderSystem.defaultBlendFunc();
            innerBlit(matrixStack.last().pose(), posLeft, posRight, posTop, posBottom, blitOffset, minU, maxU, minV, maxV, colour);
            RenderSystem.disableBlend();
//            RenderSystem.enableAlphaTest();
            RenderSystem.enableDepthTest();
        }

        public TextureAtlasSprite getSprite() {
            return spriteUploader.getSprite(location);
        }

        @Override
        public String toString() {
            Minecraft minecraft = Minecraft.getInstance();
            TextureManager textureManager = minecraft.getTextureManager();
            textureManager.bindForSetup(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
            TextureAtlasSprite icon = getSprite();

            if (icon != null) {
                return "icon: " + icon;
            } else {
                return "icon is null for location: " + location.toString();
            }
        }

        public void drawLightning(MultiBufferSource bufferIn, PoseStack matrixStack, float x1, float y1, float z1, float x2, float y2, float z2, Color colour) {
            System.out.println("fixme");

            //            TextureAtlasSprite icon = getSprite();
////            bindTexture();
////            NuminaLogger.logDebug("toString: " + toString());
//
//            drawLightningTextured(bufferIn.getBuffer(NuminaRenderState.LIGHTNING_TEST()),
//                    matrixStack.last().pose(),
//                    x1,
//                    y1,
//                    z1,
//                    x2,
//                    y2,
//                    z2,
//                    colour,
//                    icon,
//                    this.width,
//                    this.height);
        }
    }




















    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param blitOffset depth to draw at
     */
    public static void renderIcon8(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float blitOffset) {
        renderIcon8(location, matrixStack, left, top, right, bottom, blitOffset, Color.WHITE);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param blitOffset depth to draw at
     * @param colour color to apply to the texture
     */
    public static void renderIcon8(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float blitOffset, Color colour) {
        renderTextureWithColor(location, matrixStack, left, right, top, bottom, blitOffset, 8, 8, 0, 0, 8, 8, colour);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param blitOffset depth to draw at
     */
    public static void renderIcon16(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float blitOffset) {
        renderIcon16(location, matrixStack, left, top, right, bottom, blitOffset, Color.WHITE);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param blitOffset depth to draw at
     * @param colour color to apply to the texture
     */
    public static void renderIcon16(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float blitOffset, Color colour) {
        renderTextureWithColor(location, matrixStack, left, right, top, bottom, blitOffset, 16, 16, 0, 0, 16, 16, colour);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param blitOffset depth at which to draw (usually Minecraft.getInstance().currentScreen.getBlitLevel())
     * @param iconWidth actual width of the icon to draw
     * @param iconHeight actual height of the icon to draw
     * @param texStartX the leftmost point of the texture on the sheet (usually 0 for an icon)
     * @param texStartY the topmost point of the texture on the sheet (usually 0 for an icon)
     * @param textureWidth the width of the texture (often 8 or 16 for icons)
     * @param textureHeight the height of the texture (often 8 or 16 for icons)
     * @param colour the Color to apply to the texture
     */
    public static void renderTextureWithColor(ResourceLocation location, PoseStack matrixStack,
                                              double left, double right, double top, double bottom, float blitOffset, double iconWidth, double iconHeight, double texStartX, double texStartY, double textureWidth, double textureHeight, Color colour) {
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        innerBlit(matrixStack, left, right, top, bottom, blitOffset, iconWidth, iconHeight, texStartX, texStartY, textureWidth, textureHeight, colour);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    private static void innerBlit(Matrix4f matrix4f,
                                  float left,
                                  float right,
                                  float top,
                                  float bottom,
                                  float blitOffset,
                                  float minU,
                                  float maxU,
                                  float minV,
                                  float maxV) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        bufferbuilder.vertex(matrix4f, left, bottom, blitOffset).uv(minU, maxV).endVertex();
        bufferbuilder.vertex(matrix4f, right, bottom, blitOffset).uv(maxU, maxV).endVertex();
        bufferbuilder.vertex(matrix4f, right, top, blitOffset).uv(maxU, minV).endVertex();
        bufferbuilder.vertex(matrix4f, left, top, blitOffset).uv(minU, minV).endVertex();


        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }


    /**
     *
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param blitOffset depth to render at
     * @param iconWidth width of the portion of the texture to display
     * @param iconHeight iconHeight of the portion of texture to display
     * @param texStartX location of the left of the texture on the sheet
     * @param texStartY location of the top of the texture on the sheet
     * @param textureWidth total texture sheet width
     * @param textureHeight total texture sheet iconHeight
     * @param colour colour to apply to the texture
     */
    private static void innerBlit(PoseStack matrixStack,
                                  double left,
                                  double right,
                                  double top,
                                  double bottom,
                                  float blitOffset,
                                  double iconWidth,
                                  double iconHeight,
                                  double texStartX,
                                  double texStartY,
                                  double textureWidth,
                                  double textureHeight,
                                  Color colour) {
        innerBlit(matrixStack.last().pose(), left, right, top, bottom, blitOffset,
                // incoming math failure ... add 0? :P
                (float)((texStartX + 0.0F) / textureWidth),
                (float)((texStartX + iconWidth) / textureWidth),
                (float)((texStartY + 0.0F) / textureHeight),
                (float)((texStartY + iconHeight) / textureHeight),
                colour);
    }

    /**
     * Basically like vanilla's version but with floats and a colour parameter
     * Only does the inner texture rendering
     *
     * @param matrix4f
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param blitOffset the depth position of the drawing rectangle
     * Note: UV positions are scaled (0.0 - 1.0)
     * @param minU the left most UV mapped position
     * @param maxU the right most UV mapped position
     * @param minV the top most UV mapped position
     * @param maxV the bottom most UV mapped position
     * @param colour the Color to apply to the texture
     */
    private static void innerBlit(Matrix4f matrix4f,
                                  double left,
                                  double right,
                                  double top,
                                  double bottom,
                                  float blitOffset,
                                  float minU,
                                  float maxU,
                                  float minV,
                                  float maxV,
                                  Color colour) {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();

        colour.setShaderColor();

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        // bottom left
        bufferbuilder.vertex(matrix4f, (float)left, (float)bottom, blitOffset)
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv(minU, maxV).endVertex();
        // bottom right
        bufferbuilder.vertex(matrix4f, (float)right, (float)bottom, blitOffset)
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv(maxU, maxV).endVertex();
        // top right
        bufferbuilder.vertex(matrix4f, (float)right, (float)top, blitOffset)
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv(maxU, minV).endVertex();
        // top left
        bufferbuilder.vertex(matrix4f, (float)left, (float)top, blitOffset)
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv(minU, minV).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }

    public void drawLightningTextured(VertexConsumer bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, Color colour, TextureAtlasSprite icon, float textureWidth, float textureHeight) {
        float minV = icon.getV0();
        float maxV = icon.getV1();
        float uSize = icon.getU1() - icon.getU0();

        float tx = x2 - x1, ty = y2 - y1, tz = z2 - z1;
        float ax, ay, az;
        float bx, by, bz;
        float cx = 0, cy = 0, cz = 0;
        float jagfactor = 0.3F;
        while (Math.abs(cx) < Math.abs(tx) && Math.abs(cy) < Math.abs(ty) && Math.abs(cz) < Math.abs(tz)) {
            ax = x1 + cx;
            ay = y1 + cy;
            az = z1 + cz;
            cx += Math.random() * tx * jagfactor - 0.1 * tx;
            cy += Math.random() * ty * jagfactor - 0.1 * ty;
            cz += Math.random() * tz * jagfactor - 0.1 * tz;
            bx = x1 + cx;
            by = y1 + cy;
            bz = z1 + cz;

            int index = getRandomNumber(0, 50);
            float minU = icon.getU0() + uSize * (index * 0.2F); // 1/50, there are 50 different lightning elements in the texture
            float maxU = minU + uSize * 0.2F;

            drawLightningBetweenPointsFast(bufferIn, matrix4f, ax, ay, az, bx, by, bz, colour, minU, maxU, minV, maxV);
        }
    }

    void drawLightningBetweenPointsFast(VertexConsumer bufferIn,
                                        Matrix4f matrix4f,
                                        float x1,
                                        float y1,
                                        float z1,
                                        float x2,
                                        float y2,
                                        float z2,
                                        Color colour,
                                        float minU, float maxU, float minV, float maxV) {
        float px = (y1 - y2) * 0.125F;
        float py = (x2 - x1) * 0.125F;

        bufferIn.vertex(matrix4f, x1 - px, y1 - py, z1) // top left front
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv(minU, minV)
                .uv2(0x00F000F0).endVertex();

        bufferIn.vertex(matrix4f, x1 + px, y1 + py, z1) // bottom right front
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv(maxU, minV) // right top
                .uv2(0x00F000F0).endVertex();

        bufferIn.vertex(matrix4f, x2 - px, y2 - py, z2) //  top left back
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv(minU, maxV) // left bottom
                .uv2(0x00F000F0).endVertex();

        bufferIn.vertex(matrix4f, x2 + px, y2 + py, z2) // bottom right back
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv(maxU, maxV) // right bottom
                .uv2(0x00F000F0).endVertex();
    }

    Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    void bindTexture() {
        RenderSystem.setShaderTexture(0, NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
    }

    int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void unRotate() {
        BillboardHelper.unRotate();
    }
}