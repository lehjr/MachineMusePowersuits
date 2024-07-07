package lehjr.powersuits.common.item.electric.tool;

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.client.event.ModelBakeEventHandler;
import lehjr.powersuits.client.render.item.MPSBEWLR;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.HumanoidArm;
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
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.ToolAction;

import java.util.function.Consumer;

public class PowerFist extends AbstractElectricTool {
    public PowerFist() {
        super(new SimpleTier(BlockTags.BUTTONS, 0,0,0, 0, () -> Ingredient.of(Items.AIR)), BlockTags.BUTTONS, new Properties().setNoRepair().stacksTo(1));
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
        return NuminaCapabilities.getModeChangingModularItemCapability(powerFist)
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
        if(NuminaCapabilities.getModeChangingModularItemCapability(itemStack)
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

        return NuminaCapabilities.getModeChangingModularItemCapability(context.getItemInHand())
                .map(handler-> handler.onItemUseFirst(itemStack, context, fallback)).orElse(fallback);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return NuminaCapabilities.getModeChangingModularItemCapability(stack)
                .map(handler-> handler.getDestroySpeed(stack, state)).orElse(1.0F);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStackIn, Player player, LivingEntity entity, InteractionHand hand) {
        return NuminaCapabilities.getModeChangingModularItemCapability(itemStackIn)
                .map(handler-> NuminaCapabilities.getCapability(handler.getActiveModule(), NuminaCapabilities.Module.POWER_MODULE)
                                .filter(IRightClickModule.class::isInstance)
                                .map(IRightClickModule.class::cast)
                                .map(m-> m.interactLivingEntity(itemStackIn, player, entity, hand).getResult())
                                .orElse(InteractionResult.PASS)
                ).orElse(super.interactLivingEntity(itemStackIn, player, entity, hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        NuminaCapabilities.getModeChangingModularItemCapability(stack)
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

    /**
     * Current implementations of this method in child classes do not use the
     * entry argument beside stack. They just raise the damage on the stack.
     */
    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            NuminaCapabilities.getModeChangingModularItemCapability(itemStack)
                    .ifPresent(iItemHandler ->
                            NuminaCapabilities.getPowerModuleCapability(
                                    iItemHandler.getOnlineModuleOrEmpty(MPSConstants.MELEE_ASSIST_MODULE)).ifPresent(pm->{
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
                            }));
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
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        NuminaLogger.logDebug("toolAction: " + toolAction);

        return super.canPerformAction(stack, toolAction);
    }

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

//            @Nullable
//            @Override
//            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
//                return HumanoidModel.ArmPose.BOW_AND_ARROW;
////                return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
//            }
        });
    }
}
