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

package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.NuminaIcons;
import lehjr.numina.client.gui.geometry.IDrawable;
import lehjr.numina.client.gui.geometry.IDrawableRect;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class Checkbox extends Clickable {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
    private final boolean showLabel;
    protected boolean isChecked;
    protected CheckboxTile tile;
    Component label;


    @Deprecated
    public Checkbox(MusePoint2D position, String displayString, boolean isChecked) {
        this(position, Component.literal(displayString), isChecked);
    }

    @Deprecated

    public Checkbox(MusePoint2D position, Component displayString, boolean isChecked) {
        this(position, displayString, isChecked, true);
    }

    public Checkbox(MusePoint2D position, Component displayString, boolean isChecked, boolean showLabel) {
        super(MusePoint2D.ZERO, MusePoint2D.ZERO);
        setPosition(position); // FIXME: IS this center or UL?
        makeNewTile();
        this.label = displayString;
        this.isChecked = isChecked;
        this.enableAndShow();
        this.showLabel = showLabel;
        this.setHeight(20);
    }

    public Checkbox(double left, double top, int width, Component message, boolean checked) {
        super(new Rect(left, top, left + width, top + 20));
        this.isChecked = checked;
        this.label = message;
        this.showLabel = true;
    }

    public Checkbox(double posX, double posY, double width, Component message, boolean checked, boolean showLabel) {
        this(new MusePoint2D(posX, posY), message, checked, showLabel);
        this.setWidth(width);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);

        if (this.isVisible()) {
            makeNewTile();
            this.tile.render(gfx, mouseX, mouseY, partialTick);
            if (showLabel) {
                StringUtils.drawShadowedString(gfx, this.label, this.tile.centerX() + 10.0D, this.tile.centerY() - 4.0D, Color.WHITE);
            }
        }
    }

    void makeNewTile() {
        if (tile == null) {
            MusePoint2D ul = new MusePoint2D(left() + 2, centerY() - 5);
            this.tile = (new CheckboxTile(ul));//.setBackgroundColor(Color.BLACK).setTopBorderColor(Color.DARK_GREY).setBottomBorderColor(Color.DARK_GREY);
        } else {
            tile.setUL(new MusePoint2D(left() + 2, centerY() - 5));
        }
    }

    public boolean containsPoint(double x, double y) {
        return this.isVisible() && this.isEnabled() ? this.tile.containsPoint(x, y) : false;
    }

    public void setPosition(MusePoint2D position) {
        super.setPosition(position);
        if(tile == null) {
            makeNewTile();
        }
        this.tile.setPosition(position);
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public void onPressed() {
        if (this.isVisible() && this.isEnabled()) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            this.isChecked = !this.isChecked;
        }
        super.onPressed();
    }

    class CheckboxTile extends Rect implements IDrawableRect {
        public CheckboxTile(MusePoint2D ul) {
            super(ul, ul.plus(10, 10));
        }

        @Override
        public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
//            ShaderInstance oldShader = RenderSystem.getShader();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.enableBlend();
            NuminaIcons.renderTextureWithColor(TEXTURE, gfx.pose(),
                    left(), right(), top(), bottom(), getZLevel(),
                    // int uWidth, int vHeight,
                    20, 20,
                    // image start x (xOffset)
                    Checkbox.this.containsPoint(mouseX, mouseY) ? 20 : 0.0F,
                    // image start y (yOffset)
                    isChecked() ? 20 : 0.0F,
                    // textureWidth, textureHeight
                    64, 64,
                    Color.WHITE);
            RenderSystem.disableBlend();
//            RenderSystem.setShader(() -> oldShader);
        }

        @Override
        public float getZLevel() {
            return 0;
        }

        @Override
        public IDrawable setZLevel(float zLevel) {
            return this;
        }
    }
}