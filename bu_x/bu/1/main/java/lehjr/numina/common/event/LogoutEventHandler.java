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

package com.lehjr.numina.common.event;

import com.lehjr.numina.common.capabilities.module.powermodule.IConfig;
import com.lehjr.numina.common.config.ModuleConfig;
import com.lehjr.numina.common.config.NuminaSettings;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Objects;

public class LogoutEventHandler {
    @SubscribeEvent
    public void onPlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
//        Player player = event.getEntity();
//        if (player != null) {
//            player.getCapability(NuminaCapabilities.PLAYER_HAND_STORAGE).ifPresent(hs->{
//                CompoundTag tag = hs.serialize();
//                NuminaPackets.sendFromServerToClient(player, new PlayerHandStorageSyncResponseClientBound(tag));
//            });
//        }
    }

    /**
     * Note that the inventory here is just restored before the player dies.
     * This insures things like tombstone mods won't break.
     *
     * @param event
     */
    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public void onPlayerDeath(LivingDeathEvent event) {
//        LivingEntity entity = event.getEntity();
//        if (entity instanceof Player player) {
//            player.getCapability(NuminaCapabilities.PLAYER_HAND_STORAGE).ifPresent(hs->{
//
//                // offhand is TODO, "mainhand storage" is where mode changing modular item is stored where
//                // mode changing modular item is stored.
//                if (!hs.getOffHandStorage().isEmpty() || !hs.getMainHandStorage().isEmpty()) {
//                    ItemStack selected = player.getInventory().getSelected();
//                    int selectedSlot = player.getInventory().selected;
//                    ItemStack modularItemMainHand = hs.getMainHandStorage();
//
//                    // Should only be a mode changing modular item in secondary storage
//                    if(!modularItemMainHand.isEmpty()) {
//                        Optional<IModeChangingItem> modeChangingItemCap = modularItemMainHand
//                                .getCapability(ForgeCapabilities.ITEM_HANDLER)
//                                .filter(IModeChangingItem.class::isInstance)
//                                .map(IModeChangingItem.class::cast);
//
//                        // should only be a "foreign module" in the player inventory "selected" hotbar slot
//                        // whenever the selected slot is changed, the module should be put back into the modular item
//                        // and the modular item should be put back into the hotbar
//                        if (selected.getCapability(NuminaCapabilities.POWER_MODULE)
//                                .filter(IOtherModItemsAsModules.class::isInstance)
//                                .map(IOtherModItemsAsModules.class::cast).isPresent()) {
//
//                            // put the modified module back in the selected slot
//                            modeChangingItemCap.ifPresent(mcic -> {
//                                ItemStack modeItem = mcic.getActiveModule();
//                                if (modeItem.getItem() == selected.getItem()) {
//                                    mcic.setStackInSlot(mcic.getActiveMode(), selected);
//                                }
//                            });
//                            player.getInventory().setItem(selectedSlot, modularItemMainHand);
//                            player.inventoryMenu.broadcastChanges();
//                        }
//                    }
//
//
//                    // Offhand storage
//                    ItemStack modularItemOffHand = hs.getOffHandStorage();
//                }
//            });
//        }
    }


    // server side since server is null from client side

    // TODO: better way to do this since every mod using the lib has to do the same thing
    @SubscribeEvent
    public void onPlayerLogoutCommon(PlayerEvent.PlayerLoggedOutEvent event) {
        IConfig moduleConfig = NuminaSettings.getModuleConfig();
        if (event.getEntity() != null) {
            MinecraftServer server = event.getEntity().getServer();
            if (server != null && server.isSingleplayer() || Objects.requireNonNull(server).isSingleplayerOwner(event.getEntity().getGameProfile())) {
                if (moduleConfig instanceof ModuleConfig) {
                    ((ModuleConfig) moduleConfig).writeMissingConfigValues();
                }
            }
        }
    }
}
