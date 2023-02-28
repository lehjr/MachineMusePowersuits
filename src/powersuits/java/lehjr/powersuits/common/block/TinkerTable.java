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
import lehjr.powersuits.client.gui.modding.module.tweak.ModuleTweakGui;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class TinkerTable extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
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

//    @Override
//    public InteractionResult use(BlockState pState, Level level, BlockPos pPos, Player player, InteractionHand pHand, BlockHitResult pHit) {
//
//        if (!level.isClientSide()) {
//            SimpleMenuProvider container = new SimpleMenuProvider((id, inventory, player1) -> new ModularItemInventoryMenu(id, inventory, EquipmentSlot.MAINHAND), new TranslatableComponent("gui.powersuits.tab.install.salvage"));
//            NetworkHooks.openGui((ServerPlayer) player, container, buffer -> buffer.writeEnum(EquipmentSlot.MAINHAND));
//        }
//
//        return super.use(pState, level, pPos, player, pHand, pHit);
//    }



    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pPos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        if (!level.isClientSide()) {
            openGui(level);
        }

        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Level world) {
        if (world.isClientSide) {

            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
//            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new CosmeticGui(Minecraft.getInstance().player.inventory, new TranslatableComponent("gui.tinkertable"))));
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModuleTweakGui(new TranslatableComponent("gui.tinkertable"))));
//            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModuleTweakGui(new TranslatableComponent("gui.tinkertable"), true)));
        }
    }

    @SuppressWarnings( "deprecation" )
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TOP_SHAPE;
    }

//    @Override
//    public boolean func_204510_a(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
//        return true;
//    }
//
//    @Override
//    public FluidState func_204507_t(BlockState state) {
//        return state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
//    }
//
//    @Override
//    public VoxelShape func_196247_c(BlockState state, IBlockReader worldIn, BlockPos pos) {
//        return super.func_196247_c(state, worldIn, pos);
//    }
//
//    @Override
//    public boolean func_204509_a(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
//        return IWaterLoggable.super.func_204509_a(worldIn, pos, state, fluidStateIn);
//    }
//
//    public Fluid func_204508_a(IWorld worldIn, BlockPos pos, BlockState state) {
//        return Fluids.field_204541_a;
//    }
//
//    @Override
//    public BlockRenderType func_149645_b(BlockState state) {
//        return BlockRenderType.MODEL;
//    }
//
//    @Override
//    public int getHarvestLevel(BlockState state) {
//        return 2;
//    }
//
//    @Nullable
//    @Override
//    public ToolType getHarvestTool(BlockState state) {
//        return ToolType.PICKAXE;
//    }
//
//    @Override
//    protected void func_206840_a(StateAbstractContainerMenu.Builder<Block, BlockState> builder) {
//        builder.func_206894_a(field_185512_D).func_206894_a(WATERLOGGED);
//    }
//
//    @Override
//    public boolean hasTileEntity(BlockState state) {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
//        return new TinkerTableBlockEntity();
//    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED);
    }
}
