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
//public record ReturnModularItemToInventoryPacketServerBound(ItemStack modularItem, InteractionHand hand) {
//    public static void encode(ReturnModularItemToInventoryPacketServerBound msg, FriendlyByteBuf packetBuffer) {
//        packetBuffer.writeItem(msg.modularItem);
//        packetBuffer.writeEnum(msg.hand);
//    }
//
//    public static ReturnModularItemToInventoryPacketServerBound decode(FriendlyByteBuf packetBuffer) {
//        return new ReturnModularItemToInventoryPacketServerBound(
//                packetBuffer.readItem(),
//                packetBuffer.readEnum(InteractionHand.class)
//                );
//    }
//
//    public static class handler {
//        public static void handle(ReturnModularItemToInventoryPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
//            Player player = ctx.get().getSender();
//            assert player != null;
//            Inventory inventory = player.getInventory();
//            InteractionHand hand = message.hand;
//            if(inventory instanceof IMixinInventory) {
//                if (hand == InteractionHand.MAIN_HAND || hand == InteractionHand.OFF_HAND) {
//                    ItemStack module = player.getItemInHand(hand);
//                    ItemStack modularItem = ((IMixinInventory) inventory).getItemStackFromHandStorage(hand);
//                    modularItem.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                            .filter(IModeChangingItem.class::isInstance)
//                            .map(IModeChangingItem.class::cast).ifPresent(iModeChangingItem -> {
//                                int activeMode = iModeChangingItem.getActiveMode();
//                                if (module.getItem().equals(iModeChangingItem.getActiveModule().getItem())) {
//                                    iModeChangingItem.setStackInSlot(activeMode, module);
//                                }
//                            });
//
//                    player.setItemInHand(hand, modularItem);
//                    ((IMixinInventory) inventory).setItemInHandStorage(ItemStack.EMPTY, hand);
//                    player.inventoryMenu.broadcastChanges();
//                }
//            }
//        }
//    }
//}
