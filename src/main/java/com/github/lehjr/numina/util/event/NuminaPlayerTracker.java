//package github.com.lehjr.numina.util.event;
//
//import github.com.lehjr.network.NuminaPackets;
//import github.com.lehjr.network.packets.NuminaPacketConfig;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraftforge.fml.common.FMLCommonHandler;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.PlayerEvent;
//
///**
// * Author: MachineMuse (Claire Semple)
// * Created: 11:57 AM, 9/3/13
// * <p>
// * Ported to Java by lehjr on 10/26/16.
// */

// FIXME:  NO LONGER USED. Synced by Forge now


//public final class NuminaPlayerTracker {
//    @SubscribeEvent
//    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
//        PlayerEntity player = event.player;
//        boolean isUsingBuiltInServer = FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer();
//
//        // dedidated server or multiplayer game
//        if (!isUsingBuiltInServer || (isUsingBuiltInServer && FMLCommonHandler.instance().getMinecraftServerInstance().getCurrentPlayerCount() > 1)) {
//
//            // sync config settings between client and server
//            NuminaPackets.sendTo(new NuminaPacketConfig(), (EntityPlayerMP) player);
//        }
//    }
//}