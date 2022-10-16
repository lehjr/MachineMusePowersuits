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
import lehjr.numina.common.capabilities.heat.HeatCapability;
import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import javax.annotation.Nonnull;

/**
 * Handler for heating and cooling.
 * Note: values can be read on either logical side, but should only be set server side
 */
public class HeatUtils {
    public static final DamageSource overheatDamage = new OverheatDamage();

    public static double getPlayerHeat(LivingEntity entity) {
        double heat = 0;
        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            heat += getItemHeat(entity.getItemBySlot(slot));
        }
        return heat;
    }

    /**
     * Should only be called server side
     */
    public static double getPlayerMaxHeat(LivingEntity entity) {
        AtomicDouble maxHeat = new AtomicDouble(0);

        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            ItemStack itemStack = entity.getItemBySlot(slot);

                if ((slot.getType().equals(EquipmentSlotType.Group.ARMOR)) ||
                        (slot.getType().equals(EquipmentSlotType.Group.HAND) && !(itemStack.getItem() instanceof ArmorItem))) {
                    itemStack.getCapability(HeatCapability.HEAT).ifPresent(heat->maxHeat.getAndAdd(heat.getMaxHeatStored()));
                }
        }
        return maxHeat.get();
    }

    public static double coolPlayer(LivingEntity entity, double coolJoules) {
        if (entity.level.isClientSide /*|| entity.abilities.instabuild */) {
            return 0;
        }

        double coolingLeft = coolJoules;
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                ItemStack stack = player.inventory.getItem(i);
                if (entity.isUsingItem() && player.inventory.getSelected() == stack) {
                    continue;
                }
                if (coolingLeft > 0) {
                    coolingLeft -= coolItem(stack, coolingLeft);
                } else {
                    break;
                }
            }
        } else {
            for (EquipmentSlotType slot : EquipmentSlotType.values()) {
                if (coolingLeft > 0) {
                    coolingLeft -= coolItem(entity.getItemBySlot(slot), coolingLeft);
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
        if (entity.level.isClientSide /*|| entity.abilities.instabuild */) {
            return 0;
        }

        double heatLeftToGive = heatJoules;
        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            if (heatLeftToGive == 0) {
                break;
            }
            heatLeftToGive = heatLeftToGive - heatItem(entity.getItemBySlot(slot), heatLeftToGive);
        }
        return heatLeftToGive;
    }

    public static void heatEntity(LivingAttackEvent event) {
        if (event.getSource().isFire()) {
            // round amount due do float values being weird
            double heatLeftToGive = Math.round(event.getAmount());
            final double originalHeatToGive = heatLeftToGive;

            LivingEntity entity = event.getEntityLiving();
            boolean allPresent = true;

            for (ItemStack stack : entity.getArmorSlots()) {
                if (!stack.getCapability(HeatCapability.HEAT).isPresent()) {
                    allPresent = false;
                    break;
                }
            }

            for (EquipmentSlotType slot : EquipmentSlotType.values()) {
                if (heatLeftToGive == 0) {
                    break;
                }
                double finalHeatLeftToGive = heatLeftToGive;
                heatLeftToGive -= entity.getItemBySlot(slot)
                        .getCapability(HeatCapability.HEAT).map(heat->heat.receiveHeat(finalHeatLeftToGive, false)).orElse(0D);
            }

            // check if any heat applied to entity otherwise do nothing
            if (heatLeftToGive < originalHeatToGive) {
                if (allPresent) {
                    event.setCanceled(true);
                }
                if (heatLeftToGive > 0) {
                    entity.hurt(new OverheatDamage(), (float) heatLeftToGive);
                }
            }
        }
    }

    public static double getItemMaxHeat(@Nonnull ItemStack stack) {
        return stack.getCapability(HeatCapability.HEAT).map(h->h.getMaxHeatStored()).orElse(0D);
    }

    public static double getItemHeat(@Nonnull ItemStack stack) {
        return stack.getCapability(HeatCapability.HEAT).map(h->h.getHeatStored()).orElse(0D);
    }

    public static double heatItem(@Nonnull ItemStack stack, double value) {
        return stack.getCapability(HeatCapability.HEAT, null).map(h->h.receiveHeat(value, false)).orElse(0D);
    }

    public static double coolItem(@Nonnull ItemStack stack, double value) {
        return stack.getCapability(HeatCapability.HEAT, null).map(h->h.extractHeat(value, false)).orElse(0D);
    }

    protected static final class OverheatDamage extends DamageSource {
        public OverheatDamage() {
            super(NuminaConstants.OVERHEAT_DAMAGE);
            this.setIsFire();
            this.bypassArmor();
        }

        public boolean equals(DamageSource other) {
            return other.msgId.equals(this.msgId);
        }
    }
}