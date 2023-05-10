package lehjr.powersuits.client.gui;

import lehjr.numina.client.gui.IContainerULOffSet;
import lehjr.numina.client.gui.clickable.ModularItemTabToggleWidget;
import lehjr.numina.client.gui.frame.InventoryFrame;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrameContainered;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.ContainerGuiOpenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ScrollableInventoryFrame2 <C extends AbstractContainerMenu> extends InventoryFrame implements IContainerULOffSet {
    ModularItemSelectionFrameContainered modularItemSelectionFrame;
    ModularItemTabToggleWidget selected;

    public ScrollableInventoryFrame2(C containerIn,
                                     Rect rect,
                                     ModularItemSelectionFrameContainered modularItemSelectionFrame,
                                     IContainerULOffSet.ulGetter ulgetter) {
        super(rect,
                containerIn,
                9,
                new ArrayList<Integer>() {{
                    modularItemSelectionFrame.getSelectedTab().ifPresent(tab->{
                        EquipmentSlot type = tab.getSlotType();
                        Minecraft.getInstance().player.getItemBySlot(type).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModularItem.class::isInstance)
                                .map(IModularItem.class::cast)
                                .ifPresent(iItemHandler -> {
                                    IntStream.range(0, iItemHandler.getSlots()).forEach(i-> add(i));
                                });

                    });
                }},
                ulgetter);
        this.modularItemSelectionFrame = modularItemSelectionFrame;
        modularItemSelectionFrame.getSelectedTab().ifPresent(tab-> selected = tab);
    }

    @Override
    public void update(double mouseX, double mouseY) {
        modularItemSelectionFrame.getSelectedTab().ifPresent(tab->{
            if (selected != tab) {
                selected = tab;
                EquipmentSlot type = tab.getSlotType();
                MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(type));
            }
        });
        super.update(mouseX, mouseY);
    }
}