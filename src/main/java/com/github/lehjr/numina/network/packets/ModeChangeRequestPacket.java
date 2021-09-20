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

package com.github.lehjr.numina.network.packets;

import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.Supplier;

public class ModeChangeRequestPacket {
    protected static int mode;
    protected static int slot;

    public ModeChangeRequestPacket() {
    }

    public ModeChangeRequestPacket(int mode, int slot) {
        ModeChangeRequestPacket.mode = mode;
        ModeChangeRequestPacket.slot = slot;
    }

    public static void encode(ModeChangeRequestPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeInt(mode);
        packetBuffer.writeInt(slot);
    }

    public static ModeChangeRequestPacket decode(PacketBuffer packetBuffer) {
        return new ModeChangeRequestPacket(
                packetBuffer.readInt(),
                packetBuffer.readInt()
        );
    }

    public static void handle(ModeChangeRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            int slot = ModeChangeRequestPacket.slot;
            int mode = ModeChangeRequestPacket.mode;
            if (slot > -1 && slot < 9) {
                player.inventory.items.get(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(handler -> handler.setActiveMode(mode));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}