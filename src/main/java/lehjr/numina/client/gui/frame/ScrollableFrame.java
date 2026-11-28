package lehjr.numina.client.gui.frame;

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.utils.MathUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class ScrollableFrame extends AbstractGuiFrame implements IScrollable {
    protected int buttonSize = 5;
    double scrollAmount = 8;

    protected int totalSize = 0;
    protected double currentScrollPixels = 0;
    public ScrollableFrame(@Nonnull Rect backgrountRect) {
        super(backgrountRect);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        if (isVisible()) {
            NuminaLogger.logDebug("renderingscrollable frame");

            renderBackgroundRect(gfx, mouseX, mouseY, partialTick);
            super.render(gfx, mouseX, mouseY, partialTick);
            preRender(gfx, mouseX, mouseY, partialTick);
            postRender(gfx, mouseX, mouseY, partialTick);
        } else {
            NuminaLogger.logDebug("scrollable frame not visible");
        }

    }

//    public void renderTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int uOffset, int vOffset, int textureDifference, int width, int height, int textureWidth, int textureHeight) {
//        int i = vOffset;
//        if (!this.isActive()) {
//            i = vOffset + textureDifference * 2;
//        } else if (this.isHoveredOrFocused()) {
//            i = vOffset + textureDifference;
//        }
//
//        RenderSystem.enableDepthTest();
//        guiGraphics.blit(texture, x, y, (float)uOffset, (float)i, width, height, textureWidth, textureHeight);
//    }





    @Override
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int getButtonSize() {
        return buttonSize;
    }

    @Override
    public void setButtonSize(int buttonSize) {
        this.buttonSize = buttonSize;
    }

    @Override
    public int getTotalSize() {
        return totalSize;
    }

    @Override
    public double getCurrentScrollPixels() {
        return currentScrollPixels;
    }

    @Override
    public void setCurrentScrollPixels(double scrollPixels) {
        this.currentScrollPixels = (int) MathUtils.clampDouble(scrollPixels, 0, getMaxScrollPixels());
    }

    @Override
    public double getMaxScrollPixels() {
        return (int) Math.max(totalSize - height(), 0);
    }

    @Override
    public double getScrollAmount() {
        return scrollAmount;
    }

    @Override
    public void setScrollAmount(double scrollAmount) {
        this.scrollAmount = (int) MathUtils.clampDouble(scrollAmount, 0, getMaxScrollPixels());
    }
}
