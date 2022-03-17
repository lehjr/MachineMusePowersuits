package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class EntityRenderFrame extends DrawableTile implements IGuiFrame {
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
    int mouseX = 0;
    int mouseY = 0;
    double guiLeft = 0;
    double guiTop = 0;

    LivingEntity livingEntity;

    public EntityRenderFrame(boolean growFromMiddle) {
        super(0, 0, 0, 0, growFromMiddle);
        setBackgroundColour(Colour.BLACK);
        setBottomBorderColour(Colour.DARK_GREY);
        setTopBorderColour(Colour.DARK_GREY);
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
        this.mouseX = (int) mousex;

        if (this.mouseY != mousey) {
            this.oldMouseY = this.mouseY;
        }
        this.mouseY = (int) mousey;

        if (allowDrag) {
            double dx = mousex - anchorx;
            double dy = mousey - anchory;
            switch (dragging) {
                case 0: {
                    rotx = MuseMathUtils.clampDouble(rotx + dy, -90, 90);
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isVisible) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            float mouse_x = (float) ((guiLeft + 51) - this.oldMouseX);
            float mouse_y = (float) ((float) ((int) guiTop + 75 - 50) - this.oldMouseY);
            float i = (float) (centerx() + offsetx);
            float j = (float) (finalBottom() - 5 + offsety);
            renderEntityInInventory(i, j, zoom, mouse_x, mouse_y, this.livingEntity);
        }
    }

    // coppied from player inventory
    public void renderEntityInInventory(float posX, float posY, float scale, float mouseX, float mouseY, LivingEntity livingEntity) {
        float f = (float)Math.atan(mouseX / 40.0F);
        float f1 = (float)Math.atan(mouseY / 40.0F);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(posX, posY, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale((float)scale, (float)scale, (float)scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        matrixstack.mulPose(quaternion);
        float yBodyRot = livingEntity.yBodyRot;
        float yRot = livingEntity.yRot;
        float xRot = livingEntity.xRot;
        float yHeadRotO = livingEntity.yHeadRotO;
        float yHeadRot = livingEntity.yHeadRot;
        livingEntity.yBodyRot = 180.0F + f * 20.0F;
        livingEntity.yRot = 180.0F + f * 40.0F;
        livingEntity.xRot = -f1 * 20.0F;
        livingEntity.yHeadRot = livingEntity.yRot;
        livingEntity.yHeadRotO = livingEntity.yRot;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderermanager.overrideCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderermanager.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);
        });
        irendertypebuffer$impl.endBatch();
        entityrenderermanager.setRenderShadow(true);
        livingEntity.yBodyRot = yBodyRot;
        livingEntity.yRot = yRot;
        livingEntity.xRot = xRot;
        livingEntity.yHeadRotO = yHeadRotO;
        livingEntity.yHeadRot = yHeadRot;
        RenderSystem.popMatrix();
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
    public List<ITextComponent> getToolTip(int x, int y) {
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
