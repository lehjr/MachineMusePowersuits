package com.lehjr.numina.common.capabilities.render.highlight;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * used on the client side only
 */
public interface IHighlight {
   HashMap<BlockPostionData, Integer> getBlockPositions(@Nonnull ItemStack tool, @Nonnull BlockHitResult result, @Nonnull Player player, @Nonnull Level level, NonNullList<IBlockBreakingModule> modules, double playerEnergy);

   record BlockPostionData(BlockPos pos, boolean canHarvest, @Nullable IBlockBreakingModule bbm){

       @Override
       public boolean equals(Object o) {
           if (o != null) {
               if(o.getClass() == this.getClass()) {
                   if(pos.equals(((BlockPostionData) o).pos)) {
                       return true;
                   }
               }
           }
           return false;
       }
   }
}
