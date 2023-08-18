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

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.network.NuminaPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

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

    public static void encode(ModeChangeRequestPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(mode);
        packetBuffer.writeInt(slot);
    }

    public static ModeChangeRequestPacket decode(FriendlyByteBuf packetBuffer) {
        return new ModeChangeRequestPacket(
                packetBuffer.readInt(),
                packetBuffer.readInt()
        );
    }

    public static void sendToClient(ServerPlayer entity, int mode, int slot) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new ModeChangeRequestPacket(mode, slot));
    }

    public static void handle(ModeChangeRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
        final Player player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            int slot = ModeChangeRequestPacket.slot;
            int mode = ModeChangeRequestPacket.mode;
            if (slot > -1 && slot < 9) {
                player.getInventory().items.get(slot).getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(handler -> handler.setActiveMode(mode));
                if (player instanceof ServerPlayer) {
                    sendToClient((ServerPlayer) player, mode, slot);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}