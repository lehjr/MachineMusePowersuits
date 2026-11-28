package lehjr.numina.client.gui.meter;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import lehjr.numina.client.config.IMeterConfig;
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
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void draw(GuiGraphics gfx, float xpos, float ypos, float value) {
        value = Mth.clamp(value + getConfig().getDebugValue(), 0F, 1F);
        ShaderInstance oldShader = RenderSystem.getShader();
        drawFluid(gfx,xpos, ypos, value, getTexture());
        drawAdditional(gfx, xpos, ypos, value);
        drawGlass(gfx, xpos, ypos);
        RenderSystem.setShader(()->oldShader);
    }

    public void drawAdditional(GuiGraphics gfx, float xpos, float ypos, float value) {
    }

//    public void drawFluid(GuiGraphics gfx, float left, float top, float value, TextureAtlasSprite icon) {
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        int argbColor = getConfig().getBarColor().getARGBInt();
//        if(frames.isEmpty()) {
//            icon.contents().getUniqueFrames().forEach(frame -> frames.add(frame));
//        }
//
//        value = Math.min(value, 1F);
//        gfx.pose().pushPose();
//        gfx.pose().scale(0.5F, 0.5F, 0.5F);
//        // New: Horizontal, fill from left.
//        meterStart = left;
//        meterLevel = (left + xsize * value);
//        while (meterStart + 8 < meterLevel) {
//            IconUtils.drawIconAt(gfx.pose(), meterStart * 2, top * 2, icon, argbColor);
//            meterStart += 8;
//        }
//        IconUtils.drawIconPartial(gfx.pose(), meterStart * 2, top * 2, icon, argbColor,
//            0, 0, (meterLevel - meterStart) * 2, 16);
//        gfx.pose().popPose();
//        RenderSystem.disableBlend();
//    }

    public void drawFluid(GuiGraphics gfx, float left, float top, float value, @Nullable TextureAtlasSprite icon) {
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
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

            gfx.pose().pushPose();
            gfx.pose().scale(0.5F, 0.5F, 0.5F);

            Matrix4f matrix4f = gfx.pose().last().pose();
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            float meterStart = left;
            float meterLevel = left + xsize * value;

            while (meterStart + 8 < meterLevel) {
                IconUtils.appendIconPartial(bufferBuilder, matrix4f, meterStart * 2, top * 2, icon, color, 0, 0, 16, 16);
                meterStart += 8;
            }
            if (meterLevel > meterStart) {
                IconUtils.appendIconPartial(bufferBuilder, matrix4f, meterStart * 2, top * 2, icon, color, 0, 0, (meterLevel - meterStart) * 2, 16);
            }

            BufferUploader.drawWithShader(Objects.requireNonNull(bufferBuilder.build()));
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
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        Matrix4f matrix4f = gfx.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        // bottom left
        bufferbuilder.addVertex(matrix4f, left, bottom, blitOffset)
            .setUv(maxU, maxV)
            .setColor(glassColor);

        // bottom right
        bufferbuilder.addVertex(matrix4f, right, bottom, blitOffset)
            .setUv(maxU, minV)
            .setColor(glassColor);

        // top right
        bufferbuilder.addVertex(matrix4f, right, top, blitOffset)
            .setUv(minU, minV)
            .setColor(glassColor);

        // top left
        bufferbuilder.addVertex(matrix4f, left, top, blitOffset)
            .setUv(minU, maxV)
            .setColor(glassColor);

        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        RenderSystem.disableBlend();
    }
}
