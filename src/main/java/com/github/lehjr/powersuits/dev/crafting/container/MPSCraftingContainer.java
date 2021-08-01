//package com.github.lehjr.powersuits.dev.crafting.container;
//
//import com.github.lehjr.powersuits.basemod.MPSObjects;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.inventory.CraftResultInventory;
//import net.minecraft.inventory.CraftingInventory;
//import net.minecraft.inventory.IInventory;
//import net.minecraft.inventory.container.CraftingResultSlot;
//import net.minecraft.inventory.container.Slot;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.*;
//import net.minecraft.network.play.server.SSetSlotPacket;
//import net.minecraft.util.IWorldPosCallable;
//import net.minecraft.world.World;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.Optional;
//
//public class MPSCraftingContainer extends MPSRecipeBookContainer<CraftingInventory> {
//    private final CraftingInventory craftMatrix;
//    private final CraftResultInventory craftResult;
//    private final PlayerEntity player;
//
//    public MPSCraftingContainer(int id, PlayerInventory playerInventory) {
//        this(id, playerInventory, IWorldPosCallable.NULL);
//    }
//
//    public MPSCraftingContainer(int windowId, PlayerInventory playerInventory, IWorldPosCallable posCallable) {
////        super(ContainerType.CRAFTING, windowId);
//        super(MPSObjects.MPS_CRAFTING_CONTAINER_TYPE.get(), windowId);
//        System.out.println("windowID: " + windowId);
//
//
//        System.out.println("is world remote here?: " + playerInventory.player.level.isClientSide());
//
//
//        this.craftMatrix = new CraftingInventory(this, 3, 3);
//        this.craftResult = new CraftResultInventory();
//        this.player = playerInventory.player;
//
//        // crafting result: slot 0
//        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
//
//        int row;
//        int col;
//        // crafting inventory: slot 1-9
//        for(row = 0; row < 3; ++row) {
//            for(col = 0; col < 3; ++col) {
//                this.addSlot(new Slot(this.craftMatrix, col + row * 3, 30 + col * 18, 17 + row * 18));
//            }
//        }
//
//        // inventory slot 10-36
//        for(row = 0; row < 3; ++row) {
//            for(col = 0; col < 9; ++col) {
//                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
//            }
//        }
//
//        // hotbar slots 37-45
//        for(col = 0; col < 9; ++col) {
//            this.addSlot(new Slot(playerInventory, col, 8 + col * 180, 142));
//        }
//    }
//
//    protected static void setCraftingResultSlot(int windowId, World world, PlayerEntity playerIn, CraftingInventory craftingInventory, CraftResultInventory resultInventory) {
//        System.out.println("doing somethingn here");
//
//        if (!world.isClientSide) {
//            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)playerIn;
//            ItemStack itemStack = ItemStack.EMPTY;
//            Optional<ICraftingRecipe> optionalRecipe = world.getServer().getRecipeManager().getRecipeFor(IRecipeType.CRAFTING, craftingInventory, world);
//            if (optionalRecipe.isPresent()) {
//                ICraftingRecipe recipe = (ICraftingRecipe)optionalRecipe.get();
//                if (resultInventory.setRecipeUsed(world, serverPlayer, recipe)) {
//                    itemStack = recipe.assemble(craftingInventory);
//                }
//            }
//            // set result slot on server side then send packet to set same on client
//            resultInventory.setItem(0, itemStack);
//
//            System.out.println("sending slot packet: " + itemStack.serializeNBT());
//
//            serverPlayer.connection.send(new SSetSlotPacket(windowId, 0, itemStack));
//        }
//    }
//
//    /**
//     * Callback for when the crafting matrix is changed.
//     */
//    @Override
//    public void slotsChanged(IInventory iInventory) {
//        System.out.println("doing somethingn here");
//        if (!player.level.isClientSide) {
//            setCraftingResultSlot(this.containerId, player.level, this.player, this.craftMatrix, this.craftResult);
//        }
//    }
//
//    @Override
//    public void fillCraftSlotsStackedContents(RecipeItemHelper itemHelperIn) {
//        System.out.println("doing somethingn here");
//        this.craftMatrix.fillStackedContents(itemHelperIn);
//    }
//
//    @Override
//    public void clearCraftingContent() {
//        System.out.println("doing somethingn here");
//        this.craftMatrix.clearContent();
//        this.craftResult.clearContent();
//    }
//
//    @Override
//    public boolean recipeMatches(IRecipe recipeIn) {
//        return recipeIn.matches(this.craftMatrix, this.player.level);
//    }
//
//    /**
//     * replace IWorldPosCallable.consume with something not position specific
//     * since this will be used by a portable setup
//     */
//    public void consume(PlayerEntity playerIn) {
//        System.out.println("doing somethingn here");
//        this.craftResult.clearContent();
//        if (!playerIn.level.isClientSide) {
//            this.clearContainer(playerIn, playerIn.level, this.craftMatrix);
//        }
//    }
////
////    /**
////     * Called when the container is closed.
////     */
////    public void onContainerClosed(PlayerEntity playerIn) {
////        System.out.println("doing somethingn here");
////        super.onContainerClosed(playerIn);
////        consume(playerIn);
////    }
//
//    /**
//     * Called when the container is closed.
//     */
//    @Override
//    public void removed(PlayerEntity playerIn) {
//        super.removed(playerIn);
//        IWorldPosCallable.NULL.execute((container, blockPos) -> {
//            this.clearContainer(playerIn, container, this.craftMatrix);
//        });
//    }
//
//
//    @Override
//    public boolean stillValid(PlayerEntity playerIn) {
//        return true;
//    }
//
//    /**
//     * @param playerEntity
//     * @param index
//     * @return copy of the itemstack moved. ItemStack.Empty means no change
//     */
//    @Override
//    public ItemStack quickMoveStack(PlayerEntity playerEntity, int index) {
//        System.out.println("doing somethingn here");
//        ItemStack stackCopy = ItemStack.EMPTY;
//        Slot slot = this.slots.get(index);
//        if (slot != null && slot.hasItem()) {
//            ItemStack itemStack = slot.getItem();
//            stackCopy = itemStack.copy();
//
//            // crafting result
//            if (index == getResultSlotIndex()) {
//                this.consume(playerEntity);
//
//                if (!this.moveItemStackTo(itemStack, 10, 46, true)) {
//                    return ItemStack.EMPTY;
//                }
//                slot.onQuickCraft(itemStack, stackCopy);
//
//            // player inventory
//            } else if (index >= 10 && index < 37) {
//                if (!this.moveItemStackTo(itemStack, 37, 46, false)) {
//                    return ItemStack.EMPTY;
//                }
//
//            // hotbar
//            } else if (index >= 37 && index < 46) {
//                if (!this.moveItemStackTo(itemStack, 10, 37, false)) {
//                    return ItemStack.EMPTY;
//                }
//
//
//            } else if (!this.moveItemStackTo(itemStack, 10, 46, false)) {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemStack.isEmpty()) {
//                slot.set(ItemStack.EMPTY);
//            } else {
//                slot.setChanged();
//            }
//
//            if (itemStack.getCount() == stackCopy.getCount()) {
//                return ItemStack.EMPTY;
//            }
//
//            ItemStack takenStack = slot.onTake(playerEntity, itemStack);
//            if (index == getResultSlotIndex()) {
//                playerEntity.drop(takenStack, false);
//            }
//        }
//        return stackCopy;
//    }
//
//    @Override
//    public boolean canTakeItemForPickAll(ItemStack itemStack, Slot slot) {
//        return slot.container != this.craftResult && super.canTakeItemForPickAll(itemStack, slot);
//    }
//
//    @Override
//    public int getResultSlotIndex() {
//        return 0;
//    }
//
//    @Override
//    public int getGridWidth() {
//        return this.craftMatrix.getWidth();
//    }
//
//    @Override
//    public int getGridHeight() {
//        return this.craftMatrix.getHeight();
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public int getSize() {
//        // 3x3 crafting grid plus output slot
//        return getGridHeight() * getGridWidth() + 1;
//    }
//
////    @Override
////    public List<RecipeBookCategories> getRecipeBookCategories() {
////        System.out.println("doing somethingn here");
////        // needed since this isn't an an actual instance of MPAWorkbenchContainer
////        return Lists.newArrayList(new RecipeBookCategories[]{RecipeBookCategories.CRAFTING_SEARCH, RecipeBookCategories.CRAFTING_EQUIPMENT, RecipeBookCategories.CRAFTING_BUILDING_BLOCKS, RecipeBookCategories.CRAFTING_MISC, RecipeBookCategories.CRAFTING_REDSTONE});
////    }
//
//    @OnlyIn(Dist.CLIENT)
//    public RecipeBookCategory getRecipeBookType() {
//        return RecipeBookCategory.CRAFTING;
//    }
//}