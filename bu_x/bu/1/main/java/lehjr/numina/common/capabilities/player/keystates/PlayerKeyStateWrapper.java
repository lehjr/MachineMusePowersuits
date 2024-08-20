package com.lehjr.numina.common.capabilities.player.keystates;

import com.lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PlayerKeyStateWrapper implements ICapabilityProvider<ByteTag> {
    @Nullable
    @Override
    public Object getCapability(Object object, Object context) {
        return null;
    }
//    Player player;
//
//    private final PlayerKeyStateStorage keyStateStorage;
//    private final LazyOptional<IPlayerKeyStates> keyStateCap;
//    public PlayerKeyStateWrapper(Player player) {
//        this.player = player;
//        keyStateStorage = new PlayerKeyStateStorage();
//        keyStateCap = LazyOptional.of(() -> keyStateStorage);
//    }
//
//    @org.jetbrains.annotations.Nullable
//    @Override
//    public Object getCapability(Object object, Object context) {
//        return null;
//    }
////
////    @Nonnull
////    @Override
////    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
////        final LazyOptional<T> playerKeyStates = NuminaCapabilities.PLAYER_KEYSTATES.orEmpty(cap, keyStateCap);
////        if (playerKeyStates.isPresent()) {
////            return playerKeyStates;
////        }
////        return LazyOptional.empty();
////    }
//
//    @Override
//    public ByteTag serializeNBT() {
//        // FIXME? get tag from player
//
//        return keyStateStorage.serializeNBT();
//    }
//
//    @Override
//    public void deserializeNBT(ByteTag nbt) {
//        keyStateStorage.deserializeNBT(nbt);
//    }
}
