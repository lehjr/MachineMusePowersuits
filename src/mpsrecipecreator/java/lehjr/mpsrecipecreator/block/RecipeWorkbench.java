package lehjr.mpsrecipecreator.block;

import lehjr.mpsrecipecreator.container.MPSRCMenu;
import lehjr.numina.client.sound.SoundDictionary;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

/**
 * @author lehjr
 */
public class RecipeWorkbench extends Block {
    public RecipeWorkbench() {
        super(Block.Properties.of()
                .strength(1.5F, 1000.0F)
                .sound(SoundType.METAL)
                .dynamicShape()
                .lightLevel((state) -> 15));
        registerDefaultState(this.stateDefinition.any());
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        player.playSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1.0F, 1.0F);
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            NetworkHooks.openScreen((ServerPlayer) player,
                    new SimpleMenuProvider((windowID, inventory, playerEntity) ->
                            new MPSRCMenu(windowID, inventory), title),
                    buf -> buf.writeBlockPos(pos));
            return InteractionResult.SUCCESS;
        }
    }

    private static final Component title = Component.translatable("container.crafting");

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) ->
                new MPSRCMenu(id, inventory, ContainerLevelAccess.create(level, pos)), title);
    }
}