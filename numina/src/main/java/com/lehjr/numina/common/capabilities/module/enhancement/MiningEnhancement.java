package com.lehjr.numina.common.capabilities.module.enhancement;

import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import com.lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
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