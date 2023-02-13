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

package lehjr.powersuits.common.block;

import lehjr.numina.client.sound.Musique;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.powersuits.client.gui.modding.cosmetic.CosmeticGui;
import lehjr.powersuits.client.gui.modding.module.tweak.ModuleTweakGui;
import lehjr.powersuits.common.blockentity.TinkerTableBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class TinkerTable extends HorizontalBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape TOP_SHAPE = Block.box(
            0.0D, // West
            14.0D, // down?
            0.0D, // north
            16.0D, // east
            16.0D, // up?
            16.0D); // South

    public TinkerTable() {
        super(Block.Properties.of(Material.WOOD)
                .strength(1.5F, 1000.0F)
                .sound(SoundType.WOOD)
                .dynamicShape()
                .lightLevel((state) -> 15));
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (world.isClientSide) {
            openGui(world);
        }

        return ActionResultType.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(World world) {
        if (world.isClientSide) {

            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new CosmeticGui(Minecraft.getInstance().player.inventory, new TranslationTextComponent("gui.tinkertable"))));

//            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModuleTweakGui(new TranslationTextComponent("gui.tinkertable"), true)));
        }
    }

    @SuppressWarnings( "deprecation" )
    @Deprecated
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return TOP_SHAPE;
    }

    @Override
    public boolean canPlaceLiquid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return super.getOcclusionShape(state, worldIn, pos);
    }

    @Override
    public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return IWaterLoggable.super.placeLiquid(worldIn, pos, state, fluidStateIn);
    }

    public Fluid takeLiquid(IWorld worldIn, BlockPos pos, BlockState state) {
        return Fluids.EMPTY;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8));
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 2;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TinkerTableBlockEntity();
    }
}
