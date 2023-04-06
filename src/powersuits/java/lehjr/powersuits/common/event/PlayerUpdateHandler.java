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

package lehjr.powersuits.common.event;

import lehjr.numina.client.sound.Musique;
import lehjr.numina.common.config.NuminaSettings;
import lehjr.numina.common.math.MathUtils;
import lehjr.powersuits.client.sound.MPSSoundDictionary;
import lehjr.powersuits.common.item.armor.PowerArmorBoots;
import lehjr.powersuits.common.item.armor.PowerArmorChestplate;
import lehjr.powersuits.common.item.armor.PowerArmorLeggings;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
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
    public void onPlayerUpdate(LivingEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // pretty sure the whole point of this was to reduce fall distance, not increase it.
            float fallDistance = (float) MovementManager.INSTANCE.computeFallHeightFromVelocity(MathUtils.clampDouble(player.getDeltaMovement().y, -1000.0, 0.0));
            if (fallDistance < player.fallDistance) {
                player.fallDistance = fallDistance;
            }

            // Sound update
            if (player.level.isClientSide && NuminaSettings.useSounds()) {
                if ((player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof PowerArmorChestplate)) {
                    double velsq2 = MathUtils.sumsq(player.getDeltaMovement().x, player.getDeltaMovement().y, player.getDeltaMovement().z) - 0.5;
                    if (player.hasImpulse && velsq2 > 0) {
                        Musique.playerSound(player, MPSSoundDictionary.GLIDER.get(), SoundSource.PLAYERS, (float) (velsq2 / 3), 1.0f, true);
                    } else {
                        Musique.stopPlayerSound(player, MPSSoundDictionary.GLIDER.get());
                    }
                } else {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.GLIDER.get());
                }

                if (!(player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof PowerArmorBoots)) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.JETBOOTS.get());
                }

                if (!(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof PowerArmorChestplate)) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.JETPACK.get());
                }

                if (!(player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof PowerArmorLeggings)) {
                    Musique.stopPlayerSound(player, MPSSoundDictionary.SWIM_ASSIST.get());
                }
            }
        }
    }
}