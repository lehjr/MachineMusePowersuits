package lehjr.powersuits.common.item.module.miningenhancement;

import lehjr.powersuits.common.item.module.AbstractPowerModule;

public class TunnelBoreModule extends AbstractPowerModule {
//    /** TODO: Add cooldown timer */
//    public class CapProvider implements ICapabilityProvider {
//        ItemStack module;
//        private final Enhancement miningEnhancement;
//        private final LazyOptional<IPowerModule> powerModuleHolder;
//        private final Highlighter highlight;
//        private final LazyOptional<IHighlight> highlightHolder;
//
//        public CapProvider(@Nonnull ItemStack module) {
//            this.module = module;
//            this.miningEnhancement = new Enhancement(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
//                addTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.ENERGY_CONSUMPTION, 9500);
//                addIntTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.MINING_RADIUS, 5, "m", 2, 1);
//            }};
//
//            powerModuleHolder = LazyOptional.of(() -> {
//                miningEnhancement.loadCapValues();
//                return miningEnhancement;
//            });
//
//            this.highlight = new Highlighter();
//            highlightHolder = LazyOptional.of(() -> highlight);
//        }
//
//        class Enhancement extends MiningEnhancement {
//            public Enhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
//                super(module, category, target, config);
//            }
//
//            @Override
//            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, Player player) {
//                if (player.level().isClientSide) {
//                    return false; // fixme : check?
//                }
//
//                AtomicBoolean harvested = new AtomicBoolean(false);
//                HitResult rayTraceResult = getPlayerPOVHitResult(player.level(), player, ClipContext.Fluid.SOURCE_ONLY);
//                if (rayTraceResult == null || rayTraceResult.getType() != HitResult.Type.BLOCK) {
//                    return false;
//                }
//                int radius = (int) (applyPropertyModifiers(MPSConstants.MINING_RADIUS) - 1) / 2;
//                if (radius == 0) {
//                    return false;
//                }
//
//                NonNullList<BlockPos> posList = highlight.getBlockPositions((BlockHitResult) rayTraceResult);
//                int energyUsage = this.getEnergyUsage();
//
//                AtomicInteger blocksBroken = new AtomicInteger(0);
//                itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER)
//                        .filter(IModeChangingItem.class::isInstance)
//                        .map(IModeChangingItem.class::cast)
//                        .ifPresent(modeChanging -> {
//                            posList.forEach(blockPos-> {
//                                BlockState state = player.level().getBlockState(blockPos);
//                                // find an installed module to break current block
//                                for (ItemStack blockBreakingModule : modeChanging.getInstalledModulesOfType(IBlockBreakingModule.class)) {
//                                    double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
//                                    if (blockBreakingModule.getCapability(NuminaCapabilities.POWER_MODULE)
//                                            .filter(IBlockBreakingModule.class::isInstance)
//                                            .map(IBlockBreakingModule.class::cast)
//                                            .map(b -> {
//                                                // check if module can break block
//                                                if (player.isCreative() || b.canHarvestBlock(itemStack, state, player, blockPos, playerEnergy - energyUsage)) {
//                                                    if (!player.level().isClientSide()) {
//                                                        BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
//                                                        // setup drops checking for enchantments
//                                                        Block.dropResources(state, player.level(), blockPos, blockEntity, player, itemStack);
//                                                        // destroy block but don't drop default drops because they're already set above
//                                                        player.level().destroyBlock(blockPos, false, player, 512);
//                                                        ElectricItemUtils.drainPlayerEnergy(player, b.getEnergyUsage() + energyUsage);
//                                                    }
//
//                                                    return true;
//                                                }
//                                                return false;
//                                            }).orElse(false)) {
//                                        if (posIn == blockPos) { // center block
//                                            harvested.set(true);
//                                        }
//                                        blocksBroken.getAndAdd(1);
//                                        break;
//                                    }
//                                }
//                            });
//                        });
//                return harvested.get();
//            }
//
//            @Override
//            public int getEnergyUsage() {
//                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
//            }
//        }
//
//        // TODO?? : check if can break these blocks before adding to list? (not very efficient)
//        class Highlighter extends Highlight {
//
//            @Override
//            public NonNullList<BlockPos> getBlockPositions(BlockHitResult rayTraceResult) {
//                NonNullList retList = NonNullList.create();
//
//                if(miningEnhancement.isModuleOnline()) {
//                    BlockPos pos = rayTraceResult.getBlockPos();
//                    Direction side = rayTraceResult.getDirection();
//                    Stream<BlockPos> posList;
//
//                    int radius = (int) (miningEnhancement.applyPropertyModifiers(MPSConstants.MINING_RADIUS) - 1) / 2;
//
//                    switch (side) {
//                        case UP:
//                        case DOWN:
//                            posList = BlockPos.betweenClosedStream(pos.north(radius).west(radius), pos.south(radius).east(radius));
//                            break;
//
//                        case EAST:
//                        case WEST:
//                            posList = BlockPos.betweenClosedStream(pos.above(radius).north(radius), pos.below(radius).south(radius));
//                            break;
//
//                        case NORTH:
//                        case SOUTH:
//                            posList = BlockPos.betweenClosedStream(pos.above(radius).west(radius), pos.below(radius).east(radius));
//                            break;
//
//                        default:
//                            posList = new ArrayList<BlockPos>().stream();
//                    }
//
//                    posList.forEach(blockPos -> {
//                        retList.add(blockPos.immutable());
//                    });
//                }
//                return retList;
//            }
//        }
//        /** ICapabilityProvider ----------------------------------------------------------------------- */
//        @Override
//        @Nonnull
//        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
//            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.POWER_MODULE.orEmpty(capability, powerModuleHolder);
//            if (powerModuleCapability.isPresent()) {
//                return powerModuleCapability;
//            }
//            final LazyOptional<T> highlightCapability = NuminaCapabilities.HIGHLIGHT.orEmpty(capability, highlightHolder);
//            if (highlightCapability.isPresent()) {
//                return highlightCapability;
//            }
//            return LazyOptional.empty();
//        }
//    }
}
