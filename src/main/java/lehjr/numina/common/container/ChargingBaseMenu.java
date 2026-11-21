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
import lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.container.slot.CategoryIconSlotItemHandler;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.registration.NuminaBlocks;
import lehjr.numina.common.registration.NuminaMenus;
import lehjr.numina.common.utils.IconUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import static lehjr.numina.common.blockentity.ChargingBaseBlockEntity.SLOT;
import static lehjr.numina.common.blockentity.ChargingBaseBlockEntity.SLOT_COUNT;

public class ChargingBaseMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    ChargingBaseBlockEntity blockEntity;
    private int power;
    private int maxPower;


    public ChargingBaseMenu(int windowId, Player player, BlockPos pos) {
        super(NuminaMenus.CHARGING_BASE_CONTAINER_TYPE.get(), windowId);
        this.pos = pos;
        if (player.level().getBlockEntity(pos) instanceof ChargingBaseBlockEntity chargingBase) {
            this.blockEntity = chargingBase;

            // slot 0
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new CategoryIconSlotItemHandler(h, -1, SLOT, 79, 35, ModuleCategory.ENERGY_STORAGE) {
                    @Override
                    public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {
                        IconUtils.getIcon().energystorage.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
                    }
                });
            });

            // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
            // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
            // to split our integer here (split our 32 bit integer into two 16 bit integers)
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return getBlockEnergy() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    ChargingBaseMenu.this.power = (ChargingBaseMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (getBlockEnergy() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    ChargingBaseMenu.this.power = (ChargingBaseMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });

            // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
            // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
            // to split our integer here (split our 32 bit integer into two 16 bit integers)
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return getMaxEnergy() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    ChargingBaseMenu.this.maxPower = (ChargingBaseMenu.this.maxPower & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (getMaxEnergy() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    ChargingBaseMenu.this.maxPower = (ChargingBaseMenu.this.maxPower & 0xffff) | ((pValue & 0xffff) << 16);
                }
            });
        }

        int row;
        int col;
        // inventory slot 1-27
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 9; ++col) {
                Slot slot = this.addSlot(new Slot(player.getInventory(), col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // hotbar slots 28-36
        for(col = 0; col < 9; ++col) {
            Slot slot = this.addSlot(new Slot(player.getInventory(), col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index < SLOT_COUNT) {
                if (!this.moveItemStackTo(stack, SLOT_COUNT, Inventory.INVENTORY_SIZE + SLOT_COUNT, true)) {
                    return ItemStack.EMPTY;
                }
            }
            if (!this.moveItemStackTo(stack, SLOT, SLOT+1, false)) {
                if (index < 27 + SLOT_COUNT) {
                    if (!this.moveItemStackTo(stack, 27 + SLOT_COUNT, 36 + SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < Inventory.INVENTORY_SIZE + SLOT_COUNT && !this.moveItemStackTo(stack, SLOT_COUNT, 27 + SLOT_COUNT, false)) {
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

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(playerIn.level(), pos), playerIn, NuminaBlocks.CHARGING_BASE_BLOCK.get());
    }

    public float getEnergyForMeter() {
        return (float)getEnergy() / (float)getMaxEnergy();
    }

    public int getEnergy() {
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getMaxEnergy() {
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }

    public int getBlockEnergy() {
        return blockEntity.getBlockEnergyHandler().map(IEnergyStorage::getEnergyStored).orElse(0);
    }
}