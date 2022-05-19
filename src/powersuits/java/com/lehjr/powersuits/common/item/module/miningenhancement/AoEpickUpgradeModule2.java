package com.lehjr.powersuits.common.item.module.miningenhancement;

import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.miningenhancement.IMiningEnhancementModule;
import com.lehjr.numina.common.capabilities.module.miningenhancement.MiningEnhancement;
import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.IConfig;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.render.chameleon.Chameleon;
import com.lehjr.numina.common.capabilities.render.chameleon.ChameleonCapability;
import com.lehjr.numina.common.capabilities.render.chameleon.IChameleon;
import com.lehjr.numina.common.capabilities.render.highlight.HighLightCapability;
import com.lehjr.numina.common.capabilities.render.highlight.Highlight;
import com.lehjr.numina.common.capabilities.render.highlight.IHighlight;
import com.lehjr.numina.common.energy.ElectricItemUtils;
import com.lehjr.powersuits.client.control.KeybindKeyHandler;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
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
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IMiningEnhancementModule miningEnhancement;
        IChameleon chameleon;
        IHighlight highlight;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;

            this.miningEnhancement = new Enhancement(module, ModuleCategory.MINING_ENHANCEMENT, ModuleTarget.TOOLONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.AOE2_ENERGY, 500, "FE");
                addBaseProperty(MPSConstants.AOE2_LIMIT, 1);
                addIntTradeoffProperty(MPSConstants.AOE2_LIMIT, MPSConstants.AOE2_LIMIT, 59, "Blocks", 1, 0);
            }};

            this.chameleon = new Chameleon(module);
            this.highlight = new Highlighter();
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == HighLightCapability.HIGHLIGHT) {
                return HighLightCapability.HIGHLIGHT.orEmpty(cap, LazyOptional.of(() -> highlight));
            }
            if (cap == ChameleonCapability.CHAMELEON) {
                return ChameleonCapability.CHAMELEON.orEmpty(cap, LazyOptional.of(() -> chameleon));
            }

            return CapabilityPowerModule.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> miningEnhancement));
        }

        class Enhancement extends MiningEnhancement {
            public Enhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config);
            }

            @Override
            public InteractionResultHolder<ItemStack> use(@NotNull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
                if (hand.equals(InteractionHand.MAIN_HAND) && worldIn.isClientSide()) {
                    if (KeybindKeyHandler.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT) || KeybindKeyHandler.isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                        BlockHitResult rayTraceResult = getPlayerPOVHitResult(playerIn.level, playerIn, ClipContext.Fluid.NONE);
                        if (!(rayTraceResult == null)) {
                            chameleon.setTargetBlock(worldIn.getBlockState(rayTraceResult.getBlockPos()).getBlock());
                        }
                    }
                }

                return super.use(itemStackIn, worldIn, playerIn, hand);
            }

            void harvestBlocks(List<BlockPos> posList, Level world) {
                for (BlockPos pos : posList) {
                    Block.updateOrDestroy(world.getBlockState(pos), Blocks.AIR.defaultBlockState(), world, pos, Block.UPDATE_ALL);
                }
            }

            @Override
            public boolean onBlockStartBreak(ItemStack itemStack, BlockPos posIn, Player player) {
                BlockState state = player.level.getBlockState(posIn);
                Block block = state.getBlock();

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
                                if (blockBreakingModule.getCapability(CapabilityPowerModule.POWER_MODULE)
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
                return chameleon.getTargetBlock().map(targetBlock -> getPosList(targetBlock, targetPos, Minecraft.getInstance().player.level)).orElse(NonNullList.create());
            }

            @Override
            public NonNullList<BlockPos> getBlockPositions(BlockHitResult result) {
                return findBlockPositionsClient(result.getBlockPos());
            }
        }

        NonNullList<BlockPos> getPosList(Block block, BlockPos startPos, Level world) {
            NonNullList<BlockPos> list = NonNullList.create();
            if (world.getBlockState(startPos).getBlock() == block) {
                list.add(startPos.immutable());

                int i = 1;
                // this is really, really stupid and if you have a better way, use it.
                outerLoop:
                while (list.size() <= miningEnhancement.applyPropertyModifiers(MPSConstants.AOE2_LIMIT) && i < 2 /* set at 2 for performance reassons */) {
                    for (BlockPos.MutableBlockPos mutable : BlockPos.spiralAround(startPos, i, Direction.EAST, Direction.SOUTH)) {
                        for (BlockPos.MutableBlockPos mutable2 : BlockPos.spiralAround(mutable, i, Direction.UP, Direction.NORTH)) {
                            for (BlockPos.MutableBlockPos mutable3 : BlockPos.spiralAround(mutable2, i, Direction.WEST, Direction.DOWN)) {
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
    }
}




