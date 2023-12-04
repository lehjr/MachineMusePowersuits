//package lehjr.numina.common.capabilities.player.storage;
//
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.items.IItemHandler;
//import net.minecraftforge.items.IItemHandlerModifiable;
//
//import javax.annotation.Nonnull;
//
//public interface IPlayerHandStorage extends IItemHandler, IItemHandlerModifiable {
//    String SELECTED = "mps_selected";
//
//    @Nonnull
//    ItemStack getOffHandStorage();
//    @Nonnull
//    ItemStack getMainHandStorage();
//
//    void setMainHandStorage(@Nonnull ItemStack mainHandStack);
//
//    void setOffHandStorage(@Nonnull ItemStack offHandStorage);
//
//    void deserialize(CompoundTag tag);
//
//    CompoundTag serialize();
//    int getSelectedSlot();
//}
