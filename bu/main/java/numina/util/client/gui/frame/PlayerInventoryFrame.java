package lehjr.numina.util.client.gui.frame;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.util.client.gui.IContainerULOffSet;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class PlayerInventoryFrame extends MultiRectHolderFrame {
    final TranslationTextComponent title = new TranslationTextComponent("container.inventory");
    final double spacerSize = 7.0D;
    final double finalWidth = 9 * 18;
    InventoryFrame mainInventory, hotbar;
    IContainerULOffSet.ulGetter ulgetter;
    GUISpacer topSpacer;
    public boolean labelUsesULShift = true;

    /*
        leftSpacer inventory rightSpacer
     */
    public PlayerInventoryFrame(Container container, int mainInventoryStart, int hotbarStart, IContainerULOffSet.ulGetter ulgetter) {
        super(true, true, 0, 0);
        this.ulgetter = ulgetter;

        /** main stack of boxes --------------------------------------------------------------------------------------- */

        MultiRectHolderFrame inventoryFrame = new MultiRectHolderFrame(
                false,
                true,
                0,
                0);

        topSpacer = new GUISpacer(finalWidth, 13);

        // FIXME: add an inventory label
        inventoryFrame.addRect(topSpacer);

        // slot 10-36
        mainInventory = new InventoryFrame(container,
                9, 3, new ArrayList<Integer>() {{
            IntStream.range(mainInventoryStart, mainInventoryStart + 27).forEach(i -> add(i));
        }}, ulgetter);
        inventoryFrame.addRect(mainInventory);

        // middle spacer
        inventoryFrame.addRect(new GUISpacer(finalWidth, 4));

        // slot 37 -46
        hotbar = new InventoryFrame(container,
                9, 1, new ArrayList<Integer>() {{
            IntStream.range(hotbarStart, hotbarStart + 9).forEach(i -> add(i));
        }}, ulgetter);
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

    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        MusePoint2D position = new MusePoint2D(topSpacer.finalLeft() + 1, topSpacer.centery() - 3);
        if (labelUsesULShift) {
            position = position.minus(ulgetter.getULShift());
        }
        Minecraft.getInstance().font.draw(matrixStack, title, (float)position.getX(), (float)position.getY(), 4210752);
    }
}