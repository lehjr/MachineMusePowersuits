package com.github.lehjr.numina.dev.crafting.client.gui;

import com.github.lehjr.numina.util.client.gui.clickable.ClickableArrow;
import com.github.lehjr.numina.util.client.gui.clickable.IClickable;
import com.github.lehjr.numina.util.client.gui.frame.GUISpacer;
import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CraftingFrame extends RelativeRect implements IGuiFrame {
    float blitOffset = 0;
    protected InventoryFrame craftingGrid, resultFrame;
    protected final Colour gridColour = new Colour(0.1F, 0.3F, 0.4F, 0.7F);
    protected final Colour gridBorderColour = Colour.LIGHT_BLUE.withAlpha(0.8F);
    protected final Colour gridBackGound = new Colour(0.545F, 0.545F, 0.545F, 1);
    protected ClickableArrow arrow;

    List<IGuiFrame> frames = new ArrayList<>();

    public CraftingFrame(Container container, int resultIndex, int craftStartIndex) {
        super(new MusePoint2D(0,0), new MusePoint2D(0, 54));

        GUISpacer spacer;

/*
|<-(54)grid->|<-(?) spacer ->|<-(24) arrow ->|<- spacer ->|<-(24) result ->|<- spacer ->|

// extended frame (with button)
|<-(?) spacer ->|<-(18) button ->|<-(?) spacer ->|||<-(54)grid->|<-(?) spacer ->|<-(24) arrow ->|<- spacer ->|<-(24) result ->|<- spacer ->|
    // width = 3x18 + arrow width + result + spacers
    // 54 +
    //
5x + 229


spacers 5? wide


backgroundRect final WH: x: 176.0, y: 166.0
backgroundRect final UL: x: 229.0, y: 41.0

crafting grid final WH: x: 54.0, y: 54.0
crafting grid final UL: x: 258.0, y: 57.0

arrow final WH: x: 24.0, y: 24.0
arrow final UL: x: 319.0, y: 72.0

result Frame final WH: x: 0.0, y: 0.0
result Frame final UL: x: 351.0, y: 76.0

recipeBookButton final WH: x: 18.0, y: 20.0
recipeBookButton final UL: x: 234.0, y: 75.0
 */

        // slot 1-9
        craftingGrid = new InventoryFrame(
                container,
                new MusePoint2D(0,0),
                new MusePoint2D(0, 0),
                0,
                gridBackGound,
                gridBorderColour,
                gridColour,
                3,
                3,
                new ArrayList<Integer>(){{
                    IntStream.range(craftStartIndex, craftStartIndex+9).forEach(i-> add(i));
                }});
        craftingGrid.setWidth(54).setHeight(54);
        frames.add(craftingGrid);

        spacer = new GUISpacer(5, 54);
        spacer.setMeRightOf(craftingGrid);
        frames.add(spacer);

        arrow = new ClickableArrow(0, 0, 0, 0, true, gridBackGound, Colour.WHITE, Colour.BLACK);
        arrow.show();
        arrow.setWidth(24).setHeight(24);
        arrow.setMeRightOf(spacer);

        spacer = new GUISpacer(5, 54);
        spacer.setMeRightOf(arrow);

        // slot 0
        resultFrame = new InventoryFrame(
                container,
                new MusePoint2D(0,0),
                new MusePoint2D(0, 0),
                0,
                gridBackGound,
                gridBorderColour,
                gridColour,
                1,
                1,
                new ArrayList<Integer>(){{
                    IntStream.range(resultIndex, resultIndex+1).forEach(i-> add(i));
                }}).setSlotWidth(24).setSlotHeight(24);
        resultFrame.setWidth(24).setHeight(24);
        resultFrame.setMeRightOf(spacer);
        frames.add(resultFrame);
    }

    public void setUL(MusePoint2D ulPosition) {
        super.setLeft(ulPosition.getX());
        super.setTop(ulPosition.getY());
    }

    public CraftingFrame setArrowOnPressed(IClickable.IPressable onArrowClicked) {
        this.arrow.setOnPressed(onArrowClicked);
        return this;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (IGuiFrame frame : frames) {
            if (frame.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
         }
        return arrow.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (IGuiFrame frame : frames) {
            if (frame.mouseReleased(mouseX, mouseY, button)) {
                return true;
            }
        }
        return arrow.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        for (IGuiFrame frame : frames) {
            frame.update(mouseX, mouseY);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            for (IGuiFrame frame : frames) {
                frame.render(matrixStack, mouseX, mouseY, partialTicks);
            }
            arrow.render(matrixStack, mouseX, mouseY, partialTicks, blitOffset);
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int x, int y) {
        if (isVisible()) {
            for (IGuiFrame frame : frames) {
                List<ITextComponent> toolTip = frame.getToolTip(x, y);
                if (toolTip != null) {
                    return toolTip;
                }
            }
            return arrow.getToolTip();
        }
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
