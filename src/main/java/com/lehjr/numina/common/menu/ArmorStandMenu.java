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

package com.lehjr.numina.common.menu;

import com.lehjr.numina.client.gui.IconUtils;
import com.lehjr.numina.client.gui.slot.IconSlot;
import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.client.render.NuminaRenderer;
import com.lehjr.numina.common.base.NuminaObjects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;


public class ArmorStandMenu extends AbstractContainerMenu {
    private final SimpleContainer armorStandInventory;
    private static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private final Player player;
    private final ArmorStand armorStandEntity;

    public ArmorStandMenu(int windowID, Inventory playerInventory, ArmorStand armorStand) {
        super(NuminaObjects.ARMOR_STAND_CONTAINER_TYPE.get(), windowID);
        this.player = playerInventory.player;
        this.armorStandEntity = armorStand;
        armorStandInventory = new SimpleContainer(
                // These have high "inventory slot" numbers:
                armorStand.getItemBySlot(EquipmentSlot.MAINHAND), // 98
                armorStand.getItemBySlot(EquipmentSlot.OFFHAND), // 99
                armorStand.getItemBySlot(EquipmentSlot.FEET), // 100
                armorStand.getItemBySlot(EquipmentSlot.LEGS), // 101
                armorStand.getItemBySlot(EquipmentSlot.CHEST), // 102
                armorStand.getItemBySlot(EquipmentSlot.HEAD));  // 103

        // ArmorStand Equipment (container slots 0-3)
        for(int k = 0; k < 4; ++k) {
            final EquipmentSlot EquipmentSlot = VALID_EQUIPMENT_SLOTS[k];
            this.addSlot(new Slot(armorStandInventory, 5 - k, 152, 8 + k * 18) {
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
                    return stack.canEquip(EquipmentSlot, player);
                }

                /**
                 * Return whether this slot's stack can be taken from this slot.
                 */
                @Override
                public boolean mayPickup(Player playerIn) {
                    ItemStack itemstack = this.getItem();
                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(playerIn);
                }

                @Override
                public void onTake(Player thePlayer, ItemStack stack) {
                    armorStand.getItemBySlot(EquipmentSlot);
                    super.onTake(thePlayer, stack);
                }

                @Override
                public void set(ItemStack stack) {
                    armorStand.setItemSlot(EquipmentSlot, stack.copy());
                    super.set(stack);
                }

                @Nullable
                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return NuminaRenderer.getSlotBackground(EquipmentSlot);
                }
            });
        }

        // ArmorStand OffHand (container slot 4)
        this.addSlot(new Slot(armorStandInventory, 1, 83, 8) {
            @OnlyIn(Dist.CLIENT)
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return NuminaRenderer.getSlotBackground(EquipmentSlot.OFFHAND);
            }

            @Override
            public void onTake(Player thePlayer, ItemStack stack) {
                armorStand.getItemBySlot(EquipmentSlot.OFFHAND);
                super.onTake(thePlayer, stack);
            }

            @Override
            public void set(ItemStack stack) {
                armorStand.setItemSlot(EquipmentSlot.OFFHAND, stack.copy());
                super.set(stack);
            }
        });

        // ArmorStand MainHand (container slot 5)
        this.addSlot(new IconSlot(armorStandInventory, 0, 83, 26) {
            @Override
            public void drawIconAt(PoseStack matrixStack, double posX, double posY, Color colour) {
                IconUtils.getIcon().weaponSlotBackground.draw(matrixStack, posX, posY, colour);
            }

            @Override
            public void onTake(Player thePlayer, ItemStack stack) {
                armorStand.getItemBySlot(EquipmentSlot.MAINHAND);
                super.onTake(thePlayer, stack);
            }

            @Override
            public void set(ItemStack stack) {
                armorStand.setItemSlot(EquipmentSlot.MAINHAND, stack.copy());
                super.set(stack);
            }
        });

        // Player Equipment (container slots 6-9)
        for(int k = 0; k < 4; ++k) {
            final EquipmentSlot EquipmentSlot = VALID_EQUIPMENT_SLOTS[k];
            this.addSlot(new Slot(playerInventory, 39 - k, 8, 8 + k * 18) {
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
                    return stack.canEquip(EquipmentSlot, player);
                }

                /**
                 * Return whether this slot's stack can be taken from this slot.
                 */
                @Override
                public boolean mayPickup(Player playerIn) {
                    ItemStack itemstack = this.getItem();
                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(playerIn);
                }

                @OnlyIn(Dist.CLIENT)
                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, NuminaRenderer.ARMOR_SLOT_TEXTURES.get(EquipmentSlot));
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
        this.addSlot(new Slot(playerInventory, 40, 77, 62) {
            @OnlyIn(Dist.CLIENT)
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });
    }

    public ArmorStand getArmorStandEntity() {
        return armorStandEntity;
    }





    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     *
     * Based loosely on the vanilla player container
     */
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemstack);

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
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(3 - equipmentSlot.getIndex()).hasItem()) {
                // Armor Stand first 4 slots is armor inventory
                int i = 3 - equipmentSlot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }

                // to Player Armor from Player Inventory
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(9 - equipmentSlot.getIndex()).hasItem()) {
                // player armor inventory
                int i = 9 - equipmentSlot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }

                // Armor Stand offhand
            } else if (equipmentSlot == EquipmentSlot.OFFHAND && !this.slots.get(4).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 4, 5, false)) {
                    return ItemStack.EMPTY;
                }

                // player offhand
            } else if (equipmentSlot == EquipmentSlot.OFFHAND && !this.slots.get(46).hasItem()) {
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
            // FIXME!!

//            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
//            if (index == 0) {
//                playerIn.drop(itemstack2, false);
//            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void removed(Player playerIn) {
        super.removed(playerIn);
        this.armorStandInventory.stopOpen(playerIn);
    }
}