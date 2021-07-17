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

package com.github.lehjr.numina.block;

import com.github.lehjr.numina.dev.crafting.container.NuminaCraftingContainer;
import com.github.lehjr.numina.entity.NuminaArmorStandEntity;
import com.github.lehjr.numina.tileentity.ChargingBaseTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Base of the armor workstation.
 */
public class ChargingBaseBlock extends Block implements IWaterLoggable {
    private static final ITextComponent title = new TranslationTextComponent("container.crafting", new Object[0]);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape BASE_SHAPE = Block.box(
            0.0D, // West
            0.0D, // down?
            0.0D, // north
            16.0D, // east
            1.0D, // up?
            16.0D); // South

    public ChargingBaseBlock() {
        super(Block.Properties.of(Material.METAL)
                .strength(0.5F, 4.0F)
                .sound(SoundType.ANVIL)
                .harvestLevel(0)
//                .harvestTool(ToolType.PICKAXE)
                .requiresCorrectToolForDrops());
        registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.POWERED, false));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flags) {
        list.add(new TranslationTextComponent("message.charging_base", Integer.toString(/*Config.FIRSTBLOCK_GENERATE.get()*/ 1000)));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(BlockStateProperties.POWERED) ? super.getLightValue(state, world, pos) : 0;
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (p_225533_2_.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            p_225533_4_.openMenu(p_225533_1_.getMenuProvider(p_225533_2_, p_225533_3_));
            p_225533_4_.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public INamedContainerProvider getMenuProvider(BlockState p_220052_1_, World p_220052_2_, BlockPos p_220052_3_) {
        return new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
            return new NuminaCraftingContainer(p_220270_2_, p_220270_3_, IWorldPosCallable.create(p_220052_2_, p_220052_3_));
        }, new TranslationTextComponent("numina"));
    }
//
//
//
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
////        if (!world.isClientSide) {
////            TileEntity tileEntity = world.getBlockEntity(pos);
////            if (tileEntity instanceof ChargingBaseTileEntity) {
////                player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1.0F, 1.0F);
////                INamedContainerProvider containerProvider = new INamedContainerProvider() {
////                    @Override
////                    public ITextComponent getDisplayName() {
////                        return new TranslationTextComponent("screen.numina.charging_base");
////                    }
////
////                    @Override
////                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
////                        return new ChargingBaseContainer(i, world, pos, playerInventory, playerEntity);
////                    }
////                };
////                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getBlockPos());
////            } else {
////                throw new IllegalStateException("container provider is missing!");
////            }
////        }
////        return ActionResultType.SUCCESS;
//
//
//
//
//
//
//
//
//
//
//        player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1.0F, 1.0F);
//
////        if(!worldIn.isRemote) {
////            NetworkHooks.openGui((ServerPlayerEntity) player,
////                    new TinkerContainerProvider(0), (buffer) -> buffer.writeInt(0));
////        }
//
//        if (worldIn.isClientSide) {
//            return ActionResultType.SUCCESS;
//        } else {
//            player.openMenu(state.getMenuProvider(worldIn, pos));
////            player.addStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
//            return ActionResultType.SUCCESS;
//        }
//
////        if (worldIn.isRemote()) {
//////        Musique.playClientSound(, 1);
////            Minecraft.getInstance().enqueue(() -> Minecraft.getInstance().displayGuiScreen(new TestGui(new TranslationTextComponent("gui.tinkertable"))));
//////}
////        }
////        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
//    }


//    @Nullable
//    @Override
//    public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
//        return new NUminaContainerProvider(0);
//

//        return new SimpleNamedContainerProvider((windowID, playerInventory, playerEntity) -> {
//            return new WorkbenchContainer(windowID, playerInventory, IWorldPosCallable.of(worldIn, pos));
//        }, title);
//    }










    // temporary fix for armor stand spawned below the block
    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isClientSide && entityIn instanceof NuminaArmorStandEntity && pos.getY() > (int)entityIn.position().y) {
            entityIn.teleportTo(pos.getX() + 0.5, entityIn.position().y + 1, pos.getZ() + 0.5);
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, BlockStateProperties.POWERED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BASE_SHAPE;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ChargingBaseTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(BlockStateProperties.POWERED, false) // fixme: should indicate if item placed has stored power
                .setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8));
    }

    @Override
    public Fluid takeLiquid(IWorld worldIn, BlockPos pos, BlockState state) {
        return Fluids.EMPTY;
    }

    @Override
    public boolean canPlaceLiquid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return true;
    }

    @Override
    public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return IWaterLoggable.super.placeLiquid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}