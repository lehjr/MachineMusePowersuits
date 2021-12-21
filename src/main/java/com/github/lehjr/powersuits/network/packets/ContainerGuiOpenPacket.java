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

import com.github.lehjr.powersuits.container.InstallSalvageContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

/**
 * A packet for sending a containerGui open request from the client side.
 */
public class ContainerGuiOpenPacket {
    EquipmentSlotType type;
    public ContainerGuiOpenPacket(EquipmentSlotType typeIn) {
        this.type = typeIn;
    }

    public static void write(ContainerGuiOpenPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeEnum(msg.type);
    }

    public static ContainerGuiOpenPacket read(PacketBuffer packetBuffer) {
        return new ContainerGuiOpenPacket(packetBuffer.readEnum(EquipmentSlotType.class));
    }

    public static void handle(ContainerGuiOpenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            INamedContainerProvider container = new SimpleNamedContainerProvider((id, inventory, player) -> new InstallSalvageContainer(id, inventory, msg.type), new TranslationTextComponent("gui.powersuits.tab.install.salvage"));
            NetworkHooks.openGui(ctx.get().getSender(), container, buffer -> buffer.writeEnum(msg.type));
        });
        ctx.get().setPacketHandled(true);
    }
}