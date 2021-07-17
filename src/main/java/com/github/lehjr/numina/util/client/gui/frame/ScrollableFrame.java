/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableRelativeRect;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.NuminaRenderState;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ScrollableFrame extends DrawableRelativeRect implements IGuiFrame {
    protected final int buttonsize = 5;
    protected int totalsize;
    protected int currentscrollpixels;
    protected boolean visible = true;
    protected boolean enabled = true;
    protected float zLevel;

    public ScrollableFrame(MusePoint2D topleft, MusePoint2D bottomright, float zlevel, Colour backgroundColour, Colour borderColour) {
        super(topleft, bottomright, backgroundColour, borderColour);
        super.setZLevel(zlevel);
    }

    public int getMaxScrollPixels() {
        return (int) Math.max(totalsize - height(), 0);
    }

    protected double getScrollAmount() {
        return 8;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (isVisible() && containsPoint(x, y) && button == 0) {
            int dscroll = 0;
            if (y - top() < buttonsize && this.currentscrollpixels > 0) {
                dscroll = (int)((double)dscroll - this.getScrollAmount());
            } else if (bottom() - y < buttonsize) {
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
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (this.containsPoint(mouseX, mouseY)) {
            // prevent negative total scroll values
            currentscrollpixels  = (int) MuseMathUtils.clampDouble(currentscrollpixels-= dWheel * getScrollAmount(), 0, getMaxScrollPixels());
            return true;
        }
        return false;
    }

    @Override
    public void init(double left, double top, double right, double bottom) {
        IGuiFrame.super.init(left, top, right, bottom);
    }

    @Override
    public void update(double mouseX, double mouseY) {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            preRender(matrixStack, mouseX, mouseY, partialTicks);
            postRender(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        return null;
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

    public void preRender(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)  {
        if (isVisible()) {
            super.draw(matrixStack, zLevel);

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
            if (currentscrollpixels + height() < totalsize) {
                buffer.vertex(matrix4f, (float)(left() + width() / 2F), (float)bottom(), zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                buffer.vertex(matrix4f, (float) (left() + width() / 2 + 2), (float)bottom() - 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();

                buffer.vertex(matrix4f, (float) (left() + width() / 2 - 2), (float)bottom() - 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
            }

            // Can scroll up
            if (currentscrollpixels > 0) {
                buffer.vertex(matrix4f, (float) (left() + width() / 2), (float)top(), zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
                buffer.vertex(matrix4f, (float) (left() + width() / 2 - 2), (float)top() + 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
                buffer.vertex(matrix4f, (float) (left() + width() / 2 + 2), (float)top() + 4, zLevel)
                        .color(Colour.LIGHT_BLUE.r, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.b, Colour.LIGHT_BLUE.a)
                        .uv2(0x00F000F0)
                        .endVertex();
            }
            tessellator.end();

            RenderSystem.shadeModel(GL11.GL_FLAT);
            RenderSystem.disableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            NuminaRenderState.scissorsOn(left(), top() + 4, width(), height() - 8); // get rid of margins
        }
    }

    public void postRender(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            NuminaRenderState.scissorsOff();
            NuminaRenderState.glowOff();
        }
    }

    public float getzLevel() {
        return zLevel;
    }

    public void setzLevel(float zLevelIn) {
        this.zLevel = zLevelIn;
    }
}
