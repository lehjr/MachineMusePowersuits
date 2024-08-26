package com.lehjr.numina.client.gui.frame;

import com.lehjr.numina.client.gui.geometry.IDrawableRect;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.MathUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import org.joml.Matrix4f;

public interface IScrollable extends IGuiFrame, IDrawableRect {
    void setTotalSize(int totalSize);

    int getButtonSize();

    void setButtonSize(int buttonSize);

    int getTotalSize();

    double getCurrentScrollPixels();

    void setCurrentScrollPixels(double scrollPixels);

    default double getMaxScrollPixels() {
        return Math.max(getTotalSize() - height(), 0);
    }

    default double getScrollAmount() {
        return 8;
    }

    default void setScrollAmount(double scrollAmount) {
        setCurrentScrollPixels((int) MathUtils.clampDouble(scrollAmount, 0, getMaxScrollPixels()));
    }

    @Override
    default boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (this.containsPoint(mouseX, mouseY)) {
            // prevent negative total scroll values
            setCurrentScrollPixels((int) MathUtils.clampDouble(getCurrentScrollPixels() - dWheel * getScrollAmount(), 0, getMaxScrollPixels()));
            return true;
        }
        return false;
    }

    @Override
    default boolean mouseClicked(double x, double y, int button) {
        if (isVisible() && containsPoint(x, y) && button == 0) {
            double dscroll = 0;
            if (y - top() < getButtonSize() && this.getCurrentScrollPixels() > 0) {
                dscroll = (dscroll - this.getScrollAmount());
            } else if (bottom() - y < getButtonSize()) {
                dscroll = (dscroll + this.getScrollAmount());
            }
            if (dscroll != 0) {
                setCurrentScrollPixels(MathUtils.clampDouble(this.getCurrentScrollPixels() + dscroll, 0.0D, this.getMaxScrollPixels()));
            }
            return true;
        }
        return false;
    }

    default void canScrollDown(GuiGraphics gfx, int mouseX, int mouseY, float frameTIme) {
        if (getCurrentScrollPixels() + height() < getTotalSize()) {
            ShaderInstance oldShader = RenderSystem.getShader();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.enableBlend();
            Lighting.setupForEntityInInventory();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder buffer = tessellator.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_LIGHTMAP);
            Matrix4f matrix4f = gfx.pose().last().pose();

            // Can scroll down

            buffer.addVertex(matrix4f, (float) (left() + width() / 2F), (float) bottom(), getZLevel())
                .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                .setLight(0x00F000F0);

            buffer.addVertex(matrix4f, (float) (left() + width() / 2 + 2), (float) bottom() - 4, getZLevel())
                .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                .setLight(0x00F000F0);

            buffer.addVertex(matrix4f, (float) (left() + width() / 2 - 2), (float) bottom() - 4, getZLevel())
                .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                .setLight(0x00F000F0);

            BufferUploader.drawWithShader(buffer.buildOrThrow());
            RenderSystem.disableBlend();
            RenderSystem.setShader(() -> oldShader);
        }
    }

    default void canScrollUp(GuiGraphics gfx, int mouseX, int mouseY, float frameTIme) {
        if (getCurrentScrollPixels() > 0) {
            ShaderInstance oldShader = RenderSystem.getShader();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.enableBlend();
            Lighting.setupForEntityInInventory();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder buffer = tessellator.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_LIGHTMAP);
            Matrix4f matrix4f = gfx.pose().last().pose();

            buffer.addVertex(matrix4f, (float) (left() + width() / 2), (float) top(), getZLevel())
                .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                .setLight(0x00F000F0);
            buffer.addVertex(matrix4f, (float) (left() + width() / 2 - 2), (float) top() + 4, getZLevel())
                .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                .setLight(0x00F000F0);
            buffer.addVertex(matrix4f, (float) (left() + width() / 2 + 2), (float) top() + 4, getZLevel())
                .setColor(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                .setLight(0x00F000F0);

            BufferUploader.drawWithShader(buffer.buildOrThrow());
            RenderSystem.disableBlend();
            RenderSystem.setShader(() -> oldShader);
        }
    }


    @Override
    default void preRender(GuiGraphics gfx, int mouseX, int mouseY, float frameTIme) {
        if (isVisible()) {
            canScrollDown(gfx, mouseX, mouseY, frameTIme);
            canScrollUp(gfx, mouseX, mouseY, frameTIme);
            enableScissor((int)left(), (int)top(), (int)width(), (int)height()); // get rid of margins
        }
    }

    static void enableScissor(double x, double y, double w, double h) {
        Minecraft mc = Minecraft.getInstance();
        int dh = mc.getWindow().getHeight();
        double scaleFactor = mc.getWindow().getGuiScale();
        double newx = x * scaleFactor;
        double newy = dh - h * scaleFactor - y * scaleFactor;
        double neww = w * scaleFactor;
        double newh = h * scaleFactor;
        RenderSystem.enableScissor((int) newx, (int) newy, (int) neww, (int) newh);
    }

    @Override
    default void postRender(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            RenderSystem.disableScissor();
        }
    }
}
