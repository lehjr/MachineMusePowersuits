package com.lehjr.numina.common.capabilities.module.enchantment;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.TagUtils;
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

    public EnchantmentModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
//         setting to and loading these just allow values to be persistant when capability reloads
        added = TagUtils.getModuleBoolean(module, "added");
        removed = TagUtils.getModuleBoolean(module, "removed");
    }

    @Override
    public void onPlayerTickActive(Player player, Level level, @Nonnull ItemStack item) {
        if (level.isClientSide()) {
            return;
        }

        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        int energyUsage = getEnergyUsage();

        if (playerEnergy > energyUsage) {
            addEnchantment(item);
            ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
            setAdded(true);
            setRemoved(false);
        } else {
            toggleModule(false);
        }
    }

    @Override
    public void onPlayerTickInactive(Player player, Level level, @Nonnull ItemStack item) {
        if (added && !removed) {
            removeEnchantment(item);
            setAdded(false);
            setRemoved(true);
        }
    }

    @Override
    public void setAdded(boolean added) {
        TagUtils.setModuleBoolean(getModule(), "added", added);
        this.added = added;
    }

    @Override
    public void setRemoved(boolean removed) {
        TagUtils.setModuleBoolean(getModule(), "removed", removed);
        this.removed = removed;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
