package com.github.lehjr.numina.util.client.gui.scrollable;

import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableMuseRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.IRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.mpa.MPALibRenderState;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.java.games.input.Mouse;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ScrollableFrame implements IGuiFrame {
    protected final int buttonsize = 5;
    protected int totalsize;
    protected int currentscrollpixels;
    protected boolean visible = true;
    protected boolean enabled = true;
    protected float zLevel;

    protected DrawableMuseRect border;

    public ScrollableFrame(MusePoint2D topleft, MusePoint2D bottomright, float zlevel, Colour backgroundColour, Colour borderColour) {
        border = new DrawableMuseRect(topleft, bottomright, backgroundColour, borderColour);
        this.zLevel = zlevel;
    }

    public float getzLevel() {
        return zLevel;
    }

    public void setzLevel(float zLevelIn) {
        this.zLevel = zLevelIn;
    }

    @Override
    public IRect getBorder() {
        return border;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible ;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            preRender(matrixStack, mouseX, mouseY, partialTicks);
            postRender(mouseX, mouseY, partialTicks);
        }
    }

    public void preRender(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)  {
        if (isVisible()) {
            border.draw(matrixStack, zLevel);

            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.disableAlphaTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.shadeModel(GL11.GL_SMOOTH);
            MPALibRenderState.glowOn();

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR_LIGHTMAP);

            Matrix4f matrix4f = matrixStack.getLast().getMatrix();

            // Can scroll down
            if (currentscrollpixels + border.height() < totalsize) {
                buffer.pos(matrix4f, (float)(border.left() + border.width() / 2F), (float)border.bottom(), zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .lightmap(0x00F000F0)
                        .endVertex();

                buffer.pos(matrix4f, (float) (border.left() + border.width() / 2 + 2), (float)border.bottom() - 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .lightmap(0x00F000F0)
                        .endVertex();

                buffer.pos(matrix4f, (float) (border.left() + border.width() / 2 - 2), (float)border.bottom() - 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .lightmap(0x00F000F0)
                        .endVertex();
            }

            // Can scroll up
            if (currentscrollpixels > 0) {
                buffer.pos(matrix4f, (float) (border.left() + border.width() / 2), (float)border.top(), zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .lightmap(0x00F000F0)
                        .endVertex();
                buffer.pos(matrix4f, (float) (border.left() + border.width() / 2 - 2), (float)border.top() + 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .lightmap(0x00F000F0)
                        .endVertex();
                buffer.pos(matrix4f, (float) (border.left() + border.width() / 2 + 2), (float)border.top() + 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .lightmap(0x00F000F0)
                        .endVertex();
            }
            tessellator.draw();

            RenderSystem.shadeModel(GL11.GL_FLAT);
            RenderSystem.disableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            MPALibRenderState.scissorsOn(border.left(), border.top() + 4, border.width(), border.height() - 8); // get rid of margins
        }
    }

    public void postRender(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            MPALibRenderState.scissorsOff();
            MPALibRenderState.glowOff();
        }
    }

    public int getMaxScrollPixels() {
        return (int) Math.max(totalsize - border.height(), 0);
    }

    protected double getScrollAmount() {
        return 8;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (border.containsPoint(mouseX, mouseY)) {
            // prevent negative total scroll values
            currentscrollpixels  = (int) MuseMathUtils.clampDouble(currentscrollpixels-= dWheel * getScrollAmount(), 0, getMaxScrollPixels());
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (isVisible() && getBorder().containsPoint(x, y) && button == 0) {
            int dscroll = 0;
            if (y - this.border.top() < buttonsize && this.currentscrollpixels > 0) {
                dscroll = (int)((double)dscroll - this.getScrollAmount());
            } else if (this.border.bottom() - y < buttonsize) {
                dscroll = (int)((double)dscroll + this.getScrollAmount());
            }
            if (dscroll != 0) {
                this.currentscrollpixels = (int) MuseMathUtils.clampDouble(this.currentscrollpixels + dscroll, 0.0D, this.getMaxScrollPixels());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {

    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        return null;
    }
}
