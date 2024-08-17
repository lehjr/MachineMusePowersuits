package lehjr.mpsrecipecreator.client.gui;

import com.lehjr.numina.client.gui.IContainerULOffSet;
import com.lehjr.numina.client.gui.frame.IGuiFrame;
import com.lehjr.numina.client.gui.frame.ScrollableFrame;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.math.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lehjr
 */
public class ExtInventoryFrame extends ScrollableFrame {
    AbstractContainerMenu container;
    final int spacer = 4;
    final int slotHeight = 18;
    List<IGuiFrame> frames = new ArrayList<>();
    IContainerULOffSet.ulGetter ulGetter;

    public ExtInventoryFrame(
            MusePoint2D topleft,
            MusePoint2D bottomright,
            float zLevel,
            AbstractContainerMenu container,

            Color backgroundColor,
            Color topBorderColor,
            Color bottomBorderColor,
            MPSRCGui mpsrcGui,
            IContainerULOffSet.ulGetter ulGetter
    ) {
        super(new Rect(topleft, bottomright));//, backgroundColor, topBorderColor, bottomBorderColor);
        this.ulGetter = ulGetter;
        this.container = container;
    }

//    @Override
//    public Rect init(double left, double top, double right, double bottom) {
//        super.init(left, top, right, bottom);
//
//        hotbar.init(
//                left + spacer,
//                bottom - spacer - slotHeight,
//                right - spacer,
//                bottom - spacer);
//
//        mainInventory.init(
//                left + spacer,
//                hotbar.finalTop() - spacer - 3 * slotHeight,
//                right - spacer,
//                hotbar.finalTop() - spacer);
//        craftingGrid.init(
//                left + spacer,
//                mainInventory.finalTop() - spacer * 2 - 96,
//
//                0, // ignored
//                0); // ignored
//        return this;
//    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        super.render(gfx, mouseX, mouseY, partialTicks);
        if (this.isVisible()) {
            for (IGuiFrame frame : frames) {
                frame.render(gfx, mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isVisible()) {
            super.mouseClicked(mouseX, mouseY, button);
            for (IGuiFrame frame : frames) {
                if (frame.mouseClicked(mouseX, mouseY, button)) {
//                    return false;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isVisible() && this.isEnabled()) {
            super.mouseReleased(mouseX, mouseY, button);
            for (IGuiFrame frame : frames) {
                if (frame.mouseReleased(mouseX, mouseY, button)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(double x, double y) {
        if (this.isVisible() && this.isEnabled()) {
            super.update(x, y);
            for (IGuiFrame frame : frames) {
                frame.update(x, y);
            }
        }
    }
}
