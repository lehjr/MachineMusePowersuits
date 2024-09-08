package com.lehjr.powersuits.common.item.electric.tool;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.SimpleTier;

public class PowerFist extends AbstractElectricTool {
    public PowerFist() {
        super(new SimpleTier(BlockTags.BUTTONS, 0,0,0, 0, () -> Ingredient.of(Items.AIR)), BlockTags.BUTTONS, new Properties().setNoRepair().stacksTo(1));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
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
        NuminaLogger.logDebug("use()... ");
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

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

//    @Override
//    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int ticksRemaining) {
//        if(NuminaCapabilities.getModeChangingModularItemCapability(itemStack)
//                .map(handler-> handler.onUseTick(level, entity, ticksRemaining)).orElse(false)) {
//            NuminaLogger.logDebug("onUseTick remaining: " + ticksRemaining);
//            super.onUseTick(level, entity, itemStack, ticksRemaining);
//        }
//    }

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


    // sets a base block breaking speed based on the module's emulated tool
    // FIXME:
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
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(stack);
        if(mci != null) {
            mci.releaseUsing(stack, worldIn, entityLiving, timeLeft);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        NuminaLogger.logDebug("useOn");

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
        if (attacker instanceof Player) {
            IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(itemStack);
            if(mci != null) {
                int index = mci.findInstalledModule(MPSConstants.MELEE_ASSIST_MODULE);
                if(index > -1) {
                    IPowerModule pm = mci.getModuleCapability(mci.getStackInSlot(index));
                    if(pm != null) {
                        Player player = (Player) attacker;
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
//        return NuminaCapabilities.getCapability(itemstack, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
//                .map(iItemHandler -> iItemHandler.onBlockStartBreak(itemstack, pos, player)).orElse(false);
//    }

//    @Override
//    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
//        NuminaLogger.logDebug("can attack Block :" +super.canAttackBlock(pState, pLevel, pPos, pPlayer) );
//
//        return super.canAttackBlock(pState, pLevel, pPos, pPlayer);
//    }



    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
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
}
