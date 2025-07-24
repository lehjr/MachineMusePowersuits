package com.lehjr.powersuits.common.item.module.miningenhancement;

import com.google.common.util.concurrent.AtomicDouble;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.enhancement.MiningEnhancement;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.render.highlight.IHighlight;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.config.module.MiningEnhancementModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class VeinMinerModule extends AbstractPowerModule {
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
        public boolean isAllowed() {
            return MiningEnhancementModuleConfig.veinMinerModuleIsAllowed;
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
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
            if (block == Blocks.AIR || (!player.isCreative() && block == Blocks.BEDROCK) || !block.defaultBlockState().is(ORE_TAG)) {
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


        //
        //            List<BlockPos> getPosList(Block block, BlockPos startPos, Level world, String oreType) {
        //                List<BlockPos> list = new ArrayList<>() {{
        //                    add(startPos);
        //                }};
        //                for (Direction direction : Direction.values()) {
        //                    int i = 0;
        //                    while(i < 1000) { // prevent race condition
        //                        BlockPos pos2 = startPos.relative(direction, i);
        //
        //                        // no point looking beyond world limits
        //                        if (pos2.getY() >= world.getMaxBuildHeight() || pos2.getY() <= world.getMinBuildHeight()) {
        //                            break;
        //                        }
        //
        //                        BlockState state = world.getBlockState(pos2);
        //                        List<TagKey<Block>> tagsToTest = state.getTags().collect(Collectors.toList());
        //                        String oreTypeToTest = getOreType(tagsToTest);
        //                        // prioritize checking for same ore type
        //                        if (!oreType.isBlank() && !oreTypeToTest.isBlank() && oreType.equals(oreTypeToTest)) {
        //                            if (!list.contains(pos2)) {
        //                                list.add(pos2);
        //                            }
        //                            i++;
        //                        } else if (world.getBlockState(pos2).getBlock() == block) {
        //                            if (!list.contains(pos2)) {
        //                                list.add(pos2);
        //                            }
        //                            i++;
        //                        } else {
        //                            break;
        //                        }
        //                    }
        //                }
        //                return list;
        //            }
        //
        //            void harvestBlocks(List<BlockPos> posList, Level world, Player player, ItemStack itemStack) {
        //                for (BlockPos pos: posList) {
        ////                    Block.updateOrDestroy(world.getBlockState(pos), Blocks.AIR.defaultBlockState(), world, pos, Block.UPDATE_ALL);
        //
        //                    if (!world.isClientSide()) {
        //                        BlockEntity blockEntity = world.getBlockEntity(pos);
        //                        // setup drops checking for enchantments
        //                        Block.dropResources(world.getBlockState(pos), world, pos, blockEntity, player, itemStack);
        //                        // destroy block but don't drop default drops because they're already set above
        //                        player.level().destroyBlock(pos, false, player, 512);
        //                    }
        //
        //                }
        //            }
        //
        //            @Override
        //            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, Player player) {
        //                BlockState state = player.level().getBlockState(posIn);
        //                Block block = state.getBlock();
        //
        //                // filter out stone
        //                if (block == Blocks.STONE || block == Blocks.AIR) {
        //                    return false;
        //                }
        //
        //                double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        //                double energyUsage = this.getEnergyUsage();
        //
        //                AtomicInteger bbModuleEnergyUsage = new AtomicInteger(0);
        //
        //                itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER)
        //                        .filter(IModeChangingItem.class::isInstance)
        //                        .map(IModeChangingItem.class::cast)
        //                        .ifPresent(modeChanging -> {
        //                        for (ItemStack blockBreakingModule : modeChanging.getInstalledModulesOfType(IBlockBreakingModule.class)) {
        //                            if (blockBreakingModule.getCapability(NuminaCapabilities.POWER_MODULE).map(b -> {
        //                                if(b instanceof IBlockBreakingModule) {
        //                                    if (((IBlockBreakingModule) b).canHarvestBlock(itemStack, state, player, posIn, playerEnergy - energyUsage)) {
        //                                        bbModuleEnergyUsage.addAndGet(((IBlockBreakingModule) b).getEnergyUsage());
        //                                        return true;
        //                                    }
        //                                }
        //                                return false;
        //                            }).orElse(false)) {
        //                                break;
        //                            }
        //                        }
        //                    });
        //
        //                // check if block is an ore
        //                List<ResourceLocation> defaultOreTags = MPSSettings.getOreList();
        //                List<TagKey<Block>> oretags = state.getTags().collect(Collectors.toList());
        //
        //                /*
        //                    ------------------
        //                    Iron Ore
        //                    ------------------
        //                    tag: TagKey[minecraft:block / minecraft:iron_ores]
        //                    tag: TagKey[minecraft:block / minecraft:needs_stone_tool]
        //                    tag: TagKey[minecraft:block / net.forge:ores_in_ground/stone]
        //                    tag: TagKey[minecraft:block / minecraft:mineable/pickaxe]
        //                    tag: TagKey[minecraft:block / net.forge:ore_rates/singular]
        //                    tag: TagKey[minecraft:block / net.forge:ores/iron]
        //                    tag: TagKey[minecraft:block / net.forge:ores]
        //                    tag: TagKey[minecraft:block / minecraft:overworld_carver_replaceables]
        //                    tag: TagKey[minecraft:block / minecraft:snaps_goat_horn]
        //
        //                    ------------------
        //                    Block of Raw Iron
        //                    ------------------
        //                    tag: TagKey[minecraft:block / minecraft:mineable/pickaxe]
        //                    tag: TagKey[minecraft:block / net.forge:storage_blocks/raw_iron]
        //                    tag: TagKey[minecraft:block / minecraft:needs_stone_tool]
        //                    tag: TagKey[minecraft:block / minecraft:overworld_carver_replaceables]
        //                    tag: TagKey[minecraft:block / net.forge:storage_blocks]
        //
        //                    ------------------
        //                    Deepslate Iron Ore
        //                    ------------------
        //                    tag: TagKey[minecraft:block / net.forge:ore_rates/singular]
        //                    tag: TagKey[minecraft:block / minecraft:overworld_carver_replaceables]
        //                    tag: TagKey[minecraft:block / minecraft:needs_stone_tool]
        //                    tag: TagKey[minecraft:block / net.forge:ores/iron]
        //                    tag: TagKey[minecraft:block / minecraft:mineable/pickaxe]
        //                    tag: TagKey[minecraft:block / net.forge:ores]
        //                    tag: TagKey[minecraft:block / minecraft:iron_ores]
        //                    tag: TagKey[minecraft:block / net.forge:ores_in_ground/deepslate]
        //                 */
        //
        //                String oreType = getOreType(oretags);
        //                boolean isOre = !oreType.isBlank() || oretags.stream().anyMatch(tag-> defaultOreTags.contains(tag.location()));
        //
        //                if (isOre || MPSSettings.getBlockList().contains(ForgeRegistries.BLOCKS.getKey(block))) {
        //                    int energyRequired = this.getEnergyUsage() + bbModuleEnergyUsage.get();
        //
        //                    // does player have enough energy to break first block?
        //                    if (playerEnergy < energyRequired) {
        //                        return false;
        //                    }
        //
        //                    List<BlockPos> posList = getPosList(block, posIn, player.level(), oreType);
        //                    List<BlockPos> posListCopy = new ArrayList<>(posList);
        //
        //                    int size = 0;
        //                    int newSize = posListCopy.size();
        //
        //                    // is there more than one block?
        //                    if (newSize == 1) {
        //                        return false;
        //                    }
        //
        //                    // does player have enough energy to break initial list?
        //                    if (newSize * energyRequired > playerEnergy) {
        //                        posList = new ArrayList<BlockPos>(){{add(posIn);}};
        //                        posListCopy.remove(posIn);
        //
        //                        // repopulate list so the player has just enough energy
        //                        for (BlockPos pos : posListCopy) {
        //                            if ((posList.size() + 1) * energyRequired > playerEnergy) {
        //                                break;
        //                            } else {
        //                                posList.add(pos);
        //                            }
        //                        }
        //                        // create larger list
        //                    } else {
        //                        int i = 0;
        //                        while(i < 100 && size != newSize) {
        //                            size = posListCopy.size();
        //
        //                            outerLoop:
        //                            for (BlockPos pos : posListCopy) {
        //                                List<BlockPos> posList2 = getPosList(block, pos, player.level(), oreType);
        //                                for (BlockPos pos2 : posList2) {
        //                                    if(!posList.contains(pos2)) {
        //                                        // does player have enough energy to break initial list?
        //                                        if ((posList.size() +1) * energyRequired > playerEnergy) {
        //                                            i = 1000;
        //                                            break outerLoop;
        //                                        } else {
        //                                            posList.add(pos2);
        //                                        }
        //                                    }
        //                                }
        //                            }
        //                            newSize = posList.size();
        //                            posListCopy = new ArrayList<>(posList);
        //                            i++;
        //                        }
        //                    }
        //
        //                    if (!player.level().isClientSide()) {
        //                        ElectricItemUtils.drainPlayerEnergy(player, energyRequired * posList.size());
        //                    }
        //                    harvestBlocks(posList, player.level(), player, itemStack);
        //                }
        //                return false;
        //            }
        //
        //            @Override
        //            public int getEnergyUsage() {
        //                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        //            }
        //        }
        //
        //        /** ICapabilityProvider ----------------------------------------------------------------------- */
        //        @Override
        //        @Nonnull
        //        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
        //            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
        //            if (powerModuleCapability.isPresent()) {
        //                return powerModuleCapability;
        //            }
        //            return LazyOptional.empty();
        //        }
        //    }
        //
        //    static String getOreType(List<TagKey<Block>> oretags) {
        //        String oreType ="";
        //        for (TagKey<Block> tagKey: oretags) {
        //            String path = tagKey.location().getPath();
        //            if (path.contains("ores/")) {
        //                oreType = path.replace("ores/", "");
        //            } else if (path.contains("storage_blocks/raw_")) {
        //                oreType = path.replace("storage_blocks/raw_", "");
        //            }
        //        }
        //        return oreType;
        //    }



    }
}
