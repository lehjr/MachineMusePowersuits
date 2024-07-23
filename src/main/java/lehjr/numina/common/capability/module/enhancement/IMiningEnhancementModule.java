package lehjr.numina.common.capability.module.enhancement;

import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.capability.module.toggleable.IToggleableModule;
import lehjr.numina.common.capability.render.highlight.IHighlight;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

/**
 * This is only an IRightClickModule so that it can be selected and activated and deselected and deactivated like one.
 * No right click functionality is actually being used.
 */
public interface IMiningEnhancementModule extends IRightClickModule, IToggleableModule, IHighlight {
    boolean onBlockStartBreak(ItemStack itemstack, BlockHitResult hitResult, Player player, Level level);

    int getEnergyUsage();
}
