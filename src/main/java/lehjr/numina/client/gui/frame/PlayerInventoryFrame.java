//package lehjr.numina.client.gui.frame;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import lehjr.numina.client.gui.IContainerULOffSet;
//import lehjr.numina.client.gui.gemoetry.MusePoint2D;
//import lehjr.numina.client.gui.gemoetry.Rect;
//import net.minecraft.client.Minecraft;
//import net.minecraft.inventory.container.Container;
//import net.minecraft.util.text.TranslationTextComponent;
//
//import java.util.ArrayList;
//import java.util.stream.IntStream;
//
//@Deprecated
//public class PlayerInventoryFrame extends MultiRectHolderFrame {
//    final TranslationTextComponent title = new TranslationTextComponent("container.inventory");
//    final double spacerSize = 7.0D;
//    final double finalWidth = 9 * 18;
//    InventoryFrame mainInventory, hotbar;
//    IContainerULOffSet.ulGetter ulgetter;
//    Rect topSpacer;
//    public boolean labelUsesULShift = true;
//
//    /*
//        leftSpacer inventory rightSpacer
//     */
//    public PlayerInventoryFrame(Container container, int mainInventoryStart, int hotbarStart, IContainerULOffSet.ulGetter ulgetter) {
//        super(true, true, 0, 0);
//        this.ulgetter = ulgetter;
//
//        /** main stack of boxes --------------------------------------------------------------------------------------- */
//
//        MultiRectHolderFrame inventoryFrame = new MultiRectHolderFrame(
//                false,
//                true,
//                0,
//                0);
//
//        topSpacer = new Rect(MusePoint2D.ZERO, new MusePoint2D(finalWidth, 13));
//
//        // FIXME: add an inventory label
//        inventoryFrame.addRect(topSpacer);
//
//        // slot 10-36
//        mainInventory = new InventoryFrame(container,
//                9, 3, new ArrayList<Integer>() {{
//            IntStream.range(mainInventoryStart, mainInventoryStart + 27).forEach(i -> add(i));
//        }}, ulgetter);
//        inventoryFrame.addRect(mainInventory);
//
//        // middle spacer
//        inventoryFrame.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(finalWidth, 4)));
//
//        // slot 37 -46
//        hotbar = new InventoryFrame(container,
//                9, 1, new ArrayList<Integer>() {{
//            IntStream.range(hotbarStart, hotbarStart + 9).forEach(i -> add(i));
//        }}, ulgetter);
//        inventoryFrame.addRect(hotbar);
//
//        // bottom spacer
//        inventoryFrame.addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(finalWidth, spacerSize)));
//
//        inventoryFrame.doneAdding();
//
//        /** add spacers to the side ----------------------------------------------------------------------------------- */
//        // left spacer
//        addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(spacerSize, inventoryFrame.height())));
//
//        // the main set of frames
//        addRect(inventoryFrame);
//
//        // right spacer
//        addRect(new Rect(MusePoint2D.ZERO, new MusePoint2D(spacerSize, inventoryFrame.height())));
//        doneAdding();
//    }
//
//    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
//        MusePoint2D position = new MusePoint2D(topSpacer.left() + 1, topSpacer.centerY() - 3);
//        if (labelUsesULShift) {
//            position = position.minus(ulgetter.getULShift());
//        }
//        Minecraft.getInstance().font.draw(matrixStack, title, (float)position.x(), (float)position.y(), 4210752);
//    }
//}