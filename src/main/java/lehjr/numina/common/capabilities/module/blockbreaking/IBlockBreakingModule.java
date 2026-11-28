package lehjr.numina.common.capabilities.module.blockbreaking;

import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
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
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;

public interface IBlockBreakingModule extends IPowerModule {
    default boolean canHarvestBlock(@Nonnull ItemStack stack, BlockState state, Player player, BlockPos pos, double playerEnergy) {
        return (playerEnergy >= this.getEnergyUsage() && isToolEffective(player.level(), pos));
    }

    boolean mineBlock(@Nonnull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy);

    void handleBreakSpeed(PlayerEvent.BreakSpeed event);

    @Override
    double getEnergyUsage();

    @Nonnull
    ItemStack getEmulatedTool();

    default boolean isToolEffective(BlockGetter world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (Float.compare(state.getDestroySpeed(world, pos), -1.0F) <= 0 ) {// unbreakable
            return false;
        }

        if(getEmulatedTool().isCorrectToolForDrops(state)) {
            return true;
        }
        return false;
    }

    default boolean canPerformAction(ItemAbility toolAction) {
        return getEmulatedTool().canPerformAction(toolAction);
    }
}
