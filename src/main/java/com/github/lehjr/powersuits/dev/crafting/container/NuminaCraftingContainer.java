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

package com.github.lehjr.powersuits.dev.crafting.container;

import com.github.lehjr.numina.basemod.NuminaObjects;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.client.gui.slot.HideableSlot;
import com.github.lehjr.numina.util.client.gui.slot.HideableSlotItemHandler;
import com.github.lehjr.numina.util.client.gui.slot.IHideableSlot;
import com.github.lehjr.numina.util.client.gui.slot.IIConProvider;
import com.github.lehjr.numina.util.client.render.MuseIconUtils;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.math.MuseMathUtils;
import com.github.lehjr.powersuits.basemod.MPSObjects;
import com.google.common.collect.HashBiMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NuminaCraftingContainer extends NuminaRecipeBookContainer<CraftingInventory> implements IModularItemContainerSlotProvider {
    private final CraftingInventory craftSlots = new CraftingInventory(this, 3, 3);
    private final CraftResultInventory resultSlots = new CraftResultInventory();
    private static final int resultIndex = 0;
    private final PlayerEntity player;

    /** values used later */
    final Map<EquipmentSlotType, Integer> equipmentTypeToPlayerInventoryIndex = new HashMap<EquipmentSlotType, Integer>() {{
        put(EquipmentSlotType.OFFHAND, 40);
        put(EquipmentSlotType.HEAD, 39);
        put(EquipmentSlotType.CHEST, 38);
        put(EquipmentSlotType.LEGS, 37);
        put(EquipmentSlotType.FEET, 36);
    }};

    Map<EquipmentSlotType, Pair<Integer, Integer>> equipmentSlotRangeMap = new HashMap();
    HashBiMap<EquipmentSlotType, Integer> equipmentSlotTypeMap = HashBiMap.create();


    public NuminaCraftingContainer(int containerID, PlayerInventory playerInventory) {
        super(MPSObjects.MPS_CRAFTING_CONTAINER_TYPE.get(), containerID);
        /** add equipped item slot index */
        equipmentTypeToPlayerInventoryIndex.put(EquipmentSlotType.MAINHAND, playerInventory.selected);
        this.player = playerInventory.player;

        /** Crafting result */
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, resultIndex, 124, 35));

        int row, col;

        /** Crafting grid */
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 3; ++col) {
                this.addSlot(new Slot(this.craftSlots, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        /** Main inventory */
        for(row = 0; row < 3; ++row) {
            for(col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18) {
                                // disable pickup for modular items
                                 @Override
                                 public boolean mayPickup(PlayerEntity p_82869_1_) {
                                     return !(getItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(iItemHandler -> iItemHandler instanceof IModularItem).orElse(false));
                                 }
                             });
            }
        }

        /** Hotbar with pickup disabled for modular items */
        for(col = 0; col < 9; ++col) {
            if (col == playerInventory.selected &&
                    playerInventory.getItem(playerInventory.selected).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(iItemHandler -> iItemHandler instanceof IModularItem).orElse(false)) {
                addSlotForEquipment(EquipmentSlotType.MAINHAND, this.slots.size());
                this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142) {
                    @Override
                    public boolean mayPickup(PlayerEntity player) {
                        return false;
                    }
                });
            } else {
                this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142) {
                    @Override
                    public boolean mayPickup(PlayerEntity p_82869_1_) {
                        return !(getItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(iItemHandler -> iItemHandler instanceof IModularItem).orElse(false));
                    }
                });
            }
        }

        /** Hidden equipment slots and their modular item inventory if any -------------------------------------------- */
        /** Mainhand (not hidden, but disable pickup above), Offhand and Armor (hidden and pickup disabled) */
        for (Map.Entry<EquipmentSlotType, Integer> entry: equipmentTypeToPlayerInventoryIndex.entrySet()) {
            playerInventory.getItem(entry.getValue()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                if (iItemHandler instanceof IModularItem) {
                    Slot slot;
                    // add map entry
                    if (entry.getKey() != EquipmentSlotType.MAINHAND) {
                        addSlotForEquipment(entry.getKey(), this.slots.size());

                        // add slot
                        slot = this.addSlot(new HideableSlot(playerInventory, getSlotForEquipmentType(entry.getKey()), 0, 0) {
                            @Override
                            public boolean mayPickup(PlayerEntity player) {
                                return false;
                            }
                        });
                    } else {
                        slot = getSlot(getSlotForEquipmentType(entry.getKey()));
                    }

                    // then create slots for its inventory
                    int startIndex = this.slots.size();
                    for (int modularItemInvIndex = 0; modularItemInvIndex < iItemHandler.getSlots(); modularItemInvIndex ++) {
                        if (MuseMathUtils.isIntInRange(((IModularItem) iItemHandler).getRangeForCategory(EnumModuleCategory.ARMOR), modularItemInvIndex)) {
                            addSlot(new IconSlot(iItemHandler, slots.indexOf(slot), modularItemInvIndex, -1000, -1000) {

                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public com.mojang.datafixers.util.Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                                    // TODO: Armor icon
                                    return NuminaObjects.getSlotBackground(EquipmentSlotType.OFFHAND);
                                }

                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(MatrixStack matrixStack, double v, double v1, Colour colour) {

                                }
                            });
                        } else if (MuseMathUtils.isIntInRange(((IModularItem) iItemHandler).getRangeForCategory(EnumModuleCategory.ENERGY_STORAGE), modularItemInvIndex)) {
                            addSlot(new IconSlot(iItemHandler, slots.indexOf(slot), modularItemInvIndex, -1000, -1000) {
                                @OnlyIn(Dist.CLIENT)
                                @Override
                                public void drawIconAt(MatrixStack matrixStack, double posX, double posY, Colour colour) {
                                    MuseIconUtils.getIcon().energyStorageBackground.renderIconScaledWithColour(matrixStack, posX, posY, 16, 16, Colour.WHITE);
                                }
                            });
                        } else {
                            addSlot(new HideableSlotItemHandler(iItemHandler, slots.indexOf(slot), modularItemInvIndex, -1000, -1000));
                        }
                    }
                    // map entry for the modular item inventory.
                    int endIndex = this.slots.size();
                    addRangeForEquipmentSlot(entry.getKey(), startIndex, endIndex);
                }
            });
        }

        for (Slot slot : this.slots) {
            if(slot instanceof IHideableSlot) {
                ((IHideableSlot) slot).disable();

            }
        }
    }

    protected static void slotChangedCraftingGrid(int containerIDIn, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftResultInventory resultInventory) {
        if (!world.isClientSide) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<ICraftingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(IRecipeType.CRAFTING, craftingInventory, world);
            if (optional.isPresent()) {
                ICraftingRecipe icraftingrecipe = optional.get();
                if (resultInventory.setRecipeUsed(world, serverplayerentity, icraftingrecipe)) {
                    itemstack = icraftingrecipe.assemble(craftingInventory);
                }
            }

            resultInventory.setItem(resultIndex, itemstack);
            serverplayerentity.connection.send(new SSetSlotPacket(containerIDIn, resultIndex, itemstack));
        }
    }

    @Override
    public void slotsChanged(IInventory iInventory) {
        slotChangedCraftingGrid(this.containerId, this.player.level, this.player, this.craftSlots, this.resultSlots);
    }

    @Override
    public void fillCraftSlotsStackedContents(RecipeItemHelper itemHelper) {
        this.craftSlots.fillStackedContents(itemHelper);
    }

    @Override
    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    @Override
    public boolean recipeMatches(IRecipe iRecipe) {
        return iRecipe.matches(this.craftSlots, this.player.level);
    }

    // PlayerContainer version
    public void removed(PlayerEntity playerEntity) {
        super.removed(playerEntity);
        this.resultSlots.clearContent();
        if (!playerEntity.level.isClientSide) {
            this.clearContainer(playerEntity, playerEntity.level, this.craftSlots);
        }
    }

    // player container version
    public boolean stillValid(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotIndex == 0) {
//                this.access.execute((world, blockPos) -> itemstack1.getItem().onCraftedBy(itemstack1, world, player));
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (slotIndex >= 10 && slotIndex < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (slotIndex < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(player, itemstack1);
            if (slotIndex == 0) {
                player.drop(itemstack2, false);
            }
        }

        return itemstack;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack itemStack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(itemStack, slot);
    }

    @Override
    public int getResultSlotIndex() {
        return resultIndex;
    }

    @Override
    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    @Override
    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getSize() {
        // gridWidth x gridHeight + resultSlot
        return getGridWidth() * getGridHeight() + 1;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public RecipeBookCategory getRecipeBookType() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public Map<EquipmentSlotType, Pair<Integer, Integer>> getEquipmentSlotRangeMap() {
        return this.equipmentSlotRangeMap;
    }

    @Override
    public HashBiMap<EquipmentSlotType, Integer> getEquipmentSlotTypeMap() {
        return this.equipmentSlotTypeMap;
    }

    @Override
    public Container getContainer() {
        return this;
    }

    abstract class IconSlot extends HideableSlotItemHandler implements IIConProvider {
        public IconSlot(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition) {
            super(itemHandler, parent, index, xPosition, yPosition);
        }

        public IconSlot(IItemHandler itemHandler, int parent, int index, int xPosition, int yPosition, boolean isEnabled) {
            super(itemHandler, parent, index, xPosition, yPosition, isEnabled);
        }
    }
}