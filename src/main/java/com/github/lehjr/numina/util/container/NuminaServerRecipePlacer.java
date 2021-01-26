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

package com.github.lehjr.numina.util.container;

import com.github.lehjr.numina.container.CraftingContainer;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ServerRecipePlacer;
import net.minecraft.network.play.server.SPlaceGhostRecipePacket;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class NuminaServerRecipePlacer extends ServerRecipePlacer {
    public NuminaServerRecipePlacer(RecipeBookContainer recipeBookContainer) {
        super(recipeBookContainer);
    }

    public void place(ServerPlayerEntity player, @Nullable IRecipe recipeIn, boolean placeAll) {
        if (recipeIn != null && (player.getRecipeBook().isUnlocked(recipeIn)/* || // FIXME: not used here
                // we don't need no stinking locked recipes
                player.openContainer instanceof ArmorStandModdingContainer */)) {
            this.playerInventory = player.inventory;
            if (this.placeIntoInventory() || player.isCreative()) {
                this.recipeItemHelper.clear();
                player.inventory.accountStacks(this.recipeItemHelper);
                this.recipeBookContainer.fillStackedContents(this.recipeItemHelper);
                if (this.recipeItemHelper.canCraft(recipeIn, null)) {
                    this.tryPlaceRecipe(recipeIn, placeAll);
                } else {
                    this.clear();
                    /*if (this.recipeBookContainer instanceof ArmorStandModdingContainer) { // FIXME
                        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(()-> player),
                                new SPlaceGhostRecipePacket(player.openContainer.windowId, recipeIn));
                    } else */{
                        player.connection.sendPacket(new SPlaceGhostRecipePacket(player.openContainer.windowId, recipeIn));
                    }
                }
                player.inventory.markDirty();
            }
        }
    }

    private boolean placeIntoInventory() {
        List<ItemStack> itemStacks = Lists.newArrayList();
        int emptyPlayerSlots = this.getEmptyPlayerSlots();

        for(int index = 0; index < this.recipeBookContainer.getWidth() * this.recipeBookContainer.getHeight() + 1; ++index) {
            if (index != this.recipeBookContainer.getOutputSlot()) {
                ItemStack stack = this.recipeBookContainer.getSlot(index).getStack().copy();
                if (!stack.isEmpty()) {
                    int storeIndex = this.playerInventory.storeItemStack(stack);
                    if (storeIndex == -1 && itemStacks.size() <= emptyPlayerSlots) {
                        Iterator stackIterator = itemStacks.iterator();

                        while(stackIterator.hasNext()) {
                            ItemStack nextStack = (ItemStack)stackIterator.next();
                            if (nextStack.isItemEqual(stack) && nextStack.getCount() != nextStack.getMaxStackSize() && nextStack.getCount() + stack.getCount() <= nextStack.getMaxStackSize()) {
                                nextStack.grow(stack.getCount());
                                stack.setCount(0);
                                break;
                            }
                        }

                        if (!stack.isEmpty()) {
                            if (itemStacks.size() >= emptyPlayerSlots) {
                                return false;
                            }

                            itemStacks.add(stack);
                        }
                    } else if (storeIndex == -1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int getEmptyPlayerSlots() {
        int slots = 0;
        Iterator iterator = this.playerInventory.mainInventory.iterator();

        while(iterator.hasNext()) {
            ItemStack stack = (ItemStack)iterator.next();
            if (stack.isEmpty()) {
                ++slots;
            }
        }

        return slots;
    }

    @Override
    protected void clear() {
        for(int index = 0; index < this.recipeBookContainer.getWidth() * this.recipeBookContainer.getHeight() + 1; ++index) {
            if (index != this.recipeBookContainer.getOutputSlot() ||
                    !(this.recipeBookContainer instanceof CraftingContainer) &&
                           /* !(this.recipeBookContainer instanceof ArmorStandModdingContainer) && */
                            !(this.recipeBookContainer instanceof PlayerContainer)) {
                this.giveToPlayer(index);
            }
        }
        this.recipeBookContainer.clear();
    }
}