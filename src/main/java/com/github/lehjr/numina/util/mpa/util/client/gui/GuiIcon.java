/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.mpa.util.client.gui;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.client.NuminaSpriteUploader;
import com.github.lehjr.numina.util.client.render.mpa.MPALibRenderState;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.client.render.BillboardHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

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
    public final DrawableGuiIcon armorColourPatch;
    public final DrawableGuiIcon minusSign;
    public final DrawableGuiIcon plusSign;
    public final DrawableGuiIcon glassTexture;
    public final DrawableGuiIcon lightning;

    public GuiIcon(NuminaSpriteUploader spriteUploader) {
        this.spriteUploader = spriteUploader;
        checkmark = registerIcon("checkmark", 16, 16);
        transparentArmor = registerIcon("transparentarmor", 8, 8);
        normalArmor = registerIcon("normalarmor", 8, 8);
        glowArmor= registerIcon("glowarmor", 8, 8);
        selectedArmorOverlay = registerIcon("armordisplayselect", 8, 8);
        armorColourPatch = registerIcon("colourclicker", 8, 8);
        minusSign = registerIcon("minussign", 8, 8);
        plusSign= registerIcon("plussign", 8, 8);
        glassTexture = registerIcon("glass", 1, 8);
        lightning = registerIcon("lightning", 800, 62);
    }

    private DrawableGuiIcon registerIcon(String name, int width, int height) {
        ResourceLocation location = new ResourceLocation(NuminaConstants.MOD_ID, name);
        spriteUploader.registerIcon(location);
        return new DrawableGuiIcon(location, width, height, iconPrefix);
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
        String prefix;

        protected DrawableGuiIcon(ResourceLocation locationIn, int width, int height, String prefix) {
            this.location = locationIn;
            this.width = width;
            this.height = height;
            this.prefix = prefix;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void draw(MatrixStack matrixStack, double x, double y, Colour colour) {
            draw(matrixStack, x, y, 0, 0, 0, 0, colour);
        }



        public void draw(MatrixStack matrixStack, double xOffset, double yOffset, double maskTop, double maskBottom, double maskLeft, double maskRight, Colour colour) {
            double textureWidth = this.width;
            double textureHeight = this.height;

            bindTexture();
            TextureAtlasSprite icon = spriteUploader.getSprite(location);
            float zLevel = getMinecraft().currentScreen.getBlitOffset();

            double posLeft = xOffset + maskLeft;
            double posTop = yOffset + maskTop;
            double width = textureWidth - maskRight - maskLeft;
            double height = textureHeight - maskBottom - maskTop;

            double posRight = posLeft + width;
            double posBottom = posTop + height;

            double uSize = icon.getMaxU() - icon.getMinU();
            double vSize = icon.getMaxV() - icon.getMinV();
            float minU = (float) (icon.getMinU() + uSize * (maskLeft / textureWidth));
            float minV = (float) (icon.getMinV() + vSize * (maskTop / textureHeight));
            float maxU = (float) (icon.getMaxU() - uSize * (maskRight / textureWidth));
            float maxV = (float) (icon.getMaxV() - vSize * (maskBottom / textureHeight));

            RenderSystem.enableBlend();
            RenderSystem.disableAlphaTest();
            RenderSystem.defaultBlendFunc();
            innerBlit(matrixStack.getLast().getMatrix(), posLeft, posRight, posTop, posBottom, zLevel, minU, maxU, minV, maxV, colour);
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
            textureManager.bindTexture(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
            TextureAtlasSprite icon = getSprite();

            if (icon != null) {
                return "icon: " + icon.toString();
            } else {
                return "icon is null for location: " + location.toString();
            }
        }

        public void drawLightning(IRenderTypeBuffer bufferIn, MatrixStack matrixStack, float x1, float y1, float z1, float x2, float y2, float z2, Colour colour) {
            TextureAtlasSprite icon = getSprite();
//            bindTexture();
//            System.out.println("toString: " + toString());

            drawLightningTextured(bufferIn.getBuffer(MPALibRenderState.LIGHTNING_TEST()),
                    matrixStack.getLast().getMatrix(),
                    x1,
                    y1,
                    z1,
                    x2,
                    y2,
                    z2,
                    colour,
                    icon,
                    this.width,
                    this.height);
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
    public static void renderIcon8(ResourceLocation location, MatrixStack matrixStack, double left, double top, double right, double bottom, float zLevel) {
        renderIcon8(location, matrixStack, left, top, right, bottom, zLevel, Colour.WHITE);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     * @param colour color to apply to the texture
     */
    public static void renderIcon8(ResourceLocation location, MatrixStack matrixStack, double left, double top, double right, double bottom, float zLevel, Colour colour) {
        renderTextureWithColour(location, matrixStack, left, right, top, bottom, zLevel, 8, 8, 0, 0, 8, 8, colour);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     */
    public static void renderIcon16(ResourceLocation location, MatrixStack matrixStack, double left, double top, double right, double bottom, float zLevel) {
        renderIcon16(location, matrixStack, left, top, right, bottom, zLevel, Colour.WHITE);
    }

    /**
     *
     * @param location resource location of the Icon
     * @param left the left most position of the drawing rectangle
     * @param top the top most position of the drawing rectangle
     * @param bottom bottom most position of the drawing rectangle
     * @param zLevel depth to draw at
     * @param colour color to apply to the texture
     */
    public static void renderIcon16(ResourceLocation location, MatrixStack matrixStack, double left, double top, double right, double bottom, float zLevel, Colour colour) {
        renderTextureWithColour(location, matrixStack, left, right, top, bottom, zLevel, 16, 16, 0, 0, 16, 16, colour);
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
     * @param colour the Colour to apply to the texture
     */
    public static void renderTextureWithColour(ResourceLocation location, MatrixStack matrixStack,
                                               double left, double right, double top, double bottom, float zLevel, double iconWidth, double iconHeight, double texStartX, double texStartY, double textureWidth, double textureHeight, Colour colour) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(location);
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        innerBlit(matrixStack, left, right, top, bottom, zLevel, iconWidth, iconHeight, texStartX, texStartY, textureWidth, textureHeight, colour);
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
     * @param colour colour to apply to the texture
     */
    private static void innerBlit(MatrixStack matrixStack, double left, double right, double top, double bottom, float zLevel, double iconWidth, double iconHeight, double texStartX, double texStartY, double textureWidth, double textureHeight, Colour colour) {
        innerBlit(matrixStack.getLast().getMatrix(), left, right, top, bottom, zLevel,
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
     * @param colour the Colour to apply to the texture
     */
//    public static void innerBlit(float left, float right, float top, float bottom, float zLevel, float minU, float maxU, float minV, float maxV, Colour colour) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferBuilder = tessellator.getBuffer();
//
//        colour.doGL();
//        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
//        // bottom left
//        bufferBuilder.pos(left, bottom, zLevel).tex(minU, maxV).endVertex();
//        // bottom right
//        bufferBuilder.pos(right, bottom, zLevel).tex(maxU, maxV).endVertex();
//        // top right
//        bufferBuilder.pos(right, top, zLevel).tex(maxU, minV).endVertex();
//        // top left
//        bufferBuilder.pos(left, top, zLevel).tex(minU, minV).endVertex();
//
//        tessellator.draw();
//    }

    /**
     * Basically like vanilla's version but with floats and a colour parameter
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
     * @param colour the Colour to apply to the texture
     */
    private static void innerBlit(Matrix4f matrix4f, double left, double right, double top, double bottom, float zLevel, float minU, float maxU, float minV, float maxV, Colour colour) {
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();

        colour.doGL();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        // bottom left
        bufferbuilder.pos(matrix4f, (float)left, (float)bottom, zLevel).tex(minU, maxV).endVertex();

        // bottom right
        bufferbuilder.pos(matrix4f, (float)right, (float)bottom, zLevel).tex(maxU, maxV).endVertex();

        // top right
        bufferbuilder.pos(matrix4f, (float)right, (float)top, zLevel).tex(maxU, minV).endVertex();

        // top left
        bufferbuilder.pos(matrix4f, (float)left, (float)top, zLevel).tex(minU, minV).endVertex();

        bufferbuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }

    public void drawLightningTextured(IVertexBuilder bufferIn, Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, Colour colour, TextureAtlasSprite icon, float textureWidth, float textureHeight) {
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();
        float uSize = icon.getMaxU() - icon.getMinU();

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
            float minU = icon.getMinU() + uSize * (index * 0.2F); // 1/50, there are 50 different lightning elements in the texture
            float maxU = minU + uSize * 0.2F;

            drawLightningBetweenPointsFast(bufferIn, matrix4f, ax, ay, az, bx, by, bz, colour, minU, maxU, minV, maxV);
        }
    }

    void drawLightningBetweenPointsFast(IVertexBuilder bufferIn, Matrix4f matrix4f,
                                               float x1, float y1, float z1, float x2, float y2, float z2,
                                               Colour colour,
                                        float minU, float maxU, float minV, float maxV) {
        float px = (y1 - y2) * 0.125F;
        float py = (x2 - x1) * 0.125F;

//        // bottom left
//        bufferIn.pos(matrix4f, x2 - px, y2 - py, z2) //  top left back?
//                .color(colour.r, colour.g, colour.b, colour.a)
//                .tex(minU, maxV) // left bottom
//                .lightmap(0x00F000F0).endVertex();
//
//        bufferIn.pos(matrix4f, x2 + px, y2 + py, z2) // bottom right back
//                .color(colour.r, colour.g, colour.b, colour.a)
//                .tex(maxU, maxV) // right bottom
//                .lightmap(0x00F000F0).endVertex();
//
//        bufferIn.pos(matrix4f, x1 + px, y1 + py, z1) // bottom right front
//                .color(colour.r, colour.g, colour.b, colour.a)
//                .tex(maxU, minV) // right top
//                .lightmap(0x00F000F0).endVertex();
//
//        bufferIn.pos(matrix4f, x1 - px, y1 - py, z1) // top left front
//                .color(colour.r, colour.g, colour.b, colour.a)
//                .tex(minU, minV)
//                .lightmap(0x00F000F0).endVertex();


        bufferIn.pos(matrix4f, x1 - px, y1 - py, z1) // top left front
                .color(colour.r, colour.g, colour.b, colour.a)
                .tex(minU, minV)
                .lightmap(0x00F000F0).endVertex();

        bufferIn.pos(matrix4f, x1 + px, y1 + py, z1) // bottom right front
                .color(colour.r, colour.g, colour.b, colour.a)
                .tex(maxU, minV) // right top
                .lightmap(0x00F000F0).endVertex();

        bufferIn.pos(matrix4f, x2 - px, y2 - py, z2) //  top left back
                .color(colour.r, colour.g, colour.b, colour.a)
                .tex(minU, maxV) // left bottom
                .lightmap(0x00F000F0).endVertex();

        bufferIn.pos(matrix4f, x2 + px, y2 + py, z2) // bottom right back
                .color(colour.r, colour.g, colour.b, colour.a)
                .tex(maxU, maxV) // right bottom
                .lightmap(0x00F000F0).endVertex();
    }

    Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    void bindTexture() {
        TextureManager textureManager = getMinecraft().getTextureManager();
        textureManager.bindTexture(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS);
    }

    int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void unRotate() {
        BillboardHelper.unRotate();
    }
}