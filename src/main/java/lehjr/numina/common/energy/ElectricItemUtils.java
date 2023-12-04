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

package lehjr.numina.common.energy;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.item.ItemUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nonnull;

public class ElectricItemUtils {
    /**
     * returns the sum of the energy of the entity's equipped items
     *
     * Note here we filter out foreign items so the entity available/ usableenergy isn't wrong
     */
    public static double getPlayerEnergy(LivingEntity entity) {
        double avail = 0;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            avail += getItemEnergy(ItemUtils.getItemFromEntitySlot(entity, slot));
        }
        return avail;
    }

    /**
     * returns the total possible amount of energy the entity's equipped items can hold
     *
     * Note here we filter out foreign items so the entity available/ usableenergy isn't wrong
     */
    public static double getMaxPlayerEnergy(LivingEntity entity) {
        double avail = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            avail += getMaxItemEnergy(ItemUtils.getItemFromEntitySlot(entity, slot));
        }
        return avail;
    }

    @Deprecated
    public static double drainPlayerEnergy(LivingEntity entity, double drainAmount) {
        return drainPlayerEnergy(entity, (int)drainAmount, false);
    }

    /**
     * returns the total amount of energy drained from the entity's equipped items
     *
     * Note that charging held items while in use causes issues so they are skipped
     */
    public static double drainPlayerEnergy(LivingEntity entity, double drainAmount, boolean simulate){
        double drainleft = drainAmount;
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.level.isClientSide || player.getAbilities().instabuild) {
                return drainAmount;
            }

            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (drainleft <= 0) {
                    break;
                }

                ItemStack stack = ItemUtils.getItemFromEntitySlot(entity, slot);
                drainleft = drainleft - drainItem(stack, (int)drainleft, simulate);
            }
            if (!simulate && drainAmount - drainleft > 0) {
                player.getInventory().setChanged();
            }
        }
        return drainAmount - drainleft;
    }

    @Deprecated
    public static double givePlayerEnergy(LivingEntity entity, int rfToGive) {
        return givePlayerEnergy(entity, rfToGive, false);
    }

    /**
     * returns the total amount of energy given to a entity's equipped items
     *
     * Note that charging held items while in use causes issues so they are skipped
     */
    public static double givePlayerEnergy(LivingEntity entity, double rfToGive, boolean simulate) {
        double rfLeft = rfToGive;
        if (entity instanceof Player) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (rfLeft > 0) {
                    ItemStack stack = ItemUtils.getItemFromEntitySlot(entity, slot);
                    rfLeft = rfLeft - chargeItem(stack, (int)rfLeft, simulate);
                } else {
                    break;
                }
            }
            // charge other compatible items in inventory
            if (rfLeft > 0) {
                Player player = (Player) entity;
                for (int i = 0; i < ((Player) entity).getInventory().getContainerSize(); i++) {
                    if (rfLeft > 0) {
                        rfLeft = rfLeft - chargeItem(player.getInventory().getItem(i), (int) rfLeft, simulate);
                    } else {
                        break;
                    }
                }

                if (rfToGive - rfLeft > 0) {
                    player.getInventory().setChanged();
                }
            }
        }
        return rfToGive - rfLeft;
    }

    /**
     * returns the energy an itemStack has
     */
    public static int drainItem(@Nonnull ItemStack itemStack, int drainAmount, boolean simulate) {
        if (BlackList.blacklistModIds.contains(ItemUtils.getRegistryName(itemStack).getNamespace())) {
            return 0;
        }
        return itemStack.getCapability(ForgeCapabilities.ENERGY).filter(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() >= getMaxEnergyForComparison()).map(energyHandler -> energyHandler.extractEnergy(drainAmount, simulate)).orElse(0);
    }

    /**
     * returns the energy an itemStack has
     */
    public static int getItemEnergy(@Nonnull ItemStack itemStack) {
        return itemStack.getCapability(ForgeCapabilities.ENERGY).filter(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() >= getMaxEnergyForComparison()).map(energyHandler -> energyHandler.getEnergyStored()).orElse(0);
    }

    /**
     * returns total possible energy an itemStack can hold
     * Note: safe to use an integer as return value since values are stored as integers anyway
     */
    public static int getMaxItemEnergy(@Nonnull ItemStack itemStack) {
        return itemStack.getCapability(ForgeCapabilities.ENERGY).filter(iEnergyStorage -> iEnergyStorage.getMaxEnergyStored() >= getMaxEnergyForComparison()).map(energyHandler -> energyHandler.getMaxEnergyStored()).orElse(0);
    }

    public static int chargeItem(@Nonnull ItemStack itemStack, int chargeAmount, boolean simulate) {
        return itemStack.getCapability(ForgeCapabilities.ENERGY).map(energyHandler -> energyHandler.receiveEnergy(chargeAmount, simulate)).orElse(0);
    }

    /**
     * Hacky way of determining if item is a power source.
     * @return
     */
    static double getMaxEnergyForComparison() {
//        return 0;
        return (0.8 * new ItemStack(NuminaObjects.BASIC_BATTERY.get()).getCapability(ForgeCapabilities.ENERGY).map(energyHandler -> energyHandler.getMaxEnergyStored() ).orElse(0));
    }
}