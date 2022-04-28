package com.lehjr.numina.api.gui.screen;

import com.lehjr.numina.api.gui.IIConProvider;
import com.lehjr.numina.api.math.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public abstract class NuminaAbstractContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    public NuminaAbstractContainerScreen(T menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void renderSlot(PoseStack matrixStack, Slot slot) {
//        if (slot!= null && slot instanceof IHideableSlot) {
//            if (slot.isActive()) {
//                super.renderSlot(matrixStack, slot);
//            }
//        } else {
            super.renderSlot(matrixStack, slot);
//        }

        if (slot instanceof IIConProvider && slot.getItem().isEmpty() && slot.isActive() ) {
            this.setBlitOffset(100);
            this.itemRenderer.blitOffset = 100.0F;
            ((IIConProvider) slot).drawIconAt(matrixStack, slot.x, slot.y, Color.WHITE);
            this.itemRenderer.blitOffset = 0.0F;
            this.setBlitOffset(0);
        }
    }






}
