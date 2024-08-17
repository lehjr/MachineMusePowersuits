package com.lehjr.numina.common.capabilities.module.enhancement;

import com.lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.capabilities.render.highlight.IHighlight;
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
