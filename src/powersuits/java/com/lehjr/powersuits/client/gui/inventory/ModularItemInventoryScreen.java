package com.lehjr.powersuits.client.gui.inventory;

import com.lehjr.numina.common.menu.ArmorStandMenu;
import com.lehjr.powersuits.common.menu.ModularItemInventoryMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ModularItemInventoryScreen extends AbstractContainerScreen<ModularItemInventoryMenu> {

    /*
    todo:
        2 backgrounds (split window for displaying compatible modules for modular item)
        tabs for selecting modular item in equipment slot
        recipe window triggered by clicking on compatible items.

     */


    public ModularItemInventoryScreen(ModularItemInventoryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {

    }
}
