//package lehjr.numina.common.capabilities.player.storage;
//
//import lehjr.numina.common.capabilities.NuminaCapabilities;
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilitySerializable;
//import net.minecraftforge.common.util.LazyOptional;
//
//import javax.annotation.Nonnull;
//
//public class PlayerHandStorageWrapper implements ICapabilitySerializable<CompoundTag> {
//    Player player;
//
//    private final PlayerHandStorage playerHandStorage;
//    private final LazyOptional<IPlayerHandStorage> handStorageCap;
//    public PlayerHandStorageWrapper(Player player) {
//        this.player = player;
//        playerHandStorage = new PlayerHandStorage(player);
//        handStorageCap = LazyOptional.of(() -> playerHandStorage);
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
//        final LazyOptional<T> playerKeyStates = NuminaCapabilities.PLAYER_HAND_STORAGE.orEmpty(cap, handStorageCap);
//        if (playerKeyStates.isPresent()) {
//            return playerKeyStates;
//        }
//        return LazyOptional.empty();
//    }
//
//    @Override
//    public CompoundTag serializeNBT() {
//        return playerHandStorage.serializeNBT();
//    }
//
//    @Override
//    public void deserializeNBT(CompoundTag nbt) {
//        playerHandStorage.deserializeNBT(nbt);
//    }
//}
//
