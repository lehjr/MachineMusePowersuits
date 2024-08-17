package com.lehjr.numina.client.gui.clickable;

import com.lehjr.numina.client.gui.NuminaIcons;
import com.lehjr.numina.client.gui.geometry.IDrawable;
import com.lehjr.numina.client.gui.geometry.IDrawableRect;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.utils.StringUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class Checkbox extends Clickable {
    // From Minecraft's own Checkbox
    private static final ResourceLocation CHECKBOX_SELECTED_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft","textures/gui/sprites/widget/checkbox_selected_highlighted.png");

//    /gui/sprites/widget/checkbox.png

    private static final ResourceLocation CHECKBOX_SELECTED_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft","textures/gui/sprites/widget/checkbox_selected.png");
    private static final ResourceLocation CHECKBOX_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft","textures/gui/sprites/widget/checkbox_highlighted.png");
    private static final ResourceLocation CHECKBOX_SPRITE = ResourceLocation.fromNamespaceAndPath("minecraft","textures/gui/sprites/widget/checkbox.png");

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
            ResourceLocation texture;
            if (Checkbox.this.containsPoint(mouseX, mouseY)) {
                texture = isChecked? CHECKBOX_SELECTED_HIGHLIGHTED_SPRITE : CHECKBOX_HIGHLIGHTED_SPRITE;
            } else {
                texture = isChecked? CHECKBOX_SELECTED_SPRITE : CHECKBOX_SPRITE;
            }

//            gfx.blitSprite(texture, (int)this.left(), (int)this.top(), 17, 17);
            NuminaIcons.renderTextureWithColor(texture,
                    gfx.pose(),
                    left(), right(), top(), bottom(), getZLevel(),
                    // int uWidth,
                    17,
                    // int vHeight,
                    17,
                    0,
                    0,
//                    // image start x (xOffset)
//                    Checkbox.this.containsPoint(mouseX, mouseY) ? 20 : 0.0F,
//                    // image start y (yOffset)
//                    isChecked() ? 20 : 0.0F,
//                    // textureWidth, textureHeight
//                    64, 64,
                    17,
                    17,
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