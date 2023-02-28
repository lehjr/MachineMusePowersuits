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

package lehjr.numina.common.network.packets;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleRequestPacket {
    ResourceLocation registryName;
    boolean toggleval;

    public ToggleRequestPacket(ResourceLocation registryName, boolean active) {
        this.registryName = registryName;
        this.toggleval = active;
    }

    public static void encode(ToggleRequestPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeUtf(msg.registryName.toString());
        packetBuffer.writeBoolean(msg.toggleval);
    }

    public static ToggleRequestPacket decode(FriendlyByteBuf packetBuffer) {
        return new ToggleRequestPacket(
                new ResourceLocation(packetBuffer.readUtf(500)),
                packetBuffer.readBoolean()
        );
    }

    public static void handle(ToggleRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayer player = ctx.get().getSender();

        if (player == null || player.getServer() == null)
            return;

        ctx.get().enqueueWork(()-> {
            ResourceLocation registryName = message.registryName;
            boolean toggleval = message.toggleval;

            if (player == null)
                return;

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                player.getInventory().getItem(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
            }
            player.getInventory().setChanged();
        });
        ctx.get().setPacketHandled(true);
    }
}