package lehjr.numina.client.gui.meter;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class HeatMeter {
    final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("minecraft:block/lava_still");
    final int xsize = 32;
    final int ysize = 8;
    float meterStart, meterLevel;

    public float getAlpha() {
        return 0.75F;
    }

    public Color getColour() {
        return Color.WHITE.withAlpha(0.3F);
    }

    // this "should" work
    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE_LOCATION);
    }

    public void draw(PoseStack poseStack, float xpos, float ypos, float value) {
        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();
        Lighting.setupForEntityInInventory();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        drawFluid(poseStack,xpos, ypos, value, getTexture());
        drawGlass(poseStack, xpos, ypos);
        RenderSystem.disableBlend();
        RenderSystem.setShader(() -> oldShader);
    }

    public void drawFluid(PoseStack poseStack, float xpos, float ypos, float value, TextureAtlasSprite icon) {
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
        // New: Horizontal, fill from left.
        meterStart = xpos;
        meterLevel = (xpos + xsize * value);
        while (meterStart + 8 < meterLevel) {
            IconUtils.drawIconAt(poseStack, meterStart * 2, ypos * 2, icon, getColour().withAlpha(getAlpha()));
            meterStart += 8;
        }
        IconUtils.drawIconPartial(poseStack, meterStart * 2, ypos * 2, icon, getColour().withAlpha(getAlpha()), 0, 0, (meterLevel - meterStart) * 2, 16);
        poseStack.popPose();
    }

    final Color glassColor = Color.LIGHT_GREY.withAlpha(0.2F);


    /**
     * finally got this working again
     */
    public void drawGlass(PoseStack poseStack, float xpos, float ypos) {
        float minU = 0F;
        float maxU = 1F;
        float minV = 0F;
        float maxV = 1F;

        float blitOffset = 0;
        float left = xpos;
        float right = left + xsize;
        float top = ypos;
        float bottom = top + ysize;


        RenderSystem.setShaderTexture(0, NuminaConstants.GLASS_TEXTURE);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        Matrix4f matrix4f = poseStack.last().pose();

        // bottom left
        bufferbuilder.vertex(matrix4f, left, bottom, blitOffset)
                .uv(maxU, maxV)
                .color(glassColor.r, glassColor.g, glassColor.b, glassColor.a)
                .endVertex();

        // bottom right
        bufferbuilder.vertex(matrix4f, right, bottom, blitOffset)
                .uv(maxU, minV)
                .color(glassColor.r, glassColor.g, glassColor.b, glassColor.a)
                .endVertex();

        // top right
        bufferbuilder.vertex(matrix4f, right, top, blitOffset)
                .uv(minU, minV)
                .color(glassColor.r, glassColor.g, glassColor.b, glassColor.a)
                .endVertex();

        // top left
        bufferbuilder.vertex(matrix4f, left, top, blitOffset)
                .uv(minU, maxV)
                .color(glassColor.r, glassColor.g, glassColor.b, glassColor.a)
                .endVertex();

        tesselator.end();
    }
}

