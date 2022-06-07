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

package com.lehjr.powersuits.common.network.packets;//package lehjr.powersuits.network.packets;
//
//import lehjr.mpalib.capabilities.render.ModelSpecNBT;
//import lehjr.mpalib.capabilities.render.CapabilityModelSpec;
//import net.minecraft.entity.player.ServerPlayer;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraftforge.fml.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public class MusePacketCosmeticPreset {
//    protected int itemSlot;
//    protected String presetName;
//
//    public MusePacketCosmeticPreset() {
//    }
//
//    public MusePacketCosmeticPreset(int itemSlot, String presetName) {
//        this.itemSlot = itemSlot;
//        this.presetName = presetName;
//    }
//
//    public static void encode(MusePacketCosmeticPreset msg, FriendlyByteBuf packetBuffer) {
//        packetBuffer.writeInt(msg.itemSlot);
//        packetBuffer.writeString(msg.presetName);
//    }
//
//    public static MusePacketCosmeticPreset decode(FriendlyByteBuf packetBuffer) {
//        return new MusePacketCosmeticPreset(packetBuffer.readInt(), packetBuffer.readString(500));
//    }
//
//    public static void handle(MusePacketCosmeticPreset message, Supplier<NetworkEvent.Context> ctx) {
//        ctx.get().enqueueWork(() -> {
//            final ServerPlayer player = ctx.get().getSender();
//            int itemSlot = message.itemSlot;
//            String presetName = message.presetName;
//
//            player.getInventory().getStackInSlot(itemSlot).getCapability(CapabilityModelSpec.RENDER).ifPresent(render-> {
//                render.getRenderTag();
//
//
//                        // fixme preset stuff again
////                    CompoundTag itemTag = NBTUtils.getMuseItemTag(stack);
////                    itemTag.remove(MPALibConstants.RENDER);
////                    itemTag.putString(MPALibConstants.TAG_COSMETIC_PRESET, presetName);
//            });
//        });
//    }
//}