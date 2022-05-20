package com.lehjr.powersuits.common.menu;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.powersuits.common.base.MPSObjects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ModularItemInventoryMenu extends AbstractContainerMenu {
    public ModularItemInventoryMenu(int containerID, Inventory playerInventory, EquipmentSlot slotType) {
        super(MPSObjects.MODULAR_ITEM_INVENTORY_MENU_TYPE.get(), containerID);

        System.out.println("slot: " + slotType.getName());

        System.out.println("stack: " + playerInventory.player.getItemBySlot(slotType));

        System.out.println("cap is present? " +
                playerInventory.player.getItemBySlot(slotType).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent() );


        playerInventory.player.getItemBySlot(slotType).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(iModularItem -> {
                    int i= 0;

                    outerLoop:
                    for (int row = 0; row < iModularItem.getSlots(); row ++) {  // limit here is almost an arbitrary number
                        for (int col = 0; col < 9; col ++) {
                            if (i == iModularItem.getSlots()){
                                break outerLoop;
                            }
                            this.addSlot(new SlotItemHandler(iModularItem, i, 8 + col * 18, 10 + row * 18));


//                            if (i > 0) {
//                                if (col > 0) {
//                                    this.tiles.get(i).setMeRightOf(this.tiles.get(i - 1));
//                                }
//
//                                if (row > 0) {
//                                    this.tiles.get(i).setMeBelow(this.tiles.get(i - this.gridWidth));
//                                }
//                            }
                            i++;
                        }









                        /**
                         * TODO: add hideable and movable slots to allow scrolling
                         *
                         */

                        // addSlot();

                    }
                });









        // Player Inventory (container slots 10-36)
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + (row + 1) * 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Hotbar (container slots 37-45)
        for(int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
