package net.machinemuse.powersuits.client.gui.cosmetic;

import net.machinemuse.numina.client.gui.IDrawable;
import net.machinemuse.numina.client.gui.frame.IGuiFrame;
import net.machinemuse.numina.client.gui.geometry.IRect;
import net.machinemuse.numina.common.item.MuseItemUtils;
import net.machinemuse.numina.common.math.Colour;
import net.machinemuse.numina.common.math.MuseMathUtils;
import net.machinemuse.numina.client.gui.geometry.DrawableMuseRect;
import net.machinemuse.numina.client.gui.geometry.MusePoint2D;
import net.machinemuse.powersuits.client.gui.common.ItemSelectionFrame;
import net.machinemuse.powersuits.client.model.item.armor.ArmorModelInstance;
import net.machinemuse.powersuits.client.model.item.armor.IArmorModel;
import net.machinemuse.numina.client.gui.clickable.ClickableItem;
import net.machinemuse.powersuits.common.item.armor.ItemPowerArmor;
import net.machinemuse.powersuits.common.item.tool.ItemPowerFist;
import net.machinemuse.powersuits.common.utils.nbt.MPSNBTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Mouse;

import java.util.List;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:25 PM, 5/2/13
 * <p>
 * Ported to Java by lehjr on 11/2/16.
 */
public class PlayerModelViewFrame<T extends IRect> implements IGuiFrame {
    ItemSelectionFrame itemSelector;
    T border;
    double anchorx = 0;
    double anchory = 0;
    int dragging = -1;
    double lastdWheel = 0;
    double rotx = 0;
    double roty = 0;
    double offsetx = 0;
    double offsety = 29.0D;
    double zoom = 30;
    int mouseX = 0;
    int mouseY = 0;
    private float oldMouseX = 20F;
    private float oldMouseY = 20F;

    public PlayerModelViewFrame(ItemSelectionFrame itemSelector, MusePoint2D topleft, MusePoint2D bottomright, Colour borderColour, Colour insideColour) {
        this.itemSelector = itemSelector;
        setRect(new DrawableMuseRect(topleft, bottomright, borderColour, insideColour));
    }

    @Override
    public IRect getRect() {
        return border;
    }

    @Override
    public void setRect(IRect rect) {
        this.border = (T)rect;
    }

    EntityEquipmentSlot getEquipmentSlot() {
        ItemStack selectedItem = getSelectedItem().getItem();
        if (selectedItem != null && selectedItem.getItem() instanceof ItemPowerArmor)
            return ((ItemPowerArmor) selectedItem.getItem()).armorType;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        ItemStack heldItem = player.getHeldItemOffhand();

        if (!heldItem.isEmpty() && MuseItemUtils.stackEqualExact(selectedItem, heldItem))
            return EntityEquipmentSlot.OFFHAND;
        return EntityEquipmentSlot.MAINHAND;
    }

    ClickableItem getSelectedItem() {
        return itemSelector.getSelectedItem();
    }

    NBTTagCompound getRenderTag() {
        return MPSNBTUtils.getMuseRenderTag(getSelectedItem().getItem(), getEquipmentSlot());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (border.containsPoint(mouseX, mouseY)) {
            dragging = button;
            anchorx = mouseX;
            anchory = mouseY;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = -1;
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        if (this.mouseX != mouseX)
            this.oldMouseX = this.mouseX;
        this.mouseX = (int) mouseX;

        if (this.mouseY != mouseY)
            this.oldMouseY = this.mouseY;
        this.mouseY = (int) mouseY;


        if (border.containsPoint(mouseX, mouseY)) {
            double dscroll = (lastdWheel - Mouse.getDWheel()) / 120;
            zoom = zoom * Math.pow(1.1, dscroll);
            lastdWheel = Mouse.getDWheel();
        }
        double dx = mouseX - anchorx;
        double dy = mouseY - anchory;
        switch (dragging) {
            case 0: {
                rotx = MuseMathUtils.clampDouble(rotx + dy, -90, 90);
                roty = roty - dx;
                anchorx = mouseX;
                anchory = mouseY;
                break;
            }
            case 1: {
                offsetx = offsetx + dx;
                offsety = offsety + dy;
                anchorx = mouseX;
                anchory = mouseY;
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void render(double mouseX1, double mouseY1, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (border instanceof IDrawable) {
            ((IDrawable) border).render(mouseX1, mouseY1, partialTicks);
        }
        if (itemSelector.getSelectedItem() == null)
            return;
        if (getSelectedItem().getItem().getItem() instanceof ItemPowerArmor) {
            ((IArmorModel) ArmorModelInstance.getInstance()).setRenderSpec(MPSNBTUtils.getMuseRenderTag(getSelectedItem().getItem(), getEquipmentSlot()));
            ((IArmorModel) ArmorModelInstance.getInstance()).setVisibleSection(this.getEquipmentSlot());
        } else if (getSelectedItem().getItem().getItem() instanceof ItemPowerFist) {
            MPSNBTUtils.getMuseRenderTag(getSelectedItem().getItem(), getEquipmentSlot());
        } else
            return;

        // set color to normal state
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        float mouseX = (float) (border.left() + 51) - this.oldMouseX;
        float mouseY = (float) ((int) border.top() + 75 - 50) - this.oldMouseY;
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(border.centerX() + offsetx, border.centerY() + offsety, 50.0F);
        GlStateManager.scale((float) (-zoom), (float) zoom, (float) zoom);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F); // turn model right side up

        float f = mc.player.renderYawOffset;
        float f1 = mc.player.rotationYaw;
        float f2 = mc.player.rotationPitch;
        float f3 = mc.player.prevRotationYawHead;
        float f4 = mc.player.rotationYawHead;
        // XRotation with mouse look
        GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);

        GlStateManager.rotate((float) rotx, 1, 0, 0);
        GlStateManager.rotate((float) roty, 0, 1, 0);

        mc.player.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
        mc.player.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
        mc.player.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
        mc.player.rotationYawHead = mc.player.rotationYaw;
        mc.player.prevRotationYawHead = mc.player.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(mc.player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        mc.player.renderYawOffset = f;
        mc.player.rotationYaw = f1;
        mc.player.rotationPitch = f2;
        mc.player.prevRotationYawHead = f3;
        mc.player.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    public List<String> getToolTip(double mouseX, double mouseY) {
        return null;
    }
}