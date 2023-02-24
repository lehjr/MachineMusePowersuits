package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.client.gui.geometry.DrawableTile;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.common.math.Colour;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class VanillaInventoryScrollBar extends Slider {
    /**
     texture is 256x256
     image X 232-244(highlighted), 244-256 (not highlighted) 12 wide
     image Y 0-15

     vanilla scroll bar (not inventory scroll) is only 6 pixels wide



     todo: maybe split it like the vanilla slider knob

     ------------------
     Frame scroll bar...
     width should always be 6
     height should be locked to frame
     knob height should have a height range dependant on the total height of the frame's contents



     */
    private static final ResourceLocation KNOB_TEXTURE = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    public VanillaInventoryScrollBar(MusePoint2D position, double height, String id) {
        this(position, height, id, 0);
    }

    public VanillaInventoryScrollBar(MusePoint2D position, double height, String id, double currentVal) {
        this(position, height, id, currentVal, null);
    }

    public VanillaInventoryScrollBar(MusePoint2D position, double height, String id, double currentVal, @Nullable ISlider iSlider) {
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
            GuiIcon.renderTextureWithColour(KNOB_TEXTURE, matrixStack,
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
