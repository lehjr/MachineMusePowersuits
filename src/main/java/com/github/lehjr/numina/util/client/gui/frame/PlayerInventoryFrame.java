package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.math.Colour;
import net.minecraft.inventory.container.Container;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PlayerInventoryFrame extends MultiRectHolderFrame {
    final double spacerSize = 7.0D;
    final double finalWidth = 9 * 18;
    MusePoint2D slot_ulShift = new MusePoint2D(0, 0);
    InventoryFrame mainInventory, hotbar;

    /*
        leftSpacer inventory rightSpacer
     */
    public PlayerInventoryFrame(Container container, int mainInventoryStart, int hotbarStart, IContainerULOffSet.ulGetter ulGetter) {
        super(true, true, 0, 0);

        /** main stack of boxes --------------------------------------------------------------------------------------- */
        List<IGuiFrame> frames1 = new ArrayList<>();

        // FIXME: add an inventory label
        GUISpacer topSpacer = new GUISpacer(finalWidth, 13);
        frames1.add(topSpacer);

        // slot 10-36
        mainInventory = new InventoryFrame(container,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                9, 3, new ArrayList<Integer>() {{
            IntStream.range(mainInventoryStart, mainInventoryStart + 27).forEach(i -> add(i));
        }}, ulGetter);
        mainInventory.setMeBelow(topSpacer);
        frames1.add(mainInventory);

        GUISpacer middleSpacer = new GUISpacer(finalWidth, 4);
        middleSpacer.setMeBelow(mainInventory);
        frames1.add(middleSpacer);

        // slot 37 -46
        hotbar = new InventoryFrame(container,
                Colour.LIGHT_GREY, Colour.DARK_GREY, Colour.DARK_GREY,
                9, 1, new ArrayList<Integer>() {{
            IntStream.range(hotbarStart, hotbarStart + 9).forEach(i -> add(i));
        }}, ulGetter);
        hotbar.setMeBelow(middleSpacer);
        frames1.add(hotbar);

        GUISpacer bottomSpacer = new GUISpacer(finalWidth, spacerSize);
        bottomSpacer.setMeBelow(hotbar);
        frames1.add(bottomSpacer);

        MultiRectHolderFrame inventoryFrame = new MultiRectHolderFrame(
                false,
                true,
                0,
                0) {
        };

        frames1.stream().forEach(rect-> inventoryFrame.addRect(rect));
        inventoryFrame.doneAdding();

        /** add spacers to the side ----------------------------------------------------------------------------------- */
        GUISpacer leftSpacer = new GUISpacer(spacerSize, inventoryFrame.finalHeight());
        addRect(leftSpacer);

        inventoryFrame.setMeRightOf(leftSpacer);
        addRect(inventoryFrame);

        GUISpacer rightSpacer = new GUISpacer(spacerSize, inventoryFrame.finalHeight());
        rightSpacer.setMeRightOf(inventoryFrame);
        addRect(rightSpacer);
        doneAdding();
    }
}