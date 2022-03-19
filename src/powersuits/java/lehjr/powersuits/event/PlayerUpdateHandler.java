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

package lehjr.powersuits.event;

import lehjr.numina.config.NuminaSettings;
import lehjr.numina.util.client.sound.Musique;
import lehjr.numina.util.math.MuseMathUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.item.armor.PowerArmorBoots;
import lehjr.powersuits.item.armor.PowerArmorChestplate;
import lehjr.powersuits.item.armor.PowerArmorLeggings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Created by Claire Semple on 9/8/2014.
 * <p>
 * Ported to Java by lehjr on 10/24/16.
 */
public class PlayerUpdateHandler {
    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();

            // pretty sure the whole point of this was to reduce fall distance, not increase it.
            float fallDistance = (float) MovementManager.INSTANCE.computeFallHeightFromVelocity(MuseMathUtils.clampDouble(player.getDeltaMovement().y, -1000.0, 0.0));
            if (fallDistance < player.fallDistance) {
                player.fallDistance = fallDistance;
            }

            // Sound update
            if (player.level.isClientSide && NuminaSettings.useSounds()) {
                if ((player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof PowerArmorChestplate)) {
                    double velsq2 = MuseMathUtils.sumsq(player.getDeltaMovement().x, player.getDeltaMovement().y, player.getDeltaMovement().z) - 0.5;
                    if (player.hasImpulse && velsq2 > 0) {
                        Musique.playerSound(player, MPSSoundDictionary.GLIDER, SoundCategory.PLAYERS, (float) (velsq2 / 3), 1.0f, true);
                    } else {
                        Musique.stopPlayerSound(player, MPSSoundDictionary.GLIDER);
                    }
                } else {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.GLIDER);
                }

                if (!(player.getItemBySlot(EquipmentSlotType.FEET).getItem() instanceof PowerArmorBoots)) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.JETBOOTS);
                }

                if (!(player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof PowerArmorChestplate)) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.JETPACK);
                }

                if (!(player.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof PowerArmorLeggings)) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.SWIM_ASSIST);
                }
            }
        }
    }
}