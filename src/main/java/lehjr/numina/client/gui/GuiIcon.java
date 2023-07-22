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

package lehjr.numina.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import lehjr.numina.client.render.NuminaSpriteUploader;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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

    public final DrawableGuiIcon armordisplayselect;
    public final DrawableGuiIcon checkmark;
    public final DrawableGuiIcon colorclicker;
    public final DrawableGuiIcon energygeneration;
    public final DrawableGuiIcon energystorage;
    public final DrawableGuiIcon glowArmor;

    public final DrawableGuiIcon lightning;
    public final DrawableGuiIcon lightning2;
    public final DrawableGuiIcon lightningmedium;
    public final DrawableGuiIcon minusSign;
    public final DrawableGuiIcon normalArmor;
    public final DrawableGuiIcon plusSign;
    public final DrawableGuiIcon transparentArmor;
    public final DrawableGuiIcon weapon;

    public GuiIcon(NuminaSpriteUploader spriteUploader) {
        this.spriteUploader = spriteUploader;
        armordisplayselect = registerIcon("armordisplayselect", 8, 8);
        checkmark = registerIcon("checkmark", 16, 16);
        colorclicker = registerIcon("colorclicker", 8, 8);
        energygeneration = registerIcon("energygeneration",32, 32);
        energystorage = registerIcon("energystorage",32, 32);
        glowArmor= registerIcon("glowarmor", 8, 8);
        lightning = registerIcon("lightning", 800, 62);
        lightning2 = registerIcon("lightning2", 800, 62);
        lightningmedium = registerIcon("lightningmedium", 800, 62);
        minusSign = registerIcon("minussign", 8, 8);
        normalArmor = registerIcon("normalarmor", 8, 8);
        plusSign= registerIcon("plussign", 8, 8);
        transparentArmor = registerIcon("transparentarmor", 8, 8);
        weapon = registerIcon("weapon", 16, 16);
    }

    private DrawableGuiIcon registerIcon(String name, int width, int height) {
        ResourceLocation location = new ResourceLocation(NuminaConstants.MOD_ID, name);
//        spriteUploader.registerIcon(location);
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

        public void draw(PoseStack matrixStack, double x, double y, Color color) {
            ShaderInstance oldShader = RenderSystem.getShader();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.enableBlend();
            draw(matrixStack, x, y, 0, 0, 0, 0, color);
            RenderSystem.disableBlend();
            RenderSystem.setShader(() -> oldShader);
        }

        public void renderIconScaledWithColor(PoseStack matrixStack,
                                              double posLeft, double posTop, double width, double height, Color color) {
            renderIconScaledWithColor(matrixStack, posLeft, posTop, width, height, getMinecraft().screen.getBlitOffset(), color);
        }

        public void renderIconScaledWithColor(PoseStack matrixStack,
                                              double posLeft, double posTop, double width, double height, float zLevel, Color color) {            bindTexture();
            TextureAtlasSprite icon = spriteUploader.getSprite(location);
            RenderSystem.enableBlend();
//            RenderSystem.disableAlphaTest();
            RenderSystem.defaultBlendFunc();
            innerBlit(matrixStack.last().pose(), posLeft, posLeft + width, posTop, posTop + height, zLevel, icon.getU0(), icon.getU1(), icon.getV0(), icon.getV1(), color);
            RenderSystem.disableBlend();
//            RenderSystem.enableAlphaTest();
            RenderSystem.enableDepthTest();
        }


        public void draw(PoseStack matrixStack, double xOffset, double yOffset, double maskTop, double maskBottom, double maskLeft, double maskRight, Color color) {
            double textureWidth = this.width;
            double textureHeight = this.height;

            bindTexture();
            TextureAtlasSprite icon = spriteUploader.getSprite(location);
            float zLevel = getMinecraft().screen.getBlitOffset();

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
            innerBlit(matrixStack.last().pose(), posLeft, posRight, posTop, posBottom, zLevel, minU, maxU, minV, maxV, color);
            RenderSystem.disableBlend();
//            RenderSystem.enableAlphaTest();
            RenderSystem.enableDepthTest();
        }

        public TextureAtlasSprite getSprite() {
            return spriteUploader.getSprite(location);
        }

        @Override
        public String toString() {
//            Minecraft minecraft = Minecraft.getInstance();
//            TextureManager textureManager = minecraft.getTextureManager();
//            textureManager.bindForSetup(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
            TextureAtlasSprite icon = getSprite();

            if (icon != null) {
                return "icon: " + icon;
            } else {
                return "icon is null for location: " + location.toString();
            }
        }

        public void drawLightning(MultiBufferSource bufferIn, PoseStack matrixStack, float x1, float y1, float z1, float x2, float y2, float z2, Color color) {
            TextureAtlasSprite icon = getSprite();
//            bindTexture();
//            MuseLogger.logDebug("toString: " + toString());

            NuminaLogger.logDebug("FIXME!!!!!!");

//            drawLightningTextured(bufferIn.getBuffer(NuminaRenderState.LIGHTNING_TEST()),
//                    matrixStack.last().pose(),
//                    x1,
//                    y1,
//                    z1,
//                    x2,
//                    y2,
//                    z2,
//                    color,
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
     * @param zLevel depth to draw at
     */
    public static void renderIcon8(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float zLevel) {
        renderIcon8(location, matrixStack, left, top, right, bottom, zLevel, Color.WHITE);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     * @param color color to apply to the texture
     */
    public static void renderIcon8(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float zLevel, Color color) {
        renderTextureWithColor(location, matrixStack, left, right, top, bottom, zLevel, 8, 8, 0, 0, 8, 8, color);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     */
    public static void renderIcon16(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float zLevel) {
        renderIcon16(location, matrixStack, left, top, right, bottom, zLevel, Color.WHITE);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     * @param color color to apply to the texture
     */
    public static void renderIcon16(ResourceLocation location, PoseStack matrixStack, double left, double top, double right, double bottom, float zLevel, Color color) {
        renderTextureWithColor(location, matrixStack, left, right, top, bottom, zLevel, 16, 16, 0, 0, 16, 16, color);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param zLevel depth at which to draw (usually Minecraft.getInstance().currentScreen.getBlitLevel())
     * @param iconWidth actual width of the icon to draw
     * @param iconHeight actual height of the icon to draw
     * @param texStartX the leftmost point of the texture on the sheet (usually 0 for an icon)
     * @param texStartY the topmost point of the texture on the sheet (usually 0 for an icon)
     * @param textureWidth the width of the texture (often 8 or 16 for icons)
     * @param textureHeight the height of the texture (often 8 or 16 for icons)
     * @param color the Color to apply to the texture
     */
    public static void renderTextureWithColor(ResourceLocation location, PoseStack matrixStack,
                                              double left, double right, double top, double bottom, float zLevel, double iconWidth, double iconHeight, double texStartX, double texStartY, double textureWidth, double textureHeight, Color color) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.enableBlend();
//        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        innerBlit(matrixStack, left, right, top, bottom, zLevel, iconWidth, iconHeight, texStartX, texStartY, textureWidth, textureHeight, color);
        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
        RenderSystem.enableDepthTest();
    }

    /**
     *
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param zLevel depth to render at
     * @param iconWidth width of the portion of the texture to display
     * @param iconHeight iconHeight of the portion of texture to display
     * @param texStartX location of the left of the texture on the sheet
     * @param texStartY location of the top of the texture on the sheet
     * @param textureWidth total texture sheet width
     * @param textureHeight total texture sheet iconHeight
     * @param color color to apply to the texture
     */
    private static void innerBlit(PoseStack matrixStack, double left, double right, double top, double bottom, float zLevel, double iconWidth, double iconHeight, double texStartX, double texStartY, double textureWidth, double textureHeight, Color color) {
        innerBlit(matrixStack.last().pose(), left, right, top, bottom, zLevel,
                (float)((texStartX + 0.0F) / textureWidth),
                (float)((texStartX + iconWidth) / textureWidth),
                (float)((texStartY + 0.0F) / textureHeight),
                (float)((texStartY + iconHeight) / textureHeight),
                color);
    }

    /**
     * Basically like vanilla's version but with floats and a color parameter
     * Only does the inner texture rendering
     *
     * @param matrix4f
     * @param left the left most position of the drawing rectangle
     * @param right the right most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom the bottom most position of the drawing rectangle
     * @param zLevel the depth position of the drawing rectangle
     * Note: UV positions are scaled (0.0 - 1.0)
     * @param minU the left most UV mapped position
     * @param maxU the right most UV mapped position
     * @param minV the top most UV mapped position
     * @param maxV the bottom most UV mapped position
     * @param color the Color to apply to the texture
     */
    private static void innerBlit(Matrix4f matrix4f, double left, double right, double top, double bottom, float zLevel, float minU, float maxU, float minV, float maxV, Color color) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        color.setShaderColor();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        // bottom left
        bufferbuilder.vertex(matrix4f, (float)left, (float)bottom, zLevel).uv(minU, maxV)
                .color(color.r, color.g, color.b, color.a)
                .endVertex();

        // bottom right
        bufferbuilder.vertex(matrix4f, (float)right, (float)bottom, zLevel).uv(maxU, maxV)
                .color(color.r, color.g, color.b, color.a)
                .endVertex();

        // top right
        bufferbuilder.vertex(matrix4f, (float)right, (float)top, zLevel).uv(maxU, minV)
                .color(color.r, color.g, color.b, color.a)
                .endVertex();

        // top left
        bufferbuilder.vertex(matrix4f, (float)left, (float)top, zLevel).uv(minU, minV)
                .color(color.r, color.g, color.b, color.a)
                .endVertex();

        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    public void drawLightningTextured(VertexConsumer bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, Color color, TextureAtlasSprite icon, float textureWidth, float textureHeight) {
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

            drawLightningBetweenPointsFast(bufferIn, matrix4f, ax, ay, az, bx, by, bz, color, minU, maxU, minV, maxV);
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
                                        Color color,
                                        float minU, float maxU, float minV, float maxV) {
        float px = (y1 - y2) * 0.125F;
        float py = (x2 - x1) * 0.125F;

        bufferIn.vertex(matrix4f, x1 - px, y1 - py, z1) // top left front
                .color(color.r, color.g, color.b, color.a)
                .uv(minU, minV)
                .uv2(0x00F000F0).endVertex();

        bufferIn.vertex(matrix4f, x1 + px, y1 + py, z1) // bottom right front
                .color(color.r, color.g, color.b, color.a)
                .uv(maxU, minV) // right top
                .uv2(0x00F000F0).endVertex();

        bufferIn.vertex(matrix4f, x2 - px, y2 - py, z2) //  top left back
                .color(color.r, color.g, color.b, color.a)
                .uv(minU, maxV) // left bottom
                .uv2(0x00F000F0).endVertex();

        bufferIn.vertex(matrix4f, x2 + px, y2 + py, z2) // bottom right back
                .color(color.r, color.g, color.b, color.a)
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

//    public void unRotate() {
//        BillboardHelper.unRotate();
//    }
}