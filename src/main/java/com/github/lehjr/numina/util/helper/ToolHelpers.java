/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.helper;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Helper methods for the tool classes. Gets rid of multiple copies of similar code.
 * by lehjr on 11/27/16.
 */
public class ToolHelpers {
    public static boolean isToolEffective(IBlockReader world, BlockPos pos, @Nonnull ItemStack emulatedTool) {
        BlockState state = world.getBlockState(pos);

        if (Float.compare(state.getBlockHardness(world, pos), -1.0F) < 0 ) {// unbreakable
            return false;
        }

        if (emulatedTool.getItem().canHarvestBlock(state)) {
            return true;
        }

        ToolType harvestTool = state.getBlock().getHarvestTool(state);
        if (harvestTool != null) {
            // this should be enough but nooooo, stairs still don't work here;
            for (ToolType type : emulatedTool.getItem().getToolTypes(emulatedTool)) {
                if (state.getBlock().isToolEffective(state, type) || (Objects.equals(harvestTool, type) &&
                        emulatedTool.getItem().getHarvestLevel(emulatedTool, harvestTool, null, null) >= state.getBlock().getHarvestLevel(state)))
                    return true;
            }
        } else {
            // 1.0F is the neutral destroy speed
            if (Float.compare(emulatedTool.getDestroySpeed(state), 1.0F) >= 0) { // float math not reliable
                return true;
            }
        }
        return false;
    }

    public static boolean blockCheckAndHarvest(PlayerEntity player, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == null || world.isAirBlock(pos) || block == Blocks.BEDROCK)
            return false;
        if ((block instanceof IForgeShearable || block instanceof FlowerBlock || block instanceof BushBlock || block instanceof LeavesBlock)
                && block.canHarvestBlock(state, world, pos, player) || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK) {
            block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), new ItemStack(Items.IRON_SHOVEL));
            world.removeBlock(pos, false);
            return true;
        }
        return false;
    }
}