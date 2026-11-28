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

package lehjr.powersuits.common.network.packets.serverbound;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.container.InstallSalvageMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

/**
 * A packet for sending a containerGui open request from the client side.
 */
public record ContainerGuiOpenPacketServerBound(EquipmentSlot slotType, boolean shouldpreserve, double mouseX, double mouseY) {

    public static void write(ContainerGuiOpenPacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeBoolean(msg.shouldpreserve);
        packetBuffer.writeDouble(msg.mouseX);
        packetBuffer.writeDouble(msg.mouseY);
    }

    public static ContainerGuiOpenPacketServerBound read(FriendlyByteBuf packetBuffer) {
        return new ContainerGuiOpenPacketServerBound(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readBoolean(), packetBuffer.readDouble(),  packetBuffer.readDouble());
    }

    public static void handle(ContainerGuiOpenPacketServerBound msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            SimpleMenuProvider container = new SimpleMenuProvider((id, inventory, playerLamda) ->  new InstallSalvageMenu(id, inventory, msg.slotType), Component.translatable(MPSConstants.GUI_INSTALL_SALVAGE));
            assert player != null;
            NetworkHooks.openScreen(player, container, buffer -> {
                buffer.writeEnum(msg.slotType);
                buffer.writeBoolean(msg.shouldpreserve);
                buffer.writeDouble(msg.mouseX);
                buffer.writeDouble(msg.mouseY);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}