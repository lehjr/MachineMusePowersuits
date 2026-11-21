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

import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.enhancement.MiningEnhancement;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.block.CheckOreBlocksFrom;
import lehjr.powersuits.common.config.module.MiningEnhancementModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class VeinMinerModule extends AbstractPowerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Enhancement miningEnhancement;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.miningEnhancement = new Enhancement(module);

            powerModuleHolder = LazyOptional.of(() -> {
                miningEnhancement.loadCapValues();
                return miningEnhancement;
            });
        }

        static final TagKey<Block> ORE_TAG = Tags.Blocks.ORES;

        public static class Enhancement extends MiningEnhancement implements IHighlight {

            public Enhancement(@Nonnull ItemStack module) {
                super(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MiningEnhancementModuleConfig.veinMinerModuleEnergyConsumptionBase, "FE");
                addBaseProperty(NuminaConstants.HARVEST_SPEED, MiningEnhancementModuleConfig.veinMinerModuleHarvestSpeedBase, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MiningEnhancementModuleConfig.veinMinerModuleEnergyConsumptionOverclockMultiplier);
                addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, MiningEnhancementModuleConfig.veinMinerModuleHarvestSpeedOverclockMultiplier);
                addBaseProperty(MPSConstants.SELECTIVE_MINER_LIMIT, 1);
                addIntTradeoffProperty(MPSConstants.SELECTIVE_MINER_LIMIT, MPSConstants.SELECTIVE_MINER_LIMIT, MiningEnhancementModuleConfig.veinMinerModuleBlockLimit, "Blocks", 1, 0);
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
            public boolean isAllowed() {
                return MiningEnhancementModuleConfig.veinMinerModuleIsAllowed;
            }

            @Override
            public double getEnergyUsage() {
                return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemStack, BlockHitResult hitResult, Player player, Level level) {
                // Don't cancel if this isn't online...
                if (!isModuleOnline()) {
                    return false;
                }

                BlockPos pos = hitResult.getBlockPos();

                BlockState state = level.getBlockState(pos);
                Block block = state.getBlock();

                // abort if block is not set
                if (state.isAir() || (!player.isCreative() && block == Blocks.BEDROCK) || !block.defaultBlockState().is(ORE_TAG)) {
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

                    HashMap<BlockPostionData, Integer> posMap = getBlockPositions(itemStack, hitResult, player, level, modules, playerEnergy);

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
            public HashMap<BlockPostionData, Integer> getBlockPositions(@NotNull ItemStack tool,
                                                                        @NotNull BlockHitResult result,
                                                                        @NotNull Player player,
                                                                        @NotNull Level level,
                                                                        NonNullList<IBlockBreakingModule> modules,
                                                                        double playerEnergy) {

                HashMap<BlockPostionData, Integer> retMap = new HashMap<>();
                // FIXME

                if (modules.isEmpty()) {
                    return retMap;
                }

                double playerEnergyRemaining = playerEnergy;

                BlockPos pos = result.getBlockPos();
                BlockState state = level.getBlockState(pos);
                Block block = state.getBlock();

                if (state.isAir() || (!player.isCreative() && block == Blocks.BEDROCK) || !block.defaultBlockState().is(ORE_TAG)) {
                    return retMap;
                }

                // Only one BBM is needed since the state is the same for each block
                IBlockBreakingModule bbm = null;

                // check for effective tool first
                for (IBlockBreakingModule module : modules) {
                    if (module.canHarvestBlock(ItemStack.EMPTY, state, player, pos, playerEnergyRemaining)) {
                        bbm = module;
                    }
                }

                double energyUsage = 0;

                // simulate the vanilla block breaking mechanic, draining power instead of draining durability
                // this would be for things like glass.
                if(bbm == null && !state.requiresCorrectToolForDrops()) {
                    // Just go with the module that uses the least amount of energy
                    double toolEnergyTest = playerEnergyRemaining;
                    for (IBlockBreakingModule module : modules) {
                        //                    if(module.isToolEffective(level, pos)) {
                        energyUsage = module.getEnergyUsage();
                        if (module.getEnergyUsage() < toolEnergyTest) {
                            toolEnergyTest = energyUsage;
                            bbm = module;
                        }
                    }
                }

                if(bbm == null) {
                    return retMap;
                }

                energyUsage = bbm.getEnergyUsage() + getEnergyUsage();

                CheckOreBlocksFrom bcf = new CheckOreBlocksFrom(pos, 65);
                NonNullList<BlockPos> matches = bcf.startCheck(level);

                for(BlockPos posCheck : matches) {
                    if (player.isCreative()) {
                        retMap.put(new BlockPostionData(posCheck, true, null), 2);
                        break;
                    } else {
                        if(playerEnergyRemaining >= energyUsage) { // just need to know if there is enough energy left to break the block
                            retMap.put(new BlockPostionData(posCheck, true, bbm), 1);
                            playerEnergyRemaining -= energyUsage;
                            // ran out of energy?
                        } else {
                            break;
                        }
                    }
                }

                return retMap;
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