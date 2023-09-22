package lehjr.mpsrecipecreator.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.mpsrecipecreator.basemod.MPSRCConstants;
import lehjr.numina.client.gui.IContainerULOffSet;
import lehjr.numina.client.gui.clickable.button.VanillaButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public class SlotButton extends VanillaButton {
    IContainerULOffSet.ulGetter ulGetter;
    boolean active = false;
    public Slot slot;
    static final ResourceLocation BACKGROUND = new ResourceLocation(MPSRCConstants.MOD_ID, "textures/gui/mpsrc_background.png");

    public SlotButton(Slot slot, IContainerULOffSet.ulGetter ulGetter) {
        super(MusePoint2D.ZERO, Component.empty(), true);
        super.setHeight(28);
        super.setWidth(28);
        this.ulGetter = ulGetter;
        this.slot = slot;
        setULBySlot();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            this.active = !this.active;
            return true;
        }
        return false;
    }

    void setULBySlot() {
        super.setUL(new MusePoint2D(slot.x, slot.y).minus(6, 6).plus(ulGetter.getULShift()));
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = this.active ? 48 : 76;
        int y = this.containsPoint(mouseX, mouseY) ? 331 : 303;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
//        IconUtils.INSTANCE.blit(poseStack, this.left(), this.top(), x, y, this.width(), this.height());
        IconUtils.INSTANCE.blit(poseStack, left(), top(), 0, x, y, width(), height(), 512, 512);
        this.renderBg(poseStack, mouseX, mouseY, partialTicks);
    }
}
