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

package lehjr.powersuits.network.packets;//package lehjr.powersuits.network.packets;
//
//import lehjr.mpalib.basemod.MPALibLogger;
//import lehjr.mpalib.network.MuseByteBufferUtils;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.network.PacketBuffer;
//import net.minecraftforge.fml.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public class MusePacketCosmeticPresetUpdate {
//    String registryName;
//    String name;
//    CompoundNBT cosmeticSettings;
//
//    public MusePacketCosmeticPresetUpdate() {
//
//    }
//
//    public MusePacketCosmeticPresetUpdate(String registryNameIn, String nameIn, CompoundNBT cosmeticSettingsIn) {
//        this.registryName = registryNameIn;
//        this.name = nameIn;
//        this.cosmeticSettings = cosmeticSettingsIn;
//    }
//
//    public static void encode(MusePacketCosmeticPresetUpdate msg, PacketBuffer packetBuffer) {
//        packetBuffer.writeString(msg.registryName);
//        packetBuffer.writeString(msg.name);
//        MuseByteBufferUtils.writeCompressedNBT(packetBuffer, msg.cosmeticSettings);
//    }
//
//    public static MusePacketCosmeticPresetUpdate decode(PacketBuffer packetBuffer) {
//        return new MusePacketCosmeticPresetUpdate(
//                packetBuffer.readString(500),
//        packetBuffer.readString(500),
//        MuseByteBufferUtils.readCompressedNBT(packetBuffer));
//    }
//
//    public static void handle(MusePacketCosmeticPresetUpdate message, Supplier<NetworkEvent.Context> ctx) {
//        MPALibLogger.logger.error("this has not been implemented yet");
//        // FIXME !!!
//
//
////        if (ctx.side == Side.SERVER) {
////            boolean allowCosmeticPresetCreation;
////            final ServerPlayerEntity player = ctx.getServerHandler().player;
////            // check if player is the server owner
////            if (FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer()) {
////                allowCosmeticPresetCreation = player.getName().equals(FMLCommonHandler.instance().getMinecraftServerInstance().getServerOwner());
////            } else {
////                // check if player is top level op
////                UserListOpsEntry opEntry = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry(player.getGameProfile());
////                int opLevel = opEntry != null ? opEntry.getPermissionLevel() : 0;
////                allowCosmeticPresetCreation = opLevel == 4;
////            }
////            if(allowCosmeticPresetCreation) {
////                ctx.get().enqueueWork(() -> {
////                    ResourceLocation registryName = message.registryName;
////                    String name = message.name;
////                    CompoundNBT cosmeticSettings = message.cosmeticSettings;
////                    MPSServerSettings settings = MPASettings::getModuleConfig.getServerSettings();
////                    if (settings != null) {
////                        settings.updateCosmeticInfo(registryName, name, cosmeticSettings);
////                        MPSPackets.sendToAll(new MusePacketCosmeticPresetUpdate(registryName, name, cosmeticSettings));
////                    } else {
////                        MPSSettings.cosmetics.updateCosmeticInfo(registryName, name, cosmeticSettings);
////                    }
////                    if (CosmeticPresetSaveLoad.savePreset(registryName, name, cosmeticSettings))
////                        player.sendMessage(new TextComponentTranslation("gui.powersuits.savesuccessful"));
//////                        else
//////                            player.sendMessage(new TextComponentTranslation("gui.powersuits.fail"));
////                });
////            }
////        } else {
////            Minecraft.getInstance().addScheduledTask(() -> {
////                ResourceLocation registryName = message.registryName;
////                String name = message.name;
////                CompoundNBT cosmeticSettings = message.cosmeticSettings;
////                MPSServerSettings settings = MPASettings::getModuleConfig.getServerSettings();
////                settings.updateCosmeticInfo(registryName, name, cosmeticSettings);
////            });
////        }
////        return null;
//    }
//}