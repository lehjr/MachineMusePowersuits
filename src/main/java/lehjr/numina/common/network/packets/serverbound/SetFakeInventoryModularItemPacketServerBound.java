//package lehjr.numina.common.network.packets.serverbound;
//
//import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
//import lehjr.numina.imixin.common.entity.player.IMixinInventory;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.common.capabilities.ForgeCapabilities;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public record SetFakeInventoryModularItemPacketServerBound(ItemStack modularItem, InteractionHand hand) {
//
//    public static void encode(SetFakeInventoryModularItemPacketServerBound msg, FriendlyByteBuf packetBuffer) {
//        packetBuffer.writeItem(msg.modularItem);
//        packetBuffer.writeEnum(msg.hand);
//    }
//
//    public static SetFakeInventoryModularItemPacketServerBound decode(FriendlyByteBuf packetBuffer) {
//        return new SetFakeInventoryModularItemPacketServerBound(
//                packetBuffer.readItem(),
//                packetBuffer.readEnum(InteractionHand.class)
//        );
//    }
//
//    public static class handler {
//        public static void handle(SetFakeInventoryModularItemPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
//            Player player = ctx.get().getSender();
//            assert player != null;
//            Inventory inventory = player.getInventory();
//            InteractionHand hand = message.hand;
//            if(inventory instanceof IMixinInventory) {
//                if (hand == InteractionHand.MAIN_HAND || hand == InteractionHand.OFF_HAND) {
//                    ItemStack modularItem = player.getItemInHand(hand);
//                    ItemStack module = modularItem.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                            .filter(IModeChangingItem.class::isInstance)
//                            .map(IModeChangingItem.class::cast).map(IModeChangingItem::getActiveExternalModule).orElse(ItemStack.EMPTY);
//                    ((IMixinInventory) inventory).setItemInHandStorage(modularItem, hand);
//                    player.setItemInHand(hand, module);
//                    player.inventoryMenu.broadcastChanges();
//                }
//            }
//        }
//    }
//}
