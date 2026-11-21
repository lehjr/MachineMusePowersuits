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

package lehjr.powersuits.common.item.module.tool.blockbreaking;

import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.ToolModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ShearsModule extends AbstractPowerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final BlockBreaker blockBreaking;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.blockBreaking = new BlockBreaker(module);
            powerModuleHolder = LazyOptional.of(() -> {
                blockBreaking.loadCapValues();
                return blockBreaking;
            });
        }

        static final ResourceLocation CABLE_INSULATOR = new ResourceLocation("energizedpower", "cable_insulator");

        public static class BlockBreaker extends ToggleableModule implements IBlockBreakingModule, IRightClickModule {
            public BlockBreaker(ItemStack module) {
                super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ToolModuleConfig.shearsModuleEnergyConsumptionBase, "FE");
                addBaseProperty(NuminaConstants.HARVEST_SPEED, ToolModuleConfig.shearsModuleEnergyHarvestSpeedBase, "x");
            }

            @Override
            public boolean isAllowed() {
                return ToolModuleConfig.shearsModuleIsAllowed;
            }

            @Override
            public int getTier() {
                return 1;
            }

            @Override
            public String getModuleGroup() {
                return "";
            }

            @Override
            public boolean mineBlock(ItemStack powerFist, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
                if (level.isClientSide() || state.is(BlockTags.FIRE)) {
                    return false;
                }

                if (ElectricItemUtils.getPlayerEnergy(entityLiving) > getEnergyUsage() && getEmulatedTool().isCorrectToolForDrops(state)) {
                    getEmulatedTool().mineBlock(level, state, pos, (Player) entityLiving);
                    ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage(), false);
                    return true;
                }
                return false;
            }

            @Override
            public ItemStack getEmulatedTool() {
                return new ItemStack(Items.SHEARS);
            }

            @Override
            public double getEnergyUsage() {
                return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }

            @Override
            public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
                event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(NuminaConstants.HARVEST_SPEED)));
            }

            @Override
            public InteractionResult useOn(UseOnContext context) {
                Player player = context.getPlayer();
                if (player == null) {
                    return InteractionResult.PASS;
                }

                double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
                double energyUsage = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);

                if (energyUsage > playerEnergy) {
                    return InteractionResult.PASS;
                }

                Level level = context.getLevel();
                BlockPos blockpos = context.getClickedPos();
                BlockState blockstate = level.getBlockState(blockpos);

                // 1. ForgeRegistries replaces BuiltInRegistries
                Item insulatorItem = ForgeRegistries.ITEMS.getValue(CABLE_INSULATOR);
                ItemStack cableInsulator = insulatorItem != null ? new ItemStack(insulatorItem, 18) : ItemStack.EMPTY;

                if (blockstate.is(BlockTags.WOOL) && !cableInsulator.isEmpty()) {
                    if (!level.isClientSide) {
                        if (!player.isCreative()) {
                            ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
                        }

                        level.destroyBlock(blockpos, false, player);
                        ItemEntity itemEntity = new ItemEntity(
                                level,
                                blockpos.getX() + 0.5D,
                                blockpos.getY() + 0.5D,
                                blockpos.getZ() + 0.5D,
                                cableInsulator,
                                0.0D, 0.0D, 0.0D
                        );
                        itemEntity.setPickUpDelay(20);
                        level.addFreshEntity(itemEntity);
                        level.playSound(null, blockpos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }

                // 2. Vanilla 1.20.1 ShearsItem#useOn logic (replaces ItemAbilities.SHEARS_TRIM)
                if (blockstate.getBlock() instanceof GrowingPlantHeadBlock growingPlant && !growingPlant.isMaxAge(blockstate)) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockpos, context.getItemInHand());
                    }

                    level.playSound(player, blockpos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    BlockState trimmedState = growingPlant.getMaxAgeState(blockstate);
                    level.setBlockAndUpdate(blockpos, trimmedState);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, trimmedState));
                    ElectricItemUtils.drainPlayerEnergy(player, getEnergyUsage(), false);

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }

                return InteractionResult.PASS;
            }

            @Override
            public InteractionResultHolder<ItemStack> interactLivingEntity(ItemStack itemStackIn, Player player, LivingEntity entity, InteractionHand hand) {
                // 3. IForgeShearable replaces IShearable and omits the Player parameter
                if (entity instanceof IForgeShearable target && ElectricItemUtils.getPlayerEnergy(player) > getEnergyUsage()) {
                    BlockPos pos = entity.blockPosition();
                    if (target.isShearable(getEmulatedTool(), entity.level(), pos)) {
                        Level level = entity.level();
                        if (level.isClientSide) {
                            return InteractionResultHolder.success(itemStackIn);
                        }

                        // Fortune level 0 (or calculate enchantment level if needed)
                        List<ItemStack> drops = target.onSheared(player, getEmulatedTool(), level, pos, 0);
                        drops.forEach(drop -> {
                            ItemEntity itemEntity = entity.spawnAtLocation(drop, 1.0F);
                            if (itemEntity != null) {
                                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(
                                        (level.random.nextFloat() - level.random.nextFloat()) * 0.1F,
                                        level.random.nextFloat() * 0.05F,
                                        (level.random.nextFloat() - level.random.nextFloat()) * 0.1F
                                ));
                            }
                        });

                        entity.gameEvent(GameEvent.SHEAR, player);
                        ElectricItemUtils.drainPlayerEnergy(player, getEnergyUsage(), false);
                        return InteractionResultHolder.consume(itemStackIn);
                    }
                }

                return InteractionResultHolder.pass(itemStackIn);
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}