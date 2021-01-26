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

import com.github.lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:28 PM, 5/6/13
 * <p>
 * Ported to Java by lehjr on 11/14/16.
 */
public class CosmeticInfoPacket {
    protected static int itemSlot;
    protected String tagName;
    protected CompoundNBT tagData;

    public CosmeticInfoPacket() {
    }

    public CosmeticInfoPacket(int itemSlot, String tagName, CompoundNBT tagData) {
        this.itemSlot = itemSlot;
        this.tagName = tagName;
        this.tagData = tagData;
    }

    public static void encode(CosmeticInfoPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeInt(msg.itemSlot);
        packetBuffer.writeString(msg.tagName);
        packetBuffer.writeCompoundTag(msg.tagData);
    }

    public static CosmeticInfoPacket decode(PacketBuffer packetBuffer) {
        return new CosmeticInfoPacket(
                packetBuffer.readInt(),
                packetBuffer.readString(500),
                packetBuffer.readCompoundTag());
    }

    public static void handle(CosmeticInfoPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity player = ctx.get().getSender();
            int itemSlot = message.itemSlot;
            String tagName = message.tagName;
            CompoundNBT tagData = message.tagData;
            player.inventory.getStackInSlot(itemSlot).getCapability(ModelSpecNBTCapability.RENDER).ifPresent(render->{
                render.setRenderTag(tagData, tagName);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}