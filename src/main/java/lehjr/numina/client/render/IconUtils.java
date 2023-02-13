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

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 2:37 PM, 9/6/13
 * <p>
 * Ported to Java by lehjr on 10/25/16.
 */
@OnlyIn(Dist.CLIENT)
public enum IconUtils {
    INSTANCE;
    public static GuiIcon getIcon() {
        Preconditions.checkState(icon != null, "Calling icons too early.");
        return icon;
    }

    private static GuiIcon icon;
    public static void setIconInstance(GuiIcon iconIn) {
        icon = iconIn;
    }

    static TextureAtlasSprite getMissingIcon() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(MissingTextureSprite.getLocation());
    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconAt(double x, double y, TextureAtlasSprite icon, Colour colour) {
        drawIconPartial(x, y, icon, colour, 0, 0, 16, 16);
    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconAt(MatrixStack poseStack, double x, double y, TextureAtlasSprite icon, Colour colour) {
        drawIconPartial(poseStack, x, y, icon, colour, 0, 0, 16, 16);
    }


    public static void drawIconPartialOccluded(double x, double y, TextureAtlasSprite icon, Colour colour, double textureStarX, double textureStartY, double textureEndX, double textureEndY) {
        double xmin = MathUtils.clampDouble(textureStarX - x, 0, 16);
        double ymin = MathUtils.clampDouble(textureStartY - y, 0, 16);
        double xmax = MathUtils.clampDouble(textureEndX - x, 0, 16);
        double ymax = MathUtils.clampDouble(textureEndY - y, 0, 16);
        drawIconPartial(x, y, icon, colour, xmin, ymin, xmax, ymax);
    }




    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconPartial(MatrixStack poseStack, double x, double y, TextureAtlasSprite icon, Colour colour, double textureStartX, double textureStartY, double textureEndX, double textureEndY) {
        if (icon == null) {
            icon = getMissingIcon();
        }

        Minecraft minecraft = Minecraft.getInstance();
        TextureManager textureManager = minecraft.getTextureManager();
        textureManager.bind(icon.atlas().location());


        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tess.getBuilder();
        if (colour != null) {
            colour.doGL();
        }
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        double u0 = icon.getU0();
        double v0 = icon.getV0();
        double u1 = icon.getU1();
        double v1 = icon.getV1();

        double xoffset1 = textureStartX * (u1 - u0) / 16.0f;
        double yoffset1 = textureStartY * (v1 - v0) / 16.0f;
        double xoffset2 = textureEndX * (u1 - u0) / 16.0f;
        double yoffset2 = textureEndY * (v1 - v0) / 16.0f;
        Matrix4f matrix4f = poseStack.last().pose();


        // top left
        bufferBuilder.vertex(matrix4f, (float) (x + textureStartX), (float)(y + textureStartY), 0);
        bufferBuilder.uv((float) (u0 + xoffset1), (float) (v0 + yoffset1));
        bufferBuilder.endVertex();

        // textureEndY left
        bufferBuilder.vertex(matrix4f, (float) (x + textureStartX), (float) (y + textureEndY), 0);
        bufferBuilder.uv((float) (u0 + xoffset1), (float) (v0 + yoffset2));
        bufferBuilder.endVertex();

        // textureEndY right
        bufferBuilder.vertex(matrix4f, (float) (x + textureEndX), (float) (y + textureEndY), 0);
        bufferBuilder.uv((float) (u0 + xoffset2), (float) (v0 + yoffset2));
        bufferBuilder.endVertex();

        // top right
        bufferBuilder.vertex(matrix4f, (float) (x + textureEndX), (float) (y + textureStartY), 0);
        bufferBuilder.uv((float) (u0 + xoffset2), (float) (v0 + yoffset1));
        bufferBuilder.endVertex();

        tess.end();

        RenderSystem.shadeModel(GL11.GL_FLAT);
    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconPartial(double x, double y, TextureAtlasSprite icon, Colour colour, double left, double top, double right, double bottom) {
        if (icon == null) {
            icon = getMissingIcon();
        }
        Minecraft minecraft = Minecraft.getInstance();
        TextureManager textureManager = minecraft.getTextureManager();
        textureManager.bind(icon.atlas().location());


        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tess.getBuilder();
        if (colour != null) {
            colour.doGL();
        }
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        double uMin = icon.getU0();
        double vMin = icon.getV0();
        double uMax = icon.getU1();
        double vMax = icon.getV1();

        double xoffset1 = left * (uMax - uMin) / 16.0f;
        double yoffset1 = top * (vMax - vMin) / 16.0f;
        double xoffset2 = right * (uMax - uMin) / 16.0f;
        double yoffset2 = bottom * (vMax - vMin) / 16.0f;

        // top left
        bufferBuilder.vertex(x + left, y + top, 0);
        bufferBuilder.uv((float) (uMin + xoffset1), (float) (vMin + yoffset1));
        bufferBuilder.endVertex();

        // bottom left
        bufferBuilder.vertex(x + left, y + bottom, 0);
        bufferBuilder.uv((float) (uMin + xoffset1), (float) (vMin + yoffset2));
        bufferBuilder.endVertex();

        // bottom right
        bufferBuilder.vertex(x + right, y + bottom, 0);
        bufferBuilder.uv((float) (uMin + xoffset2), (float) (vMin + yoffset2));
        bufferBuilder.endVertex();

        // top right
        bufferBuilder.vertex(x + right, y + top, 0);
        bufferBuilder.uv((float) (uMin + xoffset2), (float) (vMin + yoffset1));
        bufferBuilder.endVertex();

        tess.end();

        RenderSystem.shadeModel(GL11.GL_FLAT);
    }















    /** Code below based on Minecraft's AbstractGui ------------------------------------------------------------------------------------------------------------------------ */







    public static void blit(MatrixStack matrixStack, double posX, double posY, double pBlitOffset, double drawWidth, double drawHeight, TextureAtlasSprite pSprite) {
        innerBlit(matrixStack.last().pose(),
                posX, // drawStartX
                posX + drawWidth, // drawEndX
                posY, // drawStartY
                posY + drawHeight, // drawEndY
                pBlitOffset, // z
                pSprite.getU0(), // minU
                pSprite.getU1(), // maxU
                pSprite.getV0(), // minV
                pSprite.getV1()); // maxV
    }



    public void blit(MatrixStack pMatrixStack, double posX, double posY, double uOffset, double vOffset, double uWidth, double vHeight) {
        blit(pMatrixStack, posX, posY, this.getBlitOffset(), uOffset, vOffset, uWidth, vHeight, 256, 256);
    }

    public static void blit(MatrixStack pMatrixStack, double posX, double posY, double blitOffset, double uOffset, double vOffset, double uWidth, double vHeight, double textureHeight, double textureWidth) {
        innerBlit(pMatrixStack, posX, posX + uWidth, posY, posY + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void blit(MatrixStack pMatrixStack, double posX, double posY, double pBlitOffset, double pUOffset, double vOffset, double uWidth, double pVHeight, double textureHeight, double textureWidth, Colour colour) {
        innerBlit(pMatrixStack, posX, posX + uWidth, posY, posY + pVHeight, pBlitOffset, uWidth, pVHeight, pUOffset, vOffset, textureWidth, textureHeight, colour);
    }

    public static void blit(MatrixStack pMatrixStack, double drawStartX, double drawStartY, double pUOffset, double pVOffset, double pWidth, double pHeight, double textureWidth, double textureHeight) {
        blit(pMatrixStack, drawStartX, drawStartY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, textureWidth, textureHeight);
    }

    public static void blit(MatrixStack pMatrixStack, double drawStartX, double drawStartY, double drawWidth, double drawHeight, double uOffset, double vOffset, double uWidth, double vHeight, double textureWidth, double textureHeight) {
        innerBlit(pMatrixStack, drawStartX, drawStartX + drawWidth, drawStartY, drawStartY + drawHeight, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void innerBlit(MatrixStack pMatrixStack, double posX, double pX2, double pY1, double pY2, double pBlitOffset, double uWidth, double vHeight, double uOffset, double vOffset, double textureWidth, double textureHeight) {
        innerBlit(pMatrixStack.last().pose(), posX, pX2, pY1, pY2, pBlitOffset,
                (uOffset + 0.0F) / textureWidth,
                (uOffset + uWidth) / textureWidth, (vOffset + 0.0F) / textureHeight,
                (vOffset + vHeight) / textureHeight);
    }

    public static void innerBlit(Matrix4f matrix4f, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uMin, double uMax, double vMin, double vMax) {
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float) drawStartX, (float) drawEndY, (float) blitOffset).uv((float) uMin, (float) vMax).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawEndX, (float) drawEndY, (float) blitOffset).uv((float) uMax, (float) vMax).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawEndX, (float) drawStartY, (float) blitOffset).uv((float) uMax, (float) vMin).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawStartX, (float) drawStartY, (float) blitOffset).uv((float) uMin, (float) vMin).endVertex();
        bufferbuilder.end();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.end(bufferbuilder);
    }












    public static void blit(MatrixStack matrixStack, double posX, double posY, double pBlitOffset, double drawWidth, double drawHeight, TextureAtlasSprite sprite, Colour colour) {
        innerBlit(matrixStack.last().pose(),
                posX, // drawStartX
                posX + drawWidth, // drawEndX
                posY, // drawStartY
                posY + drawHeight, // drawEndY
                pBlitOffset, // z
                sprite.getU0(), // minU
                sprite.getU1(), // maxU
                sprite.getV0(), // minV
                sprite.getV1(),// maxV
                colour);
    }

    public void blit(MatrixStack pMatrixStack, double posX, double posY, double uOffset, double vOffset, double uWidth, double vHeight, Colour colour) {
        blit(pMatrixStack, posX, posY, this.getBlitOffset(), uOffset, vOffset, uWidth, vHeight, 256, 256, colour);
    }

    public static void blit(MatrixStack pMatrixStack, double posX, double posY, double pUOffset, double pVOffset, double pWidth, double pHeight, double textureWidth, double textureHeight, Colour colour) {
        blit(pMatrixStack, posX, posY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, textureWidth, textureHeight, colour);
    }

    public static void blit(MatrixStack pMatrixStack, double posX, double posY, double drawWidth, double drawHeight, double uOffset, double vOffset, double uWidth, double vHeight, double textureWidth, double textureHeight, Colour colour) {
        innerBlit(pMatrixStack, posX, posX + drawWidth, posY, posY + drawHeight, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight, colour);
    }

    public static void innerBlit(MatrixStack pMatrixStack, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uWidth, double vHeight, double uOffset, double vOffset, double textureWidth, double textureHeight, Colour colour) {
        innerBlit(pMatrixStack.last().pose(), drawStartX, drawEndX, drawStartY, drawEndY, blitOffset,
                (uOffset) / textureWidth,
                (uOffset + uWidth) / textureWidth,
                (vOffset) / textureHeight,
                (vOffset + vHeight) / textureHeight,
                colour);
    }

    public static void innerBlit(Matrix4f matrix4f, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uMin, double uMax, double vMin, double vMax, Colour colour) {
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);


        bufferbuilder.vertex(matrix4f, (float) drawStartX, (float) drawEndY, (float) blitOffset)
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv((float) uMin, (float) vMax).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawEndX, (float) drawEndY, (float) blitOffset)
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv((float) uMax, (float) vMax).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawEndX, (float) drawStartY, (float) blitOffset)
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv((float) uMax, (float) vMin).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawStartX, (float) drawStartY, (float) blitOffset)
                .color(colour.r, colour.g, colour.b, colour.a)
                .uv((float) uMin, (float) vMin).endVertex();

        bufferbuilder.end();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.end(bufferbuilder);
    }

    double getBlitOffset() {
        return getMinecraft().screen.getBlitOffset();
    }

    Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

}