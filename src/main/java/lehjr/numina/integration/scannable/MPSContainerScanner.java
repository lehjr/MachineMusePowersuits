package lehjr.numina.integration.scannable;

import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.item.ItemUtils;
import li.cil.scannable.common.Scannable;
import li.cil.scannable.common.inventory.ItemHandlerScanner;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Optional;

/**
 * a copy of ContainerScanner from Scannable
 */
public class MPSContainerScanner extends Container {
    private final PlayerEntity player;
    private final Hand hand;
    private final ItemStack tool;
    private final ItemHandlerScanner itemHandler;

    public static MPSContainerScanner createForServer(int windowId, PlayerInventory inventory, Hand hand, ItemHandlerScanner itemHandler) {
        return new MPSContainerScanner(windowId, inventory, hand, itemHandler);
    }

    public static MPSContainerScanner createForClient(int windowId, PlayerInventory inventory, PacketBuffer buffer) {
        Hand hand = buffer.readEnum(Hand.class);
        return new MPSContainerScanner(windowId, inventory, hand, new ItemHandlerScanner(ItemUtils.getActiveModuleOrEmpty(inventory.player.getItemInHand(hand))));
    }

    public MPSContainerScanner(int windowId, PlayerInventory inventory, Hand hand, ItemHandlerScanner itemHandler) {
        super(Scannable.SCANNER_CONTAINER.get(), windowId);

        this.itemHandler = itemHandler;

        this.player = inventory.player;
        this.hand = hand;
        this.tool = this.player.getItemInHand(hand);

        IItemHandler activeModules = itemHandler.getActiveModules();
        for(int slot = 0; slot < activeModules.getSlots(); ++slot) {
            this.addSlot(new SlotItemHandler(activeModules, slot, 62 + slot * 18, 20) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    update();
                }
            });
        }

        IItemHandler storedModules = itemHandler.getInactiveModules();
        int slot;
        for(slot = 0; slot < storedModules.getSlots(); ++slot) {
            this.addSlot(new SlotItemHandler(storedModules, slot, 62 + slot * 18, 46) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    update();
                }
            });
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

    public boolean stillValid(PlayerEntity player) {
        return player == this.player && ItemStack.matches(this.tool, player.getItemInHand(hand));
    }

    public ItemStack quickMoveStack(PlayerEntity player, int index) {
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

    /**
     * Updates the scanner's tags
     */
    void update() {
        final ItemStack tool = player.inventory.getItem(player.inventory.selected);
        Optional<IModeChangingItem> modeChangingItem = tool.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).filter(IModeChangingItem.class::isInstance).map(IModeChangingItem.class::cast);
        modeChangingItem.ifPresent(iModeChangingItem -> {
            int activeModule = iModeChangingItem.getActiveMode();
            final ItemStack module = iModeChangingItem.getActiveModule();
            module.addTagElement("items", itemHandler.serializeNBT());
            iModeChangingItem.setStackInSlot(activeModule, module);
        });
    }
}
