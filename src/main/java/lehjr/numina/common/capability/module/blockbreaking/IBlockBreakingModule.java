package lehjr.numina.common.capability.module.blockbreaking;

import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;

public interface IBlockBreakingModule extends IPowerModule {
    default boolean canHarvestBlock(@Nonnull ItemStack stack, BlockState state, Player player, BlockPos pos, double playerEnergy) {
        return (playerEnergy >= this.getEnergyUsage() && isToolEffective(player.level(), pos, getEmulatedTool()));
    }

    boolean mineBlock(@Nonnull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy);

    void handleBreakSpeed(PlayerEvent.BreakSpeed event);

    int getEnergyUsage();

    @Nonnull
    ItemStack getEmulatedTool();


    default boolean isToolEffective(BlockGetter world, BlockPos pos, @Nonnull ItemStack emulatedTool) {
        BlockState state = world.getBlockState(pos);

        if(emulatedTool.isCorrectToolForDrops(state)) {
            return true;
        }

        if (Float.compare(state.getDestroySpeed(world, pos), -1.0F) <= 0 ) {// unbreakable
            return false;
        }

        if (emulatedTool.getItem().isCorrectToolForDrops(getEmulatedTool(), state)) {
            return true;
        }

        return false;
    }

    default boolean canPerformAction(ToolAction toolAction) {
        return getEmulatedTool().canPerformAction(toolAction);
    }

    default boolean blockCheckAndHarvest(Player player, Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == null || world.isEmptyBlock(pos) || block == Blocks.BEDROCK)
            return false;
        if ((block instanceof IShearable || block instanceof BushBlock || block instanceof LeavesBlock)
                && block.canHarvestBlock(state, world, pos, player) || block == Blocks.SNOW || block == Blocks.SNOW_BLOCK) {
            block.playerDestroy(world, player, pos, state, world.getBlockEntity(pos), new ItemStack(Items.IRON_SHOVEL));
            world.removeBlock(pos, false);
            return true;
        }
        return false;
    }
}
