package lehjr.powersuits.common.item.module.miningenhancement;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.miningenhancement.MiningEnhancement;
import lehjr.numina.common.capabilities.module.powermodule.*;
import lehjr.numina.common.capabilities.render.highlight.HighLightCapability;
import lehjr.numina.common.capabilities.render.highlight.Highlight;
import lehjr.numina.common.capabilities.render.highlight.IHighlight;
import lehjr.numina.common.energy.ElectricItemUtils;
import lehjr.powersuits.common.config.MPSSettings;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class TunnelBoreModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }
    /** TODO: Add cooldown timer */
    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Enhancement miningEnhancement;
        private final LazyOptional<IPowerModule> powerModuleHolder;
        private final Highlighter highlight;
        private final LazyOptional<IHighlight> highlightHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.miningEnhancement = new Enhancement(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{

                // FIXME!!
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
                addTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.ENERGY_CONSUMPTION, 9500);
                addIntTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.AOE_MINING_RADIUS, 5, "m", 2, 1);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                miningEnhancement.loadCapValues();
                return miningEnhancement;
            });

            this.highlight = new Highlighter();
            highlightHolder = LazyOptional.of(() -> highlight);
        }

        class Enhancement extends MiningEnhancement {
            public Enhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, Player player) {
                if (player.level.isClientSide) {
                    return false; // fixme : check?
                }

                AtomicBoolean harvested = new AtomicBoolean(false);
                HitResult rayTraceResult = getPlayerPOVHitResult(player.level, player, ClipContext.Fluid.SOURCE_ONLY);
                if (rayTraceResult == null || rayTraceResult.getType() != HitResult.Type.BLOCK) {
                    return false;
                }
                int radius = (int) (applyPropertyModifiers(MPSConstants.AOE_MINING_RADIUS) - 1) / 2;
                if (radius == 0) {
                    return false;
                }

                NonNullList<BlockPos> posList = highlight.getBlockPositions((BlockHitResult) rayTraceResult);
                int energyUsage = this.getEnergyUsage();

                AtomicInteger blocksBroken = new AtomicInteger(0);
                itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(modeChanging -> {
                            posList.forEach(blockPos-> {
                                BlockState state = player.level.getBlockState(blockPos);
                                // find an installed module to break current block
                                for (ItemStack blockBreakingModule : modeChanging.getInstalledModulesOfType(IBlockBreakingModule.class)) {
                                    int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
                                    if (blockBreakingModule.getCapability(PowerModuleCapability.POWER_MODULE)
                                            .filter(IBlockBreakingModule.class::isInstance)
                                            .map(IBlockBreakingModule.class::cast)
                                            .map(b -> {
                                                // check if module can break block
                                                if (b.canHarvestBlock(itemStack, state, player, blockPos, playerEnergy - energyUsage)) {
                                                    Block.updateOrDestroy(state, Blocks.AIR.defaultBlockState(), player.level, blockPos, Block.UPDATE_ALL);
                                                    ElectricItemUtils.drainPlayerEnergy(player, b.getEnergyUsage() + energyUsage);
                                                    return true;
                                                }
                                                return false;
                                            }).orElse(false)) {
                                        if (posIn == blockPos) { // center block
                                            harvested.set(true);
                                        }
                                        blocksBroken.getAndAdd(1);
                                        break;
                                    }
                                }
                            });
                        });
                return harvested.get();
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }
        }

        // TODO?? : check if can break these blocks before adding to list? (not very efficient)
        class Highlighter extends Highlight {

            @Override
            public NonNullList<BlockPos> getBlockPositions(BlockHitResult rayTraceResult) {
                NonNullList retList = NonNullList.create();

                if(miningEnhancement.isModuleOnline()) {
                    BlockPos pos = rayTraceResult.getBlockPos();
                    Direction side = rayTraceResult.getDirection();
                    Stream<BlockPos> posList;

                    int radius = (int) (miningEnhancement.applyPropertyModifiers(MPSConstants.AOE_MINING_RADIUS) - 1) / 2;

                    switch (side) {
                        case UP:
                        case DOWN:
                            posList = BlockPos.betweenClosedStream(pos.north(radius).west(radius), pos.south(radius).east(radius));
                            break;

                        case EAST:
                        case WEST:
                            posList = BlockPos.betweenClosedStream(pos.above(radius).north(radius), pos.below(radius).south(radius));
                            break;

                        case NORTH:
                        case SOUTH:
                            posList = BlockPos.betweenClosedStream(pos.above(radius).west(radius), pos.below(radius).east(radius));
                            break;

                        default:
                            posList = new ArrayList<BlockPos>().stream();
                    }

                    posList.forEach(blockPos -> {
                        retList.add(blockPos.immutable());
                    });
                }
                return retList;
            }
        }
        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = PowerModuleCapability.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            final LazyOptional<T> highlightCapability = HighLightCapability.HIGHLIGHT.orEmpty(capability, highlightHolder);
            if (highlightCapability.isPresent()) {
                return highlightCapability;
            }
            return LazyOptional.empty();
        }
    }
}
