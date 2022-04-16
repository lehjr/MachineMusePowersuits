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

package numina.api.energy;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            avail += getItemEnergy(entity.getItemBySlot(slot));
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
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            avail += getMaxItemEnergy(entity.getItemBySlot(slot));
        }
        return avail;
    }

    @Deprecated
    public static int drainPlayerEnergy(LivingEntity entity, int drainAmount) {
        return drainPlayerEnergy(entity, drainAmount, false);
    }

    /**
     * returns the total amount of energy drained from the entity's equipped items
     *
     * Note that charging held items while in use causes issues so they are skipped
     */
    public static int drainPlayerEnergy(LivingEntity entity, int drainAmount, boolean simulate){
        if (entity.level.isClientSide || (entity instanceof Player && ((Player) entity).getAbilities().instabuild)) {
            return drainAmount;
        }
        int drainleft = drainAmount;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (drainleft == 0) {
                break;
            }

            ItemStack stack = entity.getItemBySlot(slot);
            drainleft = drainleft - drainItem(stack, drainleft, simulate);
        }
        return drainAmount - drainleft;
    }

    @Deprecated
    public static int givePlayerEnergy(LivingEntity entity, int rfToGive) {
        return givePlayerEnergy(entity, rfToGive, false);
    }

    /**
     * returns the total amount of energy given to a entity's equipped items
     *
     * Note that charging held items while in use causes issues so they are skipped
     */
    public static int givePlayerEnergy(LivingEntity entity, int rfToGive, boolean simulate) {
        int rfLeft = rfToGive;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (rfLeft > 0) {
                rfLeft = rfLeft - chargeItem(entity.getItemBySlot(slot), rfLeft, simulate);
            } else {
                break;
            }
        }
        // charge other compatible items in inventory
        if (rfLeft > 0 && entity instanceof Player) {
            for(int i = 0; i < ((Player) entity).getInventory().getContainerSize(); i++ ) {
                if (rfLeft > 0) {
                    rfLeft = rfLeft - chargeItem(((Player) entity).getInventory().getItem(i), rfLeft, simulate);
                } else {
                    break;
                }
            }
        }
        return rfToGive - rfLeft;
    }

    /**
     * returns the energy an itemStack has
     */
    public static int drainItem(@Nonnull ItemStack itemStack, int drainAmount, boolean simulate) {
        if (BlackList.blacklistModIds.contains(itemStack.getItem().getRegistryName().getNamespace())) {
            return 0;
        }
        return itemStack.getCapability(CapabilityEnergy.ENERGY).map(energyHandler -> energyHandler.extractEnergy(drainAmount, simulate)).orElse(0);
    }

    /**
     * returns the energy an itemStack has
     */
    public static int getItemEnergy(@Nonnull ItemStack itemStack) {
        return itemStack.getCapability(CapabilityEnergy.ENERGY).filter(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() >= getMaxEnergyForComparison()).map(energyHandler -> energyHandler.getEnergyStored()).orElse(0);
    }

    /**
     * returns total possible energy an itemStack can hold
     */
    public static int getMaxItemEnergy(@Nonnull ItemStack itemStack) {
        return itemStack.getCapability(CapabilityEnergy.ENERGY).filter(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() >= getMaxEnergyForComparison()).map(energyHandler -> energyHandler.getMaxEnergyStored()).orElse(0);
    }

    public static int chargeItem(@Nonnull ItemStack itemStack, int chargeAmount, boolean simulate) {
        return itemStack.getCapability(CapabilityEnergy.ENERGY).map(energyHandler -> energyHandler.receiveEnergy(chargeAmount, simulate)).orElse(0);
    }

    static int getMaxEnergyForComparison() {
        return 800000; // 80% of default value for basic battery
    }
}