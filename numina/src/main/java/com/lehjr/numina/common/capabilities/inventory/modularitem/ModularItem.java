package com.lehjr.numina.common.capabilities.inventory.modularitem;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.tickable.IPlayerTickModule;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.numina.common.utils.TagUtils;
import com.lehjr.numina.imixin.common.item.IMixinRangedWrapper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ModularItem extends ComponentItemHandler implements IModularItem {
    final boolean isTool;
    final int tier;
    Map<ModuleCategory, RangedWrapper> rangedWrappers;

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

    @Override
    public boolean isModuleValid (@Nonnull IPowerModule pm) {
        if(pm.isAllowed() && pm.getTier() <= getTier()) {
            // check module target against item stack type
            switch (pm.getTarget()) {
                case ALLITEMS-> {
                    return true;
                }
                case HANDHELD -> {
                    return isHandHeld();
                }
                case TOOLONLY-> {
                    return isTool();
                }
                case ARMORONLY-> {
                    return getModularItemStack().getItem() instanceof ArmorItem;
                }
                case HEADONLY-> {
                    return getModularItemStack().getItem() instanceof ArmorItem
                            && ItemUtils.getEquipmentSlotForItem(getModularItemStack()) == EquipmentSlot.HEAD;
                }
                case TORSOONLY-> {
                    return getModularItemStack().getItem() instanceof ArmorItem
                            && ItemUtils.getEquipmentSlotForItem(getModularItemStack()) == EquipmentSlot.CHEST;
                }
                case LEGSONLY-> {
                    return getModularItemStack().getItem() instanceof ArmorItem
                            && ItemUtils.getEquipmentSlotForItem(getModularItemStack()) == EquipmentSlot.LEGS;
                }
                case FEETONLY-> {
                    return getModularItemStack().getItem() instanceof ArmorItem
                            && ItemUtils.getEquipmentSlotForItem(getModularItemStack()) == EquipmentSlot.FEET;
                }
                default-> {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isModuleValid(@Nonnull ItemStack module) {
        // empty item is valid
        if (module.isEmpty()) {
            return false;
        }

        IPowerModule cap = getModuleCapability(module);
        if (cap == null) {
            return false;
        }

        return isModuleValid(cap);
    }

    /**
     * Returns a range of slots (partition) for a given module category, or null if there is none
     * @param category
     * @return
     */
    @Nullable
    @Override
    public Pair<Integer, Integer> getRangeForCategory(ModuleCategory category) {
        RangedWrapper rangedWrapper = getRangedWrappers().get(category);
        if (rangedWrapper != null){
            return ((IMixinRangedWrapper)rangedWrapper).numina$getRange();
        }
        return null;
    }

    @Override
    public int getStackLimit(final int slot, @Nonnull final ItemStack module) {
        // allow empty slots, else empty slots will crash on opening container
        if (module.isEmpty()) {
            return 1;
        }

        if (isModuleValid(module)) {
            //  Check if the module is already in this slot instead of just blindly
            // checking if module is already installed somewhere. This is because this method will be called
            // on loading to check if the module is valid for the given slot.
            int isInstalled = findInstalledModule(module);
            if (isInstalled != -1 && isInstalled != slot) {
                NuminaLogger.logDebug("module already installed in slot: " + isInstalled);
                return 0;
            }

            // get the module category... CATEGORY_NONE) is actually just a fallback
            IPowerModule pm = getModuleCapability(module);
            ModuleCategory category = pm == null ? ModuleCategory.NONE : pm.getCategory();

            // Specfic module type for limited modules
            RangedWrapper wrapper = getRangedWrappers().get(category);

            // fallback on generic type if null
            if (wrapper == null) {
                wrapper = getRangedWrappers().get(ModuleCategory.NONE);
            }

            // Note, not using the actual wrapper mechanics, just slot ranges
            if (wrapper != null && ((IMixinRangedWrapper)wrapper).numina$contains(slot)) {
                if (getStackInSlot(slot).is(module.getItem())) {
                    return 1;
                }
                return getStackInSlot(slot).isEmpty() ? 1: 0;
            }
        }
        return 0;
    }

    /**
     * An alternative to isItemValid since the requirements are different WILL cause issues
     * @param slot
     * @param module
     * @return
     */
    @Override
    public boolean isModuleValidForPlacement(int slot, @Nonnull ItemStack module) {
        if (!isModuleValid(module)) {
            IPowerModule cap = getModuleCapability(module);
            if (cap == null) {
                return false;
            }
        }
        NuminaLogger.logDebug("==================================================================");
        NuminaLogger.logDebug("isValidForPlacement: <slot, module: <" + slot +", " +module +">"  );
        NuminaLogger.logDebug("isSlotEmpty: " + getStackInSlot(slot).isEmpty());
        NuminaLogger.logDebug("slotLimit: " + getStackLimit(slot, module));
        NuminaLogger.logDebug("isItemValid: " + isItemValid(slot, module));
        NuminaLogger.logDebug("------------------------------------------------------------------");
        return getStackInSlot(slot).isEmpty() && getStackLimit(slot, module) != 0 && isItemValid(slot, module);
    }

    @Override
    public int findInstalledModule(@Nonnull ItemStack module) {
        return findInstalledModule(ItemUtils.getRegistryName(module));
    }

    @Override
    public boolean isModuleInstalled(ResourceLocation regName) {
        return findInstalledModule(regName) > -1;
    }

    @Override
    public boolean isModuleInstalled(@Nonnull ItemStack module) {
        return findInstalledModule(module) > -1;
    }

    @Override
    public boolean isModuleInstalled(Item item) {
        return isModuleInstalled(ItemUtils.getRegistryName(item));
    }

    @Override
    public boolean isModuleOnline(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            return isModuleOnline(getStackInSlot(slot));
        }
        return false;
    }

    @Override
    public boolean isModuleOnline(ItemStack module) {
        IPowerModule pm = getModuleCapability(module);
        if(pm != null) {
            return pm.isModuleOnline() && pm.isAllowed();
        }
        return false;
    }

    @Override
    public ItemStack getOnlineModuleOrEmpty(ResourceLocation moduleName) {
        int i = findInstalledModule(moduleName);
        if (i > -1) {
            ItemStack module = getStackInSlot(i);
            if(isModuleOnline(module)) {
                return module;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void toggleModule(ResourceLocation moduleName, boolean online) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            ItemStack module = getStackInSlot(slot);
            IPowerModule pm = getModuleCapability(module);
            if (pm instanceof IToggleableModule tm) {
                ItemStack newModule = tm.toggleModule(online);
                updateModuleInSlot(slot, newModule);
            }
        }
    }

    @Override
    public void setModuleBoolean(ResourceLocation moduleName, String key, boolean value) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            ItemStack module = getStackInSlot(slot);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                ItemStack newModule = TagUtils.setModuleBoolean(module, key, value);
                updateModuleInSlot(slot, newModule);
            }
        }
    }

    /**
     * TODO: WIP!! Still assessing what changes actually need to be made for things to work
     * @param moduleName
     * @param key
     * @param value
     * @return
     */
    @Override
    public void setModuleDouble(ResourceLocation moduleName, String key, double value) {
        int i = findInstalledModule(moduleName);
        if(i > -1) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                ItemStack newModule = TagUtils.setModuleDouble(module, key, value);
                updateModuleInSlot(i, newModule);
            }
        }
    }

    @Override
    public void setModuleFloat(ResourceLocation moduleName, String key, float value) {
        int i = findInstalledModule(moduleName);
        if(i > -1) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                ItemStack newModule = TagUtils.setModuleFloat(module, key, value);
                updateModuleInSlot(i, newModule);
            }
        }
    }

    @Override
    public void setModuleBlockState(ResourceLocation moduleName, BlockState state) {
        int i = findInstalledModule(moduleName);
        NuminaLogger.logDebug("installedModule: " + i +", is i <= -1: " + (i <= -1));

        if(i > -1) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                ItemStack newModule = TagUtils.setModuleBlockState(module,state);
                updateModuleInSlot(i, newModule);
                NuminaLogger.logDebug("updated stack in slot to state: " + state);
            }
        } else {
            NuminaLogger.logDebug("failed to update Module: " + moduleName +", index: " + i);
        }
    }

    @Override
    public void setModuleString(ResourceLocation moduleName, String key, String value) {
        int i = findInstalledModule(moduleName);
        if(i > -1) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                ItemStack newModule = TagUtils.setModuleString(module, key, value);
                updateModuleInSlot(i, newModule);
            }
        }
    }

    @Override
    public int findInstalledModule(ResourceLocation registryName) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack testStack = getStackInSlot(i);
            if (!testStack.isEmpty()) {
                ResourceLocation registryNameOther = ItemUtils.getRegistryName(testStack);
                if (registryNameOther.getNamespace().equals(registryName.getNamespace())
                    && registryNameOther.getPath().equals(registryName.getPath())) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void tick(Player player, Level level, @Nonnull ItemStack itemStack) {
        for (int i = 0; i < getSlots(); i++) {
            IPowerModule pm = getModuleCapability(getStackInSlot(i));
            if(pm instanceof IPlayerTickModule ticker) {
                if(ticker.isAllowed() && ticker.isModuleOnline()) {
                    ticker.onPlayerTickActive(player, level, this.getModularItemStack());
                } else {
                    ticker.onPlayerTickInactive(player, level, this.getModularItemStack());
                }
            }
        }
    }

    // call server side only
    @Override
    public void updateModuleInSlot(int slot, @Nonnull ItemStack module) {
        this.updateContents(getContents(), module, slot);
    }
}
