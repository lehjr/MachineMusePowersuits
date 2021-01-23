package com.github.lehjr.powersuits.event;

import com.github.lehjr.powersuits.client.control.KeybindManager;
import com.github.lehjr.powersuits.network.MPAPackets;
import com.github.lehjr.powersuits.network.packets.OnClientLoginPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:01 PM, 12/05/13
 * <p>
 * Ported to Java by lehjr on 10/24/16.
 */
public final class PlayerLoginHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            MPAPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) player), new OnClientLoginPacket());
        }
    }



//        boolean isUsingBuiltInServer = FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer();
//
//        // dedidated server or multiplayer game
//        if (!isUsingBuiltInServer || (isUsingBuiltInServer && FMLCommonHandler.instance().getMinecraftServerInstance().getCurrentPlayerCount() > 1)) {
//            // sync config settings between client and server
//            MPSPackets.sendTo(new MPSPacketConfig(), (EntityPlayerMP) player);
//        } else {
//            MPSSettings.loadCustomInstallCosts();
//        }



    public static void clientPlayerLogin(PlayerEntity player) {
        KeybindManager.INSTANCE.readInKeybinds();
    }
}