package com.github.lehjr.powersuits.dev.crafting.client.gui.recipebooktest;

import com.github.lehjr.numina.util.client.gui.IContainerULOffSet;
import com.github.lehjr.numina.util.client.gui.frame.GUISpacer;
import com.github.lehjr.numina.util.client.gui.frame.InventoryFrame;
import com.github.lehjr.numina.util.client.gui.frame.MultiRectHolderFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.dev.crafting.client.gui.common.done.MPSRecipeTabToggleWidget;
import com.github.lehjr.powersuits.dev.crafting.client.gui.common.done.ModularItemSelectionFrame;
import com.github.lehjr.powersuits.dev.crafting.container.IModularItemContainerSlotProvider;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ScrollableInventoryFrame2 <C extends IModularItemContainerSlotProvider> extends MultiRectHolderFrame implements IContainerULOffSet {
    C container;
    GUISpacer topSpacer;
    ModularItemSelectionFrame modularItemSelectionFrame;
    InventoryFrame inventoryFrame;
    MultiRectHolderFrame innerFrame;
//    RangedSlider scrollbar;
    /**
     * gets the annoying UL offset setup by the main containerscreen class
     */
    IContainerULOffSet.ulGetter ulgetter;

    MPSRecipeTabToggleWidget selected;
    public boolean labelUsesULShift = true;

    TranslationTextComponent title = new TranslationTextComponent(MPSConstants.MOD_ID + ".modularitem.inventory");


    public ScrollableInventoryFrame2(C containerIn, ModularItemSelectionFrame modularItemSelectionFrame, IContainerULOffSet.ulGetter ulgetter) {
        super(false, true, 0, 0);
        this.container = containerIn;
        this.modularItemSelectionFrame = modularItemSelectionFrame;
        this.ulgetter = ulgetter;
        topSpacer = new GUISpacer(176, 13);
        addRect(topSpacer);

        // left to right
        innerFrame = new MultiRectHolderFrame(true, true, 0, 0);

        innerFrame.addRect(new GUISpacer(7, 108));

        // main frame
        inventoryFrame = new InventoryFrame((Container) container, 9, 6, 6, new ArrayList<Integer>(), ulgetter);
        inventoryFrame.setDrawBackground(true);
        inventoryFrame.setDrawBorder(true);

        innerFrame.addRect(inventoryFrame);
        innerFrame.addRect(new GUISpacer(7, 108));



//        // scrollbar width is 8
//        scrollbar = new RangedSlider(
//                new MusePoint2D(0, 0),
//                false, // not horizontal
//                108, // 108 tall
//                "",
//                0,
//                1,
//                0);
//
//        innerFrame.addRect(scrollbar);
        innerFrame.doneAdding();

        addRect(innerFrame);
        doneAdding();




        // top spacer


        // scroll bar (slider)

        //  side spacer
        // 108 tall, 7 wide


    }

    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        MusePoint2D position = new MusePoint2D(topSpacer.finalLeft() + 1, topSpacer.centery() - 3);
        if (labelUsesULShift) {
            position = position.minus(ulgetter.getULShift());
        }
        Minecraft.getInstance().font.draw(matrixStack, title, (float)position.getX(), (float)position.getY(), 4210752);
    }

    @Override
    public void update(double mouseX, double mouseY) {
        modularItemSelectionFrame.getSelectedTab().ifPresent(tab->{
            if (selected != tab) {
                selected = (MPSRecipeTabToggleWidget) tab;
                EquipmentSlotType type =((MPSRecipeTabToggleWidget) tab).getSlotType();
                Pair<Integer, Integer> range = container.getRangeForEquipmentSlot(type);
                if (range != null) {
                    this.inventoryFrame.setNewValues(new ArrayList<Integer>(){{
                        IntStream.range(range.getLeft(), range.getRight()).forEach(i-> add(i));
                    }});
                }
            }
        });
        super.update(mouseX, mouseY);
    }

    @Override
    public void setULGetter(ulGetter ulgetter) {
        this.ulgetter = ulgetter;
    }

    @Override
    public MusePoint2D getULShift() {
        return ulgetter.getULShift();
    }
}
