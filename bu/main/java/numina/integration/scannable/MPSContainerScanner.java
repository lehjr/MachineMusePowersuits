package lehjr.numina.integration.scannable;

import lehjr.numina.util.item.ItemUtils;
import li.cil.scannable.common.Scannable;
import li.cil.scannable.common.inventory.ItemHandlerScanner;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Hand;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * a copy of the
 */
public class MPSContainerScanner extends Container {
    private final Player player;
    private final Hand hand;
    private final ItemStack tool;
    private final ItemStack module;

    public static MPSContainerScanner createForServer(int windowId, PlayerInventory inventory, Hand hand, ItemHandlerScanner itemHandler) {
        return new MPSContainerScanner(windowId, inventory, hand, itemHandler);
    }

    public static MPSContainerScanner createForClient(int windowId, PlayerInventory inventory, FriendlyByteBuf buffer) {
        Hand hand = buffer.readEnum(Hand.class);
        return new MPSContainerScanner(windowId, inventory, hand, new ItemHandlerScanner(inventory.player.getItemInHand(hand)));
    }

    public MPSContainerScanner(int windowId, PlayerInventory inventory, Hand hand, ItemHandlerScanner itemHandler) {
        super(Scannable.SCANNER_CONTAINER.get(), windowId);
        this.player = inventory.player;
        this.hand = hand;
        this.tool = this.player.getItemInHand(hand);
        this.module = ItemUtils.getActiveModuleOrEmpty(tool);

        IItemHandler activeModules = itemHandler.getActiveModules();

        for(int slot = 0; slot < activeModules.getSlots(); ++slot) {
            this.addSlot(new SlotItemHandler(activeModules, slot, 62 + slot * 18, 20));
        }

        IItemHandler storedModules = itemHandler.getInactiveModules();

        int slot;
        for(slot = 0; slot < storedModules.getSlots(); ++slot) {
            this.addSlot(new SlotItemHandler(storedModules, slot, 62 + slot * 18, 46));
        }

        for(slot = 0; slot < 3; ++slot) {
            for(int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(inventory, col + slot * 9 + 9, 8 + col * 18, slot * 18 + 77));
            }
        }

        for(slot = 0; slot < 9; ++slot) {
            this.addSlot(new Slot(inventory, slot, 8 + slot * 18, 135));
        }

    }

    public Hand getHand() {
        return this.hand;
    }

    public boolean stillValid(Player player) {
        return player == this.player && ItemStack.matches(this.player.getItemInHand(hand), this.tool);
    }

    public ItemStack quickMoveStack(Player player, int index) {
        Slot from = this.slots.get(index);
        if (from == null) {
            return ItemStack.EMPTY;
        } else {
            ItemStack stack = from.getItem().copy();
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                boolean intoPlayerInventory = from.container != player.inventory;
                ItemStack fromStack = from.getItem();
                byte step;
                int begin;
                if (intoPlayerInventory) {
                    step = -1;
                    begin = this.slots.size() - 1;
                } else {
                    step = 1;
                    begin = 0;
                }

                int i;
                Slot into;
                if (fromStack.getMaxStackSize() > 1) {
                    for(i = begin; i >= 0 && i < this.slots.size(); i += step) {
                        into = this.slots.get(i);
                        if (into.container != from.container) {
                            ItemStack intoStack = into.getItem();
                            if (!intoStack.isEmpty()) {
                                boolean itemsAreEqual = fromStack.sameItem(intoStack) && ItemStack.tagMatches(fromStack, intoStack);
                                if (itemsAreEqual) {
                                    int maxSizeInSlot = Math.min(fromStack.getMaxStackSize(), into.getMaxStackSize(stack));
                                    int spaceInSlot = maxSizeInSlot - intoStack.getCount();
                                    if (spaceInSlot > 0) {
                                        int itemsMoved = Math.min(spaceInSlot, fromStack.getCount());
                                        if (itemsMoved > 0) {
                                            intoStack.grow(from.remove(itemsMoved).getCount());
                                            into.setChanged();
                                            if (from.getItem().isEmpty()) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                for(i = begin; i >= 0 && i < this.slots.size() && !from.getItem().isEmpty(); i += step) {
                    into = this.slots.get(i);
                    if (into.container != from.container && !into.hasItem() && into.mayPlace(fromStack)) {
                        int maxSizeInSlot = Math.min(fromStack.getMaxStackSize(), into.getMaxStackSize(fromStack));
                        int itemsMoved = Math.min(maxSizeInSlot, fromStack.getCount());
                        into.set(from.remove(itemsMoved));
                    }
                }

                return from.getItem().getCount() < stack.getCount() ? from.getItem() : ItemStack.EMPTY;
            }
        }
    }
}
