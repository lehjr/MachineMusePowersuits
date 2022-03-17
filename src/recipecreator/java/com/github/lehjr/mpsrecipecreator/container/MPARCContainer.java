package com.github.lehjr.mpsrecipecreator.container;

import com.github.lehjr.mpsrecipecreator.basemod.ModObjects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MPARCContainer extends Container {
    /**
     * The crafting matrix inventory (3x3).
     */
    public CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
    public CraftResultInventory craftResult = new CraftResultInventory();
    /** Position of the workbench */
    private final PlayerEntity player;
    private final IWorldPosCallable posCallable;

    public MPARCContainer(int windowID, PlayerInventory playerInventory) {
        this(windowID, playerInventory, IWorldPosCallable.NULL);
    }

    public MPARCContainer(int windowID, PlayerInventory playerInventory, IWorldPosCallable posCallable) {
        super(ModObjects.RECIPE_WORKBENCH_CONTAINER_TYPE, windowID);
        this.posCallable = posCallable;
        this.player = playerInventory.player;

        // crafting result
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        });

        // crafting grid
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.addSlot(new Slot(this.craftMatrix, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        // player inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // player hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

//        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Note only called if player is moving an itemstack through the dragging mechanics
     * @param slotIndex
     * @param mousebtn
     * @param clickTypeIn
     * @param player
     * @return
     */

    int slotChanged = -1;

    public int getSlotChanged() {
        int ret = slotChanged;
        slotChanged = -1;
        return ret;
    }





//    @Nullable
//    Slot getSlotOrNull(int index) {
//        if (index >= 0 && index <= slots.size() -1) {
//            return this.slots.get(index);
//        }
//        return null;
//    }
//
//    /**
//     * TODO: fix so that the held itemstack isn't used up
//     * @param slotIndex
//     * @param mouseButton
//     * @param clickType
//     * @param player
//     * @return
//     */
//    @Override
//    public ItemStack doClick(int slotIndex, int mouseButton, ClickType clickType, PlayerEntity player) {
//        System.out.println("slot: " + slotIndex + " clicktype: " + clickType);
//
//        ItemStack returnStack = ItemStack.EMPTY;
//        PlayerInventory playerinventory = player.inventory;
//        Slot slot = getSlotOrNull(slotIndex);
//        ItemStack carriedStack = playerinventory.getCarried();
//        ItemStack carriedStackCopy = playerinventory.getCarried().copy();
//
//        /** Quickcraft ------------------------------------------------------------------------- */
//        if (clickType == ClickType.QUICK_CRAFT) {
//            System.out.println("clickType == ClickType.QUICK_CRAFT");
//            int quickcraftStatusPrev = this.quickcraftStatus;
//            this.quickcraftStatus = getQuickcraftHeader(mouseButton);
//            if ((quickcraftStatusPrev != 1 || this.quickcraftStatus != 2) && quickcraftStatusPrev != this.quickcraftStatus) {
//                System.out.println("doing something here");
//                this.resetQuickCraft();
//            } else if (carriedStack.isEmpty()) {
//                System.out.println("playerinventory.getCarried().isEmpty()");
//                this.resetQuickCraft();
//            } else if (this.quickcraftStatus == 0) {
//                System.out.println("this.quickcraftStatus == 0");
//                this.quickcraftType = getQuickcraftType(mouseButton);
//                if (isValidQuickcraftType(this.quickcraftType, player)) {
//                    System.out.println("isValidQuickcraftType(this.quickcraftType, player))");
//                    this.quickcraftStatus = 1;
//                    this.quickcraftSlots.clear();
//                } else {
//                    System.out.println("doing something here");
//                    this.resetQuickCraft();
//                }
//            } else if (this.quickcraftStatus == 1) {
//                System.out.println("this.quickcraftStatus == 1");
//                if (slot != null && canItemQuickReplace(slot, carriedStack, true) && slot.mayPlace(carriedStack) && (this.quickcraftType == 2 || carriedStack.getCount() > this.quickcraftSlots.size()) && this.canDragTo(slot)) {
//                    System.out.println("doing something here");
//                    this.quickcraftSlots.add(slot);
//                }
//            } else if (this.quickcraftStatus == 2) {
//                System.out.println("this.quickcraftStatus == 2");
//                if (!this.quickcraftSlots.isEmpty()) {
//                    System.out.println("doing something here");
//                    int carriedStackCount = carriedStack.getCount();
//                    for(Slot currentSlot : this.quickcraftSlots) {
//                        if (currentSlot != null && canItemQuickReplace(currentSlot, carriedStack, true) && currentSlot.mayPlace(carriedStack) && (this.quickcraftType == 2 || carriedStack.getCount() >= this.quickcraftSlots.size()) && this.canDragTo(currentSlot)) {
//                            System.out.println("doing something here");
//                            ItemStack carriedStackCopyCopy = carriedStackCopy.copy();
//                            int j3 = currentSlot.hasItem() ? currentSlot.getItem().getCount() : 0;
//                            getQuickCraftSlotCount(this.quickcraftSlots, this.quickcraftType, carriedStackCopyCopy, j3);
//                            int k3 = Math.min(carriedStackCopyCopy.getMaxStackSize(), currentSlot.getMaxStackSize(carriedStackCopyCopy));
//                            if (carriedStackCopyCopy.getCount() > k3) {
//                                System.out.println("doing something here");
//                                carriedStackCopyCopy.setCount(k3);
//                            }
//                            System.out.println("doing something here");
//
//                            carriedStackCount -= carriedStackCopyCopy.getCount() - j3;
//                            currentSlot.set(carriedStackCopyCopy);
//                        }
//                    }
//
//                    carriedStackCopy.setCount(carriedStackCount);
////                    playerinventory.setCarried(carriedStackCopy); // don't decrease count
//                }
//
//                this.resetQuickCraft();
//            } else {
//                System.out.println("doing something here");
//                this.resetQuickCraft();
//            }
//
//
//
//
//
//        } else if (this.quickcraftStatus != 0) {
//            System.out.println("this.quickcraftStatus != 0");
//
//            this.resetQuickCraft();
//
//
//
//
//        /** Pickup Or QuickMove ---------------------------------------------------------------- */
//        } else if ((clickType == ClickType.PICKUP || clickType == ClickType.QUICK_MOVE) && (mouseButton == 0 || mouseButton == 1)) {
//            System.out.println("doing something here");
//            if (slotIndex == -999) {
//                if (!carriedStack.isEmpty()) {
//                    if (mouseButton == 0) {
//                        System.out.println("!playerinventory.getCarried().isEmpty()");
//                        player.drop(carriedStack, true);
//                        playerinventory.setCarried(ItemStack.EMPTY);
//                    }
//
//                    if (mouseButton == 1) {
//                        System.out.println("mouseButton == 1");
//                        player.drop(carriedStack.split(1), true);
//                    }
//                }
//            } else if (clickType == ClickType.QUICK_MOVE) {
//                System.out.println("clickType == ClickType.QUICK_MOVE");
//
//                if (slotIndex < 0) {
//                    System.out.println("slotIndex < 0");
//                    return ItemStack.EMPTY;
//                }
//
//                if (slot == null || !slot.mayPickup(player)) {
//                    System.out.println("slot == null || !slot.mayPickup(player)");
//                    return ItemStack.EMPTY;
//                }
//
//                for(ItemStack itemstack8 = this.quickMoveStack(player, slotIndex); !itemstack8.isEmpty() && ItemStack.isSame(slot.getItem(), itemstack8); itemstack8 = this.quickMoveStack(player, slotIndex)) {
//                    returnStack = itemstack8.copy();
//                }
//            } else {
//                System.out.println("doing something here");
//
//                if (slotIndex < 0) {
//                    System.out.println("slotIndex < 0");
//                    return ItemStack.EMPTY;
//                }
//
//                if (slot != null) {
//                    System.out.println("slot != null");
//                    ItemStack itemstack9 = slot.getItem();
//                    if (!itemstack9.isEmpty()) {
//                        returnStack = itemstack9.copy();
//                        System.out.println("doing something here");
//                    }
//
//                    if (itemstack9.isEmpty()) {
//                        System.out.println("doing something here");
//                        if (!carriedStack.isEmpty() && slot.mayPlace(carriedStack)) {
//                            System.out.println("doing something here");
//                            int j2 = mouseButton == 0 ? carriedStack.getCount() : 1;
//                            if (j2 > slot.getMaxStackSize(carriedStack)) {
//                                System.out.println("doing something here");
//                                j2 = slot.getMaxStackSize(carriedStack);
//                            }
//
//                            slot.set(carriedStack.split(j2));
//                        }
//                    } else if (slot.mayPickup(player)) {
//                        if (carriedStack.isEmpty()) {
//                            if (itemstack9.isEmpty()) {
//                                System.out.println("doing something here");
//                                slot.set(ItemStack.EMPTY);
//                                playerinventory.setCarried(ItemStack.EMPTY);
//                            } else {
//                                System.out.println("doing something here");
//                                int k2 = mouseButton == 0 ? itemstack9.getCount() : (itemstack9.getCount() + 1) / 2;
//                                playerinventory.setCarried(slot.remove(k2));
//                                if (itemstack9.isEmpty()) {
//                                    System.out.println("doing something here");
//                                    slot.set(ItemStack.EMPTY);
//                                }
//
//                                slot.onTake(player, playerinventory.getCarried());
//                            }
//                        } else if (slot.mayPlace(carriedStack)) {
//                            System.out.println("doing something here");
//                            if (consideredTheSameItem(itemstack9, carriedStack)) {
//                                System.out.println("doing something here");
//                                int l2 = mouseButton == 0 ? carriedStack.getCount() : 1;
//                                if (l2 > slot.getMaxStackSize(carriedStack) - itemstack9.getCount()) {
//                                    System.out.println("doing something here");
//                                    l2 = slot.getMaxStackSize(carriedStack) - itemstack9.getCount();
//                                }
//
//                                if (l2 > carriedStack.getMaxStackSize() - itemstack9.getCount()) {
//                                    System.out.println("doing something here");
//                                    l2 = carriedStack.getMaxStackSize() - itemstack9.getCount();
//                                }
//
//                                carriedStack.shrink(l2);
//                                itemstack9.grow(l2);
//                            } else if (carriedStack.getCount() <= slot.getMaxStackSize(carriedStack)) {
//                                System.out.println("doing something here");
//                                slot.set(carriedStack);
//                                playerinventory.setCarried(itemstack9);
//                            }
//                        } else if (carriedStack.getMaxStackSize() > 1 && consideredTheSameItem(itemstack9, carriedStack) && !itemstack9.isEmpty()) {
//                            int i3 = itemstack9.getCount();
//                            if (i3 + carriedStack.getCount() <= carriedStack.getMaxStackSize()) {
//                                System.out.println("doing something here");
//                                carriedStack.grow(i3);
//                                itemstack9 = slot.remove(i3);
//                                if (itemstack9.isEmpty()) {
//                                    System.out.println("doing something here");
//                                    slot.set(ItemStack.EMPTY);
//                                }
//
//                                slot.onTake(player, playerinventory.getCarried());
//                            }
//                        }
//                    }
//
//                    slot.setChanged();
//                }
//            }
//
//
//        /** Swap ------------------------------------------------------------------------------- */
//        } else if (clickType == ClickType.SWAP) {
//            System.out.println("doing something here");
//            ItemStack stackInInventory = playerinventory.getItem(mouseButton);
//            ItemStack stackInSlot = slot.getItem();
//            if (!stackInInventory.isEmpty() || !stackInSlot.isEmpty()) {
//                System.out.println("doing something here");
//                if (stackInInventory.isEmpty()) {
//                    System.out.println("doing something here");
//                    if (slot.mayPickup(player)) {
//                        System.out.println("doing something here");
//                        playerinventory.setItem(mouseButton, stackInSlot);
////                      // hacky workaround
//                        SlotSwapCraft.slotSwapCraft(slot, stackInSlot.getCount());
//                        slot.set(ItemStack.EMPTY);
//                        slot.onTake(player, stackInSlot);
//                    }
//                } else if (stackInSlot.isEmpty()) {
//                    System.out.println("doing something here");
//                    if (slot.mayPlace(stackInInventory)) {
//                        System.out.println("doing something here");
//                        int slotMaxStackSize = slot.getMaxStackSize(stackInInventory);
//                        if (stackInInventory.getCount() > slotMaxStackSize) {
//                            System.out.println("doing something here");
//                            slot.set(stackInInventory.split(slotMaxStackSize));
//                        } else {
//                            System.out.println("doing something here");
//                            slot.set(stackInInventory);
//                            playerinventory.setItem(mouseButton, ItemStack.EMPTY);
//                        }
//                    }
//                } else if (slot.mayPickup(player) && slot.mayPlace(stackInInventory)) {
//                    System.out.println("doing something here");
//                    int slotMaxStackSize = slot.getMaxStackSize(stackInInventory);
//                    if (stackInInventory.getCount() > slotMaxStackSize) {
//                        System.out.println("doing something here");
//                        slot.set(stackInInventory.split(slotMaxStackSize));
//                        slot.onTake(player, stackInSlot);
//                        if (!playerinventory.add(stackInSlot)) {
//                            System.out.println("doing something here");
//                            player.drop(stackInSlot, true);
//                        }
//                    } else {
//                        System.out.println("doing something here");
//                        slot.set(stackInInventory);
//                        playerinventory.setItem(mouseButton, stackInSlot);
//                        slot.onTake(player, stackInSlot);
//                    }
//                }
//            }
//
//        /** Clone ------------------------------------------------------------------------------ */
//        } else if (clickType == ClickType.CLONE && player.abilities.instabuild && playerinventory.getCarried().isEmpty() && slotIndex >= 0) {
//            if (slot != null && slot.hasItem()) {
//                ItemStack clonedStack = slot.getItem().copy();
//                clonedStack.setCount(clonedStack.getMaxStackSize());
//                playerinventory.setCarried(clonedStack);
//            }
//
//        /** Throw ------------------------------------------------------------------------------ */
//        } else if (clickType == ClickType.THROW && playerinventory.getCarried().isEmpty() && slotIndex >= 0) {
//            if (slot != null && slot.hasItem() && slot.mayPickup(player)) {
//                ItemStack thrownStack = slot.remove(mouseButton == 0 ? 1 : slot.getItem().getCount());
//                slot.onTake(player, thrownStack);
//                player.drop(thrownStack, true);
//            }
//
//        /** PickupAll -------------------------------------------------------------------------- */
//        } else if (clickType == ClickType.PICKUP_ALL && slotIndex >= 0) {
//            System.out.println("doing something here");
//            if (!carriedStack.isEmpty() && (slot == null || !slot.hasItem() || !slot.mayPickup(player))) {
//                System.out.println("doing something here");
//                int j1 = mouseButton == 0 ? 0 : this.slots.size() - 1;
//                int i2 = mouseButton == 0 ? 1 : -1;
//
//                for(int j = 0; j < 2; ++j) {
//                    for(int k = j1; k >= 0 && k < this.slots.size() && carriedStack.getCount() < carriedStack.getMaxStackSize(); k += i2) {
//                        Slot currrentSlot = this.slots.get(k);
//                        if (currrentSlot.hasItem() && canItemQuickReplace(currrentSlot, carriedStack, true) && currrentSlot.mayPickup(player) && this.canTakeItemForPickAll(carriedStack, currrentSlot)) {
//                            System.out.println("doing something here");
//                            ItemStack currentStack = currrentSlot.getItem();
//                            if (j != 0 || currentStack.getCount() != currentStack.getMaxStackSize()) {
//                                System.out.println("doing something here");
//                                int l = Math.min(carriedStack.getMaxStackSize() - carriedStack.getCount(), currentStack.getCount());
//                                ItemStack stackTaken = currrentSlot.remove(l);
//                                carriedStack.grow(l);
//                                if (stackTaken.isEmpty()) {
//                                    System.out.println("doing something here");
//                                    currrentSlot.set(ItemStack.EMPTY);
//                                }
//
//                                currrentSlot.onTake(player, stackTaken);
//                            }
//                        }
//                    }
//                }
//            }
//            this.broadcastChanges();
//        }
//
//        System.out.println("returing itemStack: " + returnStack);
//
//        return returnStack;
//    }

    @Override
    public ItemStack clicked(int slotIndex, int mousebtn, ClickType clickTypeIn, PlayerEntity player) {
//        System.out.println("slot: " + slotIndex + " clicktype: " + clickTypeIn + ",  mouseButton: " + mousebtn);
        ItemStack stack = ItemStack.EMPTY;

        // handle crafting grid or result
        if ((slotIndex >= 0 && slotIndex <= 9)) {
            if (mousebtn == 2) {
                getSlot(slotIndex).set(ItemStack.EMPTY);
            } else if (mousebtn == 0) {
                PlayerInventory playerInv = player.inventory;
//                getSlot(i).onSlotChanged();
                ItemStack stackSlot = getSlot(slotIndex).getItem();
                ItemStack stackHeld = playerInv.getCarried();
                if (!stackSlot.isEmpty()) {
                    stack = stackSlot.copy();
                }

                if (!stackHeld.isEmpty()) {
                    ItemStack newStack = stackHeld.copy();
                    if (!(slotIndex == 0)) {
                        newStack.setCount(1);
                    }
                    getSlot(slotIndex).set(newStack);
                } else {
                    getSlot(slotIndex).set(ItemStack.EMPTY);
                }
            } else if (mousebtn == 1) {
                PlayerInventory playerInv = player.inventory;
                getSlot(slotIndex).setChanged();
                ItemStack stackInSlot = getSlot(slotIndex).getItem();
                ItemStack stackHeld = playerInv.getCarried();

                stack = stackInSlot.copy();

                if (!stackHeld.isEmpty()) {
                    stackHeld = stackHeld.copy();
                    if (!stackInSlot.isEmpty() && consideredTheSameItem(stackInSlot, stackHeld) /* && (slotIndex == 0)*/) {
                        if (stackInSlot.getCount() < stackInSlot.getMaxStackSize()) stackInSlot.grow(1);
                    } else {
                         stackInSlot.setCount(1);
                    }
                    getSlot(slotIndex).set(stackInSlot);
                } else {
                    if (!stackInSlot.isEmpty()) {
                        stackInSlot.shrink(1);
                        if (stackInSlot.isEmpty()) {
                            getSlot(slotIndex).set(ItemStack.EMPTY);
                        }
                    }
                }
            }
        } else {
            stack = super.clicked(slotIndex, mousebtn, clickTypeIn, player);
        }
        return stack;
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return true;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        if (index < 10) {
            slots.get(index).set(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }


    @Override
    public void removed(PlayerEntity p_75134_1_) {
        super.removed(p_75134_1_);

        this.craftMatrix.clearContent();
        this.craftResult.clearContent();
    }

//    @Override
//    protected void clearContainer(PlayerEntity p_193327_1_, World p_193327_2_, IInventory p_193327_3_) {
//        super.clearContainer(p_193327_1_, p_193327_2_, p_193327_3_);
//    }
}
