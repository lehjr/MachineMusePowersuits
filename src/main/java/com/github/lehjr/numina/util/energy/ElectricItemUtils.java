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

package com.github.lehjr.numina.util.energy;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;

public class ElectricItemUtils {
    /**
     * returns the sum of the energy of the entity's equipped items
     *
     * Note here we filter out foreign items so the entity available/ usableenergy isn't wrong
     */
    public static int getPlayerEnergy(LivingEntity entity) {
        int avail = 0;

        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            avail += getItemEnergy(entity.getItemStackFromSlot(slot));
        }
        return avail;
    }

    /**
     * returns the total possible amount of energy the entity's equipped items can hold
     *
     * Note here we filter out foreign items so the entity available/ usableenergy isn't wrong
     */
    public static int getMaxPlayerEnergy(LivingEntity entity) {
        int avail = 0;
        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            avail += getMaxItemEnergy(entity.getItemStackFromSlot(slot));
        }
        return avail;
    }

    /**
     * returns the total amount of energy drained from the entity's equipped items
     *
     * Note that charging held items while in use causes issues so they are skipped
     */
    public static int drainPlayerEnergy(LivingEntity entity, int drainAmount) {
        if (entity.world.isRemote || (entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.isCreativeMode)) {
            return 0;
        }
        int drainleft = drainAmount;
        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            if (drainleft == 0) {
                break;
            }

            ItemStack stack = entity.getItemStackFromSlot(slot);
            Item item = stack.getItem();

            // check if the tool is a modular item. If not, skip it.
            if (!stack.isEmpty() && item instanceof ToolItem &&
                    !BlackList.blacklistModIds.contains(item.getRegistryName().getNamespace())) {
                drainleft = drainleft - drainItem(stack, drainleft);
            }
        }
        return drainAmount - drainleft;
    }

    /**
     * returns the total amount of energy given to a entity's equipped items
     *
     * Note that charging held items while in use causes issues so they are skipped
     */
    public static int givePlayerEnergy(LivingEntity entity, int rfToGive) {
        int rfLeft = rfToGive;
        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            if (rfLeft > 0) {
                rfLeft = rfLeft - chargeItem(entity.getItemStackFromSlot(slot), rfLeft);
            } else {
                break;
            }
        }
        return rfToGive - rfLeft;
    }


    /**
     * returns the energy an itemStack has
     */
    public static int drainItem(@Nonnull ItemStack itemStack, int drainAmount) {
        return itemStack.getCapability(CapabilityEnergy.ENERGY).map(energyHandler -> energyHandler.extractEnergy(drainAmount, false)).orElse(0);
    }

    /**
     * returns the energy an itemStack has
     */
    public static int getItemEnergy(@Nonnull ItemStack itemStack) {
        return itemStack.getCapability(CapabilityEnergy.ENERGY).map(energyHandler -> energyHandler.getEnergyStored()).orElse(0);
    }

    /**
     * returns total possible energy an itemStack can hold
     */
    public static int getMaxItemEnergy(@Nonnull ItemStack itemStack) {
        return itemStack.getCapability(CapabilityEnergy.ENERGY).map(energyHandler -> energyHandler.getMaxEnergyStored()).orElse(0);
    }

    public static int chargeItem(@Nonnull ItemStack itemStack, int chargeAmount) {
        return itemStack.getCapability(CapabilityEnergy.ENERGY).map(energyHandler -> energyHandler.receiveEnergy(chargeAmount, false)).orElse(0);
    }
}