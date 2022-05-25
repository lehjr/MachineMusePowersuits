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
    int modularItemInventorySize;
    EquipmentSlot equipmentSlot;

    public ModularItemInventoryMenu(int containerID, Inventory playerInventory, EquipmentSlot slotType) {
        super(MPSObjects.MODULAR_ITEM_INVENTORY_MENU_TYPE.get(), containerID);
        this.equipmentSlot = slotType;

        playerInventory.player.getItemBySlot(slotType).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(iModularItem -> {
                    modularItemInventorySize = iModularItem.getSlots();

                    int i= 0;

                    outerLoop:
                    for (int row = 0; row < iModularItem.getSlots(); row ++) {  // limit here is almost an arbitrary number
                        for (int col = 0; col < 9; col ++) {
                            if (i == iModularItem.getSlots()){
                                break outerLoop;
                            }
                            this.addSlot(new SlotItemHandler(iModularItem, i, 8 + col * 18, 14 + row * 18));
                            i++;
                        }
                    }
                });


        // Player Inventory (container slots 10-36)
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + (row + 1) * 9, 8 + col * 18, 133 + row * 18));
            }
        }

        // Hotbar (container slots 37-45)
        for(int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 191));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public int getModularItemInventorySize() {
        return modularItemInventorySize;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    /**
     * Only handles shift clicking
     * @param player
     * @param index
     * @return
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < modularItemInventorySize) {
                if (!this.moveItemStackTo(itemstack1, modularItemInventorySize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, modularItemInventorySize, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

}
