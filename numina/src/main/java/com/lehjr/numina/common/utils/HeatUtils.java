package com.lehjr.numina.common.utils;

import com.google.common.util.concurrent.AtomicDouble;
import com.lehjr.numina.common.capabilities.heat.IHeatStorage;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import javax.annotation.Nonnull;

/**
 * Handler for heating and cooling.
 * Note: values can be read on either logical side, but should only be set server side
 */
public class HeatUtils {

    public static double getPlayerHeat(LivingEntity entity) {
        double heat = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            heat += getItemHeat(ItemUtils.getItemFromEntitySlot(entity, slot));
        }
        return heat;
    }

    /**
     * Should only be called server side
     */
    public static double getPlayerMaxHeat(LivingEntity entity) {
        AtomicDouble maxHeat = new AtomicDouble(0);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = ItemUtils.getItemFromEntitySlot(entity, slot);

            IHeatStorage heatStorage = itemStack.getCapability(NuminaCapabilities.HEAT);
            if(heatStorage != null) {
                maxHeat.getAndAdd(heatStorage.getMaxHeatStored());
            }
        }
        return maxHeat.get();
    }

    public static double coolPlayer(LivingEntity entity, double coolJoules) {
        if (entity.level().isClientSide /*|| entity.abilities.instabuild */) {
            return 0;
        }

        double coolingLeft = coolJoules;
        if (entity instanceof Player) {
            Player player = (Player) entity;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (entity.isUsingItem() && player.getInventory().getSelected() == stack) {
                    continue;
                }
                if (coolingLeft > 0) {
                    coolingLeft -= coolItem(stack, coolingLeft);
                } else {
                    break;
                }
            }
        } else {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (coolingLeft > 0) {
                    coolingLeft -= coolItem(ItemUtils.getItemFromEntitySlot(entity, slot), coolingLeft);
                } else {
                    break;
                }
            }
        }
        return coolJoules - coolingLeft;
    }

    /**
     * Should only be called server side
     */
    public static double heatPlayer(LivingEntity entity, double heatJoules) {
        if (entity.level().isClientSide /*|| entity.abilities.instabuild */) {
            return 0;
        }

        double heatLeftToGive = heatJoules;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (heatLeftToGive == 0) {
                break;
            }
            heatLeftToGive = heatLeftToGive - heatItem(ItemUtils.getItemFromEntitySlot(entity, slot), heatLeftToGive);
        }
        return heatLeftToGive;
    }

    /**
     * Heats the player's equipment and puts out player >>IF<< the equipment's
     * temperature is below the max threshold.
     * @param event
     */
    public static void heatEntity(LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            // round amount due do float values being weird
            double heatLeftToGive = Math.round(event.getAmount());
            final double originalHeatToGive = heatLeftToGive;

            LivingEntity entity = event.getEntity();
            boolean allPresent = true;

            for (ItemStack stack : entity.getArmorSlots()) {
                if (stack.getCapability(NuminaCapabilities.HEAT) == null) {
                    allPresent = false;
                    break;
                }
            }

            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (heatLeftToGive == 0) {
                    break;
                }

                IHeatStorage iHeatStorage = ItemUtils.getItemFromEntitySlot(entity, slot).getCapability(NuminaCapabilities.HEAT);
                if(iHeatStorage != null) {
                    heatLeftToGive -= iHeatStorage.receiveHeat(heatLeftToGive, false);
                }
            }

            // check if any heat applied to entity otherwise do nothing
            if (heatLeftToGive < originalHeatToGive) {
                if (allPresent) {
                    event.setCanceled(true);
                }
                if (heatLeftToGive > 0) {
                    entity.hurt(new OverheatDamage(entity.level().registryAccess()).overheat(), (float) heatLeftToGive);
                }
            }
        }
    }

    public static double getItemMaxHeat(@Nonnull ItemStack stack) {
        IHeatStorage iHeatStorage = stack.getCapability(NuminaCapabilities.HEAT);
        if(iHeatStorage != null) {
            return iHeatStorage.getMaxHeatStored();
        }
        return 0;
    }

    public static double getItemHeat(@Nonnull ItemStack stack) {
        IHeatStorage iHeatStorage = stack.getCapability(NuminaCapabilities.HEAT);
        if(iHeatStorage != null) {
            return iHeatStorage.getHeatStored();
        }
        return 0;
    }

    public static double heatItem(@Nonnull ItemStack stack, double value) {
        IHeatStorage iHeatStorage = stack.getCapability(NuminaCapabilities.HEAT);
        if(iHeatStorage != null) {
            return iHeatStorage.receiveHeat(value, false);
        }
        return 0;
    }

    public static double coolItem(@Nonnull ItemStack stack, double value) {
        IHeatStorage iHeatStorage = stack.getCapability(NuminaCapabilities.HEAT);
        if(iHeatStorage != null) {
            return iHeatStorage.extractHeat(value, false);
        }
        return 0;
    }

    static final ResourceKey<DamageType> OVERHEAT_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            NuminaConstants.OVERHEAT_DAMAGE_REGANAME
    );

    public static class OverheatDamage {
        private final DamageSource overheat;

        private final Registry<DamageType> damageTypes;

        public OverheatDamage(RegistryAccess registry) {
            this.damageTypes = registry.registryOrThrow(Registries.DAMAGE_TYPE);
            this.overheat = this.source();
        }

        private DamageSource source() {
            return new DamageSource(this.damageTypes.getHolderOrThrow(HeatUtils.OVERHEAT_DAMAGE));
        }

        public DamageSource overheat() {
            return this.overheat;
        }
    }
}
