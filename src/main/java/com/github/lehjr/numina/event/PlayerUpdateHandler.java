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

package com.github.lehjr.numina.event;

import com.github.lehjr.numina.basemod.MuseLogger;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.heat.MuseHeatUtils;
import com.github.lehjr.numina.util.player.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

public class PlayerUpdateHandler {
    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();

            NonNullList<ItemStack> modularItems = NonNullList.create();
            for (EquipmentSlotType slot : EquipmentSlotType.values()) {
                if(player.getItemBySlot(slot).isEmpty()) {
                    continue;
                }

                switch (slot.getType()) {
                    case HAND:
                        player.getItemBySlot(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(i-> {
                            if (i instanceof IModeChangingItem) {
                                ((IModeChangingItem) i).tick(player);
                                modularItems.add(((IModeChangingItem) i).getModularItemStack());
                            }
                        });
                        break;

                    case ARMOR:

                        try {
                            player.getItemBySlot(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(i-> {
                                if (i instanceof IModularItem) {
                                    ((IModularItem) i).tick(player);
                                    modularItems.add(((IModularItem) i).getModularItemStack());
                                }
                            });
                        } catch (Exception exception) {
                            MuseLogger.logException(player.getItemBySlot(slot).toString(), exception);
                        }
                        break;
                }
            }

            //  Done this way so players can let their stuff cool in their inventory without having to equip it,
            // allowing it to cool off enough to not take damage
            if (modularItems.size() > 0) {
                // Heat update
                double currHeat = MuseHeatUtils.getPlayerHeat(player);

                if (currHeat >= 0 && !player.level.isClientSide) { // only apply serverside so change is not applied twice

                    // cooling value adjustment. Too much or too little cooling makes the heat system useless.
                    double coolPlayerAmount = (PlayerUtils.getPlayerCoolingBasedOnMaterial(player) * 0.55);  // cooling value adjustment. Too much or too little cooling makes the heat system useless.

                    if (coolPlayerAmount > 0) {
                        MuseHeatUtils.coolPlayer(player, coolPlayerAmount);
                    }

                    double maxHeat = MuseHeatUtils.getPlayerMaxHeat(player);

                    if (currHeat < maxHeat * 0.95) {
                        player.clearFire();
                    }
                }
            }
        }
    }

    /**
     * Use this instead of the above method.
     * * @param event
     */
    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void entityAttackEventHandler(LivingAttackEvent event) {
        // if damage is from overheat damage, just let it happen
        if (event.getSource() == MuseHeatUtils.overheatDamage) {
            return;
        }

        if (event.getSource().isFire()) {
            MuseHeatUtils.heatEntity(event);
        }
    }
}
