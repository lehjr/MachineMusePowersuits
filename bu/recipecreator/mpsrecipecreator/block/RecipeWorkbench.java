package com.lehjr.mpsrecipecreator.block;

import com.lehjr.mpsrecipecreator.container.MPARCContainer;
import lehjr.numina.util.client.sound.SoundDictionary;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.Player;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.util.text.Component;
import net.minecraft.util.text.TranslatableComponent;
import net.minecraft.world.World;

/**
 * @author lehjr
 */
public class RecipeWorkbench extends Block {
    public RecipeWorkbench(String regName) {
        this(new ResourceLocation(regName));
    }

    public RecipeWorkbench(ResourceLocation regName) {
        super(Block.Properties.of(Material.WOOD)
                .strength(1.5F, 1000.0F)
                .sound(SoundType.METAL)
                .dynamicShape()
                .lightLevel((state) -> 15));
        setRegistryName(regName);
        registerDefaultState(this.stateDefinition.any());
    }

    @Override
    public InteractionResult use(BlockState state, World worldIn, BlockPos pos, Player player, Hand handIn, BlockHitResult hit) {
        player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1.0F, 1.0F);
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(worldIn, pos));
//            player.addStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return InteractionResult.SUCCESS;
        }
    }

    private static final Component title = new TranslatableComponent("container.crafting");
    public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((windowID, playerInventory, playerEntity) ->
                new MPARCContainer(windowID, playerInventory, IWorldPosCallable.create(worldIn, pos)), title);
    }
}