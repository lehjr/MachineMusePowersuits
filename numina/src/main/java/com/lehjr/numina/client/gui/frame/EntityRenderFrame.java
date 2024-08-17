package com.lehjr.numina.client.gui.frame;

import com.lehjr.numina.client.gui.geometry.IDrawable;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.utils.MathUtils;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
            double i = (centerX() + offsetx);
            double j = (bottom() - 5 + offsety);
            float mouse_x = (float) (guiLeft - this.oldMouseX + 281 + offsetx); // should be close to 0 when center // 76
            float mouse_y = (float)(guiTop - this.oldMouseY + 25F + offsety);
            renderEntityInInventory(gfx, i, j, mouse_x, mouse_y, zoom, this.livingEntity);
        }
    }

    // TODO: model rotation based on a scaled value like in MPS for 1.7.10
    // coppied from player inventory
    public void renderEntityInInventory(GuiGraphics gfx, double posX, double posY, float mouseX, float mouseY, float scale, LivingEntity pLivingEntity) {
        float f = (float)Math.atan(mouseX / 40.0F);
        float f1 = (float)Math.atan(mouseY / 40.0F);

        gfx.pose().pushPose();
        gfx.pose().translate((float)posX, (float)posY, 1050.0F);
        gfx.pose().scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0F, 0.0F, 1000.0F);
        posestack1.scale(scale, scale, scale);
        Quaternionf quaternion = Axis.ZP.rotationDegrees(180.0F);
        Quaternionf quaternion1 = Axis.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        float yBodyRot = pLivingEntity.yBodyRot;
        float yRot = pLivingEntity.getYRot();
        float xRot = pLivingEntity.getXRot();
        float yHeadRotO = pLivingEntity.yHeadRotO;
        float yHeadRot = pLivingEntity.yHeadRot;
        pLivingEntity.yBodyRot = 180.0F + f * 20.0F;
        pLivingEntity.setYRot(180.0F + f * 40.0F);
        pLivingEntity.setXRot(-f1 * 20.0F);
        pLivingEntity.yHeadRot = pLivingEntity.getYRot();
        pLivingEntity.yHeadRotO = pLivingEntity.getYRot();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conjugate();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(pLivingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
        });
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        pLivingEntity.yBodyRot = yBodyRot;
        pLivingEntity.setYRot(yRot);
        pLivingEntity.setXRot(xRot);
        pLivingEntity.yHeadRotO = yHeadRotO;
        pLivingEntity.yHeadRot = yHeadRot;
        gfx.pose().popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
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
