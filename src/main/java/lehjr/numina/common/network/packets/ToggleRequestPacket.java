///*
// * Copyright (c) 2021. MachineMuse, Lehjr
// *  All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// *      Redistributions of source code must retain the above copyright notice, this
// *      list of conditions and the following disclaimer.
// *
// *     Redistributions in binary form must reproduce the above copyright notice,
// *     this list of conditions and the following disclaimer in the documentation
// *     and/or other materials provided with the distribution.
// *
// *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package lehjr.numina.common.network.packets;
//
//import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
//import lehjr.numina.common.network.NuminaPackets;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.common.capabilities.ForgeCapabilities;
//import net.minecraftforge.network.NetworkEvent;
//import net.minecraftforge.network.PacketDistributor;
//
//import java.util.function.Supplier;
//
//public record ToggleRequestPacket(ResourceLocation registryName, boolean toggleval) {
//
//    public static void encode(ToggleRequestPacket msg, FriendlyByteBuf packetBuffer) {
//        packetBuffer.writeUtf(msg.registryName.toString());
//        packetBuffer.writeBoolean(msg.toggleval);
//    }
//
//    public static ToggleRequestPacket decode(FriendlyByteBuf packetBuffer) {
//        return new ToggleRequestPacket(
//                new ResourceLocation(packetBuffer.readUtf(500)),
//                packetBuffer.readBoolean()
//        );
//    }
//
//    public static void sendToClient(ServerPlayer entity, ResourceLocation registryName, boolean active) {
//        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
//                new ToggleRequestPacket(registryName, active));
//    }
//
//    public static class Handler {
//        public static void handle(ToggleRequestPacket message, Supplier<NetworkEvent.Context> ctx) {
//            final Player player = ctx.get().getSender();
//
//            if (player == null || player.getServer() == null)
//                return;
//
//            ctx.get().enqueueWork(()-> {
//                ResourceLocation registryName = message.registryName;
//                boolean toggleval = message.toggleval;
//
//                if (player == null) {
//                    return;
//                }
//
//                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
//                    player.getInventory().getItem(i).getCapability(ForgeCapabilities.ITEM_HANDLER)
//                            .filter(IModularItem.class::isInstance)
//                            .map(IModularItem.class::cast)
//                            .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
//                }
//
//            if (player instanceof ServerPlayer) {
//                sendToClient((ServerPlayer) player, registryName, toggleval);
//                NuminaLogger.logDebug("serverside send packet side handle " + registryName);
//            } else {
//                NuminaLogger.logDebug("clientside send packet side handle " + registryName);
//            }
//            });
//            NuminaLogger.logDebug("setting handled");
//            ctx.get().setPacketHandled(true);
//        }
//    }
//
//
//
//}