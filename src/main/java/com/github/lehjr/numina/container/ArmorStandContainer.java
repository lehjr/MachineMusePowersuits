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
import com.mojang.datafixers.util.Pair;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class ArmorStandContainer extends Container {
    private final IInventory armorStandInventory;

    private static final ResourceLocation[] ARMOR_SLOT_TEXTURES =
            new ResourceLocation[]{
                    PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS,
                    PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS,
                    PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE,
                    PlayerContainer.EMPTY_ARMOR_SLOT_HELMET};
    private static final EquipmentSlotType[] VALID_EQUIPMENT_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
    private final PlayerEntity player;
    private final ArmorStandEntity armorStandEntity;

    public ArmorStandContainer(int id, PlayerInventory playerInventory, final ArmorStandEntity armorStand) { // base class for MPAArmorStandEntity
        super(NuminaObjects.ARMOR_STAND_CONTAINER_TYPE.get(), id);
        this.player = playerInventory.player;
        this.armorStandEntity = armorStand;
        armorStandInventory = new Inventory(
                // These have high "inventory slot" numbers:
                armorStand.getItemBySlot(EquipmentSlotType.MAINHAND), // 98
                armorStand.getItemBySlot(EquipmentSlotType.OFFHAND), // 99
                armorStand.getItemBySlot(EquipmentSlotType.FEET), // 100
                armorStand.getItemBySlot(EquipmentSlotType.LEGS), // 101
                armorStand.getItemBySlot(EquipmentSlotType.CHEST), // 102
                armorStand.getItemBySlot(EquipmentSlotType.HEAD));  // 103

        // ArmorStand Equipment (container slots 0-3)
        for(int k = 0; k < 4; ++k) {
            final EquipmentSlotType equipmentslottype = VALID_EQUIPMENT_SLOTS[k];
            this.addSlot(new Slot(armorStandInventory, 5 - k, 153, 8 + k * 18) {
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
                 * the case of armor slots)
                 */
                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                /**
                 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
                 */
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.canEquip(equipmentslottype, player);
                }

                /**
                 * Return whether this slot's stack can be taken from this slot.
                 */
                @Override
                public boolean mayPickup(PlayerEntity playerIn) {
                    ItemStack itemstack = this.getItem();
                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(playerIn);
                }



                @Override
                public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
                    armorStand.getItemBySlot(equipmentslottype);
                    return super.onTake(thePlayer, stack);
                }

                @Override
                public void set(ItemStack stack) {
                    armorStand.setItemSlot(equipmentslottype, stack.copy());
                    super.set(stack);
                }

                @OnlyIn(Dist.CLIENT)
                public Pair<ResourceLocation, ResourceLocation> getBackground() {
                    return Pair.of(PlayerContainer.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
                }
            });
        }

        // ArmorStand OffHand (container slot 4)
        this.addSlot(new Slot(armorStandInventory, 1, 80, 8) {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getBackground() {
                return Pair.of(PlayerContainer.BLOCK_ATLAS, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
            }

            @Override
            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
                armorStand.getItemBySlot(EquipmentSlotType.OFFHAND);
                return super.onTake(thePlayer, stack);
            }

            @Override
            public void set(ItemStack stack) {
                armorStand.setItemSlot(EquipmentSlotType.OFFHAND, stack.copy());
                super.set(stack);
            }
        });

        // ArmorStand MainHand (container slot 5)
        this.addSlot(new Slot(armorStandInventory, 0, 80, 26) {
            @Override
            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
                armorStand.getItemBySlot(EquipmentSlotType.MAINHAND);
                return super.onTake(thePlayer, stack);
            }

            @Override
            public void set(ItemStack stack) {
                armorStand.setItemSlot(EquipmentSlotType.MAINHAND, stack.copy());
                super.set(stack);
            }
        });

        // Player Equipment (container slots 6-9)
        for(int k = 0; k < 4; ++k) {
            final EquipmentSlotType equipmentslottype = VALID_EQUIPMENT_SLOTS[k];
            this.addSlot(new Slot(playerInventory, 39 - k, 8, 8 + k * 18) {
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
                 * the case of armor slots)
                 */
                public int getSlotStackLimit() {
                    return 1;
                }

                /**
                 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
                 */
                public boolean isItemValid(ItemStack stack) {
                    return stack.canEquip(equipmentslottype, player);
                }

                /**
                 * Return whether this slot's stack can be taken from this slot.
                 */
                public boolean mayPickup(PlayerEntity playerIn) {
                    ItemStack itemstack = this.getItem();
                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(playerIn);
                }

                @OnlyIn(Dist.CLIENT)
                public Pair<ResourceLocation, ResourceLocation> getBackground() {
                    return Pair.of(PlayerContainer.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
                }
            });
        }

        // Player Inventory (container slots 10-36)
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        // Hotbar (container slots 37-45)
        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }

        // Player Shield (container slot 46)
        this.addSlot(new Slot(playerInventory, 40, 80, 62) {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getBackground() {
                return Pair.of(PlayerContainer.BLOCK_ATLAS, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });
    }

    public ArmorStandEntity getArmorStandEntity() {
        return armorStandEntity;
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     *
     * Based loosely on the vanilla player container
     */
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstack);

            // from Armor Stand to Player Inventory
            if (index < 6) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

            // from Player Armor Slots to Player Inventory
            } else if (index >= 6 && index < 10) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }

            // to Armor Stand from Player Inventory
            } else if (equipmentslottype.getType() == EquipmentSlotType.Group.ARMOR && !this.slots.get(3 - equipmentslottype.getIndex()).hasItem()) {
                // Armor Stand first 4 slots is armor inventory
                int i = 3 - equipmentslottype.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }

            // to Player Armor from Player Inventory
            } else if (equipmentslottype.getType() == EquipmentSlotType.Group.ARMOR && !this.slots.get(9 - equipmentslottype.getIndex()).hasItem()) {
                // player armor inventory
                int i = 9 - equipmentslottype.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }

            // Armor Stand offhand
            } else if (equipmentslottype == EquipmentSlotType.OFFHAND && !this.slots.get(4).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 4, 5, false)) {
                    return ItemStack.EMPTY;
                }

            // player offhand
            } else if (equipmentslottype == EquipmentSlotType.OFFHAND && !this.slots.get(46).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }

            // player main inventory
            } else if (index >= 10 && index < 37) {
                if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }

            // player hotbar
            } else if (index >= 36 && index < 45) {
                if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }

            // player inventory
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

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.drop(itemstack2, false);
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void removed(PlayerEntity playerIn) {
        super.removed(playerIn);
        this.armorStandInventory.stopOpen(playerIn);
    }
}