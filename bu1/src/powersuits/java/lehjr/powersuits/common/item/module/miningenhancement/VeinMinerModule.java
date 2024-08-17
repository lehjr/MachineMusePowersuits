package lehjr.powersuits.common.item.module.miningenhancement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class VeinMinerModule extends AbstractPowerModule {
//    ResourceLocation ORE_TAG = ResourceLocation.fromNamespaceAndPath("net.forge", "ores");
//    String STORAGE_BLOCK = "storage_blocks/raw_";
//
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final Enhancement miningEnhancement;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.miningEnhancement = new Enhancement(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                miningEnhancement.loadCapValues();
//                return miningEnhancement;
//            });
//        }
//
//        class Enhancement extends MiningEnhancement {
//            public Enhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
//                super(module, category, target, config);
//            }
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