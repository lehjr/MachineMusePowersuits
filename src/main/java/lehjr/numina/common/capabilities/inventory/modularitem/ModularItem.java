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

import lehjr.numina.common.capabilities.CapabilityUpdate;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.IPlayerTickModule;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModularItem extends ItemStackHandler implements IModularItem, CapabilityUpdate {
    final boolean isTool;
    ItemStack modularItem;
    Map<ModuleCategory, NuminaRangedWrapper> rangedWrappers;
    ModuleTarget target;

    public ModularItem(@Nonnull ItemStack modularItem, int size) {
        this(modularItem, size, false);
    }

    public ModularItem(@Nonnull ItemStack modularItem, int size, boolean isTool) {
        this(modularItem, NonNullList.withSize(size, ItemStack.EMPTY), isTool);
    }

    public ModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = false;
    }

    public ModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks, boolean isTool) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = isTool;
    }

    @Override
    public void setRangedWrapperMap(Map<ModuleCategory, NuminaRangedWrapper> rangedWrappers) {
        this.rangedWrappers = rangedWrappers;
    }

    @Override
    public void toggleModule(ResourceLocation moduleName, boolean online) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            ItemStack module = getStackInSlot(slot);
            module.getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(m -> {
                if (m instanceof IToggleableModule) {
                    ((IToggleableModule) m).toggleModule(online);
                    // not enough to update the module, the tag for the item in the slot has to be updated too
                    onContentsChanged(slot);
                }
            });

        }
    }

    @Override
    @Nullable
    public Pair getRangeForCategory(ModuleCategory category) {
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
                locations.add(ItemUtils.getRegistryName(module));
            }
        }
        return locations;
    }

    @Override
    public NonNullList<ItemStack> getInstalledModulesOfType(Class<? extends IPowerModule> type) {
        NonNullList<ItemStack> modules = NonNullList.create();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            module.getCapability(NuminaCapabilities.POWER_MODULE).filter(type::isInstance).ifPresent(pm -> modules.add(module));
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
        return findInstalledModule(regName) > -1;
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
        return (module.getCapability(NuminaCapabilities.POWER_MODULE).map(m -> {

            // check if allowed
            if (m.isAllowed()) {

                // check module target against item stack type
                switch (m.getTarget()) {
                    case ALLITEMS:
                        return true;
                    case TOOLONLY:
                        return isTool;
                    case ARMORONLY:
                        return modularItem.getItem() instanceof ArmorItem;
                    case HEADONLY:
                        return modularItem.getItem() instanceof ArmorItem
                                && Mob.getEquipmentSlotForItem(modularItem) == EquipmentSlot.HEAD;
                    case TORSOONLY:
                        return modularItem.getItem() instanceof ArmorItem
                                && Mob.getEquipmentSlotForItem(modularItem) == EquipmentSlot.CHEST;
                    case LEGSONLY:
                        return modularItem.getItem() instanceof ArmorItem
                                && Mob.getEquipmentSlotForItem(modularItem) == EquipmentSlot.LEGS;
                    case FEETONLY:
                        return modularItem.getItem() instanceof ArmorItem
                                && Mob.getEquipmentSlotForItem(modularItem) == EquipmentSlot.FEET;
                    default:
                        return false;
                }
            }
            return false;
        }).orElse(false));
    }

    @Override
    public boolean isModuleOnline(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            return isModuleOnline(getStackInSlot(slot));

        }
        return false;
    }

    /**
     * return true for a module is allowed and either can't be turned off or is on, otherwise return false
     */
    public boolean isModuleOnline(ItemStack module) {
        return module.getCapability(NuminaCapabilities.POWER_MODULE)
                .map(m -> m.isAllowed() && m.isModuleOnline()).orElse(false);
    }

    @Nonnull
    @Override
    public ItemStack getOnlineModuleOrEmpty(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            ItemStack module = getStackInSlot(slot);
            if (module.getCapability(NuminaCapabilities.POWER_MODULE).map(m -> m.isAllowed() && m.isModuleOnline()).orElse(false)) {
                return module;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void tick(Player player) {
        for (int i = 0; i < getSlots(); i++) {
            getStackInSlot(i).getCapability(NuminaCapabilities.POWER_MODULE)
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

    @Nonnull
    @Override
    public ItemStack getModularItemStack() {
        return modularItem;
    }

    /**
     * IItemHandler ------------------------------------------------------------------------------
     */
    @Override
    protected int getStackLimit(final int slot, @Nonnull final ItemStack module) {
        if (isModuleValid(module)) {
            // is module already installed?
            if (isModuleInstalled(ForgeRegistries.ITEMS.getKey(module.getItem()))) { // fixme.. use item check instead? maybe?
                return 0;
            }

            // get the module category... CATEGORY_NONE) is actually just a fallback
            ModuleCategory category = module.getCapability(NuminaCapabilities.POWER_MODULE).map(m -> m.getCategory())
                    .orElse(ModuleCategory.NONE);

            // Specfic module type for limited modules
            NuminaRangedWrapper wrapper = rangedWrappers.get(category);

            // fallback on generic type if null
            if (wrapper == null) {
                wrapper = rangedWrappers.get(ModuleCategory.NONE);
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
            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)) {
                if (module.getCapability(NuminaCapabilities.POWER_MODULE).map(m -> {
                    TagUtils.setModuleDoubleOrRemove(module, key, value);
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
        modularItem.addTagElement(TagConstants.TAG_MODULE_SETTINGS, serializeNBT());
    }

    @Override
    public void loadCapValues() {
        final CompoundTag nbt = getModularItemStack().getOrCreateTag();
        if (nbt.contains(TagConstants.TAG_MODULE_SETTINGS, Tag.TAG_COMPOUND)) {
            deserializeNBT((CompoundTag) nbt.get(TagConstants.TAG_MODULE_SETTINGS));
        }
    }

    @Override
    public void onValueChanged() {
    }
}