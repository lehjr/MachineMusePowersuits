package lehjr.numina.common.capabilities.module.tickable;

import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public interface IPlayerTickModule extends IToggleableModule {
    void onPlayerTickActive(Player player, @Nonnull ItemStack item);

    void onPlayerTickInactive(Player player, @Nonnull ItemStack item);

    default int getEnergyUsage() {
        return 0;
    }
}