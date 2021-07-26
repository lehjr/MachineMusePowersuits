package com.github.lehjr.numina.dev.crafting.client.gui;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.clickable.ClickableArrow;
import com.github.lehjr.numina.util.client.gui.clickable.IClickable;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import com.github.lehjr.numina.util.client.gui.frame.MultiRectHolderFrame;
import com.github.lehjr.numina.util.client.gui.frame.RectHolderFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CraftingFrame2 extends MultiRectHolderFrame {
    protected InventoryFrame craftingGrid, resultFrame;
    protected final Colour gridColour;
    protected final Colour gridBorderColour;
    protected final Colour gridBackGound;
    protected ClickableArrow arrow;

    public CraftingFrame2(Container container, int resultIndex, int craftStartIndex, IContainerULOffSet.ulGetter ulGetter) {
        this(container, resultIndex, craftStartIndex,
                new Colour(0.1F, 0.3F, 0.4F, 0.7F),
                Colour.LIGHT_BLUE.withAlpha(0.8F),
                new Colour(0.545F, 0.545F, 0.545F, 1), ulGetter);
    }

    public CraftingFrame2(Container container, int resultIndex, int craftStartIndex, Colour gridColourIn, Colour gridBorderColourIn, Colour gridBackGoundIn, IContainerULOffSet.ulGetter ulGetter) {
        super(true, true, 0, 0);
        super.setHeight(54);
        this.gridColour = gridColourIn;
        this.gridBorderColour = gridBorderColourIn;
        this.gridBackGound = gridBackGoundIn;

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
        addRect(craftingGrid);

        arrow = new ClickableArrow(0, 0, 0, 0, false, gridBackGound, Colour.WHITE, Colour.BLACK);
        arrow.show();
        arrow.setDrawBorer(false);
        arrow.setWidth(24).setHeight(24);




        RectHolderFrame spacer1 = new RectHolderFrame(arrow, 36, 54) {
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return arrow.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                super.render(matrixStack, mouseX, mouseY, frameTime);
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

        spacer1.setMeRightOf(craftingGrid);
        addRect(spacer1);

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
//        resultFrame.setWidth(24).setHeight(24);

        RectHolderFrame spacer2 = new RectHolderFrame(resultFrame,24, 54) {
            @Override
            public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                super.render(matrixStack, mouseX, mouseY, frameTime);
                resultFrame.render(matrixStack, mouseX, mouseY, frameTime);
                System.out.println("result frame: " + resultFrame.toString());
/*
// moved right

[13:00:37] [Render thread/INFO] [STDOUT/]: [com.github.lehjr.numina.dev.crafting.client.gui.CraftingFrame2$4:render:109]: result frame: class com.github.lehjr.numina.util.client.gui.frame.InventoryFrame:
Center: x: 234.0, y: 27.0
Left: 222.0
Right: 246.0
Bottom: 39.0
Top: 15.0
Width: 24.0
Height: 24.0
Background Colour: Colour{r=0.545, g=0.545, b=0.545, a=1.0}
Background Colour 2: null
Border Colour: Colour{r=0.5, g=0.5, b=1.0, a=0.8}


// left at 00
[13:04:42] [Render thread/INFO] [STDOUT/]: [com.github.lehjr.numina.dev.crafting.client.gui.CraftingFrame2$4:render:109]: result frame: class com.github.lehjr.numina.util.client.gui.frame.InventoryFrame:
Center: x: 102.0, y: 27.0
Left: 90.0
Right: 114.0
Bottom: 39.0
Top: 15.0
Width: 24.0
Height: 24.0
Background Colour: Colour{r=0.545, g=0.545, b=0.545, a=1.0}
Background Colour 2: null
Border Colour: Colour{r=0.5, g=0.5, b=1.0, a=0.8}



 */


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
//        resultFrame.setBackgroundColour(Colour.LIGHT_BLUE);
//        resultFrame.setSecondBackgroundColour(Colour.ORANGE);
        spacer2.setMeRightOf(spacer1);
        addRect(spacer2);
        doneAdding();
    }

    public void setArrowOnPressed(IClickable.IPressable onPressed){
        arrow.setOnPressed(onPressed);
    }
}
