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

import lehjr.numina.common.network.NuminaPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

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

    public static void encode(CreativeInstallModuleRequestPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.windowId);
        packetBuffer.writeInt(msg.slotId);
        packetBuffer.writeItem(msg.itemStack);
    }

    public static CreativeInstallModuleRequestPacket decode(FriendlyByteBuf packetBuffer) {
        return new CreativeInstallModuleRequestPacket(
                packetBuffer.readInt(),
                packetBuffer.readInt(),
                packetBuffer.readItem());
    }

    // Note: only doing this because it "looks" to be more efficient
    public static void sendToClient(ServerPlayer entity, int windowIdIn, int slotIdIn, @Nonnull ItemStack itemStackIn) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new CreativeInstallModuleRequestPacket(windowIdIn, slotIdIn, itemStackIn));
    }

    public static void handle(CreativeInstallModuleRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = ctx.get().getSender();
            int slotId = message.slotId;
            int windowId = message.windowId;
            ItemStack itemStack = message.itemStack;


            if (player.containerMenu != null && player.containerMenu.containerId == windowId) {
                player.getInventory().setItem(slotId, itemStack);
//                player.containerMenu.broadcastChanges();
                if (player instanceof ServerPlayer) {
                    sendToClient((ServerPlayer) player, windowId, slotId, itemStack);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}