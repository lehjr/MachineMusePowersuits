package lehjr.numina.common.capabilities.module.enhancement;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MiningEnhancement extends ToggleableModule implements IRightClickModule, IMiningEnhancementModule {
    public MiningEnhancement(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
    }
//    public MiningEnhancement(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> moduleConfigGetterIn) {
//        super(module, category, target, moduleConfigGetterIn, true);
//    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public int getEnergyUsage() {
        return 0;
    }
}