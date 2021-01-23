package com.github.lehjr.powersuits.item.module.miningenhancement;

import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.github.lehjr.numina.util.capabilities.module.miningenhancement.IMiningEnhancementModule;
import com.github.lehjr.numina.util.capabilities.module.miningenhancement.MiningEnhancement;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class VeinMinerModule extends AbstractPowerModule {
    public VeinMinerModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IMiningEnhancementModule miningEnhancement;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.miningEnhancement = new Enhancement(module, EnumModuleCategory.MINING_ENHANCEMENT, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
            this.miningEnhancement.addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> miningEnhancement));
        }

        class Enhancement extends MiningEnhancement {
            public Enhancement(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            List<BlockPos> getPosList(Block block, BlockPos startPos, World world) {
                List<BlockPos> list = new ArrayList<BlockPos>() {{add(startPos);}};
                for (Direction direction : Direction.values()) {
                    int i = 0;
                    while(i < 1000) { // prevent race condition
                        BlockPos pos2 = startPos.offset(direction, i);

                        // no point looking beyond world limits
                        if (pos2.getY() >= world.getHeight() || pos2.getY() <= 0) {
                            break;
                        }

                        if(world.getBlockState(pos2).getBlock() == block) {
                            if (!list.contains(pos2)) {
                                list.add(pos2);
                            }
                            i++;
                        } else {
                            break;
                        }
                    }
                }
                return list;
            }

            void harvestBlocks(List<BlockPos> posList, World world) {
                for (BlockPos pos: posList) {
                    Block.replaceBlock(world.getBlockState(pos), Blocks.AIR.getDefaultState(), world, pos, Constants.BlockFlags.DEFAULT);
                }
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, PlayerEntity player) {
                BlockState state = player.world.getBlockState(posIn);
                Block block = state.getBlock();

                // filter out stone
                if (block == Blocks.STONE || block == Blocks.AIR) {
                    return false;
                }

                int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
                int energyUsage = this.getEnergyUsage();

                AtomicInteger bbModuleEnergyUsage = new AtomicInteger(0);

                itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(modeChanging -> {
                    if (modeChanging instanceof IModeChangingItem) {
                        for (ItemStack blockBreakingModule : ((IModeChangingItem) modeChanging).getInstalledModulesOfType(IBlockBreakingModule.class)) {
                            if (blockBreakingModule.getCapability(PowerModuleCapability.POWER_MODULE).map(b -> {
                                if(b instanceof IBlockBreakingModule) {
                                    if (((IBlockBreakingModule) b).canHarvestBlock(itemStack, state, player, posIn, playerEnergy - energyUsage)) {
                                        bbModuleEnergyUsage.addAndGet(((IBlockBreakingModule) b).getEnergyUsage());
                                        return true;
                                    }
                                }
                                return false;
                            }).orElse(false)) {
                                break;
                            }
                        }
                    }
                });

                // check if block is an ore
                List<ResourceLocation> defaultOreTags = MPSSettings.getOreList();
                Set<ResourceLocation> oretags = player.world.getBlockState(posIn).getBlock().getTags();
                boolean isOre = false;
                for ( ResourceLocation location : oretags ) {
                    if (defaultOreTags.contains(location)) {
                        isOre = true;
                        break;
                    }
                }

                if (isOre || MPSSettings.getBlockList().contains(block.getRegistryName())) {
                    int energyRequired = this.getEnergyUsage() + bbModuleEnergyUsage.get();

                    // does player have enough energy to break first block?
                    if (playerEnergy < energyRequired) {
                        return false;
                    }

                    List<BlockPos> posList = getPosList(block, posIn, player.world);
                    List<BlockPos> posListCopy = new ArrayList<>(posList);

                    int size = 0;
                    int newSize = posListCopy.size();

                    // is there more than one block?
                    if (newSize == 1) {
                        return false;
                    }

                    // does player have enough energy to break initial list?
                    if (newSize * energyRequired > playerEnergy) {
                        posList = new ArrayList<BlockPos>(){{add(posIn);}};
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
                        while(i < 100 && size != newSize) {
                            size = posListCopy.size();

                            outerLoop: for (BlockPos pos : posListCopy) {
                                List<BlockPos> posList2 = getPosList(block, pos, player.world);
                                for (BlockPos pos2 : posList2) {
                                    if(!posList.contains(pos2)) {
                                        // does player have enough energy to break initial list?
                                        if ((posList.size() +1) * energyRequired > playerEnergy) {
                                            i = 1000;
                                            break outerLoop;
                                        } else {
                                            posList.add(pos2);
                                        }
                                    }
                                }
                            }
                            newSize = posList.size();
                            posListCopy = new ArrayList<>(posList);
                            i++;
                        }
                    }

                    if (!player.world.isRemote()) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyRequired * posList.size());
                    }
                    harvestBlocks(posList, player.world);
                }
                return false;
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }
    }
}