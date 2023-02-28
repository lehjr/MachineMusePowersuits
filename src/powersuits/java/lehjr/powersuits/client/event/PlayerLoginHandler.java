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

package lehjr.powersuits.client.event;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.powersuits.client.control.KeybindManager;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import static lehjr.powersuits.client.control.KeybindKeyHandler.registerKeybinding;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:01 PM, 12/05/13
 * <p>
 * Ported to Java by lehjr on 10/24/16.
 */
public final class PlayerLoginHandler {
    @SubscribeEvent
    public static void onPlayerLoginClient(ClientPlayerNetworkEvent.LoggedInEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            NonNullList<ItemStack> modules = NonNullList.create();
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                    new ItemStack(item).getCapability(PowerModuleCapability.POWER_MODULE)
                            .filter(IToggleableModule.class::isInstance)
                            .map(IToggleableModule.class::cast)
                            .ifPresent(pm -> {
                                // Tool settings are a bit odd
                                if (pm.getTarget() == ModuleTarget.TOOLONLY) {
                                    if (pm.getCategory() == ModuleCategory.MINING_ENHANCEMENT) {
                                        modules.add(pm.getModuleStack());
                                        registerKeybinding(item.getRegistryName(), false);
                                    } else if (!IRightClickModule.class.isAssignableFrom(pm.getClass())) {
                                        modules.add(pm.getModuleStack());
                                        registerKeybinding(item.getRegistryName(), false);
                                    }
                                } else {
                                    modules.add(pm.getModuleStack());
                                    registerKeybinding(item.getRegistryName(), false);
                                }
                            });
                }
            KeybindManager.INSTANCE.readInKeybinds(true);
        }
    }
}