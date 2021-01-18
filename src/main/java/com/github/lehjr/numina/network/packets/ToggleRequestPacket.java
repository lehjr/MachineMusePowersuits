/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.network.packets;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.Supplier;

public class ToggleRequestPacket {
    ResourceLocation registryName;
    boolean toggleval;

    public ToggleRequestPacket(ResourceLocation registryName, boolean active) {
        this.registryName = registryName;
        this.toggleval = active;
    }

    public static void encode(ToggleRequestPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeString(msg.registryName.toString());
        packetBuffer.writeBoolean(msg.toggleval);
    }

    public static ToggleRequestPacket decode(PacketBuffer packetBuffer) {
        return new ToggleRequestPacket(
                new ResourceLocation(packetBuffer.readString(500)),
                packetBuffer.readBoolean()
        );
    }

    public static void handle(ToggleRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity player = ctx.get().getSender();

        if (player == null || player.getServer() == null)
            return;

        ctx.get().enqueueWork(()-> {
            ResourceLocation registryName = message.registryName;
            boolean toggleval = message.toggleval;

            if (player == null)
                return;

            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                player.inventory.getStackInSlot(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler ->{
                    if (handler instanceof IModularItem) {
                        ((IModularItem) handler).toggleModule(registryName, toggleval);
                    }
                });
            }
            player.inventory.markDirty();

        });
        ctx.get().setPacketHandled(true);
    }
}