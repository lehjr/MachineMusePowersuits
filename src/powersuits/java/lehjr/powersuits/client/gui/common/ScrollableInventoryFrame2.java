package lehjr.powersuits.client.gui.common;

import lehjr.numina.client.gui.IContainerULOffSet;
import lehjr.numina.client.gui.clickable.ModularItemTabToggleWidget;
import lehjr.numina.client.gui.frame.InventoryFrame;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrameContainered;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.powersuits.common.container.InstallSalvageMenu;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.serverbound.ContainerGuiOpenPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

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
        super(rect,
                containerIn,
                9,
                new ArrayList<>() {{
//                    modularItemSelectionFrame.getSelectedTab().ifPresent(tab -> {
//                        modularItemSelectionFrame.getModularItemCapability()
//
//
//                        EquipmentSlot type = tab.getSlotType();
//                        NuminaCapabilities.getCapability(
//                                        ItemUtils.getItemFromEntitySlot(Minecraft.getInstance().player, type),
//                                        NuminaCapabilities.Inventory.MODULAR_ITEM)
//                                .ifPresent(iItemHandler -> {
//                                    if(iItemHandler.getSlots() > 0) {
//                                        IntStream.range(0, iItemHandler.getSlots()).forEach(this::add);
//                                    }
//                                });
//
//                    });
//                }},
                    //                        EquipmentSlot type = tab.getSlotType();
                    //                        NuminaCapabilities.getCapability(
                    //                                        ItemUtils.getItemFromEntitySlot(Minecraft.getInstance().player, type),
                    //                                        NuminaCapabilities.Inventory.MODULAR_ITEM)
                    modularItemSelectionFrame.getSelectedTab().flatMap(tab ->
                            modularItemSelectionFrame.getModularItemCapability()).ifPresent(iItemHandler -> {
                        if (iItemHandler.getSlots() > 0) {
                            IntStream.range(0, iItemHandler.getSlots()).forEach(this::add);
                        }
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