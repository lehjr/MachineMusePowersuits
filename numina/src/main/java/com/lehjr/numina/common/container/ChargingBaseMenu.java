package com.lehjr.numina.common.container;

import com.lehjr.numina.client.gui.slot.IconSlotItemHandler;
import com.lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaBlocks;
import com.lehjr.numina.common.registration.NuminaMenus;
import com.lehjr.numina.common.utils.IconUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import static com.lehjr.numina.common.blockentity.ChargingBaseBlockEntity.SLOT;
import static com.lehjr.numina.common.blockentity.ChargingBaseBlockEntity.SLOT_COUNT;

public class ChargingBaseMenu extends AbstractContainerMenu {

    private final BlockPos pos;
    private int power;
    ChargingBaseBlockEntity blockEntity;

    public ChargingBaseMenu(int windowId, Player player, BlockPos pos) {
        super(NuminaMenus.CHARGING_BASE_CONTAINER_TYPE.get(), windowId);
        this.pos = pos;
        if (player.level().getBlockEntity(pos) instanceof ChargingBaseBlockEntity chargingBase) {
            this.blockEntity = chargingBase;

            // slot 0
            addSlot(new IconSlotItemHandler(chargingBase.getItems(), SLOT, 79, 35) {
                @Override
                public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color) {
                    IconUtils.getIcon().energystorage.renderIconScaledWithColor(matrixStack, posX, posY, 16, 16, Color.WHITE);
                }
            });

            // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
            // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
            // to split our integer here (split our 32 bit integer into two 16 bit integers)
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return chargingBase.getStoredPower() & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    ChargingBaseMenu.this.power = (ChargingBaseMenu.this.power & 0xffff0000) | (pValue & 0xffff);
                }
            });
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return (chargingBase.getStoredPower() >> 16) & 0xffff;
                }

                @Override
                public void set(int pValue) {
                    ChargingBaseMenu.this.power = (ChargingBaseMenu.this.power & 0xffff) | ((pValue & 0xffff) << 16);
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

    public int getEnergy() {
        return power;
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

    public int getBlockEntityEnergy() {
        IEnergyStorage energyStorage =  blockEntity.getLevel().getCapability(Capabilities.EnergyStorage.BLOCK, blockEntity.getBlockPos(), Direction.UP);
        return energyStorage != null ? energyStorage.getEnergyStored() : 0;
    }

    public int getBlockEntityMaxEnergy() {
        IEnergyStorage energyStorage =  blockEntity.getLevel().getCapability(Capabilities.EnergyStorage.BLOCK, blockEntity.getBlockPos(), Direction.UP);
        return energyStorage != null ? energyStorage.getMaxEnergyStored() : 0;
    }

    public float getEnergyForMeter() {
        float max = (float) getBlockEntityMaxEnergy();
        float en = (float) getBlockEntityEnergy();
        if(max > 0 && en > 0) {
            return en/max;
        }
        return 0;
    }
}
