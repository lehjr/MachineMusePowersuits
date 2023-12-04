//package lehjr.numina.common.network.packets.serverbound;
//
//import lehjr.numina.common.capabilities.NuminaCapabilities;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public class PlayerHandStorageSyncRequestServerBound {
//    public PlayerHandStorageSyncRequestServerBound() {
//    }
//
//    public static void encode(PlayerHandStorageSyncRequestServerBound msg, FriendlyByteBuf packetBuffer) {
//    }
//
//    public static PlayerHandStorageSyncRequestServerBound decode(FriendlyByteBuf packetBuffer) {
//        return new PlayerHandStorageSyncRequestServerBound();
//    }
//
//    public static void sendToClient(ServerPlayer entity, ItemStack mainHand, ItemStack offHand) {
//        System.out.println("FIXME!!");
//
////        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
////                new PlayerHandStorageSyncResponseClientBound(mainHand, offHand));
//    }
//
//    public static class Handler {
//        public static void handle(PlayerHandStorageSyncRequestServerBound message, Supplier<NetworkEvent.Context> ctx) {
//            ctx.get().enqueueWork(() -> {
//                final ServerPlayer player = ctx.get().getSender();
//                if (player != null) {
//                    player.getCapability(NuminaCapabilities.PLAYER_HAND_STORAGE).ifPresent(iPlayerHandStorage -> {
//                        ItemStack mainHandItem = iPlayerHandStorage.getMainHandStorage();
//                        ItemStack offHandItem = iPlayerHandStorage.getOffHandStorage();
//                        sendToClient(player, mainHandItem, offHandItem);
//                    });
//                }
//
//            });
//            ctx.get().setPacketHandled(true);
//        }
//    }
//}
