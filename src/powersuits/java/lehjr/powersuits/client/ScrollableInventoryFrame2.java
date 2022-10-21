package lehjr.powersuits.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.IContainerULOffSet;
import lehjr.numina.client.gui.frame.GUISpacer;
import lehjr.numina.client.gui.frame.InventoryFrame;
import lehjr.numina.client.gui.frame.MultiRectHolderFrame;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrameContainered;
import lehjr.powersuits.client.gui.common.ModularItemTabToggleWidget;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.ContainerGuiOpenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ScrollableInventoryFrame2 <C extends Container> extends MultiRectHolderFrame implements IContainerULOffSet {
    C container;
    GUISpacer topSpacer;
    ModularItemSelectionFrameContainered modularItemSelectionFrame;
    InventoryFrame inventoryFrame;
    MultiRectHolderFrame innerFrame;
    /**
     * gets the annoying UL offset setup by the main containerscreen class
     */
    IContainerULOffSet.ulGetter ulgetter;
    ModularItemTabToggleWidget selected;
    public boolean labelUsesULShift = true;

    TranslationTextComponent title = new TranslationTextComponent(MPSConstants.MOD_ID + ".modularitem.inventory");

    public ScrollableInventoryFrame2(C containerIn, ModularItemSelectionFrameContainered modularItemSelectionFrame, IContainerULOffSet.ulGetter ulgetter) {
        super(false, true, 0, 0);
        this.container = containerIn;
        this.modularItemSelectionFrame = modularItemSelectionFrame;
        this.ulgetter = ulgetter;
        topSpacer = new GUISpacer(176, 13);
        addRect(topSpacer);

        // left to right
        innerFrame = new MultiRectHolderFrame(true, true, 0, 0);

        innerFrame.addRect(new GUISpacer(7, 108));

        ArrayList<Integer> range = new ArrayList<>();

        modularItemSelectionFrame.getSelectedTab().ifPresent(tab->{
            if (selected != tab) {
                selected = tab;
                EquipmentSlotType type = tab.getSlotType();
                getMinecraft().player.getItemBySlot(type).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .ifPresent(iItemHandler -> {
                                IntStream.range(0, iItemHandler.getSlots()).forEach(i-> range.add(i));
                        });
            }
        });

        // main frame
        inventoryFrame = new InventoryFrame(container, 9, 6, 6, range, ulgetter);
        inventoryFrame.setDrawBackground(true);
        inventoryFrame.setDrawBorder(true);

        innerFrame.addRect(inventoryFrame);
        innerFrame.addRect(new GUISpacer(7, 108));
        innerFrame.doneAdding();
        addRect(innerFrame);
        doneAdding();
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
//                System.out.println();

                selected = tab;
                EquipmentSlotType type = tab.getSlotType();
                MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(type));

//                getMinecraft().player.getItemBySlot(type).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                        .filter(IModularItem.class::isInstance)
//                        .map(IModularItem.class::cast)
//                        .ifPresent(iItemHandler -> {
//                            this.inventoryFrame.setNewValues(new ArrayList<Integer>(){{
//                                IntStream.range(0, iItemHandler.getSlots()).forEach(i-> add(i));
//                            }});
//                        });


//                Pair<Integer, Integer> range = container.getRangeForEquipmentSlot(type);
//                if (range != null) {

//                } else {
//                    this.inventoryFrame.setNewValues(new ArrayList<>());
//                }

//                System.out.println("update me");

//                getMinecraft().player.getItemBySlot(type).getCapability()


            }
        });
        super.update(mouseX, mouseY);
    }
//
//
//    public void creativeInstall(@Nonnull ItemStack module) {
//        if (!module.isEmpty()) {
//            ((Container)container).slots.stream().filter(HideableSlotItemHandler.class::isInstance).filter(slot -> slot.isActive() && slot.mayPlace(module)).findFirst().ifPresent(slot -> {
//                NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CreativeInstallModuleRequestPacket(
//                        ((Container) container).containerId,
//                        ((Container)container).slots.indexOf(slot),
//                         module
//                ));
//            });
//        }
//    }

    @Override
    public void setULGetter(ulGetter ulgetter) {
        this.ulgetter = ulgetter;
    }

    @Override
    public MusePoint2D getULShift() {
        return ulgetter.getULShift();
    }
}
