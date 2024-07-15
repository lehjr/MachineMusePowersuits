package lehjr.numina.common.capability.render.highlight;

import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * used on the client side only
 */
public interface IHighlight {
   NonNullList<BlockPostions> getBlockPositions(@Nonnull ItemStack tool, @Nonnull BlockHitResult result, @Nonnull Player player, @Nonnull Level level, NonNullList<IBlockBreakingModule> modules, double playerEnergy);

   record BlockPostions(BlockPos pos, boolean canHarvest, @Nullable IBlockBreakingModule bbm){
   }
}