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

package com.github.lehjr.powersuits.container;

import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.CreativeInstallModuleRequestPacket;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.client.gui.slot.HideableSlot;
import com.github.lehjr.numina.util.client.gui.slot.HideableSlotItemHandler;
import com.github.lehjr.numina.util.client.gui.slot.IHideableSlot;
import com.github.lehjr.powersuits.basemod.MPSObjects;
import com.github.lehjr.powersuits.network.MPSPackets;
import com.github.lehjr.powersuits.network.packets.MoveModuleFromSlotToSlotPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Looks like slots have to be populated in the container's constructor.
 * This means that only equipped ... fixme: ...what?
 *
 */
public class TinkerTableContainer
//        extends MPARecipeBookContainer<CraftingInventory> implements IModularItemToSlotMapProvider {
        extends Container implements IModularItemToSlotMapProvider {
    private final PlayerEntity player;

    // A map of the slot that holds the modular item, and the set of slots in that modular item
    private Map<Integer, List<SlotItemHandler>> modularItemToSlotMap;

    public TinkerTableContainer(int windowId, PlayerInventory playerInventory) {
        super(MPSObjects.TINKERTABLE_CONTAINER_TYPE.get(), windowId);
        this.player = playerInventory.player;

        modularItemToSlotMap = new HashMap<>();

        // add all player inventory slots
        for (int index = 0; index < playerInventory.getContainerSize(); index ++) {
            this.addSlot(new HideableSlot(playerInventory, index, 0, 0));
        }

        // add all modular item slots
        for (Slot slot :  new ArrayList<Slot>(this.slots)) {
            List<SlotItemHandler> slots = new ArrayList<>();

            slot.getItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                if (iItemHandler instanceof IModularItem) {
                    for (int modularItemInvIndex = 0; modularItemInvIndex < iItemHandler.getSlots(); modularItemInvIndex ++) {
                        HideableSlotItemHandler slot1 =
                                (HideableSlotItemHandler) addSlot(new HideableSlotItemHandler(iItemHandler, slots.indexOf(slot), modularItemInvIndex, -1000, -1000));
                        slots.add(slot1);
                    }
                }
            });

            if (!slots.isEmpty()) {
                modularItemToSlotMap.put(slot.index, slots);
            }
        }

        for (Slot slot : this.slots) {
            if(slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();
            }
        }
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack itemStack, Slot slot) {
        if (slot instanceof SlotItemHandler) {
            return ((SlotItemHandler) slot).getItemHandler().isItemValid(slot.getSlotIndex(), itemStack);
        }
        return super.canTakeItemForPickAll(itemStack, slot);
    }

    @Override
    public boolean canDragTo(Slot slotIn) {
        return false;
    }

    /**
     * Merges provided ItemStack with the first avaliable one in the container/player inventor between minIndex
     * (included) and maxIndex (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the Container
     * implementation do not check if the item is valid for the slot
     */
    @Override
    public boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean flag = false;
        int i = startIndex;
        if (reverseDirection) {
            i = endIndex - 1;
        }

        if (stack.isStackable()) {
            while(!stack.isEmpty()) {
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                Slot slot = this.slots.get(i);
                ItemStack itemstack = slot.getItem();
                if (!itemstack.isEmpty() && consideredTheSameItem(stack, itemstack)) {
                    int j = itemstack.getCount() + stack.getCount();
                    int maxSize = Math.min(slot.getMaxStackSize(stack)/*.getSlotStackLimit()*/, stack.getMaxStackSize());
                    if (j <= maxSize) {
                        stack.setCount(0);
                        itemstack.setCount(j);
                        slot.setChanged();
                        flag = true;
                    } else if (itemstack.getCount() < maxSize) {
                        stack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.setChanged();
                        flag = true;
                    }
                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (reverseDirection) {
                i = endIndex - 1;
            } else {
                i = startIndex;
            }

            while(true) {
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                Slot slot1 = this.slots.get(i);
                ItemStack itemstack1 = slot1.getItem();
                if (itemstack1.isEmpty() && slot1.mayPlace(stack)) {
                    if (stack.getCount() > slot1.getMaxStackSize(stack)/*.getSlotStackLimit()*/) {
                        slot1.set(stack.split(slot1.getMaxStackSize()));
                    } else {
                        slot1.set(stack.split(stack.getCount()));
                    }

                    slot1.setChanged();
                    flag = true;
                    break;
                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        return flag;
    }

    public void creativeInstall(int slot, @Nonnull ItemStack itemStack) {
        if(this.getSlot(slot).getMaxStackSize(itemStack) > 0) {
            setItem(slot, itemStack);
//            this.detectAndSendChanges();
            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new CreativeInstallModuleRequestPacket(this.containerId, slot, itemStack));
        }
    }


    public void move(int source, int target) {
        if (source == -1)
            return;
        if (target == -1)
            return;

        Slot sourceSlot = slots.get(source);
        Slot targetSlot = slots.get(target);

        ItemStack contents = sourceSlot.getItem();
        ItemStack stackCopy = contents.copy();

        if(sourceSlot.mayPickup(player) && canTakeItemForPickAll(contents, targetSlot)) {
            targetSlot.set(stackCopy);
            sourceSlot.set(ItemStack.EMPTY);
            MPSPackets.CHANNEL_INSTANCE.sendToServer(new MoveModuleFromSlotToSlotPacket(this.containerId, source, target));
//            detectAndSendChanges();
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }


    public Map<Integer, List<SlotItemHandler>> getModularItemToSlotMap() {
        return modularItemToSlotMap;
    }

    @Override
    public Container getContainer() {
        return this;
    }
}