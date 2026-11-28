package lehjr.numina.client.gui.meter;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import lehjr.numina.client.config.IMeterConfig;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class HeatMeter {
    final FluidStack fluidStack = new FluidStack(Fluids.FLOWING_LAVA, 1000);
    final ResourceLocation TEXTURE_LOCATION = IClientFluidTypeExtensions.of(Fluids.FLOWING_LAVA).getFlowingTexture(fluidStack);

    final int xsize = 32;
    final int ysize = 8;

    Callable<IMeterConfig> config;

    public HeatMeter(Callable<IMeterConfig>config) {
        this.config = config;
    }

    private HeatMeter() {
        this.config = () -> new IMeterConfig() {};
    }

    List<Integer> frames = new ArrayList<>();
    // this "should" work
    public TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(TEXTURE_LOCATION);
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

    public void draw(GuiGraphics gfx, float left, float top, float value) {
        value = Mth.clamp(value + getConfig().getDebugValue(), 0F, 1F);

        ShaderInstance oldShader = RenderSystem.getShader();
        try {
            drawFluid(gfx,left, top, value, getTexture());
            drawAdditional(gfx, left, top, value);
            drawGlass(gfx, left, top);
        } finally {
            if (oldShader != null) {
                RenderSystem.setShader(()->oldShader);
            }
        }
    }

    public void drawAdditional(GuiGraphics gfx, float left, float top, float value) {
    }

    public void drawFluid(GuiGraphics gfx, float left, float top, float value, TextureAtlasSprite icon) {
        if(value > 0) {
            if (icon == null) {
                icon = IconUtils.getMissingIcon();
            }

            value = Mth.clamp(value, 0.0F, 1.0F);
            if (value <= 0.0F) {
                return;
            }

            Color color = getConfig().getBarColor();

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderTexture(0, icon.atlasLocation());
            RenderSystem.setShader(GameRenderer::getPositionColorTexShader);

            gfx.pose().pushPose();
            gfx.pose().scale(0.5F, 0.5F, 0.5F);

            Matrix4f matrix4f = gfx.pose().last().pose();
            BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

            float meterStart = left;
            float meterLevel = left + xsize * value;

            while (meterStart + 8 < meterLevel) {
                IconUtils.appendIconPartial(bufferBuilder, matrix4f, meterStart * 2, top * 2, icon, color, 0, 0, 16, 16);
                meterStart += 8;
            }
            if (meterLevel > meterStart) {
                IconUtils.appendIconPartial(bufferBuilder, matrix4f, meterStart * 2, top * 2, icon, color, 0, 0, (meterLevel - meterStart) * 2, 16);
            }

            BufferUploader.drawWithShader(bufferBuilder.end());
            gfx.pose().popPose();
        }
    }

    /**
     *
     */
    public void drawGlass(GuiGraphics gfx, float left, float top) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        int glassColor = getConfig().getGlassColor().getARGBInt();
        float minU = 0F;
        float maxU = 1F;
        float minV = 0F;
        float maxV = 1F;

        float blitOffset = 0;
        float right = left + xsize;
        float bottom = top + ysize;

        RenderSystem.setShaderTexture(0, NuminaConstants.GLASS_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        Matrix4f matrix4f = gfx.pose().last().pose();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

        // bottom left
        bufferbuilder.vertex(matrix4f, left, bottom, blitOffset)
                .color(glassColor)
                .uv(maxU, maxV)
                .endVertex();

        // bottom right
        bufferbuilder.vertex(matrix4f, right, bottom, blitOffset)
                .color(glassColor)
                .uv(maxU, minV)
                .endVertex();

        // top right
        bufferbuilder.vertex(matrix4f, right, top, blitOffset)
                .color(glassColor)
                .uv(minU, minV)
                .endVertex();

        // top left
        bufferbuilder.vertex(matrix4f, left, top, blitOffset)
                .color(glassColor)
                .uv(minU, maxV)
                .endVertex();

        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }
}
