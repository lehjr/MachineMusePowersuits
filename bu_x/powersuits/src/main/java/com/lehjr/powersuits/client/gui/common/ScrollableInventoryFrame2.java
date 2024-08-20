package com.lehjr.powersuits.client.gui.common;

import com.lehjr.numina.client.gui.IContainerULOffSet;
import com.lehjr.numina.client.gui.clickable.ModularItemTabToggleWidget;
import com.lehjr.numina.client.gui.frame.InventoryFrame;
import com.lehjr.numina.client.gui.frame.ModularItemSelectionFrameContainered;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.powersuits.common.container.InstallSalvageMenu;
import com.lehjr.powersuits.common.network.MPSPackets;
import com.lehjr.powersuits.common.network.packets.serverbound.ContainerGuiOpenPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.AbstractContainerMenu;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ScrollableInventoryFrame2 <C extends AbstractContainerMenu> extends InventoryFrame implements IContainerULOffSet {
    ModularItemSelectionFrameContainered<InstallSalvageMenu> modularItemSelectionFrame;
    ModularItemTabToggleWidget selected;

    public ScrollableInventoryFrame2(C containerIn,
                                     Rect rect,
                                     @Nonnull ModularItemSelectionFrameContainered<InstallSalvageMenu> modularItemSelectionFrame,
                                     IContainerULOffSet.ulGetter ulgetter) {
        super(rect, containerIn, 9, getSlotIndexes(modularItemSelectionFrame), ulgetter);
        this.modularItemSelectionFrame = modularItemSelectionFrame;
        modularItemSelectionFrame.getSelectedTab().ifPresent(tab-> selected = tab);
    }

    static ArrayList<Integer> getSlotIndexes(ModularItemSelectionFrameContainered<InstallSalvageMenu> modularItemSelectionFrame) {
        ArrayList<Integer> ret = new ArrayList<>();
        IModularItem modularItem = modularItemSelectionFrame.getModularItemCapability();
        if(modularItem != null) {
            IntStream.range(0, modularItem.getSlots()).forEach(ret::add);
        }
        return ret;
    }

    @Override
    public void update(double mouseX, double mouseY) {
        modularItemSelectionFrame.getSelectedTab().ifPresent(tab->{
            if (selected != tab) {
                selected = tab;
                EquipmentSlot type = tab.getSlotType();
                MPSPackets.sendToServer(new ContainerGuiOpenPacket(type));
            }
        });
        super.update(mouseX, mouseY);
    }

    @Override
    public List<Component> getToolTip(int i, int i1) {
        return super.getToolTip(i, i1);
    }
}