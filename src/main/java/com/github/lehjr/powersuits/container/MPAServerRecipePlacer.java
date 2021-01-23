//package com.github.lehjr.powersuits.container;
//
//import com.github.lehjr.powersuits.network.MPAPackets;
//import com.google.common.collect.Lists;
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.inventory.container.PlayerContainer;
//import net.minecraft.inventory.container.RecipeBookContainer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.ServerRecipePlacer;
//import net.minecraft.network.play.server.SPlaceGhostRecipePacket;
//import net.minecraftforge.fml.network.PacketDistributor;
//
//import javax.annotation.Nullable;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Only handles placing recipes
// */
//public class MPAServerRecipePlacer extends ServerRecipePlacer {
//    public MPAServerRecipePlacer(RecipeBookContainer recipeBookContainer) {
//        super(recipeBookContainer);
//    }
//
//    public void place(ServerPlayerEntity player, @Nullable IRecipe recipeIn, boolean placeAll) {
//        if (recipeIn != null && (player.getRecipeBook().isUnlocked(recipeIn) ||
//                // we don't need no stinking locked recipes
//                player.openContainer instanceof MPAWorkbenchContainer)) {
//            this.playerInventory = player.inventory;
//            if (this.placeIntoInventory() || player.isCreative()) {
//                this.recipeItemHelper.clear();
//                player.inventory.accountStacks(this.recipeItemHelper);
//                this.recipeBookContainer.fillStackedContents(this.recipeItemHelper);
//                if (this.recipeItemHelper.canCraft(recipeIn, null)) {
//                    this.tryPlaceRecipe(recipeIn, placeAll);
//                } else {
//                    this.clear();
//                    if (this.recipeBookContainer instanceof MPAWorkbenchContainer) {
//                        MPAPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(()-> player),
//                                new com.github.lehjr.powersuits.network.packets.reworked_crafting_packets.SPlaceGhostRecipePacket(player.openContainer.windowId, recipeIn));
//                    } else {
//                        player.connection.sendPacket(new SPlaceGhostRecipePacket(player.openContainer.windowId, recipeIn));
//                    }
//                }
//                player.inventory.markDirty();
//            }
//        }
//    }
//
//    private boolean placeIntoInventory() {
//        List<ItemStack> itemStacks = Lists.newArrayList();
//        int emptyPlayerSlots = this.getEmptyPlayerSlots();
//
//        for(int index = 0; index < this.recipeBookContainer.getWidth() * this.recipeBookContainer.getHeight() + 1; ++index) {
//            if (index != this.recipeBookContainer.getOutputSlot()) {
//                ItemStack stack = this.recipeBookContainer.getSlot(index).getStack().copy();
//                if (!stack.isEmpty()) {
//                    int storeIndex = this.playerInventory.storeItemStack(stack);
//                    if (storeIndex == -1 && itemStacks.size() <= emptyPlayerSlots) {
//                        Iterator stackIterator = itemStacks.iterator();
//
//                        while(stackIterator.hasNext()) {
//                            ItemStack nextStack = (ItemStack)stackIterator.next();
//                            if (nextStack.isItemEqual(stack) && nextStack.getCount() != nextStack.getMaxStackSize() && nextStack.getCount() + stack.getCount() <= nextStack.getMaxStackSize()) {
//                                nextStack.grow(stack.getCount());
//                                stack.setCount(0);
//                                break;
//                            }
//                        }
//
//                        if (!stack.isEmpty()) {
//                            if (itemStacks.size() >= emptyPlayerSlots) {
//                                return false;
//                            }
//
//                            itemStacks.add(stack);
//                        }
//                    } else if (storeIndex == -1) {
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//    private int getEmptyPlayerSlots() {
//        int slots = 0;
//        Iterator iterator = this.playerInventory.mainInventory.iterator();
//
//        while(iterator.hasNext()) {
//            ItemStack stack = (ItemStack)iterator.next();
//            if (stack.isEmpty()) {
//                ++slots;
//            }
//        }
//
//        return slots;
//    }
//
//    @Override
//    protected void clear() {
//        for(int index = 0; index < this.recipeBookContainer.getWidth() * this.recipeBookContainer.getHeight() + 1; ++index) {
//            if (index != this.recipeBookContainer.getOutputSlot() ||
//                    !(this.recipeBookContainer instanceof MPACraftingContainer) &&
//                            !(this.recipeBookContainer instanceof MPAWorkbenchContainer) &&
//                            !(this.recipeBookContainer instanceof PlayerContainer)) {
//                this.giveToPlayer(index);
//            }
//        }
//        this.recipeBookContainer.clear();
//    }
//}