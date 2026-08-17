package lehjr.numina.common.capabilities.module.tickable;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class PlayerTickModule extends ToggleableModule implements IPlayerTickModule {
    public PlayerTickModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }

    @Override
    public boolean onPlayerTickActive(Player player, Level level, ItemStack host, int moduleIndex) {
        return false;
    }

    @Override
    public boolean onPlayerTickInactive(Player player, Level level, ItemStack host, int moduleIndex) {
        return false;
    }
}
