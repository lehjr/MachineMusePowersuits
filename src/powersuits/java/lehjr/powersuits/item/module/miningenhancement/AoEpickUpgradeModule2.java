package lehjr.powersuits.item.module.miningenhancement;

import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.util.capabilities.module.miningenhancement.IMiningEnhancementModule;
import lehjr.numina.util.capabilities.module.miningenhancement.MiningEnhancement;
import lehjr.numina.util.capabilities.module.powermodule.*;
import lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.util.capabilities.render.chameleon.Chameleon;
import lehjr.numina.util.capabilities.render.chameleon.ChameleonCapability;
import lehjr.numina.util.capabilities.render.chameleon.IChameleon;
import lehjr.numina.util.capabilities.render.highlight.HighLightCapability;
import lehjr.numina.util.capabilities.render.highlight.Highlight;
import lehjr.numina.util.capabilities.render.highlight.IHighlight;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.powersuits.client.control.KeybindKeyHandler;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * TODO: this will become the new AoE module... similar to the vein miner but to break selected block type
 */
public class AoEpickUpgradeModule2 extends AbstractPowerModule {
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Enhancement miningEnhancement;
        private final LazyOptional<IPowerModule> powerModuleHolder;
        private final Chameleon chameleon;

        private final LazyOptional<IChameleon> chameleonHolder;
        private final Highlighter highlight;
        private final LazyOptional<IHighlight> highlightHolder;


        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.miningEnhancement = new Enhancement(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.AOE2_ENERGY, 500, "FE");
                addBaseProperty(MPSConstants.AOE2_LIMIT, 1);
                addIntTradeoffProperty(MPSConstants.AOE2_LIMIT, MPSConstants.AOE2_LIMIT, 59, "Blocks", 1, 0);
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                miningEnhancement.updateFromNBT();
                return miningEnhancement;
            });

            this.chameleon = new Chameleon(module);
            chameleonHolder = LazyOptional.of(() -> {
                chameleon.updateFromNBT();
                return chameleon;
            });

            this.highlight = new Highlighter();
            highlightHolder = LazyOptional.of(() -> highlight);
        }

        class Enhancement extends MiningEnhancement implements IRightClickModule {
            public Enhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
                if (context.getHand().equals(Hand.MAIN_HAND) && context.getLevel().isClientSide()) {
                    if (KeybindKeyHandler.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT) || KeybindKeyHandler.isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                        BlockRayTraceResult rayTraceResult = getPlayerPOVHitResult(context.getLevel(), context.getPlayer(), RayTraceContext.FluidMode.NONE);
                        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
                            System.out.println("before set: " + chameleon.getTargetBlockRegName());

                            chameleon.setTargetBlock(context.getLevel().getBlockState(rayTraceResult.getBlockPos()).getBlock());

                            System.out.println("after set: " + chameleon.getTargetBlockRegName());
                        }
                    }
                }
                return super.onItemUseFirst(stack, context);
            }

            void harvestBlocks(List<BlockPos> posList, World world) {
                for (BlockPos pos : posList) {
                    Block.updateOrDestroy(world.getBlockState(pos), Blocks.AIR.defaultBlockState(), world, pos, Constants.BlockFlags.DEFAULT);
                }
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, PlayerEntity player) {
                BlockState state = player.level.getBlockState(posIn);
                Block block = state.getBlock();
                chameleon.updateFromNBT();
                // abort if block is not set
                if (block == Blocks.AIR || block == Blocks.BEDROCK || !chameleon.getTargetBlock().filter(targetBlock -> targetBlock == block).isPresent()) {
                    return false;
                }

                int playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
                int energyUsage = this.getEnergyUsage();

                AtomicInteger bbModuleEnergyUsage = new AtomicInteger(0);

                itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(modeChanging -> {
                            for (ItemStack blockBreakingModule : modeChanging.getInstalledModulesOfType(IBlockBreakingModule.class)) {
                                if (blockBreakingModule.getCapability(PowerModuleCapability.POWER_MODULE)
                                        .filter(IBlockBreakingModule.class::isInstance)
                                        .map(IBlockBreakingModule.class::cast)
                                        .map(b -> {
                                            if (b.canHarvestBlock(itemStack, state, player, posIn, playerEnergy - energyUsage)) {
                                                bbModuleEnergyUsage.addAndGet(b.getEnergyUsage());
                                                return true;
                                            }
                                            return false;
                                        }).orElse(false)) {
                                    break;
                                }
                            }
                        });


                if (true) {
                    int energyRequired = this.getEnergyUsage() + bbModuleEnergyUsage.get();

                    // does player have enough energy to break first block?
                    if (playerEnergy < energyRequired) {
                        return false;
                    }

                    NonNullList<BlockPos> posList = getPosList(block, posIn, player.level);
                    NonNullList<BlockPos> posListCopy = NonNullList.create();
                    for (BlockPos pos : posList) {
                        posListCopy.add(pos);
                    }

                    int size = 0;
                    int newSize = posListCopy.size();

                    // is there more than one block?
                    if (newSize == 1) {
                        return false;
                    }

                    // does player have enough energy to break initial list?
                    if (newSize * energyRequired > playerEnergy) {
                        posList = NonNullList.create();
                        posList.add(posIn);
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
                        while (i < 100 && size != newSize && posList.size() <= applyPropertyModifiers(MPSConstants.AOE2_LIMIT)) {
                            size = posListCopy.size();

                            outerLoop:
                            for (BlockPos pos : posListCopy) {
                                NonNullList<BlockPos> posList2 = getPosList(block, pos, player.level);
                                for (BlockPos pos2 : posList2) {
                                    if (!posList.contains(pos2)) {
                                        // does player have enough energy to break initial list?
                                        if ((posList.size() + 1) * energyRequired > playerEnergy) {
                                            i = 1000;
                                            break outerLoop;
                                        } else {
                                            posList.add(pos2);
                                        }
                                    }
                                }
                            }
                            newSize = posList.size();
                            posListCopy = NonNullList.create();
                            for (BlockPos pos : posList) {
                                posListCopy.add(pos);
                            }
                            i++;
                        }
                    }

                    // All blocks are the same, otherwise this would have to be calculated on the fly

                    if (!player.level.isClientSide()) {
                        ElectricItemUtils.drainPlayerEnergy(player, energyRequired * posList.size());
                    }
                    harvestBlocks(posList, player.level);
                }
                return false;
            }

            @Override
            public int getEnergyUsage() {
                return (int) applyPropertyModifiers(MPSConstants.AOE2_ENERGY);
            }
        }

        // TODO?? : check if can break these blocks before adding to list? (not very efficient)
        class Highlighter extends Highlight {
            @OnlyIn(Dist.CLIENT)
            NonNullList<BlockPos> findBlockPositionsClient(BlockPos targetPos) {
                chameleon.updateFromNBT();
                return chameleon.getTargetBlock().map(targetBlock -> getPosList(targetBlock, targetPos, Minecraft.getInstance().player.level)).orElse(NonNullList.create());
            }

            @Override
            public NonNullList<BlockPos> getBlockPositions(BlockRayTraceResult rayTraceResult) {
                return findBlockPositionsClient(rayTraceResult.getBlockPos());
            }
        }

        NonNullList<BlockPos> getPosList(Block block, BlockPos startPos, World world) {
            NonNullList<BlockPos> list = NonNullList.create();
            if (world.getBlockState(startPos).getBlock() == block) {
                list.add(startPos.immutable());

                int i = 1;
                // this is really, really stupid and if you have a better way, use it.
                outerLoop:
                while (list.size() <= miningEnhancement.applyPropertyModifiers(MPSConstants.AOE2_LIMIT) && i < 2 /* set at 2 for performance reassons */) {
                    for (BlockPos.Mutable mutable : BlockPos.spiralAround(startPos, i, Direction.EAST, Direction.SOUTH)) {
                        for (BlockPos.Mutable mutable2 : BlockPos.spiralAround(mutable, i, Direction.UP, Direction.NORTH)) {
                            for (BlockPos.Mutable mutable3 : BlockPos.spiralAround(mutable2, i, Direction.WEST, Direction.DOWN)) {
                                if (world.getBlockState(mutable3).getBlock() == block && !list.contains(mutable3)) {
                                    list.add(new BlockPos(mutable3).immutable());
                                }
                            }
                        }
                    }
                    i++;
                }
            }
            return list;
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = PowerModuleCapability.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            final LazyOptional<T> chameleonCapability = ChameleonCapability.CHAMELEON.orEmpty(capability, chameleonHolder);
            if (chameleonCapability.isPresent()) {
                return chameleonCapability;
            }
            final LazyOptional<T> highlightCapability = HighLightCapability.HIGHLIGHT.orEmpty(capability, highlightHolder);
            if (highlightCapability.isPresent()) {
                return highlightCapability;
            }
            return LazyOptional.empty();
        }
    }
}



