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

package lehjr.numina.util.capabilities.module.rightclick;

import lehjr.numina.util.capabilities.module.powermodule.IPowerModule;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IRightClickModule extends IPowerModule {

    /**
     * @param itemStackIn
     * @param worldIn
     * @param playerIn
     * @param hand
     * @return
     */
    default ActionResult<ItemStack> use(@Nonnull ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
        return new ActionResult<>(ActionResultType.PASS, itemStackIn);
    }

    /**
     * replaces previously used onRightClick method, adds a parameter
     * @param itemStackIn
     * @param playerIn
     * @param entity
     * @param hand
     * @return
     */
    default ActionResult<ItemStack> interactLivingEntity(ItemStack itemStackIn, PlayerEntity playerIn, LivingEntity entity, Hand hand) {
        return new ActionResult<>(ActionResultType.PASS, itemStackIn);
    }

    default ActionResultType useOn(ItemUseContext context) {
        return ActionResultType.PASS;
    }

    default ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return ActionResultType.PASS;
    }

    default void releaseUsing(@Nonnull ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    default ItemStack finishUsingItem(ItemStack itemStack, World world, LivingEntity entity) {
        return itemStack.isEdible() ? entity.eat(world, itemStack) : itemStack;
    }

    default int getEnergyUsage() {
        return 0;
    }
}