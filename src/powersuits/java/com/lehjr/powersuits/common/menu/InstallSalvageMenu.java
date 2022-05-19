package com.lehjr.powersuits.common.menu;

import com.lehjr.powersuits.common.base.MPSObjects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class InstallSalvageMenu extends AbstractContainerMenu {
    public InstallSalvageMenu(int containerID, Inventory playerInventory, EquipmentSlot slotType) {
        super(MPSObjects.INSTALL_SALVAGE_MENU_TYPE.get(), containerID);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}
