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
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.Supplier;

public class TweakRequestDoublePacket {
    protected int itemSlot;
    protected ResourceLocation moduleName;
    protected String tweakName;
    protected double tweakValue;

    public TweakRequestDoublePacket() {

    }

    public TweakRequestDoublePacket(int itemSlot, ResourceLocation moduleRegName, String tweakName, double tweakValue) {
        this.itemSlot = itemSlot;
        this.moduleName = moduleRegName;
        this.tweakName = tweakName;
        this.tweakValue = tweakValue;
    }

    public static void encode(TweakRequestDoublePacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeInt(msg.itemSlot);
        packetBuffer.writeResourceLocation(msg.moduleName);
        packetBuffer.writeString(msg.tweakName);
        packetBuffer.writeDouble(msg.tweakValue);
    }

    public static TweakRequestDoublePacket decode(PacketBuffer packetBuffer) {
        return new TweakRequestDoublePacket(
                packetBuffer.readInt(),
                packetBuffer.readResourceLocation(),
                packetBuffer.readString(500),
                packetBuffer.readDouble());
    }

    public static void handle(TweakRequestDoublePacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity player = ctx.get().getSender();

            int itemSlot = message.itemSlot;
            ResourceLocation moduleName = message.moduleName;
            String tweakName = message.tweakName;
            double tweakValue = message.tweakValue;

            if (moduleName != null && tweakName != null) {
                ItemStack stack = player.inventory.getStackInSlot(itemSlot);
                stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                    if (iItemHandler instanceof IModularItem) {
                        ((IModularItem) iItemHandler).setModuleTweakDouble(moduleName, tweakName, tweakValue);
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}