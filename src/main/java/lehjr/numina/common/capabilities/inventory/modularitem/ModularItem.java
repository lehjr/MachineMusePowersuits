/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.capabilities.inventory.modularitem;

import com.mojang.datafixers.util.Pair;
import lehjr.numina.common.capabilities.CapabilityUpdate;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.thruster.IThruster;
import lehjr.numina.common.capabilities.module.tickable.IPlayerTickModule;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.TagUtils;
import lehjr.numina.imixin.common.item.IMixinRangedWrapper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModularItem extends ItemStackHandler implements IModularItem, CapabilityUpdate {
    final boolean isTool;
    ItemStack modularItem;
    final int tier;
    Map<ModuleCategory, RangedWrapper> rangedWrappers;
    ModuleTarget target;

    public ModularItem(@Nonnull ItemStack modularItem, int tier, int size) {
        this(modularItem, tier, size, false);
    }

    public ModularItem(@Nonnull ItemStack modularItem, int tier, int size, boolean isTool) {
        this(modularItem, tier, NonNullList.withSize(size, ItemStack.EMPTY), isTool);
    }

    public ModularItem(@Nonnull ItemStack modularItem, int tier, NonNullList<ItemStack> stacks) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = false;
        this.tier = tier;
    }

    public ModularItem(@Nonnull ItemStack modularItem, int tier, NonNullList<ItemStack> stacks, boolean isTool) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = isTool;
        this.tier = tier;
    }

    @Override
    public ItemStack getModularItemStack() {
        return modularItem;
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
    public void setRangedWrapperMap(Map<ModuleCategory, RangedWrapper> rangedWrappers) {
        this.rangedWrappers = rangedWrappers;
    }

    @Override
    public boolean isModuleValid (IPowerModule pm) {
        // follow tier requirements
        if(pm.isAllowed() && pm.getTier() <= getTier()) {
            // check module target against item stack slotType
            switch (pm.getTarget()) {
                case ALLITEMS-> {
                    return true;
                }
                case TOOLONLY-> {
                    return isTool();
                }
                // this was meant for a shield but never quite got there
                case HANDHELD -> {
                    return isHandHeld();
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
    public boolean isModuleValid(ItemStack module) {
        // filter out empty or damaged items
        if (module.isEmpty() || module.isDamaged()) {
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

    /**
     * An alternative to isItemValid since the requirements are different WILL cause issues
     * @param slot
     * @param module
     * @return
     */
    @Override
    public boolean isModuleValidForPlacement(int slot, ItemStack module) {
        if (!isModuleValid(module)) {
            IPowerModule cap = getModuleCapability(module);
            if (cap == null) {
//                NuminaLogger.logDebug("no capability for module");
                return false;
            }
        }
//        NuminaLogger.logDebug("==================================================================");
//        NuminaLogger.logDebug("isValidForPlacement: <slot, module: <" + slot +", " +module +">"  );
//        NuminaLogger.logDebug("isSlotEmpty: " + getStackInSlot(slot).isEmpty());
//        NuminaLogger.logDebug("slotLimit: " + getStackLimit(slot, module));
//        NuminaLogger.logDebug("isItemValid: " + isItemValid(slot, module));
//        NuminaLogger.logDebug("------------------------------------------------------------------");
        return getStackInSlot(slot).isEmpty() && getStackLimit(slot, module) != 0 && isItemValid(slot, module);
    }

    @Override
    public boolean isModuleOnline(int slot) {
        if(isSlotValid(slot)) {
            return isModuleOnline(getStackInSlot(slot));
        }
        return false;
    }

    @Override
    public boolean isModuleOnline(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if (isSlotValid(slot)) {
            return isModuleOnline(getStackInSlot(slot));
        }
        return false;
    }

    /**
     * return true for a module is allowed and either can't be turned off or is on, otherwise return false
     */
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
        if (isSlotValid(i)) {
            ItemStack module = getStackInSlot(i);
            if(isModuleOnline(module)) {
                return module;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void toggleModule(boolean online, int moduleIndex) {
        if (isSlotValid(moduleIndex)) {
            ItemStack module = getStackInSlot(moduleIndex);
            IPowerModule pm = getModuleCapability(module);
            if (pm instanceof IToggleableModule tm) {
                tm.toggleModule(online);
                updateModuleInSlot(moduleIndex, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void toggleModule(ResourceLocation moduleName, boolean online) {
        int slot = findInstalledModule(moduleName);
        toggleModule(online, slot);
    }

    @Override
    public void setModuleBoolean(ResourceLocation moduleName, String key, boolean value) {
        int slot = findInstalledModule(moduleName);
        if (isSlotValid(slot)) {
            ItemStack module = getStackInSlot(slot);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                TagUtils.setModuleBoolean(module, key, value);
                updateModuleInSlot(slot, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public boolean setModuleDouble(int index, String key, double value) {
        if(isSlotValid(index)) {
            ItemStack module = getStackInSlot(index);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                double testValue = TagUtils.getModuleDouble(module, key);
                if(value != testValue) {
                    TagUtils.setModuleDouble(module, key, value);
                    updateModuleInSlot(index, ItemStack.EMPTY);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * TODO: WIP!! Still assessing what changes actually need to be made for things to work
     *
     * @param moduleName
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setModuleDouble(ResourceLocation moduleName, String key, double value) {
        return setModuleDouble(findInstalledModule(moduleName), key, value);
    }

    @Override
    public double getModuleDouble(int index, String key) {
        ItemStack module = getStackInSlot(index);
        IPowerModule pm = getModuleCapability(module);
        if (pm != null) {
            return TagUtils.getModuleDouble(module, key);
        }
        return 0;
    }

    @Override
    public double getModuleDouble(ResourceLocation moduleName, String key) {
        return getModuleDouble(findInstalledModule(moduleName), key);
    }

    @Override
    public void setModuleFloat(ResourceLocation moduleName, String key, float value) {
        int i = findInstalledModule(moduleName);
        if(isSlotValid(i)) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                TagUtils.setModuleFloat(module, key, value);
                updateModuleInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void setModuleBlockState(ResourceLocation moduleName, BlockState state) {
        int i = findInstalledModule(moduleName);
        if(isSlotValid(i)) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                TagUtils.setModuleBlockState(module,state);
                updateModuleInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void setModuleString(ResourceLocation moduleName, String key, String value) {
        int i = findInstalledModule(moduleName);
        if(isSlotValid(i)) {
            ItemStack module = getStackInSlot(i);
            IPowerModule pm = getModuleCapability(module);
            if (pm != null) {
                TagUtils.setModuleString(module, key, value);
                updateModuleInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack module) {
        return getStackLimit(slot, module) > 0;
    }

    @Override
    public boolean isModuleInstalled(ResourceLocation regName) {
        return findInstalledModule(regName) > -1;
    }

    @Override
    public boolean isModuleInstalled(ItemStack module) {
        return findInstalledModule(module.getItem()) > -1;
    }

    @Override
    public boolean isModuleInstalled(Item item) {
        return findInstalledModule(item) > -1;
    }

    /**
     * returns the module capability only if the module is present and online
     * @param moduleName
     * @return
     */
    @Nullable
    @Override
    public IPowerModule getModuleCapabilityOnlineOrNull(ResourceLocation moduleName) {
        IPowerModule pm = getModuleCapability(moduleName);
        if(pm != null && pm.isModuleOnline()) {
            return pm;
        }
        return null;
    }

    /**
     * returns the module capability if the module is present even if offline
     * @param moduleName
     * @return
     */
    @Nullable
    @Override
    public  IPowerModule getModuleCapability(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if(isSlotValid(slot)) {
            IPowerModule pm = getModuleCapability(getStackInSlot(slot));
            if (pm != null && pm.isAllowed()) {
                return pm;
            }
        }
        return null;
    }

    @Override
    public int findInstalledModule(Item item) {
        if(item != Items.AIR) {
            for (int i = 0; i < getSlots(); i++) {
                if(getStackInSlot(i).is(item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int findInstalledModule(ItemStack module) {
        return findInstalledModule(module.getItem());
    }

    @Override
    public int findInstalledModule(ResourceLocation registryName) {
        return findInstalledModule(BuiltInRegistries.ITEM.get(registryName));
    }

    @Override
    public void tick(Player player, Level level, ItemStack host) {
        for (int i = 0; i < getSlots(); i++) {
            IPowerModule pm = getModuleCapability(getStackInSlot(i));
            if(pm instanceof IPlayerTickModule ticker) {
                if(ticker.isAllowed() && ticker.isModuleOnline()) {
                    if(ticker.onPlayerTickActive(player, level, this.getModularItemStack(), i)) {
                        updateModuleInSlot(i, ticker.getModule());
                    }
                } else {
                    if(ticker.onPlayerTickInactive(player, level, this.getModularItemStack(), i)) {
                        updateModuleInSlot(i, ticker.getModule());
                    }
                }
            }

            if(pm instanceof IThruster thruster && !pm.isModuleOnline()) {
                thruster.stopSound(player, level);
            }
        }
    }

    // for porting compatibility
    @Override
    public void updateModuleInSlot(int slot, ItemStack ignored) {
//        setStackInSlot(slot, module);
        onContentsChanged(slot);
    }

    @Override
    public double getMovementResistance() {
        double movementResistance = 0;
        for(int i = 0; i < getSlots(); i++) {
            IPowerModule pm = getModuleCapability(getStackInSlot(i));
            if(pm != null && pm.isAllowed() && pm.isModuleOnline()) {
                movementResistance += pm.applyPropertyModifiers(NuminaConstants.MOVEMENT_RESISTANCE);
            }
        }
        return movementResistance;
    }

    /**
     * IItemHandler ------------------------------------------------------------------------------
     */
     @Override
    public int getStackLimit(final int slot, final ItemStack module) {
        // allow empty slots, else empty slots will crash on opening container
        if (module.isEmpty()) {
            return 1;
        }

        if (isModuleValid(module)) {
            //  Check if the module is already in this slot instead of just blindly
            // checking if module is already installed somewhere. This is because this method will be called
            // on loading to check if the module is valid for the given slot.
            int isInstalled = findInstalledModule(module);
            if (isSlotValid(isInstalled) && isInstalled != slot) {
//                NuminaLogger.logDebug("module already installed in slot: " + isInstalled);
                return 0;
            }

            // get the module category... CATEGORY_NONE) is actually just a fallback
            IPowerModule pm = getModuleCapability(module);
            ModuleCategory category = pm == null ? ModuleCategory.NONE : pm.getCategory();

            // Specfic module slotType for limited modules
            RangedWrapper wrapper = getRangedWrappers().get(category);

            // fallback on generic slotType if null
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
     * ItemStackHandler --------------------------------------------------------------------------
     */
    @Override
    protected void onContentsChanged(final int slot) {
        super.onContentsChanged(slot);
//        modularItem.addTagElement(TagConstants.TAG_MODULE_SETTINGS, serializeNBT());
        modularItem.addTagElement(TagConstants.TAG_ITEM_PREFIX, serializeNBT());
    }

    @Override
    public void loadCapValues() {
        final CompoundTag nbt = getModularItemStack().getOrCreateTag();
        if (nbt.contains(TagConstants.TAG_MODULE_SETTINGS, Tag.TAG_COMPOUND)) {
            nbt.put(TagConstants.TAG_ITEM_PREFIX, Objects.requireNonNull(nbt.get(TagConstants.TAG_MODULE_SETTINGS)));
            nbt.remove(TagConstants.TAG_MODULE_SETTINGS);
        }
        if(nbt.contains(TagConstants.TAG_ITEM_PREFIX)) {
            deserializeNBT((CompoundTag) nbt.get(TagConstants.TAG_ITEM_PREFIX));
        }
    }

    @Override
    public void onValueChanged() {
    }
}