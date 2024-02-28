/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.container;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.slot.IconSlotItemHandler;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import lehjr.numina.common.capabilities.energy.BlockEnergyStorage;
import lehjr.numina.common.math.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ChargingBaseMenu extends AbstractContainerMenu {

    private BlockEntity blockEntity;
    private Player player;
    private IItemHandler playerInventory;

    public ChargingBaseMenu(int windowID, BlockPos pos, Inventory playerInventory) { // (int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player)
        super(NuminaObjects.CHARGING_BASE_CONTAINER_TYPE.get(), windowID);
        this.player = playerInventory.player;
        this.blockEntity = player.level().getBlockEntity(pos);

        this.playerInventory = new InvWrapper(playerInventory);

        // slot 0
        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new IconSlotItemHandler(h, 0, 79, 35) {
                    @Override
                    public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {
                        IconUtils.getIcon().energystorage.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
                    }
                });
            });
        }

        int row;
        int col;
        // inventory slot 1-27
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 9; ++col) {
                Slot slot = this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // hotbar slots 28-36
        for(col = 0; col < 9; ++col) {
            Slot slot = this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
        trackPower();
    }

    // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
    private void trackPower() {
        // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getBlockEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                if (blockEntity instanceof ChargingBaseBlockEntity) {
                    ((ChargingBaseBlockEntity) blockEntity).getBlockEnergyHandler().ifPresent(h -> {
                        int energyStored = h.getEnergyStored() & 0xffff0000;
                        ((BlockEnergyStorage) h).setEnergy(energyStored + (value & 0xffff));
                    });
                }
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getBlockEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                if (blockEntity instanceof ChargingBaseBlockEntity) {
                    ((ChargingBaseBlockEntity) blockEntity).getBlockEnergyHandler().ifPresent(h -> {
                        int energyStored = h.getEnergyStored() & 0x0000ffff;
                        ((BlockEnergyStorage) h).setEnergy(energyStored | (value << 16));
                    });
                }
            }
        });
    }

    public int getEnergy() {
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getMaxEnergy() {
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    public float getEnergyForMeter() {
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(energy->(float)energy.getEnergyStored()/(float) energy.getMaxEnergyStored()).orElse(0F);
    }

    public int getBlockEnergy() {
        return blockEntity instanceof ChargingBaseBlockEntity ?
                ((ChargingBaseBlockEntity) blockEntity).getBlockEnergyHandler().map(IEnergyStorage::getEnergyStored).orElse(0) : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            // from battery slot to player inventory
            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                // battery slot
                if (blockEntity != null &&
                    blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).map(h ->
                        h.isItemValid(0, stack)).orElse(false)) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                // hotbar
                } else if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                // main inventory
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, NuminaObjects.CHARGING_BASE_BLOCK.get());
    }
}
