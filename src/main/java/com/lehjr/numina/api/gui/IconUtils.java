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

package com.lehjr.numina.api.gui;

import com.google.common.base.Preconditions;
import com.lehjr.numina.api.constants.NuminaConstants;
import com.lehjr.numina.api.math.Color;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
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
    static final float SIXTEENTH = 0.0625F; // multiply by 1/16 as a decimal instead of dividing by 16

    public static GuiIcon getIcon() {
        Preconditions.checkState(INSTANCE.icon != null, "Calling icons too early.");
        return INSTANCE.icon;
    }

    protected static GuiIcon icon;

    public static void setIconInstance(GuiIcon iconIn) {
        INSTANCE.icon = iconIn;
    }

    static TextureAtlasSprite getMissingIcon() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(MissingTextureAtlasSprite.getLocation());
    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconAt(PoseStack poseStack, float x, float y, TextureAtlasSprite icon, Color colour) {
        drawIconPartial(poseStack, x, y, icon, colour, 0, 0, 16, 16);
    }

    /**
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void drawIconPartialOccluded(PoseStack poseStack, float x, float y, TextureAtlasSprite icon, Color colour, float left, float top, float right, float bottom) {
        float xmin = Mth.clamp(left - x, 0, 16); // 16x16 texture?
        float ymin = Mth.clamp(top - y, 0, 16);
        float xmax = Mth.clamp(right - x, 0, 16);
        float ymax = Mth.clamp(bottom - y, 0, 16);
        drawIconPartial(poseStack, x, y, icon, colour, xmin, ymin, xmax, ymax);
    }

    /**
     * Draws a MuseIcon
     *
     * @param x
     * @param y
     * @param icon
     * @param colour
     */
    public static void drawIconPartial(PoseStack poseStack, float x, float y, TextureAtlasSprite icon, Color colour, float left, float top, float right, float bottom) {
        if (icon == null) {
            icon = getMissingIcon();
        }

        // using texture coordinates from "icon" since it's in a sheet
        float minU = icon.getU0();
        float maxU = icon.getU1();
        float minV = icon.getV0();
        float maxV = icon.getV1();

        float xoffset1 = left * (maxU - minU) * SIXTEENTH;
        float yoffset1 = top * (maxV - minV) * SIXTEENTH;
        float xoffset2 = right * (maxU - minU) * SIXTEENTH;
        float yoffset2 = bottom * (maxV - minV) * SIXTEENTH;

        float blitOffset = 0;

            Matrix4f matrix4f = poseStack.last().pose();
            RenderSystem.setShaderTexture(0, icon.atlas().location());

            colour.setShaderColor();

            ShaderInstance oldShader = RenderSystem.getShader();

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

            // bottom left
            bufferbuilder.vertex(matrix4f, x + left, y + bottom, blitOffset)
                    .uv(minU + xoffset1, minV + yoffset1).endVertex();

            // bottom right
            bufferbuilder.vertex(matrix4f, x + right, y + bottom, blitOffset)
                    .uv(minU + xoffset2, minV + yoffset2).endVertex();

            // top right
            bufferbuilder.vertex(matrix4f, x + right, y + top, blitOffset)
                    .uv(minU + xoffset2, minV + yoffset1).endVertex();

            // top left
            bufferbuilder.vertex(matrix4f, x + left, y + top, blitOffset)
                    .uv(minU, maxV).endVertex();

            tesselator.end();
            RenderSystem.setShader(() -> oldShader);
    }
}