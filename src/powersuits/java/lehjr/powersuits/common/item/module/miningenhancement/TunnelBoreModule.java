package lehjr.powersuits.common.item.module.miningenhancement;

import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.enhancement.MiningEnhancement;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.render.highlight.IHighlight;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class TunnelBoreModule extends AbstractPowerModule {
    public static class Enhancement extends MiningEnhancement implements IHighlight {
        public Enhancement(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
            addTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.ENERGY_CONSUMPTION, 9500);
            // FIXME: mix of radius and diameter??? Just pick one!!!

            addIntTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.MINING_RADIUS, 5, "m", 2, 1);
            // TODO: overclock overrides for block breaking modules or recheck breaking speed?


        }

        @Override
        public int getTier() {
            return 1;
        }

        @Override
        public boolean isAllowed() {
            // FIXME
            return true;
        }


        /**
         * @param itemStack
         * @param hitResult
         * @param player
         * @param level
         * @return true to cancel, false to not
         */
        @Override
        public boolean onBlockStartBreak(ItemStack itemStack, BlockHitResult hitResult, Player player, Level level) {
            // Don't cancel if this isn't online...
            if (!isModuleOnline()) {
                return false;
            }

            // Shouldn't happen
            if (level.isClientSide) {
                return false; // fixme : check?
            }

            // Fixme!!! figure out if this should be radius or diameter. Looks like diameter to me
            int radius = (int) (applyPropertyModifiers(MPSConstants.MINING_RADIUS) - 1) / 2;
            if (radius == 0) {
                return false;
            }

            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

            if (getEnergyUsage() > playerEnergy) {
                return false;
            }
            playerEnergy -= getEnergyUsage();

            // TODO: check if stats are added to player blocks broken
            AtomicInteger blocksBroken = new AtomicInteger(0);

            IModeChangingItem mci = itemStack.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
            if (mci != null) {
                NonNullList<IBlockBreakingModule> modules = NonNullList.create();

                for (int i = 0; i < mci.getSlots(); i++) {
                    ItemStack module = mci.getStackInSlot(i);
                    IPowerModule pm = mci.getModuleCapability(module);
                    if (pm instanceof IBlockBreakingModule bm) {
                        modules.add(bm);
                    }
                }

                NonNullList<BlockPostions> posList = getBlockPositions(itemStack, hitResult, player, level, modules, playerEnergy);

                double energyUsage = getEnergyUsage();
                for (BlockPostions postionsRecord : posList) {
                    if (postionsRecord.canHarvest()) {
                        BlockPos blockPos = postionsRecord.pos().immutable();
                        BlockState state = level.getBlockState(blockPos);
                        BlockEntity blockEntity = level.getBlockEntity(blockPos);
                        // setup drops checking for enchantments
                        Block.dropResources(state, level, blockPos, blockEntity, player, itemStack);
                        // destroy block but don't drop default drops because they're already set above
                        level.destroyBlock(blockPos, false, player, 512);

                        // if creative then bbm will be null
                        if (!player.isCreative() && state.requiresCorrectToolForDrops() && postionsRecord.bbm() != null) {
                            IBlockBreakingModule bbm = postionsRecord.bbm();
                            energyUsage += bbm.getEnergyUsage();
                        }
                    }
                }
                ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
            }
            return false;
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public NonNullList<BlockPostions> getBlockPositions(@Nonnull ItemStack tool, @Nonnull BlockHitResult result, @Nonnull Player player, @Nonnull Level level, NonNullList<IBlockBreakingModule> modules, double playerEnergy) {
            NonNullList<BlockPostions> retList = NonNullList.create();
            BlockPos pos = result.getBlockPos();
            Direction side = result.getDirection();
            Stream<BlockPos> posList;

            int radius = (int) (applyPropertyModifiers(MPSConstants.MINING_RADIUS) - 1) / 2; // Block position +/- half diameter
            AtomicDouble energyRemaining = new AtomicDouble(playerEnergy);

            posList = switch (side) {
                // z axis
                case UP, DOWN -> BlockPos.betweenClosedStream(pos.north(radius).west(radius), pos.south(radius).east(radius));

                // x axis
                case EAST, WEST -> BlockPos.betweenClosedStream(pos.above(radius).north(radius), pos.below(radius).south(radius));

                // z axis
                case NORTH, SOUTH -> BlockPos.betweenClosedStream(pos.above(radius).west(radius), pos.below(radius).east(radius));
            };

            posList.forEach(blockPos->{
                // Super important to use immutable, otherwise you will only end up with copies of the last value
                BlockPos immutablePos = blockPos.immutable();
                BlockState state = level.getBlockState(immutablePos);
                if(!state.isAir()) {
                    for (IBlockBreakingModule bbm : modules) {
                        if (player.isCreative()) {
                            if(retList.isEmpty() || retList.stream().noneMatch(p->p.pos()==immutablePos)) {
                                retList.add(new BlockPostions(immutablePos, true, null));
                                break;
                            }
                        } else if(bbm.canHarvestBlock(tool, state, player, immutablePos, energyRemaining.get())) {
                            if(retList.isEmpty() || retList.stream().noneMatch(p->p.pos()==immutablePos)) {
                                retList.add(new BlockPostions(immutablePos, true, bbm));
                                energyRemaining.getAndAdd(-bbm.getEnergyUsage());
                                break;
                            }
                        }
                    }
                    if(retList.isEmpty() ||retList.stream().noneMatch(p->p.pos()==immutablePos)) {
                        retList.add(new BlockPostions(immutablePos, false, null));
                    }
                }
            });
            return retList;
        }
    }
}