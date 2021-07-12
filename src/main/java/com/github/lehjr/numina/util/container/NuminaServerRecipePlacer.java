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
        if (recipeIn != null && (player.getRecipeBook().contains(recipeIn)/* || // FIXME: not used here
                // we don't need no stinking locked recipes
                player.openContainer instanceof ArmorStandModdingContainer */)) {
            this.inventory = player.inventory;
            if (this.testClearGrid() || player.isCreative()) {
                this.stackedContents.clear();
                player.inventory.fillStackedContents(this.stackedContents);
                this.menu.fillCraftSlotsStackedContents(this.stackedContents);
                if (this.stackedContents.canCraft(recipeIn, null)) {
                    this.handleRecipeClicked(recipeIn, placeAll);
                } else {
                    this.clearGrid();
                    /*if (this.menu instanceof ArmorStandModdingContainer) { // FIXME
                        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(()-> player),
                                new SPlaceGhostRecipePacket(player.openContainer.windowId, recipeIn));
                    } else */{
                        player.connection.send(new SPlaceGhostRecipePacket(player.containerMenu.containerId, recipeIn));
                    }
                }
                player.inventory.setChanged();
            }
        }
    }

    // FIXME: make method in super class protected to avoid this
    private boolean testClearGrid() {
        List<ItemStack> list = Lists.newArrayList();
        int i = this.getAmountOfFreeSlotsInInventory();

        for(int j = 0; j < this.menu.getGridWidth() * this.menu.getGridHeight() + 1; ++j) {
            if (j != this.menu.getResultSlotIndex()) {
                ItemStack itemstack = this.menu.getSlot(j).getItem().copy();
                if (!itemstack.isEmpty()) {
                    int k = this.inventory.getSlotWithRemainingSpace(itemstack);
                    if (k == -1 && list.size() <= i) {
                        for(ItemStack itemstack1 : list) {
                            if (itemstack1.sameItem(itemstack) && itemstack1.getCount() != itemstack1.getMaxStackSize() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
                                itemstack1.grow(itemstack.getCount());
                                itemstack.setCount(0);
                                break;
                            }
                        }

                        if (!itemstack.isEmpty()) {
                            if (list.size() >= i) {
                                return false;
                            }

                            list.add(itemstack);
                        }
                    } else if (k == -1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // FIXME: make method in super class protected to avoid this
    private int getAmountOfFreeSlotsInInventory() {
        int slots = 0;
        Iterator iterator = this.inventory.items.iterator();

        while(iterator.hasNext()) {
            ItemStack stack = (ItemStack)iterator.next();
            if (stack.isEmpty()) {
                ++slots;
            }
        }

        return slots;
    }

    @Override
    protected void clearGrid() {
        for(int index = 0; index < this.menu.getGridWidth() * this.menu.getGridHeight()+ 1; ++index) {
            if (index != this.menu.getResultSlotIndex() ||
                    !(this.menu instanceof CraftingContainer) &&
                           /* !(this.menu instanceof ArmorStandModdingContainer) && */
                            !(this.menu instanceof PlayerContainer)) {
                this.moveItemToInventory(index);
            }
        }
        this.menu.clearCraftingContent();
    }
}