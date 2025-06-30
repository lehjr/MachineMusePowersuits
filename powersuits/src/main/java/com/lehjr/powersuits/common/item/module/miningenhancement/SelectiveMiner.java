package com.lehjr.powersuits.common.item.module.miningenhancement;

import com.google.common.util.concurrent.AtomicDouble;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.enhancement.MiningEnhancement;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import com.lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import com.lehjr.numina.common.capabilities.render.highlight.IHighlight;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.TagUtils;
import com.lehjr.powersuits.client.control.KeymappingKeyHandler;
import com.lehjr.powersuits.common.config.module.MiningEnhancementModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class SelectiveMiner extends AbstractPowerModule {
    public static class Enhancement extends MiningEnhancement implements IRightClickModule, IHighlight, IChameleon {
        public Enhancement(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MiningEnhancementModuleConfig.selectiveMinerModuleEnergyConsumptionBase, "FE");
            addBaseProperty(NuminaConstants.HARVEST_SPEED, MiningEnhancementModuleConfig.selectiveMinerModuleHarvestSpeedBase, "x");
            addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MiningEnhancementModuleConfig.selectiveMinerModuleEnergyConsumptionOverclockMultiplier);
            addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, MiningEnhancementModuleConfig.selectiveMinerModuleHarvestSpeedOverclockMultiplier);
            addBaseProperty(MPSConstants.SELECTIVE_MINER_LIMIT, 1);
            addIntTradeoffProperty(MPSConstants.SELECTIVE_MINER_LIMIT, MPSConstants.SELECTIVE_MINER_LIMIT, MiningEnhancementModuleConfig.selectiveMinerModuleBlockLimit, "Blocks", 1, 0);
        }

        @Override
        public BlockState getTargetBlockState() {
            return TagUtils.getModuleBlockState(getModule());
        }

        @Override
        public InteractionResultHolder<ItemStack> use(ItemStack itemStackIn, Level level, Player playerIn, InteractionHand hand) {
            if (level.isClientSide()) {
                if (KeymappingKeyHandler.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT) || KeymappingKeyHandler.isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                    BlockHitResult rayTraceResult = getPlayerPOVHitResult(level, playerIn, ClipContext.Fluid.NONE);
                    if ((rayTraceResult.getType() ==  HitResult.Type.BLOCK)) {
                        setTargetBlockStateByPos(rayTraceResult.getBlockPos());
                    } else if (rayTraceResult.getType() == HitResult.Type.MISS) {
                        setClearTargetBlockState();
                    }
                }
            }

            return super.use(itemStackIn, level, playerIn, hand);
        }

        @Override
        public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
            if(context.getPlayer() != null) {
                if (context.getHand().equals(InteractionHand.MAIN_HAND) && context.getLevel().isClientSide()) {
                    if (KeymappingKeyHandler.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT) || KeymappingKeyHandler.isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                        BlockHitResult rayTraceResult = getPlayerPOVHitResult(context.getLevel(), context.getPlayer(), ClipContext.Fluid.NONE);
                        if ((rayTraceResult.getType() ==  HitResult.Type.BLOCK)) {
                            setTargetBlockStateByPos(rayTraceResult.getBlockPos());
                        } else if (rayTraceResult.getType() == HitResult.Type.MISS) {
                            setClearTargetBlockState();
                        }
                    }
                }
            }
            return super.onItemUseFirst(stack, context);
        }

        @Override
        public boolean isAllowed() {
            return MiningEnhancementModuleConfig.selectiveMinerModuleIsAllowed;
        }

        // this only harvesting 1 type of block
        @Override
        public boolean onBlockStartBreak(ItemStack itemStack, BlockHitResult hitResult, Player player, Level level) {
            BlockPos pos = hitResult.getBlockPos();

            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            if (state != getTargetBlockState()) {
                return false;
            }


            // abort if block is not set
            if (block == Blocks.AIR || (!player.isCreative() && block == Blocks.BEDROCK) || getTargetBlockState() != state) {
                return false;
            }

            IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(itemStack);
            if (mci != null) {
                NonNullList<IBlockBreakingModule> modules = NonNullList.create();

                for (int i = 0; i < mci.getSlots(); i++) {
                    ItemStack module = mci.getStackInSlot(i);
                    IPowerModule pm = mci.getModuleCapability(module);
                    if (pm instanceof IBlockBreakingModule bm) {
                        modules.add(bm);
                    }
                }

                double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

                AtomicDouble energyUsage = new AtomicDouble(0);

                HashMap<BlockPostionData, Integer>  posMap = getBlockPositions(itemStack, hitResult, player, level, modules, playerEnergy);

                final double moduleEnergyUsage = getEnergyUsage();

                posMap.forEach((blockPostionData, miningLevel) -> {
                    if (blockPostionData.canHarvest()) {
                        BlockPos blockPos = blockPostionData.pos().immutable();
                        BlockEntity blockEntity = level.getBlockEntity(blockPos);
                        // setup drops checking for enchantments
                        Block.dropResources(state, level, blockPos, blockEntity, player, itemStack);
                        // destroy block but don't drop default drops because they're already set above
                        level.destroyBlock(blockPos, false, player, 512);

                        // if creative then bbm will be null
                        if (!player.isCreative() && state.requiresCorrectToolForDrops() && blockPostionData.bbm() != null) {
                            IBlockBreakingModule bbm = blockPostionData.bbm();
                            if(miningLevel > 0) {
                                energyUsage.getAndAdd(bbm.getEnergyUsage());
                            }
                            if(miningLevel > 1) {
                                energyUsage.getAndAdd(moduleEnergyUsage);
                            }
                        }
                    }
                });
                ElectricItemUtils.drainPlayerEnergy(player, energyUsage.get(), false);
            }
            return false;
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public HashMap<BlockPostionData, Integer> getBlockPositions(@Nonnull ItemStack tool, @Nonnull BlockHitResult result, @Nonnull Player player, @Nonnull Level level, NonNullList<IBlockBreakingModule> modules, double playerEnergy) {
            HashMap<BlockPostionData, Integer> retMap = new HashMap<>();
            // FIXME

            if (modules.isEmpty()) {
                return retMap;
            }

            double playerEnergyRemaining = playerEnergy;

            BlockPos pos = result.getBlockPos();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();

            if (getTargetBlockState().isAir() || state != getTargetBlockState()) {
                return retMap;
            }

            IBlockBreakingModule bbm = null;

            int i = 1;
            // this is really, really stupid and if you have a better way, use it.
            outerLoop:
            while (retMap.size() <= applyPropertyModifiers(MPSConstants.SELECTIVE_MINER_LIMIT) && i < 2 /* set at 2 for performance reasons */) {
                for (BlockPos.MutableBlockPos mutable : BlockPos.spiralAround(pos, i, Direction.EAST, Direction.SOUTH)) {
                    for (BlockPos.MutableBlockPos mutable2 : BlockPos.spiralAround(mutable, i, Direction.UP, Direction.NORTH)) {
                        for (BlockPos.MutableBlockPos mutable3 : BlockPos.spiralAround(mutable2, i, Direction.WEST, Direction.DOWN)) {
                            BlockState temp = level.getBlockState(mutable3);
                            if(temp.isAir() || temp.getBlock() != block) {
                                continue;
                            }

                            BlockPos posFinal = mutable3.immutable();
                            if (player.isCreative()) {
                                retMap.put(new BlockPostionData(posFinal.mutable(), true, null), 2);
                                break;
                            } else {
                                if (bbm == null) {
                                    for (IBlockBreakingModule module : modules) {
                                        if (module.canHarvestBlock(ItemStack.EMPTY, state, player, posFinal, playerEnergyRemaining)) {
                                            retMap.put(new BlockPostionData(posFinal.immutable(), true, bbm), 1);
                                            playerEnergyRemaining -= module.getEnergyUsage();
                                            bbm = module;
                                        }
                                    }
                                } else if (bbm.canHarvestBlock(ItemStack.EMPTY, state, player, mutable, playerEnergyRemaining)) {
                                    retMap.put(new BlockPostionData(posFinal, true, bbm), 1);
                                    playerEnergyRemaining -= bbm.getEnergyUsage();
                                    // ran out of energy?
                                } else {
                                    break outerLoop;
                                }
                            }
                        }
                    }
                }
                i++;
            }

            if(player.isCreative()) {
                return retMap;
            }

            final int energyUsage = getEnergyUsage();
            if (energyUsage <= playerEnergyRemaining) {
                HashMap<BlockPostionData, Integer> retMap2 = new HashMap<>();
                AtomicDouble playerEnergyRemainingAI = new AtomicDouble(playerEnergyRemaining);

                retMap.forEach((key, value) -> {
                    int val = value;
                    if (val == 0) {
                        retMap2.put(key, 0);
                    } else {
                        // Energy usage of the block breaking module was already removed in the while loop
                        if (playerEnergyRemainingAI.get() >= energyUsage) {
                            playerEnergyRemainingAI.getAndAdd(-energyUsage);
                            retMap2.put(key, 2);
                        } else {
                            retMap2.put(key, value);
                        }
                    }
                });
                return retMap2;
            }
            return retMap;
        }
    }
}
