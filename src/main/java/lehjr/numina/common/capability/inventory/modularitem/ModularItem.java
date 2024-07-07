package lehjr.numina.common.capability.inventory.modularitem;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.IPlayerTickModule;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ModularItem extends ComponentItemHandler implements IModularItem {
    final boolean isTool;
    final int tier;
    Map<ModuleCategory, RangedWrapper> rangedWrappers;
    ModuleTarget target;

    public ModularItem(@Nonnull ItemStack modularItem, int tier, int size) {
        this(modularItem, tier, size, false);
    }

    public ModularItem(@Nonnull ItemStack modularItem, int tier, int size, boolean isTool) {
        super(modularItem, DataComponents.CONTAINER, size);
        this.rangedWrappers = new HashMap<>();
        this.isTool = isTool;
        this.tier = tier;
    }

    @Nonnull
    @Override
    public ItemStack getModularItemStack() {
        return (ItemStack) parent;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public Map<ModuleCategory, RangedWrapper> getRangedWrappers() {
        return rangedWrappers;
    }

    @Override
    public void setRangedWrapperMap(Map<ModuleCategory, RangedWrapper> rangedWrappersIn) {
        this.rangedWrappers = rangedWrappersIn;
    }

    /**
     * An alternative to isItemValid since the requirements are different WILL cause issues
     * @param slot
     * @param module
     * @return
     */
    @Override
    public boolean isModuleValidForPlacement(int slot, @Nonnull ItemStack module) {
        return isItemValid(slot, module) && isModuleValid(module);
    }

    @Override
    public void tick(Player player) {
        for (int i = 0; i < getSlots(); i++) {
            NuminaCapabilities.getCapability(getStackInSlot(i), NuminaCapabilities.Module.POWER_MODULE)
                    .filter(IPlayerTickModule.class::isInstance)
                    .map(IPlayerTickModule.class::cast)
                    .filter(IPlayerTickModule::isAllowed).ifPresent(m -> {
                        if (m.isModuleOnline()) {
                            m.onPlayerTickActive(player, this.getModularItemStack());
                        } else {
                            m.onPlayerTickInactive(player, this.getModularItemStack());
                        }
                    });
        }
    }

    @Override
    public void updateModuleInSlot(int slot, @NotNull ItemStack module) {
        this.updateContents(getContents(), module, slot);
    }
}
