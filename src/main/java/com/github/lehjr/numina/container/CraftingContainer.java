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
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class CraftingContainer extends NuminaRecipeBookContainer<CraftingInventory> {
    private final CraftingInventory craftingInventory;
    private final CraftResultInventory resultInventory;
    private final PlayerEntity player;

    public CraftingContainer(int windowId, PlayerInventory playerInventory) {
        super(NuminaObjects.CRAFTING_CONTAINER_TYPE.get(), windowId);
        this.craftingInventory = new CraftingInventory(this, 3, 3);
        this.resultInventory = new CraftResultInventory();
        this.player = playerInventory.player;

        // crafting result: slot 0
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftingInventory, this.resultInventory, 0, 124, 35));

        int row;
        int col;
        // crafting inventory: slot 1-9
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 3; ++col) {
                this.addSlot(new Slot(this.craftingInventory, col + row * 3, 30 + col * 18, 17 + row * 18));
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

    protected static void setCraftingResultSlot(int windowId, World world, PlayerEntity playerIn, CraftingInventory craftingInventory, CraftResultInventory resultInventory) {
        if (!world.isRemote) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)playerIn;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<ICraftingRecipe> optionalRecipe = world.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING, craftingInventory, world);
            if (optionalRecipe.isPresent()) {
                ICraftingRecipe recipe = (ICraftingRecipe)optionalRecipe.get();
                if (resultInventory.canUseRecipe(world, serverPlayer, recipe)) {
                    itemStack = recipe.getCraftingResult(craftingInventory);
                }
            }
            // set result slot on server side then send packet to set same on client
            resultInventory.setInventorySlotContents(0, itemStack);
            serverPlayer.connection.sendPacket(new SSetSlotPacket(windowId, 0, itemStack));
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        if (!player.world.isRemote) {
            setCraftingResultSlot(this.windowId, player.world, this.player, this.craftingInventory, this.resultInventory);
        }
    }

    @Override
    public void fillStackedContents(RecipeItemHelper itemHelperIn) {
        this.craftingInventory.fillStackedContents(itemHelperIn);
    }

    @Override
    public void clear() {
        this.craftingInventory.clear();
        this.resultInventory.clear();
    }

    @Override
    public boolean matches(IRecipe recipeIn) {
        return recipeIn.matches(this.craftingInventory, this.player.world);
    }

    /**
     * replace IWorldPosCallable.consume with something not position specific
     * since this will be used by a portable setup
     */
    public void consume(PlayerEntity playerIn) {
        this.resultInventory.clear();
        if (!playerIn.world.isRemote) {
            this.clearContainer(playerIn, playerIn.world, this.craftingInventory);
        }
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        consume(playerIn);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    /**
     * @param playerEntity
     * @param index
     * @return copy of the itemstack moved. ItemStack.Empty means no change
     */
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int index) {
        ItemStack stackCopy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            stackCopy = itemStack.copy();

            // crafting result
            if (index == getOutputSlot()) {
                this.consume(playerEntity);

                if (!this.mergeItemStack(itemStack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack, stackCopy);

                // player inventory
            } else if (index >= 10 && index < 37) {
                if (!this.mergeItemStack(itemStack, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }

                // hotbar
            } else if (index >= 37 && index < 46) {
                if (!this.mergeItemStack(itemStack, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }


            } else if (!this.mergeItemStack(itemStack, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack.getCount() == stackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack takenStack = slot.onTake(playerEntity, itemStack);
            if (index == getOutputSlot()) {
                playerEntity.dropItem(takenStack, false);
            }
        }
        return stackCopy;
    }

    @Override
    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.inventory != this.resultInventory && super.canMergeSlot(itemStack, slot);
    }

    @Override
    public int getOutputSlot() {
        return 0;
    }

    @Override
    public int getWidth() {
        return this.craftingInventory.getWidth();
    }

    @Override
    public int getHeight() {
        return this.craftingInventory.getHeight();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        // 3x3 crafting grid plus output slot
        return getHeight() * getWidth() + 1;
    }
}