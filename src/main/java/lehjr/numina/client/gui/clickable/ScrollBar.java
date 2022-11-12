package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.client.gui.gemoetry.DrawableTile;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.common.math.Colour;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class ScrollBar extends Slider {
    /** The location of the creative inventory tabs texture
     texture is 256x256
     image startX 232
     image startY 0
     image endY 15
     */
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    public ScrollBar(MusePoint2D position, double height, String id) {
        this(position, height, id, 0);
    }

    public ScrollBar(MusePoint2D position, double height, String id, double currentVal) {
        this(position, height, id, currentVal, null);
    }

    public ScrollBar(MusePoint2D position, double height, String id, double currentVal, @Nullable ISlider iSlider) {
        super(position, false, height, id, currentVal, iSlider);
        this.setWidth(12);
        keepKnobWithinBounds=true;
    }


    @Override
    public void renderKnob(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        super.renderKnob(matrixStack, mouseX, mouseY, frameTime);
    }

    @Override
    public void createNewRects() {
        setIsCreatingNewRects(true);
        setKnobRect(new KnobTile(center()));
        setIsCreatingNewRects(false);
    }

    class KnobTile extends DrawableTile {
        public KnobTile(MusePoint2D ul) {
            super(ul, ul.plus(12, 15));
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
            GuiIcon.renderTextureWithColour(TEXTURE, matrixStack,
                    left(), right(), top(), bottom(), getZLevel(),
                    // int uWidth, int vHeight,
                    12, 15,
                    // image start x (xOffset)
                    (knobRect.containsPoint(mouseX, mouseY) || dragging) ? 232 : 244.0F,
                    // image start y (yOffset)
                    0,
                    // textureWidth, textureHeight
                    256, 256,
                    Colour.WHITE);
        }
    }
}
