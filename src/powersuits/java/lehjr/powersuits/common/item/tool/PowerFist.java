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

package lehjr.powersuits.common.item.tool;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.miningenhancement.IMiningEnhancementModule;
import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.common.base.MPSObjects;
import lehjr.powersuits.common.capability.PowerFistCap;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

//public class PowerFist extends AbstractElectricTool {
//    public PowerFist() {
//        super(new Item.Properties().tab(MPSObjects.creativeTab).stacksTo(1).defaultDurability(0));
//    }
//
//    @Override
//    public int func_77626_a(ItemStack stack) {
//        return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(handler->
//                        handler.getActiveModule().getCapability(PowerModuleCapability.POWER_MODULE)
//                                .filter(IRightClickModule.class::isInstance)
//                                .map(IRightClickModule.class::cast)
//                                .map(m-> m.getUseDuration())
//                                .orElse(72000))
//                .orElse(72000);
//    }
//
//    @Override
//    public boolean func_179218_a(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
//        int playerEnergy = ElectricItemUtils.getPlayerEnergy(entityLiving);
//        return powerFist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(iItemHandler -> iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class).stream().anyMatch(module ->
//                        module.getCapability(PowerModuleCapability.POWER_MODULE)
//                                .filter(IBlockBreakingModule.class::isInstance)
//                                .map(IBlockBreakingModule.class::cast)
//                                .map(pm-> pm.onBlockDestroyed(powerFist, worldIn, state, pos, entityLiving, playerEnergy))
//                                .orElse(false))
//                ).orElse(false);
//    }
//
//    @Override
//    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
//        return false;
//    }
//
//    @Override
//    public boolean doesSneakBypassUse(ItemStack stack, IWorldReader world, BlockPos pos, Player player) {
//        return true;
//    }
//
//    @Override
//    public Set<ToolType> getToolTypes(ItemStack itemStack) {
//        return itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(iItemHandler -> {
//                    Set<ToolType> retSet = new HashSet<>();
//                    if (!iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.PICKAXE_MODULE).isEmpty()) {
//                        retSet.add(ToolType.PICKAXE);
//                    }
//
//                    if (!iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.AXE_MODULE).isEmpty()) {
//                        retSet.add(ToolType.AXE);
//                    }
//
//                    if (!iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.SHOVEL_MODULE).isEmpty()) {
//                        retSet.add(ToolType.SHOVEL);
//                    }
//
//                    if (!iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.HOE_MODULE).isEmpty()) {
//                        retSet.add(ToolType.HOE);
//                    }
//
//                    return retSet;
//                }).orElse(new HashSet<>());
//    }
//
//    /**
//     * Current implementations of this method in child classes do not use the
//     * entry argument beside stack. They just raise the damage on the stack.
//     */
//    @Override
//    public boolean func_77644_a(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
//        if (attacker instanceof Player) {
//            itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                    .filter(IModeChangingItem.class::isInstance)
//                    .map(IModeChangingItem.class::cast)
//                    .ifPresent(iItemHandler -> iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.MELEE_ASSIST_MODULE)
//                            .getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(pm->{
//                                Player player = (Player) attacker;
//                                double drain = pm.applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//                                if (ElectricItemUtils.getPlayerEnergy(player) > drain) {
//                                    ElectricItemUtils.drainPlayerEnergy(player, (int) drain);
//                                    double damage = pm.applyPropertyModifiers(MPSConstants.PUNCH_DAMAGE);
//                                    double knockback = pm.applyPropertyModifiers(MPSConstants.PUNCH_KNOCKBACK);
//                                    DamageSource damageSource = DamageSource.func_76365_a(player);
//                                    if (target.hurt(damageSource, (float) (int) damage)) {
//                                        Vector3d lookVec = player.func_70040_Z();
//                                        target.func_70024_g(lookVec.x * knockback, Math.abs(lookVec.y + 0.2f) * knockback, lookVec.z * knockback);
//                                    }
//                                }
//                            }));
//        }
//        return true;
//    }
//
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
//        super.onBlockStartBreak(itemstack, pos, player);
//        return itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(iItemHandler -> iItemHandler.getActiveModule()
//                        .getCapability(PowerModuleCapability.POWER_MODULE)
//                        .filter(IMiningEnhancementModule.class::isInstance)
//                        .map(IMiningEnhancementModule.class::cast)
//                        .filter(pm ->pm.isModuleOnline())
//                        .map(pm ->pm.onBlockStartBreak(itemstack, pos, player))
//                        .orElse(false)).orElse(false);
//    }
//
//    @Override
//    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
//        return false;
//    }
//
//    @Override
//    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
//        return oldStack.sameItem(newStack);
//    }
//
//    // Only fires on blocks that need a tool
//    @Override
//    public int getHarvestLevel(ItemStack itemStack, ToolType toolType, @Nullable Player player, @Nullable BlockState state) {
//        return itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(iItemHandler -> iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)
//                        .stream().mapToInt(
//                                module -> module.getCapability(PowerModuleCapability.POWER_MODULE)
//                                        .filter(IBlockBreakingModule.class::isInstance)
//                                        .map(IBlockBreakingModule.class::cast)
//                                        .map(pm-> pm.getEmulatedTool().getHarvestLevel(toolType, player, state)).orElse(-1))
//                        .max().orElse(-1)).orElse(-1);
//    }
//
//    /**
//     * Needed for overriding behaviour with modules
//     * @param itemStack
//     * @param state
//     * @return
//     */
//    @Override
//    public boolean canHarvestBlock(ItemStack itemStack, BlockState state) {
//        return itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(iItemHandler -> iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)
//                        .stream().anyMatch(module ->
//                                module.getCapability(PowerModuleCapability.POWER_MODULE)
//                                        .filter(IBlockBreakingModule.class::isInstance)
//                                        .map(IBlockBreakingModule.class::cast)
//                                        .map(pm ->pm.getEmulatedTool().func_150998_b(state)).orElse(false)
//                        )).orElse(false);
//    }
//
//    @Override
//    public InteractionResult func_195939_a(UseOnContext context) {
//        final InteractionResult fallback = InteractionResult.PASS;
//
//        final InteractionHand hand = context.getHand();
//        if (hand != InteractionHand.MAIN_HAND) {
//            return fallback;
//        }
//
//        return context.getItemInHand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(handler->{
//                    ItemStack module = handler.getActiveModule();
//                    return module.getCapability(PowerModuleCapability.POWER_MODULE)
//                            .filter(IRightClickModule.class::isInstance)
//                            .map(IRightClickModule.class::cast)
//                            .map(m-> m.useOn(context)).orElse(fallback);
//                }).orElse(fallback);
//    }
//
//    @Override
//    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context) {
//        final InteractionResult fallback = InteractionResult.PASS;
//
//        final InteractionHand hand = context.getHand();
//        if (hand != InteractionHand.MAIN_HAND) {
//            return fallback;
//        }
//
//        return context.getItemInHand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(handler->{
//                    ItemStack module = handler.getActiveModule();
//                    return module.getCapability(PowerModuleCapability.POWER_MODULE)
//                            .filter(IRightClickModule.class::isInstance)
//                            .map(IRightClickModule.class::cast)
//                            .map(m-> m.onItemUseFirst(itemStack, context))
//                            .orElse(fallback);
//                }).orElse(fallback);
//    }
//
//    @Override
//    public void func_77615_a(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
//        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .ifPresent(handler->{
//                    ItemStack module = handler.getActiveModule();
//                    module.getCapability(PowerModuleCapability.POWER_MODULE)
//                            .filter(IRightClickModule.class::isInstance)
//                            .map(IRightClickModule.class::cast)
//                            .ifPresent(m-> m.releaseUsing(stack, worldIn, entityLiving, timeLeft));
//                });
//    }
//
//    @Override
//    public ItemStack func_77654_b(ItemStack stack, Level worldIn, LivingEntity entity) {
//        return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(handler->
//                        handler.getActiveModule().getCapability(PowerModuleCapability.POWER_MODULE)
//                                .filter(IRightClickModule.class::isInstance)
//                                .map(IRightClickModule.class::cast)
//                                .map(m-> m.finishUsingItem(stack, worldIn, entity))
//                                .orElse((super.func_77654_b(stack, worldIn, entity))))
//                .orElse(stack);
//    }
//
//    @Override
//    public InteractionResult func_111207_a(ItemStack itemStackIn, Player player, LivingEntity entity, Hand hand) {
//        return itemStackIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(handler->
//                        handler.getActiveModule().getCapability(PowerModuleCapability.POWER_MODULE)
//                                .filter(IRightClickModule.class::isInstance)
//                                .map(IRightClickModule.class::cast)
//                                .map(m-> m.interactLivingEntity(itemStackIn, player, entity, hand).func_188397_a())
//                                .orElse(InteractionResult.PASS)
//                ).orElse(super.func_111207_a(itemStackIn, player, entity, hand));
//    }
//
//    @Override
//    public ActionResult<ItemStack> func_77659_a(Level world, Player playerIn, Hand handIn) {
//        ItemStack fist = playerIn.getItemInHand(handIn);
//        final ActionResult<ItemStack> fallback = new ActionResult<>(InteractionResult.PASS, fist);
//        if (handIn != InteractionHand.MAIN_HAND) {
//            return fallback;
//        }
//
//        return fist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(handler-> handler.getActiveModule().
//                        getCapability(PowerModuleCapability.POWER_MODULE)
//                        .filter(IRightClickModule.class::isInstance)
//                        .map(IRightClickModule.class::cast)
//                        .map(rc-> rc.use(fist, world, playerIn, handIn)).orElse(fallback)).orElse(fallback);
//    }
//
//    @Override
//    public UseAction func_77661_b(ItemStack stack) {
//        return UseAction.BOW;
//    }
//}

public class PowerFist extends AbstractElectricTool {
    public PowerFist() {
        super(new Item.Properties().tab(MPSObjects.creativeTab).stacksTo(1).defaultDurability(0));
    }

    @Override
    public int getUseDuration(ItemStack stack) {

        return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(handler->
                        handler.getActiveModule().getCapability(PowerModuleCapability.POWER_MODULE)
                                .filter(IRightClickModule.class::isInstance)
                                .map(IRightClickModule.class::cast)
                                .map(m-> m.getModuleStack().getUseDuration())
                                .orElse(72000))
                .orElse(72000);
    }

    @Override
    public boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        int playerEnergy = ElectricItemUtils.getPlayerEnergy(entityLiving);
        return powerFist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(iItemHandler -> iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class).stream().anyMatch(module ->
                        module.getCapability(PowerModuleCapability.POWER_MODULE)
                                .filter(IBlockBreakingModule.class::isInstance)
                                .map(IBlockBreakingModule.class::cast)
                                .map(pm-> pm.mineBlock(powerFist, worldIn, state, pos, entityLiving, playerEnergy))
                                .orElse(false))
                ).orElse(false);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack fist = player.getItemInHand(hand);
        final InteractionResultHolder<ItemStack> fallback = new InteractionResultHolder(InteractionResult.PASS, fist);
        if (hand != InteractionHand.MAIN_HAND) {
            return fallback;
        }

        return fist.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(handler-> handler.getActiveModule().
                        getCapability(PowerModuleCapability.POWER_MODULE)
                        .filter(IRightClickModule.class::isInstance)
                        .map(IRightClickModule.class::cast)
                        .map(rc-> rc.use(fist, level, player, hand)).orElse(fallback)).orElse(fallback);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStackIn, Player player, LivingEntity entity, InteractionHand hand) {
        return itemStackIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(handler->
                        handler.getActiveModule().getCapability(PowerModuleCapability.POWER_MODULE)
                                .filter(IRightClickModule.class::isInstance)
                                .map(IRightClickModule.class::cast)
                                .map(m-> m.interactLivingEntity(itemStackIn, player, entity, hand).getResult())
                                .orElse(InteractionResult.PASS)
                ).orElse(super.interactLivingEntity(itemStackIn, player, entity, hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entity) {
        return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(handler->
                        handler.getActiveModule().getCapability(PowerModuleCapability.POWER_MODULE)
                                .filter(IRightClickModule.class::isInstance)
                                .map(IRightClickModule.class::cast)
                                .map(m-> m.finishUsingItem(stack, worldIn, entity))
                                .orElse((super.finishUsingItem(stack, worldIn, entity))))
                .orElse(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .ifPresent(handler->{
                    ItemStack module = handler.getActiveModule();
                    module.getCapability(PowerModuleCapability.POWER_MODULE)
                            .filter(IRightClickModule.class::isInstance)
                            .map(IRightClickModule.class::cast)
                            .ifPresent(m-> m.releaseUsing(stack, worldIn, entityLiving, timeLeft));
                });
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context) {
        final InteractionResult fallback = InteractionResult.PASS;

        final InteractionHand hand = context.getHand();
        if (hand != InteractionHand.MAIN_HAND) {
            return fallback;
        }

        return context.getItemInHand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(handler->{
                    ItemStack module = handler.getActiveModule();
                    return module.getCapability(PowerModuleCapability.POWER_MODULE)
                            .filter(IRightClickModule.class::isInstance)
                            .map(IRightClickModule.class::cast)
                            .map(m-> m.onItemUseFirst(itemStack, context))
                            .orElse(fallback);
                }).orElse(fallback);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        final InteractionResult fallback = InteractionResult.PASS;

        final InteractionHand hand = context.getHand();
        if (hand != InteractionHand.MAIN_HAND) {
            return fallback;
        }

        return context.getItemInHand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(handler->{
                    ItemStack module = handler.getActiveModule();
                    return module.getCapability(PowerModuleCapability.POWER_MODULE)
                            .filter(IRightClickModule.class::isInstance)
                            .map(IRightClickModule.class::cast)
                            .map(m-> m.useOn(context)).orElse(fallback);
                }).orElse(fallback);
    }

    /**
     * Needed for overriding behaviour with modules
     * @param itemStack
     * @param state
     * @return
     */
//    @Override // FIXME!!
    public boolean canHarvestBlock(ItemStack itemStack, BlockState state) {
        return itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(iItemHandler -> iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)
                        .stream().anyMatch(module ->
                                module.getCapability(PowerModuleCapability.POWER_MODULE)
                                        .filter(IBlockBreakingModule.class::isInstance)
                                        .map(IBlockBreakingModule.class::cast)
                                        .map(pm ->pm.getEmulatedTool().isCorrectToolForDrops(state)).orElse(false)
                        )).orElse(false);
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
//    public Set<ToolType> getToolTypes(ItemStack itemStack) {
//        return itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(iItemHandler -> {
//                    Set<ToolType> retSet = new HashSet<>();
//                    if (!iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.PICKAXE_MODULE).isEmpty()) {
//                        retSet.add(ToolType.PICKAXE);
//                    }
//
//                    if (!iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.AXE_MODULE).isEmpty()) {
//                        retSet.add(ToolType.AXE);
//                    }
//
//                    if (!iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.SHOVEL_MODULE).isEmpty()) {
//                        retSet.add(ToolType.SHOVEL);
//                    }
//
//                    if (!iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.HOE_MODULE).isEmpty()) {
//                        retSet.add(ToolType.HOE);
//                    }
//
//                    return retSet;
//                }).orElse(new HashSet<>());
//    }

    /**
     * Current implementations of this method in child classes do not use the
     * entry argument beside stack. They just raise the damage on the stack.
     */
    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(iItemHandler -> iItemHandler.getOnlineModuleOrEmpty(MPSRegistryNames.MELEE_ASSIST_MODULE)
                            .getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(pm->{
                                Player player = (Player) attacker;
                                double drain = pm.applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                                if (ElectricItemUtils.getPlayerEnergy(player) > drain) {
                                    ElectricItemUtils.drainPlayerEnergy(player, (int) drain);
                                    double damage = pm.applyPropertyModifiers(MPSConstants.PUNCH_DAMAGE);
                                    double knockback = pm.applyPropertyModifiers(MPSConstants.PUNCH_KNOCKBACK);
                                    DamageSource damageSource = DamageSource.playerAttack(player);
                                    if (target.hurt(damageSource, (float) (int) damage)) {
                                        Vec3 lookVec = player.getLookAngle();
                                        target.push(lookVec.x * knockback, Math.abs(lookVec.y + 0.2f) * knockback, lookVec.z * knockback);
                                    }
                                }
                            }));
        }
        return true;
    }

    /**
     * Called before a block is broken.  Return true to prevent default block harvesting.
     *
     * Note: In SMP, this is called on both client and server sides!
     *
     * @param itemstack The current ItemStack
     * @param pos Block's position in world
     * @param player The Player that is wielding the item
     * @return True to prevent harvesting, false to continue as normal
     */
    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        super.onBlockStartBreak(itemstack, pos, player);
        return itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModeChangingItem.class::isInstance)
                .map(IModeChangingItem.class::cast)
                .map(iItemHandler -> iItemHandler.getActiveModule()
                        .getCapability(PowerModuleCapability.POWER_MODULE)
                        .filter(IMiningEnhancementModule.class::isInstance)
                        .map(IMiningEnhancementModule.class::cast)
                        .filter(pm ->pm.isModuleOnline())
                        .map(pm ->pm.onBlockStartBreak(itemstack, pos, player))
                        .orElse(false)).orElse(false);
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.sameItem(newStack);
    }

//    // Only fires on blocks that need a tool
//    @Override
//    public int getHarvestLevel(ItemStack itemStack, ToolType toolType, @Nullable Player player, @Nullable BlockState state) {
//        return itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                .filter(IModeChangingItem.class::isInstance)
//                .map(IModeChangingItem.class::cast)
//                .map(iItemHandler -> iItemHandler.getInstalledModulesOfType(IBlockBreakingModule.class)
//                        .stream().mapToInt(
//                                module -> module.getCapability(CapabilityPowerModule.POWER_MODULE)
//                                        .filter(IBlockBreakingModule.class::isInstance)
//                                        .map(IBlockBreakingModule.class::cast)
//                                        .map(pm-> pm.getEmulatedTool().getHarvestLevel(toolType, player, state)).orElse(-1))
//                        .max().orElse(-1)).orElse(-1);
//    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new PowerFistCap(stack);
    }
}