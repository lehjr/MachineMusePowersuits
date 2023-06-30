package net.machinemuse.numina.common.item;

import net.machinemuse.numina.common.module.IModuleManager;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Interface for ItemPowerArmor and ItemPowerTool to share.
 *
 * @author MachineMuse
 * <p>
 * Ported to Java by lehjr on 11/3/16.
 */
public interface IModularItem {
    double getMaxBaseHeat(@Nonnull ItemStack itemStack);

    IModuleManager getModuleManager();
}
