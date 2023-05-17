package lehjr.mpsrecipecreator.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.clickable.button.VanillaButton;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ClickableArrow extends VanillaButton {
    ResourceLocation BACKGROUND = new ResourceLocation(NuminaConstants.MOD_ID, "textures/gui/crafting_arrows.png");

    public ClickableArrow(double left, double top, Component label, boolean enabled) {
        super(left, top, 22, label, enabled);
        super.setHeight(15);
    }

    public ClickableArrow(MusePoint2D ul, Component label, boolean enabled) {
        super(ul, label, enabled);
        super.setWidth(22);
        super.setHeight(15);
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = Mth.clamp(this.getYImage(containsPoint(mouseX, mouseY)), 0, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        // FIXME: still needed or able to use screen version?
        IconUtils.INSTANCE.blit(matrixStack, this.left(), this.top(), 0, i * 15, this.width(), this.height());
//        IconUtils.INSTANCE.blit(matrixStack, this.left() + this.width() / 2, this.top(), 200 - this.width() / 2, 46 + i * 20, this.width() / 2, this.height());
        this.renderBg(matrixStack, mouseX, mouseY, partialTicks);
        int j = getFGColor();
        getMinecraft().screen.drawCenteredString(matrixStack, fontrenderer, this.getLabel(), (int) (this.left() + this.width() / 2), (int) (this.top() + (this.height() - 8) / 2), j | Mth.ceil(this.getAlpha() * 255.0F) << 24);
    }

    @Override
    protected int getYImage(boolean pIsHovered) {
        return super.getYImage(pIsHovered);
    }
}
