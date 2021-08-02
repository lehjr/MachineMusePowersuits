package com.github.lehjr.numina.util.client.gui.frame;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
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

        MultiRectHolderFrame inventoryFrame = new MultiRectHolderFrame(
                false,
                true,
                0,
                0) {
        };

        // FIXME: add an inventory label
        inventoryFrame.addRect(new GUISpacer(finalWidth, 13));

        // slot 10-36
        mainInventory = new InventoryFrame(container,
                9, 3, new ArrayList<Integer>() {{
            IntStream.range(mainInventoryStart, mainInventoryStart + 27).forEach(i -> add(i));
        }}, ulGetter);
        inventoryFrame.addRect(mainInventory);

        // middle spacer
        inventoryFrame.addRect(new GUISpacer(finalWidth, 4));

        // slot 37 -46
        hotbar = new InventoryFrame(container,
                9, 1, new ArrayList<Integer>() {{
            IntStream.range(hotbarStart, hotbarStart + 9).forEach(i -> add(i));
        }}, ulGetter);
        inventoryFrame.addRect(hotbar);

        // bottom spacer
        inventoryFrame.addRect(new GUISpacer(finalWidth, spacerSize));

        inventoryFrame.doneAdding();

        /** add spacers to the side ----------------------------------------------------------------------------------- */
        // left spacer
        addRect(new GUISpacer(spacerSize, inventoryFrame.finalHeight()));

        // the main set of frames
        addRect(inventoryFrame);

        // right spacer
        addRect(new GUISpacer(spacerSize, inventoryFrame.finalHeight()));
        doneAdding();
    }
}