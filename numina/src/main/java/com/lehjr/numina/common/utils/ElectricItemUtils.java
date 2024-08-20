package com.lehjr.numina.common.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

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

    /**
     * returns the total amount of energy drained from the entity's equipped items
     *
     * Note that charging held items while in use causes issues so they are skipped
     */
    public static double drainPlayerEnergy(LivingEntity entity, double drainAmount, boolean simulate){
        double drainleft = drainAmount;
        if (entity.level().isClientSide || (entity instanceof Player && ((Player) entity).getAbilities().instabuild)) {
            return drainAmount;
        }

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (drainleft <= 0) {
                break;
            }

            ItemStack stack = ItemUtils.getItemFromEntitySlot(entity, slot);
            drainleft = drainleft - drainItem(stack, (int)drainleft, simulate);
        }

        if (!simulate && drainAmount - drainleft > 0 && entity instanceof Player player) {
            player.getInventory().setChanged();
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
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (rfLeft > 0) {
                ItemStack stack = ItemUtils.getItemFromEntitySlot(entity, slot);
                rfLeft = rfLeft - chargeItem(stack, (int)rfLeft, simulate);
            } else {
                break;
            }
        }
        if(entity instanceof Player player) {
            // charge other compatible items in inventory
            if (rfLeft > 0) {
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
    public static int drainItem(@Nonnull ItemStack itemStack, double drainAmount, boolean simulate) {
//        if (BlackList.blacklistModIds.machineMusePowersuits$contains(ItemUtils.getRegistryName(itemStack).getNamespace())) {
//            return 0;
//        }
        IEnergyStorage iEnergyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (iEnergyStorage != null) {
            return iEnergyStorage.extractEnergy((int)drainAmount, simulate);
        }
        return 0;
    }

    /**
     * returns the energy an itemStack has
     */
    public static int getItemEnergy(@Nonnull ItemStack itemStack) {
        IEnergyStorage iEnergyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (iEnergyStorage != null) {
            return iEnergyStorage.getEnergyStored();
        }
        return 0;
    }

    /**
     * returns total possible energy an itemStack can hold
     * Note: safe to use an integer as return value since values are stored as integers anyway
     */
    public static int getMaxItemEnergy(@Nonnull ItemStack itemStack) {
        IEnergyStorage iEnergyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (iEnergyStorage != null) {
            return iEnergyStorage.getMaxEnergyStored();
        }
        return 0;
    }

    public static int chargeItem(@Nonnull ItemStack itemStack, double chargeAmount, boolean simulate) {
        IEnergyStorage iEnergyStorage = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (iEnergyStorage != null) {
            return iEnergyStorage.receiveEnergy((int)chargeAmount, false);
        }
        return 0;
    }

    /**
     * Hacky way of determining if item is a power source.
     * @return
     */
    static double getMaxEnergyForComparison() {
        return 0;
//        return (0.8 * (NuminaCapabilities.getCapability(new ItemStack(NuminaObjects.BASIC_BATTERY.get()), Capabilities.EnergyStorage.ITEM)).map(IEnergyStorage::getMaxEnergyStored).orElse(0));
    }
}
