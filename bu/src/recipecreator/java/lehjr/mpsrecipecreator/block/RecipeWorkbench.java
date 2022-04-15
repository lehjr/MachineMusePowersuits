package lehjr.mpsrecipecreator.block;

import lehjr.mpsrecipecreator.container.MPARCContainer;
import lehjr.numina.util.client.sound.SoundDictionary;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1.0F, 1.0F);
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(worldIn, pos));
//            player.addStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return ActionResultType.SUCCESS;
        }
    }

    private static final ITextComponent title = new TranslationTextComponent("container.crafting");
    public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((windowID, playerInventory, playerEntity) ->
                new MPARCContainer(windowID, playerInventory, IWorldPosCallable.create(worldIn, pos)), title);
    }
}