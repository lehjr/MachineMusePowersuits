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
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class VeinMinerModule extends AbstractPowerModule {
    ResourceLocation ORE_TAG = new ResourceLocation("forge", "ores");
    String STORAGE_BLOCK = "storage_blocks/raw_";

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
            this.miningEnhancement = new Enhancement(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                miningEnhancement.loadCapValues();
                return miningEnhancement;
            });
        }

        class Enhancement extends MiningEnhancement {
            public Enhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            List<BlockPos> getPosList(Block block, BlockPos startPos, Level world, String oreType) {
                List<BlockPos> list = new ArrayList<>() {{
                    add(startPos);
                }};
                for (Direction direction : Direction.values()) {
                    int i = 0;
                    while(i < 1000) { // prevent race condition
                        BlockPos pos2 = startPos.relative(direction, i);

                        // no point looking beyond world limits
                        if (pos2.getY() >= world.getMaxBuildHeight() || pos2.getY() <= world.getMinBuildHeight()) {
                            break;
                        }

                        BlockState state = world.getBlockState(pos2);
                        List<TagKey<Block>> tagsToTest = state.getTags().collect(Collectors.toList());
                        String oreTypeToTest = getOreType(tagsToTest);
                        // prioritize checking for same ore type
                        if (!oreType.isBlank() && !oreTypeToTest.isBlank() && oreType.equals(oreTypeToTest)) {
                            if (!list.contains(pos2)) {
                                list.add(pos2);
                            }
                            i++;
                        } else if (world.getBlockState(pos2).getBlock() == block) {
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

            void harvestBlocks(List<BlockPos> posList, Level world, Player player, ItemStack itemStack) {
                for (BlockPos pos: posList) {
//                    Block.updateOrDestroy(world.getBlockState(pos), Blocks.AIR.defaultBlockState(), world, pos, Block.UPDATE_ALL);

                    if (!world.isClientSide()) {
                        BlockEntity blockEntity = world.getBlockEntity(pos);
                        // setup drops checking for enchantments
                        Block.dropResources(world.getBlockState(pos), world, pos, blockEntity, player, itemStack);
                        // destroy block but don't drop default drops because they're already set above
                        player.level().destroyBlock(pos, false, player, 512);
                    }

                }
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, Player player) {
                BlockState state = player.level().getBlockState(posIn);
                Block block = state.getBlock();

                // filter out stone
                if (block == Blocks.STONE || block == Blocks.AIR) {
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
                            if (blockBreakingModule.getCapability(NuminaCapabilities.POWER_MODULE).map(b -> {
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
                    });

                // check if block is an ore
                List<ResourceLocation> defaultOreTags = MPSSettings.getOreList();
                List<TagKey<Block>> oretags = state.getTags().collect(Collectors.toList());

                /*
                    ------------------
                    Iron Ore
                    ------------------
                    tag: TagKey[minecraft:block / minecraft:iron_ores]
                    tag: TagKey[minecraft:block / minecraft:needs_stone_tool]
                    tag: TagKey[minecraft:block / forge:ores_in_ground/stone]
                    tag: TagKey[minecraft:block / minecraft:mineable/pickaxe]
                    tag: TagKey[minecraft:block / forge:ore_rates/singular]
                    tag: TagKey[minecraft:block / forge:ores/iron]
                    tag: TagKey[minecraft:block / forge:ores]
                    tag: TagKey[minecraft:block / minecraft:overworld_carver_replaceables]
                    tag: TagKey[minecraft:block / minecraft:snaps_goat_horn]

                    ------------------
                    Block of Raw Iron
                    ------------------
                    tag: TagKey[minecraft:block / minecraft:mineable/pickaxe]
                    tag: TagKey[minecraft:block / forge:storage_blocks/raw_iron]
                    tag: TagKey[minecraft:block / minecraft:needs_stone_tool]
                    tag: TagKey[minecraft:block / minecraft:overworld_carver_replaceables]
                    tag: TagKey[minecraft:block / forge:storage_blocks]

                    ------------------
                    Deepslate Iron Ore
                    ------------------
                    tag: TagKey[minecraft:block / forge:ore_rates/singular]
                    tag: TagKey[minecraft:block / minecraft:overworld_carver_replaceables]
                    tag: TagKey[minecraft:block / minecraft:needs_stone_tool]
                    tag: TagKey[minecraft:block / forge:ores/iron]
                    tag: TagKey[minecraft:block / minecraft:mineable/pickaxe]
                    tag: TagKey[minecraft:block / forge:ores]
                    tag: TagKey[minecraft:block / minecraft:iron_ores]
                    tag: TagKey[minecraft:block / forge:ores_in_ground/deepslate]
                 */

                String oreType = getOreType(oretags);
                boolean isOre = !oreType.isBlank() || oretags.stream().anyMatch(tag-> defaultOreTags.contains(tag.location()));

                if (isOre || MPSSettings.getBlockList().contains(ForgeRegistries.BLOCKS.getKey(block))) {
                    int energyRequired = this.getEnergyUsage() + bbModuleEnergyUsage.get();

                    // does player have enough energy to break first block?
                    if (playerEnergy < energyRequired) {
                        return false;
                    }

                    List<BlockPos> posList = getPosList(block, posIn, player.level(), oreType);
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

                            outerLoop:
                            for (BlockPos pos : posListCopy) {
                                List<BlockPos> posList2 = getPosList(block, pos, player.level(), oreType);
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

                    if (!player.level().isClientSide()) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyRequired * posList.size());
                    }
                    harvestBlocks(posList, player.level(), player, itemStack);
                }
                return false;
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }

    static String getOreType(List<TagKey<Block>> oretags) {
        String oreType ="";
        for (TagKey<Block> tagKey: oretags) {
            String path = tagKey.location().getPath();
            if (path.contains("ores/")) {
                oreType = path.replace("ores/", "");
            } else if (path.contains("storage_blocks/raw_")) {
                oreType = path.replace("storage_blocks/raw_", "");
            }
        }
        return oreType;
    }
}