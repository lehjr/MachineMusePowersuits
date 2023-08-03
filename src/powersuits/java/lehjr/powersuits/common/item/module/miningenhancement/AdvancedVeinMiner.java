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

package lehjr.powersuits.common.item.module.miningenhancement;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.miningenhancement.MiningEnhancement;
import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.render.chameleon.Chameleon;
import lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import lehjr.numina.common.capabilities.render.highlight.Highlight;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
public class AdvancedVeinMiner extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Enhancement miningEnhancement;
        private final LazyOptional<IPowerModule> powerModuleHolder;
        private final Chameleon chameleon;

        private final LazyOptional<IChameleon> chameleonHolder;
        private final Highlighter highlight;
        private final LazyOptional<IHighlight> highlightHolder;


        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.miningEnhancement = new Enhancement(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
                addBaseProperty(MPSConstants.AOE2_LIMIT, 1);
                addIntTradeoffProperty(MPSConstants.AOE2_LIMIT, MPSConstants.AOE2_LIMIT, 59, "Blocks", 1, 0);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                miningEnhancement.loadCapValues();
                return miningEnhancement;
            });

            this.chameleon = new Chameleon(module);
            chameleonHolder = LazyOptional.of(() -> {
                chameleon.loadCapValues();
                return chameleon;
            });

            this.highlight = new Highlighter();
            highlightHolder = LazyOptional.of(() -> highlight);
        }

        class Enhancement extends MiningEnhancement implements IRightClickModule {
            public Enhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
                if (context.getHand().equals(InteractionHand.MAIN_HAND) && context.getLevel().isClientSide()) {
                    if (KeymappingKeyHandler.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT) || KeymappingKeyHandler.isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                        BlockHitResult rayTraceResult = getPlayerPOVHitResult(context.getLevel(), context.getPlayer(), ClipContext.Fluid.NONE);
                        if (!(rayTraceResult == null)) {
                            chameleon.setTargetBlock(context.getLevel().getBlockState(rayTraceResult.getBlockPos()).getBlock());
                        }
                    }
                }
                return super.onItemUseFirst(stack, context);
            }

            void harvestBlocks(List<BlockPos> posList, Level world, Player player, ItemStack itemStack) {
                for (BlockPos pos: posList) {
//                    Block.updateOrDestroy(world.getBlockState(pos), Blocks.AIR.defaultBlockState(), world, pos, Block.UPDATE_ALL);

                    if (!world.isClientSide()) {
                        BlockEntity blockEntity = world.getBlockEntity(pos);
                        // setup drops checking for enchantments
                        Block.dropResources(world.getBlockState(pos), world, pos, blockEntity, player, itemStack);
                        // destroy block but don't drop default drops because they're already set above
                        player.level.destroyBlock(pos, false, player, 512);
                    }

                }
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, Player player) {
                BlockState state = player.level.getBlockState(posIn);
                Block block = state.getBlock();
                chameleon.loadCapValues();
                // abort if block is not set
                if (block == Blocks.AIR || block == Blocks.BEDROCK || !chameleon.getTargetBlock().filter(targetBlock -> targetBlock == block).isPresent()) {
                    return false;
                }

                double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
                double energyUsage = this.getEnergyUsage();

                AtomicInteger bbModuleEnergyUsage = new AtomicInteger(0);

                itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(modeChanging -> {
                            for (ItemStack blockBreakingModule : modeChanging.getInstalledModulesOfType(IBlockBreakingModule.class)) {
                                if (blockBreakingModule.getCapability(NuminaCapabilities.POWER_MODULE)
                                        .filter(IBlockBreakingModule.class::isInstance)
                                        .map(IBlockBreakingModule.class::cast)
                                        .map(b -> {
                                            if (b.canHarvestBlock(itemStack, state, player, posIn, playerEnergy - energyUsage)) {
                                                bbModuleEnergyUsage.addAndGet(b.getEnergyUsage());
                                                return true;
                                            }
                                            return false;
                                        }).orElse(false)) {
                                    break;
                                }
                            }
                        });


                if (true) {
                    int energyRequired = this.getEnergyUsage() + bbModuleEnergyUsage.get();

                    // does player have enough energy to break first block?
                    if (playerEnergy < energyRequired) {
                        return false;
                    }

                    NonNullList<BlockPos> posList = getPosList(block, posIn, player.level);
                    NonNullList<BlockPos> posListCopy = NonNullList.create();
                    for (BlockPos pos : posList) {
                        posListCopy.add(pos);
                    }

                    int size = 0;
                    int newSize = posListCopy.size();

                    // is there more than one block?
                    if (newSize == 1) {
                        return false;
                    }

                    // does player have enough energy to break initial list?
                    if (newSize * energyRequired > playerEnergy) {
                        posList = NonNullList.create();
                        posList.add(posIn);
                        posListCopy.remove(posIn);

                        // repopulate list so the player has just enough energy
                        for (BlockPos pos : posListCopy) {
                            if ((posList.size() + 1) * energyRequired > playerEnergy) {
                                break;
                            } else {
                                posList.add(pos);
                            }
                        }
                        // create larger list
                    } else {
                        int i = 0;
                        while (i < 100 && size != newSize && posList.size() <= applyPropertyModifiers(MPSConstants.AOE2_LIMIT)) {
                            size = posListCopy.size();

                            outerLoop:
                            for (BlockPos pos : posListCopy) {
                                NonNullList<BlockPos> posList2 = getPosList(block, pos, player.level);
                                for (BlockPos pos2 : posList2) {
                                    if (!posList.contains(pos2)) {
                                        // does player have enough energy to break initial list?
                                        if ((posList.size() + 1) * energyRequired > playerEnergy) {
                                            i = 1000;
                                            break outerLoop;
                                        } else {
                                            posList.add(pos2);
                                        }
                                    }
                                }
                            }
                            newSize = posList.size();
                            posListCopy = NonNullList.create();
                            for (BlockPos pos : posList) {
                                posListCopy.add(pos);
                            }
                            i++;
                        }
                    }

                    // All blocks are the same, otherwise this would have to be calculated on the fly

                    if (!player.level.isClientSide()) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyRequired * posList.size());
                    }
                    harvestBlocks(posList, player.level, player, itemStack);
                }
                return false;
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }

        // TODO?? : check if can break these blocks before adding to list? (not very efficient)
        class Highlighter extends Highlight {
            @OnlyIn(Dist.CLIENT)
            NonNullList<BlockPos> findBlockPositionsClient(BlockPos targetPos) {
                chameleon.loadCapValues();
                return chameleon.getTargetBlock().map(targetBlock -> getPosList(targetBlock, targetPos, Minecraft.getInstance().player.level)).orElse(NonNullList.create());
            }

            @Override
            public NonNullList<BlockPos> getBlockPositions(BlockHitResult rayTraceResult) {
                return findBlockPositionsClient(rayTraceResult.getBlockPos());
            }
        }

        NonNullList<BlockPos> getPosList(Block block, BlockPos startPos, Level world) {
            NonNullList<BlockPos> list = NonNullList.create();
            if (world.getBlockState(startPos).getBlock() == block) {
                list.add(startPos.immutable());

                int i = 1;
                // this is really, really stupid and if you have a better way, use it.
                outerLoop:
                while (list.size() <= miningEnhancement.applyPropertyModifiers(MPSConstants.AOE2_LIMIT) && i < 2 /* set at 2 for performance reassons */) {
                    for (BlockPos.MutableBlockPos mutable : BlockPos.spiralAround(startPos, i, Direction.EAST, Direction.SOUTH)) {
                        for (BlockPos.MutableBlockPos mutable2 : BlockPos.spiralAround(mutable, i, Direction.UP, Direction.NORTH)) {
                            for (BlockPos.MutableBlockPos mutable3 : BlockPos.spiralAround(mutable2, i, Direction.WEST, Direction.DOWN)) {
                                if (world.getBlockState(mutable3).getBlock() == block && !list.contains(mutable3)) {
                                    list.add(new BlockPos(mutable3).immutable());
                                }
                            }
                        }
                    }
                    i++;
                }
            }
            return list;
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            final LazyOptional<T> chameleonCapability = NuminaCapabilities.CHAMELEON.orEmpty(capability, chameleonHolder);
            if (chameleonCapability.isPresent()) {
                return chameleonCapability;
            }
            final LazyOptional<T> highlightCapability = NuminaCapabilities.HIGHLIGHT.orEmpty(capability, highlightHolder);
            if (highlightCapability.isPresent()) {
                return highlightCapability;
            }
            return LazyOptional.empty();
        }
    }
}



