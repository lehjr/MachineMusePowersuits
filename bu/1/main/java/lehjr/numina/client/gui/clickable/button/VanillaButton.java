package lehjr.numina.client.gui.clickable.button;

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.clickable.Clickable;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;


/**
 * Vanilla styled button based on vanilla button
 */
public class VanillaButton extends Clickable {
    List<Component> toolTip = new ArrayList<>();
    Component label;

    /**
     * For a button with a set width
     * @param left
     * @param top
     * @param width
     * @param label
     * @param enabled
     */
    public VanillaButton(double left, double top, double width, Component label, boolean enabled) {
        super(new Rect(left, top, left + width, top + 20));
        this.label = label;
        this.setEnabled(enabled);
    }

    public VanillaButton(double left, double top, Component label, boolean enabled) {
        this(new MusePoint2D(left, top), label, enabled);
    }

    public VanillaButton(MusePoint2D ul, Component label, boolean enabled) {
        super(ul, MusePoint2D.ZERO, false);
        this.label = label;
        this.setWH(new MusePoint2D(StringUtils.getStringWidth(label.getString()) + 20, 20));
        this.setEnabled(enabled);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (isVisible()) {
            renderButton(gfx, mouseX, mouseY, partialTick);
            renderBg(gfx, mouseX, mouseY, partialTick);
        }
    }

    public static final int UNSET_FG_COLOR = -1;
    protected int packedFGColor = UNSET_FG_COLOR;

    public int getFGColor() {
        if (packedFGColor != UNSET_FG_COLOR) return packedFGColor;
        return this.isEnabled() ? 16777215 : 10526880; // White : Light Grey
    }

    public void setToolTip(List<Component> toolTip) {
        this.toolTip = toolTip;
    }

    public float getAlpha() {
        return alpha;
    }

    float alpha = 1;

    protected int getYImage(boolean pIsHovered) {
        int i = 1;
        if (!this.isEnabled()) {
            i = 0;
        } else if (pIsHovered) {
            i = 2;
        }

        return i;
    }

    public void renderButton(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AbstractWidget.WIDGETS_LOCATION);
        int i = this.getYImage(containsPoint(mouseX, mouseY));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        // FIXME: still needed or able to use screen version?
        IconUtils.INSTANCE.blit(gfx.pose(), this.left(), this.top(), 0, 46 + i * 20, this.width() / 2, this.height());
        IconUtils.INSTANCE.blit(gfx.pose(), this.left() + this.width() / 2, this.top(), 200 - this.width() / 2, 46 + i * 20, this.width() / 2, this.height());
        this.renderBg(gfx, mouseX, mouseY, partialTicks);
        int j = getFGColor();
        gfx.drawCenteredString(fontrenderer, this.getLabel(), (int) (this.left() + this.width() / 2), (int) (this.top() + (this.height() - 8) / 2), j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    protected void renderBg(GuiGraphics gfx, int pMouseX, int pMouseY, float frameTime) {
    }

    public void setLabel(Component label) {
        this.label = label;
    }

    public Component getLabel() {
        return label;
    }


    @Override
    public List<Component> getToolTip(int x, int y) {
        if (this.containsPoint(x, y)) {
            if (toolTip.isEmpty()) {
                return super.getToolTip(x, y);
            }
            return toolTip;
        }
        return null;
    }
}