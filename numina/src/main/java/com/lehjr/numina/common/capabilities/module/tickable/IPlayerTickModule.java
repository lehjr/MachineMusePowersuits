package com.lehjr.numina.common.capabilities.module.tickable;

import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public interface IPlayerTickModule extends IToggleableModule {
    void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item);

    void onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack item);

    default int getEnergyUsage() {
        return 0;
    }
}