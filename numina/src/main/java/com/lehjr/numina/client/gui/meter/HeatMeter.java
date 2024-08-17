package com.lehjr.numina.client.gui.meter;

import com.lehjr.numina.client.config.IMeterConfig;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.utils.IconUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

import java.util.concurrent.Callable;

public class HeatMeter {
    final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath("minecraft", "block/lava_still");
    final int xsize = 32;
    final int ysize = 8;
    float meterStart, meterLevel;

    Callable<IMeterConfig> config;

    public HeatMeter(Callable<IMeterConfig>config) {
        this.config = config;
    }

    private HeatMeter() {
        this.config = () -> new IMeterConfig() {};
    }

    // this "should" work
    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE_LOCATION);
    }

    IMeterConfig getConfig() {
        try {
            return config.call();
        } catch (Exception e) {
            // not initialized yet (shouldn't happen)
            e.printStackTrace();
        }
        return new IMeterConfig() {};
    }

    public void draw(GuiGraphics gfx, float xpos, float ypos, float value) {
        value = Mth.clamp(value + getConfig().getDebugValue(), 0F, 1F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        drawFluid(gfx,xpos, ypos, value, getTexture());
        drawGlass(gfx, xpos, ypos);
        RenderSystem.disableBlend();
    }

    public void drawFluid(GuiGraphics gfx, float xpos, float ypos, float value, TextureAtlasSprite icon) {
        value = Math.min(value, 1F);
        gfx.pose().pushPose();
        gfx.pose().scale(0.5F, 0.5F, 0.5F);
        // New: Horizontal, fill from left.
        meterStart = xpos;
        meterLevel = (xpos + xsize * value);
        while (meterStart + 8 < meterLevel) {
            IconUtils.drawIconAt(gfx.pose(), meterStart * 2, ypos * 2, icon, getConfig().getBarColor());
            meterStart += 8;
        }
        IconUtils.drawIconPartial(gfx.pose(), meterStart * 2, ypos * 2, icon, getConfig().getBarColor(),
                0, 0, (meterLevel - meterStart) * 2, 16);
        gfx.pose().popPose();
    }

    /**
     *
     */
    public void drawGlass(GuiGraphics gfx, float xpos, float ypos) {
        float minU = 0F;
        float maxU = 1F;
        float minV = 0F;
        float maxV = 1F;

        float blitOffset = 0;
        float left = xpos;
        float right = left + xsize;
        float top = ypos;
        float bottom = top + ysize;

        Tesselator tesselator = Tesselator.getInstance();


        BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, NuminaConstants.GLASS_TEXTURE);
        Matrix4f matrix4f = gfx.pose().last().pose();

        // bottom left
        bufferbuilder.addVertex(matrix4f, left, bottom, blitOffset)
                .setUv(maxU, maxV)
                .setColor(getConfig().getGlassColor().r, getConfig().getGlassColor().g, getConfig().getGlassColor().b, getConfig().getGlassColor().a);

        // bottom right
        bufferbuilder.addVertex(matrix4f, right, bottom, blitOffset)
                .setUv(maxU, minV)
                .setColor(getConfig().getGlassColor().r, getConfig().getGlassColor().g, getConfig().getGlassColor().b, getConfig().getGlassColor().a);

        // top right
        bufferbuilder.addVertex(matrix4f, right, top, blitOffset)
                .setUv(minU, minV)
                .setColor(getConfig().getGlassColor().r, getConfig().getGlassColor().g, getConfig().getGlassColor().b, getConfig().getGlassColor().a);

        // top left
        bufferbuilder.addVertex(matrix4f, left, top, blitOffset)
                .setUv(minU, maxV)
                .setColor(getConfig().getGlassColor().r, getConfig().getGlassColor().g, getConfig().getGlassColor().b, getConfig().getGlassColor().a);
    }
}
