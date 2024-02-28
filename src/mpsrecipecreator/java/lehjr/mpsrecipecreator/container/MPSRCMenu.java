package lehjr.mpsrecipecreator.container;

import lehjr.mpsrecipecreator.basemod.ModObjects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class MPSRCMenu extends AbstractContainerMenu {
    /**
     * The crafting matrix inventory (3x3).
     */
    public CraftingContainer craftMatrix = new TransientCraftingContainer(this, 3, 3);
    public ResultContainer craftResult = new ResultContainer();
    /** Position of the workbench */
    public MPSRCMenu(int windowID, Inventory playerInventory) {
        this(windowID, playerInventory, ContainerLevelAccess.NULL);
    }

    public MPSRCMenu(int windowID, Inventory playerInventory, ContainerLevelAccess posCallable) {
        super(ModObjects.RECIPE_WORKBENCH_CONTAINER_TYPE.get(), windowID);

        // crafting result
        this.addSlot(new ResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 367, 43) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        });

        // crafting grid
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.addSlot(new Slot(this.craftMatrix, col + row * 3, 237 + col * 32, 11 + row * 32));
            }
        }

        // player inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 232 + col * 18, 102 + row * 18));
            }
        }

        // player hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 232 + col * 18, 160));
        }
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

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        // handle crafting grid or result
        if ((slotId >= 0 && slotId <= 9)) {
            if (button == 2) {
                getSlot(slotId).set(ItemStack.EMPTY);
            } else if (button == 0) {
                Inventory playerInv = player.getInventory();
                ItemStack stackHeld = playerInv.player.containerMenu.getCarried();
                if (!stackHeld.isEmpty()) {
                    ItemStack newStack = stackHeld.copy();
                    if (!(slotId == 0)) {
                        newStack.setCount(1);
                    }
                    getSlot(slotId).set(newStack);
                } else {
                    getSlot(slotId).set(ItemStack.EMPTY);
                }
            } else if (button == 1) {
                Inventory playerInv = player.getInventory();
                getSlot(slotId).setChanged();
                ItemStack stackInSlot = getSlot(slotId).getItem();
                ItemStack stackHeld = playerInv.player.containerMenu.getCarried();
                if (!stackHeld.isEmpty()) {
                    stackHeld = stackHeld.copy();
                    if (!stackInSlot.isEmpty() && consideredTheSameItem(stackInSlot, stackHeld) /* && (slotId == 0)*/) {
                        if (stackInSlot.getCount() < stackInSlot.getMaxStackSize()) stackInSlot.grow(1);
                    } else {
                        stackInSlot.setCount(1);
                    }
                    getSlot(slotId).set(stackInSlot);
                } else {
                    if (!stackInSlot.isEmpty()) {
                        stackInSlot.shrink(1);
                        if (stackInSlot.isEmpty()) {
                            getSlot(slotId).set(ItemStack.EMPTY);
                        }
                    }
                }
            }
        } else {
            super.clicked(slotId, button, clickType, player);
        }
    }

    public static boolean consideredTheSameItem(ItemStack pStack1, ItemStack pStack2) {
        return pStack1.getItem() == pStack2.getItem() && ItemStack.isSameItemSameTags(pStack1, pStack2);
    }

    @Override
    public boolean stillValid(Player playerEntity) {
        return true;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        if (index < 10) {
            slots.get(index).set(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        this.craftMatrix.clearContent();
        this.craftResult.clearContent();
    }
}
