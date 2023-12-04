//package lehjr.numina.common.capabilities.player.storage;
//
//import lehjr.numina.common.capabilities.CapabilityUpdate;
//import lehjr.numina.common.network.NuminaPackets;
//import lehjr.numina.common.network.packets.clientbound.BlockNamePacketClientBound;
//import lehjr.numina.common.network.packets.clientbound.PlayerHandStorageSyncResponseClientBound;
//import lehjr.numina.common.network.packets.serverbound.PlayerHandStorageSyncRequestServerBound;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.common.util.INBTSerializable;
//import net.minecraftforge.items.ItemStackHandler;
//import net.minecraftforge.network.PacketDistributor;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.UUID;
//
//public class PlayerHandStorage extends ItemStackHandler implements IPlayerHandStorage, CapabilityUpdate, INBTSerializable<CompoundTag> {
//
//    private Player player;
//
//
//    // This should be set when moving the modular item to this storage and unset when moving it back
//    private int selectedSlot = -1; // hold the index for the hotbar selection at the time
//
//
//    public PlayerHandStorage(Player player) {
//        super(2);
//        this.player = player;
//    }
//
//    public Player getPlayer() {
//        return player;
//    }
//
//    @Override
//    public void loadCapValues() {
//
//    }
//
//    @Override
//    public void onValueChanged() {
//        if (getMainHandStorage().isEmpty()) {
//            selectedSlot = -1;
//        }
//    }
//
//    @NotNull
//    @Override
//    public ItemStack getMainHandStorage() {
//        return getStackInSlot(0);
//    }
//
//    @NotNull
//    @Override
//    public ItemStack getOffHandStorage() {
//        return getStackInSlot(1);
//    }
//
//    @Override
//    public void setMainHandStorage(@NotNull ItemStack mainHandStack) {
//        super.setStackInSlot(0, mainHandStack);
//        if (mainHandStack.isEmpty()) {
//            selectedSlot = -1;
//        } else {
//            selectedSlot = player.getInventory().selected;
//        }
//    }
//
//    @Override
//    public void setOffHandStorage(@NotNull ItemStack offHandStack){
//        super.setStackInSlot(1, offHandStack);
//    }
//
//    @Override
//    public int getSelectedSlot() {
//        return selectedSlot;
//    }
//
//    @Override
//    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
//        super.setStackInSlot(slot, stack);
//        if (!stack.isEmpty() && !player.isLocalPlayer()) {
//            this.selectedSlot = player.getInventory().selected;
//        }
//    }
//
//    @Override
//    public void deserializeNBT(CompoundTag nbt) {
//        super.deserializeNBT(nbt);
//        if(nbt.contains(SELECTED)) {
//            this.selectedSlot = nbt.getInt(SELECTED);
//        }
//    }
//
//    @Override
//    public CompoundTag serializeNBT() {
//        System.out.println(player.isLocalPlayer() + ", serialize nbt");
//        CompoundTag nbt = super.serializeNBT();
//        if (selectedSlot != -1) {
//            nbt.putInt(SELECTED, selectedSlot);
//        }
//        return nbt;
//    }
//
//    @Override
//    public void deserialize(CompoundTag tag) {
//        if (tag.contains(SELECTED)) {
//            this.selectedSlot = tag.getInt(SELECTED);
//        }
//        super.deserializeNBT(tag);
//    }
//
//    @Override
//    public CompoundTag serialize() {
//        CompoundTag tag = super.serializeNBT();
//        if (selectedSlot != -1) {
//            tag.putInt(SELECTED, selectedSlot);
//        }
//        return tag;
//    }
//
//    @Override
//    protected void onContentsChanged(int slot) {
//        onValueChanged();
//
////        CompoundTag tag = new CompoundTag();
////        tag.put("HAND_STORAGE", serializeNBT());
////        player.addAdditionalSaveData(tag);
//    }
//}
