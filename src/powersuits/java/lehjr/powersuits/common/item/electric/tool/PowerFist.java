package lehjr.powersuits.common.item.electric.tool;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PowerFist extends AbstractElectricTool {
    protected PowerFist(Tier pTier, TagKey<Block> pBlocks, Properties pProperties) {
        super(pTier, pBlocks, pProperties);
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

        ItemStack fist = ItemUtils.getItemFromEntityHand(player, hand);
        final InteractionResultHolder<ItemStack> fallback = new InteractionResultHolder<>(InteractionResult.PASS, fist);
//        if (hand != InteractionHand.MAIN_HAND) {
//            return fallback;
//        }
        InteractionResultHolder<ItemStack> tmp = NuminaCapabilities.getCapability(fist, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(handler-> handler.use(level, player, hand, fallback)).orElse(fallback);
        return new InteractionResultHolder<>(tmp.getResult(), fist);
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
        return NuminaCapabilities.getCapability(powerFist, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(iItemHandler -> iItemHandler.mineBlock(powerFist, worldIn, state, pos, entityLiving)).orElse(false);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int ticksRemaining) {
        if(NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(handler-> handler.onUseTick(level, entity, ticksRemaining)).orElse(false)) {
            super.onUseTick(level, entity, itemStack, ticksRemaining);
        }
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context) {
        final InteractionResult fallback = InteractionResult.PASS;

        final InteractionHand hand = context.getHand();
        if (hand != InteractionHand.MAIN_HAND) {
            return fallback;
        }

        return NuminaCapabilities.getCapability(context.getItemInHand(), NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(handler-> handler.onItemUseFirst(itemStack, context, fallback)).orElse(fallback);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState pState) {
        return NuminaCapabilities.getCapability(stack, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(handler-> handler.getDestroySpeed(stack, pState)).orElse(1.0F);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStackIn, Player player, LivingEntity entity, InteractionHand hand) {
        return NuminaCapabilities.getCapability(itemStackIn, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(handler-> NuminaCapabilities.getCapability(handler.getActiveModule(), NuminaCapabilities.PowerModule.POWER_MODULE)
                                .filter(IRightClickModule.class::isInstance)
                                .map(IRightClickModule.class::cast)
                                .map(m-> m.interactLivingEntity(itemStackIn, player, entity, hand).getResult())
                                .orElse(InteractionResult.PASS)
                ).orElse(super.interactLivingEntity(itemStackIn, player, entity, hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        NuminaCapabilities.getCapability(stack, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .ifPresent(handler-> handler.releaseUsing(stack, worldIn, entityLiving, timeLeft));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        final InteractionResult fallback = InteractionResult.PASS;

        final InteractionHand hand = context.getHand();
        if (hand != InteractionHand.MAIN_HAND) {
            return fallback;
        }

        return NuminaCapabilities.getCapability(context.getItemInHand(), NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(handler-> handler.useOn(context, fallback)).orElse(fallback);
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
        return NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(iItemHandler -> iItemHandler.isCorrectToolForDrops(itemStack, state)).orElse(false);
    }

//    /**
//     * Current implementations of this method in child classes do not use the
//     * entry argument beside stack. They just raise the damage on the stack.
//     */
//    @Override
//    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
//        if (attacker instanceof Player) {
//            NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
//                    .ifPresent(iItemHandler ->
//                            NuminaCapabilities.getCapability(
//                                    iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.MELEE_ASSIST_MODULE),
//                                            NuminaCapabilities.PowerModule.POWER_MODULE)
//
//                            .getCapability().ifPresent(pm->{
//                                Player player = (Player) attacker;
//                                double drain = pm.applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//                                if (ElectricItemUtils.getPlayerEnergy(player) > drain) {
//                                    ElectricItemUtils.drainPlayerEnergy(player, (int) drain, false);
//                                    double damage = pm.applyPropertyModifiers(MPSConstants.PUNCH_DAMAGE);
//                                    double knockback = pm.applyPropertyModifiers(MPSConstants.PUNCH_KNOCKBACK);
//                                    DamageSource damageSource = attacker.damageSources().playerAttack(player);
//                                    if (target.hurt(damageSource, (float) (int) damage)) {
//                                        Vec3 lookVec = player.getLookAngle();
//                                        target.push(lookVec.x * knockback, Math.abs(lookVec.y + 0.2f) * knockback, lookVec.z * knockback);
//                                    }
//                                }
//                            }));
//        }
//        return true;
//    }

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



    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return NuminaCapabilities.getCapability(oldStack, NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                .map(iModeChangingItem -> iModeChangingItem.canContinueUsing(newStack)).orElse(false);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
}
