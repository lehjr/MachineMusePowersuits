package lehjr.numina.common.capabilities.module.enchantment;

import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.utils.ElectricItemUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

/**
 * Uses player tick to apply/update enchantment while applying appropriate energy drain or remove enchantment when tuned off
 */
public abstract class EnchantmentModule extends PlayerTickModule implements IEnchantmentModule {
    boolean added;
    boolean removed;

    public EnchantmentModule(ItemStack module, ModuleCategory category, ModuleTarget target) {
        super(module, category, target);
//         setting to and loading these just allow values to be persistant when capability reloads
//        added = TagUtils.getBooleanOrFalse(module, "added");
//        removed = TagUtils.getBooleanOrFalse(module, "removed");
    }

    @Override
    public void onPlayerTickActive(Player player, @NotNull ItemStack item) {
        if (player.level().isClientSide()) {
            return;
        }

        double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
        int energyUsage = getEnergyUsage();

        if (playerEnergy > energyUsage) {
            addEnchantment(item);
            ElectricItemUtils.drainPlayerEnergy(player, energyUsage);
            setAdded(true);
            setRemoved(false);
        }
    }

    @Override
    public void onPlayerTickInactive(Player player, @NotNull ItemStack item) {
        if (added && !removed) {
            removeEnchantment(item);
            setAdded(false);
            setRemoved(true);
        }
    }

    @Override
    public void setAdded(boolean added) {
//        this.added = added;
//        TagUtils.setBoolean(module, "added", added);
    }

    @Override
    public void setRemoved(boolean removed) {
//        this.removed = removed;
//        TagUtils.setBoolean(module, "removed", removed);
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
