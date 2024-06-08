package lehjr.numina.common.capabilities.module.tickable;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class PlayerTickModule extends ToggleableModule implements IPlayerTickModule {
    public PlayerTickModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }

    @Override
    public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {

    }

    @Override
    public void onPlayerTickInactive(Player player, @Nonnull ItemStack item) {

    }
}