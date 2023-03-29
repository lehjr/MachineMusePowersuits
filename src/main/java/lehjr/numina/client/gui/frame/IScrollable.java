package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import lehjr.numina.client.gui.geometry.IDrawableRect;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;

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


    @Override
    default void preRender(PoseStack matrixStack, int mouseX, int mouseY, float frameTIme) {
        if (isVisible()) {
//            ShaderInstance oldShader = RenderSystem.getShader();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            Lighting.setupForEntityInInventory();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder buffer = tessellator.getBuilder();
            buffer.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_LIGHTMAP);
            Matrix4f matrix4f = matrixStack.last().pose();

            // Can scroll down
            if (getCurrentScrollPixels() + height() < getTotalSize()) {
                buffer.vertex(matrix4f, (float) (left() + width() / 2F), (float) bottom(), getZLevel())
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                buffer.vertex(matrix4f, (float) (left() + width() / 2 + 2), (float) bottom() - 4, getZLevel())
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                buffer.vertex(matrix4f, (float) (left() + width() / 2 - 2), (float) bottom() - 4, getZLevel())
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
            }

            // Can scroll up
            if (getCurrentScrollPixels() > 0) {
                buffer.vertex(matrix4f, (float) (left() + width() / 2), (float) top(), getZLevel())
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
                buffer.vertex(matrix4f, (float) (left() + width() / 2 - 2), (float) top() + 4, getZLevel())
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
                buffer.vertex(matrix4f, (float) (left() + width() / 2 + 2), (float) top() + 4, getZLevel())
                        .color(Color.LIGHT_BLUE.r, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.b, Color.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
            }
            tessellator.end();

            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
//            RenderSystem.setShader(() -> oldShader);

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
    default void postRender(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            RenderSystem.disableScissor();
//            NuminaRenderState.glowOff();
        }
    }
}
