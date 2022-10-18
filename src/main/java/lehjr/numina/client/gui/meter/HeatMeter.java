package lehjr.numina.client.gui.meter;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Colour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

public class HeatMeter {
    final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("minecraft:block/lava_still");
    final int xsize = 32;
    final int ysize = 8;
    float meterStart, meterLevel;

    public float getAlpha() {
        return 0.7F;
    }

    public Colour getColour() {
        return Colour.WHITE;
    }

    // this "should" work
    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(TEXTURE_LOCATION);
    }

    public void draw(MatrixStack poseStack, float xpos, float ypos, float value) {
        RenderSystem.enableBlend();
        drawFluid(poseStack,xpos, ypos, value, getTexture());
        drawGlass(poseStack, xpos, ypos);
        RenderSystem.disableBlend();
    }

    public void drawFluid(MatrixStack poseStack, float xpos, float ypos, float value, TextureAtlasSprite icon) {
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

    /**
     * finally got this working again
     */
    public void drawGlass(MatrixStack poseStack, float xpos, float ypos) {
        float minU = 0F;
        float maxU = 1F;
        float minV = 0F;
        float maxV = 1F;

        float blitOffset = 0;
        float left = xpos;
        float right = left + xsize;
        float top = ypos;
        float bottom = top + ysize;
        Matrix4f matrix4f = poseStack.last().pose();
//        RenderSystem.setShaderTexture(0, NuminaConstants.GLASS_TEXTURE);
//
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//
//        ShaderInstance oldShader = RenderSystem.getShader();
//
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        Minecraft minecraft = Minecraft.getInstance();
        TextureManager textureManager = minecraft.getTextureManager();
        textureManager.bind(NuminaConstants.GLASS_TEXTURE);


        Tessellator tesselator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        // bottom left
        bufferbuilder.vertex(matrix4f, left, bottom, blitOffset).uv(maxU, maxV).endVertex();

        // bottom right
        bufferbuilder.vertex(matrix4f, right, bottom, blitOffset).uv(maxU, minV).endVertex();

        // top right
        bufferbuilder.vertex(matrix4f, right, top, blitOffset).uv(minU, minV).endVertex();

        // top left
        bufferbuilder.vertex(matrix4f, left, top, blitOffset).uv(minU, maxV).endVertex();

        tesselator.end();
//        RenderSystem.setShader(() -> oldShader);
    }
}
