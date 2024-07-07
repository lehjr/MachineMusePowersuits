package lehjr.numina.common.capability.module.enhancement;

import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.rightclick.IRightClickModule;
import lehjr.numina.common.capability.module.toggleable.ToggleableModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MiningEnhancement extends ToggleableModule implements IRightClickModule, IMiningEnhancementModule {
    public MiningEnhancement(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public int getEnergyUsage() {
        return 0;
    }
}