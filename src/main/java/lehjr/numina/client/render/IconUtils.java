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
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Pair;
import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

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
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(MissingTextureAtlasSprite.getLocation());
    }

    /**
     * Draws a TextureAtlasSprite
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconAt(double x, double y, @Nonnull TextureAtlasSprite icon, Color colour) {
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
    public static void drawIconAt(PoseStack poseStack, double x, double y, @Nonnull TextureAtlasSprite icon, Color colour) {
        drawIconPartial(poseStack, x, y, icon, colour, 0, 0, icon.contents().width(), icon.contents().height());
    }

    public static void drawIconPartialOccluded(double x, double y, @Nonnull TextureAtlasSprite icon, Color colour, double textureStarX, double textureStartY, double textureEndX, double textureEndY) {
        double xmin = MathUtils.clampDouble(textureStarX - x, 0, icon.contents().width());
        double ymin = MathUtils.clampDouble(textureStartY - y, 0, icon.contents().height());
        double xmax = MathUtils.clampDouble(textureEndX - x, 0, icon.contents().width());
        double ymax = MathUtils.clampDouble(textureEndY - y, 0, icon.contents().height());
        drawIconPartial(x, y, icon, colour, xmin, ymin, xmax, ymax);
    }

    /**
     * Draws a MuseIcon
     *
     * @param posX
     * @param posY
     * @param icon
     * @param colour
     */
    public static void drawIconPartial(PoseStack poseStack, double posX, double posY, TextureAtlasSprite icon, @Nonnull Color colour, double textureStartX, double textureStartY, double textureEndX, double textureEndY) {
        if (icon == null) {
            icon = getMissingIcon();
        }
        RenderSystem.setShaderTexture(0, icon.atlasLocation());
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tess.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        float minU = icon.getU0();
        float maxU = icon.getU1();
        float minV = icon.getV0();
        float maxV = icon.getV1();

        float iconWidthDiv = 1F/icon.contents().width();
        float icomHeightDiv = 1F/icon.contents().height();

        double xoffsetMin = textureStartX * (maxU - minU) * iconWidthDiv; // 0
        double xoffsetMax = textureEndX * (maxU - minU) * iconWidthDiv;
        double yoffsetMin = textureStartY * (maxV - minV) * icomHeightDiv; // 0
        double yoffsetMax = textureEndY * (maxV - minV) * icomHeightDiv;

        Matrix4f matrix4f = poseStack.last().pose();
//        System.out.println("textureStartX: " + textureStartX + ", textureEndX: " + textureEndX + ", textureStartY: " + textureStartY +", textureEndY: " + textureEndY);
        // top left
        bufferBuilder.vertex(matrix4f, (float) (posX + textureStartX), (float)(posY + textureStartY), 0);
        bufferBuilder.uv((float) (minU + xoffsetMin), (float) (minV + yoffsetMin));
        bufferBuilder.color(colour.r, colour.g, colour.b, colour.a);
        bufferBuilder.endVertex();

        // textureEndY left
        bufferBuilder.vertex(matrix4f, (float) (posX + textureStartX), (float) (posY + textureEndY), 0);
        bufferBuilder.uv((float) (minU + xoffsetMin), (float) (minV + yoffsetMax));
        bufferBuilder.color(colour.r, colour.g, colour.b, colour.a);
        bufferBuilder.endVertex();

        // textureEndY right
        bufferBuilder.vertex(matrix4f, (float) (posX + textureEndX), (float) (posY + textureEndY), 0);
        bufferBuilder.uv((float) (minU + xoffsetMax), (float) (minV + yoffsetMax));
        bufferBuilder.color(colour.r, colour.g, colour.b, colour.a);
        bufferBuilder.endVertex();

        // top right
        bufferBuilder.vertex(matrix4f, (float) (posX + textureEndX), (float) (posY + textureStartY), 0);
        bufferBuilder.uv((float) (minU + xoffsetMax), (float) (minV + yoffsetMin));
        bufferBuilder.color(colour.r, colour.g, colour.b, colour.a);
        bufferBuilder.endVertex();

        BufferUploader.drawWithShader(bufferBuilder.end());

    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconPartial(double x, double y, TextureAtlasSprite icon, Color colour, double left, double top, double right, double bottom) {
        if (icon == null) {
            icon = getMissingIcon();
        }

        RenderSystem.setShaderTexture(0, icon.atlasLocation());
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tess.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

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
        bufferBuilder.color(colour.r, colour.g, colour.b, colour.a);
        bufferBuilder.uv((float) (uMin + xoffset1), (float) (vMin + yoffset1));
        bufferBuilder.endVertex();

        // bottom left
        bufferBuilder.vertex(x + left, y + bottom, 0);
        bufferBuilder.color(colour.r, colour.g, colour.b, colour.a);
        bufferBuilder.uv((float) (uMin + xoffset1), (float) (vMin + yoffset2));
        bufferBuilder.endVertex();

        // bottom right
        bufferBuilder.vertex(x + right, y + bottom, 0);
        bufferBuilder.color(colour.r, colour.g, colour.b, colour.a);
        bufferBuilder.uv((float) (uMin + xoffset2), (float) (vMin + yoffset2));
        bufferBuilder.endVertex();

        // top right
        bufferBuilder.vertex(x + right, y + top, 0);
        bufferBuilder.color(colour.r, colour.g, colour.b, colour.a);
        bufferBuilder.uv((float) (uMin + xoffset2), (float) (vMin + yoffset1));
        bufferBuilder.endVertex();

        tess.end();
    }

    /** Code below based on Minecraft's AbstractGui ------------------------------------------------------------------------------------------------------------------------ */

    public static void blit(PoseStack matrixStack, double posX, double posY, double pBlitOffset, double drawWidth, double drawHeight, TextureAtlasSprite pSprite) {
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

    public void blit(PoseStack pPoseStack, double posX, double posY, double uOffset, double vOffset, double uWidth, double vHeight) {
        blit(pPoseStack, posX, posY, this.getBlitOffset(), uOffset, vOffset, uWidth, vHeight, 256, 256);
    }

    public static void blit(PoseStack pPoseStack, double posX, double posY, double blitOffset, double uOffset, double vOffset, double uWidth, double vHeight, double textureHeight, double textureWidth) {
        innerBlit(pPoseStack, posX, posX + uWidth, posY, posY + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void blit(PoseStack pPoseStack, double posX, double posY, double pBlitOffset, double pUOffset, double vOffset, double uWidth, double pVHeight, double textureHeight, double textureWidth, Color colour) {
        innerBlit(pPoseStack, posX, posX + uWidth, posY, posY + pVHeight, pBlitOffset, uWidth, pVHeight, pUOffset, vOffset, textureWidth, textureHeight, colour);
    }

    public static void blit(PoseStack pPoseStack, double drawStartX, double drawStartY, double pUOffset, double pVOffset, double pWidth, double pHeight, double textureWidth, double textureHeight) {
        blit(pPoseStack, drawStartX, drawStartY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, textureWidth, textureHeight);
    }

    public static void blit(PoseStack pPoseStack, double drawStartX, double drawStartY, double drawWidth, double drawHeight, double uOffset, double vOffset, double uWidth, double vHeight, double textureWidth, double textureHeight) {
        innerBlit(pPoseStack, drawStartX, drawStartX + drawWidth, drawStartY, drawStartY + drawHeight, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public static void innerBlit(PoseStack pPoseStack, double posX, double pX2, double pY1, double pY2, double pBlitOffset, double uWidth, double vHeight, double uOffset, double vOffset, double textureWidth, double textureHeight) {
        innerBlit(pPoseStack.last().pose(), posX, pX2, pY1, pY2, pBlitOffset,
                (uOffset + 0.0F) / textureWidth,
                (uOffset + uWidth) / textureWidth, (vOffset + 0.0F) / textureHeight,
                (vOffset + vHeight) / textureHeight);
    }

    public static void innerBlit(Matrix4f matrix4f, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uMin, double uMax, double vMin, double vMax) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float) drawStartX, (float) drawEndY, (float) blitOffset).uv((float) uMin, (float) vMax).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawEndX, (float) drawEndY, (float) blitOffset).uv((float) uMax, (float) vMax).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawEndX, (float) drawStartY, (float) blitOffset).uv((float) uMax, (float) vMin).endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawStartX, (float) drawStartY, (float) blitOffset).uv((float) uMin, (float) vMin).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    public static void blit(PoseStack matrixStack, double posX, double posY, double pBlitOffset, double drawWidth, double drawHeight, TextureAtlasSprite sprite, Color colour) {
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

    public void blit(PoseStack pPoseStack, double posX, double posY, double uOffset, double vOffset, double uWidth, double vHeight, Color colour) {
        blit(pPoseStack, posX, posY, this.getBlitOffset(), uOffset, vOffset, uWidth, vHeight, 256, 256, colour);
    }

    public static void blit(PoseStack pPoseStack, double posX, double posY, double pUOffset, double pVOffset, double pWidth, double pHeight, double textureWidth, double textureHeight, Color colour) {
        blit(pPoseStack, posX, posY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, textureWidth, textureHeight, colour);
    }

    public static void blit(PoseStack pPoseStack, double posX, double posY, double drawWidth, double drawHeight, double uOffset, double vOffset, double uWidth, double vHeight, double textureWidth, double textureHeight, Color colour) {
        innerBlit(pPoseStack, posX, posX + drawWidth, posY, posY + drawHeight, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight, colour);
    }

    public static void innerBlit(PoseStack pPoseStack, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uWidth, double vHeight, double uOffset, double vOffset, double textureWidth, double textureHeight, Color colour) {
        innerBlit(pPoseStack.last().pose(), drawStartX, drawEndX, drawStartY, drawEndY, blitOffset,
                (uOffset) / textureWidth,
                (uOffset + uWidth) / textureWidth,
                (vOffset) / textureHeight,
                (vOffset + vHeight) / textureHeight,
                colour);
    }

    public static void innerBlit(Matrix4f matrix4f, double drawStartX, double drawEndX, double drawStartY, double drawEndY, double blitOffset, double uMin, double uMax, double vMin, double vMax, Color colour) {
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        bufferbuilder.vertex(matrix4f, (float) drawStartX, (float) drawEndY, (float) blitOffset)
                .uv((float) uMin, (float) vMax)
                .color(colour.r, colour.g, colour.b, colour.a)
                .endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawEndX, (float) drawEndY, (float) blitOffset)
                .uv((float) uMax, (float) vMax)
                .color(colour.r, colour.g, colour.b, colour.a)
                .endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawEndX, (float) drawStartY, (float) blitOffset)
                .uv((float) uMax, (float) vMin)
                .color(colour.r, colour.g, colour.b, colour.a)
                .endVertex();
        bufferbuilder.vertex(matrix4f, (float) drawStartX, (float) drawStartY, (float) blitOffset)
                .uv((float) uMin, (float) vMin)
                .color(colour.r, colour.g, colour.b, colour.a)
                .endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    double getBlitOffset() {
        return getMinecraft().screen.getBlitOffset();
    }

    Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }


    public static final Map<EquipmentSlot, ResourceLocation> ARMOR_SLOT_TEXTURES = new HashMap<EquipmentSlot, ResourceLocation>(){{
        put(EquipmentSlot.HEAD, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET);
        put(EquipmentSlot.CHEST, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
        put(EquipmentSlot.LEGS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS);
        put(EquipmentSlot.FEET, InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS);
        put(EquipmentSlot.OFFHAND, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
        put(EquipmentSlot.MAINHAND, NuminaConstants.WEAPON_SLOT_BACKGROUND); //FIXME: broken for slot rendering, actually crashes
    }};

    public static final Pair<ResourceLocation, ResourceLocation> getSlotBackground(EquipmentSlot slotType) {
        switch (slotType) {
            case MAINHAND:
                return Pair.of(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, ARMOR_SLOT_TEXTURES.get(slotType)); // FIXME: broken for slot rendering, actually crashes
//                 return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            default:
                return Pair.of(InventoryMenu.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES.get(slotType));
        }
    }
}