package lehjr.numina.client.gui.clickable.slider;

import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.IconUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class VanillaFrameScrollBar extends AbstractSlider {
    ResourceLocation SCROLL_TEXTURE = new ResourceLocation(NuminaConstants.MOD_ID, "textures/gui/scrollbar.png");
    ScrollableFrame parent;
    public VanillaFrameScrollBar(ScrollableFrame parent, String id) {
        super(parent.right() -6, parent.top(), parent.right(), parent.bottom(), id,  false);
        this.parent = parent;
        this.setValue(0);
    }

    @Override
    public double getKnobSize() {
        if (parent.getMaxScrollPixels() > 0) {
            return (height() * height()) / this.parent.getTotalSize();
        }
        return 0;
    }

    @Override
    public void renderBg(GuiGraphics gfx, int mouseX, int mouseY, float frameTime) {
        if (parent.getMaxScrollPixels() > 0) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, SCROLL_TEXTURE);





            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            IconUtils.INSTANCE.blit(gfx.pose(),
                    this.left(),  // left
                    this.top(),   // top
                    0,                  // texture start x
                    0,                  // texture start y
                    (this.width()), // texture uWidth
                    (this.height() * 0.5)); // texture vHeight

            IconUtils.INSTANCE.blit(gfx.pose(),
                    this.left(), //  left
                    centerY(), // top
                    0, // texture startX
                    256 - (this.height() * 0.5),  // texture startY
                    this.width(), // uWidth
                    (this.height() * 0.5)); // vHeight
        }
    }

    @Override
    public void renderKnob(GuiGraphics gfx, int mouseX, int mouseY, float frameTime) {
        if (getKnobSize() > 0) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, SCROLL_TEXTURE);
            int textureLeft = (this.containsPoint(mouseX, mouseY) ? 2 : 1) * 6;
            double knobTop = (this.top() + (this.sliderValue * (this.height() - getKnobSize())));
            double halfKnobSize = getKnobSize() * 0.5;

            IconUtils.INSTANCE.blit(gfx.pose(),
                    // left pos
                    left(),
                    // top pos
                    knobTop,
                    // texture start x
                    textureLeft,
                    // texture start y
                    0,
                    // texture end x
                    width(),
                    // texture end y
                    halfKnobSize);

            IconUtils.INSTANCE.blit(gfx.pose(),
                    // left pos
                    this.left(),
                    // top pos
                    (knobTop + halfKnobSize),
                    // texture start x
                    textureLeft,
                    // texture start y
                    (256 - halfKnobSize),
                    // texture end x
                    this.width(),
                    // texture end y
                    halfKnobSize);
        }
    }
}