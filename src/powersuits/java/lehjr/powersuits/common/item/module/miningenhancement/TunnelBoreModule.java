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
import lehjr.powersuits.common.config.module.MiningEnhancementModuleConfig;
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

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TunnelBoreModule extends AbstractPowerModule {
    public static class Enhancement extends MiningEnhancement implements IHighlight {
        public Enhancement(@Nonnull ItemStack module) {
            super(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY);
            addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, MiningEnhancementModuleConfig.tunnelBoreModuleEnergyConsumptionBase, "FE/Block");
            addBaseProperty(NuminaConstants.HARVEST_SPEED, MiningEnhancementModuleConfig.tunnelBoreModuleHarvestSpeedBase, "x");
            addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, MiningEnhancementModuleConfig.tunnelBoreModuleEnergyConsumptionOverclockMultiplier);
            addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, MiningEnhancementModuleConfig.tunnelBoreModuleHarvestSpeedOverclockMultiplier);
            addIntTradeoffProperty(MPSConstants.DIAMETER, MPSConstants.MINING_DIAMETER, MiningEnhancementModuleConfig.tunnelBoreModuleMaxDiameter, "m", 2, 1);
        }

        @Override
        public int getTier() {
            return 1;
        }

        @Override
        public boolean isAllowed() {
            return MiningEnhancementModuleConfig.tunnelBoreModuleIsAllowed;
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

            int radius = (int) (applyPropertyModifiers(MPSConstants.MINING_DIAMETER) - 1) / 2;
            if (radius == 0) {
                return false;
            }

            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);

            if (getEnergyUsage() > playerEnergy) {
                return false;
            }
            playerEnergy -= getEnergyUsage();

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
                double moduleEnergyUsage = getEnergyUsage();
                double energyUsage = 0;
                for (Map.Entry<IHighlight.BlockPostionData, Integer> entry : getBlockPositions(itemStack, hitResult, player, level, modules, playerEnergy).entrySet()) {
                    IHighlight.BlockPostionData blockPostionData = entry.getKey();
                    int miningLevel = entry.getValue();
                    if (blockPostionData.canHarvest()) {
                        BlockPos blockPos = blockPostionData.pos().immutable();
                        BlockState state = level.getBlockState(blockPos);
                        BlockEntity blockEntity = level.getBlockEntity(blockPos);
                        // setup drops checking for enchantments
                        Block.dropResources(state, level, blockPos, blockEntity, player, itemStack);
                        // destroy block but don't drop default drops because they're already set above
                        level.destroyBlock(blockPos, false, player, 512);

                        // if creative then bbm will be null
                        if (!player.isCreative() && state.requiresCorrectToolForDrops() && blockPostionData.bbm() != null) {
                            IBlockBreakingModule bbm = blockPostionData.bbm();
                            energyUsage += bbm.getEnergyUsage();
                            if(miningLevel == 2) {
                                energyUsage += moduleEnergyUsage;
                            }
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
        public HashMap<BlockPostionData, Integer> getBlockPositions(@Nonnull ItemStack tool, @Nonnull BlockHitResult result, @Nonnull Player player, @Nonnull Level level, NonNullList<IBlockBreakingModule> modules, double playerEnergy) {
            HashMap<BlockPostionData, Integer> retMap = new HashMap<>();
            BlockPos pos = result.getBlockPos();
            Direction side = result.getDirection();
            Stream<BlockPos> posList;
            int radius = (int) (applyPropertyModifiers(MPSConstants.MINING_DIAMETER) - 1) / 2; // Block position +/- half diameter
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
                            retMap.put(new BlockPostionData(immutablePos, true, null), 2);
                            break;
                        } else if (bbm.canHarvestBlock(tool, state, player, immutablePos, energyRemaining.get())) {
                            if(retMap.isEmpty() || retMap.keySet().stream().noneMatch(p->p.pos()==immutablePos)) {
                                retMap.put(new BlockPostionData(immutablePos, true, bbm), 1);
                                energyRemaining.getAndAdd(-bbm.getEnergyUsage());
                                break;
                            }
                        }
                    }
                    if(retMap.isEmpty() ||retMap.keySet().stream().noneMatch(p->p.pos()==immutablePos)) {
                        retMap.put(new BlockPostionData(immutablePos, false, null), 0);
                    }
                }
            });

            if(player.isCreative()) {
                return retMap;
            }

            final int energyUsage = getEnergyUsage();
            if (energyUsage <= energyRemaining.get()) {
                HashMap<BlockPostionData, Integer> retMap2 = new HashMap<>();
                retMap.forEach((key, value) -> {
                    int val = value;
                    if (val == 0) {
                        retMap2.put(key, 0);
                    } else {
                        if (energyRemaining.get() >= energyUsage) {
                            energyRemaining.getAndAdd(-energyUsage);
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
    }
}
