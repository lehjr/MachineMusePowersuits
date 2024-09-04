package com.lehjr.numina.client.gui.frame;

import com.lehjr.numina.client.gui.geometry.IDrawable;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.utils.MathUtils;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;

import java.util.List;

public class EntityRenderFrame extends AbstractGuiFrame implements IGuiFrame {
    boolean isVisible = true;
    boolean isEnabled = true;
    boolean allowDrag = false;
    boolean allowZoom = false;
    /** The old x position of the mouse pointer */
    private double oldMouseX = 20D;
    /** The old y position of the mouse pointer */
    private double oldMouseY = 20D;

    double anchorx = 0;
    double anchory = 0;
    int dragging = -1;
    double rotx = 0;
    double roty = 0;
    double offsetx = 0;
    double offsety = -2.5;
    float zoom = 30;
    double mouseX = 0;
    double mouseY = 0;
    double guiLeft = 0;
    double guiTop = 0;

    LivingEntity livingEntity;

    public EntityRenderFrame(double left, double top, double right, double bottom) {
        this(left, top, right, bottom, false);
    }

    public EntityRenderFrame(double left, double top, double right, double bottom, boolean growFromMiddle) {
        super(new Rect(left, top, right, bottom, growFromMiddle));
    }

    public void setLivingEntity(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (containsPoint(mouseX, mouseY)) {
            dragging = button;
            anchorx = mouseX;
            anchory = mouseY;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (containsPoint(mouseX, mouseY)) {
            dragging = -1;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        if (containsPoint(mouseX, mouseY) && allowZoom) {
            zoom += dWheel * 2;
            return true;
        }
        return false;
    }

    @Override
    public void update(double mousex, double mousey) {
        if (this.mouseX != mousex) {
            this.oldMouseX = this.mouseX;
        }
        this.mouseX = mousex;
        if (this.mouseY != mousey) {
            this.oldMouseY = this.mouseY;
        }
        this.mouseY = (int) mousey;

        if (allowDrag) {
            double dx = mousex - anchorx;
            double dy = mousey - anchory;
            switch (dragging) {
                case 0: {
                    rotx = MathUtils.clampDouble(rotx + dy, -90, 90);
                    roty = roty - dx;
                    anchorx = mousex;
                    anchory = mousey;
                    break;
                }
                case 1: {
                    offsetx = offsetx + dx;
                    offsety = offsety + dy;
                    anchorx = mousex;
                    anchory = mousey;
                    break;
                }
                default:
                    break;
            }
        }
    }


    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (isVisible) {
            double i = (left() + offsetx);
            double j = (top() - 5 + offsety);
            renderEntityInInventory(gfx, i, j, zoom, this.livingEntity);
        }
    }

    public void renderEntityInInventory(GuiGraphics gfx, double posX, double posY, float scale, LivingEntity entity) {
        InventoryScreen.renderEntityInInventoryFollowsAngle(gfx, (int)posX, (int)posY, (int)(posX + width()), (int)(posY + height()), (int)scale, 0.0625F, (int)rotx, (int)roty, entity);
    }

    @Override
    public float getZLevel() {
        return 0;
    }

    @Override
    public IDrawable setZLevel(float zLevel) {
        return this;
    }

    @Override
    public List<Component> getToolTip(int x, int y) {
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    public void setAllowDrag(boolean allowDrag) {
        this.allowDrag = allowDrag;
    }

    public void setAllowZoom(boolean allowZoom) {
        this.allowZoom = allowZoom;
    }

    public void setOffsetx(double offsetx) {
        this.offsetx = offsetx;
    }

    public void setOffsety(double offsety) {
        this.offsety = offsety;
    }

    public void setGuiLeft(double guiLeft) {
        this.guiLeft = guiLeft;
    }

    public void setGuiTop(double guiTop) {
        this.guiTop = guiTop;
    }
}
