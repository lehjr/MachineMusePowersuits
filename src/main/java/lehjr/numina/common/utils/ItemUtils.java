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

package lehjr.numina.common.utils;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public static EquipmentSlot getEquipmentSlotForItem(ItemStack stack) {
        return Mob.getEquipmentSlotForItem(stack);
    }

    public static ResourceLocation getRegistryName(@Nonnull ItemStack itemStack) {
        return getRegistryName(itemStack.getItem());
    }

    @Nonnull
    public static ItemStack getItemFromEntitySlot(LivingEntity entity, EquipmentSlot slot) {
        if (entity == null) {
            return ItemStack.EMPTY;
        }
        return entity.getItemBySlot(slot);
    }

    @Nonnull
    public static ItemStack getItemFromEntityHand(LivingEntity entity, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            return getItemFromEntitySlot(entity, EquipmentSlot.MAINHAND);
        }
        return getItemFromEntitySlot(entity, EquipmentSlot.OFFHAND);
    }

    public static ResourceLocation getRegistryName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }


    /**
     * @param player player holding the mode changing modular item
     * @param mode new mode to set
     */
    public static void setModeAndSwapIfNeeded(Player player, int mode) {
        int selected = player.getInventory().selected;
        ItemStack itemStack = player.getInventory().getSelected();
        ItemStack host;
        ItemStack newModule;
        ItemStack stackToSet = ItemStack.EMPTY;
        IOtherModItemsAsModules foreignModuleCap = getForeignItemAsModuleCap(itemStack);
        IModeChangingItem mciCap = NuminaCapabilities.getModeChangingModularItem(itemStack);

        // held item is item from another mod
        if (foreignModuleCap != null) {
            host = foreignModuleCap.retrieveHostStack();
            mciCap = NuminaCapabilities.getModeChangingModularItem(host);
            if(mciCap != null) {
                if (mciCap.returnForeignModuleToModularItem(itemStack)) {
                    mciCap.setActiveMode(mode);
                    newModule = mciCap.getActiveModule();
                    IOtherModItemsAsModules foreignModuleCap1 = getForeignItemAsModuleCap(newModule);
                    if (foreignModuleCap1 != null) {
                        foreignModuleCap1.storeHostStack(host.copy());
                        stackToSet = newModule.copy();
                    } else {
                        stackToSet = host;
                    }
                }
            }
        } else if (mciCap != null) {
            mciCap.setActiveMode(mode);
            newModule = mciCap.getActiveModule();
            IOtherModItemsAsModules foreignModuleCap1 = getForeignItemAsModuleCap(newModule);
            if (foreignModuleCap1 != null) {
                foreignModuleCap1.storeHostStack(itemStack.copy());
                stackToSet = newModule;
            } else {
                stackToSet = mciCap.getModularItemStack();
            }
        }
        player.getInventory().setItem(selected, stackToSet);
        player.containerMenu.broadcastChanges();
    }

    public static IOtherModItemsAsModules getForeignItemAsModuleCap(@Nonnull ItemStack module) {
        return module.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES).resolve().orElse(null);
    }
}