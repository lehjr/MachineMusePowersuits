/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.dev.crafting.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.stats.Stats;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class NUminaContainerProvider implements INamedContainerProvider {
    int typeIndex;
    public NUminaContainerProvider(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    @Override
    public ITextComponent getDisplayName() {
//        switch(typeIndex) {
//            case 0:
                return new TranslationTextComponent("gui.numina.tab.workbench");
//            default:
//                return MPSConstants.CRAFTING_TABLE_CONTAINER_NAME;
//        }
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        System.out.println("windowId here: " + windowId);

        switch(typeIndex) {
            case 0:
                return new NuminaCraftingContainer(windowId, playerInventory);
            default:
                // Fixme: in vanilla, this is done client side?


                player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);


//


//                System.out.println("world is remote? " + player.world.isRemote);
//                player.openContainer(getContainer(player.world, player.getPosition()));
//                return player.openContainer;

//
//
//
//
////
////
////                // FIXME: crafting GUI, original or MPS?
////
//                return new WorkbenchContainer(windowId, playerInventory);

            return new NuminaCraftingContainer(windowId, playerInventory);
        }
    }

//    public INamedContainerProvider getContainer(World worldIn, BlockPos pos) {
//        return new SimpleNamedContainerProvider((id, inventory, player) -> {
//            return new WorkbenchContainer(id, inventory, IWorldPosCallable.of(worldIn, pos));
//        },  new TranslationTextComponent("container.crafting"));
//    }

    public INamedContainerProvider getContainer(World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((id, inventory, player) -> {
            System.out.println("windowId here: " + id);

            return new NuminaCraftingContainer(id, inventory, IWorldPosCallable.create(worldIn, pos));
        },  new TranslationTextComponent("container.crafting"));
    }
}