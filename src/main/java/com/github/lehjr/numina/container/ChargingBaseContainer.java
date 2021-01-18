package com.github.lehjr.numina.container;

import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.capabilities.TileEnergyStorage;
import com.github.lehjr.numina.tileentity.ChargingBaseTileEntity;
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
        tileEntity = world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        // slot 0
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 64, 24));
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
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return getTileEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                if (tileEntity instanceof ChargingBaseTileEntity) {
                    ((ChargingBaseTileEntity) tileEntity).getTileEnergyHandler().ifPresent(h -> {
                        int energyStored = h.getEnergyStored() & 0xffff0000;
                        ((TileEnergyStorage) h).setEnergy(energyStored + (value & 0xffff));
                    });
                }
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                return (getTileEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                if (tileEntity instanceof ChargingBaseTileEntity) {
                    ((ChargingBaseTileEntity) tileEntity).getTileEnergyHandler().ifPresent(h -> {
                        int energyStored = h.getEnergyStored() & 0x0000ffff;
                        ((TileEnergyStorage) h).setEnergy(energyStored | (value << 16));
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
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, NuminaObjects.CHARGING_BASE_BLOCK.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            // from battery slot to player inventory
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                // battery slot
                if (tileEntity != null &&
                    tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(h ->
                        h.isItemValid(0, stack)).orElse(false)) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                // hotbar
                } else if (index < 28) {
                    if (!this.mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                // main inventory
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
