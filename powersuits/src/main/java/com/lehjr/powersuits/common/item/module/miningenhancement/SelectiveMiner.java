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
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.TagUtils;
import com.lehjr.powersuits.client.control.KeymappingKeyHandler;
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
            // TODO: finish this: overclock overrides for block breaking modules or recheck breaking speed?
            //            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.stonePickAxeModuleEnergyConsumptionBase, "FE");
            //            addBaseProperty(NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.stonePickAxeModuleHarvestSpeedBase, "x");
            //            addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.stonePickAxeModuleEnergyConsumptionOverclockMultiplier);
            //            addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.stonePickAxeModuleHarvestSpeedOverclockMultiplier);

            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
            addBaseProperty(MPSConstants.SELECTIVE_MINER_LIMIT, 1);
            // FIXME: add overclock
            addIntTradeoffProperty(MPSConstants.SELECTIVE_MINER_LIMIT, MPSConstants.SELECTIVE_MINER_LIMIT, 59, "Blocks", 1, 0);
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
            return true;
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






//
//            // Basically a single module wrapped in a list because all of the target blocks should be the same
//            NonNullList<IBlockBreakingModule> modules = NonNullList.create();
//
//            AtomicInteger bbModuleEnergyUsage = new AtomicInteger(0);
//            IModeChangingItem mci = itemStack.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
//            boolean canHarvest = false;
//            if(mci != null) {
//                for (int i = 0; i< mci.getSlots(); i++) {
//                    ItemStack module = mci.getStackInSlot(i);
//                    IPowerModule pm = mci.getModuleCapability(module);
//                    if (pm instanceof IBlockBreakingModule bbm) {
//                        if(bbm.canHarvestBlock(itemStack, state, player, pos, playerEnergy - energyUsage)) {
//                            canHarvest = true;
//                            bbModuleEnergyUsage.addAndGet(bbm.getEnergyUsage());
//                            modules.add(bbm);
//                            break;
//                        }
//                    }
//                }
//            }
//
//            if (!canHarvest) {
//                return false;
//            }
//
//            int energyRequired = this.getEnergyUsage() + bbModuleEnergyUsage.get();
//
//            // does player have enough energy to break first block?
//            if (playerEnergy < energyRequired) {
//                return false;
//            }







//            NonNullList<BlockPos> posMap = getPosList(block, posIn, player.level());
//            NonNullList<BlockPos> posListCopy = NonNullList.create();
//            for (BlockPos pos : posMap) {
//                posListCopy.add(pos);
//            }
//
//            int size = 0;
//            int newSize = posListCopy.size();
//
//            // is there more than one block?
//            if (newSize == 1) {
//                return false;
//            }
//
//            // does player have enough energy to break initial list?
//            if (newSize * energyRequired > playerEnergy) {
//                posMap = NonNullList.create();
//                posMap.add(posIn);
//                posListCopy.remove(posIn);
//
//                // repopulate list so the player has just enough energy
//                for (BlockPos pos : posListCopy) {
//                    if ((posMap.size() + 1) * energyRequired > playerEnergy) {
//                        break;
//                    } else {
//                        posMap.add(pos);
//                    }
//                }
//                // create larger list
//            } else {
//                int i = 0;
//                while (i < 100 && size != newSize && posMap.size() <= applyPropertyModifiers(MPSConstants.SELECTIVE_MINER_LIMIT)) {
//                    size = posListCopy.size();
//
//                    outerLoop:
//                    for (BlockPos pos : posListCopy) {
//                        NonNullList<BlockPos> posList2 = getPosList(block, pos, player.level());
//                        for (BlockPos pos2 : posList2) {
//                            if (!posMap.contains(pos2)) {
//                                // does player have enough energy to break initial list?
//                                if ((posMap.size() + 1) * energyRequired > playerEnergy) {
//                                    i = 1000;
//                                    break outerLoop;
//                                } else {
//                                    posMap.add(pos2);
//                                }
//                            }
//                        }
//                    }
//                    newSize = posMap.size();
//                    posListCopy = NonNullList.create();
//                    for (BlockPos pos : posMap) {
//                        posListCopy.add(pos);
//                    }
//                    i++;
//                }
//            }
//
//            // All blocks are the same, otherwise this would have to be calculated on the fly
//
//            if (!player.level().isClientSide()) {
//                ElectricItemUtils.drainPlayerEnergy(player, energyRequired * posMap.size(), false);
//            }
//            harvestBlocks(posMap, player.level(), player, itemStack);
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

//            if (modules.isEmpty()) {
//                return retMap;
//            }
//
//            double playerEnergyRemaining = playerEnergy;
//
//            BlockPos pos = result.getBlockPos();
//            BlockState state = level.getBlockState(pos);
//            if (getTargetBlockState().isAir() || state != getTargetBlockState()) {
//                return retMap;
//            }
//
//            IBlockBreakingModule bbm = null;
//
//            int i = 1;
//            // this is really, really stupid and if you have a better way, use it.
//            outerLoop:
//            while (retMap.size() <= applyPropertyModifiers(MPSConstants.SELECTIVE_MINER_LIMIT) && i < 2 /* set at 2 for performance reassons */) {
//                for (BlockPos.MutableBlockPos mutable : BlockPos.spiralAround(pos, i, Direction.EAST, Direction.SOUTH)) {
//                    for (BlockPos.MutableBlockPos mutable2 : BlockPos.spiralAround(mutable, i, Direction.UP, Direction.NORTH)) {
//                        for (BlockPos.MutableBlockPos mutable3 : BlockPos.spiralAround(mutable2, i, Direction.WEST, Direction.DOWN)) {
//                            BlockPos posFinal = mutable3.immutable();
//
//                            if (level.getBlockState(posFinal) == state && retMap.stream().noneMatch(p->p.pos()==posFinal)) {
//                                if (player.isCreative()) {
//                                    if(retMap.isEmpty() || retMap.stream().noneMatch(p->p.pos()==posFinal)) {
//                                        retMap.add(new BlockPostionData(posFinal, true, null));
//                                        break;
//                                    }
//                                } else {
//                                    if (bbm == null) {
//                                        for (IBlockBreakingModule module : modules) {
//                                            if (module.canHarvestBlock(ItemStack.EMPTY, state, player, posFinal, playerEnergyRemaining)) {
//                                                retMap.add(new BlockPostionData(posFinal, true, bbm));
//                                                playerEnergyRemaining -= module.getEnergyUsage();
//                                                bbm = module;
//                                            }
//                                        }
//                                    } else if (bbm.canHarvestBlock(ItemStack.EMPTY, state, player, mutable, playerEnergyRemaining)) {
//                                        retMap.add(new BlockPostionData(posFinal, true, bbm));
//                                        playerEnergyRemaining -= bbm.getEnergyUsage();
//
//                                        // ran out of energy?
//                                    } else {
//                                        return retMap;
//                                    }
//
//                                }
//                            }
//                        }
//                    }
//                }
//                i++;
//            }
            return retMap;
        }
    }
}
