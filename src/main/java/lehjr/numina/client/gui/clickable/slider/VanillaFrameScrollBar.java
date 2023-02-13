package lehjr.numina.client.gui.clickable.slider;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.client.gui.frame.fixed.ScrollableFrame2;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;

public class VanillaFrameScrollBar extends AbstractSlider {
    ResourceLocation SCROLL_TEXTURE = new ResourceLocation(NuminaConstants.MOD_ID, "textures/gui/scrollbar.png");
    ScrollableFrame2 parent;
    public VanillaFrameScrollBar(ScrollableFrame2 parent, String id) {
        super(parent.right() -6, parent.top(), parent.right(), parent.bottom(), id,  false);
        this.parent = parent;
    }

    @Override
    public double getKnobSize() {
        if (parent.getMaxScrollPixels() > 0) {
            return (height() * height()) / this.parent.getTotalSize();
        }
        return 0;
    }

    @Override
    public void renderBg(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (parent.getMaxScrollPixels() > 0) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getTextureManager().bind(SCROLL_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.7F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            IconUtils.INSTANCE.blit(matrixStack,
                    this.left(),  // left
                    this.top(),   // top
                    0,                  // texture start x
                    0,                  // texture start y
                    (this.width()), // texture uWidth
                    (this.height() * 0.5)); // texture vHeight

            IconUtils.INSTANCE.blit(matrixStack,
                    this.left(), //  left
                    centerY(), // top
                    0, // texture startX
                    256 - (this.height() * 0.5),  // texture startY
                    this.width(), // uWidth
                    (this.height() * 0.5)); // vHeight
        }
    }

    @Override
    public void renderKnob(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (getKnobSize() > 0) {
            Minecraft.getInstance().getTextureManager().bind(SCROLL_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int textureLeft = (this.containsPoint(mouseX, mouseY) ? 2 : 1) * 6;
            double knobTop = (this.top() + (this.sliderValue * (this.height() - getKnobSize())));
            double halfKnobSize = getKnobSize() * 0.5;

            IconUtils.INSTANCE.blit(matrixStack,
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

            IconUtils.INSTANCE.blit(matrixStack,
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