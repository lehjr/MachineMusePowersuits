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

package lehjr.numina.block;

import lehjr.numina.container.ChargingBaseContainer;
import lehjr.numina.entity.NuminaArmorStandEntity;
import lehjr.numina.tileentity.ChargingBaseBlockEntity;
import lehjr.numina.util.client.sound.SoundDictionary;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.BlockEntity;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.Component;
import net.minecraft.util.text.TranslatableComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Base of the armor workstation.
 */
public class ChargingBaseBlock extends Block implements IWaterLoggable {
    private static final Component title = new TranslatableComponent("container.crafting", new Object[0]);

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
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<Component> list, ITooltipFlag flags) {
        list.add(new TranslatableComponent("message.charging_base", Integer.toString(/*Config.FIRSTBLOCK_GENERATE.get()*/ 1000)));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(BlockStateProperties.POWERED) ? super.getLightValue(state, world, pos) : 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, World worldIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof ChargingBaseBlockEntity) {
                player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1.0F, 1.0F);
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.numina.charging_base");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, Player playerEntity) {
                        return new ChargingBaseContainer(i, worldIn, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException("container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

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
    public BlockEntity createBlockEntity(BlockState state, IBlockReader world) {
        return new ChargingBaseBlockEntity();
    }

    @Override
    public boolean hasBlockEntity(BlockState state) {
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