package com.lehjr.numina.common.capabilities.inventory.modularitem;

import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.numina.common.utils.StringUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IModularItem extends IItemHandlerModifiable, IItemHandler {
    /**
     * This is the "Modular Item" (weapon, armor, shield?) that has the inventory for modules.
     * @return
     */
    @Nonnull
    ItemStack getModularItemStack();

    /**
     * Any handheld item, tool, shield, crayon, whatever
     * @return
     */
    default boolean isHandHeld() {
        if(isTool()) {
            return true;
        }
        return false;
    }

    /**
     * A handheld item that is specifically a tool or weapon
     * @return
     */
    default boolean isTool() {
        return false;
    }

    /**
     * Tiers help improve a sense of progression.
     * @return actual tier
     */
    int getTier();

    /**
     * Ranged wrappers are used as a way of partitioning the storage so that some special purpose modules have a specific slot.
     * This is done as a way of mitigating performance issues of parsing the entire storage while having to perform related calculations
     * based on the presence of certain modules.
     * @return
     */
    Map<ModuleCategory, RangedWrapper> getRangedWrappers();

    void setRangedWrapperMap(Map<ModuleCategory, RangedWrapper> rangedWrappers);

    boolean isModuleValidForPlacement(int slot, @Nonnull ItemStack module);

    boolean isModuleValid (@Nonnull IPowerModule pm);

    boolean isModuleValid(@Nonnull ItemStack module);

    void updateModuleInSlot(int slot, @Nonnull ItemStack module);

    /**
     * Returns a range of slots (partition) for a given module category, or null if there is none
     * @param category
     * @return
     */
    Pair<Integer, Integer> getRangeForCategory(ModuleCategory category);

    int getStackLimit(final int slot, @Nonnull final ItemStack module);

    @Override
    default int getSlotLimit(int slot) {
        return 1;
    }

    int findInstalledModule(@Nonnull ItemStack module);

    int findInstalledModule(ResourceLocation registryName);

    boolean isModuleInstalled(ResourceLocation regName);

    boolean isModuleInstalled(@Nonnull ItemStack module);

    boolean isModuleInstalled(Item item);

    boolean isModuleOnline(ResourceLocation moduleName);

    /**
     * return true for a module is allowed and either can't be turned off or is on, otherwise return false
     */
    boolean isModuleOnline(ItemStack module);

    ItemStack getOnlineModuleOrEmpty(ResourceLocation moduleName);

    void toggleModule(ResourceLocation moduleName, boolean online);

    void setModuleBoolean(ResourceLocation moduleName, String key, boolean value);

    /**
     * TODO: WIP!! Still assessing what changes actually need to be made for things to work
     * @param moduleName
     * @param key
     * @param value
     * @return
     */
    void setModuleDouble(ResourceLocation moduleName, String key, double value);

    void setModuleFloat(ResourceLocation moduleName, String key, float value);

    void setModuleBlockState(ResourceLocation moduleName, BlockState state);

    void setModuleString(ResourceLocation moduleName, String key, String value);

    default List<ResourceLocation> getInstalledModuleNames() {
        List<ResourceLocation> locations = new ArrayList<>();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty()) {
                locations.add(ItemUtils.getRegistryName(module));
            }
        }
        return locations;
    }

    default NonNullList<ItemStack> getInstalledModules() {
        NonNullList<ItemStack> modules = NonNullList.create();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty()) {
                modules.add(module);
            }
        }
        return modules;
    }

    void tick(Player player, Level level, @Nonnull ItemStack itemStack);

    @Nullable
    default IPowerModule getModuleCapability(@Nonnull ItemStack module) {
        return module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
    }

    default String formatInfo(String string, double value) {
        return string + '\t' + StringUtils.formatNumberShort(value);
    }
}
