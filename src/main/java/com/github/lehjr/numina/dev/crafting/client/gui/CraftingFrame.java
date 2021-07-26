package com.github.lehjr.numina.dev.crafting.client.gui;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableArrow;
import com.github.lehjr.numina.util.client.gui.clickable.IClickable;
import com.github.lehjr.numina.util.client.gui.frame.GUISpacer;
import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import com.github.lehjr.numina.util.client.gui.frame.RectHolderFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.IDrawable;
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
    protected final Colour gridColour;
    protected final Colour gridBorderColour;
    protected final Colour gridBackGound;
    protected ClickableArrow arrow;
    List<IGuiFrame> frames = new ArrayList<>();

    public CraftingFrame(Container container, int resultIndex, int craftStartIndex, IContainerULOffSet.ulGetter ulGetter) {
        this(container, resultIndex, craftStartIndex,
                new Colour(0.1F, 0.3F, 0.4F, 0.7F),
                Colour.LIGHT_BLUE.withAlpha(0.8F),
                new Colour(0.545F, 0.545F, 0.545F, 1), ulGetter);
    }

    public CraftingFrame(Container container, int resultIndex, int craftStartIndex, Colour gridColourIn, Colour gridBorderColourIn, Colour gridBackGoundIn, IContainerULOffSet.ulGetter ulGetter) {
        super(new MusePoint2D(0,0), new MusePoint2D(0, 0));
        super.setHeight(54);
        this.gridColour = gridColourIn;
        this.gridBorderColour = gridBorderColourIn;
        this.gridBackGound = gridBackGoundIn;


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
backgroundRect final UL: x: 229.0, y: 41.0 (right 405)

spacer(5)

recipeBookButton final WH: x: 18.0, y: 20.0
recipeBookButton final UL: x: 234.0, y: 75.0 (right 252)

spacer(6)

crafting grid final WH: x: 54.0, y: 54.0
crafting grid final UL: x: 258.0, y: 57.0 (right 312)

spacer(7)

arrow final WH: x: 24.0, y: 24.0
arrow final UL: x: 319.0, y: 72.0 (right 343)

spacer(8)

result Frame final WH: x: 24.0, y: 24.0
result Frame final UL: x: 351.0, y: 76.0 (right 375)

spacer(30)

 */
        // slot 1-9
        craftingGrid = new InventoryFrame(
                container,
                gridBackGound,
                gridBorderColour,
                gridColour,
                3,
                3,
                new ArrayList<Integer>(){{
                    IntStream.range(craftStartIndex, craftStartIndex+9).forEach(i-> add(i));
                }}, ulGetter);
        craftingGrid.setWidth(54).setHeight(54);
        frames.add(craftingGrid);
        // total width 54

        GUISpacer spacer = new GUISpacer(5, 54);
        spacer.setMeRightOf(craftingGrid);
        frames.add(spacer);

        arrow = new ClickableArrow(0, 0, 0, 0, false, gridBackGound, Colour.WHITE, Colour.BLACK);
        arrow.show();
        arrow.setWidth(24).setHeight(24);

        RectHolderFrame spacer1 = new RectHolderFrame(arrow, 24, 54) {
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return arrow.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                arrow.render(matrixStack, mouseX, mouseY, frameTime);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return arrow.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public void update(double mouseX, double mouseY) {
                MusePoint2D position = getPosition();
                if (!position.equals(arrow.getPosition())) {
                    arrow.setPosition(position);
                    arrow.initGrowth();
                }
            }

            @Override
            public List<ITextComponent> getToolTip(int x, int y) {
                return arrow.getToolTip(x, y);
            }
        };
        spacer1.setMeRightOf(spacer);
        frames.add(spacer1);

        GUISpacer spacer2 = new GUISpacer(5, 54);
        spacer2.setMeRightOf(spacer1);
        frames.add(spacer2);

        // slot 0
        resultFrame = new InventoryFrame(
                container,
                gridBackGound,
                gridBorderColour,
                gridColour,
                1,
                1,
                new ArrayList<Integer>(){{
                    IntStream.range(resultIndex, resultIndex+1).forEach(i-> add(i));
                }}, ulGetter).setSlotWidth(24).setSlotHeight(24);
        resultFrame.setWidth(24).setHeight(24);

        RectHolderFrame spacer3 = new RectHolderFrame(resultFrame,24, 54) {
            @Override
            public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                resultFrame.render(matrixStack, mouseX, mouseY, frameTime);

                System.out.println("result frame: " + resultFrame.toString());
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return resultFrame.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return resultFrame.mouseReleased(mouseX, mouseY, button);
            }

            @Override
            public void update(double mouseX, double mouseY) {
                MusePoint2D position = getPosition();
                if (!position.equals(resultFrame.getPosition())) {
                    resultFrame.setPosition(position);
                    resultFrame.initGrowth();
                }
            }

            @Override
            public List<ITextComponent> getToolTip(int x, int y) {
                return resultFrame.getToolTip(x, y);
            }
        };
        resultFrame.setBackgroundColour(Colour.LIGHT_BLUE);
        resultFrame.setSecondBackgroundColour(Colour.ORANGE);

        frames.add(spacer3);
        spacer3.setMeRightOf(spacer2);
        updateWidth();
    }

    @Override
    public RelativeRect setUL(MusePoint2D ul) {
        super.setUL(ul);
        updatePositions();
        return this;
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
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (IGuiFrame frame : frames) {
            if (frame.mouseReleased(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double dWheel) {
        return false;
    }

    void updateWidth() {
        double totalWidth = 0;
        for (IGuiFrame frame : frames) {
            totalWidth += frame.width();
        }
        super.setWidth(totalWidth);
    }

    void updatePositions() {
        frames.get(0).setLeft(left());
        frames.get(0).setTop(top());
        for (int i = 1; i < frames.size(); i++) {
            frames.get(i).setMeRightOf((RelativeRect) frames.get(i-1));
            frames.get(i).setTop(top());
        }
    }

    @Override
    public void update(double mouseX, double mouseY) {
        for (IGuiFrame frame : frames) {
            frame.update(mouseX, mouseY);
        }
        updatePositions();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (isVisible()) {
            for (IGuiFrame frame : frames) {
                frame.render(matrixStack, mouseX, mouseY, frameTime);
            }
        }
    }

    @Override
    public float getBlitOffset() {
        return blitOffset;
    }

    @Override
    public IDrawable setBlitOffset(float zLevel) {
        return this;
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

    @Override
    public RelativeRect setWidth(double value) {
        return this;
    }

    @Override
    public RelativeRect setHeight(double value) {
        return this;
    }
}
