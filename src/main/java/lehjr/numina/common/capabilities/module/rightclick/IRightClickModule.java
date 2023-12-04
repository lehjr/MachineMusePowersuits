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

package lehjr.numina.common.capabilities.module.rightclick;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public interface IRightClickModule extends IPowerModule {

    /**
     * @param itemStackIn
     * @param worldIn
     * @param playerIn
     * @param hand
     * @return
     */

    default InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
        return new InteractionResultHolder<>(InteractionResult.PASS, itemStackIn);
    }

    /**
     * replaces previously used onRightClick method, adds a parameter
     * @param itemStackIn
     * @param playerIn
     * @param entity
     * @param hand
     * @return
     */
    default InteractionResultHolder<ItemStack>interactLivingEntity(ItemStack itemStackIn, Player playerIn, LivingEntity entity, InteractionHand hand) {
        return new InteractionResultHolder<>(InteractionResult.PASS, itemStackIn);
    }

    default InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    default InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return InteractionResult.PASS;
    }

    default void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    default ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        return itemStack.isEdible() ? entity.eat(level, itemStack) : itemStack;
    }

    default int getEnergyUsage() {
        return 0;
    }
}