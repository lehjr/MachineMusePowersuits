package lehjr.numina.common.capabilities.player;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerKeyStateWrapper implements ICapabilitySerializable<ByteTag> {
    Player player;

    private PlayerKeyStateStorage keyStateStorage;
    private LazyOptional<IPlayerKeyStates> keyStateCap;
    public PlayerKeyStateWrapper(Player player) {
        this.player = player;
        keyStateStorage = new PlayerKeyStateStorage();
        keyStateCap = LazyOptional.of(() -> keyStateStorage);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        final LazyOptional<T> playerKeyStates = NuminaCapabilities.PLAYER_KEYSTATES.orEmpty(cap, keyStateCap);
        if (playerKeyStates.isPresent()) {
            return playerKeyStates;
        }
        return LazyOptional.empty();
    }

    @Override
    public ByteTag serializeNBT() {
        // FIXME? get tag from player

        return keyStateStorage.serializeNBT();
    }

    @Override
    public void deserializeNBT(ByteTag nbt) {
        keyStateStorage.deserializeNBT(nbt);
    }
}
