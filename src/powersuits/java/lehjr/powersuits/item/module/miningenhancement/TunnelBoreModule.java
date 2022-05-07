package lehjr.powersuits.item.module.miningenhancement;

import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.util.capabilities.module.miningenhancement.IMiningEnhancementModule;
import lehjr.numina.util.capabilities.module.miningenhancement.MiningEnhancement;
import lehjr.numina.util.capabilities.module.powermodule.*;
import lehjr.numina.util.capabilities.render.chameleon.Chameleon;
import lehjr.numina.util.capabilities.render.chameleon.ChameleonCapability;
import lehjr.numina.util.capabilities.render.chameleon.IChameleon;
import lehjr.numina.util.capabilities.render.highlight.HighLightCapability;
import lehjr.numina.util.capabilities.render.highlight.Highlight;
import lehjr.numina.util.capabilities.render.highlight.IHighlight;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

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
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
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
                addBaseProperty(MPSConstants.AOE_ENERGY, 500, "FE");
                addTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.AOE_ENERGY, 9500);
                addIntTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.AOE_MINING_RADIUS, 5, "m", 2, 1);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                miningEnhancement.updateFromNBT();
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
            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, PlayerEntity player) {
                if (player.level.isClientSide) {
                    return false; // fixme : check?
                }

                AtomicBoolean harvested = new AtomicBoolean(false);
                RayTraceResult rayTraceResult = getPlayerPOVHitResult(player.level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
                if (rayTraceResult == null || rayTraceResult.getType() != RayTraceResult.Type.BLOCK) {
                    return false;
                }
                int radius = (int) (applyPropertyModifiers(MPSConstants.AOE_MINING_RADIUS) - 1) / 2;
                if (radius == 0) {
                    return false;
                }

                NonNullList<BlockPos> posList = highlight.getBlockPositions((BlockRayTraceResult) rayTraceResult);
                int energyUsage = this.getEnergyUsage();

                AtomicInteger blocksBroken = new AtomicInteger(0);
                itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
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
                                                    Block.updateOrDestroy(state, Blocks.AIR.defaultBlockState(), player.level, blockPos, Constants.BlockFlags.DEFAULT);
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
                return (int) applyPropertyModifiers(MPSConstants.AOE_ENERGY);
            }
        }

        // TODO?? : check if can break these blocks before adding to list? (not very efficient)
        class Highlighter extends Highlight {

            @Override
            public NonNullList<BlockPos> getBlockPositions(BlockRayTraceResult rayTraceResult) {
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
