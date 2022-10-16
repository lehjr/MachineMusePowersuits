package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.render.NuminaRenderState;
import lehjr.numina.common.math.Colour;
import lehjr.numina.common.math.MathUtils;
import lehjr.numina.client.gui.gemoetry.IDrawableRect;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

public interface IScrollable extends IGuiFrame, IDrawableRect {
    void setTotalSize(int totalSize);

    int getButtonSize();

    void setButtonSize(int buttonSize);

    int getTotalSize();

    int getCurrentScrollPixels();

    void setCurrentScrollPixels(int scrollPixels);

    default int getMaxScrollPixels() {
        return (int) Math.max(getTotalSize() - height(), 0);
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
            int dscroll = 0;
            if (y - top() < getButtonSize() && this.getCurrentScrollPixels() > 0) {
                dscroll = (int) ((double) dscroll - this.getScrollAmount());
            } else if (bottom() - y < getButtonSize()) {
                dscroll = (int) ((double) dscroll + this.getScrollAmount());
            }
            if (dscroll != 0) {
                setCurrentScrollPixels((int) MathUtils.clampDouble(this.getCurrentScrollPixels() + dscroll, 0.0D, this.getMaxScrollPixels()));
            }
            return true;
        }
        return false;
    }


    @Override
    default void preRender(MatrixStack matrixStack, int mouseX, int mouseY, float frameTIme) {
        if (isVisible()) {
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.disableAlphaTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.shadeModel(GL11.GL_SMOOTH);
            NuminaRenderState.glowOn();

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuilder();
            buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR_LIGHTMAP);

            Matrix4f matrix4f = matrixStack.last().pose();

            // Can scroll down
            if (getCurrentScrollPixels() + height() < getTotalSize()) {
                buffer.vertex(matrix4f, (float) (left() + width() / 2F), (float) bottom(), getZLevel())
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                buffer.vertex(matrix4f, (float) (left() + width() / 2 + 2), (float) bottom() - 4, getZLevel())
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                buffer.vertex(matrix4f, (float) (left() + width() / 2 - 2), (float) bottom() - 4, getZLevel())
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
            }

            // Can scroll up
            if (getCurrentScrollPixels() > 0) {
                buffer.vertex(matrix4f, (float) (left() + width() / 2), (float) top(), getZLevel())
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
                buffer.vertex(matrix4f, (float) (left() + width() / 2 - 2), (float) top() + 4, getZLevel())
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
                buffer.vertex(matrix4f, (float) (left() + width() / 2 + 2), (float) top() + 4, getZLevel())
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
            }
            tessellator.end();

            RenderSystem.shadeModel(GL11.GL_FLAT);
            RenderSystem.disableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            NuminaRenderState.scissorsOn(left(), top(), width(), height()); // get rid of margins
        }
    }

    @Override
    default void postRender(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            NuminaRenderState.scissorsOff();
            NuminaRenderState.glowOff();
        }
    }
}
