package lehjr.numina.common.capabilities.module.enhancement;

import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * This is only an IRightClickModule so that it can be selected and activated and deselected and deactivated like one.
 * No right click functionality is actually being used.
 */
public interface IMiningEnhancementModule extends IRightClickModule, IToggleableModule {
    boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player);

    int getEnergyUsage();
}
