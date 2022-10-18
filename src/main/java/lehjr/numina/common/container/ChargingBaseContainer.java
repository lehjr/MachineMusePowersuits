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

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.slot.IIConProvider;
import lehjr.numina.client.render.IconUtils;
import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.blockentity.ChargingBaseTileEntity;
import lehjr.numina.common.capabilities.energy.BlockEnergyStorage;
import lehjr.numina.common.math.Colour;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ChargingBaseContainer extends Container {

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public ChargingBaseContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(NuminaObjects.CHARGING_BASE_CONTAINER_TYPE.get(), windowId);
        tileEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        // slot 0
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new IconSlot(h, 0, 64, 24) {
                    @Override
                    public void drawIconAt(MatrixStack matrixStack, double posX, double posY, Colour colour) {
                        IconUtils.getIcon().energyStorageBackground.renderIconScaledWithColour(matrixStack, posX, posY, 16, 16, Colour.WHITE);
                    }
                });
            });
        }

        int row;
        int col;
        // inventory slot 1-27
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // hotbar slots 28-36
        for(col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 180, 142));
        }
        trackPower();
    }


    // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
    private void trackPower() {
        // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        addDataSlot(new IntReferenceHolder() {
            @Override
            public int get() {
                return getTileEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                if (tileEntity instanceof ChargingBaseTileEntity) {
                    ((ChargingBaseTileEntity) tileEntity).getTileEnergyHandler().ifPresent(h -> {
                        int energyStored = h.getEnergyStored() & 0xffff0000;
                        ((BlockEnergyStorage) h).setEnergy(energyStored + (value & 0xffff));
                    });
                }
            }
        });
        addDataSlot(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getTileEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                if (tileEntity instanceof ChargingBaseTileEntity) {
                    ((ChargingBaseTileEntity) tileEntity).getTileEnergyHandler().ifPresent(h -> {
                        int energyStored = h.getEnergyStored() & 0x0000ffff;
                        ((BlockEnergyStorage) h).setEnergy(energyStored | (value << 16));
                    });
                }
            }
        });
    }

    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getMaxEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    public float getEnergyForMeter() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(energy->(float)energy.getEnergyStored()/(float) energy.getMaxEnergyStored()).orElse(0F);
    }

    public int getTileEnergy() {
        return tileEntity instanceof ChargingBaseTileEntity ?
                ((ChargingBaseTileEntity) tileEntity).getTileEnergyHandler().map(IEnergyStorage::getEnergyStored).orElse(0) : 0;
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()), playerEntity, NuminaObjects.CHARGING_BASE_BLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
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
                if (tileEntity != null &&
                    tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(h ->
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

    abstract class IconSlot extends SlotItemHandler implements IIConProvider {
        public IconSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
    }
}
