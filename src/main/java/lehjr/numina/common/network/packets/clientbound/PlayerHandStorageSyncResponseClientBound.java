//package lehjr.numina.common.network.packets.clientbound;
//
//import lehjr.numina.common.capabilities.NuminaCapabilities;
//import net.minecraft.client.Minecraft;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public record PlayerHandStorageSyncResponseClientBound(CompoundTag tag) {
//    public static void encode(PlayerHandStorageSyncResponseClientBound msg, FriendlyByteBuf packetBuffer) {
//        packetBuffer.writeNbt(msg.tag);
//    }
//
//    public static PlayerHandStorageSyncResponseClientBound decode(FriendlyByteBuf packetBuffer) {
//        return new PlayerHandStorageSyncResponseClientBound(packetBuffer.readNbt());
//    }
//
//    public static class Handler {
//        public static void handle(PlayerHandStorageSyncResponseClientBound message, Supplier<NetworkEvent.Context> ctx) {
//            ctx.get().enqueueWork(()->{
//                Player player = Minecraft.getInstance().player;
//
//                System.out.println("made it here 5");
//
//                CompoundTag tag = message.tag;
//                assert player != null;
//                player.getCapability(NuminaCapabilities.PLAYER_HAND_STORAGE).ifPresent(iPlayerHandStorage -> {
//                    iPlayerHandStorage.deserialize(tag);
//                });
//                ctx.get().setPacketHandled(true);
//            });
//        }
//    }
//}