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

package lehjr.numina.common.capabilities.inventory.modechanging;

import lehjr.numina.common.utils.IconUtils;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import lehjr.numina.common.math.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 7:11 PM, 9/3/13
 *
 * Ported to Java by lehjr on 11/1/16.
 */
public interface IModeChangingItem extends IModularItem {
    String TAG_MODE = "mode";
//    String UNIQUE_ID = "unique_ID";

    @OnlyIn(Dist.CLIENT)
    default void drawModeChangeIcon(LocalPlayer player, int hotbarIndex, GuiGraphics gfx, int screenWidth, int screenHeight) {
        ItemStack module = getActiveModule();
        if (!module.isEmpty()) {
            double currX;
            double currY;

            int baroffset = 22;
            if (!player.isCreative()) {
                baroffset += 16;
                int totalArmorValue = player.getArmorValue();
                baroffset += 8 * (int) Math.ceil((double) totalArmorValue / 20); // 20 points per row @ 2 armor points per icon
            }
            baroffset = screenHeight - baroffset;
            currX = screenWidth / 2.0 - 89.0 + 20.0 * hotbarIndex;
            currY = baroffset - 18;
            Color.WHITE.setShaderColor();

            IPowerModule pm = getModuleCapability(module);
            if (pm == null) {
                return;
            }

            ItemStack stackToRender;
            if(pm instanceof IChameleon && !((IChameleon) pm).getStackToRender(player.clientLevel).isEmpty()) {
                stackToRender = ((IChameleon) pm).getStackToRender(player.clientLevel);
            } else {
                stackToRender = module.copy();
            }

            if (pm.isModuleOnline()) {
                gfx.renderItem(stackToRender, (int) currX, (int) currY);
            } else {
                IconUtils.drawModuleAt(gfx, currX, currY, stackToRender, false);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    default void drawModeChangingModularItemIcon(LocalPlayer player, int hotbarIndex, GuiGraphics gfx, int screenWidth, int screenHeight) {
        ItemStack modularItem = getModularItemStack();
        if (!modularItem.isEmpty()) {
            double currX;
            double currY;

            int baroffset = 22;
            if (!player.isCreative()) {
                baroffset += 16;
                int totalArmorValue = player.getArmorValue();
                baroffset += 8 * (int) Math.ceil((double) totalArmorValue / 20); // 20 points per row @ 2 armor points per icon
            }
            baroffset = screenHeight - baroffset;
            currX = screenWidth / 2.0 - 89.0 + 20.0 * hotbarIndex;
            currY = baroffset - 18;
            Color.WHITE.setShaderColor();
            gfx.renderItem(modularItem, (int) currX, (int) currY);
        }
    }

    List<Integer> getValidModes();

    boolean isValidMode(@Nonnull ItemStack module);

    int getActiveMode();

    ItemStack getActiveModule();

    void setActiveMode(int newMode);

    boolean hasActiveModule(Item item);

    boolean hasActiveModule(int index, Item item);

    boolean hasActiveModule(ResourceLocation regName);

    void cycleMode(Player player, int dMode);

    int nextMode();

    int prevMode();

    boolean isModuleActiveAndOnline(Item module);

    boolean isModuleActiveAndOnline(ResourceLocation moduleName);

    InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand, InteractionResultHolder<ItemStack> fallback);

    /**
     * @return useDuration from active module or fallback
     */
    default int getUseDuration() {
        return getActiveModule().getUseDuration();
    }

    InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context, InteractionResult fallback);

    InteractionResult useOn(UseOnContext context, InteractionResult fallback);

    /**
     * Item.releaseUsing(ItemStack, Level, LivingEntity, int) (ItemStack, Level, LivingEntity)} for when the player releases but the full timer has not passed
     * @param stack
     * @param level
     * @param entityLiving
     * @param timeLeft
     */
    void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft);

    /**
     * Item.finishUsingItem(ItemStack, Level, LivingEntity) for when the player releases and enough ticks have passed
     * @param stack
     * @param level
     * @param livingEntity
     * @return
     */
    ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity);

    default boolean canContinueUsing(@Nonnull ItemStack itemStack) {
//        return ItemStack.isSameItemSameTags(itemStack, getModularItemStack()) || ItemStack.isSameItemSameTags(itemStack, getActiveExternalModule());
        // fixme!!
        return true;
    }

    boolean onBlockStartBreak(ItemStack itemstack, BlockHitResult hitResult, Player player, Level level);

    boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving);


    /**
     * Get the base block breaking speed for and from the emulated tool
     * @param pStack
     * @param state
     * @return
     */
    float getDestroySpeed(ItemStack pStack, BlockState state);

    boolean canPerformAction(ToolAction toolAction);

    boolean isCorrectToolForDrops(ItemStack itemStack, BlockState state);

    boolean returnForeignModuleToModularItem(@Nonnull ItemStack module);
}