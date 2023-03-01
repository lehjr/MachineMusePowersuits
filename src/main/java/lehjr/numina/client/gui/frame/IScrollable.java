package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import lehjr.numina.client.gui.geometry.IDrawableRect;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.math.MathUtils;

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
                dscroll = ((double) dscroll - this.getScrollAmount());
            } else if (bottom() - y < getButtonSize()) {
                dscroll = ((double) dscroll + this.getScrollAmount());
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
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
//            RenderSystem.disableAlphaTest();
            RenderSystem.defaultBlendFunc();
//            NuminaRenderState.glowOn();

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
//            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            RenderSystem.enableScissor((int)left(), (int)top(), (int)width(), (int)height()); // get rid of margins
        }
    }

    @Override
    default void postRender(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            RenderSystem.disableScissor();
//            NuminaRenderState.glowOff();
        }
    }
}
