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

package lehjr.powersuits.common.item.electric.tool;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.event.ModelBakeEventHandler;
import lehjr.powersuits.client.render.item.MPSBEWLR;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PowerFist extends AbstractElectricTool {
    public PowerFist() {
        super(new Item.Properties().stacksTo(1).defaultDurability(0));
    }

    /**
     * Note ItemStack returned in the InteractionResultHolder becomes the held item
     * @param level
     * @param player
     * @param hand
     * @return
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        NuminaLogger.logDebug("use()... ");
        ItemStack fist = ItemUtils.getItemFromEntityHand(player, hand);
        final InteractionResultHolder<ItemStack> fallback = new InteractionResultHolder<>(InteractionResult.PASS, fist);
//        if (hand != InteractionHand.MAIN_HAND) {
//            return fallback;
//        }
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(fist);
        if(mci!= null) {
            return new InteractionResultHolder<>(mci.use(level, player, hand, fallback).getResult(), fist);
        }
        return fallback;
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int ticksRemaining) {
//        if(itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(handler-> handler.onUseTick(level, entity, ticksRemaining)).orElse(false)) {
//            super.onUseTick(level, entity, itemStack, ticksRemaining);
//        }
//    }

//    @Override
//    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entity) {
//        return stack.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(handler-> finishUsingItem(stack, worldIn, entity)).orElse(stack);
//    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        stack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .ifPresent(handler-> handler.releaseUsing(stack, worldIn, entityLiving, timeLeft));
    }




    /**
     * Called server side when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     *
     * @param powerFist
     * @param worldIn
     * @param state
     * @param pos
     * @param entityLiving
     * @return
     */
    @Override
    public boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(powerFist);
        if(mci != null) {
            return mci.mineBlock(powerFist, worldIn, state, pos, entityLiving);
        }
        return false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

//    /**
//     * Called before a block is broken.  Return true to prevent default block harvesting.
//     *
//     * Note: In SMP, this is called on both client and server sides!
//     *
//     * @param itemstack The current ItemStack
//     * @param pos Block's position in world
//     * @param player The Player that is wielding the item
//     * @return True to prevent harvesting, false to continue as normal
//     */
//    @Override
//    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
//        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(itemstack);
//        if(mci != null) {
//
//        }
//
//
//        return itemstack.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(iItemHandler -> iItemHandler.onBlockStartBreak(itemstack, pos, player)).orElse(false);
//    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context) {
        final InteractionResult fallback = InteractionResult.PASS;

        final InteractionHand hand = context.getHand();
        if (hand != InteractionHand.MAIN_HAND) {
            return fallback;
        }
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(itemStack);
        if(mci != null) {
            return mci.onItemUseFirst(itemStack, context, fallback);
        }
        return fallback;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState state) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(itemStack);
        if(mci != null) {
            return mci.getDestroySpeed(itemStack, state);
        }
        return 1F;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity entity, InteractionHand hand) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(itemStack);
        if(mci != null) {
            ItemStack module = mci.getActiveModule();
            IPowerModule pm = mci.getModuleCapability(module);
            if(pm instanceof IRightClickModule) {
                return ((IRightClickModule) pm).interactLivingEntity(itemStack, player, entity, hand).getResult();
            }
        }
        return super.interactLivingEntity(itemStack, player, entity, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        final InteractionResult fallback = InteractionResult.PASS;

        final InteractionHand hand = context.getHand();
        if (hand != InteractionHand.MAIN_HAND) {
            return fallback;
        }

        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(context.getItemInHand());
        if(mci != null) {
            return mci.useOn(context, fallback);
        }
        return fallback;
    }

    /**
     * Needed for overriding behaviour with modules
     * Note: also called by mods like TheOneProbe to display whether the required tool is equipped
     * @param itemStack
     * @param state
     * @return
     */
    @Override
    public boolean isCorrectToolForDrops(ItemStack itemStack, BlockState state) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(itemStack);
        return mci != null && mci.isCorrectToolForDrops(itemStack, state);
    }

    /**
     * Current implementations of this method in child classes do not use the
     * entry argument beside stack. They just raise the damage on the stack.
     */
    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(itemStack);
            if(mci != null) {
                int index = mci.findInstalledModule(MPSConstants.MELEE_ASSIST_MODULE);
                if(mci.isSlotValid(index)) {
                    IPowerModule pm = mci.getModuleCapability(mci.getStackInSlot(index));
                    if(pm != null) {
                        double drain = pm.applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                        if (ElectricItemUtils.getPlayerEnergy(player) > drain) {
                            ElectricItemUtils.drainPlayerEnergy(player, (int) drain, false);
                            double damage = pm.applyPropertyModifiers(MPSConstants.PUNCH_DAMAGE);
                            double knockback = pm.applyPropertyModifiers(MPSConstants.PUNCH_KNOCKBACK);
                            DamageSource damageSource = attacker.damageSources().playerAttack(player);
                            if (target.hurt(damageSource, (float) (int) damage)) {
                                Vec3 lookVec = player.getLookAngle();
                                target.push(lookVec.x * knockback, Math.abs(lookVec.y + 0.2f) * knockback, lookVec.z * knockback);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(stack);
        return mci != null && mci.canPerformAction(toolAction);
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(oldStack);
        if(mci != null) {
            return mci.canContinueUsing(newStack);
        }
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = ModelBakeEventHandler.INSTANCE.MPSBERINSTANCE;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                ((MPSBEWLR)renderer).setFiringData(new MPSBEWLR.FiringData(player, arm, itemInHand));
                return IClientItemExtensions.super.applyForgeHandTransform(poseStack, player, arm, itemInHand, partialTick, equipProcess, swingProcess);
            }
        });
    }
}