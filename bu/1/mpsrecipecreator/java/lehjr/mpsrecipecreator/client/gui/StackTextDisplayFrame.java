package lehjr.mpsrecipecreator.client.gui;


import com.lehjr.numina.client.gui.frame.ScrollableFrame;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.string.StringUtils;
import net.minecraft.client.gui.GuiGraphics;

public class StackTextDisplayFrame extends ScrollableFrame {
    String label = "";
    int slot = -1;

    public StackTextDisplayFrame() {
        super(new Rect(MusePoint2D.ZERO, MusePoint2D.ZERO));
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setLabel(String labelIn) {
        label = labelIn;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        super.render(gfx, mouseX, mouseY, partialTick);
        StringUtils.drawLeftAlignedShadowedString(gfx, slot != -1 ? "Slot " + slot + ": " +
                this.label : "No slot selected", left() + 4, centerY());
    }
}