//package lehjr.numina.imixin.common.entity.player;
//
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.item.ItemStack;
//
//import javax.annotation.Nonnull;
//
//public interface IMixinInventory {
//    void setOffHandStorage(@Nonnull ItemStack itemStack);
//
//    void setMainHandStorage(@Nonnull ItemStack itemStack);
//
//    @Nonnull
//    ItemStack getOffHandStorage();
//
//    @Nonnull
//    ItemStack getMainHandStorage();
//
//    @Nonnull
//    default ItemStack getItemStackFromHandStorage(InteractionHand hand) {
//        if (hand == InteractionHand.MAIN_HAND) {
//            return getMainHandStorage();
//        }
//        if (hand == InteractionHand.OFF_HAND) {
//            return getOffHandStorage();
//        }
//        throw new IllegalArgumentException("Invalid hand " + hand);
//    }
//
//    default void setItemInHandStorage(@Nonnull ItemStack itemStack, InteractionHand hand) {
//        if (hand == InteractionHand.MAIN_HAND) {
//            System.out.println("setting mainHandStorage");
//            setMainHandStorage(itemStack);
//            System.out.println("mainHandStorage: " + getMainHandStorage());
//
//        }
//        if (hand == InteractionHand.OFF_HAND) {
//            setOffHandStorage(itemStack);
//        }
//        throw new IllegalArgumentException("Invalid hand " + hand);
//    }
//}
