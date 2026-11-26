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

import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.clientbound.ToggleableModuleListClientBound;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

/**
 * Created by Claire Semple on 9/8/2014.
 * <p>
 * Ported to Java by lehjr on 10/24/16.
 */
public class PlayerEventHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
//        for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
//            ItemStack module = new ItemStack(item);
//            IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
//            if (pm != null && pm.isAllowed() && pm instanceof IToggleableModule) {
//                moduleNames.add(ItemUtils.getRegistryName(item));
//            }
//        }

        // NOT empty and NOT client side
        if(!(AttachCapabilityEventHandler.toggleable.isEmpty() && player.level().isClientSide)) {
            MPSPackets.sendToPlayer(new ToggleableModuleListClientBound(AttachCapabilityEventHandler.toggleable), (ServerPlayer) player);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            MovementManager.playerMovementTick(event.player, event.player.level());
        }
    }
}