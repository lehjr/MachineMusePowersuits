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

public class ModularItemInventoryMenu extends AbstractContainerMenu {
    public ModularItemInventoryMenu(int containerID, Inventory playerInventory, EquipmentSlot slotType) {
        super(MPSObjects.MODULAR_ITEM_INVENTORY_MENU_TYPE.get(), containerID);

        playerInventory.player.getItemBySlot(slotType).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast)
                .ifPresent(iModularItem -> {
                    for (int i=0; i < iModularItem.getSlots(); i++) {
                        /**
                         * TODO: add hideable and movable slots to allow scrolling
                         *
                         */

                        // addSlot();

                    }
                });









        // Player Inventory (container slots 10-36)
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        // Hotbar (container slots 37-45)
        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
