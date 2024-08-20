package lehjr.mpsrecipecreator.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.lehjr.numina.client.gui.clickable.button.VanillaButton;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.render.IconUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ClickableNavArrow extends VanillaButton {
    final ArrowDirection direction;
    ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("textures/gui/recipe_book.png");

    public ClickableNavArrow(double left, double top, boolean enabled, ArrowDirection direction) {
        super(left, top, Component.empty(), enabled);
        super.setWidth(12);
        super.setHeight(17);
        this.direction = direction;
    }

    public ClickableNavArrow(MusePoint2D ul, boolean enabled, ArrowDirection direction) {
        super(ul, Component.empty(), enabled);
        super.setWidth(12);
        super.setHeight(17);
        this.direction = direction;
    }

    @Override
    public void renderButton(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = this.direction == ArrowDirection.LEFT ? 0 : 15;
        int j = this.containsPoint(mouseX, mouseY) ? 226 : 208;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        IconUtils.INSTANCE.blit(gfx.pose(), this.left(), this.top(), i, j, this.width(), this.height());
        this.renderBg(gfx, mouseX, mouseY, partialTicks);
    }

    public enum ArrowDirection {
        LEFT,
        RIGHT;
    }
}
