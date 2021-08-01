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

import com.github.lehjr.powersuits.container.TinkerTableContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoveModuleFromSlotToSlotPacket {
    int windowId;
    int sourceSlot;
    int targetSlot;

    public MoveModuleFromSlotToSlotPacket(int windowIdIn, int sourceSlotIn, int targetSlotIn) {
        this.windowId = windowIdIn;
        this.sourceSlot = sourceSlotIn;
        this.targetSlot = targetSlotIn;
    }

    public static void write(MoveModuleFromSlotToSlotPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeInt(msg.windowId);
        packetBuffer.writeInt(msg.sourceSlot);
        packetBuffer.writeInt(msg.targetSlot);
    }

    public static MoveModuleFromSlotToSlotPacket read(PacketBuffer packetBuffer) {
        return new MoveModuleFromSlotToSlotPacket(packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt());
    }

    public static void handle(MoveModuleFromSlotToSlotPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player.containerMenu != null && player.containerMenu.containerId == message.windowId && player.containerMenu instanceof TinkerTableContainer) {
                TinkerTableContainer container = (TinkerTableContainer) player.containerMenu;

                Slot source = container.getSlot(message.sourceSlot);
                Slot target = container.getSlot(message.targetSlot);

                ItemStack itemStack = source.getItem();
                ItemStack stackCopy = itemStack.copy();
                // fixme: no idea if this will work with target set as range
//                if (source instanceof CraftingResultSlot && source.getHasStack()) {
//                    container.consume(player);
//                    if (container.mergeItemStack(itemStack, message.targetSlot, message.targetSlot + 1, false)) {
////                        source.onSlotChange(itemStack, stackCopy);
//                        source.onTake(player, itemStack);
//                    }
//                } else

                {
                    target.set(stackCopy);
                    source.remove(1);
                }
                player.containerMenu.broadcastChanges();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}