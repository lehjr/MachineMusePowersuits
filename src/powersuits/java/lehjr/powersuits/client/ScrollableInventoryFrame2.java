package lehjr.powersuits.client;

import lehjr.numina.client.gui.IContainerULOffSet;
import lehjr.numina.client.gui.frame.InventoryFrame;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrameContainered;
import lehjr.powersuits.client.gui.common.ModularItemTabToggleWidget;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.ContainerGuiOpenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ScrollableInventoryFrame2 <C extends Container> extends InventoryFrame implements IContainerULOffSet {
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
                        EquipmentSlotType type = tab.getSlotType();
                        Minecraft.getInstance().player.getItemBySlot(type).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
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
                EquipmentSlotType type = tab.getSlotType();
                MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(type));
            }
        });
        super.update(mouseX, mouseY);
    }
}