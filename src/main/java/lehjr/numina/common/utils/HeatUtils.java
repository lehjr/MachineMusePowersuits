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

import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler for heating and cooling.
 * Note: values can be read on either logical side, but should only be set server side
 */
public class HeatUtils {
    public record PlayerHeat(double currentHeat, double maxHeat) {}

    public static PlayerHeat getPlayerHeat(LivingEntity entity) {
        double currentHeat = 0;
        double maxHeat = 0;

        Map<EquipmentSlot, IHeatStorage> heatCapMap = getHeatCapMap(entity);
        for (Map.Entry<EquipmentSlot, IHeatStorage> entry : heatCapMap.entrySet()) {
            currentHeat += entry.getValue().getHeatStored();
            maxHeat += entry.getValue().getMaxHeatStored();
        }

        return  new PlayerHeat(currentHeat, maxHeat);
    }


















    /**
     * Should only be called server side
     */
    public static double getPlayerMaxHeat(LivingEntity entity) {
        AtomicDouble maxHeat = new AtomicDouble(0);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = ItemUtils.getItemFromEntitySlot(entity, slot);

            // Armor slots can't hold tools
            if ((slot.getType().equals(EquipmentSlot.Type.ARMOR)) ||
                    // but hand slots can hold armor
                    (slot.getType().equals(EquipmentSlot.Type.HAND) && !(itemStack.getItem() instanceof ArmorItem))) {
                itemStack.getCapability(NuminaCapabilities.HEAT).ifPresent(heat->maxHeat.getAndAdd(heat.getMaxHeatStored()));
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
     */ // fixme!!!
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
    @SubscribeEvent
    public static void heatEntity(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE) && event.getEntity() instanceof Player player) {
            // round amount due do float values being weird
            final double originalHeatToGive = Math.round(event.getAmount());
            double heatLeftToGive = originalHeatToGive;

            Map<EquipmentSlot, IHeatStorage> heatCapMap = getHeatCapMap(player);
            boolean allPresent = allPresent(heatCapMap);

            for (Map.Entry<EquipmentSlot, IHeatStorage> entry : heatCapMap.entrySet()) {
                if (heatLeftToGive == 0) {
                    break;
                }
                IHeatStorage iHeatStorage = entry.getValue();
                heatLeftToGive -= iHeatStorage.receiveHeat(heatLeftToGive, false);
            }

            // check if any heat applied to entity otherwise do nothing
            if (heatLeftToGive < originalHeatToGive) {
                if (allPresent) {
                    event.setCanceled(true);
                }

                if (heatLeftToGive > 0) {
                    player.hurt(new OverheatDamage(player.level().registryAccess()).overheat(), (float) heatLeftToGive);
                }
            }
        }
    }

//    /**
//     * Heats the player's equipment and puts out player >>IF<< the equipment's
//     * temperature is below the max threshold.
//     * @param event
//     */
//    // Looks like this event fires last whereas the other fires first
//    public static void heatEntity(LivingDamageEvent event) {
//
//        if (event.getSource().is(DamageTypeTags.IS_FIRE) && event.getEntity() instanceof Player player) {
//            // round amount due do float values being weird
//            final double originalHeatToGive = Math.round(event.getAmount());
//            double heatLeftToGive = originalHeatToGive;
//
//            Map<EquipmentSlot, IHeatStorage> heatCapMap = getHeatCapMap(player);
//            boolean allPresent = allPresent(heatCapMap);
//
//            for (Map.Entry<EquipmentSlot, IHeatStorage> entry : heatCapMap.entrySet()) {
//                if (heatLeftToGive == 0) {
//                    break;
//                }
//                IHeatStorage iHeatStorage = entry.getValue();
//                heatLeftToGive -= iHeatStorage.receiveHeat(heatLeftToGive, false);
//            }
//
//            // check if any heat applied to entity otherwise do nothing
//            if (heatLeftToGive < originalHeatToGive) {
//                if (allPresent) {
//                    event.setCanceled(true);
//                }
//
//                if (heatLeftToGive > 0) {
//                    player.hurt(new OverheatDamage(player.level().registryAccess()).overheat(), (float) heatLeftToGive);
//                }
//            }
//        }
//    }

    static Map<EquipmentSlot, IHeatStorage> getHeatCapMap (LivingEntity entity) {
        Map<EquipmentSlot, IHeatStorage> capMap = new HashMap<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = ItemUtils.getItemFromEntitySlot(entity, slot);
            IHeatStorage cap = stack.getCapability(NuminaCapabilities.HEAT).resolve().orElse(null);
            if(cap != null) {
                capMap.put(slot, cap);
            }
        }
        return capMap;
    }

    static boolean allPresent(Map<EquipmentSlot, IHeatStorage> heatCapMap) {
        return heatCapMap.containsKey(EquipmentSlot.HEAD) &&
                heatCapMap.containsKey(EquipmentSlot.CHEST) &&
                heatCapMap.containsKey(EquipmentSlot.LEGS) &&
                heatCapMap.containsKey(EquipmentSlot.FEET);
    }

    public static double heatItem(@Nonnull ItemStack stack, double value) {
        IHeatStorage iHeatStorage = stack.getCapability(NuminaCapabilities.HEAT).resolve().orElse(null);
        if(iHeatStorage != null) {
            return iHeatStorage.receiveHeat(value, false);
        }
        return 0;
    }

    public static double coolItem(@Nonnull ItemStack stack, double value) {
        IHeatStorage iHeatStorage = stack.getCapability(NuminaCapabilities.HEAT).resolve().orElse(null);
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