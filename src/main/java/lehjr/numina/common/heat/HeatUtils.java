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

package lehjr.numina.common.heat;

import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.item.ItemUtils;
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
    public static void heatEntity(LivingAttackEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            // round amount due do float values being weird
            double heatLeftToGive = Math.round(event.getAmount());
            final double originalHeatToGive = heatLeftToGive;

            LivingEntity entity = event.getEntity();
            boolean allPresent = true;

            for (ItemStack stack : entity.getArmorSlots()) {
                if (!stack.getCapability(NuminaCapabilities.HEAT).isPresent()) {
                    allPresent = false;
                    break;
                }
            }

            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (heatLeftToGive == 0) {
                    break;
                }
                double finalHeatLeftToGive = heatLeftToGive;
                heatLeftToGive -= ItemUtils.getItemFromEntitySlot(entity, slot)
                        .getCapability(NuminaCapabilities.HEAT).map(heat->heat.receiveHeat(finalHeatLeftToGive, false)).orElse(0D);
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
        return stack.getCapability(NuminaCapabilities.HEAT).map(h->h.getMaxHeatStored()).orElse(0D);
    }

    public static double getItemHeat(@Nonnull ItemStack stack) {
        return stack.getCapability(NuminaCapabilities.HEAT).map(h->h.getHeatStored()).orElse(0D);
    }

    public static double heatItem(@Nonnull ItemStack stack, double value) {
        return stack.getCapability(NuminaCapabilities.HEAT, null).map(h->h.receiveHeat(value, false)).orElse(0D);
    }

    public static double coolItem(@Nonnull ItemStack stack, double value) {
        return stack.getCapability(NuminaCapabilities.HEAT, null).map(h->h.extractHeat(value, false)).orElse(0D);
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