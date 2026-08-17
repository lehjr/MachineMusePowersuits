package lehjr.numina.common.capabilities.module.tickable;

import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public interface IPlayerTickModule extends IToggleableModule {
    boolean onPlayerTickActive(Player player, Level level, ItemStack host, int moduleIndex);

    boolean onPlayerTickInactive(Player player, Level level, ItemStack host, int moduleIndex);
}
