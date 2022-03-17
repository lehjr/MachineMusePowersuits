package com.github.lehjr.mpsrecipecreator.client.gui;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.frame.IGuiFrame;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import com.github.lehjr.numina.util.client.gui.frame.PlayerInventoryFrame;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.RelativeRect;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.inventory.container.Container;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author lehjr
 */
public class ExtInventoryFrame extends ScrollableFrame {
    PlayerInventoryFrame playerInventoryFrame;


    InventoryFrame mainInventory, hotbar;
    SpecialCraftingGrid craftingGrid;
    Container container;
    final int spacer = 4;
    final int slotHeight = 18;
    List<IGuiFrame> frames = new ArrayList<>();
    IContainerULOffSet.ulGetter ulGetter;

    public ExtInventoryFrame(
            MusePoint2D topleft,
            MusePoint2D bottomright,
            float zLevel,
            Container container,

            Colour backgroundColour,
            Colour topBorderColour,
            Colour bottomBorderColour,
            MPARCGui mparcGui,
            IContainerULOffSet.ulGetter ulGetter
    ) {
        super(topleft, bottomright, backgroundColour, topBorderColour, bottomBorderColour);


        this.ulGetter = ulGetter;
        this.container = container;

//        playerInventoryFrame = new PlayerInventoryFrame();
        //     public PlayerInventoryFrame(Container container, final int mainInventoryStart, final int hotbarStart, ulGetter ulgetter) {
        //



        // slots 0 - 9
        craftingGrid = new SpecialCraftingGrid(
                container,
                new MusePoint2D(0, 0),
                zLevel,

                backgroundColour,
                topBorderColour,

                mparcGui,
                ulGetter
        );
        frames.add(craftingGrid);

        // slot 10-36
        mainInventory = new InventoryFrame(this.container,
                9, 3, new ArrayList<Integer>(){{
            IntStream.range(10, 37).forEach(i-> add(i));
        }},
                ulGetter);
        frames.add(mainInventory);

        // slot 0-9
        hotbar = new InventoryFrame(this.container,
                9, 1, new ArrayList<Integer>(){{
            IntStream.range(37, 46).forEach(i-> add(i));
        }},
                ulGetter);
        frames.add(hotbar);
    }

    @Override
    public RelativeRect init(double left, double top, double right, double bottom) {
        super.init(left, top, right, bottom);

        hotbar.init(
                left + spacer,
                bottom - spacer - slotHeight,
                right - spacer,
                bottom - spacer);

        mainInventory.init(
                left + spacer,
                hotbar.finalTop() - spacer - 3 * slotHeight,
                right - spacer,
                hotbar.finalTop() - spacer);
        craftingGrid.init(
                left + spacer,
                mainInventory.finalTop() - spacer * 2 - 96,

                0, // ignored
                0); // ignored
        return this;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (this.isVisible()) {
            for (IGuiFrame frame : frames) {
                frame.render(matrixStack, mouseX, mouseY, partialTicks);
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
