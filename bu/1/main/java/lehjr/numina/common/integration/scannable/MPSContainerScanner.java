//package lehjr.numina.common.integration.scannable;
//
//import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
//import lehjr.numina.common.utils.ItemUtils;
//import li.cil.scannable.common.Scannable;
//import li.cil.scannable.common.inventory.ItemHandlerScanner;
//import net.minecraft.inventory.container.AbstractContainerMenu;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.util.Hand;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.IItemHandler;
//import net.neoforged.neoforge.items.SlotItemHandler;
//
//import java.util.Optional;
//
///**
// * a copy of AbstractContainerMenuScanner from Scannable
// */
//public class MPSAbstractContainerMenuScanner extends AbstractContainerMenu {
//    private final Player player;
//    private final Hand hand;
//    private final ItemStack tool;
//    private final ItemHandlerScanner itemHandler;
//
//    public static MPSAbstractContainerMenuScanner createForServer(int windowId, Inventory inventory, Hand hand, ItemHandlerScanner itemHandler) {
//        return new MPSAbstractContainerMenuScanner(windowId, inventory, hand, itemHandler);
//    }
//
//    public static MPSAbstractContainerMenuScanner createForClient(int windowId, Inventory inventory, FriendlyByteBuf buffer) {
//        Hand hand = buffer.readEnum(InteractionHand.class);
//        return new MPSAbstractContainerMenuScanner(windowId, inventory, hand, new ItemHandlerScanner(ItemUtils.getActiveModuleOrEmpty(inventory.player.getItemInHand(hand))));
//    }
//
//    public MPSAbstractContainerMenuScanner(int windowId, Inventory inventory, Hand hand, ItemHandlerScanner itemHandler) {
//        super(Scannable.SCANNER_CONTAINER.get(), windowId);
//
//        this.itemHandler = itemHandler;
//
//        this.player = inventory.player;
//        this.hand = hand;
//        this.tool = this.player.getItemInHand(hand);
//
//        IItemHandler activeModules = itemHandler.getActiveModules();
//        for(int slot = 0; slot < activeModules.getSlots(); ++slot) {
//            this.addSlot(new SlotItemHandler(activeModules, slot, 62 + slot * 18, 20) {
//                @Override
//                public void setChanged() {
//                    super.setChanged();
//                    update();
//                }
//            });
//        }
//
//        IItemHandler storedModules = itemHandler.getInactiveModules();
//        int slot;
//        for(slot = 0; slot < storedModules.getSlots(); ++slot) {
//            this.addSlot(new SlotItemHandler(storedModules, slot, 62 + slot * 18, 46) {
//                @Override
//                public void setChanged() {
//                    super.setChanged();
//                    update();
//                }
//            });
//        }
//
//        for(slot = 0; slot < 3; ++slot) {
//            for(int col = 0; col < 9; ++col) {
//                this.addSlot(new Slot(inventory, col + slot * 9 + 9, 8 + col * 18, slot * 18 + 77));
//            }
//        }
//
//        for(slot = 0; slot < 9; ++slot) {
//            this.addSlot(new Slot(inventory, slot, 8 + slot * 18, 135));
//        }
//    }
//
//    public Hand getHand() {
//        return this.hand;
//    }
//
//    public boolean stillValid(Player player) {
//        return player == this.player && ItemStack.func_77989_b(this.tool, player.getItemInHand(hand));
//    }
//
//    public ItemStack quickMoveStack(Player player, int index) {
//        Slot from = this.slots.get(index);
//        if (from == null) {
//            return ItemStack.EMPTY;
//        } else {
//            ItemStack stack = from.getItem().copy();
//            if (stack.isEmpty()) {
//                return ItemStack.EMPTY;
//            } else {
//                boolean intoInventory = from.field_75224_c != player.getInventory();
//                ItemStack fromStack = from.getItem();
//                byte step;
//                int begin;
//                if (intoInventory) {
//                    step = -1;
//                    begin = this.slots.size() - 1;
//                } else {
//                    step = 1;
//                    begin = 0;
//                }
//
//                int i;
//                Slot into;
//                if (fromStack.getMaxStackSize() > 1) {
//                    for(i = begin; i >= 0 && i < this.slots.size(); i += step) {
//                        into = this.slots.get(i);
//                        if (into.field_75224_c != from.field_75224_c) {
//                            ItemStack intoStack = into.getItem();
//                            if (!intoStack.isEmpty()) {
//                                boolean itemsAreEqual = fromStack.sameItem(intoStack) && ItemStack.func_77970_a(fromStack, intoStack);
//                                if (itemsAreEqual) {
//                                    int maxSizeInSlot = Math.min(fromStack.getMaxStackSize(), into.func_178170_b(stack));
//                                    int spaceInSlot = maxSizeInSlot - intoStack.getCount();
//                                    if (spaceInSlot > 0) {
//                                        int itemsMoved = Math.min(spaceInSlot, fromStack.getCount());
//                                        if (itemsMoved > 0) {
//                                            intoStack.grow(from.func_75209_a(itemsMoved).getCount());
//                                            into.setChanged();
//                                            if (from.getItem().isEmpty()) {
//                                                break;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                for(i = begin; i >= 0 && i < this.slots.size() && !from.getItem().isEmpty(); i += step) {
//                    into = this.slots.get(i);
//                    if (into.field_75224_c != from.field_75224_c && !into.hasItem() && into.mayPlace(fromStack)) {
//                        int maxSizeInSlot = Math.min(fromStack.getMaxStackSize(), into.func_178170_b(fromStack));
//                        int itemsMoved = Math.min(maxSizeInSlot, fromStack.getCount());
//                        into.set(from.func_75209_a(itemsMoved));
//                    }
//                }
//
//                return from.getItem().getCount() < stack.getCount() ? from.getItem() : ItemStack.EMPTY;
//            }
//        }
//    }
//
//    /**
//     * Updates the scanner's tags
//     */
//    void update() {
//        final ItemStack tool = player.getInventory().getItem(player.getInventory().selected);
//        Optional<IModeChangingItem> modeChangingItem = tool.getCapability(ForgeCapabilities.ITEM_HANDLER).filter(IModeChangingItem.class::isInstance).map(IModeChangingItem.class::cast);
//        modeChangingItem.ifPresent(iModeChangingItem -> {
//            int activeModule = iModeChangingItem.getActiveMode();
//            final ItemStack module = iModeChangingItem.getActiveModule();
//            module.func_77983_a("items", itemHandler.serializeNBT());
//            iModeChangingItem.setStackInSlot(activeModule, module);
//        });
//    }
//}
