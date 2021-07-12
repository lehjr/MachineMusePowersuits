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

package com.github.lehjr.numina.container;

import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.util.container.NuminaRecipeBookContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class CraftingContainer extends NuminaRecipeBookContainer<CraftingInventory> {
    private final CraftingInventory craftSlots = new CraftingInventory(this, 3, 3);
    private final CraftResultInventory resultSlots = new CraftResultInventory();
    private final IWorldPosCallable access;
    private final PlayerEntity player;

    public CraftingContainer(int windowID, PlayerInventory playerInventory) {
        this(windowID, playerInventory, IWorldPosCallable.NULL);
    }

    public CraftingContainer(int windowID, PlayerInventory playerInventory, IWorldPosCallable posCallable) {
        super(NuminaObjects.CRAFTING_CONTAINER_TYPE.get(), windowID);
        this.player = playerInventory.player;
        this.access = posCallable;
        // crafting result: slot 0
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));

        int row;
        int col;
        // crafting inventory: slot 1-9
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 3; ++col) {
                this.addSlot(new Slot(this.craftSlots, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        // inventory slot 10-36
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // hotbar slots 37-45
        for(col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 180, 142));
        }
    }

    protected static void setCraftingResultSlot(int windowId, World level, PlayerEntity playerIn, CraftingInventory craftingSlots, CraftResultInventory resultInventory) {
        if (!level.isClientSide()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)playerIn;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<ICraftingRecipe> optionalRecipe = level.getServer().getRecipeManager().getRecipeFor(IRecipeType.CRAFTING, craftingSlots, level);
            if (optionalRecipe.isPresent()) {
                ICraftingRecipe recipe = (ICraftingRecipe)optionalRecipe.get();
                if (resultInventory.setRecipeUsed(level, serverPlayer, recipe)) {
                    itemStack = recipe.assemble(craftingSlots);
                }
            }
            // set result slot on server side then send packet to set same on client
            resultInventory.setItem(0, itemStack);
            serverPlayer.connection.send(new SSetSlotPacket(windowId, 0, itemStack));
        }
    }

    @Override
    public void slotsChanged(IInventory iInventory) {
        if (!player.level.isClientSide) {
            setCraftingResultSlot(this.containerId, player.level, this.player, this.craftSlots, this.resultSlots);
        }
    }

    @Override
    public void fillCraftSlotsStackedContents(RecipeItemHelper itemHelperIn) {
        this.craftSlots.fillStackedContents(itemHelperIn);
    }


    /**
     * replace IWorldPosCallable.consume with something not position specific
     * since this will be used by a portable setup
     */
    public void consume(PlayerEntity playerIn) {
        this.resultSlots.clearContent();
        if (!playerIn.level.isClientSide) {
            this.clearContainer(playerIn, playerIn.level, this.craftSlots);
        }
    }

    /**
     * Called when the container is closed.
     */
    public void removed(PlayerEntity playerIn) {
        super.removed(playerIn);
        consume(playerIn);
    }


    /**
     * @param playerEntity
     * @param index
     * @return copy of the itemstack moved. ItemStack.Empty means no change
     */
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerEntity, int index) {
        ItemStack stackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack = slot.getItem();
            stackCopy = itemStack.copy();

            // crafting result
            if (index == getResultSlotIndex()) {
                this.consume(playerEntity);

                if (!this.moveItemStackTo(itemStack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack, stackCopy);

                // player inventory
            } else if (index >= 10 && index < 37) {
                if (!this.moveItemStackTo(itemStack, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }

                // hotbar
            } else if (index >= 37 && index < 46) {
                if (!this.moveItemStackTo(itemStack, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }


            } else if (!this.moveItemStackTo(itemStack, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack.getCount() == stackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack takenStack = slot.onTake(playerEntity, itemStack);
            if (index == getResultSlotIndex()) {
                playerEntity.drop(takenStack, false);
            }
        }
        return stackCopy;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack itemStack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(itemStack, slot);
    }


    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    @Override
    public boolean recipeMatches(IRecipe recipeIn) {
        return recipeIn.matches(this.craftSlots, this.player.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    @Override
    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        // 3x3 crafting grid plus output slot
        return getGridHeight() * getGridWidth() + 1;
    }
}