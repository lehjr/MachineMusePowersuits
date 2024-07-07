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

import lehjr.numina.common.capability.module.powermodule.IConfig;
import lehjr.numina.common.config.ModuleConfig;
import lehjr.powersuits.common.config.MPSSettings;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LoginLogoutEventHandler {
    // server side since server is null from client side
    @SubscribeEvent
    public void OnPlayerLogoutCommon(PlayerEvent.PlayerLoggedOutEvent event) {
        IConfig moduleConfig = MPSSettings.getModuleConfig();
        if (event.getEntity() != null) {
            MinecraftServer server = event.getEntity().getServer();
            if (server != null && server.isSingleplayer() || server.isSingleplayerOwner(event.getEntity().getGameProfile())) {
                if (moduleConfig instanceof ModuleConfig) {
//                    NuminaLogger.logDebug("write missing values");
                    ((ModuleConfig) moduleConfig).writeMissingConfigValues();
                }
            }
        }
    }

    // LOOKS like this only fired server side
//    @SubscribeEvent
//    public void onPlayerLoginClient(PlayerEvent.PlayerLoggedInEvent event) {
//
//        Player player = event.getEntity();
//        if (player != null) {
////            NonNullList<ItemStack> modules = NonNullList.create();
////            for (Item item : ForgeRegistries.ITEMS.getValues()) {
////                    new ItemStack(item).getCapability(NuminaCapabilities.POWER_MODULE)
////                            .filter(IToggleableModule.class::isInstance)
////                            .map(IToggleableModule.class::cast)
////                            .ifPresent(pm -> {
////                                // Tool settings are a bit odd
////                                if (pm.getTarget() == ModuleTarget.TOOLONLY) {
////                                    if (pm.getCategory() == ModuleCategory.MINING_ENHANCEMENT) {
////                                        modules.add(pm.getModule());
////                                        KeybindKeyHandler.registerKeybinding(ItemUtils.getRegistryName(item), false);
////                                    } else if (!IRightClickModule.class.isAssignableFrom(pm.getClass())) {
////                                        modules.add(pm.getModule());
////                                        KeybindKeyHandler.registerKeybinding(ItemUtils.getRegistryName(item), false);
////                                    }
////                                } else {
////                                    modules.add(pm.getModule());
////                                    KeybindKeyHandler.registerKeybinding(ItemUtils.getRegistryName(item), false);
////                                }
////                            });
////                }
////            KeybindManager.INSTANCE.readInKeybinds(true);
//        }
//    }
}