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

package com.github.lehjr.numina.util.capabilities.inventory.modularitem;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IPowerModule;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModularItem extends ItemStackHandler implements IModularItem {
    public static final String TAG_MODULES = "Modules"; // ModularItemHandler tag
    public static final String TAG_MODULE_SETTINGS = "Module Settings";

    ItemStack modularItem;
    Map<EnumModuleCategory, NuminaRangedWrapper> rangedWrappers;
    EnumModuleTarget target;

    public ModularItem(@Nonnull ItemStack modularItem, int size) {
        this(modularItem, NonNullList.withSize(size, ItemStack.EMPTY));
    }

    public ModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
    }

    @Override
    public void setRangedWrapperMap(Map<EnumModuleCategory, NuminaRangedWrapper> rangedWrappers) {
        this.rangedWrappers = rangedWrappers;
    }

    @Override
    public void toggleModule(ResourceLocation moduleName, boolean online) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && module.getItem().getRegistryName().equals(moduleName)) {
                final int index = i;
                module.getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(m -> {
                    if (m instanceof IToggleableModule) {
                        ((IToggleableModule) m).toggleModule(online);
                        // not enough to update the module, the tag for the item in the slot has to be updated too
                        onContentsChanged(index);
                    }
                });
            }
        }
    }

    @Override
    @Nullable
    public Pair getRangeForCategory(EnumModuleCategory category) {
        NuminaRangedWrapper rangedWrapper = rangedWrappers.get(category);
        if (rangedWrapper != null){
            return rangedWrapper.getRange();
        }
        return null;
    }

    @Override
    public List<ResourceLocation> getInstalledModuleNames() {
        List<ResourceLocation> locations = new ArrayList<>();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty()) {
                locations.add(module.getItem().getRegistryName());
            }
        }
        return locations;
    }

    @Override
    public NonNullList<ItemStack> getInstalledModulesOfType(Class<? extends IPowerModule> type) {
        NonNullList<ItemStack> modules = NonNullList.create();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (module.getCapability(PowerModuleCapability.POWER_MODULE).map(c ->
                    type.isAssignableFrom(c.getClass())).orElse(false))
                modules.add(module);
        }
        return modules;
    }

    @Override
    public NonNullList<ItemStack> getInstalledModules() {
        NonNullList<ItemStack> modules = NonNullList.create();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty()) {
                modules.add(module);
            }
        }
        return modules;
    }

    @Override
    public boolean isModuleInstalled(ResourceLocation regName) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty()) {
                if (module.getItem().getRegistryName().equals(regName))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack module) {
        return getStackLimit(slot, module) > 0;
    }

    @Override
    public boolean isModuleValid(@Nonnull ItemStack module) {
        // is module empty?
        if (module.isEmpty())
            return false;

        // is module valid for this targer?
        return (module.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {

            // check if allowed
            if (m.isAllowed()) {

                // check module target against item stack type
                switch (m.getTarget()) {
                    case ALLITEMS:
                        return true;
                    case TOOLONLY:
                        return modularItem.getItem() instanceof ToolItem;
                    case ARMORONLY:
                        return modularItem.getItem() instanceof ArmorItem;
                    case HEADONLY:
                        return modularItem.getItem() instanceof ArmorItem
                                && MobEntity.getSlotForItemStack(modularItem) == EquipmentSlotType.HEAD;
                    case TORSOONLY:
                        return modularItem.getItem() instanceof ArmorItem
                                && MobEntity.getSlotForItemStack(modularItem) == EquipmentSlotType.CHEST;
                    case LEGSONLY:
                        return modularItem.getItem() instanceof ArmorItem
                                && MobEntity.getSlotForItemStack(modularItem) == EquipmentSlotType.LEGS;
                    case FEETONLY:
                        return modularItem.getItem() instanceof ArmorItem
                                && MobEntity.getSlotForItemStack(modularItem) == EquipmentSlotType.FEET;
                    default:
                        return false;
                }
            }
            return false;
        }).orElse(false));
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public boolean isModuleOnline(ResourceLocation moduleName) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && module.getItem().getRegistryName().equals(moduleName)) {
                return isModuleOnline(module);
            }
        }
        return false;
    }

    /**
     * return true for a module is allowed and either can't be turned off or is on, otherwise return false
     */
    public boolean isModuleOnline(ItemStack module) {
        return module.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {
            if (m.isAllowed()) {
                if (m instanceof IToggleableModule) {
                    return ((IToggleableModule) m).isModuleOnline();
                }
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Nonnull
    @Override
    public ItemStack getOnlineModuleOrEmpty(ResourceLocation moduleName) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && module.getItem().getRegistryName().equals(moduleName)) {
                if (module.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {
                    if (m.isAllowed()) {
                       if(m instanceof IToggleableModule){
                            return ((IToggleableModule) m).isModuleOnline();
                        }
                       return true;
                    }
                    return false;
                }).orElse(false)) {
                    return module;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void tick(PlayerEntity player) {
        for (int i = 0; i < getSlots(); i++) {
            getStackInSlot(i).getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(m ->{
                if (m.isAllowed() && m instanceof IPlayerTickModule) {
                    if (((IPlayerTickModule) m).isModuleOnline()) {
                        ((IPlayerTickModule) m).onPlayerTickActive(player, this.getModularItemStack());
                    } else {
                        ((IPlayerTickModule) m).onPlayerTickInactive(player, this.getModularItemStack());
                    }
                }
            });
        }
    }

    @Nonnull
    @Override
    public ItemStack getModularItemStack() {
        return modularItem;
    }

    /**
     * IItemStackContainerUpdate -----------------------------------------------------------------
     */
    @Override
    public void updateFromNBT() {
        final CompoundNBT nbt = getModularItemStack().getOrCreateTag();
        if (nbt.contains(TAG_MODULE_SETTINGS, Constants.NBT.TAG_COMPOUND)) {
            deserializeNBT((CompoundNBT) nbt.get(TAG_MODULE_SETTINGS));
        }
    }

    /**
     * IItemHandler ------------------------------------------------------------------------------
     */
    @Override
    protected int getStackLimit(final int slot, @Nonnull final ItemStack module) {
        if (isModuleValid(module)) {
            // is module already installed?
            if (isModuleInstalled(module.getItem().getRegistryName()))
                return 0;

            // get the module category... CATEGORY_NONE) is actually just a fallback
            EnumModuleCategory category = module.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> m.getCategory())
                    .orElse(EnumModuleCategory.NONE);

            // Specfic module type for limited modules
            NuminaRangedWrapper wrapper = rangedWrappers.get(category);

            // fallback on generic type if null
            if (wrapper == null) {
                wrapper = rangedWrappers.get(EnumModuleCategory.NONE);
            }

            if (wrapper != null && wrapper.contains(slot)) {
                return wrapper.getStackInSlot(slot).isEmpty() ? 1: 0;
            }
        }
        return 0;
    }

    @Override
    public boolean setModuleTweakDouble(ResourceLocation moduleName, String key, double value) {
        boolean handled = false;
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && module.getItem().getRegistryName().equals(moduleName)) {
                if (module.getCapability(PowerModuleCapability.POWER_MODULE).map(m -> {
                    MuseNBTUtils.setModuleDoubleOrRemove(module, key, value);
                    return true;
                }).orElse(false)) {
                    onContentsChanged(i);
                    handled = true;
                    break;
                }
            }
        }
        return handled;
    }

    /**
     * ItemStackHandler --------------------------------------------------------------------------
     */
    @Override
    protected void onContentsChanged(final int slot) {
        super.onContentsChanged(slot);
        modularItem.setTagInfo(TAG_MODULE_SETTINGS, serializeNBT());
    }
}