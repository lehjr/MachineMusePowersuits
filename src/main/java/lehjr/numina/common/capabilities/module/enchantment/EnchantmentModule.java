package lehjr.numina.common.capabilities.module.enchantment;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Uses player tick to apply/update enchantment while applying appropriate energy drain or remove enchantment when tuned off
 */
public abstract class EnchantmentModule extends PlayerTickModule implements IEnchantmentModule {
    boolean added;
    boolean removed;

    public EnchantmentModule(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
        // setting to and loading these just allow values to be persistant when capability reloads
        added = TagUtils.getModuleBoolean(module, "added");
        removed = TagUtils.getModuleBoolean(module, "removed");
    }

    @Override
    public boolean onPlayerTickActive(Player player, Level level, ItemStack host, int moduleIndex) {
        if (level.isClientSide()) {
            return false;
        }

        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        double energyUsage = getEnergyUsage();

        if (playerEnergy > energyUsage) {
            addEnchantment(host);
            ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
            setAdded(true);
            setRemoved(false);
        }
        return false;
    }

    @Override
    public boolean onPlayerTickInactive(Player player, Level level, ItemStack host, int moduleIndex) {
        if (added && !removed) {
            removeEnchantment(host);
            setAdded(false);
            setRemoved(true);
        }
        return false;
    }

    @Override
    public void setAdded(boolean added) {
        this.added = added;
        TagUtils.setModuleBoolean(module, "added", added);
    }

    @Override
    public void setRemoved(boolean removed) {
        this.removed = removed;
        TagUtils.setModuleBoolean(module, "removed", removed);
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
