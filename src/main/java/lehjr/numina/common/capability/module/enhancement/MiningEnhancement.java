package lehjr.numina.common.capability.module.enhancement;

import lehjr.numina.common.capability.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.capability.module.toggleable.ToggleableModule;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class MiningEnhancement extends ToggleableModule implements IRightClickModule, IMiningEnhancementModule {
    public MiningEnhancement(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockHitResult hitResult, Player player, Level level) {
        return false;
    }

    @Override
    public int getEnergyUsage() {
        return 0;
    }


    @Override
    public NonNullList<BlockPostions> getBlockPositions(@NotNull ItemStack tool, @NotNull BlockHitResult result, @NotNull Player player, @NotNull Level level, NonNullList<IBlockBreakingModule> modules, double playerEnergy) {
        return NonNullList.create();
    }
}