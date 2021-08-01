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

package com.github.lehjr.powersuits.network.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * creates a new itemstack on both client and server side
 */
public class CreativeInstallModuleRequestPacket {
    int windowId;
    int slotId;
    ItemStack itemStack;

    public CreativeInstallModuleRequestPacket(int windowIdIn, int slotIdIn, @Nonnull ItemStack itemStackIn) {
        windowId = windowIdIn;
        slotId = slotIdIn;
        itemStack = itemStackIn;
    }

    public static void encode(CreativeInstallModuleRequestPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeInt(msg.windowId);
        packetBuffer.writeInt(msg.slotId);
        packetBuffer.writeItem(msg.itemStack);
    }

    public static CreativeInstallModuleRequestPacket decode(PacketBuffer packetBuffer) {
        return new CreativeInstallModuleRequestPacket(
                packetBuffer.readInt(),
                packetBuffer.readInt(),
                packetBuffer.readItem());
    }


    public static void handle(CreativeInstallModuleRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player.containerMenu != null && player.containerMenu.containerId == message.windowId) {
                player.containerMenu.setItem(message.slotId, message.itemStack);
//                player.openContainer.detectAndSendChanges();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}