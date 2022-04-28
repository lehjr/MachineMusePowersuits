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

package com.lehjr.numina.common.network.packets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent;
import com.lehjr.numina.api.capabilities.render.CapabilityModelSpec;

import java.util.function.Supplier;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:28 PM, 5/6/13
 * <p>
 * Ported to Java by lehjr on 11/14/16.
 */
public class CosmeticInfoPacket {
    protected EquipmentSlot slotType;
    protected String tagName;
    protected CompoundTag tagData;

    public CosmeticInfoPacket() {
    }

    public CosmeticInfoPacket(EquipmentSlot slotType, String tagName, CompoundTag tagData) {
        this.slotType = slotType;
        this.tagName = tagName;
        this.tagData = tagData;
    }

    public static void encode(CosmeticInfoPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeUtf(msg.tagName);
        packetBuffer.writeNbt(msg.tagData);
    }

    public static CosmeticInfoPacket decode(FriendlyByteBuf packetBuffer) {
        return new CosmeticInfoPacket(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readUtf(500),
                packetBuffer.readNbt());
    }

    public static void handle(CosmeticInfoPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayer player = ctx.get().getSender();
            EquipmentSlot slotType = message.slotType;
            String tagName = message.tagName;
            CompoundTag tagData = message.tagData;
            player.getItemBySlot(slotType).getCapability(CapabilityModelSpec.RENDER).ifPresent(render-> {
                render.setRenderTag(tagData, tagName);
            });
            player.getInventory().setChanged();
        });
        ctx.get().setPacketHandled(true);
    }
}