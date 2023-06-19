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

package lehjr.numina.common.event;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.heat.HeatUtils;
import lehjr.numina.common.player.PlayerUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerUpdateHandler {




    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        // switched from LivingEvent because it fires multiple times per tick causing major issues

//            LivingEvent event) {
//        if (event.getEntity() instanceof Player) {
//            Player player = (Player) event.getEntity();
            if(true) {
                Player player = event.player;
            NonNullList<ItemStack> modularItems = NonNullList.create();
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if(player.getItemBySlot(slot).isEmpty()) {
                    continue;
                }

                switch (slot.getType()) {
                    case HAND:
                        player.getItemBySlot(slot).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                .filter(IModeChangingItem.class::isInstance)
                                .map(IModeChangingItem.class::cast)
                                .ifPresent(i-> {
                                    i.tick(player);
                                    modularItems.add(i.getModularItemStack());
                                });
                        break;

                    case ARMOR:

                        try {
                            player.getItemBySlot(slot).getCapability(ForgeCapabilities.ITEM_HANDLER)
                                    .filter(IModularItem.class::isInstance)
                                    .map(IModularItem.class::cast)
                                    .ifPresent(i-> {
                                    i.tick(player);
                                    modularItems.add(i.getModularItemStack());
                                    });
                        } catch (Exception exception) {
                            NuminaLogger.logException(player.getItemBySlot(slot).toString(), exception);
                        }
                        break;
                }
            }

            //  Done this way so players can let their stuff cool in their inventory without having to equip it,
            // allowing it to cool off enough to not take damage
            if (modularItems.size() > 0) {
                // Heat update
                double currHeat = HeatUtils.getPlayerHeat(player);

                if (currHeat >= 0 && !player.level.isClientSide) { // only apply serverside so change is not applied twice

                    // cooling value adjustment. Too much or too little cooling makes the heat system useless.
                    double coolPlayerAmount = (PlayerUtils.getPlayerCoolingBasedOnMaterial(player) * 0.55);  // cooling value adjustment. Too much or too little cooling makes the heat system useless.

                    if (coolPlayerAmount > 0) {
                        HeatUtils.coolPlayer(player, coolPlayerAmount);
                    }

                    double maxHeat = HeatUtils.getPlayerMaxHeat(player);

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
        if (event.getSource() == HeatUtils.overheatDamage) {
            return;
        }

        if (event.getSource().isFire()) {
            HeatUtils.heatEntity(event);
        }
    }
}
