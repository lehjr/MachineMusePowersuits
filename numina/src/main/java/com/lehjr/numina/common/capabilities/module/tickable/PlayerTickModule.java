package com.lehjr.numina.common.capabilities.module.tickable;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class PlayerTickModule extends ToggleableModule implements IPlayerTickModule {
    public PlayerTickModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }

    @Override
    public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item) {

    }

    @Override
    public void onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack item) {

    }
}
