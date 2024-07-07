package lehjr.powersuits.common.item.module.tool.misc;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.RightClickModule;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Created by User: Andrew2448
 * 7:13 PM 4/21/13
 */
public class LeafBlowerModule extends AbstractPowerModule {
    public static class RightClickie extends RightClickModule {
        public RightClickie(@Nonnull ItemStack module) {
            super(module, ModuleCategory.TOOL, ModuleTarget.TOOLONLY);
//                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
//                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, 9500);
//                addBaseProperty(MPSConstants.RADIUS, 1, "m");
//                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, 15);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(@NotNull ItemStack itemStackIn, Level worldIn, Player playerIn, InteractionHand hand) {
//            int radius = (int) applyPropertyModifiers(MPSConstants.RADIUS);
//            if (useBlower(radius, itemStackIn, playerIn, worldIn, playerIn.blockPosition()))
//                return InteractionResultHolder.success(itemStackIn);
            return InteractionResultHolder.pass(itemStackIn);
        }

        private boolean useBlower(int radius, ItemStack itemStack, Player player, Level world, BlockPos pos) {
//                int totalEnergyDrain = 0;
//                BlockPos newPos;
//                for (int i = pos.getX() - radius; i < pos.getX() + radius; i++) {
//                    for (int j = pos.getY() - radius; j < pos.getY() + radius; j++) {
//                        for (int k = pos.getZ() - radius; k < pos.getZ() + radius; k++) {
//                            newPos = new BlockPos(i, j, k);
//                            if (ToolHelpers.blockCheckAndHarvest(player, world, newPos)) {
//                                totalEnergyDrain += getEnergyUsage();
//                            }
//                        }
//                    }
//                }
//                ElectricItemUtils.drainPlayerEnergy(player, totalEnergyDrain);
            return true;
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }
    }
}